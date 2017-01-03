package by.rudkouski.auction.dao;

import by.rudkouski.auction.bean.AbstractEntity;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public interface ILotDao<T extends AbstractEntity> {
    List<T> setupLot();

    List<T> searchLotByCategory(long categoryId, int page);

    List<T> searchLotByName(String search, int page);

    T searchFinishedLotById(long lotId);

    T searchUnfinishedLotById(long lotId);

    boolean checkAndMarkFinishLot(long lotId, Date time);

    void markFinishLot(long lotId);

    List<T> receiveFinishedLotHistoryByUser(long userId);

    List<T> receiveUnfinishedLotHistoryByUser(long userId);

    List<T> receiveUncheckedLotHistoryByUser(long userId);

    BigDecimal determineLotMinBet(long lotId);

    long addLot(T lot);

    void addPhotoByLotId(long lotId, String photo);

    void editLot(T lot);

    void checkLot(long lotId);
}
