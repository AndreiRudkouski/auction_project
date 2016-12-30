package by.rudkouski.auction.service;

import by.rudkouski.auction.bean.AbstractEntity;

import java.math.BigDecimal;
import java.util.List;

public interface ILotService<T extends AbstractEntity> {
    List<T> setupLot();

    List<T> searchLotByCategory(long categoryId, int page);

    List<T> searchLotByNamePart(String search, int page);

    T searchLotById(long lotId);

    List<List<T>> receiveLotHistoryByUser(long userId);

    BigDecimal determineLotMinBet(long lotId);

    boolean addLot(T lot, String appPath);
}
