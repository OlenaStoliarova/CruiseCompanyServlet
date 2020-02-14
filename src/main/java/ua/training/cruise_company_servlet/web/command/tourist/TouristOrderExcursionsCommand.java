package ua.training.cruise_company_servlet.web.command.tourist;

import ua.training.cruise_company_servlet.web.command.Command;
import ua.training.cruise_company_servlet.web.constant.PathConstants;

import javax.servlet.http.HttpServletRequest;

public class TouristOrderExcursionsCommand implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        return PathConstants.TOURIST_ORDER_EXCURSIONS_JSP;
    }
}
