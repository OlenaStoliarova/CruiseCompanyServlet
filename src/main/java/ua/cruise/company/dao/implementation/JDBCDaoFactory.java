package ua.cruise.company.dao.implementation;

import ua.cruise.company.dao.*;

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

    @Override
    public CruiseDao createCruiseDao() {
        return new JDBCCruiseDao();
    }

    @Override
    public OrderDao createOrderDao() {
        return new JDBCOrderDao();
    }
}
