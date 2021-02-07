package cs261_project;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Process group joining, template fetching and feedback submission for clients
 * @author Group 12 - Stephen Xu, JuanYan Huo, Ellen Tatum, JiaQi Lv, Alexander Odewale
 */
@Controller
public final class RequestProcessor {

    public RequestProcessor(){

    }
    
    @GetMapping("/")
    public final String serveIndex(Model model){
        model.addAttribute("test", "Say hello to our template function");
        return "index";
    }

}
