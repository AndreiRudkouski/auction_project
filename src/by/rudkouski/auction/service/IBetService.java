package by.rudkouski.auction.service;

import by.rudkouski.auction.entity.impl.Bet;
import by.rudkouski.auction.service.exception.ServiceException;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * This interface contains methods for data conversion and sending data to other layer.
 * Provides performance of the main application logic.
 * All methods are associated with entity "Bet"
 */

public interface IBetService<T extends Bet> {

    /**
     * Receives bet data and realizes getting it to next layer to saving in database.
     * Checks and updates users balances (current and previous), also checks reaching of lot blitz price.
     *
     * @param userId  unique id of user which has given bet
     * @param lotId   unique id of lot for which bet
     * @param curBet  amount of bet
     * @param curTime time when user has given bet
     * @param balance current balance of user which has given bet
     * @return <tt>true</tt> if the bet is successful added
     * @throws ServiceException if SQLException, DaoException or ConnectionPoolException is thrown
     */
    boolean addBet(long userId, long lotId, BigDecimal curBet, Date curTime, BigDecimal balance) throws ServiceException;

    /**
     * Returns list of Bets for certain user
     *
     * @param userId unique user id
     * @return list containing the Bets for the specified user
     * @throws ServiceException if DaoException or ConnectionPoolException is thrown
     */
    List<List<T>> receiveBetHistoryByUser(long userId) throws ServiceException;
}
