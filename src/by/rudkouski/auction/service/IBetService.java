package by.rudkouski.auction.service;

import by.rudkouski.auction.bean.AbstractEntity;

import java.math.BigDecimal;
import java.util.List;

public interface IBetService<T extends AbstractEntity> {

    boolean addBet(T bet, BigDecimal balance);

    List<List<T>> receiveBetHistoryByUser(long userId);
}
