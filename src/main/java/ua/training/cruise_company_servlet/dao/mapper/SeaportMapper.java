package ua.training.cruise_company_servlet.dao.mapper;

import ua.training.cruise_company_servlet.entity.Seaport;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SeaportMapper implements ObjectMapper<Seaport> {
    @Override
    public Seaport extractFromResultSet(ResultSet rs) throws SQLException {
        return extractFromResultSet(rs, "");
    }

    public Seaport extractFromResultSet(ResultSet rs, String prefix) throws SQLException {
        Seaport result = new Seaport();

        result.setId( rs.getLong(prefix + "id") );
        result.setNameEn( rs.getString(prefix + "name_en") );
        result.setCountryEn( rs.getString(prefix + "country_en"));
        result.setNameUkr( rs.getString(prefix + "name_ukr") );
        result.setCountryUkr( rs.getString(prefix + "country_ukr") );

        return result;
    }
}
