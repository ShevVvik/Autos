package com.shevvvik.autos.database.connection;

import com.shevvvik.autos.database.StoredProcedureList;
import com.shevvvik.autos.services.entities.CommentEntity;
import com.shevvvik.autos.services.entities.OrderProfile;
import com.shevvvik.autos.web.forms.CommentForm;
import com.shevvvik.autos.web.forms.OrderForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

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
                orderProfile.setDealer(resultSet.getString(3) != null ? resultSet.getString(3) : " ");
                orderProfile.setDateStart(resultSet.getDate(4).toString());
                orderProfile.setDateEnd(resultSet.getDate(5) != null
                        ? (resultSet.getDate(5).toString()) : " ");
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

                orderProfile.setComments(getComments(orderProfile.getId()));
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

    public void changeStatus(Integer id, Integer status) throws SQLException {
        Connection connection = dataSource.getConnection();
        CallableStatement callableStatement = connection.prepareCall(StoredProcedureList.CHANGE_ORDER_STATUS);
        callableStatement.setInt(1, id);
        callableStatement.setInt(2, status);
        callableStatement.execute();
        connection.close();
    }

    public void addComment(CommentForm commentForm) throws SQLException {
        Connection connection = dataSource.getConnection();
        CallableStatement callableStatement = connection.prepareCall(StoredProcedureList.ADD_COMMENT);
        callableStatement.setString(1, commentForm.getUsername());
        callableStatement.setInt(2, commentForm.getOrderId());
        callableStatement.setString(3, commentForm.getText());
        callableStatement.execute();
        connection.close();
    }

    public List<CommentEntity> getComments(Integer id) throws SQLException {
        List<CommentEntity> list = new ArrayList<>();
        Connection connection = dataSource.getConnection();
        CallableStatement callableStatement = connection.prepareCall(StoredProcedureList.GET_ALL_COMMENTS_FOR_OFFER);
        callableStatement.setInt(1, id);

        try(ResultSet resultSet = callableStatement.executeQuery()) {
            while (resultSet.next()) {
                CommentEntity commentEntity = new CommentEntity();
                commentEntity.setUser(resultSet.getString(1));
                commentEntity.setMessage(resultSet.getString(2));
                String pattern = "dd-M-yyyy hh:mm:ss";
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern, new Locale("en", "US"));
                String date = simpleDateFormat.format(resultSet.getDate(3));
                commentEntity.setDate(date);
                list.add(commentEntity);
            }
        }

        connection.close();
        return list;
    }
}
