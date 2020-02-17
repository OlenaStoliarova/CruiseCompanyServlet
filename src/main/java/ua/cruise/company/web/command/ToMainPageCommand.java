package ua.cruise.company.web.command;

import ua.cruise.company.enums.UserRole;
import ua.cruise.company.web.authentication.Authentication;
import ua.cruise.company.web.constant.PathConstants;

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
