package com.shevvvik.autos.services.logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

public class Logger {

    private final String logQuery = "{ call logEvent(?, ?, ?) }";
    private Map<LoggerConstants, Boolean> config;

    @Autowired
    private DataSource dataSource;

    private void storeInDB(LoggerConstants eventType, String message, String username) {
        try {
            Connection connection = dataSource.getConnection();
            CallableStatement callableStatement = connection.prepareCall(logQuery);
            callableStatement.setInt(1, eventType.getValue());
            callableStatement.setString(2, message);
            callableStatement.setString(3, username);
            callableStatement.execute();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    public void log(LoggerConstants eventType, String message, String... args) {
        int counter = 0;
        while (message.contains("{?}")) {
            message = message.replaceFirst("\\{\\?}", args[counter]);
            counter++;
        }
        String username = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        if (config.get(eventType)) {
            storeInDB(eventType, message, username);
        }
    }

    public void setLogConfiguration(Map<LoggerConstants, Boolean> config) {
        this.config = config;
    }
}
