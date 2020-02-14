package ua.training.cruise_company_servlet.dao;

import ua.training.cruise_company_servlet.entity.Order;

import java.util.List;

public interface OrderDao extends GenericDao<Order> {
    List<Order> findByUserId(long userId);
}
