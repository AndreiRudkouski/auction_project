package by.rudkouski.auction.command.impl;

import by.rudkouski.auction.bean.impl.User;
import by.rudkouski.auction.command.ICommand;
import by.rudkouski.auction.service.ServiceManager;
import by.rudkouski.auction.service.impl.UserService;
import by.rudkouski.auction.validation.Validator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;

public class BalanceFillCommand implements ICommand {
    private static final String CARD_NUM = "cardNum";
    private static final String AMOUNT = "amount";
    private static final String USER = "user";
    private static final String ERROR_CARD = "errorCard";
    private static final String ERROR_AMOUNT = "errorAmount";
    private static final String CHANGE_ACCEPT = "changeAccept";
    private static final String COMMAND = "command";

    @Override
    public String execute(HttpServletRequest request) {
        String cardNum;
        long userId;
        BigDecimal amount;

        HttpSession session = request.getSession();
        String page = returnPage(session);
        try {
            cardNum = request.getParameter(CARD_NUM);
            amount = new BigDecimal(request.getParameter(AMOUNT));
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

        session.setAttribute(COMMAND, request.getParameter(COMMAND));
        boolean validCard = new Validator().cardNumberValidate(cardNum);
        boolean validAmount = new Validator().amountValidate(amount);
        if (!validCard || !validAmount) {
            if (!validCard) {
                session.setAttribute(ERROR_CARD, ERROR_CARD);
            }
            if (!validAmount) {
                session.setAttribute(ERROR_AMOUNT, ERROR_AMOUNT);
            }
            return page;
        }

        ServiceManager manager = ServiceManager.getInstance();
        UserService userService = manager.getUserService();
        user = userService.fillUserBalanceById(userId, amount);
        if (user != null) {
            session.setAttribute(USER, user);
            session.setAttribute(CHANGE_ACCEPT, CHANGE_ACCEPT);
        }
        return page;
    }

    @Override
    public void resetSessionMessage(HttpSession session) {
        session.removeAttribute(ERROR_CARD);
        session.removeAttribute(ERROR_AMOUNT);
        session.removeAttribute(CHANGE_ACCEPT);
        session.removeAttribute(COMMAND);
    }
}
