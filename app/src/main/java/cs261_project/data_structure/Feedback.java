package cs261_project.data_structure;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

import org.springframework.lang.Nullable;

/**
 * Holding feedback data from an attendee.
 * @author Group 12 - Stephen Xu, JunYan Huo, Ellen Tatum, JiaQi Lv, Alexander Odewale
 */
public class Feedback {
    //Variables
    private int ID;
    private int BelongsTo;
    private @Nullable String Name;
    private String Feedback;
    private byte Mood;
    private @Nullable String TemplateAnswer;//TODO I will structure JSON into an object later
    private @Nullable String AdditionalInfo;
   
    private final static RowMapper<Feedback> Mapper = new RowMapper<Feedback>(){
        @Override
        public Feedback mapRow(ResultSet rs, int rowNum) throws SQLException{

            Feedback feedback = new Feedback();
        
            feedback.ID = rs.getInt("FID");
            feedback.BelongsTo = rs.getInt("EventID");
            feedback.Name = rs.getString("AttendeeName");
            feedback.Feedback = rs.getString("Feedback");
            feedback.Mood = rs.getByte("Mood");
            feedback.TemplateAnswer = rs.getString("Answer");
            feedback.AdditionalInfo = rs.getString("Additionals");

            return feedback;
        }
    };

    public Feedback(){

    }

    public void setFeedbackID(int id){
        this.ID = id;
    }

    public void setEventID(int event_id){
        this.BelongsTo = event_id;
    }

    public void setAttendeeName(@Nullable String attendeename){
        this.Name = attendeename;
    }

    public void setFeedback(String feedback){
        this.Feedback = feedback;
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

    public static final RowMapper<Feedback> getFeedbackRowMapper(){
        return Feedback.Mapper;
    }

    public int getFeedbackID(){
        return this.ID;
    }

    public int getEventID(){
        return this.BelongsTo;
    }

    public @Nullable String getAttendeeName(){
        return this.Name;
    }

    public String getFeedback(){
        return this.Feedback;
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
