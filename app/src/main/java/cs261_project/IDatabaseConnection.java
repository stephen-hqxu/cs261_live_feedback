package cs261_project;

import cs261_project.data_structure.*;

/**
 * Handle data communication between the database. This is the header.
 * @author Group 12 - Stephen Xu, JuanYan Huo, Ellen Tatum, JiaQi Lv, Alexander Odewale
 */
public interface IDatabaseConnection {

    /**
     * Authentication to event host. This will fetch data from database. 
     * Check for the username and password and find the user ID if both username and password matches.
     * Fetch All information of the event host from the database and format them into HostUser object.
     * @see HostUser
     * @param username The username for the event host
     * @param password The password for the event host
     * @return The user object if username and password are both correct. It needs to contain host ID (HID), first name, last name, username and password.
     * If incorrect username or password has been provided, return null.
     * Also username in the database schema is a candidate key.
     */
    public abstract HostUser AuthenticateHost(String username, String password);

    /**
     * Register a new account for an event host. This will put new data to the database.
     * @see HostUser
     * @param host The host user data
     * @return True if new host account has been pushed to database.
     * If duplicated username has found, do not register and return false.
     */
    public abstract boolean RegisterHost(HostUser host);

    /**
     * Lookup an event. This will fetch data from the database. This will be mainly used by attendee to join the event.
     * Similar to host login, function should check for event code and event password, and read data if and only if both are correct.
     * Fetch all information of the event from the database and format the into Event object.
     * This is mainly used for attendees to join an event.
     * @see Event
     * @param eventcode The event code, also known as event ID (EID in the database schema).
     * @param eventPassword The event password.
     * @return The event object if event code and event password are both matched. 
     * It needs to contain event code (or event ID), host ID that the event belongs to, event name, event password, event start and finish time
     * Note that to convert string literal to LocalDateTime object in the event class, use Event.StringToTempo() function.
     * If event code or event password are incorrect, return null.
     */
    public abstract Event LookupEvent(String eventcode, String eventPassword);

    /**
     * Fetch all events with a given host ID, for whom the events belong to. This will be used by event host to see all created events.
     * @see Event
     * @param hostID The host ID to lookup.
     * @return An array of event objects, each event object needs to be filled with data from the database similar to LookupEvent() function.
     * If host ID cannot be found, or there is no event associated with the host, return null.
     */
    public abstract Event[] fetchEvents(int hostID);

    /**
     * Create a new event. This will write new data to the database.
     * Note that to convert LocalDateTime object in the event class to string literal for database, use Event.TempoToString() function.
     * @see Event
     * @param event The event data
     * @return True if event has been created in the database.
     * Currently there is no other considerations for the case when an event shouldn't be created (feel free to add). So function always return true.
     */
    public abstract boolean newEvent(Event event);

    /**
     * Fetch all feedbacks with a given event ID for the event host, for which the feedbacks belong to.
     * @see Feedback
     * @param eventID The event ID to lookup.
     * @return An array of feedback objects, each one needs to be formatted into Feedback object.
     * Feedback object contains feedback ID (FID), event ID that the feedback belongs to, attendee name which is optional and nullable, feedback contents,
     * mood, optional template answers and optional additional information.
     * If no feedback can be found with the event ID, or there is currently no feedback for this event, return null.
     */
    public abstract Feedback[] fetchFeedbacks(int eventID);

    /**
     * Submit feedback from an attendee to the database.
     * @see Feedback
     * @param feedback The feedback object with the information to submit
     * @return True if feedback has been submitted.
     * Currently there is no other considerations for the case when feedback shouldn't be submitted (feel free to add). So function always return true.
     */
    public abstract boolean submitFeedback(Feedback feedback);

    /**
     * Fetch template for a given event ID. Each event can only have one template.
     * @see Template
     * @param eventID The event ID to lookup.
     * @return The template for such event. All data will be formatted and store into the template object.
     * Template object will contain template ID (TID in database), event id and template questions.
     * If no template can be found with the given event ID, return null.
     */
    public abstract Template fetchTemplate(int eventID);

    /**
     * Create a new template for an event.
     * @see Template
     * @param template The template object with information to the new template.
     * @return True if the template has been created.
     * If event ID is duplicated, return false and template shouldn't be inserted into the table.
     * Also event ID in the database schema is a candidate key.
     */
    public abstract boolean createTemplate(Template template);
}
