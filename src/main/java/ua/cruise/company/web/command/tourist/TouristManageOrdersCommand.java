package ua.cruise.company.web.command.tourist;

import ua.cruise.company.service.OrderService;
import ua.cruise.company.web.authentication.Authentication;
import ua.cruise.company.web.command.Command;
import ua.cruise.company.web.constant.AttributesConstants;
import ua.cruise.company.web.constant.PathConstants;
import ua.cruise.company.web.dto.OrderDTO;

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
