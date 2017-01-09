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
import java.util.List;

import static by.rudkouski.auction.constant.ConstantName.*;

public class SetupLotCommand implements ICommand {
    private static final Logger LOGGER = LogManager.getLogger(SetupLotCommand.class);

    @Override
    public String execute(HttpServletRequest request) {
        ServiceManager manager = ServiceManager.getInstance();
        LotService lotService = manager.getLotService();
        List<Lot> lotList;
        try {
            lotList = lotService.setupLot();
        } catch (ServiceException e) {
            LOGGER.log(Level.FATAL, "Exception: ", e);
            throw new RuntimeException("Error of setup lots", e);
        }

        request.getSession().setAttribute(SAVE_REQ, null);
        request.setAttribute(LOT_LIST, lotList);
        return null;
    }

    @Override
    public void resetSessionMessage(HttpSession session) {
    }
}