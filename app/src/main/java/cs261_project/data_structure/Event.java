package cs261_project.data_structure;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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

    //Our special event time formatter
    private final static DateTimeFormatter EVENTDATETIME_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
    
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
    public static LocalDateTime StringToTempo(String datetime){
        return LocalDateTime.parse(datetime, Event.EVENTDATETIME_FORMATTER);
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

    //-----------------------------------------------//

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

}
