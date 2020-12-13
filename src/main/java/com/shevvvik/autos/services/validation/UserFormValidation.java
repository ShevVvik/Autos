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
            System.out.println(1);
            return false;
        }
        if (!cities.keySet().contains(form.getCity())) {
            System.out.println(2);
            return false;
        }
        if (!form.getPassword().equals(form.getPasswordConfirm())) {
            System.out.println(3);
            return false;
        }
        if (form.getFirstName() == null || form.getFirstName().trim().equals("")) {
            System.out.println(4);
            return false;
        }
        if (form.getSurName() == null || form.getSurName().trim().equals("")) {
            System.out.println(5);
            return false;
        }
        if (form.getLastName() == null || form.getLastName().trim().equals("")) {
            System.out.println(6);
            return false;
        }
        if (form.getEmail() == null || validateEmail(form.getEmail().trim())) {
            System.out.println(form.getEmail());
            return false;
        }
        if (form.getPhone() == null || validatePhone(form.getPhone().toString())) {
            System.out.println(8);
            return false;
        }
        if (form.getAddress() == null || form.getAddress().trim().equals("")) {
            System.out.println(9);
            return false;
        }
        return true;
    }

    private boolean validateEmail(String email) {
        Pattern emailPattern = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
        Matcher matcher = emailPattern.matcher(email);
        return matcher.matches();
    }

    private boolean validatePhone(String phone) {
        Pattern pattern = Pattern.compile("^\\d{10}$");
        Matcher matcher = pattern.matcher(phone);
        return matcher.matches();
    }

}
