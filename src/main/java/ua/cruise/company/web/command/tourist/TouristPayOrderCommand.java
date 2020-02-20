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

import javax.servlet.http.HttpServletRequest;

public class TouristPayOrderCommand implements Command {
    private static final Logger LOG = LogManager.getLogger(TouristPayOrderCommand.class);

    @Override
    public String execute(HttpServletRequest request) {
        long orderId = Long.parseLong(request.getParameter(AttributesConstants.ORDER_ID));
        OrderService orderService = new OrderService();
        try {
            boolean isPaid = orderService.payForOrder(orderId, new Authentication(request.getSession()).getUserId());
            if (!isPaid) {
                LOG.warn("Payment was unsuccessful");
                //TODO: show some error message if there was payment error
            }
        } catch (NoEntityFoundException | UntimelyOperationException | IllegalRequestException e) {
            LOG.error(e.getMessage(), e);
        }

        return PathConstants.REDIRECT_PREFIX + PathConstants.SERVLET_PATH + PathConstants.TOURIST_MANAGE_ORDERS_COMMAND;
    }
}
