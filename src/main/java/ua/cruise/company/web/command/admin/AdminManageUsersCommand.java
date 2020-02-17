package ua.cruise.company.web.command.admin;

import ua.cruise.company.web.command.Command;
import ua.cruise.company.web.constant.PathConstants;
import ua.cruise.company.enums.UserRole;
import ua.cruise.company.service.UserService;

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
