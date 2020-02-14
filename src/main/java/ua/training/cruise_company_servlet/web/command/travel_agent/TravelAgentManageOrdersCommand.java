package ua.training.cruise_company_servlet.web.command.travel_agent;

import ua.training.cruise_company_servlet.web.command.Command;
import ua.training.cruise_company_servlet.web.constant.PathConstants;

import javax.servlet.http.HttpServletRequest;

public class TravelAgentManageOrdersCommand implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        return PathConstants.TRAVEL_AGENT_MANAGE_ORDERS_JSP;
    }
}
