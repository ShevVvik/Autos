package com.shevvvik.autos.database.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@ComponentScan
@Configuration
public class JDBCConfiguration {

    @Bean(name  = "dataSource")
    public DriverManagerDataSource dataSource() {
        DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();
        driverManagerDataSource.setDriverClassName("org.mariadb.jdbc.Driver");
        driverManagerDataSource.setUrl("jdbc:mariadb://localhost:3307/autos?useSSL=false&characterEncoding=utf8mb4");
        driverManagerDataSource.setUsername("root");
        driverManagerDataSource.setPassword("Brintecros1999S2");
        return driverManagerDataSource;
    }
}
