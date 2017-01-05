package by.rudkouski.auction.command.impl;

import by.rudkouski.auction.command.ICommand;
import by.rudkouski.auction.service.exception.ServiceException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class PageNextCommand implements ICommand {
    private static final Logger LOGGER = LogManager.getLogger(PageNextCommand.class);
    private static final String MAIN_PAGE = "main.jsp";
    private static final String PAGE_LIST = "pageList";
    private static final String ERROR_MESSAGE = "errorMessage";

    @Override
    public String execute(HttpServletRequest request) {
        String pageList = request.getParameter(PAGE_LIST);
        if (pageList != null && !pageList.isEmpty()) {
            try {
                int page = Integer.parseInt(pageList) + 1;
                contentByPage(request, page);
            } catch (NumberFormatException | ServiceException e) {
                LOGGER.log(Level.ERROR, "Exception: ", e);
                HttpSession session = request.getSession();
                session.setAttribute(ERROR_MESSAGE, ERROR_MESSAGE);
            }
        }
        return MAIN_PAGE;
    }

    @Override
    public void resetSessionMessage(HttpSession session) {
        session.removeAttribute(ERROR_MESSAGE);
    }
}