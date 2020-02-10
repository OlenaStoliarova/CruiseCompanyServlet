package ua.training.cruise_company_servlet.controller.constant;

public interface PathConstants {
    String INDEX_PAGE_COMMAND = "";
    String SERVLET_PATH = "/app/";

    String LOGIN_COMMAND = "login";
    String LOGOUT_COMMAND = "logout";
    String LOGIN_JSP = "/login.jsp";

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

    String ADMIN_MANAGE_USERS_COMMAND = "admin/users";
    String ADMIN_USERS_JSP = "/WEB-INF/" + ADMIN_FOLDER + "/users.jsp";
    String ADMIN_UPDATE_ROLE_COMMAND = "admin/updateuserrole";

    String ADMIN_MANAGE_SEAPORTS_COMMAND = "admin/seaports";
    String ADMIN_SEAPORTS_JSP = "/WEB-INF/" + ADMIN_FOLDER + "/seaports.jsp";
    String ADMIN_ADD_SEAPORT_COMMAND = "admin/addport";

    String TRAVEL_AGENT_MANAGE_EXCURSIONS_COMMAND = "travel_agent/excursions";
    String TRAVEL_AGENT_EXCURSIONS_JSP = "/WEB-INF/" + TRAVEL_AGENT_FOLDER + "/excursions.jsp";
    String TRAVEL_AGENT_ADD_EXCURSION_COMMAND = "travel_agent/add_excursion";
    String TRAVEL_AGENT_ADD_EXCURSION_JSP = "/WEB-INF/" + TRAVEL_AGENT_FOLDER + "/addExcursion.jsp";
    String TRAVEL_AGENT_DELETE_EXCURSION_COMMAND = "travel_agent/delete_excursion";
    String TRAVEL_AGENT_EDIT_EXCURSION_COMMAND = "travel_agent/edit_excursion";
    String TRAVEL_AGENT_EDIT_EXCURSION_JSP = "/WEB-INF/" + TRAVEL_AGENT_FOLDER + "/editExcursion.jsp";

}
