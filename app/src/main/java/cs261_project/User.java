package cs261_project;

import org.springframework.web.bind.annotation.RequestParam;

/**
 * User class for user information
 * 
 * @author Group 12 - Stephen Xu, JuanYan Huo, Ellen Tatum, JiaQi Lv, Alexander
 *         Odewale
 */
public class User {
    //Properties
    private String Username;
    private String Password;
    private UserType Type;

    /**
     * Indicate the type of the user
     * @author Group 12 - Stephen Xu, JuanYan Huo, Ellen Tatum, JiaQi Lv, Alexander Odewale
     */
    public static enum UserType{
        //User who joins event and provide feedback
        ATTENDEE("ATTENDEE"),
        //User who set up the event and listen to feedback
        HOST("HOST");

        private final String Value;

        private UserType(String literal){
            this.Value = literal;
        }

        /**
         * Convert enum type to string
         * @return String literal of the type
         */
        @Override
        public String toString(){
            return this.Value;
        }
    }

    public User(){
        
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
     * Set the user type associated with this object
     * @param username User tpe
     */
    public void setUserType(UserType type){
        this.Type = type;
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

    /**
     * Get the user type associated with this object
     * @return User type
     */
    public UserType getUserType(){
        return this.Type;
    }

}
