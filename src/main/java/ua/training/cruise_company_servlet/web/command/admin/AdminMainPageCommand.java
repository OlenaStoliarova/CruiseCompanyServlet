package ua.training.cruise_company_servlet.web.command.admin;

import ua.training.cruise_company_servlet.web.command.Command;
import ua.training.cruise_company_servlet.web.constant.PathConstants;

import javax.servlet.http.HttpServletRequest;

public class AdminMainPageCommand implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        return PathConstants.ADMIN_MAIN_JSP;
    }
}
