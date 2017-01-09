package by.rudkouski.auction.pool;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import static by.rudkouski.auction.constant.ConstantName.*;

public class DataBaseManager {
    private static final Logger LOGGER = LogManager.getLogger(DataBaseManager.class);
    private static final Properties PROP = new Properties();

    private DataBaseManager() {
    }

    public static ProxyConnection getConnection() throws SQLException, IOException {
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        InputStream stream = loader.getResourceAsStream(FILE_PROPERTIES);
        PROP.load(stream);
        String url = PROP.getProperty(DB_URL);
        String user = PROP.getProperty(DB_USER);
        String pass = PROP.getProperty(DB_PASSWORD);

        Connection connection = DriverManager.getConnection(url, user, pass);
        return new ProxyConnection(connection);
    }

    public static int getPoolSize() {
        try {
            return Integer.parseInt(PROP.getProperty(DB_POOL_SIZE, Integer.toString(INIT_POOL_SIZE)));
        } catch (NumberFormatException e) {
            LOGGER.log(Level.ERROR, "Error during getting connection pool size", e);
            return INIT_POOL_SIZE;
        }
    }
}
