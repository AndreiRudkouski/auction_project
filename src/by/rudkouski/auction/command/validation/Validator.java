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
import java.util.Map;

import static by.rudkouski.auction.constant.ConstantName.*;

/**
 * This class contains methods for data validation
 */

public class Validator {
    private static final Logger LOGGER = LogManager.getLogger(Validator.class);

    public static boolean userMailValidate(String mail) {
        if (mail == null || mail.isEmpty() || !mail.matches(REGEX_MAIL)) {
            return false;
        }
        return true;
    }

    public static boolean userPasswordValidate(String password) {
        if (password == null || password.isEmpty() || password.length() < PWD_LENGTH || !password.matches(REGEX_PWD)) {
            return false;
        }
        return true;
    }

    public static boolean betDataValidate(BigDecimal bet, BigDecimal minBet) {
        if (bet == null || minBet == null) {
            return false;
        } else {
            if (bet.compareTo(minBet) < 0 || bet.compareTo(MAX_AMOUNT) > 0) {
                return false;
            }
        }
        return true;
    }

    public static boolean userLoginChangeValidate(String newLogin, String oldLogin) {
        return newLogin == null || newLogin.equals(oldLogin) || newLogin.length() > LOGIN_LENGTH;
    }

    public static boolean cardNumberValidate(String cardNum) {
        return cardNum == null || cardNum.isEmpty() || cardNum.length() != CARD_NUMBER_LENGTH;
    }

    public static boolean amountValidate(BigDecimal amount) {
        return amount.compareTo(MIN_AMOUNT) < 0 || amount.compareTo(MAX_AMOUNT) > 0;
    }

    public static boolean lotDataValidate(Map<String, String[]> paramMap) {
        String title = paramMap.get(TITLE) != null ? paramMap.get(TITLE)[0] : null;
        String description = paramMap.get(DESCRIPTION) != null ? paramMap.get(DESCRIPTION)[0] : null;
        if (title == null || title.isEmpty() || title.length() > TITLE_LENGTH || description == null || description.isEmpty()) {
            return false;
        }
        long typeId;
        try {
            Long.parseLong(paramMap.get(CATEGORY) != null ? paramMap.get(CATEGORY)[0] : CATEGORY);
            typeId = Long.parseLong(paramMap.get(TYPE) != null ? paramMap.get(TYPE)[0] : TYPE);
            Long.parseLong(paramMap.get(TERM) != null ? paramMap.get(TERM)[0] : TERM);
            Long.parseLong(paramMap.get(CONDITION)[0] != null ? paramMap.get(CONDITION)[0] : CONDITION);
        } catch (NumberFormatException e) {
            LOGGER.log(Level.ERROR, "Exception: ", e);
            return false;
        }

        String priceStartTmp = paramMap.get(PRICE_START) != null ? paramMap.get(PRICE_START)[0] : null;
        BigDecimal priceStart = priceStartTmp != null && !priceStartTmp.isEmpty() ? new BigDecimal(paramMap.get(PRICE_START)[0]) : null;
        if (priceStart == null || priceStart.compareTo(ZERO_AMOUNT) <= 0 || priceStart.compareTo(MAX_AMOUNT) > 0) {
            return false;
        }

        String priceStepTmp = paramMap.get(PRICE_STEP) != null ? paramMap.get(PRICE_STEP)[0] : null;
        BigDecimal priceStep = priceStepTmp != null && !priceStepTmp.isEmpty() ? new BigDecimal(paramMap.get(PRICE_STEP)[0]) : null;
        if (priceStep != null && (priceStep.compareTo(ZERO_AMOUNT) <= 0 || priceStart.compareTo(MAX_AMOUNT) > 0)) {
            return false;
        }

        if (typeId == BLITZ_AUCTION_TYPE_ID) {
            String priceBlitzTmp = paramMap.get(PRICE_BLITZ) != null ? paramMap.get(PRICE_BLITZ)[0] : null;
            BigDecimal priceBlitz = priceBlitzTmp != null && !priceBlitzTmp.isEmpty() ? new BigDecimal(paramMap.get(PRICE_BLITZ)[0]) : null;
            if (priceBlitz == null || priceBlitz.compareTo(ZERO_AMOUNT) <= 0 || priceBlitz.compareTo(MAX_AMOUNT) > 0) {
                return false;
            }
        }

        return true;
    }

    public static boolean lotPhotoValidate(Map<String, String[]> paramMap, Part part) {
        String photo = null;
        if (part != null) {
            photo = extractFileName(part);
        }

        String oldPhoto = paramMap.get(OLD_PHOTO) != null ? paramMap.get(OLD_PHOTO)[0] : null;
        if ((photo == null || photo.isEmpty() || !photo.matches(REGEX_IMG_FILE)) && (oldPhoto == null || oldPhoto.isEmpty())) {
            return false;
        }

        if (photo != null) {
            paramMap.put(PHOTO, new String[]{photo});
        }
        return true;
    }

    private static String extractFileName(Part part) {
        String content = part.getHeader(CONTENT);
        String[] items = content.split(SEMICOLON_DIVIDE);
        for (String str : items) {
            if (str.trim().startsWith(FILE_NAME)) {
                return str.substring(str.indexOf(EQUAL_DIVIDE) + 2, str.length() - 1);
            }
        }
        return null;
    }

    public static boolean userValidate(HttpServletRequest request) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(USER);
        return user != null;
    }
}