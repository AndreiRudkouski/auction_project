package by.rudkouski.auction.controller;

import by.rudkouski.auction.command.ICommand;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Locale;

import static by.rudkouski.auction.constant.ConstantName.*;

public class CommandManager {
    private static final Logger LOGGER = LogManager.getLogger(CommandManager.class);

    public ICommand defineCommandRequest(HttpServletRequest request) {
        String action = request.getParameter(COMMAND);
        if (action == null || action.isEmpty()) {
            LOGGER.log(Level.FATAL, "Command name is null or empty");
            throw new RuntimeException("Do not find command name");
        }
        return receiveCommand(action);
    }

    public ICommand defineCommandSession(HttpSession session) {
        String action = null;
        if (session != null) {
            action = (String) session.getAttribute(COMMAND);
        }
        if (action == null || action.isEmpty()) {
            return null;
        }
        return receiveCommand(action);
    }

    public void saveCommandSession(HttpServletRequest request, boolean isRedirect) {
        HttpSession session = request.getSession();
        if (session != null) {
            if (session.getAttribute(COMMAND) == null) {
                session.setAttribute(COMMAND, request.getParameter(COMMAND));
                session.setAttribute(SEND_TYPE, isRedirect);
            } else {
                boolean prevSendType = (boolean) session.getAttribute(SEND_TYPE);
                if (prevSendType) {
                    session.setAttribute(SEND_TYPE, !prevSendType);
                } else {
                    session.setAttribute(COMMAND, request.getParameter(COMMAND));
                    session.setAttribute(SEND_TYPE, isRedirect);
                }
            }
        }
    }

    private ICommand receiveCommand(String name) {
        try {
            CommandType currentType = CommandType.valueOf(name.toUpperCase(Locale.ENGLISH));
            return currentType.getCurrentCommand();
        } catch (IllegalArgumentException e) {
            LOGGER.log(Level.FATAL, "Error command type", e);
            throw new RuntimeException("Do not find type for command creating");
        }
    }
}