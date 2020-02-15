package ua.training.cruise_company_servlet.web.command.travel_agent;

import ua.training.cruise_company_servlet.service.OrderService;
import ua.training.cruise_company_servlet.utility.Page;
import ua.training.cruise_company_servlet.utility.PaginationHelper;
import ua.training.cruise_company_servlet.utility.PaginationSettings;
import ua.training.cruise_company_servlet.web.command.Command;
import ua.training.cruise_company_servlet.web.constant.AttributesConstants;
import ua.training.cruise_company_servlet.web.constant.PathConstants;
import ua.training.cruise_company_servlet.web.dto.OrderDTO;

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
