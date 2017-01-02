package by.rudkouski.auction.dao;

import by.rudkouski.auction.bean.AbstractEntity;
import by.rudkouski.auction.bean.impl.User;
import by.rudkouski.auction.pool.ProxyConnection;

import java.math.BigDecimal;
import java.util.List;

public interface IUserDao<T extends AbstractEntity> {
    T logInUser(String mail, String password);

    boolean registerUser(String mail, String password);

    T receiveUserById(long userId);

    boolean changeBanUserById(long userId, boolean ban);

    boolean changeUserLogin(long userId, String login);

    boolean changeUserPassword(long userId, String password);

    BigDecimal receiveUserBalance(long userId);

    boolean checkUserPassword(long userId, String password);

    boolean checkUniqueUserMail(String mail);

    boolean checkUniqueUserLogin(String login);

    boolean updateUserBalanceById(long userId, BigDecimal newBalance);

    T receivePrevMaxBetUser(long lotId);

    List<T> searchUserByLoginMail(String search);
}
