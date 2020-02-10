package ua.training.cruise_company_servlet.web.command.travel_agent;

import ua.training.cruise_company_servlet.web.command.Command;
import ua.training.cruise_company_servlet.web.constant.PathConstants;
import ua.training.cruise_company_servlet.service.ExcursionService;
import ua.training.cruise_company_servlet.service.SeaportService;

import javax.servlet.http.HttpServletRequest;

public class TravelAgentManageExcursionsCommand implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        SeaportService seaportService = new SeaportService();
        ExcursionService excursionService = new ExcursionService();
        request.setAttribute("all_seaports", seaportService.getAllSeaportsLocalizedSorted());

        String strSeaportId = request.getParameter("seaportId");
        if(strSeaportId != null){
            long seaportId = Long.parseLong(strSeaportId);
            request.setAttribute("all_excursions", excursionService.getAllExcursionsInSeaportForTravelAgent(seaportId));
        }
        else {
            request.setAttribute("all_excursions", excursionService.getAllExcursionsForTravelAgent());
        }

        return PathConstants.TRAVEL_AGENT_EXCURSIONS_JSP;
    }
}
