package cs261_project;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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

        //find template
        final Template template = db.fetchTemplate(event.getEventID());
        if(template != null){
            //template exists for this event, send to client
            //we send all JSON to client and let the web browser to parse the template to reduce work load on the server
            model.addAttribute("question", template.getQuestions());
        }

        return "feedbackForm";
    }

    @PostMapping("/submitFeedback")
    public String processFeedback(Feedback feedback, HttpSession session){
        final Object eventid = session.getAttribute("EventID");
        //if no active event has found
        if(eventid == null){
            return "redirect:/joinEventPage";
        }
        final DatabaseConnection db = App.getInstance().getDbConnection();
        //submit feedback to database
        feedback.setEventID(Integer.parseInt(eventid.toString()));
        final boolean status = db.submitFeedback(feedback);
        if(!status){
            //feedback submission failed
            return "feedbackForm";
        }

        //after the feedback has been submitted, we should automatically make them leave the event
        //since we are not asking attendees to submit multiple feedback
        return "redirect:/attendee/leaveEvent";
    }

    @GetMapping("/leaveEvent")
    public final String handleLeaveEvent(HttpSession session){
        //remove event session id
        session.removeAttribute("EventID");
        session.invalidate();

        return "redirect:/joinEventPage";
    }

}
