package ua.training.cruise_company_servlet.controller.authentication;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.training.cruise_company_servlet.controller.constant.AttributesConstants;
import ua.training.cruise_company_servlet.model.enums.UserRole;
import ua.training.cruise_company_servlet.model.service.UserNotFoundException;
import ua.training.cruise_company_servlet.model.service.UserService;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import java.util.HashSet;
import java.util.Set;

public class Authentication {
    private static final Logger LOG = LogManager.getLogger(Authentication.class);

    private final HttpSession session;

    public Authentication(HttpSession session) {
        this.session = session;
    }

    public UserRole doLogin(String login, String password) throws UserNotFoundException, AlreadyLoggedInException {
        UserService userService = new UserService();
        UserRole userRole = userService.checkUserOnLogin(login,password);

        if( ! addUserToLoggedUsers(session.getServletContext(), login)){
            throw new AlreadyLoggedInException();
        }
        session.setAttribute(AttributesConstants.USER_ROLE, userRole);
        session.setAttribute(AttributesConstants.USER_NAME, login);
        return userRole;
    }

    public boolean isLoggedIn(){
        return  ( session.getAttribute(AttributesConstants.USER_ROLE) != null ) &&
                (session.getAttribute(AttributesConstants.USER_NAME) != null );
    }

    public UserRole getUserRole(){
        return (UserRole) session.getAttribute(AttributesConstants.USER_ROLE);
    }

    public String getUserName(){ return (String) session.getAttribute(AttributesConstants.USER_NAME);}


    public void doLogout(){
        String user = (String) session.getAttribute(AttributesConstants.USER_NAME);
        removeUserFromLoggedUsers(session.getServletContext(), user);
        session.removeAttribute(AttributesConstants.USER_NAME);
        session.removeAttribute(AttributesConstants.USER_ROLE);
    }

    private boolean addUserToLoggedUsers(ServletContext context, String login){
        Set<String> loggedUsers =  (HashSet<String>) context.getAttribute(AttributesConstants.LOGGED_USERS);
        if(loggedUsers == null) {
            loggedUsers = new HashSet<>();
        }
        LOG.info("Logged users: " + loggedUsers);
        if( !loggedUsers.add(login)){
            LOG.warn("User '" + login + "' is already logged in.");
            return false;
        }
        context.setAttribute(AttributesConstants.LOGGED_USERS, loggedUsers);
        LOG.info("User '" + login + "' was added to loggedUsers list");
        return true;
    }

    private void removeUserFromLoggedUsers(ServletContext context, String login){
        Set<String> loggedUsers = (HashSet<String>) context.getAttribute(AttributesConstants.LOGGED_USERS);
        if(loggedUsers != null) {
            LOG.info("Logged users: " + loggedUsers);
            if(loggedUsers.remove(login))
                LOG.info("User '" + login + "' was removed from loggedUsers list");
            context.setAttribute(AttributesConstants.LOGGED_USERS, loggedUsers);
        }
    }
}
