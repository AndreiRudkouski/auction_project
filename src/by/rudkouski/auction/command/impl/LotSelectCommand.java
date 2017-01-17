package by.rudkouski.auction.command.impl;

import by.rudkouski.auction.entity.EntityListTag;
import by.rudkouski.auction.entity.impl.Lot;
import by.rudkouski.auction.command.ICommand;
import by.rudkouski.auction.service.ServiceManager;
import by.rudkouski.auction.service.exception.ServiceException;
import by.rudkouski.auction.service.impl.LotService;
import by.rudkouski.auction.command.validation.Validator;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

import static by.rudkouski.auction.constant.ConstantName.*;

public class LotSelectCommand implements ICommand {
    private static final Logger LOGGER = LogManager.getLogger(LotSelectCommand.class);

    @Override
    public String execute(HttpServletRequest request) {
        if (!Validator.userValidate(request)) {
            return MAIN_PAGE;
        }

        String select = request.getParameter(LOT_SELECT);
        if (select != null && !select.isEmpty()) {
            ServiceManager manager = ServiceManager.getInstance();
            LotService lotService = manager.getLotService();
            List<Lot> lotList;
            EntityListTag<Lot> resultList;
            try {
                if (select.equals(LOT_LIST_FINISH)) {
                    lotList = lotService.receiveFinishedLotHistoryByUser(NULL_USER_ID);
                    resultList = new EntityListTag<>(lotList);
                    request.setAttribute(LOT_LIST_FINISH, resultList);
                }
                if (select.equals(LOT_LIST_UNFINISHED)) {
                    lotList = lotService.receiveUnfinishedLotHistoryByUser(NULL_USER_ID);
                    resultList = new EntityListTag<>(lotList);
                    request.setAttribute(LOT_LIST_UNFINISHED, resultList);
                }
                if (select.equals(LOT_LIST_UNCHECKED)) {
                    lotList = lotService.receiveUncheckedLotHistoryByUser(NULL_USER_ID);
                    resultList = new EntityListTag<>(lotList);
                    request.setAttribute(LOT_LIST_UNCHECKED, resultList);
                }
                request.setAttribute(LOT_SELECT, LOT_SELECT);
            } catch (ServiceException e) {
                LOGGER.log(Level.ERROR, "Exception: ", e);
                HttpSession session = request.getSession();
                session.setAttribute(USER_MESSAGE, ERROR_MESSAGE);
            }
        }
        return MAIN_PAGE;
    }

    @Override
    public void resetSessionMessage(HttpSession session) {
        session.removeAttribute(USER_MESSAGE);
    }
}