package ua.training.cruise_company_servlet.web.command.admin;

import ua.training.cruise_company_servlet.web.command.Command;
import ua.training.cruise_company_servlet.web.constant.PathConstants;
import ua.training.cruise_company_servlet.service.SeaportService;

import javax.servlet.http.HttpServletRequest;

public class AdminManageSeaportsCommand implements Command {

    @Override
    public String execute(HttpServletRequest request) {
        SeaportService seaportService = new SeaportService();
        request.setAttribute("all_ports", seaportService.getAllSeaports());
        return PathConstants.ADMIN_SEAPORTS_JSP;
    }
}
