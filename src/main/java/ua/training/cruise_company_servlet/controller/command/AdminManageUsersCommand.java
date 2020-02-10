package ua.training.cruise_company_servlet.controller.command;

import ua.training.cruise_company_servlet.controller.constant.PathConstants;
import ua.training.cruise_company_servlet.model.enums.UserRole;
import ua.training.cruise_company_servlet.model.service.UserService;

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
