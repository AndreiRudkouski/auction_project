package by.rudkouski.auction.controller;

import by.rudkouski.auction.command.ICommand;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;

public class CommandDefiner {
    static final String COMMAND = "command";

    public ICommand defineCommand(HttpServletRequest request) {
        String action = request.getParameter(COMMAND);
        if (action == null || action.isEmpty()) {
            throw new RuntimeException("Do not find command name");
        }

        try {
            CommandType currentType = CommandType.valueOf(action.toUpperCase(Locale.ENGLISH));
            return currentType.getCurrentCommand();
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Do not find type for command creating");
        }
    }
}
