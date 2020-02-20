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
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class TouristAddExcursionsToOrderCommand implements Command {
    private static final Logger LOG = LogManager.getLogger(TouristAddExcursionsToOrderCommand.class);

    @Override
    public String execute(HttpServletRequest request) {
        long orderId = Long.parseLong(request.getParameter(AttributesConstants.ORDER_ID));
        String[] chosenExcursions = request.getParameterValues(AttributesConstants.CHOSEN_EXCURSIONS);
        List<Long> excursions = Arrays.stream(chosenExcursions)
                .map(Long::parseLong)
                .collect(Collectors.toList());

        OrderService orderService = new OrderService();
        try {
            orderService.addExcursionsToOrder(orderId, excursions, new Authentication(request.getSession()).getUserId());
        } catch (NoEntityFoundException | UntimelyOperationException | IllegalRequestException e) {
            LOG.error(e.getMessage(), e);
        }

        return PathConstants.REDIRECT_PREFIX + PathConstants.SERVLET_PATH + PathConstants.TOURIST_MANAGE_ORDERS_COMMAND;
    }
}
