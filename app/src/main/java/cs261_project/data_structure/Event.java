package cs261_project.data_structure;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import org.springframework.jdbc.core.RowMapper;

/**
 * Event class for a event data
 * @author Group 12 - Stephen Xu, JuanYan Huo, Ellen Tatum, JiaQi Lv, Alexander Odewale
 */
public class Event {
    //Variables
    private int ID;
    private int BelongsTo;
    private String Name;
    private String Password;
    private LocalDateTime Start;
    private LocalDateTime Finish;
    private int Estimated;

    private final static RowMapper<Event> Mapper = new RowMapper<Event>(){
        @Override
        public Event mapRow(ResultSet rs, int rowNum) throws SQLException{
            Event event = new Event();

            event.ID = rs.getInt("EID");
            event.BelongsTo = rs.getInt("HostID");
            event.Name = rs.getString("EventName");
            event.Password = rs.getString("EventPassword");
            event.Start = Event.StringToTempo(rs.getString("StartTime"));
            event.Finish = Event.StringToTempo(rs.getString("FinishTime"));
            event.Estimated = rs.getInt("EstimatedAttendeeNumber");

            return event;
        }
    };
    //Our special event time formatter
    private final static DateTimeFormatter EVENTDATETIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    
    public Event(){

    }

    /**
     * Format date time object to string.
     * @param datetime Date time object
     * @return String literal of date time.
     * @see LocalDateTime
     */
    public static String TempoToString(LocalDateTime datetime){
        return datetime.format(Event.EVENTDATETIME_FORMATTER);
    }

    /**
     * Parse datetime literal to string.
     * @param datetime Date time literal.
     * @return Date time object
     * @see LocalDateTime
     */
    public static LocalDateTime StringToTempo(String datetime) throws DateTimeParseException{
        try{
            return LocalDateTime.parse(datetime, Event.EVENTDATETIME_FORMATTER);
        }catch(DateTimeParseException dtpe){
            throw dtpe;
        }
    }

    public void setEventID(int id){
        this.ID = id;
    }

    public void setHostID(int host_id){
        BelongsTo = host_id;
    }

    public void setEventName(String name){
        this.Name = name;
    }

    public void setEventPassword(String password){
        this.Password = password;
    }

    public void setStartDateTime(LocalDateTime start){
        this.Start = start;
    }

    public void setFinishDateTime(LocalDateTime finish){
        this.Finish = finish;
    }

    public void setEstimatedAttendeeNumber(int estimated){
        this.Estimated = estimated;
    }

    //-----------------------------------------------//

    public static final RowMapper<Event> getEventRowMapper(){
        return Event.Mapper;
    }

    public int getEventID(){
        return this.ID;
    }

    public int getHostID(){
        return this.BelongsTo;
    }

    public String getEventName(){
        return this.Name;
    }

    public String getEventPassword(){
        return this.Password;
    }

    public LocalDateTime getStartDateTime(){
        return this.Start;
    }

    public LocalDateTime getFinishDateTime(){
        return this.Finish;
    }

    public int getEstimatedAttendeeNumber(){
        return this.Estimated;
    }

    /**
     * Get the event status
     * @return If event has yet started, return -1.
     * If event is active, return 0.
     * If event has ended, return 1
     */
    public byte getEventStatus(){
        //get the current time
        final LocalDateTime current = LocalDateTime.now();

        if(current.compareTo(this.Start) < 0){
            return -1;
        }else if(current.compareTo(this.Finish) > 0){
            return 1;
        }

        return 0;
    }

}
