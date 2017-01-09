package by.rudkouski.auction.command.impl;

import by.rudkouski.auction.bean.impl.Bet;
import by.rudkouski.auction.bean.impl.Lot;
import by.rudkouski.auction.bean.impl.User;
import by.rudkouski.auction.command.ICommand;
import by.rudkouski.auction.service.ServiceManager;
import by.rudkouski.auction.service.exception.ServiceException;
import by.rudkouski.auction.service.impl.BetService;
import by.rudkouski.auction.service.impl.LotService;
import by.rudkouski.auction.service.impl.UserService;
import by.rudkouski.auction.command.validation.Validator;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.Date;

import static by.rudkouski.auction.constant.ConstantName.*;

public class BetAddCommand implements ICommand {
    private static final Logger LOGGER = LogManager.getLogger(BetAddCommand.class);

    @Override
    public String execute(HttpServletRequest request) {
        long lotId;
        long userId;
        BigDecimal curBet;

        HttpSession session = request.getSession();
        String page = returnPage(session);
        User user = (User) session.getAttribute(USER);
        if (user != null) {
            userId = user.getId();
        } else {
            return MAIN_PAGE;
        }

        boolean validBet;
        ServiceManager manager = ServiceManager.getInstance();
        UserService userService = manager.getUserService();
        try {
            lotId = Long.parseLong(request.getParameter(LOT_ID));
            curBet = new BigDecimal(request.getParameter(AMOUNT_BET));

            LotService lotService = manager.getLotService();
            BigDecimal minBet;
            minBet = lotService.determineLotMinBet(lotId);
            validBet = new Validator().betDataValidate(curBet, minBet);
            if (!validBet) {
                session.setAttribute(ERROR_BET, ERROR_BET);
                return page;
            }

            BigDecimal balance = userService.receiveUserBalance(userId);
            if (balance.compareTo(curBet) < 0) {
                session.setAttribute(ERROR_BALANCE, ERROR_BALANCE);
                return page;
            }

            Date curTime = new Date(System.currentTimeMillis());
            Bet bet = new Bet();
            user = new User();
            user.setId(userId);
            bet.setUser(user);
            Lot lot = new Lot();
            lot.setId(lotId);
            bet.setLot(lot);
            bet.setAmount(curBet);
            bet.setTime(curTime);

            BetService betService = manager.getBetService();
            validBet = betService.addBet(bet, balance);
            if (validBet) {
                userService = manager.getUserService();
                user = userService.receiveUserById(userId);
                session.setAttribute(USER, user);
                session.setAttribute(BET_ACCEPT, BET_ACCEPT);
            } else {
                session.setAttribute(ERROR_FINISH, ERROR_FINISH);
            }
        } catch (NumberFormatException | ServiceException e) {
            LOGGER.log(Level.ERROR, "Exception: ", e);
            session.setAttribute(ERROR_MESSAGE, ERROR_MESSAGE);
            return MAIN_PAGE;
        }
        return page;
    }

    @Override
    public void resetSessionMessage(HttpSession session) {
        session.removeAttribute(ERROR_BET);
        session.removeAttribute(ERROR_BALANCE);
        session.removeAttribute(ERROR_FINISH);
        session.removeAttribute(BET_ACCEPT);
        session.removeAttribute(ERROR_MESSAGE);
    }
}