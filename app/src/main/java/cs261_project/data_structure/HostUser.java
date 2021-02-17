package cs261_project.data_structure;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

/**
 * User class for event host information
 * 
 * @author Group 12 - Stephen Xu, JuanYan Huo, Ellen Tatum, JiaQi Lv, Alexander
 *         Odewale
 */
public class HostUser {
    //Properties
    private int ID;
    private String Firstname;
    private String Lastname;
    private String Username;
    private String Password;

    private static final RowMapper<HostUser> Mapper = new RowMapper<HostUser>(){
        @Override
        public HostUser mapRow(ResultSet rs, int rowNum) throws SQLException{
            HostUser host = new HostUser();
            //Do not call rs.next(), it's automatically managed
            host.ID = rs.getInt("HID");
            host.Firstname = rs.getString("FirstName");
            host.Lastname = rs.getString("LastName");
            host.Username = rs.getString("Username");
            host.Password = rs.getString("Password");

            return host;
        }
    };

    public HostUser(){
        
    }

    public void setFirstname(String firstname){
        this.Firstname = firstname;
    }

    public void setLastname(String lastname){
        this.Lastname = lastname;
    }

    public void setUsername(String username){
        this.Username = username;
    }

    public void setPassword(String password){
        this.Password = password;
    }

    public void setUserID(int id){
        this.ID = id;
    }

    //-----------------------------------------//

    public static final RowMapper<HostUser> getHostUserRowMapper(){
        return HostUser.Mapper;
    }

    public String getFirstname(){
        return this.Firstname;
    }

    public String getLastname(){
        return this.Lastname;
    }

    public String getUsername(){
        return this.Username;
    }

    public String getPassword(){
        //Password is stored as plaintext in our build
        return this.Password;
    }

    public int getUserID(){
        return this.ID;
    }

}
