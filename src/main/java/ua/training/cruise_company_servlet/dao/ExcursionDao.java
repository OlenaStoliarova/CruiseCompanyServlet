package ua.training.cruise_company_servlet.dao;

import ua.training.cruise_company_servlet.entity.Excursion;

import java.util.List;

public interface ExcursionDao extends GenericDao<Excursion> {
    List<Excursion> findBySeaportId(long seaportId);
}
