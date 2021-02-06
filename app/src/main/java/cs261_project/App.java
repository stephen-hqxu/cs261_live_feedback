/*
 * CS261 SE project
 */
package cs261_project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * The starter program for our live feedback system
 * @author Group 12 - Stephen Xu, JuanYan Huo, Ellen Tatum, JiaQi Lv, Alexander Odewale
 */
@RestController
@SpringBootApplication
@EnableWebMvc
public class App {
    //The program
    private static App program;
    //app context
    private ConfigurableApplicationContext context;

    //controllers
    private RequestProcessor request;

    public App(){
        //init controllers
        this.request = new RequestProcessor();
    }

    /**
     * Start the live feedback web application
     * @param args Launch arguments
     */
    private final void run(String[] args){
        //launch the server
        this.context = SpringApplication.run(App.program.getClass(), args);
    }

    /**
     * Get the application instance
     * @return The App
     */
    public final static App getInstance(){
        return App.program;
    }
    
    /**
     * Get the Spring application context
     * @return The context
     */
    public final ConfigurableApplicationContext getContext(){
        return this.context;
    }

    public static void main(String[] args) {
        //Get the app running
        App.program = new App();
        App.program.run(args);
    }

    @GetMapping("/")
    public final String serveIndex(){
        return this.request.serveIndex();
    }

}
