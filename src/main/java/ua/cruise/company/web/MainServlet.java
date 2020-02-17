package ua.cruise.company.web;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import ua.cruise.company.web.command.*;
import ua.cruise.company.web.command.admin.*;
import ua.cruise.company.web.command.tourist.*;
import ua.cruise.company.web.command.travel_agent.*;
import ua.cruise.company.web.constant.PathConstants;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class MainServlet extends HttpServlet {
    private static final Logger LOG = LogManager.getLogger(MainServlet.class);

    private Map<String, Command> commands = new HashMap<>();

    public void init(ServletConfig servletConfig){
        commands.put(PathConstants.LOGIN_COMMAND, new LoginCommand());
        commands.put(PathConstants.LOGOUT_COMMAND, new LogoutCommand());
        commands.put(PathConstants.REGISTRATION_COMMAND, new RegistrationCommand());
        commands.put(PathConstants.REGISTRATION_SUCCESS_COMMAND, new RegistrationSuccessCommand());
        commands.put(PathConstants.MAIN_COMMAND, new ToMainPageCommand());
        commands.put(PathConstants.ADMIN_MAIN_COMMAND, new AdminMainPageCommand());
        commands.put(PathConstants.TRAVEL_AGENT_MAIN_COMMAND, new TravelAgentMainPageCommand());
        commands.put(PathConstants.TOURIST_MAIN_COMMAND, new TouristMainPageCommand());
        commands.put(PathConstants.TOURIST_CRUISES_COMMAND, new TouristCruisesCommand());
        commands.put(PathConstants.ADMIN_MANAGE_USERS_COMMAND, new AdminManageUsersCommand());
        commands.put(PathConstants.ADMIN_UPDATE_ROLE_COMMAND, new AdminUpdateRoleCommand());
        commands.put(PathConstants.ADMIN_MANAGE_SEAPORTS_COMMAND, new AdminManageSeaportsCommand());
        commands.put(PathConstants.ADMIN_ADD_SEAPORT_COMMAND, new AdminAddSeaportCommand());
        commands.put(PathConstants.TRAVEL_AGENT_MANAGE_EXCURSIONS_COMMAND, new TravelAgentManageExcursionsCommand());
        commands.put(PathConstants.TRAVEL_AGENT_ADD_EXCURSION_COMMAND, new TravelAgentAddExcursionCommand());
        commands.put(PathConstants.TRAVEL_AGENT_DELETE_EXCURSION_COMMAND, new TravelAgentDeleteExcursionCommand());
        commands.put(PathConstants.TRAVEL_AGENT_EDIT_EXCURSION_COMMAND, new TravelAgentEditExcursionCommand());
        commands.put(PathConstants.ADMIN_MANAGE_SHIPS_COMMAND, new AdminManageShipsCommand());
        commands.put(PathConstants.TOURIST_ORDER_CRUISE_COMMAND, new TouristOrderCruiseCommand());
        commands.put(PathConstants.TOURIST_MANAGE_ORDERS_COMMAND, new TouristManageOrdersCommand());
        commands.put(PathConstants.TOURIST_CANCEL_ORDER_COMMAND, new TouristCancelOrderCommand());
        commands.put(PathConstants.TOURIST_PAY_ORDER_COMMAND, new TouristPayOrderCommand());
        commands.put(PathConstants.TOURIST_ORDER_EXCURSIONS_COMMAND, new TouristOrderExcursionsCommand());
        commands.put(PathConstants.TOURIST_ADD_EXCURSIONS_TO_ORDER_COMMAND, new TouristAddExcursionsToOrderCommand());
        commands.put(PathConstants.TRAVEL_AGENT_MANAGE_ORDERS_COMMAND, new TravelAgentManageOrdersCommand());
        commands.put(PathConstants.TRAVEL_AGENT_ADD_EXTRAS_TO_ORDER_COMMAND, new TravelAgentAddExtrasToOrderCommand());
        commands.put(PathConstants.TOURIST_PRINT_ORDER_COMMAND, new TouristPrintOrderCommand());
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String path = request.getRequestURI();
        path = path.toLowerCase();
        path = path.replaceAll(".*" + PathConstants.SERVLET_PATH , "");
        Command command = commands.get(path);
        if(command == null){
            LOG.warn("non existing page request (" + path + ")");
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        LOG.info(command.getClass().getName());

        String page = command.execute(request);

        if( page.startsWith(PathConstants.REDIRECT_PREFIX)){
            page = page.replaceFirst(PathConstants.REDIRECT_PREFIX, "");
            response.sendRedirect(page);
        }
        else {
            request.getRequestDispatcher(page).forward(request, response);
        }
    }
}
