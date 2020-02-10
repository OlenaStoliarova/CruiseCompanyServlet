package ua.training.cruise_company_servlet.web.filter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.training.cruise_company_servlet.web.authentication.Authentication;
import ua.training.cruise_company_servlet.web.constant.PathConstants;
import ua.training.cruise_company_servlet.enums.UserRole;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AuthFilter implements Filter {
    private static final Logger LOG = LogManager.getLogger(AuthFilter.class);
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        final HttpServletRequest request = (HttpServletRequest) servletRequest;
        final HttpServletResponse response = (HttpServletResponse) servletResponse;

        String path = request.getRequestURI();
        path = path.toLowerCase();
        path = path.replaceAll(".*" + PathConstants.SERVLET_PATH , "");

        Authentication authentication = new Authentication(request.getSession());
        if( ! authentication.isLoggedIn() ) {
            if (isAuthenticationNeeded(path)) {
                LOG.warn("guest tried to access '" + path + "'");
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            } else {
                filterChain.doFilter(servletRequest, servletResponse);
            }
            return;
        }
        UserRole role = authentication.getUserRole();

        if(isPathAllowedForUserRole(path,role)){
            filterChain.doFilter(servletRequest,servletResponse);
            return;
        }
        String user = authentication.getUserName();
        LOG.error(user + "(" + role + ") tried to access forbidden path '" + path + "'");
        response.sendError(HttpServletResponse.SC_FORBIDDEN);
    }

    @Override
    public void destroy() {
    }

    private boolean isAuthenticationNeeded(String path){
        return  path.startsWith(PathConstants.ADMIN_FOLDER) ||
                path.startsWith(PathConstants.TOURIST_FOLDER) ||
                path.startsWith(PathConstants.TRAVEL_AGENT_FOLDER);
    }

    private boolean isPathAllowedForUserRole(String path, UserRole role){
        if(path.startsWith(PathConstants.LOGOUT_COMMAND) || path.startsWith(PathConstants.LOGIN_COMMAND) ||
                path.startsWith(PathConstants.MAIN_COMMAND))
            return true;

        return (role.equals(UserRole.ROLE_ADMIN) && path.startsWith(PathConstants.ADMIN_FOLDER)) ||
                (role.equals(UserRole.ROLE_TRAVEL_AGENT) && path.startsWith(PathConstants.TRAVEL_AGENT_FOLDER)) ||
                (role.equals(UserRole.ROLE_TOURIST) && path.startsWith(PathConstants.TOURIST_FOLDER));
    }
}
