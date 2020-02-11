package ua.training.cruise_company_servlet.dao.implementation;

import java.sql.PreparedStatement;
import java.sql.SQLException;

@FunctionalInterface
public interface StatementMapper {
    void map(PreparedStatement ps) throws SQLException;
}
