package com.shevvvik.autos.services.validation;

import com.shevvvik.autos.database.StatusConstants;
import com.shevvvik.autos.database.connection.JDBCValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.sql.SQLException;

@Service
public class TransitionValidator {

    @Autowired
    private JDBCValidation connection;

    public boolean checkTransitionToCompleted(Integer orderId) {
        String role = StatusConstants.ROLE_CLIENT;
        for(GrantedAuthority grantedAuthority : SecurityContextHolder.getContext().getAuthentication().getAuthorities()) {
            role = grantedAuthority.getAuthority();
        }

        if (!StatusConstants.ROLE_DEALER.equals(role)) {
            return false;
        }
        Integer currentStatus = 0;
        try {
            currentStatus = connection.getOrderStatusById(orderId);
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        if (StatusConstants.STATUS_CANCELLED.equals(currentStatus)) {
            return false;
        }

        return true;
    }

    public boolean checkTransitionToInProgress(Integer orderId) {
        String role = StatusConstants.ROLE_CLIENT;
        for(GrantedAuthority grantedAuthority : SecurityContextHolder.getContext().getAuthentication().getAuthorities()) {
            role = grantedAuthority.getAuthority();
        }

        if (!StatusConstants.ROLE_DEALER.equals(role)) {
            return false;
        }
        Integer currentStatus = 0;
        try {
            currentStatus = connection.getOrderStatusById(orderId);
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        if (StatusConstants.STATUS_CANCELLED.equals(currentStatus)
                || StatusConstants.STATUS_COMPLETED.equals(currentStatus)) {
            return false;
        }

        return true;
    }

    public boolean checkTransitionToCancelled(Integer orderId) {
        String role = StatusConstants.ROLE_CLIENT;
        for(GrantedAuthority grantedAuthority : SecurityContextHolder.getContext().getAuthentication().getAuthorities()) {
            role = grantedAuthority.getAuthority();
        }

        Integer currentStatus = 0;
        try {
            currentStatus = connection.getOrderStatusById(orderId);
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        //Dealer always can cancel order
        if (StatusConstants.ROLE_DEALER.equals(role)
                && !StatusConstants.STATUS_COMPLETED.equals(currentStatus)) {
            return true;
        }

        if (StatusConstants.STATUS_CANCELLED.equals(currentStatus)
                || StatusConstants.STATUS_COMPLETED.equals(currentStatus)
                || StatusConstants.STATUS_IN_PROGRESS.equals(currentStatus)) {
            return false;
        }

        return true;
    }

    public boolean checkAssign(Integer orderId) {
        String role = StatusConstants.ROLE_CLIENT;
        for(GrantedAuthority grantedAuthority : SecurityContextHolder.getContext().getAuthentication().getAuthorities()) {
            role = grantedAuthority.getAuthority();
        }

        Integer currentStatus = 0;
        try {
            currentStatus = connection.getOrderStatusById(orderId);
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        //Dealer always can cancel order
        if (StatusConstants.ROLE_DEALER.equals(role)
                && !(StatusConstants.STATUS_COMPLETED.equals(currentStatus)
                || StatusConstants.STATUS_CANCELLED.equals(currentStatus))) {
            return true;
        }

        return false;
    }
}
