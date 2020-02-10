package ua.training.cruise_company_servlet.dao.mapper;

import ua.training.cruise_company_servlet.entity.User;
import ua.training.cruise_company_servlet.enums.UserRole;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserMapper implements ObjectMapper<User>{
    @Override
    public User extractFromResultSet(ResultSet rs) throws SQLException {
        return extractFromResultSet(rs, "");
    }

    public User extractFromResultSet(ResultSet rs, String prefix) throws SQLException {
        User result = new User();

        result.setId( rs.getLong("id") );
        result.setEmail( rs.getString("email") );
        result.setPassword( rs.getString("password"));
        result.setFirstNameEn( rs.getString("first_name_en") );
        result.setLastNameEn( rs.getString("last_name_en") );
        result.setFirstNameNative( rs.getString("first_name_native") );
        result.setLastNameNative( rs.getString("last_name_native") );
        result.setRole( UserRole.valueOf( rs.getString("role") ));

        return result;
    }
}
