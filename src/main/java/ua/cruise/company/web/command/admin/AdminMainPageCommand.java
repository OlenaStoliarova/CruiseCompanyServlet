package ua.cruise.company.web.command.admin;

import ua.cruise.company.web.command.Command;
import ua.cruise.company.web.constant.PathConstants;

import javax.servlet.http.HttpServletRequest;

public class AdminMainPageCommand implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        return PathConstants.ADMIN_MAIN_JSP;
    }
}
