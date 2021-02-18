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
    public void shouldRunTest(){
        Assertions.assertEquals("1","1");
    }

    @Test
    @DisplayName("Should be able to create and authenticate user")
    public void shouldCreateAndAuthenticateUser(){
        HostUser newHost = new HostUser();
        newHost.setFirstname("test");
        newHost.setLastname("user");
        newHost.setUsername("testUser");
        newHost.setPassword("123");
        conn.RegisterHost(newHost);
        HostUser user1 = conn.AuthenticateHost("testUser","123");
        conn.deleteUser("testUser");
        Assertions.assertAll("Full name",
                ()-> Assertions.assertEquals("test",user1.getFirstname()),
                ()-> Assertions.assertEquals("user",user1.getLastname())
        );
    }

}
