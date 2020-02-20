package ua.cruise.company.web.command.tourist;

import ua.cruise.company.service.CruiseService;
import ua.cruise.company.utility.Page;
import ua.cruise.company.utility.PaginationHelper;
import ua.cruise.company.utility.PaginationSettings;
import ua.cruise.company.web.command.Command;
import ua.cruise.company.web.constant.AttributesConstants;
import ua.cruise.company.web.constant.PathConstants;
import ua.cruise.company.web.dto.CruiseDTO;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;

public class TouristCruisesCommand implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        CruiseService cruiseService = new CruiseService();

        LocalDate fromDate = LocalDate.now();
        String strFilterDate = request.getParameter("fromDate");
        if (strFilterDate != null) {
            try {
                fromDate = LocalDate.parse(strFilterDate);
            } catch (Exception ignored) {
            }
        }

        PaginationSettings paginationSettings = PaginationHelper.getPaginationSettings(request);
        Page<CruiseDTO> page = cruiseService.getAvailableCruisesAfterDate(fromDate, paginationSettings);

        request.setAttribute(AttributesConstants.ALL_CRUISES, page.getContent());
        PaginationHelper.setPaginationAttributes(request, page);

        return PathConstants.TOURIST_CRUISES_JSP;
    }
}
