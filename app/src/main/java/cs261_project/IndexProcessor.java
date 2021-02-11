package cs261_project;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ServerWebInputException;

import cs261_project.User.UserType;

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

    @GetMapping("/login")
    public final String serveLogin(Model model){
        model.addAttribute("port", this.PORT);
        return "login";
    }

    @PostMapping("/login")
    public final String handleLogin(@RequestParam("selection") String selection, User user, @RequestParam("usertype") UserType type) throws ServerWebInputException {
        if(!selection.equals("login")){
            //error handling, normally it won't trigger
            throw new ServerWebInputException("Only login can be selected");
        }
        user.setUserType(type);

        //TODO processing login

        return "redirect:/hostHomePage";
    }

    @GetMapping("/register")
    public final String serveRegister(){
        return "register";
    }

    @PostMapping("/register")
    public final String handleRegister(User user, @RequestParam("usertype") UserType type){
        user.setUserType(type);

        //TODO processing register

        return "redirect:/login";
    }

    @GetMapping("/joinEvent")
    public final String serveJoinEvent(){
        return "joinEvent";
    }

    @GetMapping("/hostHomePage")
    public final String servehostHomePage(){
        return "hostHomePage";
    }

}
