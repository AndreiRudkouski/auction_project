package by.rudkouski.auction.command.impl;

import by.rudkouski.auction.bean.impl.User;
import by.rudkouski.auction.command.ICommand;
import by.rudkouski.auction.service.ServiceManager;
import by.rudkouski.auction.service.impl.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

public class UserSearchCommand implements ICommand {
    private static final String MAIN_PAGE = "main.jsp";
    private static final String USER_SEARCH = "user_search";
    private static final String USER_LIST = "userList";
    private static final int USER_LIST_NULL = 0;

    @Override
    public String execute(HttpServletRequest request) {
        String search = request.getParameter(USER_SEARCH);
        if (search != null && !search.isEmpty()) {
            ServiceManager manager = ServiceManager.getInstance();
            UserService userService = manager.getUserService();
            List<User> userList = userService.searchUser(search);
            if (userList != null) {
                request.setAttribute(USER_LIST, userList);
            } else {
                request.setAttribute(USER_LIST, USER_LIST_NULL);
            }
        }
        return MAIN_PAGE;
    }

    @Override
    public void resetSessionMessage(HttpSession session) {
    }
}
