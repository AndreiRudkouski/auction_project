package by.rudkouski.auction.command.impl;

import by.rudkouski.auction.bean.impl.Lot;
import by.rudkouski.auction.command.ICommand;
import by.rudkouski.auction.service.ServiceManager;
import by.rudkouski.auction.service.impl.LotService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class LotChoiceCommand implements ICommand {
    private static final String MAIN_PAGE = "main.jsp";
    private static final String LOT_ID = "lotId";
    private static final String LOT = "lot";

    @Override
    public String execute(HttpServletRequest request) {
        long lotId;
        HttpSession session = request.getSession();
        String page = returnPage(session);
        try {
            lotId = Long.parseLong(request.getParameter(LOT_ID));
        } catch (NumberFormatException e) {
            //throw new CommandException("Wrong data parsing", e);
            return page;
        }

        ServiceManager factory = ServiceManager.getInstance();
        LotService lotService = factory.getLotService();
        Lot lot = lotService.searchLotById(lotId);

        request.setAttribute(LOT, lot);
        return MAIN_PAGE;
    }

    @Override
    public void resetSessionMessage(HttpSession session) {
    }
}
