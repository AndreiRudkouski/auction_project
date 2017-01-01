package by.rudkouski.auction.command;

import by.rudkouski.auction.bean.impl.Lot;
import by.rudkouski.auction.service.ServiceManager;
import by.rudkouski.auction.service.impl.LotService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

public interface ICommand {
    public static final String LOT_SEARCH = "lot_search";
    public static final String LOT_LIST = "lotList";
    public static final String PAGE_LIST = "pageList";
    public static final String CATEGORY_ID = "categoryId";
    public static final String SAVE_REQ = "saveReq";
    public static final String MAIN_PAGE = "main.jsp";

    String execute(HttpServletRequest request);

    void resetSessionMessage(HttpSession session);

    default boolean searchLot(HttpServletRequest request, String search, int page) {
        ServiceManager factory = ServiceManager.getInstance();
        LotService lotService = factory.getLotService();
        List<Lot> lotList = lotService.searchLotByName(search, page);

        request.setAttribute(LOT_SEARCH, search);
        request.setAttribute(LOT_LIST, lotList);
        request.setAttribute(PAGE_LIST, page);

        return true;
    }

    default boolean contentByPage (HttpServletRequest request, int page) {
        boolean result;
        String search = request.getParameter(LOT_SEARCH);
        if (search != null && !search.isEmpty()) {
            result = searchLot(request, search, page);
        } else {
            result = categoryChoice(request, page);
        }

        return result;
    }

    default boolean categoryChoice(HttpServletRequest request, int page) {
        long categoryId;
        try {
            categoryId = Long.parseLong(request.getParameter(CATEGORY_ID));
        } catch (NumberFormatException e) {
            //throw new CommandException("Wrong data parsing", e);
            return false;
        }
        if (categoryId <= 0) {
            //throw new CommandException("Wrong category id", e);
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
