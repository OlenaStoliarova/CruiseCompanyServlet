package ua.training.cruise_company_servlet.dao.mapper;

import ua.training.cruise_company_servlet.entity.Excursion;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ExcursionMapper implements ObjectMapper<Excursion> {
    @Override
    public Excursion extractFromResultSet(ResultSet rs) throws SQLException {
        return extractFromResultSet(rs, "");
    }

    public Excursion extractFromResultSet(ResultSet rs, String prefix) throws SQLException {
        Excursion result = new Excursion();

        result.setId( rs.getLong(prefix + "id") );
        result.setNameEn( rs.getString(prefix + "name_en") );
        result.setNameUkr( rs.getString(prefix + "name_ukr") );
        result.setDescriptionEn(rs.getString(prefix + "description_en"));
        result.setDescriptionUkr(rs.getString(prefix + "description_ukr"));
        result.setApproximateDurationHr(rs.getLong(prefix + "approximate_duration_hr"));
        result.setPriceUSD( rs.getBigDecimal(prefix + "priceusd"));

        return result;
    }
}
