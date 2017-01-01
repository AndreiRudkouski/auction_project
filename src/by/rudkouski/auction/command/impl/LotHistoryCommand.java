package by.rudkouski.auction.command.impl;

import by.rudkouski.auction.bean.impl.Lot;
import by.rudkouski.auction.bean.impl.User;
import by.rudkouski.auction.command.ICommand;
import by.rudkouski.auction.service.ServiceManager;
import by.rudkouski.auction.service.impl.LotService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

public class LotHistoryCommand implements ICommand {
    private static final String USER = "user";
    private static final String PROFILE = "profile";
    private static final String LOT_HISTORY = "lotHistory";
    private static final String LOT_LIST_FINISH = "lotListFinished";
    private static final String LOT_LIST_UNFINISHED = "lotListUnfinished";
    private static final String LOT_LIST_UNCHECKED = "lotListUnchecked";
    private static final String MAIN_PAGE = "main.jsp";
    private static final String USER_ID = "userId";
    private static final int RESULT_LIST_SIZE = 3;

    @Override
    public String execute(HttpServletRequest request) {
        long userId;
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(USER);
        if (user != null) {
            userId = user.getId();
        } else {
            //throw new CommandException("Wrong data parsing", e);
            return MAIN_PAGE;
        }

        String id = request.getParameter(USER_ID);
        if (id != null && !id.isEmpty()) {
            try {
                userId = Long.parseLong(id);
            } catch (NumberFormatException e) {
                //throw new CommandException("Wrong data parsing", e);
                return MAIN_PAGE;
            }
        }

        ServiceManager manager = ServiceManager.getInstance();
        LotService lotService = manager.getLotService();
        List<List<Lot>> lotResult = lotService.receiveLotHistoryByUser(userId);
        if (lotResult.size() == RESULT_LIST_SIZE) {
            request.setAttribute(LOT_LIST_FINISH, lotResult.get(0));
            request.setAttribute(LOT_LIST_UNFINISHED, lotResult.get(1));
            request.setAttribute(LOT_LIST_UNCHECKED, lotResult.get(2));
        }
        request.setAttribute(LOT_HISTORY, LOT_HISTORY);
        request.setAttribute(PROFILE, PROFILE);
        return MAIN_PAGE;
    }

    @Override
    public void resetSessionMessage(HttpSession session) {
    }
}
