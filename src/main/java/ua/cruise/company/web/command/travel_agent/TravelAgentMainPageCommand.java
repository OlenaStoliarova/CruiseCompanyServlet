package ua.cruise.company.web.command.travel_agent;

import ua.cruise.company.web.command.Command;
import ua.cruise.company.web.constant.PathConstants;

import javax.servlet.http.HttpServletRequest;

public class TravelAgentMainPageCommand implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        return PathConstants.TRAVEL_AGENT_MAIN_JSP;
    }
}
