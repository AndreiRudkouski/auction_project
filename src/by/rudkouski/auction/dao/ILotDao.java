package by.rudkouski.auction.dao;

import by.rudkouski.auction.entity.AbstractEntity;
import by.rudkouski.auction.dao.exception.DaoException;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public interface ILotDao<T extends AbstractEntity> {
    List<T> setupLot() throws DaoException;

    List<T> searchLotByCategory(long categoryId, int page) throws DaoException;

    List<T> searchLotByName(String search, int page) throws DaoException;

    T searchFinishedLotById(long lotId) throws DaoException;

    T searchUnfinishedLotById(long lotId) throws DaoException;

    boolean checkAndMarkFinishLot(long lotId, Date time) throws DaoException;

    void markFinishLot(long lotId) throws DaoException;

    List<T> receiveFinishedLotHistoryByUser(long userId) throws DaoException;

    List<T> receiveUnfinishedLotHistoryByUser(long userId) throws DaoException;

    List<T> receiveUncheckedLotHistoryByUser(long userId) throws DaoException;

    BigDecimal determineLotMinBet(long lotId) throws DaoException;

    long addLot(T lot) throws DaoException;

    void addPhotoByLotId(long lotId, String photo) throws DaoException;

    void editLot(T lot) throws DaoException;

    void checkLot(long lotId) throws DaoException;
}
