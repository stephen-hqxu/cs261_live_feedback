package cs261_project.data_structure;

import org.springframework.lang.Nullable;

/**
 * Holding feedback data from an attendee.
 * @author Group 12 - Stephen Xu, JuanYan Huo, Ellen Tatum, JiaQi Lv, Alexander Odewale
 */
public class Feedback {
    //Variables
    private int ID;
    private int BelongsTo;
    private @Nullable String Name;
    private byte Mood;
    private @Nullable String TemplateAnswer;//TODO I will structure JSON into an object later
    private @Nullable String AdditionalInfo;
    
    public Feedback(){

    }

    public void setFeedbackID(int id){
        this.ID = id;
    }

    public void setEventID(int event_id){
        this.BelongsTo = event_id;
    }

    public void setEventName(@Nullable String eventname){
        this.Name = eventname;
    }

    public void setMood(byte mood){
        this.Mood = mood;
    }

    public void setAnswer(@Nullable String answer){
        this.TemplateAnswer = answer;
    }

    public void setAdditionalInfomation(@Nullable String additionals){
        this.AdditionalInfo = additionals;
    }

    //------------------------------------------------------------------//

    public int getFeedbackID(){
        return this.ID;
    }

    public int getEventID(){
        return this.BelongsTo;
    }

    public @Nullable String getEventName(){
        return this.Name;
    }

    public byte getMood(){
        return this.Mood;
    }

    public @Nullable String getAnswer(){
        return this.TemplateAnswer;
    }

    public @Nullable String getAdditionalInfomation(){
        return this.AdditionalInfo;
    }

}
