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
    private static final CommandDefiner DEFINER = new CommandDefiner();
    private static final String ERROR_USER = "errorUser";
    private static final String ERROR_BAN = "errorBan";
    private static final String ERROR_AUTH = "errorAuth";
    private static final String ERROR_BET = "errorBet";
    private static final String ERROR_BALANCE = "errorBalance";
    private static final String ERROR_FINISH = "errorFinish";
    private static final String BET_ACCEPT = "betAccept";
    private static final String CHANGE_ACCEPT = "changeAccept";
    private static final String ERROR_LOGIN = "errorLogin";
    private static final String ERROR_PWD = "errorPwd";
    private static final String ERROR_EXIST_LOGIN = "errorExistLogin";
    private static final String ERROR_CARD = "errorCard";
    private static final String ERROR_AMOUNT = "errorAmount";
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

        ICommand command = DEFINER.defineCommand(request);
        String page = command.execute(request);

        if (page != null) {
            if (page.matches(REGEX_SEND_REDIRECT)) {
                response.sendRedirect(page);
            } else {
                request.getRequestDispatcher(page).forward(request, response);
                HttpSession session = request.getSession();
                if (session != null) {
                    resetSessionMessage(session);
                }
            }
        }
    }

    private void resetSessionMessage(HttpSession session) {
        session.removeAttribute(ERROR_USER);
        session.removeAttribute(ERROR_BAN);
        session.removeAttribute(ERROR_AUTH);
        session.removeAttribute(ERROR_BET);
        session.removeAttribute(ERROR_BALANCE);
        session.removeAttribute(ERROR_FINISH);
        session.removeAttribute(BET_ACCEPT);
        session.removeAttribute(CHANGE_ACCEPT);
        session.removeAttribute(ERROR_LOGIN);
        session.removeAttribute(ERROR_PWD);
        session.removeAttribute(ERROR_EXIST_LOGIN);
        session.removeAttribute(ERROR_CARD);
        session.removeAttribute(ERROR_AMOUNT);
    }
}