package by.rudkouski.auction.command;

import by.rudkouski.auction.entity.impl.Lot;
import by.rudkouski.auction.service.ServiceManager;
import by.rudkouski.auction.service.exception.ServiceException;
import by.rudkouski.auction.service.impl.LotService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

import static by.rudkouski.auction.constant.ConstantName.*;

public interface ICommand {

    String execute(HttpServletRequest request);

    void resetSessionMessage(HttpSession session);

    default boolean searchLot(HttpServletRequest request, String search, int page) throws ServiceException {
        ServiceManager factory = ServiceManager.getInstance();
        LotService lotService = factory.getLotService();
        List<Lot> lotList = lotService.searchLotByName(search, page);

        request.setAttribute(LOT_SEARCH, search);
        request.setAttribute(LOT_LIST, lotList);
        request.setAttribute(PAGE_LIST, page);

        return true;
    }

    default boolean contentByPage(HttpServletRequest request, int page) throws ServiceException {
        boolean result;
        String search = request.getParameter(LOT_SEARCH);
        if (search != null && !search.isEmpty()) {
            result = searchLot(request, search, page);
        } else {
            result = categoryChoice(request, page);
        }

        return result;
    }

    default boolean categoryChoice(HttpServletRequest request, int page) throws ServiceException {
        long categoryId;
        try {
            categoryId = Long.parseLong(request.getParameter(CATEGORY_ID));
        } catch (NumberFormatException e) {
            throw new ServiceException(e);
        }
        if (categoryId <= 0) {
            return false;
        }

        ServiceManager factory = ServiceManager.getInstance();
        LotService lotService = factory.getLotService();
        List<Lot> lotList = lotService.searchLotByCategory(categoryId, page);

        request.setAttribute(CATEGORY_ID, categoryId);
        request.setAttribute(LOT_LIST, lotList);
        request.setAttribute(PAGE_LIST, page);

        return true;
    }

    default String returnPage(HttpSession session) {
        String url = (String) session.getAttribute(SAVE_REQ);
        return url != null ? url : MAIN_PAGE;
    }
}
