package com.shevvvik.autos.services;

import com.shevvvik.autos.database.connection.JDBCConnection;
import com.shevvvik.autos.services.entities.ClientProfile;
import com.shevvvik.autos.services.entities.DealerProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.sql.SQLException;

@Service
public class ClientsLogic {

    @Autowired
    private JDBCConnection jdbcConnection;

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

        return clientProfile;
    }

    public ClientProfile getClientProfileById(String id) {
        ClientProfile clientProfile = new ClientProfile();
        try {
            clientProfile = jdbcConnection.getClientById(Integer.valueOf(id));
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return clientProfile;
    }
}
