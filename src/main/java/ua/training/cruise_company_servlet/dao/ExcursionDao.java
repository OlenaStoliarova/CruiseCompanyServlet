package ua.training.cruise_company_servlet.dao;

import ua.training.cruise_company_servlet.entity.Excursion;
import ua.training.cruise_company_servlet.utility.Page;
import ua.training.cruise_company_servlet.utility.PaginationSettings;

public interface ExcursionDao extends GenericDao<Excursion> {
    Page<Excursion> findAllOrderByPrice(PaginationSettings paginationSettings);

    Page<Excursion> findBySeaportIdOrderByNameEn(long seaportId, PaginationSettings paginationSettings);
}
