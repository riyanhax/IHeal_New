package company.tap.gosellapi.api.facade;

final class API_Constants {
    static final String BASE_URL = "https://api.tap.company/v1.1/";
    static final String AUTH_TOKEN_KEY = "Authorization";
    static final String AUTH_TOKEN_PREFIX = "Bearer ";
    static final String CONTENT_TYPE_KEY = "content-type";
    static final String CONTENT_TYPE_VALUE = "application/json";

    //url parts
    static final String TOKEN = "token";
    static final String TOKENS = "tokens";
    static final String TOKEN_ID = "token_id";

    static final String CUSTOMERS = "customers";
    static final String CUSTOMER_ID = "customer_id";

    static final String CARD = "card";
    static final String CARD_ID = "card_id";

    static final String CHARGES = "charges";
    static final String CHARGE_ID = "charge_id";

    static final String BIN = "bin";
    static final String NUMBER = "number";
}