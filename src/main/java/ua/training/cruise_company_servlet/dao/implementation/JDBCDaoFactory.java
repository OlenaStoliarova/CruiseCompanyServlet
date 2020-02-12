package ua.training.cruise_company_servlet.dao.implementation;

import ua.training.cruise_company_servlet.dao.*;

public class JDBCDaoFactory extends DaoFactory {
    @Override
    public UserDao createUserDao() {
        return new JDBCUserDao();
    }

    @Override
    public SeaportDao createSeaportDao() {
        return new JDBCSeaportDao();
    }

    @Override
    public ExcursionDao createExcursionDao() {
        return new JDBCExcursionDao();
    }

    @Override
    public ExtraDao createExtraDao() {
        return new JDBCExtraDao();
    }

    @Override
    public ShipDao createShipDao() {
        return new JDBCShipDao();
    }
}
