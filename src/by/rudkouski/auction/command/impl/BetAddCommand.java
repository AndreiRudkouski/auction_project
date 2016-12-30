package by.rudkouski.auction.command.impl;

import by.rudkouski.auction.bean.impl.Bet;
import by.rudkouski.auction.bean.impl.Lot;
import by.rudkouski.auction.bean.impl.User;
import by.rudkouski.auction.command.ICommand;
import by.rudkouski.auction.service.ServiceManager;
import by.rudkouski.auction.service.impl.BetService;
import by.rudkouski.auction.service.impl.LotService;
import by.rudkouski.auction.service.impl.UserService;
import by.rudkouski.auction.validation.Validator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.Date;

public class BetAddCommand implements ICommand {
    private static final String LOT_ID = "lotId";
    private static final String AMOUNT_BET = "amountBet";
    private static final String ERROR_BET = "errorBet";
    private static final String ERROR_BALANCE = "errorBalance";
    private static final String ERROR_FINISH = "errorFinish";
    private static final String BET_ACCEPT = "betAccept";
    private static final String USER = "user";
    private static final String COMMAND = "command";

    @Override
    public String execute(HttpServletRequest request) {
        long lotId;
        long userId;
        BigDecimal curBet;

        HttpSession session = request.getSession();
        String page = returnPage(session);
        try {
            lotId = Long.parseLong(request.getParameter(LOT_ID));
            curBet = new BigDecimal(request.getParameter(AMOUNT_BET));
        } catch (NumberFormatException e) {
            //throw new CommandException("Wrong data parsing", e);
            return page;
        }
        User user = (User) session.getAttribute(USER);
        if (user != null) {
            userId = user.getId();
        } else {
            //throw new CommandException("Wrong data parsing", e);
            return page;
        }

        ServiceManager factory = ServiceManager.getInstance();
        LotService lotService = factory.getLotService();
        BigDecimal minBet = lotService.determineLotMinBet(lotId);

        session.setAttribute(COMMAND, request.getParameter(COMMAND));
        boolean validBet = new Validator().betDataValidate(curBet, minBet);
        if (!validBet) {
            session.setAttribute(ERROR_BET, ERROR_BET);
            return page;
        }

        UserService userService = factory.getUserService();
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

        BetService betService = factory.getBetService();
        validBet = betService.addBet(bet, balance);
        if (validBet) {
            userService = factory.getUserService();
            user = userService.receiveUserById(userId);
            session.setAttribute(USER, user);
            session.setAttribute(BET_ACCEPT, BET_ACCEPT);
        } else {
            session.setAttribute(ERROR_FINISH, ERROR_FINISH);
        }
        return page;
    }

    @Override
    public void resetSessionMessage(HttpSession session) {
        session.removeAttribute(ERROR_BET);
        session.removeAttribute(ERROR_BALANCE);
        session.removeAttribute(ERROR_FINISH);
        session.removeAttribute(BET_ACCEPT);
        session.removeAttribute(COMMAND);
    }
}
