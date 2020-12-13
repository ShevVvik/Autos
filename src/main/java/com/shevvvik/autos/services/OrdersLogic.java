package com.shevvvik.autos.services;

import com.shevvvik.autos.database.StatusConstants;
import com.shevvvik.autos.database.connection.JDBCOrders;
import com.shevvvik.autos.services.entities.OrderProfile;
import com.shevvvik.autos.web.forms.OrderForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.sql.SQLException;

@Service
public class OrdersLogic {

    @Autowired
    private JDBCOrders jdbcOrders;

    public void cancelOrder(String id) {
        Integer orderId = Integer.valueOf(id);
        try {
            jdbcOrders.cancelOrder(orderId);
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    public void createOrder(OrderForm orderForm) {

        String username = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();
        String date = orderForm.getDate() + "-01-01";
        orderForm.setDate(date);
        try {
            jdbcOrders.createOrder(orderForm, username);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public OrderProfile getOrderProfile(String id) {
        OrderProfile orderProfile = new OrderProfile();
        try {
            orderProfile = jdbcOrders.getOrderProfileById(Integer.valueOf(id));
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return orderProfile;
    }

    public void assignOrder(String id) {
        String username = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();
        try {
            jdbcOrders.assignOrderByUsername(Integer.valueOf(id), username);
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

    }

    public void completeOrder(String id) {
        try {
            jdbcOrders.changeStatus(Integer.valueOf(id), StatusConstants.STATUS_COMPLETED);
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    public void startOrder(String id) {
        try {
            jdbcOrders.changeStatus(Integer.valueOf(id), StatusConstants.STATUS_IN_PROGRESS);
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }
}
