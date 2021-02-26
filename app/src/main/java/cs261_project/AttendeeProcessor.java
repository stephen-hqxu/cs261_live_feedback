package cs261_project;

import java.util.Base64;

import javax.annotation.Nullable;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import cs261_project.data_structure.*;

/**
 * Process group joining, template fetching and feedback submission for clients
 * @author Group 12 - Stephen Xu, JuanYan Huo, Ellen Tatum, JiaQi Lv, Alexander Odewale
 */
@Controller
@RequestMapping("/attendee")
public final class AttendeeProcessor {
    //Pattern matching for abusive language
    //however it can't deal with hidden abusal, for which neural network must be used
    //this is a base64 encoded message to avoid showing out the bad contents directly in the source code
    private static String ABUSIVE_PATTERN = 
    "LiooZnVja3xzaGl0fGJpdGNofG1vdGhlcmZ1Y2tlcnxqZXJrfG1vcm9ufGJhc3RhcmR8c3Vja3xhc3N8ZGlja3xwdXNzeXxkYW1ufGhlbGx8d2Fua2VyfGlkaW90fGN1bnR8d2hvcmV8d2hvcmluZykuKg==";

    static{
        //decode the content
        AttendeeProcessor.ABUSIVE_PATTERN = new String(Base64.getDecoder().decode(AttendeeProcessor.ABUSIVE_PATTERN));
    }

    public AttendeeProcessor(){

    }

    @GetMapping("/feedbackForm")
    public final String serveFeedbackForm(@RequestParam("error") @Nullable String error, HttpSession session, Model model){
        final Object eventid = session.getAttribute("EventID");
        //if no active event has found
        if(eventid == null){
            return "redirect:/joinEventPage";
        }

        if(error != null){
            //we don't need to inform user, just make them leaving the event without submitting the feedback
            model.addAttribute("error", "Your feedback has violated our community rules, please change your feedback content.");
        }

        final DatabaseConnection db = App.getInstance().getDbConnection();
        //fetch event
        final Event event = db.LookupEvent(Integer.parseInt(eventid.toString()));
        //render event details and display
        model.addAttribute("Event", event);

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

        //post processing
        final String fed_str = feedback.getFeedback();
        //firstly don't submit feedback if it contains bad language
        if(fed_str.matches(AttendeeProcessor.ABUSIVE_PATTERN)){
            return "redirect:/attendee/feedbackForm?error=" + "offensive";
        }
        //secondly calculate the average score for the provided mood and the sentiment analyser
        feedback.setMood((byte)(0.5 * (feedback.getMood() + SentimentAnalyzer.getSentimentType(fed_str))));

        //submit feedback to database
        feedback.setEventID(Integer.parseInt(eventid.toString()));
        final boolean status = db.submitFeedback(feedback);
        if(!status){
            //feedback submission failed
            return "redirect:/feedbackForm";
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
