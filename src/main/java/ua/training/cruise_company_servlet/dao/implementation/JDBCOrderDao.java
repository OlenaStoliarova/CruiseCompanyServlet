package ua.training.cruise_company_servlet.dao.implementation;

import ua.training.cruise_company_servlet.dao.OrderDao;
import ua.training.cruise_company_servlet.entity.Order;
import ua.training.cruise_company_servlet.enums.OrderStatus;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
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
                                                                            " ORDER BY " + COLUMN_DATE + " ASC";
    private static final String SELECT_USER_ORDERS_ORDER_BY_CREATION_DATE =
                    "SELECT * FROM " + TABLE +
                    " WHERE " + COLUMN_USER_ID + "=?" +
                    " ORDER BY " + COLUMN_DATE + " DESC";

    private static final String UPDATE_ORDER = "UPDATE " + TABLE + " SET " +
            COLUMN_DATE + "=?, " + COLUMN_USER_ID + "=?, " + COLUMN_CRUISE_ID + "=?, " +
            COLUMN_QUANTITY + "=?, " + COLUMN_PRICE + "=?, " + COLUMN_STATUS + "=? " +
            "WHERE " + COLUMN_ID + "=?";

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
