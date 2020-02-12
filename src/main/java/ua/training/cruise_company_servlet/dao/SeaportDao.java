package ua.training.cruise_company_servlet.dao;

import ua.training.cruise_company_servlet.entity.Seaport;

import java.util.List;
import java.util.Optional;

public interface SeaportDao extends GenericDao<Seaport> {
    Optional<Seaport> findByNameEn(String  nameEn);
    List<Seaport> findAllOfShipRoute(long shipId);
}
