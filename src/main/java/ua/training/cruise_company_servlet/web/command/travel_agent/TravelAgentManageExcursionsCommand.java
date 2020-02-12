package ua.training.cruise_company_servlet.web.command.travel_agent;

import ua.training.cruise_company_servlet.service.ExcursionService;
import ua.training.cruise_company_servlet.service.SeaportService;
import ua.training.cruise_company_servlet.utility.Page;
import ua.training.cruise_company_servlet.utility.PaginationHelper;
import ua.training.cruise_company_servlet.utility.PaginationSettings;
import ua.training.cruise_company_servlet.web.command.Command;
import ua.training.cruise_company_servlet.web.constant.AttributesConstants;
import ua.training.cruise_company_servlet.web.constant.PathConstants;
import ua.training.cruise_company_servlet.web.dto.ExcursionForTravelAgentDTO;

import javax.servlet.http.HttpServletRequest;

public class TravelAgentManageExcursionsCommand implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        SeaportService seaportService = new SeaportService();
        ExcursionService excursionService = new ExcursionService();
        request.setAttribute(AttributesConstants.ALL_SEAPORTS, seaportService.getAllSeaportsLocalizedSorted());

        PaginationSettings paginationSettings = PaginationHelper.getPaginationSettings(request);
        Page<ExcursionForTravelAgentDTO> page;

        String strSeaportId = request.getParameter(AttributesConstants.EXCURSION_PORT);
        if(strSeaportId != null){
            long seaportId = Long.parseLong(strSeaportId);
            page = excursionService.getAllExcursionsForTravelAgent(seaportId, paginationSettings);
        }
        else {
            page = excursionService.getAllExcursionsForTravelAgent(paginationSettings);
        }

        request.setAttribute(AttributesConstants.ALL_EXCURSIONS, page.getContent());
        PaginationHelper.setPaginationAttributes(request, page);

        return PathConstants.TRAVEL_AGENT_EXCURSIONS_JSP;
    }
}
