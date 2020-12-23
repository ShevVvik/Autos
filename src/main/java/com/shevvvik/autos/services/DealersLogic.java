package com.shevvvik.autos.services;

import com.shevvvik.autos.configuration.exceptions.ObjectNotFound;
import com.shevvvik.autos.database.connection.JDBCDealers;
import com.shevvvik.autos.services.entities.DealerProfile;
import com.shevvvik.autos.services.logger.Logger;
import com.shevvvik.autos.services.logger.LoggerConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.sql.SQLException;

@Service
public class DealersLogic {
    
    @Autowired
    private JDBCDealers jdbcDealers;

    @Autowired
    private Logger logger;

    public DealerProfile getDealerById(String id) throws ObjectNotFound {
        DealerProfile dealerProfile = new DealerProfile();
        try {
            dealerProfile = jdbcDealers.getDealerById(Integer.valueOf(id));
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        logger.log(LoggerConstants.DEFAULT_OPERATION, "Profile for dealer ({?}) was opened", id);
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
        logger.log(LoggerConstants.DEFAULT_OPERATION, "Dealer's profile was opened");
        return dealerProfile;
    }
}
