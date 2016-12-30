package by.rudkouski.auction.command.impl;

import by.rudkouski.auction.bean.impl.Bet;
import by.rudkouski.auction.bean.impl.User;
import by.rudkouski.auction.command.ICommand;
import by.rudkouski.auction.service.ServiceManager;
import by.rudkouski.auction.service.impl.BetService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

public class BetHistoryCommand implements ICommand {
    private static final String USER = "user";
    private static final String PROFILE = "profile";
    private static final String BET_HISTORY = "betHistory";
    private static final String BET_LIST_WIN = "betListWin";
    private static final String BET_LIST_DONE = "betListDone";
    private static final String MAIN_PAGE = "main.jsp";
    private static final int RESULT_LIST_SIZE = 2;

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

        ServiceManager factory = ServiceManager.getInstance();
        BetService betService = factory.getBetService();
        List<List<Bet>> betResult = betService.receiveBetHistoryByUser(userId);
        if (betResult.size() == RESULT_LIST_SIZE) {
            request.setAttribute(BET_LIST_WIN, betResult.get(0));
            request.setAttribute(BET_LIST_DONE, betResult.get(1));
            request.setAttribute(BET_HISTORY, BET_HISTORY);
        }
        request.setAttribute(PROFILE, PROFILE);
        return MAIN_PAGE;
    }
}
