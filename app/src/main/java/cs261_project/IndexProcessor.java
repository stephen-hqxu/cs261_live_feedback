package cs261_project;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.server.ServerWebInputException;

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

    @GetMapping("/login")
    public final String serveLogin(Model model){
        model.addAttribute("port", this.PORT);
        return "login";
    }

    @PostMapping("/login")
    public final String handleLogin(@RequestParam() Map<String, String> args) throws ServerWebInputException {
        if(!args.get("selection").equals("login")){
            //error handling, normally it won't trigger
            throw new ServerWebInputException("Only login can be selected");
        }
        //read the username and password
        final String username = args.get("username");
        final String password = args.get("password");

        //TODO processing login

        return "redirect:/hostHomePage";
    }

    @GetMapping("/register")
    public final String serveRegister(){
        return "register";
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
