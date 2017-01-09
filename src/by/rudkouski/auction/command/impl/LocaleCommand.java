package by.rudkouski.auction.command.impl;

import by.rudkouski.auction.command.ICommand;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static by.rudkouski.auction.constant.ConstantName.*;

public class LocaleCommand implements ICommand {

    @Override
    public String execute(HttpServletRequest request) {
        HttpSession session = request.getSession();
        String name = (String) session.getAttribute(LOCALE);
        if (name == null || name == LOCALE_BE) {
            session.setAttribute(LOCALE, LOCALE_EN);
        } else {
            session.setAttribute(LOCALE, LOCALE_BE);
        }
        session = request.getSession();
        return returnPage(session);
    }

    @Override
    public void resetSessionMessage(HttpSession session) {
    }
}