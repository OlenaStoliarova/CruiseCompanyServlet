package ua.training.cruise_company_servlet.web.command;

import ua.training.cruise_company_servlet.enums.UserRole;
import ua.training.cruise_company_servlet.web.authentication.Authentication;
import ua.training.cruise_company_servlet.web.constant.PathConstants;

import javax.servlet.http.HttpServletRequest;

public class ToMainPageCommand implements Command{
    @Override
    public String execute(HttpServletRequest request) {
        UserRole curUserRole = new Authentication(request.getSession()).getUserRole();

        if(curUserRole == null)
            return PathConstants.REDIRECT_PREFIX + PathConstants.INDEX_PAGE_COMMAND;

        if( curUserRole.equals( UserRole.ROLE_ADMIN ))
            return PathConstants.REDIRECT_PREFIX + PathConstants.SERVLET_PATH + PathConstants.ADMIN_MAIN_COMMAND;
        if( curUserRole.equals( UserRole.ROLE_TRAVEL_AGENT))
            return PathConstants.REDIRECT_PREFIX + PathConstants.SERVLET_PATH + PathConstants.TRAVEL_AGENT_MAIN_COMMAND;
        if( curUserRole.equals( UserRole.ROLE_TOURIST))
            return PathConstants.REDIRECT_PREFIX + PathConstants.SERVLET_PATH + PathConstants.TOURIST_MAIN_COMMAND;

         return PathConstants.REDIRECT_PREFIX + PathConstants.INDEX_PAGE_COMMAND;
    }
}
