package ua.training.cruise_company_servlet.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface ObjectMapper<T> {
    T extractFromResultSet(ResultSet rs) throws SQLException;
}
