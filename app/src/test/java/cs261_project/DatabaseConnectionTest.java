package cs261_project;

import cs261_project.data_structure.Event;
import cs261_project.data_structure.Feedback;
import cs261_project.data_structure.HostUser;
import cs261_project.data_structure.Template;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;


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

    private Event getEvent(int hostID){
        Event newEvent = new Event();
        newEvent.setEventName("testEvent");
        LocalDateTime startTime = LocalDateTime.now();
        LocalDateTime endTime = startTime.plusHours(1);
        newEvent.setStartDateTime(startTime);
        newEvent.setFinishDateTime(endTime);
        newEvent.setEventPassword("123");
        newEvent.setEstimatedAttendeeNumber(10);
        newEvent.setHostID(hostID);
        return newEvent;
    }

    private Template getTemplate(int eventID){
        Template newTemplate = new Template();
        newTemplate.setEventID(eventID);
        newTemplate.setQuestions("This is a test");
        return newTemplate;
    }

    private Feedback getFeedback(int eventID){
        Feedback newFeedback = new Feedback();
        newFeedback.setEventID(eventID);
        newFeedback.setFeedback("This is a test");
        newFeedback.setAnswer("N/A");
        newFeedback.setAdditionalInformation("N/A");
        newFeedback.setAttendeeName("testUser");
        newFeedback.setMood((byte) 1);
        return newFeedback;
    }

    @Test
    @DisplayName("Test should run")
    void shouldRunTest(){
        Assertions.assertEquals("1","1");
    }


    @Test
    @DisplayName("The database should not create new user if the username is already taken")
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
    @DisplayName("The database should be able to create and authenticate user")
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
    @DisplayName("The database should be able to look up host by host ID")
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


    @Test
    @DisplayName("The database should be able to store events")
    void shouldStoreEvent(){
        //Create a new HostUser
        HostUser newHost = getTestHostUser("testUser");
        //Add the new user to the database
        conn.RegisterHost(newHost);
        //Retrieve the new user from the database using username and password
        HostUser user1 = conn.AuthenticateHost("testUser","123");
        //Get userID
        int userID = user1.getUserID();

        //Create a new event, link it to testUser
        Event newEvent = getEvent(userID);

        //Add new event to database
        int eventID = conn.newEvent(newEvent);

        //Verify new event
        Event event = conn.LookupEvent(eventID);

        //Clean up
        conn.deleteEvent(eventID);
        conn.deleteUser("testUser");

        Assertions.assertAll("Event",
                ()->Assertions.assertEquals("testEvent",event.getEventName()),
                ()->Assertions.assertEquals("123",event.getEventPassword())
        );

    }

    @Test
    @DisplayName("The database shoule be able to store the template of an event")
    void shouldStoreTemplate(){
        //Create a new HostUser
        HostUser newHost = getTestHostUser("testUser");
        //Add the new user to the database
        conn.RegisterHost(newHost);
        //Retrieve the new user from the database using username and password
        HostUser user1 = conn.AuthenticateHost("testUser","123");
        //Get userID
        int userID = user1.getUserID();
        //Create a new event, link it to testUser
        Event newEvent = getEvent(userID);
        //Add new event to database
        int eventID = conn.newEvent(newEvent);

        //Create a new template, link it to testEvent
        Template newTemplate = getTemplate(eventID);
        //Add template to database
        boolean result = conn.createTemplate(newTemplate);

        //Verify
        Template template = conn.fetchTemplate(eventID);
        int TID = template.getTemplateID();

        //Clean up
        conn.deleteEvent(eventID);
        conn.deleteUser("testUser");
        conn.deleteTemplate(TID);

        Assertions.assertAll("Template",
                ()->Assertions.assertTrue(result),
                ()->Assertions.assertEquals("This is a test", template.getQuestions())
        );

    }

    @Test
    @DisplayName("The database should be able to store feedback to an event")
    void shouldStoreFeedback(){
        //Create a new HostUser
        HostUser newHost = getTestHostUser("testUser");
        //Add the new user to the database
        conn.RegisterHost(newHost);
        //Retrieve the new user from the database using username and password
        HostUser user1 = conn.AuthenticateHost("testUser","123");
        //Get userID
        int userID = user1.getUserID();
        //Create a new event, link it to testUser
        Event newEvent = getEvent(userID);
        //Add new event to database
        int eventID = conn.newEvent(newEvent);

        //Create a new feedback
        Feedback newFeedback = getFeedback(eventID);
        //Add new feedback to database
        boolean result = conn.submitFeedback(newFeedback);

        //Verify
        List<Feedback> feedbackList = conn.fetchFeedbacks(eventID);
        Feedback feedback = feedbackList.get(0);

        //Clean up
        conn.deleteFeedback(feedback.getFeedbackID());
        conn.deleteEvent(eventID);
        conn.deleteUser("testUser");

        Assertions.assertAll("Feedback",
                ()->Assertions.assertTrue(result),
                ()->Assertions.assertEquals(1,feedbackList.size()),
                ()->Assertions.assertEquals("This is a test",feedback.getFeedback()),
                ()->Assertions.assertEquals("testUser",feedback.getAttendeeName())
                );

    }




}
