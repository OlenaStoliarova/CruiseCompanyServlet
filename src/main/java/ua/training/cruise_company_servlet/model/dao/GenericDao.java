package ua.training.cruise_company_servlet.model.dao;

import java.util.List;
import java.util.Optional;

public interface GenericDao<T>{
    boolean create (T entity);
    Optional<T> findById(long id);
    List<T> findAll();
    boolean update(T entity);
    boolean delete(long id);
}
