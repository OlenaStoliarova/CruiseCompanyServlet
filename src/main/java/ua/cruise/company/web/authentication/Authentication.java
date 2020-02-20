package ua.cruise.company.web.authentication;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.cruise.company.enums.UserRole;
import ua.cruise.company.service.UserService;
import ua.cruise.company.service.exception.UserNotFoundException;
import ua.cruise.company.web.constant.AttributesConstants;
import ua.cruise.company.web.dto.UserDTO;

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
        UserDTO user = userService.checkUserOnLogin(login, password);

        if (!addUserToLoggedUsers(session.getServletContext(), login)) {
            throw new AlreadyLoggedInException();
        }

        UserPrincipal userToStoreInSession = new UserPrincipal(user.getId(), user.getEmail(), user.getRole());
        session.setAttribute(AttributesConstants.USER, userToStoreInSession);
        return user.getRole();
    }

    public boolean isLoggedIn() {
        return (session.getAttribute(AttributesConstants.USER) != null);
    }

    public UserRole getUserRole() {
        UserPrincipal userPrincipal = (UserPrincipal) session.getAttribute(AttributesConstants.USER);
        if (userPrincipal != null) {
            return userPrincipal.getRole();
        }
        return null;
    }

    public String getUserName() {
        UserPrincipal userPrincipal = (UserPrincipal) session.getAttribute(AttributesConstants.USER);
        if (userPrincipal != null) {
            return userPrincipal.getEmail();
        }
        return null;
    }

    public long getUserId() {
        UserPrincipal userPrincipal = (UserPrincipal) session.getAttribute(AttributesConstants.USER);
        if (userPrincipal != null) {
            return userPrincipal.getId();
        }
        return 0;
    }

    public void doLogout() {
        if (isLoggedIn()) {
            removeUserFromLoggedUsers(session.getServletContext(), getUserName());
            session.removeAttribute(AttributesConstants.USER);
        }
    }

    private boolean addUserToLoggedUsers(ServletContext context, String login) {
        Set<String> loggedUsers = (HashSet<String>) context.getAttribute(AttributesConstants.LOGGED_USERS);
        if (loggedUsers == null) {
            loggedUsers = new HashSet<>();
        }
        LOG.info("Logged users: " + loggedUsers);
        if (!loggedUsers.add(login)) {
            LOG.warn("User '" + login + "' is already logged in.");
            return false;
        }
        context.setAttribute(AttributesConstants.LOGGED_USERS, loggedUsers);
        LOG.info("User '" + login + "' was added to loggedUsers list");
        return true;
    }

    private void removeUserFromLoggedUsers(ServletContext context, String login) {
        Set<String> loggedUsers = (HashSet<String>) context.getAttribute(AttributesConstants.LOGGED_USERS);
        if (loggedUsers != null) {
            LOG.info("Logged users: " + loggedUsers);
            if (loggedUsers.remove(login))
                LOG.info("User '" + login + "' was removed from loggedUsers list");
            context.setAttribute(AttributesConstants.LOGGED_USERS, loggedUsers);
        }
    }
}
