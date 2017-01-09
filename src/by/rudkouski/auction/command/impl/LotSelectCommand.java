package by.rudkouski.auction.command.impl;

import by.rudkouski.auction.bean.impl.Lot;
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
        if (!new Validator().userValidate(request)) {
            return MAIN_PAGE;
        }

        String select = request.getParameter(LOT_SELECT);
        if (select != null && !select.isEmpty()) {
            ServiceManager manager = ServiceManager.getInstance();
            LotService lotService = manager.getLotService();
            List<Lot> lotList;
            try {
                if (select.equals(LOT_LIST_FINISH)) {
                    lotList = lotService.receiveFinishedLotHistoryByUser(NULL_USER_ID);
                    request.setAttribute(LOT_LIST_FINISH, lotList);
                }
                if (select.equals(LOT_LIST_UNFINISHED)) {
                    lotList = lotService.receiveUnfinishedLotHistoryByUser(NULL_USER_ID);
                    request.setAttribute(LOT_LIST_UNFINISHED, lotList);
                }
                if (select.equals(LOT_LIST_UNCHECKED)) {
                    lotList = lotService.receiveUncheckedLotHistoryByUser(NULL_USER_ID);
                    request.setAttribute(LOT_LIST_UNCHECKED, lotList);
                }
                request.setAttribute(LOT_SELECT, LOT_SELECT);
            } catch (ServiceException e) {
                LOGGER.log(Level.ERROR, "Exception: ", e);
                HttpSession session = request.getSession();
                session.setAttribute(ERROR_MESSAGE, ERROR_MESSAGE);
            }
        }
        return MAIN_PAGE;
    }

    @Override
    public void resetSessionMessage(HttpSession session) {
        session.removeAttribute(ERROR_MESSAGE);
    }
}