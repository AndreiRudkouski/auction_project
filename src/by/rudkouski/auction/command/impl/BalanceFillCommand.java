package by.rudkouski.auction.command.impl;

import by.rudkouski.auction.entity.impl.User;
import by.rudkouski.auction.command.ICommand;
import by.rudkouski.auction.service.ServiceManager;
import by.rudkouski.auction.service.exception.ServiceException;
import by.rudkouski.auction.service.impl.UserService;
import by.rudkouski.auction.command.validation.Validator;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;

import static by.rudkouski.auction.constant.ConstantName.*;

public class BalanceFillCommand implements ICommand {
    private static final Logger LOGGER = LogManager.getLogger(BalanceFillCommand.class);

    @Override
    public String execute(HttpServletRequest request) {
        String cardNum;
        long userId;
        BigDecimal amount;

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(USER);
        if (user != null) {
            userId = user.getId();
        } else {
            return MAIN_PAGE;
        }

        String page = returnPage(session);
        try {
            cardNum = request.getParameter(CARD_NUM);
            amount = new BigDecimal(request.getParameter(AMOUNT));
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
        } catch (NumberFormatException | ServiceException e) {
            LOGGER.log(Level.ERROR, "Exception: ", e);
            session.setAttribute(USER_MESSAGE, ERROR_MESSAGE);
            return MAIN_PAGE;
        }
        if (user != null) {
            session.setAttribute(USER, user);
            session.setAttribute(USER_MESSAGE, CHANGE_ACCEPT);
        }
        return page;
    }

    @Override
    public void resetSessionMessage(HttpSession session) {
        session.removeAttribute(ERROR_CARD);
        session.removeAttribute(ERROR_AMOUNT);
        session.removeAttribute(USER_MESSAGE);
    }
}