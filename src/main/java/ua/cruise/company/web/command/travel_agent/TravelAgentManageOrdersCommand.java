package ua.cruise.company.web.command.travel_agent;

import ua.cruise.company.service.OrderService;
import ua.cruise.company.utility.Page;
import ua.cruise.company.utility.PaginationHelper;
import ua.cruise.company.utility.PaginationSettings;
import ua.cruise.company.web.command.Command;
import ua.cruise.company.web.constant.AttributesConstants;
import ua.cruise.company.web.constant.PathConstants;
import ua.cruise.company.web.dto.OrderDTO;

import javax.servlet.http.HttpServletRequest;

public class TravelAgentManageOrdersCommand implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        OrderService orderService = new OrderService();

        PaginationSettings paginationSettings = PaginationHelper.getPaginationSettings(request);
        Page<OrderDTO> page = orderService.getAllOrders(paginationSettings);
        request.setAttribute(AttributesConstants.ORDERS, page.getContent());
        PaginationHelper.setPaginationAttributes(request, page);

        return PathConstants.TRAVEL_AGENT_MANAGE_ORDERS_JSP;
    }
}
