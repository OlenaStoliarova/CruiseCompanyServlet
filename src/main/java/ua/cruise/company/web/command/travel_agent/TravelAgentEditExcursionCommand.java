package ua.cruise.company.web.command.travel_agent;

import ua.cruise.company.entity.Excursion;
import ua.cruise.company.service.ExcursionService;
import ua.cruise.company.service.SeaportService;
import ua.cruise.company.service.exception.NoEntityFoundException;
import ua.cruise.company.service.exception.NonUniqueObjectException;
import ua.cruise.company.web.command.Command;
import ua.cruise.company.web.constant.AttributesConstants;
import ua.cruise.company.web.constant.PathConstants;
import ua.cruise.company.web.form.ExcursionForm;
import ua.cruise.company.web.form.mapper.ExcursionFormMapper;
import ua.cruise.company.web.form.validator.ExcursionFormValidator;

import javax.servlet.http.HttpServletRequest;

public class TravelAgentEditExcursionCommand implements Command {
    private final SeaportService seaportService = new SeaportService();
    private final ExcursionService excursionService = new ExcursionService();

    @Override
    public String execute(HttpServletRequest request) {
        Long excursionId = Long.valueOf(request.getParameter(AttributesConstants.EXCURSION_ID));

        ExcursionForm excursionForm;
        if (request.getMethod().equalsIgnoreCase("GET")) {
            Excursion excursion;
            try {
                excursion = excursionService.getExcursionById(excursionId);
            } catch (NoEntityFoundException ex) {
                request.setAttribute(AttributesConstants.ERROR_NO_EXCURSION_FOUND, true);
                return PathConstants.TRAVEL_AGENT_EDIT_EXCURSION_JSP;
            }

            excursionForm = new ExcursionFormMapper().fillForm(excursion);
            setExcursionFormAttributes(request, excursionForm);
            return PathConstants.TRAVEL_AGENT_EDIT_EXCURSION_JSP;
        } else {
            excursionForm = new ExcursionFormMapper().fillForm(request);
        }

        ExcursionFormValidator validator = new ExcursionFormValidator();
        if (validator.isFormNotCompleted(excursionForm)) {
            setExcursionFormAttributes(request, excursionForm);
            return PathConstants.TRAVEL_AGENT_EDIT_EXCURSION_JSP;
        }

        if (!validator.isFormValid(excursionForm)) {
            setExcursionFormAttributes(request, excursionForm);
            request.setAttribute(AttributesConstants.ERROR_VALIDATION, true);
            return PathConstants.TRAVEL_AGENT_EDIT_EXCURSION_JSP;
        }

        try {
            excursionService.editExcursion(excursionId, excursionForm);
        } catch (NoEntityFoundException e) {
            setExcursionFormAttributes(request, excursionForm);
            request.setAttribute(AttributesConstants.ERROR_NO_PORT_FOUND, true);
            return PathConstants.TRAVEL_AGENT_EDIT_EXCURSION_JSP;
        } catch (NonUniqueObjectException e) {
            setExcursionFormAttributes(request, excursionForm);
            request.setAttribute(AttributesConstants.ERROR_NON_UNIQUE, true);
            return PathConstants.TRAVEL_AGENT_EDIT_EXCURSION_JSP;
        }

        return PathConstants.REDIRECT_PREFIX + PathConstants.SERVLET_PATH + PathConstants.TRAVEL_AGENT_MANAGE_EXCURSIONS_COMMAND;
    }

    private void setExcursionFormAttributes(HttpServletRequest request, ExcursionForm excursionForm) {
        request.setAttribute(AttributesConstants.EXCURSION_OBJECT, excursionForm);
        request.setAttribute(AttributesConstants.ALL_SEAPORTS, seaportService.getAllSeaportsLocalizedSorted());
    }
}
