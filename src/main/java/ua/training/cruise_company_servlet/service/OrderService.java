package ua.training.cruise_company_servlet.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.training.cruise_company_servlet.dao.*;
import ua.training.cruise_company_servlet.entity.*;
import ua.training.cruise_company_servlet.enums.OrderStatus;
import ua.training.cruise_company_servlet.persistence.TransactionManager;
import ua.training.cruise_company_servlet.web.dto.OrderDTO;
import ua.training.cruise_company_servlet.web.dto.converter.OrderDTOConverter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

public class OrderService {
    private static final Logger LOG = LogManager.getLogger(OrderService.class);

    private final OrderDao orderDao = DaoFactory.getInstance().createOrderDao();
    private final CruiseDao cruiseDao = DaoFactory.getInstance().createCruiseDao();

    public List<OrderDTO> allOrdersOfUser(Long userId) {
        List<Order> userOrders = orderDao.findByUserId(userId);

        Iterator<Order> iterator = userOrders.iterator();
        while (iterator.hasNext()) {
            Order order = iterator.next();
            try{
                loadUser(order);
                loadCruise(order);
            } catch (NoEntityFoundException ex){
                LOG.debug(ex.getMessage(), ex);
                iterator.remove();
            }
            loadOrderExtras(order);
            loadOrderExcursions(order);
        }


        return userOrders.stream()
                .map(OrderDTOConverter::convertToDTO)
                .collect(Collectors.toList());
    }

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

    public boolean cancelBooking(long orderId) throws NoEntityFoundException {
        Order orderFromDB = orderDao.findById(orderId)
                .orElseThrow(() -> new NoEntityFoundException("There is no order with provided id (" + orderId + ")"));

        orderFromDB.setStatus(OrderStatus.CANCELED);
        Cruise cruise = new CruiseService().getCruiseById( orderFromDB.getCruise().getId());
        cruise.setVacancies(cruise.getVacancies() + orderFromDB.getQuantity());

        try {
            TransactionManager.startTransaction();
            cruiseDao.update(cruise);
            orderDao.update(orderFromDB);
            TransactionManager.commit();
        } catch (DAOLevelException e) {
            LOG.error("cruise booking wasn't cancelled ", e);
            try {
                TransactionManager.rollback();
            } catch (DAOLevelException ex) {
                LOG.error(ex.getMessage(), ex);
            }
            return false;
        }

        return true;
    }

    public boolean payForOrder(long orderId) throws NoEntityFoundException {
        Order orderFromDB = orderDao.findById(orderId)
                .orElseThrow(() -> new NoEntityFoundException("There is no order with provided id (" + orderId + ")"));

        orderFromDB.setStatus(OrderStatus.PAID);
        return orderDao.update(orderFromDB);
    }

    private void loadUser(Order order) throws NoEntityFoundException {
        User user = new UserService().getUserById( order.getUser().getId());
        order.setUser(user);
    }

    private void loadCruise(Order order) throws NoEntityFoundException {
        Cruise cruise = new CruiseService().getCruiseById( order.getCruise(). getId());
        order.setCruise( cruise);
    }

    private void loadOrderExtras(Order order){
        ExtraDao extraDao = DaoFactory.getInstance().createExtraDao();
        List<Extra> extras = extraDao.findAllByOrderId(order.getId());
        order.setFreeExtras(new HashSet<>(extras));
    }

    private void loadOrderExcursions(Order order){
        ExcursionDao excursionDao = DaoFactory.getInstance().createExcursionDao();
        List<Excursion> addedExcursions = excursionDao.findAllByOrderId(order.getId());
        order.setExcursions(new HashSet<>(addedExcursions));
    }
}
