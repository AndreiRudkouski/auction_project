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

public class ProfileChangeCommand implements ICommand {
    private static final Logger LOGGER = LogManager.getLogger(ProfileChangeCommand.class);
    private static final String LOGIN = "login";
    private static final String OLD_LOGIN = "userOldLogin";
    private static final String NEW_PWD = "newPwd";
    private static final String OLD_PWD = "oldPwd";
    private static final String USER = "user";
    private static final String CHANGE_ACCEPT = "changeAccept";
    private static final String ERROR_LOGIN = "errorLogin";
    private static final String ERROR_PWD = "errorPwd";
    private static final String ERROR_EXIST_LOGIN = "errorExistLogin";
    private static final String ERROR_MESSAGE = "errorMessage";
    private static final String MAIN_PAGE = "main.jsp";

    @Override
    public String execute(HttpServletRequest request) {
        long userId;
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(USER);
        if (user != null) {
            userId = user.getId();
        } else {
            return MAIN_PAGE;
        }

        String newLogin = request.getParameter(LOGIN);
        String oldLogin = request.getParameter(OLD_LOGIN);
        String newPassword = request.getParameter(NEW_PWD);
        String oldPassword = request.getParameter(OLD_PWD);

        String page = returnPage(session);
        boolean validLogin = new Validator().userLoginChangeValidate(newLogin, oldLogin);
        boolean validOldPassword = new Validator().userPasswordValidate(oldPassword);
        boolean validNewPassword = new Validator().userPasswordValidate(newPassword);
        if (!validLogin && ((oldPassword == null || oldPassword.isEmpty()) &&
                (newPassword == null || newPassword.isEmpty()))) {
            session.setAttribute(ERROR_LOGIN, ERROR_LOGIN);
            return page;
        }
        if ((!validOldPassword || !validNewPassword) && (newPassword != null && newPassword.equals(oldPassword)) &&
                (newLogin != null && newLogin.equals(oldLogin))) {
            session.setAttribute(ERROR_PWD, ERROR_PWD);
            return page;
        }
        if (!validLogin && (!validOldPassword || !validNewPassword)) {
            session.setAttribute(ERROR_LOGIN, ERROR_LOGIN);
            session.setAttribute(ERROR_PWD, ERROR_PWD);
            return page;
        }

        ServiceManager manager = ServiceManager.getInstance();
        UserService userService = manager.getUserService();
        try {
            if (validLogin) {
                user = userService.changeUserLogin(userId, newLogin);
                if (user != null) {
                    session.setAttribute(USER, user);
                    session.setAttribute(CHANGE_ACCEPT, CHANGE_ACCEPT);
                } else {
                    session.setAttribute(ERROR_EXIST_LOGIN, ERROR_EXIST_LOGIN);
                }
            }
            if (validOldPassword && validNewPassword) {
                user = userService.changeUserPassword(userId, oldPassword, newPassword);
                if (user != null) {
                    session.setAttribute(USER, user);
                    session.setAttribute(CHANGE_ACCEPT, CHANGE_ACCEPT);
                } else {
                    session.setAttribute(ERROR_PWD, ERROR_PWD);
                }
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
        session.removeAttribute(ERROR_LOGIN);
        session.removeAttribute(ERROR_PWD);
        session.removeAttribute(ERROR_EXIST_LOGIN);
        session.removeAttribute(CHANGE_ACCEPT);
        session.removeAttribute(ERROR_MESSAGE);
    }
}