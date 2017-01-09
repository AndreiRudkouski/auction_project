package by.rudkouski.auction.command.impl;

import by.rudkouski.auction.command.ICommand;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static by.rudkouski.auction.constant.ConstantName.MAIN_PAGE;
import static by.rudkouski.auction.constant.ConstantName.PWD_SELECT;

public class AdminPasswordCommand implements ICommand {


    @Override
    public String execute(HttpServletRequest request) {
        request.setAttribute(PWD_SELECT, PWD_SELECT);
        return MAIN_PAGE;
    }

    @Override
    public void resetSessionMessage(HttpSession session) {
    }
}