package by.rudkouski.auction.command.impl;

import by.rudkouski.auction.command.ICommand;
import by.rudkouski.auction.service.ServiceManager;
import by.rudkouski.auction.service.impl.LotService;
import by.rudkouski.auction.validation.Validator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class LotCheckCommand implements ICommand {
    private static final String LOT_ID = "lotId";
    private static final String CHANGE_ACCEPT = "changeAccept";

    @Override
    public String execute(HttpServletRequest request) {
        HttpSession session = request.getSession();
        if (!new Validator().userValidate(request)) {
            //throw new CommandException("Wrong data parsing", e);
            return MAIN_PAGE;
        }

        long lotId;
        try {
            lotId = Long.parseLong(request.getParameter(LOT_ID));
        } catch (NumberFormatException e) {
            //throw new CommandException("Wrong data parsing", e);
            return MAIN_PAGE;
        }

        String page = returnPage(session);
        ServiceManager manager = ServiceManager.getInstance();
        LotService lotService = manager.getLotService();
        lotService.checkLot(lotId);
        session.setAttribute(CHANGE_ACCEPT, CHANGE_ACCEPT);
        return page;
    }

    @Override
    public void resetSessionMessage(HttpSession session) {
        session.removeAttribute(CHANGE_ACCEPT);
    }
}
