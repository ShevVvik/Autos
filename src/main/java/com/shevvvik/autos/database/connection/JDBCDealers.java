package com.shevvvik.autos.database.connection;


import com.shevvvik.autos.database.StoredProcedureList;
import com.shevvvik.autos.services.entities.DealerProfile;
import com.shevvvik.autos.services.entities.DealersOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class JDBCDealers {

    @Autowired
    private DataSource dataSource;

    public DealerProfile getDealerProfileByUsername(String username) throws SQLException {
        Connection connection = dataSource.getConnection();
        CallableStatement callableStatement = connection.prepareCall(StoredProcedureList.GET_DEALER_PROFILE_BY_USERNAME);
        callableStatement.setString(1, username);
        DealerProfile dealerProfile = new DealerProfile();
        try (ResultSet resultSet = callableStatement.executeQuery()) {
            if (resultSet.next()) {
                Integer id = resultSet.getInt(1);
                dealerProfile.setFirstName(resultSet.getString(2));
                dealerProfile.setPatronymic(resultSet.getString(3));
                dealerProfile.setLastName(resultSet.getString(4));
                dealerProfile.setEmail(resultSet.getString(5));
                dealerProfile.setPhone(resultSet.getString(6));
                dealerProfile.setCompletedOrders(resultSet.getInt(7));
                dealerProfile.setEnteredOrders(resultSet.getInt(8));
                dealerProfile.setCancelledOrders(resultSet.getInt(9));
                dealerProfile.setOrders(getOrdersByDealerId(id));
            }
        } catch (Exception e) {
            throw e;
        }
        connection.close();
        return dealerProfile;
    }

    private List<DealersOrder> getOrdersByDealerId(Integer id) throws SQLException {
        List<DealersOrder> list = new ArrayList<>();
        Connection connection = dataSource.getConnection();
        CallableStatement callableStatement = connection.prepareCall(StoredProcedureList.GET_OFFERS_BY_DEALER_ID);
        callableStatement.setInt(1, id);
        try (ResultSet resultSet = callableStatement.executeQuery()) {
            while (resultSet.next()) {
                DealersOrder dealersOrder = new DealersOrder();
                dealersOrder.setId(resultSet.getInt(1));
                dealersOrder.setAuto(resultSet.getString(2));
                dealersOrder.setDateStart(resultSet.getDate(3).toString());
                dealersOrder.setDateEnd(resultSet.getDate(4) != null
                        ? (resultSet.getDate(4).toString()) : null);
                dealersOrder.setStatus(resultSet.getString(5));
                dealersOrder.setPrice(resultSet.getInt(6));
                dealersOrder.setClientId(resultSet.getInt(7));
                dealersOrder.setClient(resultSet.getString(8));
                dealersOrder.setPercent(resultSet.getInt(9));
                dealersOrder.setFinalPrice((int) (dealersOrder.getPrice() * (1 + dealersOrder.getPercent() / 100.0)));
                list.add(dealersOrder);
            }
        } catch (Exception e) {
            throw e;
        }
        connection.close();
        return list;
    }

    public DealerProfile getDealerById(Integer dealerId) throws SQLException {
        Connection connection = dataSource.getConnection();
        CallableStatement callableStatement = connection.prepareCall(StoredProcedureList.GET_DEALER_PROFILE_BY_ID);
        callableStatement.setInt(1, dealerId);
        DealerProfile dealerProfile = new DealerProfile();
        try (ResultSet resultSet = callableStatement.executeQuery()) {
            if (resultSet.next()) {
                Integer id = resultSet.getInt(1);
                dealerProfile.setFirstName(resultSet.getString(2));
                dealerProfile.setPatronymic(resultSet.getString(3));
                dealerProfile.setLastName(resultSet.getString(4));
                dealerProfile.setEmail(resultSet.getString(5));
                dealerProfile.setPhone(resultSet.getString(6));
                dealerProfile.setCompletedOrders(resultSet.getInt(7));
                dealerProfile.setEnteredOrders(resultSet.getInt(8));
                dealerProfile.setCancelledOrders(resultSet.getInt(9));
                dealerProfile.setOrders(getOrdersByDealerId(id));
            }
        } catch (Exception e) {
            throw e;
        }
        connection.close();
        return dealerProfile;
    }
}
