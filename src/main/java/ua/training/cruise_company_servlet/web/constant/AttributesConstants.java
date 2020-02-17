package ua.training.cruise_company_servlet.web.constant;

public interface AttributesConstants {
    String USER = "session_user";
    String LOGGED_USERS = "logged_users";

    String LOGIN = "username";
    String PASSWORD = "password";
    String ERROR_UNKNOWN_USER = "unknown_user";
    String ERROR_ALREADY_LOGGED_IN = "already_loggedin";

    String REGISTRATION_EMAIL = "email";
    String REGISTRATION_PASSWORD = "password";
    String REGISTRATION_REPEAT_PASSWORD = "repeatPassword";
    String REGISTRATION_FIRST_NAME_EN = "nameEn";
    String REGISTRATION_LAST_NAME_EN = "lastnameEn";
    String REGISTRATION_FIRST_NAME_NATIVE = "nameNative";
    String REGISTRATION_LAST_NAME_NATIVE = "lastnameNative";
    String REGISTRATION_USER = "registration_user";
    String ERROR_REGISTARTION_ERROR = "registration_error";

    String PAGE = "page";
    String SIZE = "size";
    String PAGINATION_TOTAL_PAGES = "total_pages";
    String PAGINATION_PAGE_SIZE = "page_size";
    String PAGINATION_CURRENT_PAGE = "current_page";

    String PORT_NAME_EN = "nameEn";
    String PORT_NAME_UKR = "nameUkr";
    String PORT_COUNTRY_EN = "countryEn";
    String PORT_COUNTRY_UKR = "countryUkr";

    String ALL_SEAPORTS = "all_seaports";
    String ALL_SHIPS = "all_ships";
    String ALL_EXCURSIONS = "all_excursions";
    String EXCURSION_OBJECT = "excursion";
    String EXCURSION_NAME_EN = "nameEn";
    String EXCURSION_NAME_UKR = "nameUkr";
    String EXCURSION_DESCRIPTION_EN = "descriptionEn";
    String EXCURSION_DESCRIPTION_UKR = "descriptionUkr";
    String EXCURSION_DURATION = "approximateDurationHr";
    String EXCURSION_PRICE = "priceUSD";
    String EXCURSION_PORT = "seaportId";
    String ERROR_VALIDATION = "validation_errors";
    String ERROR_NO_PORT_FOUND = "no_port_found";
    String ERROR_NON_UNIQUE = "non_unique";
    String EXCURSION_ID = "excursionId";
    String ERROR_NO_EXCURSION_FOUND = "no_excursion_found";

    String ALL_CRUISES = "all_cruises";
    String CRUISE_ID = "cruiseId";
    String ERROR_NO_CRUISE_FOUND = "no_cruise_found";
    String CRUISE_OBJECT = "cruise";
    String CRUISE_ORDER_QUANTITY = "quantity";
    String ERROR_BOOKING_CRUISE = "booking_error";
    String ORDERS = "orders";
    String ORDER_ID = "orderId";
    String ORDER_EXCURSIONS = "excursions";
    String CHOSEN_EXCURSIONS = "chosenExcursions";
    String ORDER_OBJECT = "order";
    String BONUSES = "bonuses";
    String ERROR_NO_ORDER_FOUND = "no_order_found";
    String UNTIMELY_OPERATION = "untimely_operation";
    String ILLEGAL_REQUEST = "illegal_request";
    String CHOSEN_BONUSES = "chosenBonuses";
}
