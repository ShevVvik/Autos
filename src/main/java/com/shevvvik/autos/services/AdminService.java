package com.shevvvik.autos.services;

import com.shevvvik.autos.database.connection.JDBCAdmins;
import com.shevvvik.autos.services.logger.LogEntity;
import com.shevvvik.autos.services.logger.Logger;
import com.shevvvik.autos.services.logger.LoggerConstants;
import com.shevvvik.autos.web.forms.LoggerForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AdminService {

    @Autowired
    private JDBCAdmins jdbcAdmins;

    public List<LogEntity> getLogEvents(LoggerForm loggerForm) {
        Map<Integer, Boolean> events = new HashMap<>();
        Date date = new Date(0);
        events.put(LoggerConstants.DEFAULT_OPERATION.getValue(), true); //loggerForm.isDefaultOp());
        events.put(LoggerConstants.CREATE_ORDER_OPERATION.getValue(), loggerForm.isCreateOp());
        events.put(LoggerConstants.ASSIGN_ORDER_OPERATION.getValue(), loggerForm.isAssignOp());
        events.put(LoggerConstants.CHANGE_ORDER_STATUS_OPERATION.getValue(), loggerForm.isStatusOp());
        events.put(LoggerConstants.LOG_IN_OPERATION.getValue(), loggerForm.isLogInOp());
        events.put(LoggerConstants.REGISTRATION_OPERATION.getValue(), loggerForm.isRegistrationOp());
        events.put(LoggerConstants.SEARCH_OPERATION.getValue(), loggerForm.isSearchOp());

        System.out.println(loggerForm.isAssignOp());
        System.out.println(loggerForm.getDateStart());
        System.out.println(loggerForm.getDateEnd());

        List<LogEntity> result = new ArrayList<>();
        try {
            result = jdbcAdmins.getLogEntities(loggerForm.getDateStart() == null
                            || loggerForm.getDateStart().trim() == "" ? date.toString() : loggerForm.getDateStart(),
                    loggerForm.getDateEnd() == null
                            || loggerForm.getDateEnd().trim() == "" ? date.toString() : loggerForm.getDateEnd(),
                    events);
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return result;
    }

}
