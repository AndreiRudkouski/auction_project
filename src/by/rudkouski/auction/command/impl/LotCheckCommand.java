package by.rudkouski.auction.command.impl;

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

import static by.rudkouski.auction.constant.ConstantName.*;

public class LotCheckCommand implements ICommand {
    private static final Logger LOGGER = LogManager.getLogger(LotCheckCommand.class);

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
            LOGGER.log(Level.ERROR, "Exception: ", e);
            session.setAttribute(USER_MESSAGE, ERROR_MESSAGE);
            return MAIN_PAGE;
        }
        session.setAttribute(USER_MESSAGE, CHANGE_ACCEPT);
        return page;
    }

    @Override
    public void resetSessionMessage(HttpSession session) {
        session.removeAttribute(USER_MESSAGE);
    }
}
