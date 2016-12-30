package by.rudkouski.auction.dao;

import by.rudkouski.auction.bean.AbstractEntity;
import by.rudkouski.auction.bean.impl.Bet;
import by.rudkouski.auction.bean.impl.User;
import by.rudkouski.auction.pool.ProxyConnection;

import java.math.BigDecimal;
import java.util.List;

public interface IBetDao<T extends AbstractEntity> {

    void writeNewBet(Bet bet);

    boolean checkReachBlitzPriceByLotId(long lotId, BigDecimal bet);

    List<T> receiveBetListByLotId(long lotId, boolean createBetList);

    List<T> receiveBetHistoryByUser(long userId);
}
