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

/**
 * Handle data communication between the database
 * @author Group 12 - Stephen Xu, JuanYan Huo, Ellen Tatum, JiaQi Lv, Alexander Odewale
 */
public class DatabaseConnection {
    //Database instance
    private JdbcTemplate source;

    public DatabaseConnection(){
        //setup database connection
        this.source = new JdbcTemplate(AppConfiguration.dataSource());
    }

    /**
     * Just a demo program to show database works
     * @return Greeting
     */
    public String greet(){
        //PreparedStatementCreator create sql and arguments at the same time, PreparedStatementSetter only sets arguments
        final String res = this.source.query(new PreparedStatementCreator(){
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException{
                PreparedStatement stmt = con.prepareStatement("SELECT Message FROM Greet WHERE MID=?");
                stmt.setString(1, "0");
                return stmt;
            }
        //RowCallbackHandler deals with individual row data, whereas ResultSetExtractor deals with entire result set
        }, new ResultSetExtractor<String>(){
            @Override
            public String extractData(ResultSet rs) throws SQLException, DataAccessException{
                rs.next();
                final String val = rs.getString(1);
                rs.close();
                return val;
            }
        });
        return res;
    }
    
}
