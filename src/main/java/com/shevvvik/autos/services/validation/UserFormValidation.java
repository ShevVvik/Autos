package com.shevvvik.autos.services.validation;

import com.shevvvik.autos.database.connection.JDBCValidation;
import com.shevvvik.autos.services.ServiceUtils;
import com.shevvvik.autos.web.forms.RegistrationForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class UserFormValidation {

    @Autowired
    private JDBCValidation validation;

    @Autowired
    private ServiceUtils serviceUtils;

    public boolean checkRegistrationForm(RegistrationForm form) {

        Map<Integer, String> cities = serviceUtils.getCities();
        Integer loginCount = 0;
        try {
            loginCount = validation.checkLogin(form.getLogin());
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        if (loginCount != 0) {
            return false;
        }
        if (!cities.keySet().contains(form.getCity())) {
            return false;
        }
        if (!form.getPassword().equals(form.getPasswordConfirm())) {
            return false;
        }
        if (form.getFirstName() == null || form.getFirstName().trim().equals("")) {
            return false;
        }
        if (form.getSurName() == null || form.getSurName().trim().equals("")) {
            return false;
        }
        if (form.getLastName() == null || form.getLastName().trim().equals("")) {
            return false;
        }
        if (form.getEmail() == null || validateEmail(form.getEmail().trim())) {
            return false;
        }
        if (form.getPhone() == null || validatePhone(form.getPhone())) {
            return false;
        }
        if (form.getAddress() == null || form.getAddress().trim().equals("")) {
            return false;
        }
        return true;
    }

    private boolean validateEmail(String email) {
        Pattern emailPattern = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
        Matcher matcher = emailPattern.matcher(email);
        return !matcher.matches();
    }

    private boolean validatePhone(String phone) {
        Pattern pattern = Pattern.compile("^\\d{11}$");
        Matcher matcher = pattern.matcher(phone);
        return !matcher.matches();
    }

}
