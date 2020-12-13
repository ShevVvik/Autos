package com.shevvvik.autos.database.connection;

import com.shevvvik.autos.database.StoredProcedureList;
import com.shevvvik.autos.services.entities.ClientProfile;
import com.shevvvik.autos.services.entities.ClientsOrder;
import com.shevvvik.autos.web.forms.RegistrationForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class JDBCConnection {

    @Autowired
    private DataSource dataSource;


    public void createUser(RegistrationForm form) throws SQLException {
        Connection connection = dataSource.getConnection();
        CallableStatement callableStatement = connection.prepareCall(StoredProcedureList.REGISTRATION_QUERY);
        callableStatement.setString(1, form.getLogin());
        callableStatement.setString(2, form.getPassword());
        callableStatement.setString(3, form.getEmail());
        callableStatement.setString(4, form.getFirstName());
        callableStatement.setString(5, form.getSurName());
        callableStatement.setString(6, form.getLastName());
        callableStatement.setInt(7, form.getCity());
        callableStatement.setString(8, form.getAddress());
        callableStatement.setLong(9, form.getPhone());
        callableStatement.execute();
        connection.close();
    }

    public Map<Integer, String> getCities() throws SQLException {
        Map<Integer, String> map = new HashMap<>();
        Connection connection = dataSource.getConnection();
        CallableStatement callableStatement = connection.prepareCall(StoredProcedureList.CITIES_QUERY);
        try (ResultSet resultSet = callableStatement.executeQuery()) {
            while (resultSet.next()) {
                map.put(resultSet.getInt("id"),
                        resultSet.getString("name"));
            }
        } catch (Exception e) {
            throw e;
        }
        return map;
    }

    public ClientProfile getClientProfileByUsername(String username) throws SQLException {
        Connection connection = dataSource.getConnection();
        CallableStatement callableStatement = connection.prepareCall(StoredProcedureList.GET_CLIENT_PROFILE);
        callableStatement.setString(1, username);
        ClientProfile clientProfile = new ClientProfile();
        try (ResultSet resultSet = callableStatement.executeQuery()) {
            if (resultSet.next()) {
                Integer id = resultSet.getInt(1);
                clientProfile.setFirstName(resultSet.getString(2));
                clientProfile.setPatronymic(resultSet.getString(3));
                clientProfile.setLastName(resultSet.getString(4));
                clientProfile.setCity(resultSet.getString(5));
                clientProfile.setEmail(resultSet.getString(6));
                clientProfile.setAddress(resultSet.getString(7));
                clientProfile.setPhone(resultSet.getString(8));
                clientProfile.setCompletedOrders(resultSet.getInt(9));
                clientProfile.setEnteredOrders(resultSet.getInt(10));
                clientProfile.setCancelledOrders(resultSet.getInt(11));
                clientProfile.setOrders(getOrdersByClientId(id));
            }
        } catch (Exception e) {
            throw e;
        }
        connection.close();
        return clientProfile;
    }

    private List<ClientsOrder> getOrdersByClientId(Integer id) throws SQLException {
        List<ClientsOrder> list = new ArrayList<>();
        Connection connection = dataSource.getConnection();
        CallableStatement callableStatement = connection.prepareCall(StoredProcedureList.GET_OFFERS_BY_CLIENT_ID);
        callableStatement.setInt(1, id);
        try (ResultSet resultSet = callableStatement.executeQuery()) {
            while (resultSet.next()) {
                ClientsOrder clientsOrder = new ClientsOrder();
                clientsOrder.setId(resultSet.getInt(1));
                clientsOrder.setAuto(resultSet.getString(2));
                clientsOrder.setDateStart(resultSet.getDate(3).toString());
                clientsOrder.setDateEnd(resultSet.getDate(4) != null
                        ? (resultSet.getDate(4).toString()) : " ");
                clientsOrder.setStatus(resultSet.getString(5));
                clientsOrder.setPrice(resultSet.getInt(6));
                clientsOrder.setDealerId(resultSet.getInt(7));
                clientsOrder.setDealer(resultSet.getString(8));
                if ("Entered".equals(clientsOrder.getStatus())) {
                    clientsOrder.setCancel(true);
                } else {
                    clientsOrder.setCancel(false);
                }
                list.add(clientsOrder);
            }
        } catch (Exception e) {
            throw e;
        }
        connection.close();
        return list;
    }

    public ClientProfile getClientById(Integer id) throws SQLException {
        Connection connection = dataSource.getConnection();
        CallableStatement callableStatement = connection.prepareCall(StoredProcedureList.GET_CLIENT_PROFILE_BY_ID);
        callableStatement.setInt(1, id);
        ClientProfile clientProfile = new ClientProfile();
        try (ResultSet resultSet = callableStatement.executeQuery()) {
            if (resultSet.next()) {
                clientProfile.setFirstName(resultSet.getString(2));
                clientProfile.setPatronymic(resultSet.getString(3));
                clientProfile.setLastName(resultSet.getString(4));
                clientProfile.setCity(resultSet.getString(5));
                clientProfile.setEmail(resultSet.getString(6));
                clientProfile.setAddress(resultSet.getString(7));
                clientProfile.setPhone(resultSet.getString(8));
                clientProfile.setCompletedOrders(resultSet.getInt(9));
                clientProfile.setEnteredOrders(resultSet.getInt(10));
                clientProfile.setCancelledOrders(resultSet.getInt(11));
                clientProfile.setOrders(getOrdersByClientId(id));
            }
        } catch (Exception e) {
            throw e;
        }
        connection.close();
        return clientProfile;
    }
}
