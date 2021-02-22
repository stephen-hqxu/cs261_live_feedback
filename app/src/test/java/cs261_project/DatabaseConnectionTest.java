package cs261_project;

import cs261_project.data_structure.HostUser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


//Unit tests for all functions from DatabaseConnection.java
public class DatabaseConnectionTest {

    private DatabaseConnection conn = new DatabaseConnection();

    @Test
    @DisplayName("Test should run")
    void shouldRunTest(){
        Assertions.assertEquals("1","1");
    }

    @Test
    @DisplayName("Should be able to create and authenticate user")
    void shouldCreateAndAuthenticateUser(){
        //Create a new HostUser object
        HostUser newHost = new HostUser();
        newHost.setFirstname("test");
        newHost.setLastname("user");
        newHost.setUsername("testUser");
        newHost.setPassword("123");

        //Add the new user to the database
        conn.RegisterHost(newHost);

        //Authenticate user with correct username and password
        HostUser user1 = conn.AuthenticateHost("testUser","123");
        //Authenticate user with correct username but wrong password
        HostUser user2 = conn.AuthenticateHost("testUser","123456");
        //Clean up
        conn.deleteUser("testUser");
        Assertions.assertAll("Host user",
                ()-> Assertions.assertEquals("test",user1.getFirstname()),
                ()-> Assertions.assertEquals("user",user1.getLastname()),
                ()-> Assertions.assertNull(user2)
        );
    }

}
