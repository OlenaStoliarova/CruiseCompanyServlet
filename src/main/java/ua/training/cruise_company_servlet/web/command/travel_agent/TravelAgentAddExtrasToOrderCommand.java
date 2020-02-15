package ua.training.cruise_company_servlet.web.command.travel_agent;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.training.cruise_company_servlet.service.NoEntityFoundException;
import ua.training.cruise_company_servlet.service.OrderService;
import ua.training.cruise_company_servlet.web.command.Command;
import ua.training.cruise_company_servlet.web.constant.AttributesConstants;
import ua.training.cruise_company_servlet.web.constant.PathConstants;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class TravelAgentAddExtrasToOrderCommand implements Command {
    private static final Logger LOG = LogManager.getLogger(TravelAgentAddExtrasToOrderCommand.class);

    @Override
    public String execute(HttpServletRequest request) {
        if(request.getMethod().equalsIgnoreCase("GET")){
            showBonusesSelection(request);
            return PathConstants.TRAVEL_AGENT_ADD_EXTRAS_TO_ORDER_JSP;
        } else {
            addChosenBonusesToOrder(request);
            return PathConstants.REDIRECT_PREFIX + PathConstants.SERVLET_PATH + PathConstants.TRAVEL_AGENT_MANAGE_ORDERS_COMMAND;
        }
    }

    private void showBonusesSelection(HttpServletRequest request) {
        long orderId = Long.parseLong(request.getParameter(AttributesConstants.ORDER_ID));
        OrderService orderService = new OrderService();
        try {
            request.setAttribute(AttributesConstants.ORDER_OBJECT, orderService.getOrderDtoById(orderId, false));
            request.setAttribute(AttributesConstants.BONUSES, orderService.getAllExtrasForOrderCruise(orderId));
        } catch (NoEntityFoundException e) {
            request.setAttribute(AttributesConstants.ERROR_NO_ORDER_FOUND, true);
        }
    }

    private void addChosenBonusesToOrder(HttpServletRequest request){
        long orderId = Long.parseLong(request.getParameter(AttributesConstants.ORDER_ID));
        String[] chosenBonuses = request.getParameterValues(AttributesConstants.CHOSEN_BONUSES);
        List<Long> extras = Arrays.stream(chosenBonuses)
                .map(Long::parseLong)
                .collect(Collectors.toList());

        OrderService orderService = new OrderService();
        try {
            orderService.addExtrasToOrder(orderId, extras);
        } catch (NoEntityFoundException e) {
            LOG.error(e.getMessage(), e);
        }
    }
}
