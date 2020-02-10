package ua.training.cruise_company_servlet.controller.command;

import ua.training.cruise_company_servlet.controller.constant.PathConstants;

import javax.servlet.http.HttpServletRequest;

public class AdminMainPageCommand implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        return PathConstants.ADMIN_MAIN_JSP;
    }
}
