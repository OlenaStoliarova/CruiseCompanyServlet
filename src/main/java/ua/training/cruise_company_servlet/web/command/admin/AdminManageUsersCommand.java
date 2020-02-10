package ua.training.cruise_company_servlet.web.command.admin;

import ua.training.cruise_company_servlet.web.command.Command;
import ua.training.cruise_company_servlet.web.constant.PathConstants;
import ua.training.cruise_company_servlet.enums.UserRole;
import ua.training.cruise_company_servlet.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

public class AdminManageUsersCommand implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        UserService userService = new UserService();
        request.setAttribute("allUsers", userService.getAllUsers());
        request.setAttribute("roles", Arrays.asList(UserRole.values()) );
        return PathConstants.ADMIN_USERS_JSP;
    }
}
