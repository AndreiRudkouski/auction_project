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

public class LotChoiceCommand implements ICommand {
    private static final Logger LOGGER = LogManager.getLogger(LotChoiceCommand.class);
    private static final String MAIN_PAGE = "main.jsp";
    private static final String LOT_ID = "lotId";
    private static final String LOT = "lot";
    private static final String ERROR_MESSAGE = "errorMessage";

    @Override
    public String execute(HttpServletRequest request) {
        long lotId;
        HttpSession session = request.getSession();
        String page = returnPage(session);
        Lot lot;
        try {
            lotId = Long.parseLong(request.getParameter(LOT_ID));
            ServiceManager manager = ServiceManager.getInstance();
            LotService lotService = manager.getLotService();
            lot = lotService.searchLotById(lotId);
        } catch (NumberFormatException | ServiceException e) {
            LOGGER.log(Level.ERROR, "Exception: ", e);
            session.setAttribute(ERROR_MESSAGE, ERROR_MESSAGE);
            return page;
        }
        request.setAttribute(LOT, lot);
        return MAIN_PAGE;
    }

    @Override
    public void resetSessionMessage(HttpSession session) {
        session.removeAttribute(ERROR_MESSAGE);
    }
}