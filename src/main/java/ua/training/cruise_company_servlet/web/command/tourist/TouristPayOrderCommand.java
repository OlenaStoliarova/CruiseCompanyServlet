package ua.training.cruise_company_servlet.web.command.tourist;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.training.cruise_company_servlet.service.NoEntityFoundException;
import ua.training.cruise_company_servlet.service.OrderService;
import ua.training.cruise_company_servlet.service.UntimelyOperationException;
import ua.training.cruise_company_servlet.web.command.Command;
import ua.training.cruise_company_servlet.web.constant.AttributesConstants;
import ua.training.cruise_company_servlet.web.constant.PathConstants;

import javax.servlet.http.HttpServletRequest;

public class TouristPayOrderCommand implements Command {
    private static final Logger LOG = LogManager.getLogger(TouristPayOrderCommand.class);

    @Override
    public String execute(HttpServletRequest request) {
        long orderId = Long.parseLong(request.getParameter(AttributesConstants.ORDER_ID));
        OrderService orderService = new OrderService();
        try {
            boolean isPaid = orderService.payForOrder(orderId);
            if( !isPaid) {
                LOG.warn("Payment was unsuccessful");
                //TODO: show some error message if there was payment error
            }
        } catch (NoEntityFoundException | UntimelyOperationException e) {
            LOG.error(e.getMessage(), e);
        }

        return PathConstants.REDIRECT_PREFIX + PathConstants.SERVLET_PATH + PathConstants.TOURIST_MANAGE_ORDERS_COMMAND;
    }
}
