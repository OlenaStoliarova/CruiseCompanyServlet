package ua.training.cruise_company_servlet.web.command.tourist;

import ua.training.cruise_company_servlet.service.CruiseService;
import ua.training.cruise_company_servlet.utility.Page;
import ua.training.cruise_company_servlet.utility.PaginationHelper;
import ua.training.cruise_company_servlet.utility.PaginationSettings;
import ua.training.cruise_company_servlet.web.command.Command;
import ua.training.cruise_company_servlet.web.constant.AttributesConstants;
import ua.training.cruise_company_servlet.web.constant.PathConstants;
import ua.training.cruise_company_servlet.web.dto.CruiseDTO;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;

public class TouristCruisesCommand implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        CruiseService cruiseService = new CruiseService();

        LocalDate fromDate = LocalDate.now();
        String strFilterDate = request.getParameter("fromDate");
        if(strFilterDate != null) {
            try {
                fromDate = LocalDate.parse(strFilterDate);
            } catch(Exception ignored){}
        }

        PaginationSettings paginationSettings = PaginationHelper.getPaginationSettings(request);
        Page<CruiseDTO> page = cruiseService.getAvailableCruisesAfterDate(fromDate, paginationSettings);

        request.setAttribute(AttributesConstants.ALL_CRUISES, page.getContent());
        PaginationHelper.setPaginationAttributes(request, page);

        return PathConstants.TOURIST_CRUISES_JSP;
    }
}
