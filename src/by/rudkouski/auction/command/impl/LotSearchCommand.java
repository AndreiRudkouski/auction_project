package by.rudkouski.auction.command.impl;

import by.rudkouski.auction.command.ICommand;
import by.rudkouski.auction.service.exception.ServiceException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;

public class LotSearchCommand implements ICommand {
    private static final String MAIN_PAGE = "main.jsp";
    private static final String FIELD_SEARCH = "fieldSearch";
    private static final String ENCODING = "ISO-8859-1";
    private static final String ERROR_MESSAGE = "errorMessage";

    @Override
    public String execute(HttpServletRequest request) {
        try {
            String search = new String(request.getParameter(FIELD_SEARCH).getBytes(ENCODING), request.getCharacterEncoding());
            if (search != null && !search.isEmpty()) {
                searchLot(request, search, 0);
            }
        } catch (UnsupportedEncodingException | ServiceException e) {
            //log("Wrong data parsing", e);
            HttpSession session = request.getSession();
            session.setAttribute(ERROR_MESSAGE, ERROR_MESSAGE);
        }
        return MAIN_PAGE;
    }

    @Override
    public void resetSessionMessage(HttpSession session) {
        session.removeAttribute(ERROR_MESSAGE);
    }
}