package ua.training.cruise_company_servlet.web.command;

import ua.training.cruise_company_servlet.web.constant.PathConstants;

import javax.servlet.http.HttpServletRequest;

public class RegistrationSuccessCommand implements Command{
    @Override
    public String execute(HttpServletRequest request) {
        return PathConstants.REGISTRATION_SUCCESS_JSP;
    }
}
