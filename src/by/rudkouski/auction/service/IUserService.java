package by.rudkouski.auction.service;

import by.rudkouski.auction.bean.AbstractEntity;

import java.math.BigDecimal;
import java.util.List;

public interface IUserService<T extends AbstractEntity> {
    T logInUser(String mail, String password);

    T registerUser(String mail, String password);

    T receiveUserById(long userId);

    T changeUserLogin(long userId, String login);

    T changeUserPassword(long userId, String oldPassword, String newPassword);

    T fillUserBalanceById(long userId, BigDecimal amount);

    BigDecimal receiveUserBalance(long userId);

    List<T> searchUserByLoginMail(String search);
}
