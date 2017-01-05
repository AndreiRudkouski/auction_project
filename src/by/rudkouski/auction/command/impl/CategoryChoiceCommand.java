package by.rudkouski.auction.command.impl;

import by.rudkouski.auction.command.ICommand;
import by.rudkouski.auction.service.exception.ServiceException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class CategoryChoiceCommand implements ICommand {
    private static final Logger LOGGER = LogManager.getLogger(CategoryChoiceCommand.class);
    private static final String MAIN_PAGE = "main.jsp";
    private static final String ERROR_MESSAGE = "errorMessage";

    @Override
    public String execute(HttpServletRequest request) {
        try {
            categoryChoice(request, 0);
        } catch (ServiceException e) {
            LOGGER.log(Level.ERROR, "Exception: ", e);
            HttpSession session = request.getSession();
            session.setAttribute(ERROR_MESSAGE, ERROR_MESSAGE);
            return MAIN_PAGE;
        }
        return MAIN_PAGE;
    }

    @Override
    public void resetSessionMessage(HttpSession session) {
        session.removeAttribute(ERROR_MESSAGE);
    }
}