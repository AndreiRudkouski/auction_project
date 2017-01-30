package by.rudkouski.auction.command.impl;

import by.rudkouski.auction.entity.impl.User;
import by.rudkouski.auction.command.ICommand;
import by.rudkouski.auction.service.ServiceManager;
import by.rudkouski.auction.service.exception.ServiceException;
import by.rudkouski.auction.service.impl.UserService;
import by.rudkouski.auction.command.validation.Validator;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static by.rudkouski.auction.constant.ConstantName.*;

public class BanChangeCommand implements ICommand {
    private static final Logger LOGGER = LogManager.getLogger(BanChangeCommand.class);

    @Override
    public String execute(HttpServletRequest request) {
        if (!Validator.userValidate(request)) {
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
            HttpSession session = request.getSession();
            session.setAttribute(USER_MESSAGE, CHANGE_ACCEPT);
        } catch (NumberFormatException | ServiceException e) {
            LOGGER.log(Level.ERROR, "Exception: ", e);
            HttpSession session = request.getSession();
            session.setAttribute(USER_MESSAGE, ERROR_MESSAGE);
        }
        HttpSession session = request.getSession();
        String page = returnPage(session);
        return page;
    }

    @Override
    public void resetSessionMessage(HttpSession session) {
        session.removeAttribute(USER_MESSAGE);
    }
}