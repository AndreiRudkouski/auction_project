package by.rudkouski.auction.command.impl;

import by.rudkouski.auction.bean.impl.Lot;
import by.rudkouski.auction.bean.impl.User;
import by.rudkouski.auction.command.ICommand;
import by.rudkouski.auction.service.ServiceManager;
import by.rudkouski.auction.service.exception.ServiceException;
import by.rudkouski.auction.service.impl.LotService;
import by.rudkouski.auction.validation.Validator;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class LotSaveCommand implements ICommand {
    private static final Logger LOGGER = LogManager.getLogger(LotSaveCommand.class);
    private static final String USER = "user";
    private static final String NEW_LOT = "newLot";
    private static final String EMPTY_LINE = "";
    private static final String ERROR_LOT = "errorLot";
    private static final String PROFILE = "profile";
    private static final String CHANGE_ACCEPT = "changeAccept";
    private static final String ERROR_MESSAGE = "errorMessage";
    private static final String MAIN_PAGE = "main.jsp";
    private static final String LOT_ID = "lotId";

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

        Lot lot = new Validator().newLotValidateAndCreate(request);
        if (lot == null) {
            request.setAttribute(NEW_LOT, NEW_LOT);
            request.setAttribute(ERROR_LOT, ERROR_LOT);
            request.setAttribute(PROFILE, PROFILE);
            return MAIN_PAGE;
        }
        user = new User();
        user.setId(userId);
        lot.setUser(user);

        String page = returnPage(session);
        ServiceManager manager = ServiceManager.getInstance();
        LotService lotService = manager.getLotService();
        boolean resultEdit = false;
        boolean resultSave = false;
        long lotId;
        String id = request.getParameter(LOT_ID);
        String appPath = request.getServletContext().getRealPath(EMPTY_LINE);
        try {
            if (id != null && !id.isEmpty()) {
                lotId = Long.parseLong(id);
                lot.setId(lotId);
                resultEdit = lotService.editLot(lot, appPath);
            } else {
                resultSave = lotService.addLot(lot, appPath);
            }
        } catch (NumberFormatException | ServiceException e) {
            LOGGER.log(Level.ERROR, "Exception: ", e);
            session.setAttribute(ERROR_MESSAGE, ERROR_MESSAGE);
            return page;
        }

        if (resultEdit || resultSave) {
            session.setAttribute(CHANGE_ACCEPT, CHANGE_ACCEPT);
        } else {
            request.setAttribute(NEW_LOT, NEW_LOT);
            request.setAttribute(ERROR_LOT, ERROR_LOT);
            request.setAttribute(PROFILE, PROFILE);
            return MAIN_PAGE;
        }
        return page;
    }

    @Override
    public void resetSessionMessage(HttpSession session) {
        session.removeAttribute(CHANGE_ACCEPT);
        session.removeAttribute(ERROR_MESSAGE);
    }
}