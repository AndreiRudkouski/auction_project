package by.rudkouski.auction.service;

import by.rudkouski.auction.entity.impl.Bet;
import by.rudkouski.auction.service.exception.ServiceException;

import java.math.BigDecimal;
import java.util.List;

public interface IBetService<T extends Bet> {

    boolean addBet(T bet, BigDecimal balance) throws ServiceException;

    List<List<T>> receiveBetHistoryByUser(long userId) throws ServiceException;
}
