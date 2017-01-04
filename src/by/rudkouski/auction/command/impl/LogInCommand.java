package by.rudkouski.auction.command.impl;

import by.rudkouski.auction.bean.impl.User;
import by.rudkouski.auction.command.ICommand;
import by.rudkouski.auction.service.ServiceManager;
import by.rudkouski.auction.service.impl.UserService;
import by.rudkouski.auction.validation.Validator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class LogInCommand implements ICommand {
    private static final String MAIL = "mail";
    private static final String PWD = "pwd";
    private static final String USER = "user";
    private static final String ERROR_USER = "errorUser";
    private static final String ERROR_BAN = "errorBan";
    private static final String COMMAND = "command";
    private static final long ADMIN_ROLE_ID = 2;
    public static final String MAIN_PAGE = "main.jsp";

    @Override
    public String execute(HttpServletRequest request) {
        String mail = request.getParameter(MAIL);
        String password = request.getParameter(PWD);

        HttpSession session = request.getSession();
        String page = returnPage(session);
        session.setAttribute(COMMAND, request.getParameter(COMMAND));
        boolean validMail = new Validator().userMailValidate(mail);
        boolean validPassword = new Validator().userPasswordValidate(password);
        if (!validMail || !validPassword) {
            session.setAttribute(ERROR_USER, ERROR_USER);
            return page;
        }

        ServiceManager manager = ServiceManager.getInstance();
        UserService userService = manager.getUserService();
        User user = userService.logInUser(mail, password);

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
    }
}
