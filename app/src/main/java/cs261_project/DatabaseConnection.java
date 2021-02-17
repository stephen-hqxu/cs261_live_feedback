package cs261_project;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import cs261_project.data_structure.*;
import cs261_project.config.DatabaseConfiguration;

/**
 * Handle data communication between the database
 * @author Group 12 - Stephen Xu, JunYan Huo, Ellen Tatum, JiaQi Lv, Alexander Odewale
 */
public class DatabaseConnection implements IDatabaseConnection {
    //Database instance
    @Autowired
    private JdbcTemplate source;

    //Don't worry about intuitive error handling such as empty username etc., JiaQi has handled all errors in the webpage.

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
            return null;
        }
    }

    @Override
    public ArrayList<Event> fetchEvents(int hostID){
        final String sql = "SELECT * FROM Events WHERE HostID = ?";
        
        ArrayList<Event> events = new ArrayList<Event>();

        return events;
    }

    @Override
    public boolean newEvent(Event event){
        return true;
    }

    @Override
    public Feedback[] fetchFeedbacks(int eventID){
        return null;
    }

    @Override
    public boolean submitFeedback(Feedback feedback){
        return true;
    }
    
    @Override
    public Template fetchTemplate(int eventID){
        return null;
    }

    @Override
    public boolean createTemplate(Template template){
        return true;
    }

}
