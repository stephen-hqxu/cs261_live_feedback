package cs261_project.data_structure;

/**
 * Templates are custom questions from event host.
 * @author Group 12 - Stephen Xu, JuanYan Huo, Ellen Tatum, JiaQi Lv, Alexander Odewale
 */
public class Template {
    //Variables
    private int ID;
    private int BelongsTo;
    private String Questions;//TODO Format JSON into a question object
    
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
