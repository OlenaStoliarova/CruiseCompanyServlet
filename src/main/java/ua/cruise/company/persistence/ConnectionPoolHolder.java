package ua.cruise.company.persistence;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionPoolHolder {
    private static final Logger LOG = LogManager.getLogger(ConnectionPoolHolder.class);

    private static DataSource dataSource;

    static {
        try {
            Context initContext = new InitialContext();
            dataSource = (DataSource) initContext.lookup("java:comp/env/jdbc/CruiseCompany");
        } catch (NamingException ex) {
            LOG.error("Could not find DataSource jdbc/CruiseCompany");
            LOG.error(ex.getMessage(), ex);
            throw new RuntimeException(ex);
        }
    }

    private ConnectionPoolHolder() {
    }

    public static Connection getConnection() {
        try {
            Connection connection = dataSource.getConnection();
            LOG.debug("Connection to DB has been obtained successfully " + connection);
            return connection;
        } catch (SQLException ex) {
            LOG.error("Failed to get connection to DB");
            LOG.error(ex.getMessage(), ex);
            throw new RuntimeException(ex);
        }
    }
}
