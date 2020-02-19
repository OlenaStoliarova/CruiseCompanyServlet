package ua.cruise.company.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.cruise.company.dao.DAOLevelException;
import ua.cruise.company.dao.DaoFactory;
import ua.cruise.company.dao.ExtraDao;
import ua.cruise.company.dao.OrderDao;
import ua.cruise.company.entity.*;
import ua.cruise.company.enums.OrderStatus;
import ua.cruise.company.persistence.TransactionManager;
import ua.cruise.company.service.exception.IllegalRequestException;
import ua.cruise.company.service.exception.NoEntityFoundException;
import ua.cruise.company.service.exception.UntimelyOperationException;
import ua.cruise.company.utility.Page;
import ua.cruise.company.utility.PaginationSettings;
import ua.cruise.company.web.dto.ExcursionDTO;
import ua.cruise.company.web.dto.ExtraDTO;
import ua.cruise.company.web.dto.OrderDTO;
import ua.cruise.company.web.dto.converter.ExtraDTOConverter;
import ua.cruise.company.web.dto.converter.OrderDTOConverter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

public class OrderService {
    private static final Logger LOG = LogManager.getLogger(OrderService.class);

    private OrderDao orderDao;
    private UserService userService;
    private CruiseService cruiseService;
    private ExcursionService excursionService;
    private ExtraDao extraDao;

    public OrderService() {
        orderDao = DaoFactory.getInstance().createOrderDao();
        userService = new UserService();
        cruiseService = new CruiseService();
        excursionService = new ExcursionService();
        extraDao = DaoFactory.getInstance().createExtraDao();
    }

    public List<OrderDTO> allOrdersOfUser(Long userId) {
        List<Order> userOrders = orderDao.findByUserId(userId);
        eagerLoadOrders(userOrders);
        return userOrders.stream()
                .map(OrderDTOConverter::convertToDTO)
                .collect(Collectors.toList());
    }

    public Page<OrderDTO> getAllOrders(PaginationSettings paginationSettings) {
        Page<Order> ordersPage = orderDao.findAll(paginationSettings);
        eagerLoadOrders(ordersPage.getContent());

        List<OrderDTO> contentDTO = ordersPage.getContent().stream()
                .map(OrderDTOConverter::convertToDTO)
                .collect(Collectors.toList());

        return new Page<>(contentDTO, paginationSettings, ordersPage.getTotalElements());
    }

    public boolean bookCruise(Long userId, Long cruiseId, int quantity) throws NoEntityFoundException {
        Order order = new Order();
        order.setStatus(OrderStatus.NEW);
        order.getUser().setId(userId);
        order.getCruise().setId(cruiseId);
        order.setQuantity(quantity);
        order.setCreationDate(LocalDate.now());

        try {
            TransactionManager.startTransaction();

            Cruise cruise = cruiseService.getCruiseById(cruiseId);
            cruise.setVacancies(cruise.getVacancies() - quantity);
            order.setTotalPrice(cruise.getPrice().multiply(BigDecimal.valueOf(quantity)));

            if( cruiseService.update(cruise)
                && orderDao.create(order)){
                TransactionManager.commit();
                LOG.info("Oder was created " + order);
                LOG.info("And cruise was updated");
                return true;
            } else {
                LOG.error("error creating order or updating cruise");
                rollbackTransaction();
                return false;
            }
        } catch (DAOLevelException e) {
            LOG.error("cruise wasn't booked ", e);
            rollbackTransaction();
            return false;
        } catch (NoEntityFoundException e) {
            LOG.error("No cruise with provided id ({}) was found", cruiseId);
            rollbackTransaction();
            throw e;
        }
    }

    public boolean cancelBooking(long orderId, long userId) throws NoEntityFoundException, UntimelyOperationException, IllegalRequestException {

        try {
            TransactionManager.startTransaction();

            Order orderFromDB = getOrderById(orderId, false);
            checkOwnership(userId, orderFromDB);
            if( !orderFromDB.getStatus().equals(OrderStatus.NEW)){
                throw new UntimelyOperationException("Only NEW orders can be cancelled.");
            }
            orderFromDB.setStatus(OrderStatus.CANCELED);
            Cruise cruise = cruiseService.getCruiseById( orderFromDB.getCruise().getId());
            cruise.setVacancies(cruise.getVacancies() + orderFromDB.getQuantity());

            if( cruiseService.update(cruise)
                && orderDao.update(orderFromDB)){
                TransactionManager.commit();
                LOG.info("Oder was updated " + orderFromDB);
                LOG.info("And cruise was updated " + cruise);
            } else {
                throw new NoEntityFoundException("Update operation haven't found rows to update");
            }
        } catch (DAOLevelException e) {
            LOG.error("cruise booking wasn't cancelled ", e);
            rollbackTransaction();
            return false;
        } catch (NoEntityFoundException | UntimelyOperationException e) {
            rollbackTransaction();
            throw e;
        }

        return true;
    }

    public Order getOrderById(long orderId, boolean isEagerLoad) throws NoEntityFoundException {
        Order order = orderDao.findById(orderId)
                .orElseThrow(() -> new NoEntityFoundException("There is no order with provided id (" + orderId + ")"));

        if(isEagerLoad) {
            loadUser(order);
            loadCruise(order);
            loadOrderExtras(order);
            loadOrderExcursions(order);
        }

        return order;
    }

    public OrderDTO getOrderDtoById(long orderId, boolean isEagerLoad) throws NoEntityFoundException {
        return OrderDTOConverter.convertToDTO( getOrderById(orderId, isEagerLoad));
    }

    public boolean payForOrder(long orderId, long userId) throws NoEntityFoundException, UntimelyOperationException, IllegalRequestException {
        try {
            TransactionManager.startTransaction();

            Order orderFromDB = getOrderById(orderId, false);
            checkOwnership(userId, orderFromDB);
            if( !orderFromDB.getStatus().equals(OrderStatus.NEW)){
                throw new UntimelyOperationException("Only NEW orders can be payed.");
            }
            orderFromDB.setStatus(OrderStatus.PAID);
            boolean result = orderDao.update(orderFromDB);

            TransactionManager.commit();
            LOG.info("Payed for order " + orderFromDB);
            return result;
        } catch (DAOLevelException e) {
            LOG.error("error paying for order ", e);
            rollbackTransaction();
            return false;
        } catch (NoEntityFoundException e) {
            LOG.error("order with provided id ({}) wasn't found", orderId);
            rollbackTransaction();
            throw e;
        } catch (UntimelyOperationException e) {
            rollbackTransaction();
            throw e;
        }
    }

    public List<ExcursionDTO> getAllExcursionsForOrderCruise(Long orderId, long userId) throws NoEntityFoundException, UntimelyOperationException, IllegalRequestException {
        Order order = getOrderById(orderId, false);
        checkOwnership(userId, order);
        if( !order.getStatus().equals(OrderStatus.PAID)){
            throw new UntimelyOperationException("Excursions can be added to PAID orders only.");
        }

        loadCruise(order);
        List<Long> portIds = order.getCruise().getShip()
                .getVisitingPorts().stream()
                .map(Seaport::getId)
                .collect(Collectors.toList());
        return new ExcursionService().getAllExcursionBySeaportIds(portIds);
    }

    public boolean addExcursionsToOrder(long orderId, List<Long> chosenExcursions, long userId) throws UntimelyOperationException, NoEntityFoundException, IllegalRequestException {
        try {
            TransactionManager.startTransaction();

            Order orderFromDB = getOrderById(orderId, false);
            checkOwnership(userId, orderFromDB);
            if( !orderFromDB.getStatus().equals(OrderStatus.PAID)){
                throw new UntimelyOperationException("Excursions can be added to PAID orders only.");
            }
            orderFromDB.setStatus(OrderStatus.EXCURSIONS_ADDED);

            if( orderDao.addExcursionsToOrder(orderId, chosenExcursions)
                && orderDao.update(orderFromDB)) {
                TransactionManager.commit();
                LOG.info("chosenExcursions ({}) was added to order {}", chosenExcursions, orderFromDB);
                return true;
            } else {
                LOG.error("Error adding excursions to order");
                return false;
            }
        } catch (DAOLevelException e) {
            LOG.error("chosen excursions weren't added to order ", e);
            rollbackTransaction();
            return false;
        } catch (NoEntityFoundException | UntimelyOperationException e) {
            rollbackTransaction();
            throw e;
        }
    }

    public List<ExtraDTO> getAllExtrasForOrderCruise(Long orderId) throws NoEntityFoundException, UntimelyOperationException {
        Order order = getOrderById(orderId, false);
        if( !order.getStatus().equals(OrderStatus.EXCURSIONS_ADDED)){
            throw new UntimelyOperationException("Extra bonuses can be added only to orders with status EXCURSIONS_ADDED.");
        }
        loadCruise(order);
        return order.getCruise().getShip().getExtras().stream()
                .map(ExtraDTOConverter::convertToDTO)
                .collect(Collectors.toList());
    }

    public boolean addExtrasToOrder(long orderId, List<Long> chosenExtras) throws UntimelyOperationException, NoEntityFoundException {
        try {
            TransactionManager.startTransaction();

            Order orderFromDB = getOrderById(orderId, false);
            if( !orderFromDB.getStatus().equals(OrderStatus.EXCURSIONS_ADDED)){
                throw new UntimelyOperationException("Extra bonuses can be added only to orders with status EXCURSIONS_ADDED.");
            }
            orderFromDB.setStatus(OrderStatus.EXTRAS_ADDED);

            if ( orderDao.addExtrasToOrder(orderId, chosenExtras)
                && orderDao.update(orderFromDB)){
                TransactionManager.commit();
                LOG.info("Free extras ({}) was added to order {}", chosenExtras, orderFromDB);
                return true;
            } else {
                LOG.error("Error adding extras to order");
                return false;
            }
        } catch (DAOLevelException e) {
            LOG.error("chosen extras weren't added to order ", e);
            rollbackTransaction();
            return false;
        } catch (NoEntityFoundException | UntimelyOperationException e) {
            rollbackTransaction();
            throw e;
        }
    }


    private void loadUser(Order order) throws NoEntityFoundException {
        User user = userService.getUserById( order.getUser().getId());
        order.setUser(user);
    }

    private void loadCruise(Order order) throws NoEntityFoundException {
        Cruise cruise = cruiseService.getCruiseById( order.getCruise().getId());
        order.setCruise( cruise);
    }

    private void loadOrderExtras(Order order){
        List<Extra> extras = extraDao.findAllByOrderId(order.getId());
        order.setFreeExtras(extras);
    }

    private void loadOrderExcursions(Order order){
        List<Excursion> addedExcursions = excursionService.gelAllExcursionByOrderId(order.getId());
        order.setExcursions(addedExcursions);
    }

    private void eagerLoadOrders(List<Order> orders){
        Iterator<Order> iterator = orders.iterator();
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
    }

    private void rollbackTransaction() {
        try {
            TransactionManager.rollback();
        } catch (DAOLevelException ex) {
            LOG.error(ex.getMessage(), ex);
        }
    }

    private void checkOwnership(long userId, Order order) throws IllegalRequestException {
        if (order.getUser().getId().compareTo(userId) != 0) {
            LOG.error("Attempt to change someone else's order");
            throw new IllegalRequestException("User can change only his own orders.");
        }
    }
}
