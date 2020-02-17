 package ua.cruise.company.dao.implementation;

 import ua.cruise.company.dao.SeaportDao;
 import ua.cruise.company.entity.Seaport;

 import java.sql.ResultSet;
 import java.sql.SQLException;
 import java.util.List;
 import java.util.Optional;

public class JDBCSeaportDao extends JDBCAbstractDao<Seaport> implements SeaportDao {
    private static final String TABLE = "seaport";
    private static final String COLUMN_NAME_EN = "name_en";
    private static final String COLUMN_COUNTRY_EN = "country_en";
    private static final String COLUMN_NAME_UKR = "name_ukr";
    private static final String COLUMN_COUNTRY_UKR = "country_ukr";

    private static final String INSERT_NEW_PORT = "INSERT INTO " + TABLE +
                                                  " (" + COLUMN_NAME_EN + ", " + COLUMN_COUNTRY_EN + ", " +
                                                    COLUMN_NAME_UKR + ", " + COLUMN_COUNTRY_UKR + ") VALUES (?, ?, ?, ?)";

    private static final String SELECT_PORT_BY_ID = "SELECT * FROM " + TABLE + " WHERE " + COLUMN_ID + "=?";
    private static final String SELECT_PORT_BY_NAME_EN = "SELECT * FROM " + TABLE + " WHERE " + COLUMN_NAME_EN + "=?";
    private static final String SELECT_ALL_PORTS = "SELECT * FROM " + TABLE;
    private static final String DELETE_PORT_BY_ID = "DELETE FROM " + TABLE + " WHERE " + COLUMN_ID + "=?";

    private static final String UPDATE_PORT = "UPDATE " + TABLE + " SET " +
                                                COLUMN_NAME_EN + "=?, " + COLUMN_COUNTRY_EN + "=?, " +
                                                COLUMN_NAME_UKR + "=?, " + COLUMN_COUNTRY_UKR + "=? " +
                                                "WHERE " + COLUMN_ID + "=?";

    private static final String SELECT_ALL_PORTS_OF_SHIP_ROUTE = "SELECT p.* FROM " + TABLE + " AS p " +
                                                                    "INNER JOIN ship_route_seaport AS srp " +
                                                                    "ON srp.seaport_id=p." + COLUMN_ID +
                                                                    " WHERE srp.ship_id=?";
    @Override
    public boolean create(Seaport entity) {
        return executeCUDQuery(INSERT_NEW_PORT, preparedStatement -> {
            preparedStatement.setString(1, entity.getNameEn());
            preparedStatement.setString(2, entity.getCountryEn());
            preparedStatement.setString(3, entity.getNameUkr());
            preparedStatement.setString(4, entity.getCountryUkr());
        });
    }

    @Override
    public Optional<Seaport> findById(long id) {
        return selectOne(SELECT_PORT_BY_ID,
                preparedStatement -> preparedStatement.setLong(1, id),
                new SeaportMapper());
    }

    @Override
    public Optional<Seaport> findByNameEn(String  nameEn) {
        return selectOne(SELECT_PORT_BY_NAME_EN,
                preparedStatement -> preparedStatement.setString(1, nameEn),
                new SeaportMapper());
    }

    @Override
    public List<Seaport> findAll() {
        return selectMany(SELECT_ALL_PORTS, preparedStatement -> {}, new SeaportMapper());
    }

    @Override
    public List<Seaport> findAllOfShipRoute(long shipId) {
        return selectMany(SELECT_ALL_PORTS_OF_SHIP_ROUTE,
                            preparedStatement -> preparedStatement.setLong(1, shipId),
                            new SeaportMapper());
    }

    @Override
    public boolean update(Seaport entity) {
        return executeCUDQuery(UPDATE_PORT, preparedStatement ->{
            preparedStatement.setString(1, entity.getNameEn());
            preparedStatement.setString(2, entity.getCountryEn());
            preparedStatement.setString(3, entity.getNameUkr());
            preparedStatement.setString(4, entity.getCountryUkr());
            preparedStatement.setLong(5, entity.getId());
        });
    }

    @Override
    public boolean delete(long id) {
        return executeCUDQuery(DELETE_PORT_BY_ID,
                                preparedStatement ->  preparedStatement.setLong(1, id));
    }


    private static class SeaportMapper implements ObjectMapper<Seaport> {
        @Override
        public Seaport extractFromResultSet(ResultSet rs) throws SQLException {
            Seaport result = new Seaport();

            result.setId( rs.getLong(COLUMN_ID) );
            result.setNameEn( rs.getString(COLUMN_NAME_EN) );
            result.setCountryEn( rs.getString(COLUMN_COUNTRY_EN));
            result.setNameUkr( rs.getString(COLUMN_NAME_UKR));
            result.setCountryUkr( rs.getString(COLUMN_COUNTRY_UKR));

            return result;
        }
    }
}
