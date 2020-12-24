package com.shevvvik.autos.services.validation;

import com.fasterxml.jackson.core.JsonToken;
import com.shevvvik.autos.database.connection.JDBCConnection;
import com.shevvvik.autos.services.ServiceUtils;
import com.shevvvik.autos.web.forms.SearchClientsForm;
import com.shevvvik.autos.web.forms.SearchDealersForm;
import com.shevvvik.autos.web.forms.SearchOrdersForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class SearchValidator {

    @Autowired
    private ServiceUtils serviceUtils;

    public boolean checkSearchClientForm(SearchClientsForm searchForm) {

        if(searchForm.getCity() != 0) {
            Map<Integer, String> cities = serviceUtils.getCities();
            if (!cities.keySet().contains(searchForm.getCity())) {
                return false;
            }
        }

        if (searchForm.getEmail() != null && !searchForm.getEmail().trim().equals("")) {
            Pattern emailPattern = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
            Matcher matcher = emailPattern.matcher(searchForm.getEmail());
            if (!matcher.find()) {
                return false;
            }
        }

        if (searchForm.getPhone() != null && !searchForm.getPhone().equals("")) {
            Pattern pattern = Pattern.compile("^\\d{11}$");
            Matcher matcher = pattern.matcher(searchForm.getPhone());
            if (!matcher.matches()) {
                return false;
            }
        }

        return true;
    }

    public boolean checkSearchDealerForm(SearchDealersForm searchForm) {

        if (searchForm.getEmail() != null && !searchForm.getEmail().trim().equals("")) {
            if(!validateEmail(searchForm.getEmail())) return false;
        }

        if (searchForm.getPhone() != null && !searchForm.getPhone().equals("")) {
            if(!validatePhone(searchForm.getPhone().toString())) return false;
        }

        if (searchForm.getOrdersType() < 0 || searchForm.getOrdersType() > 6) {
            return false;
        }

        return true;
    }

    public boolean checkSearchOffersForm(SearchOrdersForm searchForm) {
        if(searchForm.getCity() != 0) {
            Map<Integer, String> cities = serviceUtils.getCities();
            if (!cities.keySet().contains(searchForm.getCity())) {
                return false;
            }
        }
        if (searchForm.getMileageMin() != null && searchForm.getMileageMin() < 0) {
            return false;
        }

        if (searchForm.getMileageMax() != null && searchForm.getMileageMax() < 0) {
            return false;
        }

        if (searchForm.getMileageMax() != null
                && searchForm.getMileageMin() != null
                && searchForm.getMileageMax() < searchForm.getMileageMin()) {
            return false;
        }

        if (searchForm.getDate() != null && searchForm.getDate() < 1900) {
            return false;
        }

        if (searchForm.getPriceMin() != null && searchForm.getPriceMin() < 0) {
            return false;
        }

        if (searchForm.getPriceMax() != null && searchForm.getPriceMax() < 0) {
            return false;
        }

        if (searchForm.getPriceMax() != null
                && searchForm.getPriceMin() != null
                && searchForm.getPriceMax() < searchForm.getPriceMin()) {
            return false;
        }

        if (searchForm.getStatus() < 0 || searchForm.getStatus() > 4) {
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
        Pattern pattern = Pattern.compile("^\\d{11}$");
        Matcher matcher = pattern.matcher(phone);
        return matcher.matches();
    }
}
