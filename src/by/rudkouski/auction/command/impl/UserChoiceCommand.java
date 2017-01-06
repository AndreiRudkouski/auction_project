package by.rudkouski.auction.command.impl;

import by.rudkouski.auction.bean.impl.User;
import by.rudkouski.auction.command.ICommand;
import by.rudkouski.auction.service.ServiceManager;
import by.rudkouski.auction.service.exception.ServiceException;
import by.rudkouski.auction.service.impl.UserService;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class UserChoiceCommand implements ICommand {
    private static final Logger LOGGER = LogManager.getLogger(UserChoiceCommand.class);
    private static final String USER_ID = "userId";
    private static final String MAIN_PAGE = "main.jsp";
    private static final String USER = "user";
    private static final String ERROR_MESSAGE = "errorMessage";

    @Override
    public String execute(HttpServletRequest request) {
        long userId;
        try {
            userId = Long.parseLong(request.getParameter(USER_ID));
            ServiceManager manager = ServiceManager.getInstance();
            UserService userService = manager.getUserService();
            User user = userService.receiveUserById(userId);
            if (user != null) {
                request.setAttribute(USER, user);
            }
        } catch (NumberFormatException | ServiceException e) {
            LOGGER.log(Level.ERROR, "Exception: ", e);
            HttpSession session = request.getSession();
            session.setAttribute(ERROR_MESSAGE, ERROR_MESSAGE);
        }
        return MAIN_PAGE;
    }

    @Override
    public void resetSessionMessage(HttpSession session) {
        session.removeAttribute(ERROR_MESSAGE);
    }
}
