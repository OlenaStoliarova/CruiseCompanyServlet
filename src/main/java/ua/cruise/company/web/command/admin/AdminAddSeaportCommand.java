package ua.cruise.company.web.command.admin;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.cruise.company.entity.Seaport;
import ua.cruise.company.service.SeaportService;
import ua.cruise.company.web.command.Command;
import ua.cruise.company.web.constant.AttributesConstants;
import ua.cruise.company.web.constant.PathConstants;

import javax.servlet.http.HttpServletRequest;

public class AdminAddSeaportCommand implements Command {
    private static final Logger LOG = LogManager.getLogger(AdminAddSeaportCommand.class);

    @Override
    public String execute(HttpServletRequest request) {
        String portNameEn = request.getParameter(AttributesConstants.PORT_NAME_EN);
        String portCountryEn = request.getParameter(AttributesConstants.PORT_COUNTRY_EN);
        String portNameUkr = request.getParameter(AttributesConstants.PORT_NAME_UKR);
        String portCountryUkr = request.getParameter(AttributesConstants.PORT_COUNTRY_UKR);
        if (portNameEn == null || portNameEn.equals("") || portCountryEn == null || portCountryEn.equals("") ||
                portNameUkr == null || portNameUkr.equals("") || portCountryUkr == null || portCountryUkr.equals("")) {
            return PathConstants.REDIRECT_PREFIX + PathConstants.SERVLET_PATH + PathConstants.ADMIN_MANAGE_SEAPORTS_COMMAND;
        }
        LOG.info("portNameEn: " + portNameEn + "; portCountryEn: " + portCountryEn + ";");
        LOG.info("portNameUkr: " + portNameUkr + "; portCountryUkr: " + portCountryUkr + ";");

        Seaport addedSeaport = new Seaport();
        addedSeaport.setNameEn(portNameEn);
        addedSeaport.setNameUkr(portNameUkr);
        addedSeaport.setCountryEn(portCountryEn);
        addedSeaport.setCountryUkr(portCountryUkr);

        SeaportService seaportService = new SeaportService();
        boolean wasCreated = seaportService.savePort(addedSeaport);
        if (!wasCreated)
            return PathConstants.REDIRECT_PREFIX + PathConstants.SERVLET_PATH + PathConstants.ADMIN_MANAGE_SEAPORTS_COMMAND + "?error=true";
        LOG.info("Seaport was added successfully.");
        return PathConstants.REDIRECT_PREFIX + PathConstants.SERVLET_PATH + PathConstants.ADMIN_MANAGE_SEAPORTS_COMMAND;
    }
}
