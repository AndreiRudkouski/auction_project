package by.rudkouski.auction.command.impl;

import by.rudkouski.auction.command.ICommand;
import by.rudkouski.auction.service.ServiceManager;
import by.rudkouski.auction.service.exception.ServiceException;
import by.rudkouski.auction.service.impl.LotService;
import by.rudkouski.auction.validation.Validator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class LotCheckCommand implements ICommand {
    private static final String LOT_ID = "lotId";
    private static final String CHANGE_ACCEPT = "changeAccept";
    private static final String ERROR_MESSAGE = "errorMessage";

    @Override
    public String execute(HttpServletRequest request) {
        HttpSession session = request.getSession();
        if (!new Validator().userValidate(request)) {
            return MAIN_PAGE;
        }

        long lotId;
        String page = returnPage(session);
        try {
            lotId = Long.parseLong(request.getParameter(LOT_ID));
            ServiceManager manager = ServiceManager.getInstance();
            LotService lotService = manager.getLotService();
            lotService.checkLot(lotId);
        } catch (NumberFormatException | ServiceException e) {
            //log("Wrong data parsing", e);
            session.setAttribute(ERROR_MESSAGE, ERROR_MESSAGE);
            return page;
        }
        session.setAttribute(CHANGE_ACCEPT, CHANGE_ACCEPT);
        return page;
    }

    @Override
    public void resetSessionMessage(HttpSession session) {
        session.removeAttribute(CHANGE_ACCEPT);
        session.removeAttribute(ERROR_MESSAGE);
    }
}
