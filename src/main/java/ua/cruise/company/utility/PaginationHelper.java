package ua.cruise.company.utility;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.cruise.company.web.constant.AttributesConstants;

import javax.servlet.http.HttpServletRequest;

public class PaginationHelper {
    private static final Logger LOG = LogManager.getLogger(PaginationHelper.class);

    private static final long DEFAULT_CURRENT_PAGE = 1;
    private static final int DEFAULT_PAGE_SIZE = 5;

    public static PaginationSettings getPaginationSettings(HttpServletRequest request){
        try{
            long page = Long.parseLong(request.getParameter(AttributesConstants.PAGE));
            int size = Integer.parseInt(request.getParameter(AttributesConstants.SIZE));

            return new PaginationSettings(size, page);
        }
        catch (IllegalArgumentException ex){
            LOG.debug(ex.getMessage(), ex);
            return new PaginationSettings(DEFAULT_PAGE_SIZE, DEFAULT_CURRENT_PAGE);
        }
    }

    public static void setPaginationAttributes(HttpServletRequest request, Page<?> page){
        request.setAttribute(AttributesConstants.PAGINATION_TOTAL_PAGES, page.getTotalPages());
        request.setAttribute(AttributesConstants.PAGINATION_CURRENT_PAGE, page.getCurrentPageNumber());
        request.setAttribute(AttributesConstants.PAGINATION_PAGE_SIZE, page.getSize());
    }
}
