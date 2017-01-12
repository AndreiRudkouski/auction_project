package by.rudkouski.auction.service;

import by.rudkouski.auction.entity.impl.User;
import by.rudkouski.auction.service.exception.ServiceException;

import java.math.BigDecimal;
import java.util.List;

public interface IUserService<T extends User> {

    T logInUser(String mail, String password) throws ServiceException;

    T registerUser(String mail, String password) throws ServiceException;

    T receiveUserById(long userId) throws ServiceException;

    T changeUserLogin(long userId, String login) throws ServiceException;

    T changeUserPassword(long userId, String oldPassword, String newPassword) throws ServiceException;

    T fillUserBalanceById(long userId, BigDecimal amount) throws ServiceException;

    T changeBanUserById(long userId) throws ServiceException;

    BigDecimal receiveUserBalance(long userId) throws ServiceException;

    List<T> searchUserByLoginMail(String search) throws ServiceException;
}
