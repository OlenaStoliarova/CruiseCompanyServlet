package ua.training.cruise_company_servlet.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.training.cruise_company_servlet.dao.CruiseDao;
import ua.training.cruise_company_servlet.dao.DAOLevelException;
import ua.training.cruise_company_servlet.dao.DaoFactory;
import ua.training.cruise_company_servlet.dao.OrderDao;
import ua.training.cruise_company_servlet.entity.Cruise;
import ua.training.cruise_company_servlet.entity.Order;
import ua.training.cruise_company_servlet.enums.OrderStatus;
import ua.training.cruise_company_servlet.persistence.TransactionManager;

import java.math.BigDecimal;
import java.time.LocalDate;

public class OrderService {
    private static final Logger LOG = LogManager.getLogger(OrderService.class);

    private final OrderDao orderDao = DaoFactory.getInstance().createOrderDao();
    private final CruiseDao cruiseDao = DaoFactory.getInstance().createCruiseDao();

    public boolean bookCruise(Long userId, Long cruiseId, int quantity) throws NoEntityFoundException {
        CruiseService cruiseService = new CruiseService();
        Cruise cruise = cruiseService.getCruiseById(cruiseId);
        cruise.setVacancies(cruise.getVacancies() - quantity);

        Order order = new Order();
        order.setStatus(OrderStatus.NEW);
        order.getUser().setId(userId);
        order.getCruise().setId(cruiseId);
        order.setQuantity(quantity);
        order.setTotalPrice(cruise.getPrice().multiply(BigDecimal.valueOf(quantity)));
        order.setCreationDate(LocalDate.now());

        try {
            TransactionManager.startTransaction();
            cruiseDao.update(cruise);
            orderDao.create(order);
            TransactionManager.commit();
        } catch (DAOLevelException e) {
            LOG.error("cruise wasn't booked ", e);
            try {
                TransactionManager.rollback();
            } catch (DAOLevelException ex) {
                LOG.error(ex.getMessage(), ex);
            }
            return false;
        }

        return true;
    }
}
