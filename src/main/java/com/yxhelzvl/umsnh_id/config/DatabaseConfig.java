package com.yxhelzvl.umsnh_id.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;

@Configuration
public class DatabaseConfig {
    private final DataSourcePropertiesConf dataSourceProperties;

    public DatabaseConfig(DataSourcePropertiesConf dataSourceProperties) {
        this.dataSourceProperties = dataSourceProperties;
    }

    @Bean
    public DataSource dataSource() {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl(dataSourceProperties.getUrl());
        dataSource.setUsername(dataSourceProperties.getUsername());
        dataSource.setPassword(dataSourceProperties.getPassword());
        dataSource.setDriverClassName(dataSourceProperties.getDriverClassName());
        dataSource.setMaximumPoolSize(dataSourceProperties.getMaximumPoolSize());
        System.out.println("Datasource: " + dataSourceProperties.toString());
        return dataSource;
    }
    @Bean
    public JdbcTemplate crudJdbcTemplate(DataSource dataSource) {
        var jdbcTemplate = new JdbcTemplate(dataSource);
        return jdbcTemplate;
    }

    @Bean
    public NamedParameterJdbcTemplate crudNamedParameterJdbcTemplate(JdbcTemplate crudJdbcTemplate) {
        return new NamedParameterJdbcTemplate(crudJdbcTemplate);
    }


}