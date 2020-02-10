package ua.training.cruise_company_servlet.model.dao.implementation;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.training.cruise_company_servlet.model.dao.DAOLevelException;
import ua.training.cruise_company_servlet.model.dao.ExcursionDao;
import ua.training.cruise_company_servlet.model.dao.mapper.ExcursionMapper;
import ua.training.cruise_company_servlet.model.dao.mapper.SeaportMapper;
import ua.training.cruise_company_servlet.model.entity.Excursion;
import ua.training.cruise_company_servlet.model.entity.Seaport;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JDBCExcursionDao implements ExcursionDao {
    private static final Logger LOG = LogManager.getLogger(JDBCExcursionDao.class);

    private static final String INSERT_NEW_EXCURSION = "INSERT INTO excursion" +
            "(name_en, name_ukr, description_en, description_ukr, " +
            "approximate_duration_hr, priceusd, seaport_id) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?)";

    private static final String SELECT_EXCURSION_BY_ID = "SELECT e.*, " +
            "s.id AS port_id, " +
            "s.name_en AS port_name_en, " +
            "s.name_ukr AS port_name_ukr, " +
            "s.country_en AS port_country_en, " +
            "s.country_ukr AS port_country_ukr " +
            "FROM excursion AS e " +
            "LEFT JOIN seaport AS s " +
            "ON e.seaport_id = s.id " +
            "WHERE e.id=?";

    private static final String SELECT_ALL_EXCURSIONS = "SELECT e.*, " +
            "s.id AS port_id, " +
            "s.name_en AS port_name_en, " +
            "s.name_ukr AS port_name_ukr, " +
            "s.country_en AS port_country_en, " +
            "s.country_ukr AS port_country_ukr " +
            "FROM excursion AS e " +
            "LEFT JOIN seaport AS s " +
            "ON e.seaport_id = s.id";

    private static final String SELECT_ALL_EXCURSIONS_IN_SEAPORT = "SELECT e.*, " +
            "s.id AS port_id, " +
            "s.name_en AS port_name_en, " +
            "s.name_ukr AS port_name_ukr, " +
            "s.country_en AS port_country_en, " +
            "s.country_ukr AS port_country_ukr " +
            "FROM excursion AS e " +
            "LEFT JOIN seaport AS s " +
            "ON e.seaport_id = s.id " +
            "WHERE e.seaport_id = ?";

    private static final String DELETE_EXCURSION_BY_ID = "DELETE FROM excursion WHERE id=?";

    private static final String UPDATE_EXCURSION = "UPDATE excursion " +
            "SET name_en=?, name_ukr=?, description_en=?, description_ukr=?, " +
            "approximate_duration_hr=?, priceusd=?, seaport_id=? " +
            "WHERE id=?";

    @Override
    public boolean create(Excursion entity) {
        try(Connection connection = ConnectionPoolHolder.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_NEW_EXCURSION)) {

            preparedStatement.setString(1, entity.getNameEn());
            preparedStatement.setString(2, entity.getNameUkr());
            preparedStatement.setString(3, entity.getDescriptionEn());
            preparedStatement.setString(4, entity.getDescriptionUkr());
            preparedStatement.setLong(5, entity.getApproximateDurationHr());
            preparedStatement.setBigDecimal(6, entity.getPriceUSD());
            preparedStatement.setLong(7, entity.getSeaport().getId());

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
    public Optional<Excursion> findById(long id) {
        Optional<Excursion> foundExcursion = Optional.empty();

        try(Connection connection = ConnectionPoolHolder.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_EXCURSION_BY_ID)) {

            preparedStatement.setLong(1, id);
            try(ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    Excursion excursion = new ExcursionMapper().extractFromResultSet(resultSet);
                    Seaport seaport = new SeaportMapper().extractFromResultSet(resultSet, "port_");
                    excursion.setSeaport(seaport);
                    foundExcursion = Optional.of(excursion);
                }
            }
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
            throw new DAOLevelException(e);
        }

        return foundExcursion;
    }

    @Override
    public List<Excursion> findAll() {
        List<Excursion> resultList = new ArrayList<>();

        try(Connection connection = ConnectionPoolHolder.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SELECT_ALL_EXCURSIONS)) {

            while (resultSet.next()) {
                Excursion excursion = new ExcursionMapper().extractFromResultSet(resultSet);
                Seaport seaport = new SeaportMapper().extractFromResultSet(resultSet, "port_");
                excursion.setSeaport(seaport);
                resultList.add(excursion);
            }
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
            throw new DAOLevelException(e);
        }

        return resultList;
    }

    @Override
    public List<Excursion> findBySeaportId(long seaportId) {
        List<Excursion> resultList = new ArrayList<>();

        try(Connection connection = ConnectionPoolHolder.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_EXCURSIONS_IN_SEAPORT)) {

            preparedStatement.setLong(1, seaportId);
            try(ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Excursion excursion = new ExcursionMapper().extractFromResultSet(resultSet);
                    Seaport seaport = new SeaportMapper().extractFromResultSet(resultSet, "port_");
                    excursion.setSeaport(seaport);
                    resultList.add(excursion);
                }
            }
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
            throw new DAOLevelException(e);
        }

        return resultList;
    }

    @Override
    public boolean update(Excursion entity) {
        try(Connection connection = ConnectionPoolHolder.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_EXCURSION)) {

            preparedStatement.setString(1, entity.getNameEn());
            preparedStatement.setString(2, entity.getNameUkr());
            preparedStatement.setString(3, entity.getDescriptionEn());
            preparedStatement.setString(4, entity.getDescriptionUkr());
            preparedStatement.setLong(5, entity.getApproximateDurationHr());
            preparedStatement.setBigDecimal(6, entity.getPriceUSD());
            preparedStatement.setLong(7, entity.getSeaport().getId());
            preparedStatement.setLong(8, entity.getId());

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
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE_EXCURSION_BY_ID)) {

            preparedStatement.setLong(1, id);
            return 1 == preparedStatement.executeUpdate(); //one row was deleted
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
            throw new DAOLevelException(e);
        }
    }
}
