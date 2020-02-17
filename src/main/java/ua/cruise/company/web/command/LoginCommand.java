package ua.cruise.company.web.command;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.cruise.company.web.authentication.AlreadyLoggedInException;
import ua.cruise.company.web.authentication.Authentication;
import ua.cruise.company.web.constant.AttributesConstants;
import ua.cruise.company.web.constant.PathConstants;
import ua.cruise.company.enums.UserRole;
import ua.cruise.company.service.exception.UserNotFoundException;

import javax.servlet.http.HttpServletRequest;

public class LoginCommand implements Command {
    private static final Logger LOG = LogManager.getLogger(LoginCommand.class);

    @Override
    public String execute(HttpServletRequest request) {
        String login = request.getParameter(AttributesConstants.LOGIN);
        String password = request.getParameter(AttributesConstants.PASSWORD);
        Authentication authentication = new Authentication(request.getSession());

        if( login == null || login.equals("") || password == null || password.equals("")  ){
            authentication.doLogout();
            return PathConstants.LOGIN_JSP;
        }
        LOG.info( login + ", " + password);

        UserRole userRole;
        try {
            userRole = authentication.doLogin(login,password);
        }
        catch (UserNotFoundException e) {
            request.setAttribute(AttributesConstants.ERROR_UNKNOWN_USER, true);
            return PathConstants.LOGIN_JSP;
        } catch (AlreadyLoggedInException e) {
            request.setAttribute(AttributesConstants.ERROR_ALREADY_LOGGED_IN, true);
            return PathConstants.LOGIN_JSP;
        }

        if( userRole.equals( UserRole.ROLE_ADMIN ))
            return PathConstants.REDIRECT_PREFIX + PathConstants.SERVLET_PATH + PathConstants.ADMIN_MAIN_COMMAND;
        if( userRole.equals( UserRole.ROLE_TRAVEL_AGENT))
            return PathConstants.REDIRECT_PREFIX + PathConstants.SERVLET_PATH + PathConstants.TRAVEL_AGENT_MAIN_COMMAND;
        if( userRole.equals( UserRole.ROLE_TOURIST))
            return PathConstants.REDIRECT_PREFIX + PathConstants.SERVLET_PATH + PathConstants.TOURIST_MAIN_COMMAND;

        return PathConstants.REDIRECT_PREFIX + PathConstants.INDEX_PAGE_COMMAND;
    }
}
