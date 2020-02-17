package ua.training.cruise_company_servlet.web.command.tourist;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.training.cruise_company_servlet.service.IllegalRequestException;
import ua.training.cruise_company_servlet.service.NoEntityFoundException;
import ua.training.cruise_company_servlet.service.OrderService;
import ua.training.cruise_company_servlet.service.UntimelyOperationException;
import ua.training.cruise_company_servlet.web.authentication.Authentication;
import ua.training.cruise_company_servlet.web.command.Command;
import ua.training.cruise_company_servlet.web.constant.AttributesConstants;
import ua.training.cruise_company_servlet.web.constant.PathConstants;

import javax.servlet.http.HttpServletRequest;

public class TouristCancelOrderCommand implements Command {
    private static final Logger LOG = LogManager.getLogger(TouristCancelOrderCommand.class);

    @Override
    public String execute(HttpServletRequest request) {
        long orderId = Long.parseLong(request.getParameter(AttributesConstants.ORDER_ID));
        OrderService orderService = new OrderService();
        try {
            orderService.cancelBooking(orderId, new Authentication(request.getSession()).getUserId());
        } catch (NoEntityFoundException | UntimelyOperationException | IllegalRequestException e) {
            LOG.error(e.getMessage(), e);
        }
        return PathConstants.REDIRECT_PREFIX + PathConstants.SERVLET_PATH + PathConstants.TOURIST_MANAGE_ORDERS_COMMAND;
    }
}
