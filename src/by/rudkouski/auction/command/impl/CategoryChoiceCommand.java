package by.rudkouski.auction.command.impl;

import by.rudkouski.auction.command.ICommand;

import javax.servlet.http.HttpServletRequest;

public class CategoryChoiceCommand implements ICommand {
    private static final String MAIN_PAGE = "main.jsp";


    @Override
    public String execute(HttpServletRequest request) {
        categoryChoice(request, 0);
        return MAIN_PAGE;
    }
}
