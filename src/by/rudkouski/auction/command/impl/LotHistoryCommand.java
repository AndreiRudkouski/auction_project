package by.rudkouski.auction.command.impl;

import by.rudkouski.auction.entity.EntityListTag;
import by.rudkouski.auction.entity.impl.Lot;
import by.rudkouski.auction.entity.impl.User;
import by.rudkouski.auction.command.ICommand;
import by.rudkouski.auction.service.ServiceManager;
import by.rudkouski.auction.service.exception.ServiceException;
import by.rudkouski.auction.service.impl.LotService;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

import static by.rudkouski.auction.constant.ConstantName.*;

public class LotHistoryCommand implements ICommand {
    private static final Logger LOGGER = LogManager.getLogger(LotHistoryCommand.class);

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
            LotService lotService = manager.getLotService();
            List<List<Lot>> lotResult = lotService.receiveLotHistoryByUser(userId);
            if (lotResult != null && lotResult.size() == RESULT_LOT_LIST_SIZE) {
                EntityListTag<Lot> resultList = new EntityListTag<>(lotResult.get(0));
                request.setAttribute(LOT_LIST_FINISH, resultList);
                resultList = new EntityListTag<>(lotResult.get(1));
                request.setAttribute(LOT_LIST_UNFINISHED, resultList);
                resultList = new EntityListTag<>(lotResult.get(2));
                request.setAttribute(LOT_LIST_UNCHECKED, resultList);
                resultList = new EntityListTag<>(lotResult.get(3));
                request.setAttribute(LOT_LIST_REMOVED, resultList);
                request.setAttribute(LOT_HISTORY, LOT_HISTORY);
            }
            request.setAttribute(PROFILE, PROFILE);
        } catch (NumberFormatException | ServiceException e) {
            LOGGER.log(Level.ERROR, "Exception: ", e);
            session.setAttribute(USER_MESSAGE, ERROR_MESSAGE);
        }
        return MAIN_PAGE;
    }

    @Override
    public void resetSessionMessage(HttpSession session) {
        session.removeAttribute(USER_MESSAGE);
    }
}