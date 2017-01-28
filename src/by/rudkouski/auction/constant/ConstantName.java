package by.rudkouski.auction.constant;

import java.io.File;
import java.math.BigDecimal;

/**
 * This class contains all constant name of this application
 */

public final class ConstantName {
    public static final String AMOUNT = "amount";
    public static final String AMOUNT_BET = "amountBet";
    public static final String ANY_SYMBOL_SQL = "%";
    public static final String BET_ACCEPT = "betAccept";
    public static final String BET_HISTORY = "betHistory";
    public static final String BET_LIST_DONE = "betListDone";
    public static final String BET_LIST_WIN = "betListWin";
    public static final String CARD_NUM = "cardNum";
    public static final String CATEGORY = "category";
    public static final String CATEGORY_ID = "categoryId";
    public static final String CATEGORY_LIST = "categoryList";
    public static final String CHANGE_ACCEPT = "changeAccept";
    public static final String COMMAND = "command";
    public static final String CONDITION = "condition";
    public static final String CONDITION_LIST = "condList";
    public static final String CONTENT = "content-disposition";
    public static final String DESCRIPTION = "description";
    public static final String EQUAL_DIVIDE = "=";
    public static final String EMPTY_LINE = "";
    public static final String ENCODING = "encoding";
    public static final String ENCODING_ISO = "ISO-8859-1";
    public static final String ERROR_AMOUNT = "errorAmount";
    public static final String ERROR_AUTH = "errorAuth";
    public static final String ERROR_BALANCE = "errorBalance";
    public static final String ERROR_BALANCE_FILL = "errorBalanceFill";
    public static final String ERROR_BAN = "errorBan";
    public static final String ERROR_BET = "errorBet";
    public static final String ERROR_CARD = "errorCard";
    public static final String ERROR_EXIST_LOGIN = "errorExistLogin";
    public static final String ERROR_FINISH = "errorFinish";
    public static final String ERROR_LOGIN = "errorLogin";
    public static final String ERROR_LOT = "errorLot";
    public static final String ERROR_MAIL = "errorMail";
    public static final String ERROR_MESSAGE = "errorMessage";
    public static final String ERROR_PWD = "errorPwd";
    public static final String ERROR_USER = "errorUser";
    public static final String IMG_FOLDER_SAVE = File.separator + "img" + File.separator + "lots" + File.separator;
    public static final String IMG_FOLDER_LOAD = ".." + IMG_FOLDER_SAVE;
    public static final String INDEX_PAGE = "index_page";
    public static final String FIELD_SEARCH = "fieldSearch";
    public static final String FILE_NAME = "filename";
    public static final String FINISHED = "finished";
    public static final String FORMAT_DATE = "yyyy-MM-dd HH:mm:ss";
    public static final String LOCALE = "loc";
    public static final String LOCALE_BE = "be";
    public static final String LOCALE_EN = "en";
    public static final String LOGIN = "login";
    public static final String LOT = "lot";
    public static final String LOT_ID = "lotId";
    public static final String LOT_CHOICE_TYPE = "lotChoiceType";
    public static final String LOT_HISTORY = "lotHistory";
    public static final String LOT_LIST = "lotList";
    public static final String LOT_LIST_FINISH = "lotListFinished";
    public static final String LOT_LIST_REMOVED = "lotListRemoved";
    public static final String LOT_LIST_UNFINISHED = "lotListUnfinished";
    public static final String LOT_LIST_UNCHECKED = "lotListUnchecked";
    public static final String LOT_SEARCH = "lot_search";
    public static final String LOT_SELECT = "lotSelect";
    public static final String MAIL = "mail";
    public static final String MAIL_CONTENT = "You have sent request for changing your password. Your new password is ";
    public static final String MAIL_MESSAGE = "mailMessage";
    public static final String MAIL_THEME = "Change password on auction.by";
    public static final String MAIN_PAGE = "main.jsp";
    public static final String MD5_ALGORITHM = "MD5";
    public static final String NEW_LOT = "newLot";
    public static final String NEW_PWD = "newPwd";
    public static final String OLD_LOGIN = "userOldLogin";
    public static final String OLD_PHOTO = "oldPhoto";
    public static final String OLD_PWD = "oldPwd";
    public static final String PAGE_LIST = "pageList";
    public static final String PARAMETER_DIVIDER = "&";
    public static final String PHOTO = "photo";
    public static final String POINT = ".";
    public static final String PRICE_BLITZ = "priceBlitz";
    public static final String PRICE_START = "priceStart";
    public static final String PRICE_STEP = "priceStep";
    public static final String PROFILE = "profile";
    public static final String PWD = "pwd";
    public static final String PWD_SELECT = "pwdSelect";
    public static final String REGEX_IMG_FILE = ".+\\.(jpe?g)$";
    public static final String REGEX_MAIL = "^[-\\w.]+@([A-z0-9][-A-z0-9]+\\.)+[A-z]{2,4}$";
    public static final String REGEX_POINT_DIVIDER = "\\.";
    public static final String REGEX_PWD = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?!.*\\s).*$";
    public static final String REGEX_SEND_REDIRECT = ".*(Controller).*";
    public static final String SAVE_DIRECTORY = "saveDirectory";
    public static final String SAVE_REQ = "saveReq";
    public static final String SEMICOLON_DIVIDE = ";";
    public static final String SEND_TYPE = "sendType";
    public static final String TERM = "term";
    public static final String TERM_LIST = "termList";
    public static final String TITLE = "title";
    public static final String TYPE = "type";
    public static final String TYPE_LIST = "typeList";
    public static final String UNFINISHED = "unfinished";
    public static final String URL = "../jsp/Controller?";
    public static final String USER = "user";
    public static final String USER_ID = "userId";
    public static final String USER_LIST = "userList";
    public static final String USER_MESSAGE = "userMessage";
    public static final String USER_SEARCH = "userSearch";
    public static final String ZERO = "0";

    public static final long ADMIN_ROLE_ID = 2;
    public static final long BLIND_AUCTION_TYPE_ID = 3;
    public static final long BLIND_LOT_TYPE_ID = 3;
    public static final long BLITZ_AUCTION_TYPE_ID = 2;
    public static final long NULL_USER_ID = 0;

    public static final int CARD_NUMBER_LENGTH = 16;
    public static final int CHAR_START_NUMBER = 48;
    public static final int CHAR_START_SMALL_LETTER = 65;
    public static final int CHAR_START_BIG_LETTER = 97;
    public static final int COUNT_VIEW = 3;
    public static final int LOGIN_LENGTH = 20;
    public static final int PAGE_ZERO = 0;
    public static final int PARAMETER_LIST_SIZE = 3;
    public static final int PWD_LENGTH = 6;
    public static final int QTY_NUMBER = 9;
    public static final int QTY_LETTER = 25;
    public static final int QTY_PASSWORD_GENERATION = PWD_LENGTH / 2;
    public static final int RESULT_BET_LIST_SIZE = 2;
    public static final int RESULT_LOT_LIST_SIZE = 4;
    public static final int TITLE_LENGTH = 90;

    public static final BigDecimal MAX_AMOUNT = new BigDecimal("9999.99");
    public static final BigDecimal MIN_AMOUNT = new BigDecimal(10);
    public static final BigDecimal TEN_PERCENT = new BigDecimal("0.1");
    public static final BigDecimal ZERO_AMOUNT = new BigDecimal(0);

    //Connection Pool
    public static final String DB_PASSWORD = "db.password";
    public static final String DB_POOL_SIZE = "db.pool_size";
    public static final String DB_URL = "db.url";
    public static final String DB_USER = "db.user";
    public static final String BD_PROPERTIES = "/resource/db.properties";
    public static final int INIT_POOL_SIZE = 5;

    //Mail
    public static final String MAIL_PROPERTIES = "/resource/mail.properties";
    public static final String MAIL_PWD = "mail.user.pwd";
    public static final String MAIL_USER = "mail.user.name";

    private ConstantName() {
    }
}
