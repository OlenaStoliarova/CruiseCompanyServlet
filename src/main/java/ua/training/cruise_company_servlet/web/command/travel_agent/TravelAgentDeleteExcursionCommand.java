package ua.training.cruise_company_servlet.web.command.travel_agent;

import ua.training.cruise_company_servlet.web.command.Command;
import ua.training.cruise_company_servlet.web.constant.AttributesConstants;
import ua.training.cruise_company_servlet.web.constant.PathConstants;
import ua.training.cruise_company_servlet.service.ExcursionService;

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
        return PathConstants.REDIRECT_PREFIX + PathConstants.SERVLET_PATH + PathConstants.TRAVEL_AGENT_MANAGE_EXCURSIONS_COMMAND + param;
    }
}
