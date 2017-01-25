package by.rudkouski.auction.command.impl;

import by.rudkouski.auction.entity.AbstractEntity;
import by.rudkouski.auction.entity.impl.Lot;
import by.rudkouski.auction.command.ICommand;
import by.rudkouski.auction.service.ServiceManager;
import by.rudkouski.auction.service.exception.ServiceException;
import by.rudkouski.auction.service.impl.CategoryService;
import by.rudkouski.auction.service.impl.LotService;
import by.rudkouski.auction.command.validation.Validator;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

import static by.rudkouski.auction.constant.ConstantName.*;

public class LotNewCommand implements ICommand {
    private static final Logger LOGGER = LogManager.getLogger(LotNewCommand.class);

    @Override
    public String execute(HttpServletRequest request) {
        if (!Validator.userValidate(request)) {
            return MAIN_PAGE;
        }

        ServiceManager manager = ServiceManager.getInstance();
        CategoryService categoryService = manager.getCategoryService();
        List<List<? extends AbstractEntity>> setupList;
        try {
            setupList = categoryService.setupNewLotData();
            if (setupList != null && setupList.size() == RARAMETER_LIST_SIZE) {
                request.setAttribute(TYPE_LIST, setupList.get(0));
                request.setAttribute(TERM_LIST, setupList.get(1));
                request.setAttribute(CONDITION_LIST, setupList.get(2));
            } else {
                request.setAttribute(PROFILE, PROFILE);
                return MAIN_PAGE;
            }

            long lotId;
            String id = request.getParameter(LOT_ID);
            if (id != null && !id.isEmpty()) {
                lotId = Long.parseLong(id);
                LotService lotService = manager.getLotService();
                Lot lot = lotService.searchLotById(lotId);
                request.setAttribute(LOT, lot);
            }
        } catch (NumberFormatException | ServiceException e) {
            LOGGER.log(Level.ERROR, "Exception: ", e);
            HttpSession session = request.getSession();
            session.setAttribute(USER_MESSAGE, ERROR_MESSAGE);
        }
        request.setAttribute(NEW_LOT, NEW_LOT);
        request.setAttribute(PROFILE, PROFILE);
        return MAIN_PAGE;
    }

    @Override
    public void resetSessionMessage(HttpSession session) {
        session.removeAttribute(USER_MESSAGE);
    }
}
