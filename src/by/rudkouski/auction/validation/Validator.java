package by.rudkouski.auction.validation;

import by.rudkouski.auction.bean.impl.Condition;
import by.rudkouski.auction.bean.impl.Lot;
import by.rudkouski.auction.bean.impl.Term;
import by.rudkouski.auction.bean.impl.Type;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;

public class Validator {
    private static final String MAIL_REGEX = "^[-\\w.]+@([A-z0-9][-A-z0-9]+\\.)+[A-z]{2,4}$";
    private static final String PWD_REGEX = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?!.*\\s).*$";
    private static final int PWD_LENGTH = 6;
    private static final int LOGIN_LENGTH = 20;
    private static final int CARD_NUMBER_LENGTH = 16;
    private static final int TITLE_LENGTH = 90;
    private static final BigDecimal ZERO_AMOUNT = new BigDecimal(0);
    private static final BigDecimal MIN_AMOUNT = new BigDecimal(10);
    private static final BigDecimal MAX_AMOUNT = new BigDecimal(9999.99);
    private static final long BLITZ_AUCTION_TYPE_ID = 2;
    private static final String TITLE = "title";
    private static final String DESCRIPTION = "description";
    private static final String CATEGORY = "category";
    private static final String TYPE = "type";
    private static final String TERM = "term";
    private static final String CONDITION = "condition";
    private static final String PRICE_START = "priceStart";
    private static final String PRICE_STEP = "priceStep";
    private static final String PRICE_BLITZ = "priceBlitz";
    private static final String PHOTO = "photo";
    private static final String REGEX_IMG_FILE = ".+\\.(jpe?g)$";
    private static final String CONTENT = "content-disposition";
    private static final String FILE_NAME = "filename";
    private static final String SEMICOLON_DIVIDE = ";";
    private static final String EQUAL_DIVIDE = "=";

    public boolean userMailValidate(String mail) {
        if (mail == null || mail.isEmpty() || !mail.matches(MAIL_REGEX)) {
            return false;
        }
        return true;
    }

    public boolean userPasswordValidate(String password) {
        if (password == null || password.isEmpty() || password.length() < PWD_LENGTH || !password.matches(PWD_REGEX)) {
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
        BigDecimal priceStep;
        BigDecimal priceBlitz = null;
        long typeId;
        long termId;
        long conditionId;
        String description;
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
            //throw new CommandException("Wrong data parsing", e);
            return null;
        }
        priceStart = new BigDecimal(request.getParameter(PRICE_START));
        if (priceStart.compareTo(ZERO_AMOUNT) <= 0 || priceStart.compareTo(MAX_AMOUNT) > 0) {
            return null;
        }

        priceStep = new BigDecimal(request.getParameter(PRICE_STEP));
        if (priceStep != null && (priceStart.compareTo(ZERO_AMOUNT) <= 0 || priceStart.compareTo(MAX_AMOUNT) > 0)) {
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
            //throw new CommandException("Wrong data parsing", e);
            return null;
        }
        if (photo == null || photo.isEmpty() || !photo.matches(REGEX_IMG_FILE)) {
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
}