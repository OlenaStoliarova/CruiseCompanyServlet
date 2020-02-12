package ua.training.cruise_company_servlet.dao;

import ua.training.cruise_company_servlet.entity.Cruise;
import ua.training.cruise_company_servlet.utility.Page;
import ua.training.cruise_company_servlet.utility.PaginationSettings;

import java.time.LocalDate;

public interface CruiseDao extends GenericDao<Cruise> {
    Page<Cruise> findAllAvailableAfterDate(LocalDate date, PaginationSettings paginationSettings);
}
