package by.rudkouski.auction.controller;

import by.rudkouski.auction.command.ICommand;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Locale;

public class CommandDefiner {
    static final String COMMAND = "command";

    public ICommand defineCommandRequest(HttpServletRequest request) {
        String action = request.getParameter(COMMAND);
        if (action == null || action.isEmpty()) {
            throw new RuntimeException("Do not find command name");
        }
        return receiveCommand(action);
    }

    public ICommand defineCommandSession(HttpSession session) {
        String action = (String) session.getAttribute(COMMAND);
        if (action == null || action.isEmpty()) {
            return null;
        }
        return receiveCommand(action);
    }

    private ICommand receiveCommand(String name) {
        try {
            CommandType currentType = CommandType.valueOf(name.toUpperCase(Locale.ENGLISH));
            return currentType.getCurrentCommand();
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Do not find type for command creating");
        }
    }
}
