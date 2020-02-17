package ua.cruise.company.web.command.tourist;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.cruise.company.service.CruiseService;
import ua.cruise.company.service.exception.NoEntityFoundException;
import ua.cruise.company.service.OrderService;
import ua.cruise.company.web.authentication.Authentication;
import ua.cruise.company.web.command.Command;
import ua.cruise.company.web.constant.AttributesConstants;
import ua.cruise.company.web.constant.PathConstants;
import ua.cruise.company.web.dto.CruiseDTO;

import javax.servlet.http.HttpServletRequest;

public class TouristOrderCruiseCommand implements Command {
    private static final Logger LOG = LogManager.getLogger(TouristOrderCruiseCommand.class);

    @Override
    public String execute(HttpServletRequest request) {

        if(request.getMethod().equalsIgnoreCase("GET")) {
            prepareCruiseDetails(request);
            return PathConstants.TOURIST_ORDER_CRUISE_JSP;
        } else {
            boolean isBooked = bookCruise(request);
            if ( !isBooked) {
                request.setAttribute(AttributesConstants.ERROR_BOOKING_CRUISE, true);
                prepareCruiseDetails(request);
                return PathConstants.TOURIST_ORDER_CRUISE_JSP;
            }

            return PathConstants.REDIRECT_PREFIX + PathConstants.SERVLET_PATH + PathConstants.TOURIST_MANAGE_ORDERS_COMMAND;
        }
    }


    private void prepareCruiseDetails(HttpServletRequest request){
        try {
            long cruiseId = Long.parseLong(request.getParameter(AttributesConstants.CRUISE_ID));
            CruiseDTO cruiseDTO = new CruiseService().getCruiseDtoById(cruiseId);
            request.setAttribute(AttributesConstants.CRUISE_OBJECT, cruiseDTO);
        } catch (NumberFormatException | NoEntityFoundException e) {
            LOG.debug("Exception in TouristOrderCruiseCommand GET");
            LOG.debug(e.getMessage(), e);
            request.setAttribute(AttributesConstants.ERROR_NO_CRUISE_FOUND, true);
        }
    }

    private boolean bookCruise(HttpServletRequest request){
        long cruiseId;
        int quantity;
        try {
            cruiseId = Long.parseLong(request.getParameter(AttributesConstants.CRUISE_ID));
            quantity = Integer.parseInt(request.getParameter(AttributesConstants.CRUISE_ORDER_QUANTITY));
        }catch (NumberFormatException e) {
            LOG.debug(e.getMessage(), e);
            return false;
        }

        long userId = new Authentication(request.getSession()).getUserId();
        OrderService orderService = new OrderService();
        try {
            return orderService.bookCruise(userId, cruiseId, quantity);
        } catch (NoEntityFoundException e) {
            LOG.debug( e.getMessage() );
            return false;
        }
    }
}
