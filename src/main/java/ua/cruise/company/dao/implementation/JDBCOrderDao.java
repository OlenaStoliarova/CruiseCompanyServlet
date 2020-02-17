package ua.cruise.company.dao.implementation;

import ua.cruise.company.dao.OrderDao;
import ua.cruise.company.entity.Order;
import ua.cruise.company.enums.OrderStatus;
import ua.cruise.company.utility.Page;
import ua.cruise.company.utility.PaginationSettings;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class JDBCOrderDao extends JDBCAbstractDao<Order> implements OrderDao {
    private static final String TABLE = "orders";
    private static final String COLUMN_DATE = "creation_date";
    private static final String COLUMN_USER_ID = "user_id";
    private static final String COLUMN_CRUISE_ID = "cruise_id";
    private static final String COLUMN_QUANTITY = "quantity";
    private static final String COLUMN_PRICE = "total_price";
    private static final String COLUMN_STATUS = "status";

    private static final String INSERT_NEW_ORDER = "INSERT INTO " + TABLE +
            " (" + COLUMN_DATE + ", " + COLUMN_USER_ID + ", " + COLUMN_CRUISE_ID + ", " +
            COLUMN_QUANTITY + ", " + COLUMN_PRICE + ", " + COLUMN_STATUS + ") " +
            "VALUES (?, ?, ?, ?, ?, ?)";

    private static final String SELECT_ORDER_BY_ID = "SELECT * FROM " + TABLE + " WHERE " + COLUMN_ID + "=?";
    private static final String SELECT_ALL_ORDERS_ORDER_BY_CREATION_DATE = "SELECT * FROM " + TABLE +
                                                                            " ORDER BY " + COLUMN_DATE + " DESC";
    private static final String SELECT_USER_ORDERS_ORDER_BY_CREATION_DATE =
                    "SELECT * FROM " + TABLE +
                    " WHERE " + COLUMN_USER_ID + "=?" +
                    " ORDER BY " + COLUMN_DATE + " DESC";

    private static final String UPDATE_ORDER = "UPDATE " + TABLE + " SET " +
            COLUMN_DATE + "=?, " + COLUMN_USER_ID + "=?, " + COLUMN_CRUISE_ID + "=?, " +
            COLUMN_QUANTITY + "=?, " + COLUMN_PRICE + "=?, " + COLUMN_STATUS + "=? " +
            "WHERE " + COLUMN_ID + "=?";

    private static final String INSERT_ORDER_EXCURSIONS = "INSERT INTO order_excursion (order_id , excursion_id)" +
                                                            " VALUES order_excursions_list";

    private static final String INSERT_ORDER_EXTRAS = "INSERT INTO order_extras (order_id , extra_id)" +
            " VALUES order_extras_list";

    @Override
    public boolean create(Order entity) {
        return executeCUDQuery(INSERT_NEW_ORDER, preparedStatement ->{
            preparedStatement.setDate(1, Date.valueOf(entity.getCreationDate()));
            preparedStatement.setLong(2, entity.getUser().getId());
            preparedStatement.setLong(3, entity.getCruise().getId());
            preparedStatement.setInt(4, entity.getQuantity());
            preparedStatement.setBigDecimal(5, entity.getTotalPrice());
            preparedStatement.setString(6, entity.getStatus().toString());
        });
    }

    @Override
    public Optional<Order> findById(long id) {
        return selectOne(SELECT_ORDER_BY_ID,
                preparedStatement -> preparedStatement.setLong(1, id),
                new OrderMapper());
    }

    @Override
    public List<Order> findByUserId(long userId) {
        return selectMany(SELECT_USER_ORDERS_ORDER_BY_CREATION_DATE,
                preparedStatement -> preparedStatement.setLong(1, userId),
                new OrderMapper());
    }

    @Override
    public List<Order> findAll() {
        return selectMany(SELECT_ALL_ORDERS_ORDER_BY_CREATION_DATE,
                preparedStatement -> {}, new OrderMapper());
    }

    @Override
    public Page<Order> findAll(PaginationSettings paginationSettings) {
        return selectMany(SELECT_ALL_ORDERS_ORDER_BY_CREATION_DATE,
                preparedStatement -> {},
                new OrderMapper(),
                paginationSettings);
    }

    @Override
    public boolean update(Order entity) {
        return executeCUDQuery(UPDATE_ORDER, preparedStatement ->{
            preparedStatement.setDate(1, Date.valueOf(entity.getCreationDate()));
            preparedStatement.setLong(2, entity.getUser().getId());
            preparedStatement.setLong(3, entity.getCruise().getId());
            preparedStatement.setInt(4, entity.getQuantity());
            preparedStatement.setBigDecimal(5, entity.getTotalPrice());
            preparedStatement.setString(6, entity.getStatus().toString());
            preparedStatement.setLong(7, entity.getId());
        });
    }

    @Override
    public boolean addExcursionsToOrder(long orderId, List<Long> excursionsIds) {
        String placeHolders = String.join(",", Collections.nCopies(excursionsIds.size(), "(?,?)"));
        String queryWithCorrectInsertValues = INSERT_ORDER_EXCURSIONS.replaceAll("order_excursions_list", placeHolders);
        return insertManyToManyDependency(orderId, excursionsIds, queryWithCorrectInsertValues);
    }

    @Override
    public boolean addExtrasToOrder(long orderId, List<Long> extrasIds) {
        String placeHolders = String.join(",", Collections.nCopies(extrasIds.size(), "(?,?)"));
        String queryWithCorrectInsertValues = INSERT_ORDER_EXTRAS.replaceAll("order_extras_list", placeHolders);
        return insertManyToManyDependency(orderId, extrasIds, queryWithCorrectInsertValues);
    }

    private boolean insertManyToManyDependency(long orderId, List<Long> dependencyIds, String insertQuery) {
        return executeCUDQuery(insertQuery,
                preparedStatement -> {
                    for (int i = 0; i < dependencyIds.size(); i++) {
                        preparedStatement.setLong(2*i + 1, orderId);
                        preparedStatement.setLong(2*i + 2, dependencyIds.get(i));
                    }
                },
                dependencyIds.size());
    }


    private static class OrderMapper implements ObjectMapper<Order> {
        @Override
        public Order extractFromResultSet(ResultSet rs) throws SQLException {
            Order result = new Order();

            result.setId( rs.getLong(COLUMN_ID) );
            result.getUser().setId( rs.getLong(COLUMN_USER_ID) );
            result.getCruise().setId( rs.getLong(COLUMN_CRUISE_ID) );
            result.setCreationDate( rs.getDate(COLUMN_DATE).toLocalDate() );
            result.setQuantity( rs.getInt(COLUMN_QUANTITY));
            result.setTotalPrice(rs.getBigDecimal(COLUMN_PRICE));
            result.setStatus( OrderStatus.valueOf(rs.getString(COLUMN_STATUS)) );

            return result;
        }
    }
}
