package com.shevvvik.autos.services;

import com.shevvvik.autos.database.connection.JDBCConnection;
import com.shevvvik.autos.web.forms.RegistrationForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;

@Service
public class UserDomainServices {

    @Autowired
    JDBCConnection jdbcConnection;

    public boolean createUser(RegistrationForm registrationForm) {
        try {
            jdbcConnection.createUser(registrationForm.getLogin(),
                    registrationForm.getPassword(),
                    registrationForm.getEmail(),
                    registrationForm.getLastName(),
                    registrationForm.getSurName(),
                    registrationForm.getFirstName(),
                    registrationForm.getCity());
        } catch (SQLException exception) {
            System.out.println(exception);
        }
        return true;
    }

}
