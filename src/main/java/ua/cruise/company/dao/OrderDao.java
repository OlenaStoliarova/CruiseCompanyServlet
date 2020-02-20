package ua.cruise.company.dao;

import ua.cruise.company.entity.Order;
import ua.cruise.company.utility.Page;
import ua.cruise.company.utility.PaginationSettings;

import java.util.List;

public interface OrderDao extends GenericDao<Order> {
    List<Order> findByUserId(long userId);

    Page<Order> findAll(PaginationSettings paginationSettings);

    boolean addExcursionsToOrder(long orderId, List<Long> excursionsIds);

    boolean addExtrasToOrder(long orderId, List<Long> extrasIds);
}
