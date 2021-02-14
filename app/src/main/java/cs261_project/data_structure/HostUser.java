package cs261_project.data_structure;

/**
 * User class for event host information
 * @author Group 12 - Stephen Xu, JuanYan Huo, Ellen Tatum, JiaQi Lv, Alexander Odewale
 */
public class HostUser {
    //Properties
    private int ID;
    private String Firstname;
    private String Lastname;
    private String Username;
    private String Password;

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
