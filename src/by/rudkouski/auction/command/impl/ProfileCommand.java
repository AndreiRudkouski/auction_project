package by.rudkouski.auction.command.impl;

import by.rudkouski.auction.command.ICommand;

import javax.servlet.http.HttpServletRequest;

public class ProfileCommand implements ICommand {
    private static final String PROFILE = "profile";
    private static final String MAIN_PAGE = "main.jsp";

    @Override
    public String execute(HttpServletRequest request) {
        request.setAttribute(PROFILE, PROFILE);
        return MAIN_PAGE;
    }
}
