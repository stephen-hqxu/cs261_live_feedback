package cs261_project;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowCallbackHandler;

import cs261_project.config.DatabaseConfiguration;

/**
 * Handle data communication between the database
 * @author Group 12 - Stephen Xu, JuanYan Huo, Ellen Tatum, JiaQi Lv, Alexander Odewale
 */
public class DatabaseConnection {
    //Database instance
    private JdbcTemplate source;

    public DatabaseConnection(){
        //setup database connection
        this.source = new JdbcTemplate(DatabaseConfiguration.dataSource());
    }

}
