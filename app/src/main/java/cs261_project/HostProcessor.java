package cs261_project;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import cs261_project.data_structure.*;

/**
 * Process feedback fetching, mood analysis for event host whenever they asks for live feedback
 * @author Group 12 - Stephen Xu, JuanYan Huo, Ellen Tatum, JiaQi Lv, Alexander Odewale
 */
@Controller
@RequestMapping("/host")
public final class HostProcessor {
    
    public HostProcessor(){

    }

    @GetMapping("/hostHomePage")
    public final String servehostHomePage(HttpSession session, Model model){
        final Object hostid = session.getAttribute("HostID");
        //if no active login has found, redirect back to login page
        if(hostid == null){
            return "redirect:/loginPage";
        }

        final DatabaseConnection db = App.getInstance().getDbConnection();
        //get the host firstname and lastname
        final HostUser host = db.LookupHost(Integer.parseInt(hostid.toString()));
        if(host == null){
            //if host id invalid, usually it won't happen
            return "redirect:/loginPage";
        }
        //display host name
        model.addAttribute("Firstname", host.getFirstname());
        model.addAttribute("Lastname", host.getLastname());

        //render it
        return "hostHomePage";
    }

    @GetMapping("/newEventPage")
    public final String serveCreateEventPage(){
        return "newEventPage";
    }

    @GetMapping("/eventsPage")
    public final String serveEventsPage(HttpSession session, Model model){
        final Object hostid = session.getAttribute("HostID");
        if(hostid == null){
            return "redirect:/loginPage";
        }

        final DatabaseConnection db = App.getInstance().getDbConnection();
        //fetch all events belong to this host
        final List<Event> events = db.fetchEvents(Integer.parseInt(hostid.toString()));
        if(events == null || events.isEmpty()){
            //for some reason host has signed in but we could find any event (maybe he hasn't created any)
            model.addAttribute("error", "You don't have any event yet. Create one now!");
        }
        model.addAttribute("events", events);

        return "eventsPage";
    }

    @PostMapping("/newEvent")
    public final String handleNewEvent(Event event, @RequestParam() Map<String, String> args, HttpSession session){
        final Object hostid = session.getAttribute("HostID");
        if(hostid == null){
            return "redirect:/loginPage";
        }

        final DatabaseConnection db = App.getInstance().getDbConnection();
        //parsing datetime
        //start
        String datetime = args.get("startDate").toString() + " " + args.get("startTime").toString();
        event.setStartDateTime(Event.StringToTempo(datetime));
        //finish
        datetime = args.get("endDate").toString() + " " + args.get("endTime").toString();
        event.setFinishDateTime(Event.StringToTempo(datetime));
        event.setHostID(Integer.parseInt(hostid.toString()));

        final int eventid = db.newEvent(event);
        if(eventid == -1){
            //event creation failed, stay at the current page
            return "newEventPage";
        }
        event.setEventID(eventid);

        //parsing template questions (if any)
        final int num_template = Integer.parseInt(args.get("template_count"));
        if(num_template > 0){
            //there are customised questions from host
            Template template = new Template();

            template.setEventID(event.getEventID());
            //template_questions have been transalated into JSON
            template.setQuestions(args.get("template_questions"));

            final boolean status = db.createTemplate(template);
            if(!status){
                //template creation failed
                return "newEventPage";
            }
        }

        //so far so good, redirect user  back to home page
        return "redirect:/host/hostHomePage";
    }

    @GetMapping("/viewFeedback")
    public final String serveViewFeedback(){

        //TODO view feedback

        return "viewFeedbackPage";
    }

    @GetMapping("/signout")
    public final String handleSignout(HttpSession session){
        //delete the host session
        session.removeAttribute("HostID");
        session.invalidate();
        
        return "redirect:/loginPage";
    }
}
