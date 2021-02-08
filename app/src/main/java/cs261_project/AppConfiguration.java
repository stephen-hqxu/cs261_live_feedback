package cs261_project;

import javax.sql.DataSource;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;
import org.thymeleaf.templatemode.TemplateMode;

/**
 * Configuration class for web application
 * @author Group 12 - Stephen Xu, JuanYan Huo, Ellen Tatum, JiaQi Lv, Alexander Odewale
 */
@Configuration
@EnableWebMvc
@ComponentScan
public class AppConfiguration implements WebMvcConfigurer, ApplicationContextAware {
    //Database name goes here, the root location is /app/, same as which for build.gradle
    private static final String DATABASE_NAME = "data.db";
    //application
    private ApplicationContext context;

    public AppConfiguration(){

    }

    @Override
    public void setApplicationContext(final ApplicationContext appContext) throws BeansException {
        this.context = appContext;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        WebMvcConfigurer.super.addResourceHandlers(registry);
        //in the resources folder
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/staic/");
        registry.addResourceHandler("/image/**").addResourceLocations("classpath:/image/");
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        WebMvcConfigurer.super.addFormatters(registry);
        //TODO, more formatters will be added later
    }

    @Bean
    static public DataSource dataSource(){
        DriverManagerDataSource source = new DriverManagerDataSource();
        //tell JDBC something about the database we are using
        source.setDriverClassName("org.sqlite.JDBC");
        source.setUrl("jdbc:sqlite:" + AppConfiguration.DATABASE_NAME);
        
        return source;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(){
        LocalContainerEntityManagerFactoryBean entity = new LocalContainerEntityManagerFactoryBean();
        //since Spring doesn't support SQLite, we need to tell the API how to use it...
        entity.setDataSource(AppConfiguration.dataSource());
        entity.setPersistenceXmlLocation("classpath:/static/persistence/persistence.xml");
        //entity.setPackagesToScan("");
        entity.setJpaVendorAdapter(new HibernateJpaVendorAdapter());

        return entity;
    }

    @Bean
    public SpringResourceTemplateResolver templateResolver(){
        SpringResourceTemplateResolver resolver = new SpringResourceTemplateResolver();

        resolver.setApplicationContext(this.context);
        resolver.setTemplateMode(TemplateMode.HTML);
        //in the resources folder
        resolver.setPrefix("classpath:/dynamic/");
        resolver.setSuffix(".html");
        resolver.setCacheable(true);

        return resolver;
    }

    @Bean
    public SpringTemplateEngine templateEngine(){
        SpringTemplateEngine engine = new SpringTemplateEngine();

        engine.setTemplateResolver(this.templateResolver());
        engine.setEnableSpringELCompiler(true);

        return engine;
    }

    @Bean
    public ThymeleafViewResolver viewResolver(){
        ThymeleafViewResolver resolver = new ThymeleafViewResolver();

        resolver.setTemplateEngine(this.templateEngine());
        resolver.setOrder(1);

        return resolver;
    }
    
}
