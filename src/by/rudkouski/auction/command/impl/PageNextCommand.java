package by.rudkouski.auction.command.impl;

import by.rudkouski.auction.command.ICommand;

import javax.servlet.http.HttpServletRequest;

public class PageNextCommand implements ICommand {
    private static final String MAIN_PAGE = "main.jsp";
    private static final String PAGE_LIST = "pageList";

    @Override
    public String execute(HttpServletRequest request) {
        String pageList = request.getParameter(PAGE_LIST);
        int page = 1;
        if (pageList != null && !pageList.isEmpty()) {
            try {
                page = Integer.parseInt(pageList) + 1;
            } catch (NumberFormatException e) {
                //throw new CommandException("Wrong data parsing", e);
                return MAIN_PAGE;
            }
        }
        contentByPage(request, page);
        return MAIN_PAGE;
    }
}
