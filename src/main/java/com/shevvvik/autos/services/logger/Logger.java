package com.shevvvik.autos.services.logger;

import org.springframework.beans.factory.annotation.Autowired;

import javax.sql.DataSource;

public class Logger {

    private static final String logQuery = "{ call logEvent(?, ? ?) }";

    @Autowired
    private static DataSource dataSource;

    private static void storeInDB() {

    }

    public static void log(Integer eventType, String description) {

    }

}
