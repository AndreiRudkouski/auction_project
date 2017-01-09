package by.rudkouski.auction.command.impl;

import by.rudkouski.auction.bean.impl.User;
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

public class LogInCommand implements ICommand {
    private static final Logger LOGGER = LogManager.getLogger(LogInCommand.class);

    @Override
    public String execute(HttpServletRequest request) {
        String mail = request.getParameter(MAIL);
        String password = request.getParameter(PWD);

        HttpSession session = request.getSession();
        String page = returnPage(session);
        boolean validMail = new Validator().userMailValidate(mail);
        boolean validPassword = new Validator().userPasswordValidate(password);
        if (!validMail || !validPassword) {
            session.setAttribute(ERROR_USER, ERROR_USER);
            return page;
        }

        ServiceManager manager = ServiceManager.getInstance();
        UserService userService = manager.getUserService();
        User user;
        try {
            user = userService.logInUser(mail, password);
        } catch (ServiceException e) {
            LOGGER.log(Level.ERROR, "Exception: ", e);
            session.setAttribute(ERROR_MESSAGE, ERROR_MESSAGE);
            return MAIN_PAGE;
        }

        if (user != null) {
            if (!user.isBan()) {
                session.setAttribute(USER, user);
                if (user.getRoleId() == ADMIN_ROLE_ID) {
                    page = MAIN_PAGE;
                }
            } else {
                session.setAttribute(ERROR_BAN, USER);
            }
        } else {
            session.setAttribute(ERROR_USER, USER);
        }
        return page;
    }

    @Override
    public void resetSessionMessage(HttpSession session) {
        session.removeAttribute(ERROR_USER);
        session.removeAttribute(ERROR_BAN);
        session.removeAttribute(ERROR_MESSAGE);
    }
}