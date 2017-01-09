package by.rudkouski.auction.command.impl;

import by.rudkouski.auction.bean.impl.Lot;
import by.rudkouski.auction.command.ICommand;
import by.rudkouski.auction.service.ServiceManager;
import by.rudkouski.auction.service.exception.ServiceException;
import by.rudkouski.auction.service.impl.LotService;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static by.rudkouski.auction.constant.ConstantName.*;

public class LotChoiceCommand implements ICommand {
    private static final Logger LOGGER = LogManager.getLogger(LotChoiceCommand.class);

    @Override
    public String execute(HttpServletRequest request) {
        try {
            long lotId = Long.parseLong(request.getParameter(LOT_ID));
            ServiceManager manager = ServiceManager.getInstance();
            LotService lotService = manager.getLotService();
            Lot lot = lotService.searchLotById(lotId);
            request.setAttribute(LOT, lot);
        } catch (NumberFormatException | ServiceException e) {
            HttpSession session = request.getSession();
            LOGGER.log(Level.ERROR, "Exception: ", e);
            session.setAttribute(ERROR_MESSAGE, ERROR_MESSAGE);
        }
        return MAIN_PAGE;
    }

    @Override
    public void resetSessionMessage(HttpSession session) {
        session.removeAttribute(ERROR_MESSAGE);
    }
}