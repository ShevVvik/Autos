package com.shevvvik.autos.services.entities;

public class SearchDealerEntity {

    private Integer id;
    private String firstName;
    private String patronymic;
    private String lastName;
    private Integer phone;
    private String email;
    private Integer enteredOrders;
    private Integer inProgressOrders;
    private Integer completedOrders;
    private Integer cancelledOrders;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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

    public Integer getPhone() {
        return phone;
    }

    public void setPhone(Integer phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getEnteredOrders() {
        return enteredOrders;
    }

    public void setEnteredOrders(Integer enteredOrders) {
        this.enteredOrders = enteredOrders;
    }

    public Integer getInProgressOrders() {
        return inProgressOrders;
    }

    public void setInProgressOrders(Integer inProgressOrders) {
        this.inProgressOrders = inProgressOrders;
    }

    public Integer getCompletedOrders() {
        return completedOrders;
    }

    public void setCompletedOrders(Integer completedOrders) {
        this.completedOrders = completedOrders;
    }

    public Integer getCancelledOrders() {
        return cancelledOrders;
    }

    public void setCancelledOrders(Integer cancelledOrders) {
        this.cancelledOrders = cancelledOrders;
    }
}
