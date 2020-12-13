package com.shevvvik.autos.services;

import com.shevvvik.autos.database.connection.JDBCDealers;
import com.shevvvik.autos.services.entities.DealerProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.sql.SQLException;

@Service
public class DealersLogic {
    
    @Autowired
    private JDBCDealers jdbcDealers;


    public DealerProfile getDealerById(String id) {
        DealerProfile dealerProfile = new DealerProfile();
        try {
            dealerProfile = jdbcDealers.getDealerById(Integer.valueOf(id));
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return dealerProfile;
    }

    public DealerProfile getDealerProfile() {
        String username = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();
        DealerProfile dealerProfile = new DealerProfile();
        try {
            dealerProfile = jdbcDealers.getDealerProfileByUsername(username);
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return dealerProfile;
    }
}
