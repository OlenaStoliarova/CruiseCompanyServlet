package ua.cruise.company.web.command.tourist;

import ua.cruise.company.web.command.Command;
import ua.cruise.company.web.constant.PathConstants;

import javax.servlet.http.HttpServletRequest;

public class TouristPrintOrderCommand implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        return PathConstants.TOURIST_PRINT_ORDER_JSP;
    }
}
