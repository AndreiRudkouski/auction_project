package by.rudkouski.auction.command.impl;

import by.rudkouski.auction.bean.impl.User;
import by.rudkouski.auction.command.ICommand;
import by.rudkouski.auction.service.ServiceManager;
import by.rudkouski.auction.service.impl.CategoryService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

public class LotNewCommand implements ICommand {
    private static final String USER = "user";
    private static final String TYPE_LIST = "typeList";
    private static final String TERM_LIST = "termList";
    private static final String CONDITION_LIST = "condList";
    private static final String NEW_LOT = "newLot";
    private static final String PROFILE = "profile";
    private static final String MAIN_PAGE = "main.jsp";

    @Override
    public String execute(HttpServletRequest request) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(USER);
        if (user == null) {
            //throw new CommandException("Wrong data parsing", e);
            return MAIN_PAGE;
        }

        ServiceManager factory = ServiceManager.getInstance();
        CategoryService categoryService = factory.getCategoryService();
        List<List> setupList = categoryService.setupNewLotData();
        if (setupList.size() == 3) {
            request.setAttribute(TYPE_LIST, setupList.get(0));
            request.setAttribute(TERM_LIST, setupList.get(1));
            request.setAttribute(CONDITION_LIST, setupList.get(2));
        }
        request.setAttribute(NEW_LOT, NEW_LOT);
        request.setAttribute(PROFILE, PROFILE);
        return MAIN_PAGE;
    }
}
