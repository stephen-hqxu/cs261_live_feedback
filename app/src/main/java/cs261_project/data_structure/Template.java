package cs261_project.data_structure;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.Nullable;
/**
 * Templates are custom questions from event host.
 * @author Group 12 - Stephen Xu, JunYan Huo, Ellen Tatum, JiaQi Lv, Alexander Odewale
 */
public class Template {
    //Variables
    private int ID;
    private int BelongsTo;
    private String Questions;//TODO Format JSON into a question object
    
    private static final RowMapper<Template> Mapper = new RowMapper<Template>(){
        @Override
        public Template mapRow(ResultSet rs, int rowNum) throws SQLException{

            Template template = new Template();
            
            template.ID = rs.getInt("TID");
            template.BelongsTo = rs.getInt("EventID");
            template.Questions = rs.getString("Question");

            return template;
        }
    };

    public Template(){

    }

    public void setTemplateID(int id){
        this.ID = id;
    }

    public void setEventID(int event_id){
        this.BelongsTo = event_id;
    }

    public void setQuestions(String questions){
        this.Questions = questions;
    }

    //-------------------------------------------//

    public static final RowMapper<Template> getTemplateRowMapper(){
        return Template.Mapper;
    }

    public int getTemplateID(){
        return this.ID;
    }

    public int getEventID(){
        return this.BelongsTo;
    }

    public String getQuestions(){
        return this.Questions;
    }

}
