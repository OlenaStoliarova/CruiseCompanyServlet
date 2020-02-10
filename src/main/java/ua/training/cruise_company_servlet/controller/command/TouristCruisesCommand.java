package ua.training.cruise_company_servlet.controller.command;

import ua.training.cruise_company_servlet.controller.constant.PathConstants;

import javax.servlet.http.HttpServletRequest;

public class TouristCruisesCommand implements Command{
    @Override
    public String execute(HttpServletRequest request) {
        return PathConstants.TOURIST_CRUISES_JSP;
    }
}
