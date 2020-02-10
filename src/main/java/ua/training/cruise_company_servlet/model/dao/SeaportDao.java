package ua.training.cruise_company_servlet.model.dao;

import ua.training.cruise_company_servlet.model.entity.Seaport;

import java.util.Optional;

public interface SeaportDao extends GenericDao<Seaport> {
    Optional<Seaport> findByNameEn(String  nameEn);
}
