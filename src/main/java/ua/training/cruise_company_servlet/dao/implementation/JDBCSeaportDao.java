 package ua.training.cruise_company_servlet.dao.implementation;

 import org.apache.logging.log4j.LogManager;
 import org.apache.logging.log4j.Logger;
 import ua.training.cruise_company_servlet.dao.DAOLevelException;
 import ua.training.cruise_company_servlet.dao.SeaportDao;
 import ua.training.cruise_company_servlet.dao.mapper.SeaportMapper;
 import ua.training.cruise_company_servlet.entity.Seaport;

 import java.sql.*;
 import java.util.ArrayList;
 import java.util.List;
 import java.util.Optional;

public class JDBCSeaportDao implements SeaportDao {
    private static final Logger LOG = LogManager.getLogger(JDBCSeaportDao.class);

    private static final String INSERT_NEW_PORT ="INSERT INTO seaport (name_en, country_en, name_ukr, country_ukr) VALUES (?, ?, ?, ?)";
    private static final String SELECT_PORT_BY_ID = "SELECT * FROM seaport WHERE id=?";
    private static final String SELECT_PORT_BY_NAME_EN = "SELECT * FROM seaport WHERE name_en=?";
    private static final String SELECT_ALL_PORTS = "SELECT * FROM seaport";
    private static final String DELETE_PORT_BY_ID = "DELETE FROM seaport WHERE id=?";

    private static final String UPDATE_PORT = "UPDATE seaport " +
            "SET name_en=? , country_en=?, name_ukr=?, country_ukr=? " +
            "WHERE id=?";

    @Override
    public boolean create(Seaport entity) {
        try(Connection connection = ConnectionPoolHolder.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_NEW_PORT)) {

            preparedStatement.setString(1, entity.getNameEn());
            preparedStatement.setString(2, entity.getCountryEn());
            preparedStatement.setString(3, entity.getNameUkr());
            preparedStatement.setString(4, entity.getCountryUkr());

            return 1 == preparedStatement.executeUpdate(); //one row was inserted
        }catch (SQLIntegrityConstraintViolationException ex){
            LOG.error(ex.getMessage(), ex);
            return false;
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
            throw new DAOLevelException(e);
        }
    }

    @Override
    public Optional<Seaport> findById(long id) {
        Optional<Seaport> foundPort = Optional.empty();

        try(Connection connection = ConnectionPoolHolder.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_PORT_BY_ID)) {

            preparedStatement.setLong(1, id);
            try(ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    foundPort = Optional.of(new SeaportMapper().extractFromResultSet(resultSet));
                }
            }
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
            throw new DAOLevelException(e);
        }

        return foundPort;
    }

    @Override
    public Optional<Seaport> findByNameEn(String  nameEn) {
        Optional<Seaport> foundPort = Optional.empty();

        try(Connection connection = ConnectionPoolHolder.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_PORT_BY_NAME_EN)) {

            preparedStatement.setString(1, nameEn);
            try(ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    foundPort = Optional.of(new SeaportMapper().extractFromResultSet(resultSet));
                }
            }
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
            throw new DAOLevelException(e);
        }

        return foundPort;
    }

    @Override
    public List<Seaport> findAll() {
        List<Seaport> resultList = new ArrayList<>();

        try(Connection connection = ConnectionPoolHolder.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SELECT_ALL_PORTS)) {

            while (resultSet.next()) {
                Seaport seaport = new SeaportMapper().extractFromResultSet(resultSet);
                resultList.add(seaport);
            }
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
            throw new DAOLevelException(e);
        }

        return resultList;
    }

    @Override
    public boolean update(Seaport entity) {
        try(Connection connection = ConnectionPoolHolder.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_PORT)) {

            preparedStatement.setString(1, entity.getNameEn());
            preparedStatement.setString(2, entity.getCountryEn());
            preparedStatement.setString(3, entity.getNameUkr());
            preparedStatement.setString(4, entity.getCountryUkr());
            preparedStatement.setLong(5, entity.getId());

            return 1 == preparedStatement.executeUpdate(); //one row was updated
        }catch (SQLIntegrityConstraintViolationException ex){
            LOG.error(ex.getMessage(), ex);
            return false;
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
            throw new DAOLevelException(e);
        }
    }

    @Override
    public boolean delete(long id) {
        try(Connection connection = ConnectionPoolHolder.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE_PORT_BY_ID)) {

            preparedStatement.setLong(1, id);
            return 1 == preparedStatement.executeUpdate(); //one row was deleted
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
            throw new DAOLevelException(e);
        }
    }
}
