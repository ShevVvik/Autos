package com.shevvvik.autos.web.forms;

import java.util.Date;

public class SearchOrdersForm {

    private String clientFirstName;
    private String clientPatronymic;
    private String clientLastName;
    private Integer city;

    private String dealerFirstName;
    private String dealerPatronymic;
    private String dealerLastName;

    private String brand;
    private String model;
    private Integer mileageMin;
    private Integer mileageMax;
    private Integer date;

    private Integer priceMin;
    private Integer priceMax;
    private String dateStartMin;
    private String dateStartMax;
    private Integer Status;

    public String getClientFirstName() {
        return clientFirstName;
    }

    public void setClientFirstName(String clientFirstName) {
        this.clientFirstName = clientFirstName;
    }

    public String getClientPatronymic() {
        return clientPatronymic;
    }

    public void setClientPatronymic(String clientPatronymic) {
        this.clientPatronymic = clientPatronymic;
    }

    public String getClientLastName() {
        return clientLastName;
    }

    public void setClientLastName(String clientLastName) {
        this.clientLastName = clientLastName;
    }

    public Integer getCity() {
        return city;
    }

    public void setCity(Integer city) {
        this.city = city;
    }

    public String getDealerFirstName() {
        return dealerFirstName;
    }

    public void setDealerFirstName(String dealerFirstName) {
        this.dealerFirstName = dealerFirstName;
    }

    public String getDealerPatronymic() {
        return dealerPatronymic;
    }

    public void setDealerPatronymic(String dealerPatronymic) {
        this.dealerPatronymic = dealerPatronymic;
    }

    public String getDealerLastName() {
        return dealerLastName;
    }

    public void setDealerLastName(String dealerLastName) {
        this.dealerLastName = dealerLastName;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Integer getMileageMin() {
        return mileageMin;
    }

    public void setMileageMin(Integer mileageMin) {
        this.mileageMin = mileageMin;
    }

    public Integer getMileageMax() {
        return mileageMax;
    }

    public void setMileageMax(Integer mileageMax) {
        this.mileageMax = mileageMax;
    }

    public Integer getDate() {
        return date;
    }

    public void setDate(Integer date) {
        this.date = date;
    }

    public Integer getPriceMin() {
        return priceMin;
    }

    public void setPriceMin(Integer priceMin) {
        this.priceMin = priceMin;
    }

    public Integer getPriceMax() {
        return priceMax;
    }

    public void setPriceMax(Integer priceMax) {
        this.priceMax = priceMax;
    }

    public String getDateStartMin() {
        return dateStartMin;
    }

    public void setDateStartMin(String dateStartMin) {
        this.dateStartMin = dateStartMin;
    }

    public String getDateStartMax() {
        return dateStartMax;
    }

    public void setDateStartMax(String dateStartMax) {
        this.dateStartMax = dateStartMax;
    }

    public Integer getStatus() {
        return Status;
    }

    public void setStatus(Integer status) {
        Status = status;
    }
}
