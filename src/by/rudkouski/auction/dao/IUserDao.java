package by.rudkouski.auction.dao;

import by.rudkouski.auction.bean.AbstractEntity;
import by.rudkouski.auction.bean.impl.User;
import by.rudkouski.auction.dao.exception.DaoException;
import by.rudkouski.auction.pool.ProxyConnection;

import java.math.BigDecimal;
import java.util.List;

public interface IUserDao<T extends AbstractEntity> {
    T logInUser(String mail, String password) throws DaoException;

    void registerUser(String mail, String password) throws DaoException;

    T receiveUserById(long userId) throws DaoException;

    void changeBanUserById(long userId, boolean ban) throws DaoException;

    void changeUserLogin(long userId, String login) throws DaoException;

    void changeUserPassword(long userId, String password) throws DaoException;

    BigDecimal receiveUserBalance(long userId) throws DaoException;

    boolean checkUserPassword(long userId, String password) throws DaoException;

    boolean checkUniqueUserMail(String mail) throws DaoException;

    boolean checkUniqueUserLogin(String login) throws DaoException;

    void updateUserBalanceById(long userId, BigDecimal newBalance) throws DaoException;

    T receivePrevMaxBetUser(long lotId) throws DaoException;

    List<T> searchUserByLoginMail(String search) throws DaoException;
}
