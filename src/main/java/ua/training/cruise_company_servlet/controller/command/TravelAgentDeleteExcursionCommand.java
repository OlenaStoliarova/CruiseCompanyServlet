package ua.training.cruise_company_servlet.controller.command;

import ua.training.cruise_company_servlet.controller.constant.AttributesConstants;
import ua.training.cruise_company_servlet.controller.constant.PathConstants;
import ua.training.cruise_company_servlet.model.service.ExcursionService;

import javax.servlet.http.HttpServletRequest;

public class TravelAgentDeleteExcursionCommand implements Command {
    private final ExcursionService excursionService = new ExcursionService();

    @Override
    public String execute(HttpServletRequest request) {
        Long excursionId = Long.valueOf(request.getParameter(AttributesConstants.EXCURSION_ID));
        String param;
        if( excursionService.deleteExcursion(excursionId)){
            param = "?deleted=true";
        } else {
            param = "?deleted=false";
        }
        return "redirect:" + PathConstants.SERVLET_PATH + PathConstants.TRAVEL_AGENT_MANAGE_EXCURSIONS_COMMAND + param;
    }
}
