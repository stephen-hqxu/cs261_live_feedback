package cs261_project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * The starter program for our live feedback system
 * @author Group 12 - Stephen Xu, JuanYan Huo, Ellen Tatum, JiaQi Lv, Alexander Odewale
 */
@SpringBootApplication
public class App {
    //The program
    private static App program;
    //app context
    private ConfigurableApplicationContext context;
    //database connection
    private DatabaseConnection db;

    public App(){

    }

    /**
     * Start the live feedback web application
     * @param args Launch arguments
     */
    private final void run(String[] args){
        //setup database
        this.db = new DatabaseConnection();

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

    /**
     * Get application database connection
     * @return The database connection
     */
    public final DatabaseConnection getDbConnection(){
        return this.db;
    }

    public static void main(String[] args) {
        //Get the app running
        App.program = new App();
        App.program.run(args);
    }

}
