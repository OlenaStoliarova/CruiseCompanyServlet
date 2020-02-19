package ua.cruise.company.web.constant;

public interface PathConstants {
    String REDIRECT_PREFIX = "redirect:";
    String INDEX_PAGE_COMMAND = "/";
    String SERVLET_PATH = "/app/";

    String LOGIN_COMMAND = "login";
    String LOGOUT_COMMAND = "logout";
    String LOGIN_JSP = "/WEB-INF/login.jsp";
    String REGISTRATION_COMMAND = "registration";
    String REGISTRATION_JSP = "/WEB-INF/registration.jsp";
    String REGISTRATION_SUCCESS_COMMAND = "registration_success";
    String REGISTRATION_SUCCESS_JSP = "/WEB-INF/registrationSuccess.jsp";

    String ADMIN_FOLDER = "admin";
    String TRAVEL_AGENT_FOLDER = "travel_agent";
    String TOURIST_FOLDER = "tourist";

    String MAIN_COMMAND = "main";
    String ADMIN_MAIN_COMMAND = ADMIN_FOLDER + "/main";
    String TRAVEL_AGENT_MAIN_COMMAND = TRAVEL_AGENT_FOLDER + "/main";
    String TOURIST_MAIN_COMMAND = TOURIST_FOLDER + "/main";
    String ADMIN_MAIN_JSP = "/WEB-INF/" + ADMIN_FOLDER + "/adminMain.jsp";
    String TRAVEL_AGENT_MAIN_JSP = "/WEB-INF/" + TRAVEL_AGENT_FOLDER + "/travelAgentMain.jsp";
    String TOURIST_MAIN_JSP = "/WEB-INF/" + TOURIST_FOLDER + "/touristMain.jsp";

    String TOURIST_CRUISES_COMMAND = "tourist/cruises";
    String TOURIST_CRUISES_JSP = "/WEB-INF/" + TOURIST_FOLDER + "/cruises.jsp";
    String TOURIST_ORDER_CRUISE_COMMAND = "tourist/order/cruise";
    String TOURIST_ORDER_CRUISE_JSP = "/WEB-INF/" + TOURIST_FOLDER + "/orderCruise.jsp";
    String TOURIST_MANAGE_ORDERS_COMMAND = "tourist/my_orders";
    String TOURIST_MANAGE_ORDERS_JSP = "/WEB-INF/" + TOURIST_FOLDER + "/myOrders.jsp";
    String TOURIST_CANCEL_ORDER_COMMAND = "tourist/cancel_order";
    String TOURIST_PAY_ORDER_COMMAND = "tourist/pay_order";
    String TOURIST_ORDER_EXCURSIONS_COMMAND = "tourist/order/excursions";
    String TOURIST_ORDER_EXCURSIONS_JSP = "/WEB-INF/" + TOURIST_FOLDER + "/orderExcursions.jsp";
    String TOURIST_ADD_EXCURSIONS_TO_ORDER_COMMAND = "tourist/order/add_excursions";
    String TOURIST_PRINT_ORDER_COMMAND = "tourist/print_order";
    String TOURIST_PRINT_ORDER_JSP = "/WEB-INF/" + TOURIST_FOLDER + "/printOrder.jsp";

    String ADMIN_MANAGE_USERS_COMMAND = "admin/users";
    String ADMIN_USERS_JSP = "/WEB-INF/" + ADMIN_FOLDER + "/users.jsp";
    String ADMIN_UPDATE_ROLE_COMMAND = "admin/updateuserrole";

    String ADMIN_MANAGE_SEAPORTS_COMMAND = "admin/seaports";
    String ADMIN_SEAPORTS_JSP = "/WEB-INF/" + ADMIN_FOLDER + "/seaports.jsp";
    String ADMIN_ADD_SEAPORT_COMMAND = "admin/addport";

    String ADMIN_MANAGE_SHIPS_COMMAND = "admin/ships";
    String ADMIN_SHIPS_JSP = "/WEB-INF/" + ADMIN_FOLDER + "/ships.jsp";

    String TRAVEL_AGENT_MANAGE_EXCURSIONS_COMMAND = "travel_agent/excursions";
    String TRAVEL_AGENT_EXCURSIONS_JSP = "/WEB-INF/" + TRAVEL_AGENT_FOLDER + "/excursions.jsp";
    String TRAVEL_AGENT_ADD_EXCURSION_COMMAND = "travel_agent/add_excursion";
    String TRAVEL_AGENT_ADD_EXCURSION_JSP = "/WEB-INF/" + TRAVEL_AGENT_FOLDER + "/addExcursion.jsp";
    String TRAVEL_AGENT_DELETE_EXCURSION_COMMAND = "travel_agent/delete_excursion";
    String TRAVEL_AGENT_EDIT_EXCURSION_COMMAND = "travel_agent/edit_excursion";
    String TRAVEL_AGENT_EDIT_EXCURSION_JSP = "/WEB-INF/" + TRAVEL_AGENT_FOLDER + "/editExcursion.jsp";

    String TRAVEL_AGENT_MANAGE_ORDERS_COMMAND = "travel_agent/orders";
    String TRAVEL_AGENT_MANAGE_ORDERS_JSP = "/WEB-INF/" + TRAVEL_AGENT_FOLDER + "/orders.jsp";
    String TRAVEL_AGENT_ADD_EXTRAS_TO_ORDER_COMMAND = "travel_agent/order/add_bonuses";
    String TRAVEL_AGENT_ADD_EXTRAS_TO_ORDER_JSP = "/WEB-INF/" + TRAVEL_AGENT_FOLDER + "/addExtrasToOrder.jsp";
}
