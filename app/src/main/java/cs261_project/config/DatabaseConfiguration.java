package cs261_project.config;

import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.sqlite.SQLiteConfig;

/**
 * Configurate the database connectors
 * @author Group 12 - Stephen Xu, JuanYan Huo, Ellen Tatum, JiaQi Lv, Alexander Odewale
 */
@Configuration
public class DatabaseConfiguration {
    //Database name goes here, the root location is /app/, same as which for build.gradle
    private static final String DATABASE_NAME = "data.db";

    public DatabaseConfiguration(){

    }

    @Bean
    static public DataSource dataSource(){
        DriverManagerDataSource source = new DriverManagerDataSource();
        //tell JDBC something about the database we are using
        source.setDriverClassName("org.sqlite.JDBC");
        source.setUrl("jdbc:sqlite:" + DatabaseConfiguration.DATABASE_NAME);

        //enable foreign key
        Properties prop = new Properties();
        prop.setProperty("foreign_keys", "true");
        source.setConnectionProperties(prop);
        
        return source;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(){
        LocalContainerEntityManagerFactoryBean entity = new LocalContainerEntityManagerFactoryBean();
        //since Spring doesn't support SQLite, we need to tell the API how to use it...
        entity.setDataSource(DatabaseConfiguration.dataSource());
        entity.setPersistenceXmlLocation("classpath:/static/persistence/persistence.xml");
        //entity.setPackagesToScan("");
        entity.setJpaVendorAdapter(new HibernateJpaVendorAdapter());

        return entity;
    }
    
}
