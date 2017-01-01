package by.rudkouski.auction.command.impl;

import by.rudkouski.auction.bean.impl.User;
import by.rudkouski.auction.command.ICommand;
import by.rudkouski.auction.service.ServiceManager;
import by.rudkouski.auction.service.impl.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class UserChoiceCommand implements ICommand {
    private static final String USER_ID = "userId";
    private static final String MAIN_PAGE = "main.jsp";
    private static final String USER = "user";

    @Override
    public String execute(HttpServletRequest request) {
        long userId;
        try {
            userId = Long.parseLong(request.getParameter(USER_ID));
        } catch (NumberFormatException e) {
            //throw new CommandException("Wrong data parsing", e);
            return MAIN_PAGE;
        }

        ServiceManager manager = ServiceManager.getInstance();
        UserService userService = manager.getUserService();
        User user = userService.receiveUserById(userId);
        if (user != null) {
            request.setAttribute(USER, user);
        }
        return MAIN_PAGE;
    }

    @Override
    public void resetSessionMessage(HttpSession session) {

    }
}
