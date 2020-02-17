package ua.cruise.company.web.command.travel_agent;

import ua.cruise.company.service.ExcursionService;
import ua.cruise.company.service.SeaportService;
import ua.cruise.company.utility.Page;
import ua.cruise.company.utility.PaginationHelper;
import ua.cruise.company.utility.PaginationSettings;
import ua.cruise.company.web.command.Command;
import ua.cruise.company.web.constant.AttributesConstants;
import ua.cruise.company.web.constant.PathConstants;
import ua.cruise.company.web.dto.ExcursionDTO;

import javax.servlet.http.HttpServletRequest;

public class TravelAgentManageExcursionsCommand implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        SeaportService seaportService = new SeaportService();
        ExcursionService excursionService = new ExcursionService();
        request.setAttribute(AttributesConstants.ALL_SEAPORTS, seaportService.getAllSeaportsLocalizedSorted());

        PaginationSettings paginationSettings = PaginationHelper.getPaginationSettings(request);
        Page<ExcursionDTO> page;

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
