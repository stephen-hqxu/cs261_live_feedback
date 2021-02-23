package cs261_project;

import cs261_project.data_structure.Event;
import cs261_project.data_structure.HostUser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


//Unit tests for all functions from DatabaseConnection.java
public class DatabaseConnectionTest {

    private DatabaseConnection conn = new DatabaseConnection();

    private HostUser getTestHostUser(String username){
        HostUser newHost = new HostUser();
        newHost.setFirstname("test");
        newHost.setLastname("user");
        newHost.setUsername(username);
        newHost.setPassword("123");
        return newHost;
    }

//    private Event getEvent(int hostID){
//        Event newEvent = new Event();
//        return newEvent;
//    }

    @Test
    @DisplayName("Test should run")
    void shouldRunTest(){
        Assertions.assertEquals("1","1");
    }


    @Test
    @DisplayName("Should not allow user to register if the username is already taken")
    void shouldNotAllowDuplicateUsername(){
        //Create a HostUser (username = "testUser")
        HostUser user1 = getTestHostUser("testUser");

        //Create a HostUser with the same username ("testUser")
        HostUser user2 = getTestHostUser("testUser");

        //Create a HostUser with a different username ("testUser3");
        HostUser user3 = getTestHostUser("testUser3");

        //Register the first user
        boolean r1 = conn.RegisterHost(user1);

        //Register the second user, this should return false
        boolean r2 = conn.RegisterHost(user2);

        //Register the third user, this should return true
        boolean r3 = conn.RegisterHost(user3);

        //Clean up
        conn.deleteUser("testUser");
        conn.deleteUser("testUser3");

        Assertions.assertAll("Results",
                ()->Assertions.assertEquals(r1,true),
                ()->Assertions.assertEquals(r2,false),
                ()->Assertions.assertEquals(r3,true)
        );

    }


    @Test
    @DisplayName("Should be able to create and authenticate host user")
    void shouldCreateAndAuthenticateHostUser(){
        //Create a new HostUser
        HostUser newHost = getTestHostUser("testUser");

        //Add the new user to the database
        conn.RegisterHost(newHost);

        //Authenticate user with correct username and password
        HostUser user1 = conn.AuthenticateHost("testUser","123");
        //Authenticate user with correct username but wrong password
        HostUser user2 = conn.AuthenticateHost("testUser","123456");
        //Clean up
        conn.deleteUser("testUser");
        Assertions.assertAll("Host user",
                ()->Assertions.assertEquals("test",user1.getFirstname()),
                ()->Assertions.assertEquals("user",user1.getLastname()),
                ()->Assertions.assertNull(user2)
        );
    }


    @Test
    @DisplayName("Should be able to loop up host by host ID")
    void shouldLookUpHostUser(){
        //Create a new HostUser
        HostUser newHost = getTestHostUser("testUser");

        //Add the new user to the database
        conn.RegisterHost(newHost);

        //Retrieve the new user from the database using username and password
        HostUser user1 = conn.AuthenticateHost("testUser","123");
        int userID = user1.getUserID();

        //Retrieve the new user from the database using userID
        HostUser user2 = conn.LookupHost(userID);

        //Clean up
        conn.deleteUser("testUser");

        //Two users should be the same
        Assertions.assertEquals(user1.getUsername(),user2.getUsername());

    }




}
