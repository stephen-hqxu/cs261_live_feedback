package cs261_project;

import cs261_project.config.DatabaseConfiguration;
import cs261_project.data_structure.Event;
import cs261_project.data_structure.Feedback;
import cs261_project.data_structure.HostUser;
import cs261_project.data_structure.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Handle data communication between the database
 * @author Group 12 - Stephen Xu, JunYan Huo, Ellen Tatum, JiaQi Lv, Alexander Odewale
 */
public class DatabaseConnection implements IDatabaseConnection {
    //Database instance
    @Autowired
    private JdbcTemplate source;

    public DatabaseConnection(){
        //setup database connection
        this.source = new JdbcTemplate(DatabaseConfiguration.dataSource());
    }

    // public String greet(){
    //     //PreparedStatementCreator create sql and arguments at the same time, PreparedStatementSetter only sets arguments
    //     final String res = this.source.query(new PreparedStatementCreator(){
    //         @Override
    //         public PreparedStatement createPreparedStatement(Connection con) throws SQLException{
    //             PreparedStatement stmt = con.prepareStatement("SELECT Message FROM Greet WHERE MID=?");
    //             stmt.setString(1, "0");
    //             return stmt;
    //         }
    //         //RowCallbackHandler deals with individual row data, whereas ResultSetExtractor deals with entire result set
    //     }, new ResultSetExtractor<String>(){
    //         @Override
    //         public String extractData(ResultSet rs) throws SQLException, DataAccessException{
    //             rs.next();
    //             final String val = rs.getString(1);
    //             rs.close();
    //             return val;
    //         }
    //     });
    //     return res;
    // }
    
    @Override
    public HostUser AuthenticateHost(String username, String password){
        final String sql = "SELECT * FROM Users WHERE UserName = ? AND Password = ?";
        
        //variadic template function
        try{
            return this.source.queryForObject(sql, HostUser.getHostUserRowMapper(), username, password);
        }catch(EmptyResultDataAccessException erdae){
            //username or password is incorrect
            return null;
        }
    }


    //This method is for testing purposes
    public void deleteUser(String username){
        String sql = "DELETE FROM Users WHERE UserName = ?";
        this.source.update(sql, username);
        return;
    }

    @Override
    public HostUser LookupHost(int hostid){
        final String sql = "SELECT * FROM Users WHERE HID = ?";

        try{
            return this.source.queryForObject(sql, HostUser.getHostUserRowMapper(), hostid);
        }catch(EmptyResultDataAccessException erdae){
            //host id not found
            return null;
        }
    }

    @Override
    public boolean RegisterHost(HostUser host){
        final String sql = "INSERT INTO Users (UserName, Password, FirstName, LastName) VALUES (?, ?, ?, ?)";
        
        try{
            //if row has been updated, user has been registered
            return this.source.update(
                sql,
                host.getUsername(), host.getPassword(), host.getFirstname(), host.getLastname()
            ) >= 1;
        }catch(DataAccessException dae){
            //username duplicating violation will be catched here
            return false;
        }
        
    }

    @Override
    public Event LookupEvent(String eventcode, String eventPassword){
        final String sql = "SELECT * FROM Events WHERE EID = ? AND EventPassword = ?";
        
        try{
            return this.source.queryForObject(sql, Event.getEventRowMapper(), eventcode, eventPassword);
        }catch(EmptyResultDataAccessException erdae){
            //event code or password are incorrect
            return null;
        }
    }

    @Override
    public Event LookupEvent(int eventid){
        final String sql = "SELECT * FROM Events WHERE EID = ?";

        try{
            return this.source.queryForObject(sql, Event.getEventRowMapper(), eventid);
        }catch(EmptyResultDataAccessException erdae){
            //event id not found
            return null;
        }
    }

    @Override
    public List<Event> fetchEvents(int hostID){
        final String sql = "SELECT * FROM Events WHERE HostID = ? ORDER BY EID ASC";

        try{
            return this.source.query(sql, Event.getEventRowMapper(), hostID);
        }catch(EmptyResultDataAccessException erdae){
            //host id is incorrect
            return null;
        }
    }

    @Override
    public int newEvent(Event event){
        final String sql = "INSERT INTO Events (HostID, EventName, EventPassword, StartTime, FinishTime, EstimatedAttendeeNumber) VALUES (?, ?, ?, ?, ?, ?)";
        final String id_query = "SELECT SEQ AS EID FROM Sqlite_Sequence WHERE Name = 'Events' LIMIT 1";

        try{
            //add a new event
            this.source.update(
                sql,
                event.getHostID(), event.getEventName(), event.getEventPassword(), Event.TempoToString(event.getStartDateTime()), Event.TempoToString(event.getFinishDateTime()), event.getEstimatedAttendeeNumber()
            );
            //query the inserted event id
            //I couldn't find any better way of doing this. As long as the queries are not executing in parallel, it should be fine.
            final int eventid = this.source.query(id_query, new ResultSetExtractor<Integer>(){
                @Override
                public Integer extractData(ResultSet rs) throws SQLException, DataAccessException{
                    //there is only one row of data, containing the event id of the last inserted event
                    rs.next();
                    final int val = rs.getInt("EID");
                    rs.close();

                    return val;
                }
            });

            return eventid;
        }catch(DataAccessException dae){  
            return -1;
        }
    }

    @Override
    public ArrayList<Feedback> fetchFeedbacks(int eventID){
        final String sql = "SELECT * FROM Feedback WHERE EventID = ?";
        // ArrayList<Feedback> feedback = new ArrayList<Feedback>();

        try{
            return (ArrayList<Feedback>)this.source.query(sql, Feedback.getFeedbackRowMapper(), eventID);
        }
        catch(EmptyResultDataAccessException erdae){
            //event id is not valid
            return null;
        }
    }

    @Override
    public boolean submitFeedback(Feedback feedback){
        final String sql = "INSERT INTO Feedback (EventID, AttendeeName, Feedback, Mood, Answer, Additionals) VALUES (?, ?, ?, ?, ?, ?)";
        
        try{
            return this.source.update(
                sql,
                feedback.getEventID(), feedback.getAttendeeName(), feedback.getFeedback(), feedback.getMood(), feedback.getAnswer(), feedback.getAdditionalInfomation()
            ) >= 1;
        }catch(DataAccessException dae){
            return false;
        }
    }
    
    @Override
    public Template fetchTemplate(int eventID){

        final String sql = "SELECT * FROM Template WHERE EventID = ?";

        try{
            return this.source.queryForObject(sql, Template.getTemplateRowMapper(), eventID);
        }catch(EmptyResultDataAccessException erdae){
            //event id not found
            return null;
        }
    }

    @Override
    public boolean createTemplate(Template template){
        final String sql = "INSERT INTO Template (EventID, Question) VALUES (?, ?)";
        
        try{
            return this.source.update(
                sql,
                template.getEventID(), template.getQuestions()
            ) >= 1;
        }catch(DataAccessException dae){
            return false;
        }
    }

}
