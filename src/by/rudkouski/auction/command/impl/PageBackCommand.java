package by.rudkouski.auction.command.impl;

import by.rudkouski.auction.command.ICommand;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class PageBackCommand implements ICommand {
    private static final String MAIN_PAGE = "main.jsp";
    private static final String PAGE_LIST = "pageList";

    @Override
    public String execute(HttpServletRequest request) {
        String pageList = request.getParameter(PAGE_LIST);
        int page = 0;
        try {
            page = Integer.parseInt(pageList);
        } catch (NumberFormatException e) {
            //throw new CommandException("Wrong data parsing", e);
        }
        page = page != 0 ? page - 1 : 0;
        contentByPage(request, page);
        return MAIN_PAGE;
    }

    @Override
    public void resetSessionMessage(HttpSession session) {
    }
}
