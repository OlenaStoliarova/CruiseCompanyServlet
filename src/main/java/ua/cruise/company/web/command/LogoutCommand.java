package ua.cruise.company.web.command;

import ua.cruise.company.web.authentication.Authentication;
import ua.cruise.company.web.constant.PathConstants;

import javax.servlet.http.HttpServletRequest;

public class LogoutCommand implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        new Authentication(request.getSession()).doLogout();
        return PathConstants.REDIRECT_PREFIX + PathConstants.INDEX_PAGE_COMMAND;
    }
}
