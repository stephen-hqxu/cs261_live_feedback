package cs261_project;

import java.util.Map;

import javax.annotation.Nullable;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import cs261_project.data_structure.*;

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

    //flags
    /**
     * A list of returned flag to indicate error and print message to the webpage
     * @author Group 12 - Stephen Xu, JuanYan Huo, Ellen Tatum, JiaQi Lv, Alexander Odewale
     */
    private static enum ErrorFlag{
        INCORRECT_USERNAME_PASSWORD,
        DUPLICATE_USERNAME,
        INCORRECT_EVENTCODE_PASSWORD;
    }
    
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
    public final String serveLogin(@RequestParam("error") @Nullable ErrorFlag error, Model model){
        if(error == ErrorFlag.INCORRECT_USERNAME_PASSWORD){
            //inform user about the incorrect username or password
            model.addAttribute("error", "Incorrect username or password, please try again.");
        }
        return "login";
    }

    @PostMapping("/login")
    public final String handleLogin(@RequestParam() Map<String, String> args, HttpServletRequest request, HttpSession session) {
        final DatabaseConnection db = App.getInstance().getDbConnection();
        final String username = args.get("username").toString();
        final String password = args.get("password").toString();

        final HostUser user = db.AuthenticateHost(username, password);

        //if username or password are incorrect
        if(user == null){
            return "redirect:/loginPage?error=" + ErrorFlag.INCORRECT_USERNAME_PASSWORD.toString();
        }
        //create a new session
        session = request.getSession();
        //set user id as session variable
        session.setAttribute("HostID", user.getUserID());
       
        //The general strategy is, fetch user data from database using the host id
        //we can also pass some arguments to hostHomePage for initial display, for example firstname lastname etc.

        return "redirect:/host/hostHomePage";
    }

    @GetMapping("/registerPage")
    public final String serveRegister(@RequestParam("error") @Nullable ErrorFlag error, Model model){
        if(error == ErrorFlag.DUPLICATE_USERNAME){
            //inform user that the username has been used
            model.addAttribute("error", "Username already exists, Please choose another username.");
        }
        return "register";
    }

    @PostMapping("/register")
    public final String handleRegister(HostUser user){
        final DatabaseConnection db = App.getInstance().getDbConnection();

        final boolean status = db.RegisterHost(user);
        if(!status){
            //tell user about their error
            return "redirect:/registerPage?error=" + ErrorFlag.DUPLICATE_USERNAME.toString();
        }

        return "redirect:/loginPage";
    }

    @GetMapping("/joinEventPage")
    public final String serveJoinEvent(@RequestParam("error") @Nullable ErrorFlag error, Model model){
        if(error == ErrorFlag.INCORRECT_EVENTCODE_PASSWORD){
            //event code or password are incorrect, inform user
            model.addAttribute("error", "Incorrect event code or password, please try again.");
        }
        return "joinEvent";
    }


    @PostMapping("/joinEvent")
    public final String handleJoinEvent(@RequestParam() Map<String, String> args, HttpServletRequest request, HttpSession session) {
        final DatabaseConnection db = App.getInstance().getDbConnection();
        final String eventCode = args.get("eventCode").toString();
        final String eventPassword = args.get("eventPassword").toString();

        final Event event = db.LookupEvent(eventCode, eventPassword);
        if(event == null){
            return "redirect:/joinEventPage?error=" + ErrorFlag.INCORRECT_EVENTCODE_PASSWORD.toString();
        }
        //create a session
        session = request.getSession();
        //set event id as attendee session
        session.setAttribute("EventID", event.getEventID());
        
        return "redirect:/attendee/feedbackForm";
    }

}
