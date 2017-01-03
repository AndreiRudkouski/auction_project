package by.rudkouski.auction.command.impl;

import by.rudkouski.auction.bean.impl.User;
import by.rudkouski.auction.command.ICommand;
import by.rudkouski.auction.service.ServiceManager;
import by.rudkouski.auction.service.impl.UserService;
import by.rudkouski.auction.validation.Validator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

public class UserSearchCommand implements ICommand {
    private static final String MAIN_PAGE = "main.jsp";
    private static final String USER_SEARCH = "userSearch";
    private static final String USER_LIST = "userList";

    @Override
    public String execute(HttpServletRequest request) {
        if (!new Validator().userValidate(request)) {
            //throw new CommandException("Wrong data parsing", e);
            return MAIN_PAGE;
        }

        String search = request.getParameter(USER_SEARCH);
        if (search != null && !search.isEmpty()) {
            ServiceManager manager = ServiceManager.getInstance();
            UserService userService = manager.getUserService();
            List<User> userList = userService.searchUserByLoginMail(search);
            request.setAttribute(USER_LIST, userList);
        }
        return MAIN_PAGE;
    }

    @Override
    public void resetSessionMessage(HttpSession session) {
    }
}
