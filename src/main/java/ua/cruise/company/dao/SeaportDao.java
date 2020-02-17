package ua.cruise.company.dao;

import ua.cruise.company.entity.Seaport;

import java.util.List;
import java.util.Optional;

public interface SeaportDao extends GenericDao<Seaport> {
    Optional<Seaport> findByNameEn(String  nameEn);
    List<Seaport> findAllOfShipRoute(long shipId);
}
