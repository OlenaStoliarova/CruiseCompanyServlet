package ua.training.cruise_company_servlet.controller.command;

import ua.training.cruise_company_servlet.controller.constant.PathConstants;
import ua.training.cruise_company_servlet.model.service.SeaportService;

import javax.servlet.http.HttpServletRequest;

public class AdminManageSeaportsCommand implements Command {

    @Override
    public String execute(HttpServletRequest request) {
        SeaportService seaportService = new SeaportService();
        request.setAttribute("all_ports", seaportService.getAllSeaports());
        return PathConstants.ADMIN_SEAPORTS_JSP;
    }
}
