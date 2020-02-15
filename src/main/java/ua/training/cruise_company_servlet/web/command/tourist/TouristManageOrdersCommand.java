package ua.training.cruise_company_servlet.web.command.tourist;

import ua.training.cruise_company_servlet.service.OrderService;
import ua.training.cruise_company_servlet.web.authentication.Authentication;
import ua.training.cruise_company_servlet.web.command.Command;
import ua.training.cruise_company_servlet.web.constant.AttributesConstants;
import ua.training.cruise_company_servlet.web.constant.PathConstants;
import ua.training.cruise_company_servlet.web.dto.OrderDTO;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class TouristManageOrdersCommand implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        long userId = new Authentication(request.getSession()).getUserId();

        OrderService orderService = new OrderService();
        List<OrderDTO> userOrders = orderService.allOrdersOfUser(userId);
        request.setAttribute(AttributesConstants.ORDERS, userOrders);
        return PathConstants.TOURIST_MANAGE_ORDERS_JSP;
    }
}
