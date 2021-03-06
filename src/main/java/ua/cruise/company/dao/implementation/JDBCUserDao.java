package ua.cruise.company.dao.implementation;

import ua.cruise.company.dao.UserDao;
import ua.cruise.company.entity.User;
import ua.cruise.company.enums.UserRole;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class JDBCUserDao extends JDBCAbstractDao<User> implements UserDao {
    private static final String TABLE = "users";
    private static final String COLUMN_EMAIL = "email";
    private static final String COLUMN_PASSWORD = "password";
    private static final String COLUMN_FIRST_NAME_EN = "first_name_en";
    private static final String COLUMN_LAST_NAME_EN = "last_name_en";
    private static final String COLUMN_FIRST_NAME_NATIVE = "first_name_native";
    private static final String COLUMN_LAST_NAME_NATIVE = "last_name_native";
    private static final String COLUMN_ROLE = "role";

    private static final String INSERT_NEW_USER = "INSERT INTO " + TABLE +
            " (" + COLUMN_EMAIL + ", " + COLUMN_PASSWORD + ", " +
            COLUMN_FIRST_NAME_EN + ", " + COLUMN_LAST_NAME_EN + ", " +
            COLUMN_FIRST_NAME_NATIVE + ", " + COLUMN_LAST_NAME_NATIVE + ", " +
            COLUMN_ROLE + ") VALUES (?, ?, ?, ?, ?, ?, ?)";

    private static final String SELECT_USER_BY_EMAIL = "SELECT * FROM " + TABLE + " WHERE " + COLUMN_EMAIL + "=?";
    private static final String SELECT_USER_BY_ID = "SELECT * FROM " + TABLE + " WHERE " + COLUMN_ID + "=?";
    private static final String SELECT_ALL_USERS = "SELECT * FROM " + TABLE;
    private static final String UPDATE_USER_ROLE = "UPDATE " + TABLE + " SET " + COLUMN_ROLE + "=? WHERE " + COLUMN_EMAIL + "=?";

    @Override
    public boolean create(User entity) {
        return executeCUDQuery(INSERT_NEW_USER, preparedStatement -> {
            preparedStatement.setString(1, entity.getEmail());
            preparedStatement.setString(2, entity.getPassword());
            preparedStatement.setString(3, entity.getFirstNameEn());
            preparedStatement.setString(4, entity.getLastNameEn());
            preparedStatement.setString(5, entity.getFirstNameNative());
            preparedStatement.setString(6, entity.getLastNameNative());
            preparedStatement.setString(7, entity.getRole().toString());
        });
    }

    @Override
    public Optional<User> findById(long id) {
        return selectOne(SELECT_USER_BY_ID,
                 preparedStatement -> preparedStatement.setLong(1, id),
                 new UserMapper());
    }

    @Override
    public Optional<User> findByEmail(String email){
        return selectOne(SELECT_USER_BY_EMAIL,
                preparedStatement -> preparedStatement.setString(1, email),
                new UserMapper());
    }

    @Override
    public List<User> findAll(){
        return selectMany(SELECT_ALL_USERS, preparedStatement -> {}, new UserMapper());
    }

    public boolean updateUserRole(String email, UserRole newRole){
        return executeCUDQuery(UPDATE_USER_ROLE, preparedStatement -> {
            preparedStatement.setString(1, newRole.toString());
            preparedStatement.setString(2, email);
        });
    }

    @Override
    public boolean update(User entity) {
        //TODO: add ability to edit user profile
        throw new UnsupportedOperationException("not implemented yet");
    }

    private static class UserMapper implements ObjectMapper<User> {

        @Override
        public User extractFromResultSet(ResultSet rs) throws SQLException {
            User result = new User();

            result.setId( rs.getLong(COLUMN_ID) );
            result.setEmail( rs.getString(COLUMN_EMAIL) );
            result.setPassword( rs.getString(COLUMN_PASSWORD));
            result.setFirstNameEn( rs.getString(COLUMN_FIRST_NAME_EN) );
            result.setLastNameEn( rs.getString(COLUMN_LAST_NAME_EN) );
            result.setFirstNameNative( rs.getString(COLUMN_FIRST_NAME_NATIVE) );
            result.setLastNameNative( rs.getString(COLUMN_LAST_NAME_NATIVE) );
            result.setRole( UserRole.valueOf( rs.getString(COLUMN_ROLE) ));

            return result;
        }
    }
}


