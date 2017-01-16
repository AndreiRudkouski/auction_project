package by.rudkouski.auction.dao;

import by.rudkouski.auction.dao.exception.DaoException;
import by.rudkouski.auction.entity.impl.User;

import java.math.BigDecimal;
import java.util.List;

/**
 * This interface contains methods for transferring data to database and for receiving data from it.
 * All methods are associated with entity "User"
 */
public interface IUserDao<T extends User> {

    /**
     * Returns User from database
     *
     * @param mail     user mail to search in database
     * @param password user password to search in database
     * @return User from database or null if the specified mail or password are not contained in database
     * @throws DaoException if SQLException is thrown
     */
    T logInUser(String mail, String password) throws DaoException;

    /**
     * Add User into database
     *
     * @param mail     user mail to add into database
     * @param password user password to add into database
     * @throws DaoException if SQLException is thrown
     */
    void registerUser(String mail, String password) throws DaoException;

    /**
     * Returns User from database
     *
     * @param userId unique user id to search in database
     * @return User from database or null if user whit the specified id is not contained in database
     * @throws DaoException if SQLException is thrown
     */
    T receiveUserById(long userId) throws DaoException;

    /**
     * Changes user ban status in database
     *
     * @param userId unique user id to search in database
     * @param ban    user ban status will be changed as the specified ban
     * @throws DaoException if SQLException is thrown
     */
    void changeBanUserById(long userId, boolean ban) throws DaoException;

    /**
     * Changes user login in database
     *
     * @param userId unique user id to search in database
     * @param login  new user login to be changed
     * @throws DaoException if SQLException is thrown
     */
    void changeUserLogin(long userId, String login) throws DaoException;

    /**
     * Changes user password in database
     *
     * @param userId   unique user id to search in database
     * @param password new user password to be changed
     * @throws DaoException if SQLException is thrown
     */
    void changeUserPassword(long userId, String password) throws DaoException;

    /**
     * Returns user balance from database
     *
     * @param userId unique user id to search in database
     * @return Balance of user from database
     * @throws DaoException if SQLException is thrown
     */
    BigDecimal receiveUserBalance(long userId) throws DaoException;

    /**
     * Checks user password in database
     *
     * @param userId   unique user id to search in database
     * @param password user password to check
     * @return <tt>true</tt> if the specified password is equal to password from database
     * @throws DaoException if SQLException is thrown
     */
    boolean checkUserPassword(long userId, String password) throws DaoException;

    /**
     * Checks the uniqueness of user mail in database
     *
     * @param mail user mail to check
     * @return <tt>-1</tt> if the specified mail is unique and is not contained in database otherwise user id
     * @throws DaoException if SQLException is thrown
     */
    long checkUniqueUserMail(String mail) throws DaoException;

    /**
     * Checks the uniqueness of user login in database
     *
     * @param login user login to check
     * @return <tt>true</tt> if the specified login is unique and is not contained in database
     * @throws DaoException if SQLException is thrown
     */
    boolean checkUniqueUserLogin(String login) throws DaoException;

    /**
     * Updates user balance in database
     *
     * @param userId     unique user id to search in database
     * @param newBalance user balance to update
     * @throws DaoException if SQLException is thrown
     */
    void updateUserBalanceById(long userId, BigDecimal newBalance) throws DaoException;

    /**
     * Returns User which has given the max previous bet to certain lot from database
     *
     * @param lotId unique lot id to search in database
     * @return User from database or null if bets have not been done on the specified lot
     * (user balance will be equal current balance plus the max previous bet)
     * @throws DaoException if SQLException is thrown
     */
    T receivePrevMaxBetUser(long lotId) throws DaoException;

    /**
     * Returns list of User from database
     *
     * @param search the parameter to be compared for partial coincidence with user login or mail in database
     * @return list containing the Users with partial coincidence of user login or mail with the specified search
     * @throws DaoException if SQLException is thrown
     */
    List<T> searchUserByLoginMail(String search) throws DaoException;
}
