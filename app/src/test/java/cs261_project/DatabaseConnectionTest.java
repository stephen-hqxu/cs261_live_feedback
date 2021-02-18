package cs261_project;

import cs261_project.data_structure.HostUser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class DatabaseConnectionTest {

    @Test
    @DisplayName("Test should run")
    public void shouldRunTest(){
        Assertions.assertEquals("1","1");
    }

    @Test
    @DisplayName("Should authenticate user")
    public void shouldAuthenticateUser(){
        DatabaseConnection conn = new DatabaseConnection();
        HostUser user1 = conn.AuthenticateHost("apple","123456");
        Assertions.assertNotNull(user1);
    }

}
