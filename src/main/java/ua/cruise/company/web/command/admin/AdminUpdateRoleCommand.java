package ua.cruise.company.web.command.admin;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.cruise.company.web.command.Command;
import ua.cruise.company.web.constant.PathConstants;
import ua.cruise.company.enums.UserRole;
import ua.cruise.company.service.UserService;

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
            return PathConstants.REDIRECT_PREFIX + PathConstants.SERVLET_PATH + PathConstants.ADMIN_MANAGE_USERS_COMMAND + "?error_updating_role=true";
        return PathConstants.REDIRECT_PREFIX + PathConstants.SERVLET_PATH + PathConstants.ADMIN_MANAGE_USERS_COMMAND;
    }
}
