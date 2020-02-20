package ua.cruise.company.web.command.tourist;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.cruise.company.service.OrderService;
import ua.cruise.company.service.exception.IllegalRequestException;
import ua.cruise.company.service.exception.NoEntityFoundException;
import ua.cruise.company.service.exception.UntimelyOperationException;
import ua.cruise.company.web.authentication.Authentication;
import ua.cruise.company.web.command.Command;
import ua.cruise.company.web.constant.AttributesConstants;
import ua.cruise.company.web.constant.PathConstants;
import ua.cruise.company.web.dto.ExcursionDTO;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class TouristOrderExcursionsCommand implements Command {
    private static final Logger LOG = LogManager.getLogger(TouristOrderExcursionsCommand.class);

    @Override
    public String execute(HttpServletRequest request) {
        long orderId = Long.parseLong(request.getParameter(AttributesConstants.ORDER_ID));

        OrderService orderService = new OrderService();
        try {
            List<ExcursionDTO> excursionDTO = orderService.getAllExcursionsForOrderCruise(orderId, new Authentication(request.getSession()).getUserId());
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
        } catch (IllegalRequestException e) {
            LOG.error(e.getMessage(), e);
            request.setAttribute(AttributesConstants.ILLEGAL_REQUEST, true);
        }

        return PathConstants.TOURIST_ORDER_EXCURSIONS_JSP;
    }
}
