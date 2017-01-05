package by.rudkouski.auction.command.impl;

import by.rudkouski.auction.bean.impl.User;
import by.rudkouski.auction.command.ICommand;
import by.rudkouski.auction.service.ServiceManager;
import by.rudkouski.auction.service.exception.ServiceException;
import by.rudkouski.auction.service.impl.UserService;
import by.rudkouski.auction.validation.Validator;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class BanChangeCommand implements ICommand {
    private static final Logger LOGGER = LogManager.getLogger(BanChangeCommand.class);
    private static final String USER_ID = "userId";
    private static final String USER = "user";
    private static final String ERROR_MESSAGE = "errorMessage";
    private static final String CHANGE_ACCEPT = "changeAccept";

    @Override
    public String execute(HttpServletRequest request) {
        if (!new Validator().userValidate(request)) {
            return MAIN_PAGE;
        }

        try {
            long userId = Long.parseLong(request.getParameter(USER_ID));
            ServiceManager manager = ServiceManager.getInstance();
            UserService userService = manager.getUserService();
            User user = userService.changeBanUserById(userId);
            if (user != null) {
                request.setAttribute(USER, user);
            }
        } catch (NumberFormatException | ServiceException e) {
            LOGGER.log(Level.ERROR, "Exception: ", e);
            HttpSession session = request.getSession();
            session.setAttribute(ERROR_MESSAGE, ERROR_MESSAGE);
            return returnPage(session);
        }
        HttpSession session = request.getSession();
        session.setAttribute(CHANGE_ACCEPT, CHANGE_ACCEPT);
        return MAIN_PAGE;
    }

    @Override
    public void resetSessionMessage(HttpSession session) {
        session.removeAttribute(ERROR_MESSAGE);
        session.removeAttribute(CHANGE_ACCEPT);
    }
}