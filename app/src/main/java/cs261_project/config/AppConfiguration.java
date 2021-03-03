package cs261_project.config;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.format.Formatter;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.handler.SimpleUrlHandlerMapping;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;
import org.thymeleaf.templatemode.TemplateMode;

import cs261_project.data_structure.Event;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

/**
 * Configuration class for web application
 * @author Group 12 - Stephen Xu, JuanYan Huo, Ellen Tatum, JiaQi Lv, Alexander Odewale
 */
@Configuration
@ComponentScan
public class AppConfiguration implements WebMvcConfigurer, ApplicationContextAware {
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
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
        registry.addResourceHandler("/image/**").addResourceLocations("classpath:/image/");
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        WebMvcConfigurer.super.addFormatters(registry);

        //format the datetime using the style given in the Event class
        registry.addFormatter(new Formatter<LocalDateTime>(){
            @Override
            public LocalDateTime parse(String text, Locale locale) {
                try{
                    return Event.StringToTempo(text);
                }catch(DateTimeParseException dtpe){
                    throw dtpe;
                }
            }

            @Override
            public String print(LocalDateTime datetime, Locale locale) {
                try{
                    return Event.TempoToString(datetime);
                }catch(DateTimeParseException dtpe){
                    throw dtpe;
                }
            }
        });
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
        resolver.setContentType("text/html");
        resolver.setCharacterEncoding("UTF-8");
        resolver.setViewNames(new String[]{"*.html"});

        return resolver;
    }

    @Bean
    public SimpleUrlHandlerMapping customFaviconHandlerMapping() {
        SimpleUrlHandlerMapping mapping = new SimpleUrlHandlerMapping();
        mapping.setOrder(Integer.MIN_VALUE);
        mapping.setUrlMap(Collections.singletonMap(
                "/favicon.ico", faviconRequestHandler()));
        return mapping;
    }

    @Bean
    protected ResourceHttpRequestHandler faviconRequestHandler() {
        ResourceHttpRequestHandler requestHandler
                = new ResourceHttpRequestHandler();
        ClassPathResource classPathResource
                = new ClassPathResource("../resources/static");
        List<Resource> locations = Arrays.asList(classPathResource);
        requestHandler.setLocations(locations);
        return requestHandler;
    }
    
}
