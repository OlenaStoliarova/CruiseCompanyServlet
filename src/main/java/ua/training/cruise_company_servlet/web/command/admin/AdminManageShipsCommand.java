package ua.training.cruise_company_servlet.web.command.admin;

import ua.training.cruise_company_servlet.service.ShipService;
import ua.training.cruise_company_servlet.web.command.Command;
import ua.training.cruise_company_servlet.web.constant.AttributesConstants;
import ua.training.cruise_company_servlet.web.constant.PathConstants;

import javax.servlet.http.HttpServletRequest;

public class AdminManageShipsCommand implements Command {

    @Override
    public String execute(HttpServletRequest request) {
        ShipService shipService = new ShipService();
        request.setAttribute(AttributesConstants.ALL_SHIPS, shipService.getAllShips());
        return PathConstants.ADMIN_SHIPS_JSP;
    }
}
