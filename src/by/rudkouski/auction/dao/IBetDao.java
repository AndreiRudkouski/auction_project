package by.rudkouski.auction.dao;

import by.rudkouski.auction.entity.AbstractEntity;
import by.rudkouski.auction.entity.impl.Bet;
import by.rudkouski.auction.dao.exception.DaoException;

import java.math.BigDecimal;
import java.util.List;

public interface IBetDao<T extends AbstractEntity> {

    void writeNewBet(Bet bet) throws DaoException;

    boolean checkReachBlitzPriceByLotId(long lotId, BigDecimal bet) throws DaoException;

    List<T> receiveBetListByLotId(long lotId, boolean createBetList) throws DaoException;

    List<T> receiveBetHistoryByUser(long userId) throws DaoException;
}
