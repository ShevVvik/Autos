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

    public String checkRegistrationForm(RegistrationForm form) {
        String errorMessage = "";
        Map<Integer, String> cities = serviceUtils.getCities();
        Integer loginCount = 0;
        Integer emailCount = 0;
        try {
            loginCount = validation.checkLogin(form.getLogin());
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        try {
            emailCount = validation.checkEmail(form.getEmail());
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        if (loginCount != 0) {
            errorMessage += "<span> - Login already used </span><br>";
        }
        if (emailCount != 0) {
            errorMessage += "<span> - Email already used </span><br>";
        }
        if (!cities.keySet().contains(form.getCity())) {
            errorMessage += "<span> - Wrong city </span><br>";
        }
        if (!form.getPassword().equals(form.getPasswordConfirm())) {
            errorMessage += "<span> - Check password </span><br>";
        }
        if (form.getFirstName() == null || form.getFirstName().trim().equals("")) {
            errorMessage += "<span> - Fill first name </span><br>";
        }
        if (form.getSurName() == null || form.getSurName().trim().equals("")) {
            errorMessage += "<span> - Fill patronymic </span><br>";
        }
        if (form.getLastName() == null || form.getLastName().trim().equals("")) {
            errorMessage += "<span> - Fill last name </span><br>";
        }
        if (form.getEmail() == null || validateEmail(form.getEmail().trim())) {
            errorMessage += "<span> - Email not valid </span><br>";
        }
        if (form.getPhone() == null || validatePhone(form.getPhone())) {
            errorMessage += "<span> - Phone not valid </span><br>";
        }
        if (form.getAddress() == null || form.getAddress().trim().equals("")) {
            errorMessage += "<span> - Fill address </span><br>";
        }
        return errorMessage;
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
