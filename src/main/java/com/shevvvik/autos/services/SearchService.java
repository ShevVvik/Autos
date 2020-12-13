package com.shevvvik.autos.services;

import com.shevvvik.autos.database.connection.JDBCSearch;
import com.shevvvik.autos.services.entities.SearchClientEntity;
import com.shevvvik.autos.services.entities.SearchDealerEntity;
import com.shevvvik.autos.services.entities.SearchOfferEntity;
import com.shevvvik.autos.web.forms.SearchClientsForm;
import com.shevvvik.autos.web.forms.SearchDealersForm;
import com.shevvvik.autos.web.forms.SearchOrdersForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
public class SearchService {

    @Autowired
    private JDBCSearch jdbcSearch;

    public List<SearchClientEntity> searchClients(SearchClientsForm searchForm) {
        List<SearchClientEntity> result = new ArrayList<>();

        searchForm.setAddress(searchForm.getAddress() == null ? "" : searchForm.getAddress().trim());
        searchForm.setCity(searchForm.getCity() == null ? 0 : searchForm.getCity());
        searchForm.setEmail(searchForm.getEmail() == null ? "" : searchForm.getEmail().trim());
        searchForm.setFirstName(searchForm.getFirstName() == null ? "" : searchForm.getFirstName().trim());
        searchForm.setLastName(searchForm.getLastName() == null ? "" : searchForm.getLastName().trim());
        searchForm.setPatronymic(searchForm.getPatronymic() == null ? "" : searchForm.getPatronymic().trim());
        searchForm.setPhone(searchForm.getPhone() == null ? 0 : searchForm.getPhone());

        try {
            result = jdbcSearch.searchClientsByForm(searchForm);
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        return result;
    }

    public List<SearchDealerEntity> searchDealers(SearchDealersForm searchForm) {
        List<SearchDealerEntity> result = new ArrayList<>();
        searchForm.setEmail(searchForm.getEmail() == null ? "" : searchForm.getEmail().trim());
        searchForm.setFirstName(searchForm.getFirstName() == null ? "" : searchForm.getFirstName().trim());
        searchForm.setLastName(searchForm.getLastName() == null ? "" : searchForm.getLastName().trim());
        searchForm.setPatronymic(searchForm.getPatronymic() == null ? "" : searchForm.getPatronymic().trim());
        searchForm.setPhone(searchForm.getPhone() == null ? 0 : searchForm.getPhone());
        searchForm.setOrdersType(searchForm.getOrdersType() == null ? 0 : searchForm.getOrdersType());
        try {
            result = jdbcSearch.searchDealersBySearchForm(searchForm);
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return result;
    }

    public List<SearchOfferEntity> searchOffersDetailed(SearchOrdersForm searchForm) {
        List<SearchOfferEntity> result = new ArrayList<>();
        Date date = new Date(0);

        searchForm.setClientFirstName(searchForm.getClientFirstName() == null ? "" : searchForm.getClientFirstName().trim());
        searchForm.setClientLastName(searchForm.getClientLastName() == null ? "" : searchForm.getClientLastName().trim());
        searchForm.setClientPatronymic(searchForm.getClientPatronymic() == null ? "" : searchForm.getClientPatronymic().trim());
        searchForm.setCity(searchForm.getCity() == null ? 0 : searchForm.getCity());

        searchForm.setDealerFirstName(searchForm.getDealerFirstName() == null ? "" : searchForm.getDealerFirstName().trim());
        searchForm.setDealerLastName(searchForm.getDealerLastName() == null ? "" : searchForm.getDealerLastName().trim());
        searchForm.setDealerPatronymic(searchForm.getDealerPatronymic() == null ? "" : searchForm.getDealerPatronymic().trim());

        searchForm.setBrand(searchForm.getBrand() == null ? "" : searchForm.getBrand().trim());
        searchForm.setModel(searchForm.getModel() == null ? "" : searchForm.getModel().trim());
        searchForm.setDate(searchForm.getDate() == null ? 0 : searchForm.getDate());
        searchForm.setMileageMin(searchForm.getMileageMin() == null ? 0 : searchForm.getMileageMax());
        searchForm.setMileageMax(searchForm.getMileageMax() == null ? -1 : searchForm.getMileageMax());

        searchForm.setPriceMin(searchForm.getPriceMin() == null ? 0 : searchForm.getPriceMin());
        searchForm.setPriceMax(searchForm.getPriceMax() == null ? 0 : searchForm.getPriceMax());
        searchForm.setDateStartMin(searchForm.getDateStartMin() == null
                || searchForm.getDateStartMin().trim() == "" ? date.toString() : searchForm.getDateStartMin());
        searchForm.setDateStartMax(searchForm.getDateStartMax() == null
                || searchForm.getDateStartMax().trim() == "" ? date.toString() : searchForm.getDateStartMax());
        searchForm.setStatus(searchForm.getStatus() == null ? 0 : searchForm.getStatus());
        try {
            result = jdbcSearch.searchOffersByDetailedSearchForm(searchForm);
        } catch (Exception exception) {
            exception.printStackTrace();
        }

        searchForm.setDateStartMin(searchForm.getDateStartMin().equals(date.toString()) ? "" : searchForm.getDateStartMin());
        searchForm.setDateStartMax(searchForm.getDateStartMax().equals(date.toString()) ? "" : searchForm.getDateStartMax());
        return result;
    }
}
