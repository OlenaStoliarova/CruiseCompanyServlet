package ua.training.cruise_company_servlet.dao.implementation;

import ua.training.cruise_company_servlet.dao.CruiseDao;
import ua.training.cruise_company_servlet.entity.Cruise;
import ua.training.cruise_company_servlet.utility.Page;
import ua.training.cruise_company_servlet.utility.PaginationSettings;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class JDBCCruiseDao extends JDBCAbstractDao<Cruise> implements CruiseDao {
    private static final String TABLE = "cruise";
    private static final String COLUMN_SHIP_ID = "ship_id";
    private static final String COLUMN_START_DATE = "starting_date";
    private static final String COLUMN_VACANCIES = "vacancies";
    private static final String COLUMN_PRICE = "price";

    private static final String SELECT_CRUISE_BY_ID = "SELECT * FROM " + TABLE + " WHERE " + COLUMN_ID + "=?";
    private static final String SELECT_ALL_CRUISES_ORDER_BY_STARTING_DATE = "SELECT * FROM " + TABLE +
                                                                            " ORDER BY " + COLUMN_START_DATE + " ASC";

    private static final String SELECT_ALL_AVAILABLE_FUTURE_CRUISES_ORDER_BY_STARTING_DATE = "SELECT * FROM " + TABLE +
            " WHERE " + COLUMN_START_DATE + ">=? AND " + COLUMN_VACANCIES + ">0" +
            " ORDER BY " + COLUMN_START_DATE + " ASC";


    @Override
    public boolean create(Cruise entity) {
        //TODO: add ability to add cruises
        throw new UnsupportedOperationException("not implemented yet");
    }

    @Override
    public Optional<Cruise> findById(long id) {
        return selectOne(SELECT_CRUISE_BY_ID,
                preparedStatement -> preparedStatement.setLong(1, id),
                new CruiseMapper());
    }

    @Override
    public List<Cruise> findAll() {
        return selectMany(SELECT_ALL_CRUISES_ORDER_BY_STARTING_DATE,
                            preparedStatement -> {}, new CruiseMapper());
    }

    @Override
    public Page<Cruise> findAllAvailableAfterDate(LocalDate date, PaginationSettings paginationSettings) {
        return selectMany(SELECT_ALL_AVAILABLE_FUTURE_CRUISES_ORDER_BY_STARTING_DATE,
                preparedStatement -> preparedStatement.setDate(1, Date.valueOf( date)),
                new CruiseMapper(),
                paginationSettings);
    }

    @Override
    public boolean update(Cruise entity) {
        //TODO: add ability to edit cruise
        throw new UnsupportedOperationException("not implemented yet");
    }

    private static class CruiseMapper implements ObjectMapper<Cruise> {
        @Override
        public Cruise extractFromResultSet(ResultSet rs) throws SQLException {
            Cruise result = new Cruise();

            result.setId( rs.getLong(COLUMN_ID) );
            result.getShip().setId( rs.getLong(COLUMN_SHIP_ID));
            result.setStartingDate( rs.getDate(COLUMN_START_DATE).toLocalDate() );
            result.setVacancies(rs.getInt(COLUMN_VACANCIES));
            result.setPrice(rs.getBigDecimal(COLUMN_PRICE));

            return result;
        }
    }
}
