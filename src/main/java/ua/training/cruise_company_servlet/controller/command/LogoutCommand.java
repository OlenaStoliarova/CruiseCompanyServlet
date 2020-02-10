package ua.training.cruise_company_servlet.controller.command;

import ua.training.cruise_company_servlet.controller.authentication.Authentication;
import ua.training.cruise_company_servlet.controller.constant.PathConstants;

import javax.servlet.http.HttpServletRequest;

public class LogoutCommand implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        new Authentication(request.getSession()).doLogout();
        return "redirect:/" + PathConstants.INDEX_PAGE_COMMAND;
    }
}
