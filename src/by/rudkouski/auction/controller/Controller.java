package by.rudkouski.auction.controller;

import by.rudkouski.auction.command.ICommand;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;

@WebServlet("/jsp/Controller")
@MultipartConfig
public class Controller extends HttpServlet {
    private static final CommandManager COM_MANAGER = new CommandManager();
    private static final String REGEX_SEND_REDIRECT = ".*(Controller).*";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        ICommand command = COM_MANAGER.defineCommandRequest(request);
        String page = command.execute(request);

        if (page != null) {
            if (page.matches(REGEX_SEND_REDIRECT)) {
                COM_MANAGER.saveCommandSession(request);
                response.sendRedirect(page);
            } else {
                request.getRequestDispatcher(page).forward(request, response);
                HttpSession session = request.getSession();
                if (session != null) {
                    command = COM_MANAGER.defineCommandSession(session);
                    if (command != null) {
                        command.resetSessionMessage(session);
                        COM_MANAGER.resetCommandSession(session);
                    }
                }
            }
        }
    }
}