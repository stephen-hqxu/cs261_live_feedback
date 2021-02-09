package cs261_project;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Handle general web page requests such as login and registrations
 * @author Group 12 - Stephen Xu, JuanYan Huo, Ellen Tatum, JiaQi Lv, Alexander Odewale
 */
@Controller
public class IndexProcessor {
    
    public IndexProcessor(){

    }

    /**
     * Testing method to show the web server and template engine works
     * @param model Template model
     * @return Template filename
     */
    @GetMapping("/")
    public final String serveIndex(Model model){
        //test for database
        //final String greet = App.getInstance().getDbConnection().greet();
        //assign the variable with name "test" in the template index.html with value
        //model.addAttribute("test", "Say hello to our template function and " + greet);
        return "index";
    }

    @GetMapping("login")
    public final String serveLogin(){
        return "login";
    }

    @GetMapping("joinEvent")
    public final String serveJoinEvent(){
        return "joinEvent";
    }


}
