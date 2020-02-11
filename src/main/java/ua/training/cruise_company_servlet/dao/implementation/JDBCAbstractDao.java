package ua.training.cruise_company_servlet.dao.implementation;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.training.cruise_company_servlet.dao.DAOLevelException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public abstract class JDBCAbstractDao<T>{
    private static final Logger LOG = LogManager.getLogger(JDBCAbstractDao.class);

    protected static final String COLUMN_ID = "id";

    protected boolean executeCUDQuery(String query, StatementMapper statementMapper) {
        try(Connection connection = ConnectionPoolHolder.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            statementMapper.map(preparedStatement);

            return 1 == preparedStatement.executeUpdate(); //one row was affected
        }catch (SQLIntegrityConstraintViolationException ex){
            LOG.error(ex.getMessage(), ex);
            return false;
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
            throw new DAOLevelException(e);
        }
    }

    protected Optional<T> selectOne(String query, StatementMapper statementMapper, ObjectMapper<T> objectMapper) {
        Optional<T> foundEntity = Optional.empty();

        try(Connection connection = ConnectionPoolHolder.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            statementMapper.map(preparedStatement);
            try(ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    foundEntity = Optional.of(objectMapper.extractFromResultSet(resultSet));
                }
            }
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
            throw new DAOLevelException(e);
        }

        return foundEntity;
    }

    protected List<T> selectMany(String query, StatementMapper statementMapper, ObjectMapper<T> objectMapper) {
        List<T> resultList = new ArrayList<>();

        try (Connection connection = ConnectionPoolHolder.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            statementMapper.map(preparedStatement);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    T entity = objectMapper.extractFromResultSet(resultSet);
                    resultList.add(entity);
                }
            }
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
            throw new DAOLevelException(e);
        }

        return resultList;
    }
}
