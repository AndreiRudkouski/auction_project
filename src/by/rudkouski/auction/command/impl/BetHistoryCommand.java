package by.rudkouski.auction.command.impl;

import by.rudkouski.auction.entity.EntityListTag;
import by.rudkouski.auction.entity.impl.Bet;
import by.rudkouski.auction.entity.impl.User;
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

import static by.rudkouski.auction.constant.ConstantName.*;

public class BetHistoryCommand implements ICommand {
    private static final Logger LOGGER = LogManager.getLogger(BetHistoryCommand.class);

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
            if (betResult != null && betResult.size() == RESULT_BET_LIST_SIZE) {
                EntityListTag<Bet> resultList = new EntityListTag<>(betResult.get(0));
                request.setAttribute(BET_LIST_WIN, resultList);
                resultList = new EntityListTag<>(betResult.get(1));
                request.setAttribute(BET_LIST_DONE, resultList);
                request.setAttribute(BET_HISTORY, BET_HISTORY);
            }
            request.setAttribute(PROFILE, PROFILE);
        } catch (NumberFormatException | ServiceException e) {
            LOGGER.log(Level.ERROR, "Exception: ", e);
            session.setAttribute(ERROR_MESSAGE, ERROR_MESSAGE);
        }
        return MAIN_PAGE;
    }

    @Override
    public void resetSessionMessage(HttpSession session) {
        session.removeAttribute(ERROR_MESSAGE);
    }
}