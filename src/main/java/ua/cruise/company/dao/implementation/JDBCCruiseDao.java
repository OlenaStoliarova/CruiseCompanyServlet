package ua.cruise.company.dao.implementation;

import ua.cruise.company.dao.CruiseDao;
import ua.cruise.company.entity.Cruise;
import ua.cruise.company.utility.Page;
import ua.cruise.company.utility.PaginationSettings;

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

    private static final String UPDATE_CRUISE = "UPDATE " + TABLE + " SET " +
            COLUMN_SHIP_ID + "=?, " + COLUMN_START_DATE + "=?, " +
            COLUMN_VACANCIES + "=?, "  + COLUMN_PRICE + "=? " +
            "WHERE " + COLUMN_ID + "=?";

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
        return executeCUDQuery(UPDATE_CRUISE, preparedStatement ->{
            preparedStatement.setLong(1, entity.getShip().getId());
            preparedStatement.setDate(2, Date.valueOf(entity.getStartingDate()));
            preparedStatement.setInt(3, entity.getVacancies());
            preparedStatement.setBigDecimal(4, entity.getPrice());
            preparedStatement.setLong(5, entity.getId());
        });
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
