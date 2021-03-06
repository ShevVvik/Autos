package com.shevvvik.autos.services;

import com.shevvvik.autos.database.connection.JDBCConnection;
import com.shevvvik.autos.services.logger.Logger;
import com.shevvvik.autos.services.logger.LoggerConstants;
import com.shevvvik.autos.web.forms.RegistrationForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.SQLException;

@Service
public class UserDomainServices {

    @Autowired
    private JDBCConnection jdbcConnection;

    @Autowired
    private Logger logger;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public boolean createUser(RegistrationForm registrationForm) {
        registrationForm.setPassword(passwordEncoder.encode(registrationForm.getPassword()));
        try {
            jdbcConnection.createUser(registrationForm);
        } catch (SQLException exception) {
            System.out.println(exception);
        }
        logger.log(LoggerConstants.REGISTRATION_OPERATION, "New user with username - {?} was created", registrationForm.getLogin());
        return true;
    }

}
