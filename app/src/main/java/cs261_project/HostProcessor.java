package cs261_project;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import cs261_project.data_structure.Event;

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
    public final String servehostHomePage(){
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
    public final String handleNewEvent(Event event, @RequestParam() Map<String, String> args){
        //parsing datetime
        //TODO wrong datetime format, since HTML doesn't allow formatting, have to change from Java, will be modified after DatabaseConnection is implemented.
        //start
        String datetime = args.get("startDate").toString() + " " + args.get("startTime").toString() + ":00";
        //event.setStartDateTime(Event.StringToTempo(datetime));
        //finish
        datetime = args.get("endDate").toString() + " " + args.get("endTime").toString() + ":00";
        //event.setStartDateTime(Event.StringToTempo(datetime));

        //TODO Attendee number is forgotten in the database schema, will be added after DatabaseConnection is implemented
        //TODO create new event

        return "redirect:/host/hostHomePage";
    }

    @GetMapping("/viewFeedback")
    public final String serveViewFeedback(){

        //TODO view feedback

        return "viewFeedbackPage";
    }

}
