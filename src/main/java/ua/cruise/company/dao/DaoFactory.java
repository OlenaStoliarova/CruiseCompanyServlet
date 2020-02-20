package ua.cruise.company.dao;

import ua.cruise.company.dao.implementation.JDBCDaoFactory;

public abstract class DaoFactory {
    private static DaoFactory daoFactory;

    public static DaoFactory getInstance() {
        if (daoFactory == null) {
            synchronized (DaoFactory.class) {
                if (daoFactory == null) {
                    daoFactory = new JDBCDaoFactory();
                }
            }
        }
        return daoFactory;
    }

    public abstract UserDao createUserDao();

    public abstract SeaportDao createSeaportDao();

    public abstract ExcursionDao createExcursionDao();

    public abstract ExtraDao createExtraDao();

    public abstract ShipDao createShipDao();

    public abstract CruiseDao createCruiseDao();

    public abstract OrderDao createOrderDao();
}