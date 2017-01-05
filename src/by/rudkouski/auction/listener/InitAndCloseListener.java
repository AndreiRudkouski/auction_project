package by.rudkouski.auction.listener;

import by.rudkouski.auction.pool.ConnectionPool;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener()
public class InitAndCloseListener implements ServletContextListener {
    private static final String EMPTY_LINE = "";
    private static final String SAVE_DIRECTORY = "saveDirectory";

    public InitAndCloseListener() {
    }

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.setProperty(SAVE_DIRECTORY, sce.getServletContext().getRealPath(EMPTY_LINE));
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        ConnectionPool.getInstance().closeConnectionPool();
    }
}