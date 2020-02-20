package ua.cruise.company.web.command;

import ua.cruise.company.web.constant.PathConstants;

import javax.servlet.http.HttpServletRequest;

public class RegistrationSuccessCommand implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        return PathConstants.REGISTRATION_SUCCESS_JSP;
    }
}
