package ua.training.cruise_company_servlet.web.command.admin;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.training.cruise_company_servlet.web.command.Command;
import ua.training.cruise_company_servlet.web.constant.PathConstants;
import ua.training.cruise_company_servlet.enums.UserRole;
import ua.training.cruise_company_servlet.service.UserService;

import javax.servlet.http.HttpServletRequest;

public class AdminUpdateRoleCommand implements Command {
    private static final Logger LOG = LogManager.getLogger(AdminUpdateRoleCommand.class);

    @Override
    public String execute(HttpServletRequest request) {
        String userEmail = request.getParameter("email");
        UserRole userRole = UserRole.valueOf( request.getParameter("userRoles"));
        LOG.info("Updating role. User email: " + userEmail + "; new role: " + userRole);
        UserService userService = new UserService();
        boolean isSuccess = userService.updateUserRole(userEmail, userRole);
        if( !isSuccess)
            return "redirect:" + PathConstants.SERVLET_PATH + PathConstants.ADMIN_MANAGE_USERS_COMMAND + "?error_updating_role=true";
        return "redirect:" + PathConstants.SERVLET_PATH + PathConstants.ADMIN_MANAGE_USERS_COMMAND;
    }
}
