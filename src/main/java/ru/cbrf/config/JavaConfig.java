package ru.cbrf.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.lookup.JndiDataSourceLookup;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import javax.sql.DataSource;

@EnableWebMvc
@Configuration
@ComponentScan("ru.cbrf")
public class JavaConfig {
    @Bean
    public DataSource dataSource() {
        return new JndiDataSourceLookup().getDataSource("java:comp/env/jdbc/db");
    }

    @Bean
    public InternalResourceViewResolver viewResolver() {
        var viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/WEB-INF/views/");
        viewResolver.setSuffix(".jsp");
        return viewResolver;
    }
}
