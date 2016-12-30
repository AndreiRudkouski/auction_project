package by.rudkouski.auction.dao;

import by.rudkouski.auction.bean.AbstractEntity;
import by.rudkouski.auction.pool.ProxyConnection;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public interface ILotDao<T extends AbstractEntity> {
    List<T> setupLot();

    List<T> searchLotByCategory(long categoryId, int page);

    List<T> searchLotByNamePart(String search, int page);

    T searchFinishedLotById(long lotId);

    T searchNotFinishedLotById(long lotId);

    boolean checkAndMarkFinishLot(long lotId, Date time);

    void markFinishLot(long lotId);

    List<T> receiveLotHistoryByUser(long userId);

    BigDecimal determineLotMinBet(long lotId);

    long addLot(T lot);

    void addPhotoByLotId (long lotId, String photo);
}
