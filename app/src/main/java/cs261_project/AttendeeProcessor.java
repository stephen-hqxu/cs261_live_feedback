package cs261_project;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import cs261_project.data_structure.Feedback;

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
    public final String serveFeedbackForm(){

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
    public final String handleLeaveEvent(HttpServletRequest request){
        //remove event session id
        request.removeAttribute("EventID");

        return "redirect:/joinEventPage";
    }

}
