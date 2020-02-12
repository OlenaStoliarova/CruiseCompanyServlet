package ua.training.cruise_company_servlet.dao.implementation;

import ua.training.cruise_company_servlet.dao.ExtraDao;
import ua.training.cruise_company_servlet.entity.Extra;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class JDBCExtraDao extends JDBCAbstractDao<Extra> implements ExtraDao {
    private static final String TABLE = "extras";
    private static final String COLUMN_NAME_EN = "name_en";
    private static final String COLUMN_NAME_UKR = "name_ukr";

    private static final String SELECT_EXTRA_BY_ID = "SELECT * FROM " + TABLE + " WHERE " + COLUMN_ID + "=?";
    private static final String SELECT_ALL_EXTRAS = "SELECT * FROM " + TABLE;
    private static final String SELECT_ALL_EXTRAS_ORDER_BY_NAME_EN = "SELECT * FROM " + TABLE + " ORDER BY " + COLUMN_NAME_EN + " ASC";

    private static final String SELECT_ALL_EXTRAS_OF_SHIP = "SELECT e.* FROM " + TABLE + " AS e " +
                                                            "INNER JOIN ship_extras AS se " +
                                                            "ON se.extra_id=e." + COLUMN_ID +
                                                            " WHERE se.ship_id=?" +
                                                            " ORDER BY " + COLUMN_NAME_EN;
    @Override
    public boolean create(Extra entity) {
        //TODO: add ability to add extras
        throw new UnsupportedOperationException("not implemented yet");
    }

    @Override
    public Optional<Extra> findById(long id) {
        return selectOne(SELECT_EXTRA_BY_ID,
                preparedStatement -> preparedStatement.setLong(1, id),
                new ExtraMapper());
    }

    @Override
    public List<Extra> findAll() {
        return selectMany(SELECT_ALL_EXTRAS, preparedStatement -> {}, new ExtraMapper());
    }

    @Override
    public List<Extra> findAllOrderByNameEn() {
        return selectMany(SELECT_ALL_EXTRAS_ORDER_BY_NAME_EN, preparedStatement -> {}, new ExtraMapper());
    }

    @Override
    public List<Extra> findAllByShipId(long shipId) {
        return selectMany(SELECT_ALL_EXTRAS_OF_SHIP,
                            preparedStatement -> preparedStatement.setLong(1, shipId),
                            new ExtraMapper());
    }

    @Override
    public boolean update(Extra entity) {
        //TODO: add ability to edit extras
        throw new UnsupportedOperationException("not implemented yet");
    }

    private static class ExtraMapper implements ObjectMapper<Extra> {
        @Override
        public Extra extractFromResultSet(ResultSet rs) throws SQLException {
            Extra result = new Extra();

            result.setId( rs.getLong(COLUMN_ID) );
            result.setNameEn( rs.getString(COLUMN_NAME_EN) );
            result.setNameUkr( rs.getString(COLUMN_NAME_UKR));

            return result;
        }
    }
}
