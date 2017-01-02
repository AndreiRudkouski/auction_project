package by.rudkouski.auction.command.impl;

import by.rudkouski.auction.command.ICommand;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class AdminPasswordCommand implements ICommand {
    private static final String PWD_SELECT = "pwdSelect";

    @Override
    public String execute(HttpServletRequest request) {
        request.setAttribute(PWD_SELECT, PWD_SELECT);
        return MAIN_PAGE;
    }

    @Override
    public void resetSessionMessage(HttpSession session) {
    }
}
