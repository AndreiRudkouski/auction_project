package by.rudkouski.auction.command.validation;

import by.rudkouski.auction.entity.impl.*;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;

import static by.rudkouski.auction.constant.ConstantName.*;

public class Validator {
    private static final Logger LOGGER = LogManager.getLogger(Validator.class);

    public boolean userMailValidate(String mail) {
        if (mail == null || mail.isEmpty() || !mail.matches(REGEX_MAIL)) {
            return false;
        }
        return true;
    }

    public boolean userPasswordValidate(String password) {
        if (password == null || password.isEmpty() || password.length() < PWD_LENGTH || !password.matches(REGEX_PWD)) {
            return false;
        }
        return true;
    }

    public boolean betDataValidate(BigDecimal bet, BigDecimal minBet) {
        if (bet == null || minBet == null) {
            return false;
        } else {
            if (bet.compareTo(minBet) < 0 || bet.compareTo(MAX_AMOUNT) > 0) {
                return false;
            }
        }
        return true;
    }

    public boolean userLoginChangeValidate(String newLogin, String oldLogin) {
        return newLogin == null || newLogin.equals(oldLogin) || newLogin.length() > LOGIN_LENGTH ? false : true;
    }

    public boolean cardNumberValidate(String cardNum) {
        return cardNum == null || cardNum.isEmpty() || cardNum.length() != CARD_NUMBER_LENGTH ? false : true;
    }

    public boolean amountValidate(BigDecimal amount) {
        return amount.compareTo(MIN_AMOUNT) < 0 || amount.compareTo(MAX_AMOUNT) > 0 ? false : true;
    }

    public Lot newLotValidateAndCreate(HttpServletRequest request) {
        String title;
        long categoryId;
        BigDecimal priceStart;
        BigDecimal priceStep = null;
        BigDecimal priceBlitz = null;
        long typeId;
        long termId;
        long conditionId;
        String description;
        String oldPhoto;
        String photo = null;
        Part part;

        title = request.getParameter(TITLE);
        description = request.getParameter(DESCRIPTION);
        if (title == null || title.isEmpty() || title.length() > TITLE_LENGTH || description == null || description.isEmpty()) {
            return null;
        }
        try {
            categoryId = Long.parseLong(request.getParameter(CATEGORY));
            typeId = Long.parseLong(request.getParameter(TYPE));
            termId = Long.parseLong(request.getParameter(TERM));
            conditionId = Long.parseLong(request.getParameter(CONDITION));
        } catch (NumberFormatException e) {
            LOGGER.log(Level.ERROR, "Exception: ", e);
            return null;
        }
        priceStart = new BigDecimal(request.getParameter(PRICE_START));
        if (priceStart.compareTo(ZERO_AMOUNT) <= 0 || priceStart.compareTo(MAX_AMOUNT) > 0) {
            return null;
        }

        String priceStepTmp = request.getParameter(PRICE_STEP);
        if (priceStepTmp != null && !priceStepTmp.isEmpty()) {
            priceStep = new BigDecimal(request.getParameter(PRICE_STEP));
        }

        if (priceStep!= null && (priceStart.compareTo(ZERO_AMOUNT) <= 0 || priceStart.compareTo(MAX_AMOUNT) > 0)) {
            return null;
        }

        if (typeId == BLITZ_AUCTION_TYPE_ID) {
            priceBlitz = new BigDecimal(request.getParameter(PRICE_BLITZ));
            if (priceBlitz == null || priceStep.compareTo(ZERO_AMOUNT) <= 0 || priceStep.compareTo(MAX_AMOUNT) > 0 ||
                    priceBlitz.compareTo(ZERO_AMOUNT) <= 0 || priceBlitz.compareTo(MAX_AMOUNT) > 0) {
                return null;
            }
        }

        try {
            part = request.getPart(PHOTO);
            if (part != null) {
                photo = extractFileName(part);
            }
        } catch (IOException | ServletException e) {
            LOGGER.log(Level.ERROR, "Exception: ", e);
            return null;
        }
        oldPhoto = request.getParameter(OLD_PHOTO);
        if ((photo == null || photo.isEmpty() || !photo.matches(REGEX_IMG_FILE)) && (oldPhoto == null || oldPhoto.isEmpty())) {
            return null;
        }

        Lot lot = new Lot();
        lot.setName(title);
        lot.setPrice(priceStart);
        lot.setStepPrice(priceStep);
        lot.setPriceBlitz(priceBlitz);
        lot.setPhoto(photo);
        lot.setPart(part);
        lot.setDescription(description);
        lot.setCategoryId(categoryId);
        Type type = new Type();
        type.setId(typeId);
        lot.setType(type);
        Term term = new Term();
        term.setId(termId);
        lot.setTerm(term);
        Condition cond = new Condition();
        cond.setId(conditionId);
        lot.setCondition(cond);
        lot.setTimeStart(new Date(System.currentTimeMillis()));
        return lot;
    }

    private String extractFileName(Part part) {
        String content = part.getHeader(CONTENT);
        String[] items = content.split(SEMICOLON_DIVIDE);
        for (String str : items) {
            if (str.trim().startsWith(FILE_NAME)) {
                return str.substring(str.indexOf(EQUAL_DIVIDE) + 2, str.length() - 1);
            }
        }
        return null;
    }

    public boolean userValidate(HttpServletRequest request) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(USER);
        return user != null ? true : false;
    }
}