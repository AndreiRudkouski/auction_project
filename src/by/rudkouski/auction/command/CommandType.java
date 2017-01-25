package by.rudkouski.auction.command;

import by.rudkouski.auction.command.impl.*;

public enum CommandType {
    LOCALE(new LocaleCommand()),
    SETUP_CATEGORY(new SetupCategoryCommand()),
    SETUP_LOT(new SetupLotCommand()),
    CATEGORY_CHOICE(new CategoryChoiceCommand()),
    LOT_CHOICE(new LotChoiceCommand()),
    LOGIN(new LogInCommand()),
    REGISTER(new RegisterCommand()),
    LOGOUT(new LogOutCommand()),
    LOT_SEARCH(new LotSearchCommand()),
    PAGE_NEXT(new PageNextCommand()),
    PAGE_BACK(new PageBackCommand()),
    BET_ADD(new BetAddCommand()),
    PROFILE(new ProfileCommand()),
    LOT_HISTORY(new LotHistoryCommand()),
    BET_HISTORY(new BetHistoryCommand()),
    PROFILE_CHANGE(new ProfileChangeCommand()),
    BALANCE_FILL(new BalanceFillCommand()),
    LOT_NEW(new LotNewCommand()),
    LOT_SAVE(new LotSaveCommand()),
    USER_SEARCH(new UserSearchCommand()),
    USER_CHOICE(new UserChoiceCommand()),
    LOT_SELECT(new LotSelectCommand()),
    ADMIN_USER(new AdminUserCommand()),
    ADMIN_LOT(new AdminLotCommand()),
    ADMIN_PWD(new AdminPasswordCommand()),
    BAN_CHANGE(new BanChangeCommand()),
    LOT_CHECK(new LotCheckCommand()),
    PWD_FORGOT(new PwdForgotCommand()),
    LOT_REMOVE(new LotRemoveCommand());

    ICommand command;

    CommandType(ICommand command) {
        this.command = command;
    }

    public ICommand getCurrentCommand() {
        return command;
    }
}
