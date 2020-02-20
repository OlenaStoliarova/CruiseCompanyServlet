package ua.cruise.company.web.command.admin;

import ua.cruise.company.service.SeaportService;
import ua.cruise.company.web.command.Command;
import ua.cruise.company.web.constant.PathConstants;

import javax.servlet.http.HttpServletRequest;

public class AdminManageSeaportsCommand implements Command {

    @Override
    public String execute(HttpServletRequest request) {
        SeaportService seaportService = new SeaportService();
        request.setAttribute("all_ports", seaportService.getAllSeaports());
        return PathConstants.ADMIN_SEAPORTS_JSP;
    }
}
