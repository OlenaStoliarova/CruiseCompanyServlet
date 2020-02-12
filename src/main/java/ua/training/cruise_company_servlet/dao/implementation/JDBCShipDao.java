package ua.training.cruise_company_servlet.dao.implementation;

import ua.training.cruise_company_servlet.dao.ShipDao;
import ua.training.cruise_company_servlet.entity.Ship;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class JDBCShipDao extends JDBCAbstractDao<Ship> implements ShipDao {
    private static final String TABLE = "ship";
    private static final String COLUMN_SHIP_NAME = "name";
    private static final String COLUMN_CAPACITY = "capacity";
    private static final String COLUMN_ROUTE_NAME_EN = "route_name_en";
    private static final String COLUMN_ROUTE_NAME_UKR = "route_name_ukr";
    private static final String COLUMN_TRIP_DURATION = "one_trip_duration_days";

    private static final String SELECT_SHIP_BY_ID = "SELECT * FROM " + TABLE + " WHERE " + COLUMN_ID + "=?";
    private static final String SELECT_ALL_SHIPS = "SELECT * FROM " + TABLE;

    @Override
    public boolean create(Ship entity) {
        //TODO: add ability to add ships
        throw new UnsupportedOperationException("not implemented yet");
    }

    @Override
    public Optional<Ship> findById(long id) {
        return selectOne(SELECT_SHIP_BY_ID,
                preparedStatement -> preparedStatement.setLong(1, id),
                new ShipMapper());
    }

    @Override
    public List<Ship> findAll() {
        return selectMany(SELECT_ALL_SHIPS, preparedStatement -> {}, new ShipMapper());
    }

    @Override
    public boolean update(Ship entity) {
        //TODO: add ability to edit ship info
        throw new UnsupportedOperationException("not implemented yet");
    }

    private static class ShipMapper implements ObjectMapper<Ship> {
        @Override
        public Ship extractFromResultSet(ResultSet rs) throws SQLException {
            Ship result = new Ship();

            result.setId( rs.getLong(COLUMN_ID) );
            result.setName( rs.getString(COLUMN_SHIP_NAME) );
            result.setCapacity( rs.getInt(COLUMN_CAPACITY) );
            result.setRouteNameEn(rs.getString(COLUMN_ROUTE_NAME_EN));
            result.setRouteNameUkr(rs.getString(COLUMN_ROUTE_NAME_UKR));
            result.setOneTripDurationDays(rs.getInt(COLUMN_TRIP_DURATION));

            return result;
        }
    }
}
