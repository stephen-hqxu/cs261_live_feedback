package cs261_project;

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
    public final String serveEventsPage(){
        return "eventsPage";
    }

    @PostMapping("/newEvent")
    public final String handleNewEvent(Event event, @RequestParam() Map<String, String> args, HttpSession session){
        final Object hostid = session.getAttribute("HostID");
        //if no active login has found
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

        final boolean status = db.newEvent(event);
        if(!status){
            //usually the status will always be true
            return "newEventPage";
        }

        return "redirect:/host/hostHomePage";
    }

    @GetMapping("/viewFeedback")
    public final String serveViewFeedback(){

        //TODO view feedback

        return "viewFeedbackPage";
    }

    @PostMapping("/signout")
    public final String handleSignout(HttpSession session){
        //delete the host session
        session.removeAttribute("HostID");
        session.invalidate();
        
        return "redirect:/loginPage";
    }
}
