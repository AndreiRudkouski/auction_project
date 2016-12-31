package by.rudkouski.auction.command.impl;

import by.rudkouski.auction.bean.impl.Lot;
import by.rudkouski.auction.command.ICommand;
import by.rudkouski.auction.service.ServiceManager;
import by.rudkouski.auction.service.impl.LotService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

public class SetupLotCommand implements ICommand {
    private static final String SAVE_REQ = "saveReq";
    private static final String LOT_LIST = "lotList";

    @Override
    public String execute(HttpServletRequest request) {
        ServiceManager manager = ServiceManager.getInstance();
        LotService lotService = manager.getLotService();
        List<Lot> lotList = lotService.setupLot();

        request.getSession().setAttribute(SAVE_REQ, null);
        request.setAttribute(LOT_LIST, lotList);
        return null;
    }

    @Override
    public void resetSessionMessage(HttpSession session) {
    }
}
