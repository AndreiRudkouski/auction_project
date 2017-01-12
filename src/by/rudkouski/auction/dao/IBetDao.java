package by.rudkouski.auction.dao;

import by.rudkouski.auction.entity.impl.Bet;
import by.rudkouski.auction.dao.exception.DaoException;

import java.math.BigDecimal;
import java.util.List;

/**
 * This interface contains methods for working with entity "Bet"
 */
public interface IBetDao<T extends Bet> {

    /**
     * Add Bet into database
     *
     * @param bet new bet to add into database
     * @throws DaoException if SQLException is thrown
     */
    void writeNewBet(Bet bet) throws DaoException;

    /**
     * Checks reaching of blitz price for lot in database
     *
     * @param lotId unique lot id to search in database
     * @param bet   user bet to check
     * @return <tt>true</tt> if the specified bet is equal or exceeds blitz price for specified lot
     * @throws DaoException if SQLException is thrown
     */
    boolean checkReachBlitzPriceByLotId(long lotId, BigDecimal bet) throws DaoException;

    /**
     * Returns list of Bets for certain lot from database
     *
     * @param lotId         unique lot id to search in database
     * @param createBetList <tt>true</tt> for filling list with full creating of bets
     *                      <tt>false</tt> for filling list without full creating of bets (for example, when lot has blind type)
     * @return list containing the Bets for the specified lot
     * @throws DaoException if SQLException is thrown
     */
    List<T> receiveBetListByLotId(long lotId, boolean createBetList) throws DaoException;

    /**
     * Returns list of Bets for certain user from database
     *
     * @param userId unique user id to search in database
     * @return list containing the Bets for the specified user
     * @throws DaoException if SQLException is thrown
     */
    List<T> receiveBetHistoryByUser(long userId) throws DaoException;
}
