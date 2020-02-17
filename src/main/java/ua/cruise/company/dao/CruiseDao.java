package ua.cruise.company.dao;

import ua.cruise.company.entity.Cruise;
import ua.cruise.company.utility.Page;
import ua.cruise.company.utility.PaginationSettings;

import java.time.LocalDate;

public interface CruiseDao extends GenericDao<Cruise> {
    Page<Cruise> findAllAvailableAfterDate(LocalDate date, PaginationSettings paginationSettings);
}
