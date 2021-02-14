package cs261_project;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

import cs261_project.data_structure.*;

/**
 * Handle general web page requests such as login and registrations
 * @author Group 12 - Stephen Xu, JuanYan Huo, Ellen Tatum, JiaQi Lv, Alexander Odewale
 */
@Controller
public class IndexProcessor {
    //Server port number
    @Value("${server.port}")
    private String PORT;
    
    public IndexProcessor(){

    }

    @GetMapping("/")
    public final String serveIndex(){
        return "index";
    }

    @GetMapping("/terms")
    public final String serveTerms(){
        return "terms";
    }

    @GetMapping("/loginPage")
    public final String serveLogin(){
        return "login";
    }

    @PostMapping("/login")
    public final String handleLogin(@RequestParam() Map<String, String> args) {
        final String username = args.get("username").toString();
        final String password = args.get("password").toString();

        //TODO processing login

        return "redirect:/hostHomePage";
    }

    @GetMapping("/registerPage")
    public final String serveRegister(){
        return "register";
    }

    @PostMapping("/register")
    public final String handleRegister(HostUser user){

        //TODO processing register

        return "redirect:/loginPage";
    }

    @GetMapping("/joinEventPage")
    public final String serveJoinEvent(){
        return "joinEvent";
    }


    @PostMapping("/joinEvent")
    public final String handleJoinEvent() {

        //TODO render feedback template

        return "redirect:/feedbackForm";
    }

    @GetMapping("/feedbackForm")
    public final String serveFeedbackForm(){
        return "feedbackForm";
    }

    @PostMapping("/submitFeedback")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<String> processFeedback(){

        //TODO process feedback, the attendee needs to stay on the feedbackForm webpage

        return new ResponseEntity<String>(HttpStatus.NO_CONTENT);
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
    public final String handleNewEvent(){

        //TODO create new event

        return "redirect:/hostHomePage";
    }

    @GetMapping("/viewFeedback")
    public final String serveViewFeedback(){

        //TODO view feedback

        return "viewFeedbackPage";
    }

}
