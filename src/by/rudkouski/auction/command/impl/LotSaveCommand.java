package by.rudkouski.auction.command.impl;

import by.rudkouski.auction.bean.impl.Lot;
import by.rudkouski.auction.bean.impl.User;
import by.rudkouski.auction.command.ICommand;
import by.rudkouski.auction.service.ServiceManager;
import by.rudkouski.auction.service.impl.LotService;
import by.rudkouski.auction.validation.Validator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class LotSaveCommand implements ICommand {
    private static final String USER = "user";
    private static final String NEW_LOT = "newLot";
    static final String EMPTY_LINE = "";
    private static final String ERROR_LOT = "errorLot";
    private static final String PROFILE = "profile";
    private static final String CHANGE_ACCEPT = "changeAccept";
    private static final String MAIN_PAGE = "main.jsp";

    @Override
    public String execute(HttpServletRequest request) {
        long userId;

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(USER);
        if (user != null) {
            userId = user.getId();
        } else {
            //throw new CommandException("Wrong data parsing", e);
            return MAIN_PAGE;
        }

        Lot lot = new Validator().newLotValidateAndCreate(request);
        if (lot == null) {
            request.setAttribute(NEW_LOT, NEW_LOT);
            request.setAttribute(ERROR_LOT, ERROR_LOT);
            request.setAttribute(PROFILE, PROFILE);
            return MAIN_PAGE;
        }
        lot.setUserId(userId);
        String appPath = request.getServletContext().getRealPath(EMPTY_LINE);


        ServiceManager manager = ServiceManager.getInstance();
        LotService lotService = manager.getLotService();
        boolean result = lotService.addLot(lot, appPath);
        if (result) {
            session.setAttribute(CHANGE_ACCEPT, CHANGE_ACCEPT);
        } else {
            request.setAttribute(NEW_LOT, NEW_LOT);
            request.setAttribute(ERROR_LOT, ERROR_LOT);
            request.setAttribute(PROFILE, PROFILE);
            return MAIN_PAGE;
        }

        String page = returnPage(session);
        return page;
    }

    @Override
    public void resetSessionMessage(HttpSession session) {
    }
}
