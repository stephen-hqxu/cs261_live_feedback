package cs261_project.config;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import cs261_project.User.UserType;

/**
 * Convert user type string
 * @author Group 12 - Stephen Xu, JuanYan Huo, Ellen Tatum, JiaQi Lv, Alexander Odewale
 */
@Component
public class UserTypeConverter implements Converter<String, UserType> {

    public UserTypeConverter(){
        super();
    }
    
    @Override
    public UserType convert(String value){
        return UserType.valueOf(value.toUpperCase());
    }

}
