package com.shevvvik.autos.services.entities;

import java.util.List;

public class DealerProfile {

    private String firstName;
    private String patronymic;
    private String lastName;
    private String email;
    private String phone;

    private Integer completedOrders;
    private Integer enteredOrders;
    private Integer cancelledOrders;

    private List<DealersOrder> orders;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getCompletedOrders() {
        return completedOrders;
    }

    public void setCompletedOrders(Integer completedOrders) {
        this.completedOrders = completedOrders;
    }

    public Integer getEnteredOrders() {
        return enteredOrders;
    }

    public void setEnteredOrders(Integer enteredOrders) {
        this.enteredOrders = enteredOrders;
    }

    public Integer getCancelledOrders() {
        return cancelledOrders;
    }

    public void setCancelledOrders(Integer cancelledOrders) {
        this.cancelledOrders = cancelledOrders;
    }

    public List<DealersOrder> getOrders() {
        return orders;
    }

    public void setOrders(List<DealersOrder> orders) {
        this.orders = orders;
    }
}
