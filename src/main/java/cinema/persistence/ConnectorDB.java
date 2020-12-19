package cinema.persistence;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Properties;

/**
 * Class ConnectorDB.
 *
 * @author Vitaly Yagufarov (for.viy@gmail.com)
 * @version 1.0
 * @since 11.12.2020
 */
public class ConnectorDB {
    private static final BasicDataSource POOL = new BasicDataSource();
    private static final ConnectorDB INSTANCE = new ConnectorDB();
    private static final Log LOG = LogFactory.getLog(ConnectorDB.class.getName());

    private ConnectorDB() {
        Properties cfg = new Properties();
        try (BufferedReader io = new BufferedReader(
                new InputStreamReader(Objects.requireNonNull(getClass().getClassLoader()
                        .getResourceAsStream("db.properties")))
        )) {
            cfg.load(io);
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new IllegalStateException(e);
        }
        try {
            Class.forName(cfg.getProperty("jdbc.driver"));
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new IllegalStateException(e);
        }
        POOL.setDriverClassName(cfg.getProperty("jdbc.driver"));
        POOL.setUrl(cfg.getProperty("jdbc.url"));
        POOL.setUsername(cfg.getProperty("jdbc.username"));
        POOL.setPassword(cfg.getProperty("jdbc.password"));
        POOL.setMinIdle(5);
        POOL.setMaxIdle(10);
        POOL.setMaxOpenPreparedStatements(100);
    }

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static ConnectorDB getInstance() {
        return INSTANCE;
    }

    /**
     * Gets connection.
     *
     * @return the connection
     * @throws SQLException the sql exception
     */
    public Connection getConnection() throws SQLException {
        return POOL.getConnection();
    }
}
