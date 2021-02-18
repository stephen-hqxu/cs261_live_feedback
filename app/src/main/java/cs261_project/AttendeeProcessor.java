package cs261_project;

import javax.servlet.http.HttpSession;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import cs261_project.data_structure.*;

/**
 * Process group joining, template fetching and feedback submission for clients
 * @author Group 12 - Stephen Xu, JuanYan Huo, Ellen Tatum, JiaQi Lv, Alexander Odewale
 */
@Controller
@RequestMapping("/attendee")
public final class AttendeeProcessor {

    public AttendeeProcessor(){

    }

    @GetMapping("/feedbackForm")
    public final String serveFeedbackForm(HttpSession session, Model model){
        final Object eventid = session.getAttribute("EventID");
        //if no active event has found
        if(eventid == null){
            return "redirect:/joinEventPage";
        }

        final DatabaseConnection db = App.getInstance().getDbConnection();
        //fetch event
        final Event event = db.LookupEvent(Integer.parseInt(eventid.toString()));
        //render event details and display
        model.addAttribute("eventCode", event.getEventID());
        model.addAttribute("eventName", event.getEventName());

        //TODO render feedback template

        return "feedbackForm";
    }

    @PostMapping("/submitFeedback")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<String> processFeedback(Feedback feedback){

        //TODO make the Feedback.java matches the interface of the webpage parameters
        //TODO process feedback, the attendee needs to stay on the feedbackForm webpage

        return new ResponseEntity<String>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/leaveEvent")
    public final String handleLeaveEvent(HttpSession session){
        //remove event session id
        session.removeAttribute("EventID");
        session.invalidate();

        return "redirect:/joinEventPage";
    }

}
