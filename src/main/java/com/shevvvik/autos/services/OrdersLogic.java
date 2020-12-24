package com.shevvvik.autos.services;

import com.shevvvik.autos.configuration.exceptions.ObjectNotFound;
import com.shevvvik.autos.database.StatusConstants;
import com.shevvvik.autos.database.connection.JDBCOrders;
import com.shevvvik.autos.services.entities.OrderProfile;
import com.shevvvik.autos.services.logger.Logger;
import com.shevvvik.autos.services.logger.LoggerConstants;
import com.shevvvik.autos.web.forms.CommentForm;
import com.shevvvik.autos.web.forms.OrderForm;
import com.shevvvik.autos.web.forms.PriceForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.sql.SQLException;

@Service
public class OrdersLogic {

    @Autowired
    private JDBCOrders jdbcOrders;

    @Autowired
    private Logger logger;

    public void cancelOrder(String id) {
        Integer orderId = Integer.valueOf(id);
        try {
            jdbcOrders.cancelOrder(orderId);
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        logger.log(LoggerConstants.CHANGE_ORDER_STATUS_OPERATION, "Order - {?} was cancelled", id);
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
        logger.log(LoggerConstants.CREATE_ORDER_OPERATION, "New order was created");
    }


    public OrderProfile getOrderProfile(String id) {
        OrderProfile orderProfile = new OrderProfile();
        try {
            orderProfile = jdbcOrders.getOrderProfileById(Integer.valueOf(id));
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        logger.log(LoggerConstants.DEFAULT_OPERATION, "Profile for order ({?}) was opened", id);
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
        logger.log(LoggerConstants.ASSIGN_ORDER_OPERATION, "Order - {?} was assigned to new dealer", id);
    }

    public void completeOrder(String id) {
        try {
            jdbcOrders.changeStatus(Integer.valueOf(id), StatusConstants.STATUS_COMPLETED);
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        logger.log(LoggerConstants.CHANGE_ORDER_STATUS_OPERATION, "Order - {?} was completed", id);
    }

    public void startOrder(String id) {
        try {
            jdbcOrders.changeStatus(Integer.valueOf(id), StatusConstants.STATUS_IN_PROGRESS);
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        logger.log(LoggerConstants.CHANGE_ORDER_STATUS_OPERATION, "Order - {?} was started (Status in progress)", id);
    }

    public void addComment(CommentForm commentForm) {
        String username = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();
        commentForm.setUsername(username);

        try {
            jdbcOrders.addComment(commentForm);
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        logger.log(LoggerConstants.DEFAULT_OPERATION, "New comment was added to order - {?}", commentForm.getOrderId().toString());
    }

    public void changePrice(PriceForm priceForm) {
        String username = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();
        try {
            jdbcOrders.changePrice(priceForm);
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        logger.log(LoggerConstants.DEFAULT_OPERATION, "Change price to order - {?}", priceForm.getOrderId().toString());
    }
}
