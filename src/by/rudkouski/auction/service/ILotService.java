package by.rudkouski.auction.service;

import by.rudkouski.auction.entity.impl.Lot;
import by.rudkouski.auction.service.exception.ServiceException;

import java.math.BigDecimal;
import java.util.List;

public interface ILotService<T extends Lot> {

    List<T> setupLot() throws ServiceException;

    List<T> searchLotByCategory(long categoryId, int page) throws ServiceException;

    List<T> searchLotByName(String search, int page) throws ServiceException;

    T searchLotById(long lotId) throws ServiceException;

    List<List<T>> receiveLotHistoryByUser(long userId) throws ServiceException;

    List<T> receiveFinishedLotHistoryByUser(long userId) throws ServiceException;

    List<T> receiveUnfinishedLotHistoryByUser(long userId) throws ServiceException;

    List<T> receiveUncheckedLotHistoryByUser(long userId) throws ServiceException;

    BigDecimal determineLotMinBet(long lotId) throws ServiceException;

    boolean addLot(T lot, String appPath) throws ServiceException;

    boolean editLot(T lot, String appPath) throws ServiceException;

    void checkLot(long lotId) throws ServiceException;
}
