package ua.cruise.company.dao;

import ua.cruise.company.entity.Extra;

import java.util.List;

public interface ExtraDao extends GenericDao<Extra> {
    List<Extra> findAllOrderByNameEn();

    List<Extra> findAllByShipId(long shipId);

    List<Extra> findAllByOrderId(long orderId);
}
