package by.rudkouski.auction.command.impl;

import by.rudkouski.auction.command.ICommand;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class AdminLotCommand implements ICommand {
    private static final String LOT_SELECT = "lotSelect";

    @Override
    public String execute(HttpServletRequest request) {
        request.setAttribute(LOT_SELECT, LOT_SELECT);
        return MAIN_PAGE;
    }

    @Override
    public void resetSessionMessage(HttpSession session) {
    }
}
