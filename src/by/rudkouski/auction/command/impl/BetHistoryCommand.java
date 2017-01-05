package by.rudkouski.auction.command.impl;

import by.rudkouski.auction.bean.impl.Bet;
import by.rudkouski.auction.bean.impl.User;
import by.rudkouski.auction.command.ICommand;
import by.rudkouski.auction.service.ServiceManager;
import by.rudkouski.auction.service.exception.ServiceException;
import by.rudkouski.auction.service.impl.BetService;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

public class BetHistoryCommand implements ICommand {
    private static final Logger LOGGER = LogManager.getLogger(BetHistoryCommand.class);
    private static final String USER = "user";
    private static final String PROFILE = "profile";
    private static final String BET_HISTORY = "betHistory";
    private static final String BET_LIST_WIN = "betListWin";
    private static final String BET_LIST_DONE = "betListDone";
    private static final String ERROR_MESSAGE = "errorMessage";
    private static final String MAIN_PAGE = "main.jsp";
    private static final String USER_ID = "userId";
    private static final int RESULT_LIST_SIZE = 2;

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

        try {
            String id = request.getParameter(USER_ID);
            if (id != null && !id.isEmpty()) {
                userId = Long.parseLong(id);
            }

            ServiceManager manager = ServiceManager.getInstance();
            BetService betService = manager.getBetService();
            List<List<Bet>> betResult;
            betResult = betService.receiveBetHistoryByUser(userId);
            if (betResult != null && betResult.size() == RESULT_LIST_SIZE) {
                request.setAttribute(BET_LIST_WIN, betResult.get(0));
                request.setAttribute(BET_LIST_DONE, betResult.get(1));
                request.setAttribute(BET_HISTORY, BET_HISTORY);
            }
        } catch (NumberFormatException | ServiceException e) {
            LOGGER.log(Level.ERROR, "Exception: ", e);
            session.setAttribute(ERROR_MESSAGE, ERROR_MESSAGE);
            return returnPage(session);
        }
        request.setAttribute(PROFILE, PROFILE);
        return MAIN_PAGE;
    }

    @Override
    public void resetSessionMessage(HttpSession session) {
        session.removeAttribute(ERROR_MESSAGE);
    }
}