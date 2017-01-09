package by.rudkouski.auction.command.impl;

import by.rudkouski.auction.command.ICommand;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static by.rudkouski.auction.constant.ConstantName.LOT_SELECT;
import static by.rudkouski.auction.constant.ConstantName.MAIN_PAGE;

public class AdminLotCommand implements ICommand {

    @Override
    public String execute(HttpServletRequest request) {
        request.setAttribute(LOT_SELECT, LOT_SELECT);
        return MAIN_PAGE;
    }

    @Override
    public void resetSessionMessage(HttpSession session) {
    }
}