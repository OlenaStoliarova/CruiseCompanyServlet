package ua.training.cruise_company_servlet.dao.implementation;

import ua.training.cruise_company_servlet.dao.ExcursionDao;
import ua.training.cruise_company_servlet.entity.Excursion;
import ua.training.cruise_company_servlet.utility.Page;
import ua.training.cruise_company_servlet.utility.PaginationSettings;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class JDBCExcursionDao extends JDBCAbstractDao<Excursion> implements ExcursionDao {
    private static final String TABLE = "excursion";
    private static final String COLUMN_NAME_EN = "name_en";
    private static final String COLUMN_NAME_UKR = "name_ukr";
    private static final String COLUMN_DESCRIPTION_EN = "description_en";
    private static final String COLUMN_DESCRIPTION_UKR = "description_ukr";
    private static final String COLUMN_DURATION = "approximate_duration_hr";
    private static final String COLUMN_PRICE = "priceusd";
    private static final String COLUMN_SEAPORT_ID = "seaport_id";

    private static final String INSERT_NEW_EXCURSION = "INSERT INTO " + TABLE +
            " (" + COLUMN_NAME_EN + ", " + COLUMN_NAME_UKR + ", " +
            COLUMN_DESCRIPTION_EN + ", " + COLUMN_DESCRIPTION_UKR + ", " +
            COLUMN_DURATION + ", " + COLUMN_PRICE + ", " + COLUMN_SEAPORT_ID + ") " +
            "VALUES (?, ?, ?, ?, ?, ?, ?)";

    private static final String SELECT_EXCURSION_BY_ID = "SELECT * FROM " + TABLE + " WHERE " + COLUMN_ID + "=?";
    private static final String SELECT_ALL_EXCURSIONS = "SELECT * FROM " + TABLE;
    private static final String SELECT_ALL_EXCURSIONS_ORDER_BY_PRICE = "SELECT * FROM " + TABLE + " ORDER BY " + COLUMN_PRICE + " DESC";
    private static final String SELECT_ALL_EXCURSIONS_IN_SEAPORT_ORDER_BY_NAME_EN = "SELECT * FROM " + TABLE +
                                                                                    " WHERE " + COLUMN_SEAPORT_ID + "=?" +
                                                                                    " ORDER BY " + COLUMN_NAME_EN + " ASC";

    private static final String SELECT_ALL_EXCURSIONS_OF_ORDER = "SELECT e.* FROM " + TABLE + " AS e " +
            "INNER JOIN order_excursion AS oe " +
            "ON oe.excursion_id=e." + COLUMN_ID +
            " WHERE oe.order_id=?" +
            " ORDER BY " + COLUMN_NAME_EN;

    private static final String SELECT_ALL_EXCURSIONS_BY_SEAPORT_IDS = "SELECT * FROM " + TABLE +
            " WHERE " + COLUMN_SEAPORT_ID + " IN (seaport_ids_list)";


    private static final String DELETE_EXCURSION_BY_ID = "DELETE FROM " + TABLE + " WHERE " + COLUMN_ID + "=?";

    private static final String UPDATE_EXCURSION = "UPDATE " + TABLE + " SET " +
            COLUMN_NAME_EN + "=?, " + COLUMN_NAME_UKR + "=?, " +
            COLUMN_DESCRIPTION_EN + "=?, " + COLUMN_DESCRIPTION_UKR + "=?, " +
            COLUMN_DURATION + "=?, " + COLUMN_PRICE + "=?, " + COLUMN_SEAPORT_ID + "=? " +
            "WHERE " + COLUMN_ID + "=?";

    @Override
    public boolean create(Excursion entity) {
        return executeCUDQuery(INSERT_NEW_EXCURSION, preparedStatement -> {
            preparedStatement.setString(1, entity.getNameEn());
            preparedStatement.setString(2, entity.getNameUkr());
            preparedStatement.setString(3, entity.getDescriptionEn());
            preparedStatement.setString(4, entity.getDescriptionUkr());
            preparedStatement.setLong(5, entity.getApproximateDurationHr());
            preparedStatement.setBigDecimal(6, entity.getPriceUSD());
            preparedStatement.setLong(7, entity.getSeaport().getId());
        });
    }

    @Override
    public Optional<Excursion> findById(long id) {
        return selectOne(SELECT_EXCURSION_BY_ID,
                        preparedStatement -> preparedStatement.setLong(1, id),
                        new ExcursionMapper());
    }

    @Override
    public List<Excursion> findAll() {
        return selectMany(SELECT_ALL_EXCURSIONS, preparedStatement -> {}, new ExcursionMapper());
    }

    @Override
    public Page<Excursion> findAllOrderByPrice(PaginationSettings paginationSettings) {
        return selectMany(SELECT_ALL_EXCURSIONS_ORDER_BY_PRICE,
                            preparedStatement -> {},
                            new ExcursionMapper(),
                            paginationSettings);
    }

    @Override
    public List<Excursion> findAllByOrderId(long orderId) {
        return selectMany(SELECT_ALL_EXCURSIONS_OF_ORDER,
                preparedStatement -> preparedStatement.setLong(1, orderId),
                new ExcursionMapper());
    }

    @Override
    public Page<Excursion> findBySeaportIdOrderByNameEn(long seaportId, PaginationSettings paginationSettings) {
        return selectMany(SELECT_ALL_EXCURSIONS_IN_SEAPORT_ORDER_BY_NAME_EN,
                preparedStatement -> preparedStatement.setLong(1, seaportId),
                new ExcursionMapper(),
                paginationSettings);
    }

    @Override
    public List<Excursion> findAllBySeaportIds(List<Long> seaportIds) {
        String placeHolders = String.join(",", Collections.nCopies(seaportIds.size(), "?"));
        String queryWithCorrectWhereIn = SELECT_ALL_EXCURSIONS_BY_SEAPORT_IDS.replaceAll("seaport_ids_list", placeHolders);

        return selectMany(queryWithCorrectWhereIn,
                preparedStatement -> {
                    for (int i = 0; i < seaportIds.size(); i++) {
                        preparedStatement.setLong(i + 1, seaportIds.get(i));
                    }
                },
                new ExcursionMapper());
    }

    @Override
    public boolean update(Excursion entity) {
        return executeCUDQuery(UPDATE_EXCURSION, preparedStatement ->{
            preparedStatement.setString(1, entity.getNameEn());
            preparedStatement.setString(2, entity.getNameUkr());
            preparedStatement.setString(3, entity.getDescriptionEn());
            preparedStatement.setString(4, entity.getDescriptionUkr());
            preparedStatement.setLong(5, entity.getApproximateDurationHr());
            preparedStatement.setBigDecimal(6, entity.getPriceUSD());
            preparedStatement.setLong(7, entity.getSeaport().getId());
            preparedStatement.setLong(8, entity.getId());
        });
    }

    @Override
    public boolean delete(long id) {
        return executeCUDQuery(DELETE_EXCURSION_BY_ID,
                                preparedStatement ->  preparedStatement.setLong(1, id));
    }


    private static class ExcursionMapper implements ObjectMapper<Excursion> {
        @Override
        public Excursion extractFromResultSet(ResultSet rs) throws SQLException {
            Excursion result = new Excursion();

            result.setId( rs.getLong(COLUMN_ID) );
            result.setNameEn( rs.getString(COLUMN_NAME_EN) );
            result.setNameUkr( rs.getString(COLUMN_NAME_UKR) );
            result.setDescriptionEn(rs.getString(COLUMN_DESCRIPTION_EN));
            result.setDescriptionUkr(rs.getString(COLUMN_DESCRIPTION_UKR));
            result.setApproximateDurationHr(rs.getLong(COLUMN_DURATION));
            result.setPriceUSD( rs.getBigDecimal(COLUMN_PRICE));
            result.getSeaport().setId(rs.getLong(COLUMN_SEAPORT_ID));

            return result;
        }
    }
}
