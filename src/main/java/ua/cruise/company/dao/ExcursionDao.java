package ua.cruise.company.dao;

import ua.cruise.company.entity.Excursion;
import ua.cruise.company.utility.Page;
import ua.cruise.company.utility.PaginationSettings;

import java.util.List;

public interface ExcursionDao extends GenericDao<Excursion> {
    Page<Excursion> findAllOrderByPrice(PaginationSettings paginationSettings);

    Page<Excursion> findBySeaportIdOrderByNameEn(long seaportId, PaginationSettings paginationSettings);

    List<Excursion> findAllByOrderId(long orderId);

    List<Excursion> findAllBySeaportIds(List<Long> seaportIds);
}
