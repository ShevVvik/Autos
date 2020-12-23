package com.shevvvik.autos.services;

import com.shevvvik.autos.configuration.exceptions.ObjectNotFound;
import com.shevvvik.autos.database.connection.JDBCConnection;
import com.shevvvik.autos.services.entities.ClientProfile;
import com.shevvvik.autos.services.entities.DealerProfile;
import com.shevvvik.autos.services.logger.Logger;
import com.shevvvik.autos.services.logger.LoggerConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.sql.SQLException;

@Service
public class ClientsLogic {

    @Autowired
    private JDBCConnection jdbcConnection;

    @Autowired
    private Logger logger;

    public ClientProfile getClientProfile() {
        ClientProfile clientProfile = new ClientProfile();

        String username = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        try {
            clientProfile = jdbcConnection.getClientProfileByUsername(username);
        } catch (Exception e) {
            e.printStackTrace();
        }

        logger.log(LoggerConstants.DEFAULT_OPERATION, "Profile was opened");
        return clientProfile;
    }

    public ClientProfile getClientProfileById(String id) {
        ClientProfile clientProfile = new ClientProfile();
        try {
            clientProfile = jdbcConnection.getClientById(Integer.valueOf(id));
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        logger.log(LoggerConstants.DEFAULT_OPERATION, "Profile for client ({?}) was opened", id);
        return clientProfile;
    }
}
