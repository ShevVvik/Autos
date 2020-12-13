package com.shevvvik.autos.database.connection;

import com.shevvvik.autos.database.StoredProcedureList;
import com.shevvvik.autos.services.entities.OrderProfile;
import com.shevvvik.autos.web.forms.OrderForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.*;

@Service
public class JDBCOrders {

    @Autowired
    private DataSource dataSource;

    public void cancelOrder(Integer id) throws SQLException {
        Connection connection = dataSource.getConnection();
        CallableStatement callableStatement = connection.prepareCall(StoredProcedureList.CANCEL_ORDER);
        callableStatement.setInt(1, id);
        callableStatement.execute();
        connection.close();
    }

    public void createOrder(OrderForm orderForm, String username) throws SQLException {
        Connection connection = dataSource.getConnection();
        CallableStatement callableStatement = connection.prepareCall(StoredProcedureList.CREATE_ORDER_BY_CLIENT);
        callableStatement.setString(1, orderForm.getBrand());
        callableStatement.setString(2, orderForm.getModel());
        callableStatement.setInt(3, orderForm.getMileage());
        callableStatement.setDate(4, Date.valueOf(orderForm.getDate()));
        callableStatement.setString(5, orderForm.getVin());
        callableStatement.setString(6, orderForm.getCarNumber());
        callableStatement.setString(7, username);
        callableStatement.setInt(8, orderForm.getPrice());
        callableStatement.execute();
        connection.close();
    }

    public OrderProfile getOrderProfileById(Integer id) throws SQLException {
        OrderProfile orderProfile = new OrderProfile();
        Connection connection = dataSource.getConnection();
        CallableStatement callableStatement = connection.prepareCall(StoredProcedureList.GET_ORDER_PROFILE);
        callableStatement.setInt(1, id);
        try (ResultSet resultSet = callableStatement.executeQuery()) {
            if (resultSet.next()) {
                orderProfile.setId(resultSet.getInt(1));
                orderProfile.setClient(resultSet.getString(2));
                orderProfile.setDealer(resultSet.getString(3));
                orderProfile.setDateStart(resultSet.getDate(4).toString());
                orderProfile.setDateEnd(resultSet.getDate(5) != null
                        ? (resultSet.getDate(5).toString()) : null);
                orderProfile.setStatus(resultSet.getString(6));

                orderProfile.setPrice(resultSet.getInt(7));
                orderProfile.setPercent(resultSet.getInt(8));
                orderProfile.setFullPrice(resultSet.getInt(9));

                orderProfile.setClientId(resultSet.getInt(10));
                orderProfile.setClientFirstName(resultSet.getString(11));
                orderProfile.setClientPatronymic(resultSet.getString(12));
                orderProfile.setClientLastName(resultSet.getString(13));
                orderProfile.setCity(resultSet.getString(14));
                orderProfile.setClientEmail(resultSet.getString(15));
                orderProfile.setClientPhone(resultSet.getInt(16));

                orderProfile.setDealerId(resultSet.getInt(17));
                orderProfile.setDealerFirstName(resultSet.getString(18));
                orderProfile.setDealerPatronymic(resultSet.getString(19));
                orderProfile.setDealerLastName(resultSet.getString(20));
                orderProfile.setDealerEmail(resultSet.getString(21));
                orderProfile.setDealerPhone(resultSet.getInt(22));

                orderProfile.setBrand(resultSet.getString(23));
                orderProfile.setModel(resultSet.getString(24));
                orderProfile.setDate(resultSet.getInt(25));
                orderProfile.setMileage(resultSet.getInt(26));
                orderProfile.setVin(resultSet.getString(27));
                orderProfile.setCarNumber(resultSet.getString(28));
            }
        } catch (Exception e) {
            connection.close();
            throw e;
        }
        connection.close();
        return orderProfile;
    }

    public void assignOrderByUsername(Integer id, String username) throws SQLException {
        Connection connection = dataSource.getConnection();
        CallableStatement callableStatement = connection.prepareCall(StoredProcedureList.ASSIGN_ORDER);
        callableStatement.setInt(1, id);
        callableStatement.setString(2, username);
        callableStatement.execute();
        connection.close();
    }
}
