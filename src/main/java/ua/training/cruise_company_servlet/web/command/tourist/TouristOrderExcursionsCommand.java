package ua.training.cruise_company_servlet.web.command.tourist;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.training.cruise_company_servlet.service.NoEntityFoundException;
import ua.training.cruise_company_servlet.service.OrderService;
import ua.training.cruise_company_servlet.service.UntimelyOperationException;
import ua.training.cruise_company_servlet.web.command.Command;
import ua.training.cruise_company_servlet.web.constant.AttributesConstants;
import ua.training.cruise_company_servlet.web.constant.PathConstants;
import ua.training.cruise_company_servlet.web.dto.ExcursionDTO;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class TouristOrderExcursionsCommand implements Command {
    private static final Logger LOG = LogManager.getLogger(TouristOrderExcursionsCommand.class);

    @Override
    public String execute(HttpServletRequest request) {
        long orderId = Long.parseLong(request.getParameter(AttributesConstants.ORDER_ID));

        OrderService orderService = new OrderService();
        try {
            List<ExcursionDTO> excursionDTO = orderService.getAllExcursionsForOrderCruise(orderId);
            request.setAttribute(AttributesConstants.ORDER_EXCURSIONS, excursionDTO);
        } catch (NoEntityFoundException e) {
            LOG.error(e.getMessage(), e);
        } catch (UntimelyOperationException e) {
            LOG.error(e.getMessage(), e);
            request.setAttribute(AttributesConstants.UNTIMELY_OPERATION, true);
            try {
                request.setAttribute(AttributesConstants.ORDER_OBJECT, orderService.getOrderDtoById(orderId, false));
            } catch (NoEntityFoundException ex) {
                LOG.error(e.getMessage(), e);
            }
        }
        return PathConstants.TOURIST_ORDER_EXCURSIONS_JSP;
    }
}
