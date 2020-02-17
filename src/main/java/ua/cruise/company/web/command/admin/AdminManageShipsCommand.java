package ua.cruise.company.web.command.admin;

import ua.cruise.company.service.ShipService;
import ua.cruise.company.web.command.Command;
import ua.cruise.company.web.constant.AttributesConstants;
import ua.cruise.company.web.constant.PathConstants;

import javax.servlet.http.HttpServletRequest;

public class AdminManageShipsCommand implements Command {

    @Override
    public String execute(HttpServletRequest request) {
        ShipService shipService = new ShipService();
        request.setAttribute(AttributesConstants.ALL_SHIPS, shipService.getAllShips());
        return PathConstants.ADMIN_SHIPS_JSP;
    }
}
