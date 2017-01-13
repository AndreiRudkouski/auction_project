package by.rudkouski.auction.command.impl;

import by.rudkouski.auction.command.ICommand;
import by.rudkouski.auction.command.validation.Validator;
import by.rudkouski.auction.service.ServiceManager;
import by.rudkouski.auction.service.exception.ServiceException;
import by.rudkouski.auction.service.impl.UserService;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static by.rudkouski.auction.constant.ConstantName.*;

public class PwdForgotCommand implements ICommand {
    private static final Logger LOGGER = LogManager.getLogger(PwdForgotCommand.class);

    @Override
    public String execute(HttpServletRequest request) {
        String mail = request.getParameter(MAIL);

        HttpSession session = request.getSession();
        String page = returnPage(session);
        boolean validMail = new Validator().userMailValidate(mail);
        if (!validMail) {
            session.setAttribute(ERROR_MAIL, ERROR_MAIL);
            return page;
        }

        ServiceManager manager = ServiceManager.getInstance();
        UserService userService = manager.getUserService();
        try {
            boolean result = userService.forgotUserPassword(mail);
            if (result) {
                session.setAttribute(MAIL_MESSAGE, MAIL_MESSAGE);
            } else {
                session.setAttribute(ERROR_MAIL, ERROR_MAIL);
            }
        } catch (ServiceException e) {
            LOGGER.log(Level.ERROR, "Exception: ", e);
            session.setAttribute(ERROR_MESSAGE, ERROR_MESSAGE);
            return MAIN_PAGE;
        }
        return page;
    }

    @Override
    public void resetSessionMessage(HttpSession session) {
        session.removeAttribute(ERROR_MAIL);
        session.removeAttribute(MAIL_MESSAGE);
        session.removeAttribute(ERROR_MESSAGE);
    }
}
