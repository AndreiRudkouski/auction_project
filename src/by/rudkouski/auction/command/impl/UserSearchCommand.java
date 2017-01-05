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
import java.util.List;

public class UserSearchCommand implements ICommand {
    private static final Logger LOGGER = LogManager.getLogger(UserSearchCommand.class);
    private static final String MAIN_PAGE = "main.jsp";
    private static final String USER_SEARCH = "userSearch";
    private static final String USER_LIST = "userList";
    private static final String ERROR_MESSAGE = "errorMessage";

    @Override
    public String execute(HttpServletRequest request) {
        if (!new Validator().userValidate(request)) {
            return MAIN_PAGE;
        }

        String search = request.getParameter(USER_SEARCH);
        if (search != null && !search.isEmpty()) {
            ServiceManager manager = ServiceManager.getInstance();
            UserService userService = manager.getUserService();
            List<User> userList;
            try {
                userList = userService.searchUserByLoginMail(search);
            } catch (ServiceException e) {
                LOGGER.log(Level.ERROR, "Exception: ", e);
                HttpSession session = request.getSession();
                session.setAttribute(ERROR_MESSAGE, ERROR_MESSAGE);
                return returnPage(session);
            }
            request.setAttribute(USER_LIST, userList);
        }
        return MAIN_PAGE;
    }

    @Override
    public void resetSessionMessage(HttpSession session) {
        session.removeAttribute(ERROR_MESSAGE);
    }
}