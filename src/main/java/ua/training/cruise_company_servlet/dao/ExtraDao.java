package ua.training.cruise_company_servlet.dao;

import ua.training.cruise_company_servlet.entity.Extra;

import java.util.List;

public interface ExtraDao extends GenericDao<Extra> {
    List<Extra> findAllOrderByNameEn();
    List<Extra> findAllByShipId(long shipId);
    List<Extra> findAllByOrderId(long orderId);
}
