package ua.cruise.company.persistence;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.cruise.company.dao.DAOLevelException;

import java.sql.SQLException;

public class TransactionManager {
    private static final Logger LOG = LogManager.getLogger(TransactionManager.class);

    private static ThreadLocal<ConnectionWrapper> connectionWrapThreadLocal = new ThreadLocal<>();

    public static ConnectionWrapper getConnection() {
        ConnectionWrapper connectionWrapper = connectionWrapThreadLocal.get();

        if (connectionWrapper != null) {
            return connectionWrapper;
        }

        connectionWrapper = new ConnectionWrapper(ConnectionPoolHolder.getConnection());
        return connectionWrapper;
    }

    public static void startTransaction() {
        ConnectionWrapper connectionWrapper = connectionWrapThreadLocal.get();

        if (connectionWrapper != null) {
            throw new DAOLevelException("Transaction already started");
        }

        connectionWrapper = new ConnectionWrapper(ConnectionPoolHolder.getConnection());
        try {
            connectionWrapper.setAutoCommit(false);
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
            throw new DAOLevelException("Transaction already started");
        }
        connectionWrapThreadLocal.set(connectionWrapper);
    }

    public static void commit() {
        ConnectionWrapper connectionWrapper = connectionWrapThreadLocal.get();

        if (connectionWrapper == null) {
            throw new DAOLevelException("Transaction not started to be commit.");
        }

        try {
            connectionWrapper.commit();
            connectionWrapper.setAutoCommit(true);
            connectionWrapper.close();
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
            throw new DAOLevelException(e);
        } finally {
            connectionWrapThreadLocal.remove();
        }

    }

    public static void rollback() {
        ConnectionWrapper connectionWrapper = connectionWrapThreadLocal.get();

        if (connectionWrapper == null) {
            throw new DAOLevelException("Transaction not started to be rolled back.");
        }

        try {
            connectionWrapper.rollback();
            connectionWrapper.setAutoCommit(true);
            connectionWrapper.close();
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
            throw new DAOLevelException(e);
        } finally {
            connectionWrapThreadLocal.remove();
        }
    }
}
