package by.rudkouski.auction.command.impl;

import by.rudkouski.auction.entity.impl.Lot;
import by.rudkouski.auction.entity.impl.User;
import by.rudkouski.auction.command.ICommand;
import by.rudkouski.auction.service.ServiceManager;
import by.rudkouski.auction.service.exception.ServiceException;
import by.rudkouski.auction.service.impl.LotService;
import by.rudkouski.auction.command.validation.Validator;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static by.rudkouski.auction.constant.ConstantName.*;

public class LotSaveCommand implements ICommand {
    private static final Logger LOGGER = LogManager.getLogger(LotSaveCommand.class);

    @Override
    public String execute(HttpServletRequest request) {
        long userId;
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(USER);
        if (user != null) {
            userId = user.getId();
        } else {
            return MAIN_PAGE;
        }

        Map<String, String[]> paramMap = new HashMap<>(request.getParameterMap());
        boolean validLot = new Validator().lotDataValidate(paramMap);
        Part part;
        try {
            part = request.getPart(PHOTO);
        } catch (IOException | ServletException e) {
            LOGGER.log(Level.ERROR, "Exception: ", e);
            session.setAttribute(ERROR_MESSAGE, ERROR_MESSAGE);
            return MAIN_PAGE;
        }
        boolean validPhoto = new Validator().lotPhotoValidate(paramMap, part);
        if (!validLot || !validPhoto) {
            request.setAttribute(NEW_LOT, NEW_LOT);
            request.setAttribute(ERROR_LOT, ERROR_LOT);
            request.setAttribute(PROFILE, PROFILE);
            return MAIN_PAGE;
        }
        paramMap.put(USER_ID, new String[] {String.valueOf(userId)});

        String page = returnPage(session);
        ServiceManager manager = ServiceManager.getInstance();
        LotService lotService = manager.getLotService();
        boolean resultEdit = false;
        boolean resultSave = false;
        String lotId = request.getParameter(LOT_ID);
        String appPath = request.getServletContext().getRealPath(EMPTY_LINE);
        try {
            if (lotId != null && !lotId.isEmpty()) {
                paramMap.put(LOT_ID, new String[] {lotId});
                resultEdit = lotService.editLot(paramMap, part, appPath);
            } else {
                resultSave = lotService.addLot(paramMap, part, appPath);
            }
        } catch (NumberFormatException | ServiceException e) {
            LOGGER.log(Level.ERROR, "Exception: ", e);
            session.setAttribute(ERROR_MESSAGE, ERROR_MESSAGE);
            return MAIN_PAGE;
        }

        if (resultEdit || resultSave) {
            session.setAttribute(CHANGE_ACCEPT, CHANGE_ACCEPT);
        } else {
            request.setAttribute(NEW_LOT, NEW_LOT);
            request.setAttribute(ERROR_LOT, ERROR_LOT);
            request.setAttribute(PROFILE, PROFILE);
            return MAIN_PAGE;
        }
        return page;
    }

    @Override
    public void resetSessionMessage(HttpSession session) {
        session.removeAttribute(CHANGE_ACCEPT);
        session.removeAttribute(ERROR_MESSAGE);
    }
}