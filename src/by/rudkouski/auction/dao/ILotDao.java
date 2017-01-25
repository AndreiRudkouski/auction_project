package by.rudkouski.auction.dao;

import by.rudkouski.auction.dao.exception.DaoException;
import by.rudkouski.auction.entity.impl.Lot;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * This interface contains methods for transferring data to database and for receiving data from it.
 * All methods are associated with entity "Lot"
 */
public interface ILotDao<T extends Lot> {

    /**
     * Returns list of checked Lots from database
     *
     * @return list containing Lots which are in database
     * @throws DaoException if SQLException is thrown
     */
    List<T> setupLot() throws DaoException;

    /**
     * Returns list of checked Lots which refer to a certain category from database
     *
     * @param categoryId unique category id to search in database
     * @param page       number of page to determining of position to search in database
     * @param isFinish   lot type (false - not finished lots, true - finished lots, null - all lots)
     * @return list containing Lots for the specified category
     * @throws DaoException if SQLException is thrown
     */
    List<T> searchLotByCategory(long categoryId, int page, Boolean isFinish) throws DaoException;

    /**
     * Returns list of checked Lots with partial coincidence of name from database
     *
     * @param search   the parameter to be compared for partial coincidence with lot name in database
     * @param page     number of page to determining of position to search in database
     * @param isFinish lot type (false - not finished lots, true - finished lots, null - all lots)
     * @return list containing Lots with partial coincidence of name with the specified search
     * @throws DaoException if SQLException is thrown
     */
    List<T> searchLotByName(String search, int page, Boolean isFinish) throws DaoException;

    /**
     * Returns finished Lot from database
     *
     * @param lotId unique lot id to search in database
     * @return Lot with finished time
     * @throws DaoException if SQLException is thrown
     */
    T searchFinishedLotById(long lotId) throws DaoException;

    /**
     * Returns unfinished Lot from database
     *
     * @param lotId unique lot id to search in database
     * @return Lot with unfinished time
     * @throws DaoException if SQLException is thrown
     */
    T searchUnfinishedLotById(long lotId) throws DaoException;

    /**
     * Checks finished Lot and marks it if lot time is over in database
     *
     * @param lotId unique lot id to search in database
     * @param time  current user time to check lot in database
     * @return <tt>true</tt> if the specified lot is finished
     * @throws DaoException if SQLException is thrown
     */
    boolean checkAndMarkFinishLot(long lotId, Date time) throws DaoException;

    /**
     * Changes mark of finished lot in database
     *
     * @param lotId unique lot id to search in database
     * @throws DaoException if SQLException is thrown
     */
    void markFinishLot(long lotId) throws DaoException;

    /**
     * Returns list of finished Lots for a certain user from database
     *
     * @param userId unique user id to search in database
     * @return list containing finished Lots for the specified user
     * @throws DaoException if SQLException is thrown
     */
    List<T> receiveFinishedLotHistoryByUser(long userId) throws DaoException;

    /**
     * Returns list of unfinished Lots for a certain user from database
     *
     * @param userId unique user id to search in database
     * @return list containing unfinished Lots for the specified user
     * @throws DaoException if SQLException is thrown
     */
    List<T> receiveUnfinishedLotHistoryByUser(long userId) throws DaoException;

    /**
     * Returns list of unchecked by admin Lots for a certain user from database
     *
     * @param userId unique user id to search in database
     * @return list containing unchecked by admin Lots for the specified user
     * @throws DaoException if SQLException is thrown
     */
    List<T> receiveUncheckedLotHistoryByUser(long userId) throws DaoException;

    /**
     * Returns list of removed by admin Lots for a certain user from database
     *
     * @param userId unique user id to search in database
     * @return list containing removed by admin Lots for the specified user
     * @throws DaoException if SQLException is thrown
     */
    List<T> receiveRemovedLotHistoryByUser(long userId) throws DaoException;

    /**
     * Returns min bet for a certain lot from database
     *
     * @param lotId unique lot id to search in database
     * @return min bet for the specified lot
     * @throws DaoException if SQLException is thrown
     */
    BigDecimal determineLotMinBet(long lotId) throws DaoException;

    /**
     * Add Lot into database
     *
     * @param lot new lot to add into database
     * @return id for added lot
     * @throws DaoException if SQLException is thrown
     */
    long addLot(T lot) throws DaoException;

    /**
     * Add photo for a certain lot into database
     *
     * @param lotId unique lot id to search in database
     * @param photo relative path to lot photo
     * @throws DaoException if SQLException is thrown
     */
    void addPhotoByLotId(long lotId, String photo) throws DaoException;

    /**
     * Changes lot data for a certain lot into database
     *
     * @param lot new lot data to change in database
     * @throws DaoException if SQLException is thrown
     */
    void editLot(T lot) throws DaoException;

    /**
     * Changes mark of checked by admin for lot in database
     *
     * @param lotId unique lot id to search in database
     * @throws DaoException if SQLException is thrown
     */
    void checkLot(long lotId) throws DaoException;

    /**
     * Changes mark of removed by admin for lot in database
     *
     * @param lotId unique lot id to search in database
     * @throws DaoException if SQLException is thrown
     */
    void removeLot(long lotId) throws DaoException;
}
