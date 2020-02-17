package ua.cruise.company.dao.implementation;

import java.sql.PreparedStatement;
import java.sql.SQLException;

@FunctionalInterface
public interface StatementMapper {
    void map(PreparedStatement ps) throws SQLException;
}
