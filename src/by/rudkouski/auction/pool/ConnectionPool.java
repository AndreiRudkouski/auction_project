package by.rudkouski.auction.pool;

import java.io.IOException;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ConnectionPool {
    private static final Logger LOGGER = LogManager.getLogger(ConnectionPool.class);
    private static final ConnectionPool INSTANCE = new ConnectionPool();
    private final BlockingQueue<ProxyConnection> connectionQueue;
    private final int poolSize;

    private ConnectionPool() {
        poolSize = DataBaseManager.getPoolSize();
        int tryNum = 0;
        connectionQueue = new ArrayBlockingQueue<>(poolSize);
        try {
            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
        } catch (SQLException e) {
            LOGGER.log(Level.FATAL, "Wrong the new JDBC Driver registration", e);
            throw new RuntimeException("Error of JDBC Driver registration", e);
        }

        for (int i = 0; i < poolSize; i++) {
            try {
                ProxyConnection connection = DataBaseManager.getConnection();
                connectionQueue.put(connection);
            } catch (SQLException | InterruptedException | IOException e) {
                //if throw exception try create and put additional connection
                LOGGER.log(Level.ERROR, "Error during creating a new connection", e);
                tryNum++;
                if (tryNum < poolSize) {
                    i--;
                } else {
                    LOGGER.log(Level.FATAL, "Wrong initialization connection pool", e);
                    throw new RuntimeException("Error of connection pool initialization", e);
                }
            }
        }
    }

    public static ConnectionPool getInstance() {
        return INSTANCE;
    }

    public void closeConnectionPool() {
        closeConnectionQueue(connectionQueue);
    }

    private void closeConnectionQueue(BlockingQueue<ProxyConnection> queue) {
        for (int i = 0; i < queue.size(); i++) {
            try {
                queue.take().realClose();
            } catch (SQLException | InterruptedException e) {
                LOGGER.log(Level.ERROR, "Error during closing the connection", e);
            }
        }
    }

    public ProxyConnection takeConnection() throws ConnectionPoolException {
        ProxyConnection connection;
        try {
            connection = connectionQueue.take();
        } catch (InterruptedException e) {
            throw new ConnectionPoolException("Error during taking connection from the pool", e);
        }
        return connection;
    }

    public void returnConnection(ProxyConnection connection) throws ConnectionPoolException {
        try {
            if (connection != null) {
                connection.setAutoCommit(true);
                connectionQueue.put(connection);
            }
        } catch (InterruptedException | SQLException e) {
            throw new ConnectionPoolException("Error during returning connection to the pool", e);
        }
    }
}
