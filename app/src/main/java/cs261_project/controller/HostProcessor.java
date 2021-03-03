package cs261_project.controller;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import cs261_project.*;
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
    public final String serveCreateEventPage(HttpSession session){
        //login check
        if(session.getAttribute("HostID") == null){
            return "redirect:/loginPage";
        }

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
        model.addAttribute("current_time", LocalDateTime.now());

        return "eventsPage";
    }

    @PostMapping("/newEvent")
    public final String handleNewEvent(Event event, @RequestParam() Map<String, String> args, HttpSession session, Model model){
        final Object hostid = session.getAttribute("HostID");
        if(hostid == null){
            return "redirect:/loginPage";
        }

        final DatabaseConnection db = App.getInstance().getDbConnection();
        try{
            //parsing datetime
            //start
            String datetime = args.get("startDate").toString() + " " + args.get("startTime").toString();
            event.setStartDateTime(Event.StringToTempo(datetime));
            //finish
            datetime = args.get("endDate").toString() + " " + args.get("endTime").toString();
            event.setFinishDateTime(Event.StringToTempo(datetime));
            event.setHostID(Integer.parseInt(hostid.toString()));

            //make sure start time happens before finish time
            if(event.getStartDateTime().compareTo(event.getFinishDateTime()) > 0){
                model.addAttribute("error", "Start time must go before finish time.");
                return "newEventPage";
            }
        }catch(DateTimeParseException dtpe){
            model.addAttribute("error", "Invalid date time input.");
            return "newEventPage";
        }

        final int eventid = db.newEvent(event);
        if(eventid == -1){
            //event creation failed, stay at the current page
            return "redirect:/newEventPage";
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
                return "redirect:/newEventPage";
            }
        }

        //so far so good, redirect user back to home page
        return IndexProcessor.renderRedirect(
            "Your new event has been created. Redirecting back to home page...", 
            "/host/hostHomePage", 
            model);
    }

    //AJAX request
    @DeleteMapping("/deleteEvent")
    @ResponseBody
    public final boolean handleDeleteEvent(@RequestParam("eventCode") String eventid){
        final DatabaseConnection db = App.getInstance().getDbConnection();

        if(db.deleteEvent(Integer.parseInt(eventid))){
            return true;
        }

        return false;
    }

    @GetMapping("/viewFeedback")
    public final String serveViewFeedback(@RequestParam("eventCode") String eventid, HttpSession session, Model model){
        //login check
        if(session.getAttribute("HostID") == null){
            return "redirect:/loginPage";
        }
        if(eventid == null || eventid.isEmpty()){
            //no event code has been provided
            return "redirect:/host/hostHomePage";
        }
        final DatabaseConnection db = App.getInstance().getDbConnection();
        //render feedback page with event information
        final Event event = db.LookupEvent(Integer.parseInt(eventid));
        //also fetch template to display to the host
        final Template template = db.fetchTemplate(Integer.parseInt(eventid));
        if(event == null){
            //event not found
            return "redirect:/host/hostHomePage";
        }
        model.addAttribute("Event", event);
        model.addAttribute("StartDateTime", Event.TempoToString(event.getStartDateTime()));
        model.addAttribute("FinishDateTime", Event.TempoToString(event.getFinishDateTime()));
        //return an empty JSON array if there is no template
        model.addAttribute("templateQuestions", (template == null) ? "[]" : template.getQuestions());

        return "viewFeedbackPage";
    }

    //HTML SSE
    @GetMapping("/updateFeedback")
    @ResponseBody //it will return an object instead of view
    public final SseEmitter handleFeedbackUpdate(@RequestParam("eventCode") String eventid){
        if(eventid == null || eventid.isEmpty()){
            return null;
        }
        //set time out of the connection 10s
        SseEmitter emitter = new SseEmitter(10000L);
        final DatabaseConnection db = App.getInstance().getDbConnection();

        //non-blocking response, as there might be multiple hosts asking for response, and we don't want to stall any of them
        ExecutorService exec = Executors.newSingleThreadExecutor();
        exec.execute(new Runnable(){
            @Override
            public void run(){
                try{
                    //fetch feedback
                    //now regardless of any new feedback, it will always return
                    final List<Feedback> feedback = db.fetchFeedbacks(Integer.parseInt(eventid));
                    emitter.send(feedback);
                    
                }catch(IOException ioe){
                    emitter.completeWithError(ioe);
                }
                finally{
                    emitter.complete();
                }
            }
        });
        //shutdown the thread
        exec.shutdown();
        
        return emitter;
    }

    @GetMapping("/signout")
    public final String handleSignout(HttpSession session){
        //delete the host session
        session.removeAttribute("HostID");
        session.invalidate();
        
        return "redirect:/loginPage";
    }
}
