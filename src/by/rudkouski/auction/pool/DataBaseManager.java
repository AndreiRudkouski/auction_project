package by.rudkouski.auction.pool;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DataBaseManager {
    private static final Properties PROP = new Properties();
    private static final String FILE_PROPERTIES = "/resource/db.properties";
    private static final String DB_URL = "db.url";
    private static final String DB_USER = "db.user";
    private static final String DB_PASSWORD = "db.password";
    private static final String DB_POOL_SIZE = "db.pool_size";
    private static final int INIT_POOL_SIZE = 5;

    private DataBaseManager() {
    }

    public static ProxyConnection getConnection() throws SQLException {
        try {
            ClassLoader loader = Thread.currentThread().getContextClassLoader();
            InputStream stream = loader.getResourceAsStream(FILE_PROPERTIES);
            PROP.load(stream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
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
            return INIT_POOL_SIZE;
        }
    }
}
