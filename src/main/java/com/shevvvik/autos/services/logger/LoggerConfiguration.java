package com.shevvvik.autos.services.logger;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import java.util.HashMap;
import java.util.Map;

@Configuration
@ComponentScan
public class LoggerConfiguration {

    @Bean(name  = "logger")
    @Scope("singleton")
    public Logger createLogger() {
        Logger logger = new Logger();

        Map<LoggerConstants, Boolean> config = new HashMap<>();
        for (LoggerConstants event : LoggerConstants.values()) {
            config.put(event, true);
        }

        logger.setLogConfiguration(config);

        return logger;
    }

}
