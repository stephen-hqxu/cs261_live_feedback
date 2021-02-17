package cs261_project;

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
    @DisplayName("Should create a new user")
    @Disabled("Not implemented")
    public void createNewUser(){
        DatabaseConnection conn = new DatabaseConnection();
    }

}
