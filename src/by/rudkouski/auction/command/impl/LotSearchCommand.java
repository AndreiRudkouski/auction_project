package by.rudkouski.auction.command.impl;

import by.rudkouski.auction.command.ICommand;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;

public class LotSearchCommand implements ICommand {
    private static final String MAIN_PAGE = "main.jsp";
    private static final String FIELD_SEARCH = "field_search";
    private static final String ENCODING = "ISO-8859-1";

    @Override
    public String execute(HttpServletRequest request) {
        String search = null;
        try {
            search = new String(request.getParameter(FIELD_SEARCH).getBytes(ENCODING), request.getCharacterEncoding());
        } catch (UnsupportedEncodingException e) {
            //throw new CommandException("Wrong data encoding", e);
        }
        if (search != null && !search.isEmpty()) {
            searchLot(request, search, 0);
        }
        return MAIN_PAGE;
    }

    @Override
    public void resetSessionMessage(HttpSession session) {
    }
}
