package by.rudkouski.auction.service;

import by.rudkouski.auction.entity.impl.User;
import by.rudkouski.auction.service.exception.ServiceException;

import java.math.BigDecimal;
import java.util.List;

/**
 * This interface contains methods for data conversion and sending data to other layer.
 * Provides performance of the main application logic.
 * All methods are associated with entity "User"
 */

public interface IUserService<T extends User> {

    /**
     * Returns User from database.
     * Converts password by MD5 algorithm
     *
     * @param mail     user mail
     * @param password user password
     * @return User from database or null if the specified mail or password are not contained in database
     * @throws ServiceException if DaoException or ConnectionPoolException is thrown
     */
    T logInUser(String mail, String password) throws ServiceException;

    /**
     * Returns User after added into database.
     * Converts password by MD5 algorithm
     *
     * @param mail     user mail
     * @param password user password
     * @return User from database or null if the specified mail or password are not contained in database
     * @throws ServiceException if DaoException or ConnectionPoolException is thrown
     */
    T registerUser(String mail, String password) throws ServiceException;

    /**
     * Returns User from database
     *
     * @param userId unique user id
     * @return User from database or null if user whit the specified id is not contained in database
     * @throws ServiceException if DaoException or ConnectionPoolException is thrown
     */
    T receiveUserById(long userId) throws ServiceException;

    /**
     * Returns User after changing user login
     *
     * @param userId unique user id
     * @param login  new user login to be changed
     * @return User with new login or null if user login is not unique
     * @throws ServiceException if DaoException or ConnectionPoolException is thrown
     */
    T changeUserLogin(long userId, String login) throws ServiceException;

    /**
     * Returns User after changing user password
     *
     * @param userId   unique user id
     * @param oldPassword old user password for verification
     * @param newPassword new user password for changing
     * @return User with new password or null if old password of user is wrong
     * @throws ServiceException if DaoException or ConnectionPoolException is thrown
     */
    T changeUserPassword(long userId, String oldPassword, String newPassword) throws ServiceException;

    /**
     * Returns User after updating user balance
     *
     * @param userId   unique user id
     * @param amount amount for filling of user balance
     * @return User with new balance
     * @throws ServiceException if DaoException or ConnectionPoolException is thrown
     */
    T fillUserBalanceById(long userId, BigDecimal amount) throws ServiceException;

    /**
     * Returns User after changing ban status
     *
     * @param userId   unique user id
     * @return User with new ban status
     * @throws ServiceException if DaoException or ConnectionPoolException is thrown
     */
    T changeBanUserById(long userId) throws ServiceException;

    /**
     * Returns user balance
     *
     * @param userId unique user id
     * @return Balance of user
     * @throws ServiceException if DaoException or ConnectionPoolException is thrown
     */
    BigDecimal receiveUserBalance(long userId) throws ServiceException;

    /**
     * Returns list of User
     *
     * @param search the parameter to be compared for partial coincidence with user login or mail
     * @return list containing the Users with partial coincidence of user login or mail with the specified search
     * @throws ServiceException if DaoException or ConnectionPoolException is thrown
     */
    List<T> searchUserByLoginMail(String search) throws ServiceException;
}
