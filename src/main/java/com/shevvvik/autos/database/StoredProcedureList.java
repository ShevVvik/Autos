package com.shevvvik.autos.database;

public class StoredProcedureList {

    public static final String REGISTRATION_QUERY = "{call registr(?, ?, ?, ?, ?, ?, ?, ?, ?) }";
    public static final String CITIES_QUERY = "{ call getCities() }";
    public static final String GET_CLIENT_PROFILE = "{ call getClientProfileByUsername(?) }";
    public static final String GET_CLIENT_PROFILE_BY_ID = "{ call getClientProfileById(?) }";
    public static final String GET_OFFERS_BY_CLIENT_ID = "{ call getOffersByClientId(?) }";
    public static final String GET_OFFERS_BY_DEALER_ID = "{ call getOffersByDealerId(?) }";
    public static final String CREATE_ORDER_BY_CLIENT = "{ call createOrder(?, ?, ?, ?, ?, ?, ?, ?) }";

    public static final String CANCEL_ORDER = "{ call cancelOrder(?) }";

    public static final String GET_DEALER_PROFILE_BY_USERNAME = "{ call getDealerProfileByUsername(?) }";
    public static final String GET_DEALER_PROFILE_BY_ID = "{ call getDealerProfileById(?) }";

    public static final String SEARCH_CLIENTS = "{ call searchClients(?, ?, ?, ?, ?, ?, ?) }";
    public static final String SEARCH_DEALERS = "{ call searchDealers(?, ?, ?, ?, ?, ?) }";
    public static final String SEARCH_OFFERS = "{ call searchOffers(?, ?, ?, ?, ?, ?, ?, ?, ?, ?) }";
    public static final String SEARCH_DETAILED = "{ call searchOffersDetailed(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) }";

    public static final String GET_ORDER_PROFILE = "{ call getOrderProfileById(?) }";
    public static final String ASSIGN_ORDER = "{ call assignOrder(?, ?) }";
    public static final String CHANGE_ORDER_STATUS = "{ call changeOrderStatus(?, ?) }";

    public static final String GET_ORDER_STATUS = "{ ? = call getOrderStatus(?) }";
    public static final String CHECK_LOGIN = "{ ? = call checkLogin(?) }";

    public static final String ADD_COMMENT = "{ call addCommentToOrder(?, ?, ?) }";
    public static final String GET_ALL_COMMENTS_FOR_OFFER = "{ call getCommentsByOffer(?) }";
}
