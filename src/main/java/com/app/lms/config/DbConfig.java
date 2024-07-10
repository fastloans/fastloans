package com.app.lms.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;

import javax.sql.DataSource;

@Configuration
@EnableJpaRepositories(entityManagerFactoryRef = "lmsEntityManagerFactory",basePackages = {"com.app.lms.repository"})
public class DbConfig {

    @Primary
    @Bean(name = "lms")
    @ConfigurationProperties("spring.datasource")
    public DataSource dataSource(){
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "lmsEntityManagerFactory")
    @Primary
    public LocalContainerEntityManagerFactoryBean consumerEntityManagerFactory(EntityManagerFactoryBuilder builder,@Qualifier("lms") DataSource dataSource) {
        return builder.dataSource(dataSource).packages("com.app.lms.entity").persistenceUnit("lms").build();
    }

    @Bean("lmsJdbcTemplate")
    @Primary
    public JdbcTemplate jdbcTemplate(@Qualifier("lms") DataSource dataSource){
        return new JdbcTemplate(dataSource);
    }
}
