package cs261_project;

import java.util.ArrayList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowCallbackHandler;

import cs261_project.config.DatabaseConfiguration;
import jdk.internal.event.Event;

/**
 * Handle data communication between the database
 * @author Group 12 - Stephen Xu, JunYan Huo, Ellen Tatum, JiaQi Lv, Alexander Odewale
 */
public class DatabaseConnection implements IDatabaseConnection {
    //Database instance
    private JdbcTemplate source;

    //Don't worry about intuitive error handling such as empty username etc., JiaQi has handled all errors in the webpage.

    public DatabaseConnection(){
        //setup database connection
        this.source = new JdbcTemplate(DatabaseConfiguration.dataSource());
    }

    public String greet(){
        //PreparedStatementCreator create sql and arguments at the same time, PreparedStatementSetter only sets arguments
        final String res = this.source.query(new PreparedStatementCreator(){
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException{
                PreparedStatement stmt = con.prepareStatement("SELECT Message FROM Greet WHERE MID=?");
                stmt.setString(1, "0");
                return stmt;
            }
            //RowCallbackHandler deals with individual row data, whereas ResultSetExtractor deals with entire result set
        }, new ResultSetExtractor<String>(){
            @Override
            public String extractData(ResultSet rs) throws SQLException, DataAccessException{
                rs.next();
                final String val = rs.getString(1);
                rs.close();
                return val;
            }
        });
        return res;
    }

    public HostUser AuthenticateHost(String username, String password){
        String sql = "SELECT * FROM Users WHERE UserName = ? and Password = ?";

        return source.queryForObject(sql, new Object[]{username}, new Object[]{password}, (rs, rowNum) ->
                new HostUser(
                        rs.getInt("HID"),
                        rs.getString("FirstName"),
                        rs.getString("LastName"),
                        rs.getString("UserName"),
                        rs.getString("Password")
                ));
    }

    /**
     * Register a new account for an event host. This will put new data to the database.
     * @see HostUser
     * @param host The host user data
     * @return True if new host account has been pushed to database.
     * If duplicated username has found, do not register and return false.
     */
    public boolean RegisterHost(HostUser host){
        source.update(
                "INSERT INTO Users (UserName, Password, FirstName, LastName) VALUES (?, ?, ?, ?)",
                var1, var2, var3, var4
        );
        String sql = "SELECT COUNT(UserName) AS UN FROM Users WHERE UserName = ?";

        if (source.queryForObject(sql, new Object[] { host.user }, Long.class)==0)
        {
            source.update(
                    "INSERT INTO Users (UserName, Password, FirstName, LastName) VALUES (?, ?, ?, ?)",
                    var1, var2, var3, var4
            );
            return true;
        }
        else
            return false;

        return true;
    }

    /**
     * Lookup an event. This will fetch data from the database. This will be mainly used by attendee to join the event.
     * Similar to host login, function should check for event code and event password, and read data if and only if both are correct.
     * Fetch all information of the event from the database and format the into Event object.
     * This is mainly used for attendees to join an event.
     * @see Event
     * @param eventcode The event code, also known as event ID (EID in the database schema).
     * @param eventPassword The event password.
     * @return The event object if event code and event password are both matched.
     * It needs to contain event code (or event ID), host ID that the event belongs to, event name, event password, event start and finish time
     * Note that to convert string literal to LocalDateTime object in the event class, use Event.StringToTempo() function.
     * If event code or event password are incorrect, return null.
     */
    public Event LookupEvent(String eventcode, String eventPassword){
        
    }

    /**
     * Fetch all events with a given host ID, for whom the events belong to. This will be used by event host to see all created events.
     * @see Event
     * @param hostID The host ID to lookup.
     * @return An arraylist of event objects, each event object needs to be filled with data from the database similar to LookupEvent() function.
     * If host ID cannot be found, or there is no event associated with the host, return null.
     */
    public Event[] fetchEvents(int hostID){
        String sql = "SELECT * FROM Events WHERE HostID = hostID";
        
        ArrayList<Events> events = new ArrayList<Events>();

        


        return events;
    }

    /**
     * Create a new event. This will write new data to the database.
     * Note that to convert LocalDateTime object in the event class to string literal for database, use Event.TempoToString() function.
     * @see Event
     * @param event The event data
     * @return True if event has been created in the database.
     * Currently there is no other considerations for the case when an event shouldn't be created (feel free to add). So function always return true.
     */
    public boolean newEvent(Event event){

    }

    /**
     * Fetch all feedbacks with a given event ID for the event host, for which the feedbacks belong to.
     * @see Feedback
     * @param eventID The event ID to lookup.
     * @return An arraylist of feedback objects, each one needs to be formatted into Feedback object.
     * Feedback object contains feedback ID (FID), event ID that the feedback belongs to, attendee name which is optional and nullable, feedback contents,
     * mood, optional template answers and optional additional information.
     * If no feedback can be found with the event ID, or there is currently no feedback for this event, return null.
     */
    public Feedback[] fetchFeedbacks(int eventID){

    }

    /**
     * Submit feedback from an attendee to the database.
     * @see Feedback
     * @param feedback The feedback object with the information to submit
     * @return True if feedback has been submitted.
     * Currently there is no other considerations for the case when feedback shouldn't be submitted (feel free to add). So function always return true.
     */
    public boolean submitFeedback(Feedback feedback){

    }

    /**
     * Fetch template for a given event ID. Each event can only have one template.
     * @see Template
     * @param eventID The event ID to lookup.
     * @return The template for such event. All data will be formatted and store into the template object.
     * Template object will contain template ID (TID in database), event id and template questions.
     * If no template can be found with the given event ID, return null.
     */
    public Template fetchTemplate(int eventID){

    }

    /**
     * Create a new template for an event.
     * @see Template
     * @param template The template object with information to the new template.
     * @return True if the template has been created.
     * If event ID is duplicated, return false and template shouldn't be inserted into the table.
     * Also event ID in the database schema is a candidate key.
     */
    public boolean createTemplate(Template template){

    }


}
