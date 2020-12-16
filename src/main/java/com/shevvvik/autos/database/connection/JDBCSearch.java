package com.shevvvik.autos.database.connection;

import com.shevvvik.autos.database.StoredProcedureList;
import com.shevvvik.autos.services.entities.SearchClientEntity;
import com.shevvvik.autos.services.entities.SearchDealerEntity;
import com.shevvvik.autos.services.entities.SearchOfferEntity;
import com.shevvvik.autos.web.forms.SearchClientsForm;
import com.shevvvik.autos.web.forms.SearchDealersForm;
import com.shevvvik.autos.web.forms.SearchOrdersForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
public class JDBCSearch {

    @Autowired
    private DataSource dataSource;

    public List<SearchClientEntity> searchClientsByForm(SearchClientsForm searchForm) throws SQLException {
        List<SearchClientEntity> result = new ArrayList<>();
        Connection connection = dataSource.getConnection();
        CallableStatement callableStatement = connection.prepareCall(StoredProcedureList.SEARCH_CLIENTS);
        callableStatement.setString(1, searchForm.getFirstName());
        callableStatement.setString(2, searchForm.getLastName());
        callableStatement.setString(3, searchForm.getPatronymic());
        callableStatement.setInt(4, searchForm.getCity());
        callableStatement.setString(5, searchForm.getAddress());
        callableStatement.setInt(6, Integer.valueOf(searchForm.getPhone()));
        callableStatement.setString(7, searchForm.getEmail());
        try (ResultSet resultSet = callableStatement.executeQuery()) {
            while (resultSet.next()) {
                SearchClientEntity searchClientEntity = new SearchClientEntity();
                searchClientEntity.setId(resultSet.getInt(1));
                searchClientEntity.setFirstName(resultSet.getString(2));
                searchClientEntity.setPatronymic(resultSet.getString(3));
                searchClientEntity.setLastName(resultSet.getString(4));
                searchClientEntity.setCity(resultSet.getString(5));
                searchClientEntity.setPhone(resultSet.getInt(6));
                searchClientEntity.setEmail(resultSet.getString(7));
                result.add(searchClientEntity);
            }
        } catch (Exception e) {
            connection.close();
            throw e;
        }
        connection.close();
        return result;
    }


    public List<SearchDealerEntity> searchDealersBySearchForm(SearchDealersForm searchForm) throws SQLException {
        List<SearchDealerEntity> result = new ArrayList<>();
        Connection connection = dataSource.getConnection();
        CallableStatement callableStatement = connection.prepareCall(StoredProcedureList.SEARCH_DEALERS);
        callableStatement.setString(1, searchForm.getFirstName());
        callableStatement.setString(2, searchForm.getLastName());
        callableStatement.setString(3, searchForm.getPatronymic());
        callableStatement.setInt(4, Integer.valueOf(searchForm.getPhone()));
        callableStatement.setInt(5, searchForm.getOrdersType());
        callableStatement.setString(6, searchForm.getEmail());
        try (ResultSet resultSet = callableStatement.executeQuery()) {
            while(resultSet.next()) {
                SearchDealerEntity searchDealerEntity = new SearchDealerEntity();
                searchDealerEntity.setId(resultSet.getInt(1));
                searchDealerEntity.setFirstName(resultSet.getString(2));
                searchDealerEntity.setPatronymic(resultSet.getString(3));
                searchDealerEntity.setLastName(resultSet.getString(4));
                searchDealerEntity.setPhone(resultSet.getInt(5));
                searchDealerEntity.setEmail(resultSet.getString(6));
                searchDealerEntity.setEnteredOrders(resultSet.getInt(7));
                searchDealerEntity.setInProgressOrders(resultSet.getInt(8));
                searchDealerEntity.setCompletedOrders(resultSet.getInt(9));
                searchDealerEntity.setCancelledOrders(resultSet.getInt(10));
                result.add(searchDealerEntity);
            }
        } catch (Exception e) {
            connection.close();
            throw e;
        }
        connection.close();
        return result;
    }

    public List<SearchOfferEntity> searchOffersByDetailedSearchForm(SearchOrdersForm searchForm) throws SQLException {
        List<SearchOfferEntity> result = new ArrayList<>();
        Connection connection = dataSource.getConnection();
        CallableStatement callableStatement = connection.prepareCall(StoredProcedureList.SEARCH_DETAILED); //TODO

        callableStatement.setString(1, searchForm.getClientFirstName());
        callableStatement.setString(2, searchForm.getClientLastName());
        callableStatement.setString(3, searchForm.getClientPatronymic());
        callableStatement.setInt(4, searchForm.getCity());

        callableStatement.setString(5, searchForm.getDealerFirstName());
        callableStatement.setString(6, searchForm.getDealerLastName());
        callableStatement.setString(7, searchForm.getDealerPatronymic());

        callableStatement.setString(8, searchForm.getBrand());
        callableStatement.setString(9, searchForm.getModel());
        callableStatement.setInt(10, searchForm.getMileageMin());
        callableStatement.setInt(11, searchForm.getMileageMax());
        callableStatement.setInt(12, searchForm.getDate());

        callableStatement.setInt(13, searchForm.getPriceMin());
        callableStatement.setInt(14, searchForm.getPriceMax());
        callableStatement.setDate(15, java.sql.Date.valueOf(searchForm.getDateStartMin()));
        callableStatement.setDate(16, java.sql.Date.valueOf(searchForm.getDateStartMax()));
        callableStatement.setInt(17, searchForm.getStatus());

        try (ResultSet resultSet = callableStatement.executeQuery()) {
            while(resultSet.next()) {
                SearchOfferEntity searchOfferEntity = new SearchOfferEntity();
                searchOfferEntity.setId(resultSet.getInt(1));
                searchOfferEntity.setClientId(resultSet.getInt(2));
                searchOfferEntity.setClient(resultSet.getString(3));
                searchOfferEntity.setDealerId(resultSet.getInt(4));
                searchOfferEntity.setDealer(resultSet.getString(5));
                searchOfferEntity.setAuto(resultSet.getString(6));
                searchOfferEntity.setDateStart(resultSet.getDate(7));
                searchOfferEntity.setDateEnd(resultSet.getDate(8));
                searchOfferEntity.setStatus(resultSet.getString(9));
                searchOfferEntity.setFinalPrice(resultSet.getInt(10));
                result.add(searchOfferEntity);
            }
        } catch (Exception e) {
            connection.close();
            throw e;
        }
        connection.close();
        return result;
    }
}
