package ua.training.cruise_company_servlet.dao;

import ua.training.cruise_company_servlet.entity.Order;
import ua.training.cruise_company_servlet.utility.Page;
import ua.training.cruise_company_servlet.utility.PaginationSettings;

import java.util.List;

public interface OrderDao extends GenericDao<Order> {
    List<Order> findByUserId(long userId);
    Page<Order> findAll(PaginationSettings paginationSettings);

    boolean addExcursionsToOrder(long orderId, List<Long> excursionsIds);
    boolean addExtrasToOrder(long orderId, List<Long> extrasIds);
}
