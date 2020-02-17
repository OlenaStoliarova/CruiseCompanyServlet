package ua.cruise.company.dao;

import java.util.List;
import java.util.Optional;

public interface GenericDao<T>{
    boolean create (T entity);
    Optional<T> findById(long id);
    List<T> findAll();
    boolean update(T entity);
    default boolean delete(long id){
        throw new UnsupportedOperationException("delete method isn't implemented in DAO for this entity");
    }
}
