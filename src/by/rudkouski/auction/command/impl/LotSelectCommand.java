package by.rudkouski.auction.command.impl;

import by.rudkouski.auction.bean.impl.Lot;
import by.rudkouski.auction.command.ICommand;
import by.rudkouski.auction.service.ServiceManager;
import by.rudkouski.auction.service.impl.LotService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

public class LotSelectCommand implements ICommand {
    private static final String LOT_SELECT = "lotSelect";
    private static final String LOT_LIST_FINISH = "lotListFinished";
    private static final String LOT_LIST_UNFINISHED = "lotListUnfinished";
    private static final String LOT_LIST_UNCHECKED = "lotListUnchecked";
    private static final long NULL_USER_ID = 0;

    @Override
    public String execute(HttpServletRequest request) {
        String select = request.getParameter(LOT_SELECT);
        if (select != null && !select.isEmpty()) {
            ServiceManager manager = ServiceManager.getInstance();
            LotService lotService = manager.getLotService();
            List<Lot> lotList;
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
        }
        return MAIN_PAGE;
    }

    @Override
    public void resetSessionMessage(HttpSession session) {
    }
}
