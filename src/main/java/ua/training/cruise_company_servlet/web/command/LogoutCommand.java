package ua.training.cruise_company_servlet.web.command;

import ua.training.cruise_company_servlet.web.authentication.Authentication;
import ua.training.cruise_company_servlet.web.constant.PathConstants;

import javax.servlet.http.HttpServletRequest;

public class LogoutCommand implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        new Authentication(request.getSession()).doLogout();
        return "redirect:/" + PathConstants.INDEX_PAGE_COMMAND;
    }
}
