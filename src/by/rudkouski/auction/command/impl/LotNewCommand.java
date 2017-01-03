package by.rudkouski.auction.command.impl;

import by.rudkouski.auction.bean.impl.Lot;
import by.rudkouski.auction.command.ICommand;
import by.rudkouski.auction.service.ServiceManager;
import by.rudkouski.auction.service.impl.CategoryService;
import by.rudkouski.auction.service.impl.LotService;
import by.rudkouski.auction.validation.Validator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

public class LotNewCommand implements ICommand {
    private static final String TYPE_LIST = "typeList";
    private static final String TERM_LIST = "termList";
    private static final String CONDITION_LIST = "condList";
    private static final String NEW_LOT = "newLot";
    private static final String LOT = "lot";
    private static final String PROFILE = "profile";
    private static final String MAIN_PAGE = "main.jsp";
    private static final String LOT_ID = "lotId";

    @Override
    public String execute(HttpServletRequest request) {
        if (!new Validator().userValidate(request)) {
            //throw new CommandException("Wrong data parsing", e);
            return MAIN_PAGE;
        }

        ServiceManager manager = ServiceManager.getInstance();
        CategoryService categoryService = manager.getCategoryService();
        List<List> setupList = categoryService.setupNewLotData();
        if (setupList.size() == 3) {
            request.setAttribute(TYPE_LIST, setupList.get(0));
            request.setAttribute(TERM_LIST, setupList.get(1));
            request.setAttribute(CONDITION_LIST, setupList.get(2));
        }

        long lotId;
        String id = request.getParameter(LOT_ID);
        if (id != null && !id.isEmpty()) {
            try {
                lotId = Long.parseLong(id);
            } catch (NumberFormatException e) {
                //throw new CommandException("Wrong data parsing", e);
                return MAIN_PAGE;
            }
            LotService lotService = manager.getLotService();
            Lot lot = lotService.searchLotById(lotId);
            request.setAttribute(LOT, lot);
        }
        request.setAttribute(NEW_LOT, NEW_LOT);
        request.setAttribute(PROFILE, PROFILE);
        return MAIN_PAGE;
    }

    @Override
    public void resetSessionMessage(HttpSession session) {
    }
}
