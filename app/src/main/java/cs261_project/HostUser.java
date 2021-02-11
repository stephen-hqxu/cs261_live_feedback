package cs261_project;

/**
 * User class for event host information
 * 
 * @author Group 12 - Stephen Xu, JuanYan Huo, Ellen Tatum, JiaQi Lv, Alexander
 *         Odewale
 */
public class HostUser {
    //Properties
    private String Firstname;
    private String Lastname;
    private String Username;
    private String Password;

    public HostUser(){
        
    }

    /**
     * Set the first name associated with this object
     * @param username First name
     */
    public void setFirstname(String firstname){
        this.Firstname = firstname;
    }

    /**
     * Set the last name associated with this object
     * @param username Last name
     */
    public void setLastname(String lastname){
        this.Lastname = lastname;
    }

    /**
     * Set the username associated with this object
     * @param username Username
     */
    public void setUsername(String username){
        this.Username = username;
    }

    /**
     * Set the password associated with this object
     * @param username Password
     */
    public void setPassword(String password){
        this.Password = password;
    }

    /**
     * Get the first name associated with this object
     * @return First name
     */
    public String getFirstname(){
        return this.Firstname;
    }

    /**
     * Get the last name associated with this object
     * @return Last name
     */
    public String getLastname(){
        return this.Lastname;
    }

    /**
     * Get the username associated with this object
     * @return Username
     */
    public String getUsername(){
        return this.Username;
    }

    /**
     * Get the password associated with this object
     * @return Password
     */
    public String getPassword(){
        //Password is stored as plaintext in our build
        return this.Password;
    }

}
