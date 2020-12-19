package com.shevvvik.autos.services.logger;

public enum LoggerConstants {

    DEFAULT_OPERATION(1),
    SEARCH_OPERATION(2),
    CHANGE_ORDER_STATUS_OPERATION(3),
    ASSIGN_ORDER_OPERATION(4),
    LOG_IN_OPERATION(5),
    REGISTRATION_OPERATION(6),
    CREATE_ORDER_OPERATION(7);

    private Integer value;
    LoggerConstants(Integer value){
        this.value = value;
    }
    public Integer getValue(){ return value;}
}
