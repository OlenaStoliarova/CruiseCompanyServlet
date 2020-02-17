package ua.cruise.company.dao.implementation;

import java.sql.ResultSet;
import java.sql.SQLException;

@FunctionalInterface
public interface ObjectMapper<T> {
    T extractFromResultSet(ResultSet rs) throws SQLException;
}
