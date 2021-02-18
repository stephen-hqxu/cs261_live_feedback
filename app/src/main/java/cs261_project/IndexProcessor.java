package cs261_project;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import cs261_project.data_structure.HostUser;

/**
 * Handle general web page requests such as login and registrations
 * @author Group 12 - Stephen Xu, JuanYan Huo, Ellen Tatum, JiaQi Lv, Alexander Odewale
 */
@Controller
@RequestMapping("/")
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
    public final String handleLogin(@RequestParam() Map<String, String> args, HttpServletRequest request) {
        final DatabaseConnection db = App.getInstance().getDbConnection();
        final String username = args.get("username").toString();
        final String password = args.get("password").toString();

        final HostUser user = db.AuthenticateHost(username, password);

        //if username or password are incorrect
        if(user == null){
            //TODO pop-up some message box to inform user
            return "redirect:/loginPage";
        }
        //set user id as session variable
        request.setAttribute("HostID", user.getUserID());

        //The general strategy is, fetch user data from database using the host id
        //we can also pass some arguments to hostHomePage for initial display, for example firstname lastname etc.

        return "redirect:/host/hostHomePage";
    }

    @GetMapping("/registerPage")
    public final String serveRegister(){
        return "register";
    }

    @PostMapping("/register")
    public final String handleRegister(HostUser user){
        final DatabaseConnection db = App.getInstance().getDbConnection();

        final boolean status = db.RegisterHost(user);
        //TODO: tell user to change their username (duplicate found) if status is false

        return "redirect:/loginPage";
    }

    @GetMapping("/joinEventPage")
    public final String serveJoinEvent(){
        return "joinEvent";
    }


    @PostMapping("/joinEvent")
    public final String handleJoinEvent(@RequestParam() Map<String, String> args) {
        final String eventCode = args.get("eventCode").toString();
        final String eventPassword = args.get("eventPassword").toString();

        //TODO fetch event info, template will be rendered in the form.
        
        return "redirect:/attendee/feedbackForm";
    }

}
