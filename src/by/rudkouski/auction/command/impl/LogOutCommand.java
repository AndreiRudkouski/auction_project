package by.rudkouski.auction.command.impl;

import by.rudkouski.auction.command.ICommand;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static by.rudkouski.auction.constant.ConstantName.MAIN_PAGE;

public class LogOutCommand implements ICommand {

    @Override
    public String execute(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return MAIN_PAGE;
    }

    @Override
    public void resetSessionMessage(HttpSession session) {
    }
}