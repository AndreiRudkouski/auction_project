package by.rudkouski.auction.service.impl;

import by.rudkouski.auction.entity.impl.Bet;
import by.rudkouski.auction.entity.impl.User;
import by.rudkouski.auction.dao.exception.DaoException;
import by.rudkouski.auction.dao.impl.BetDao;
import by.rudkouski.auction.dao.impl.LotDao;
import by.rudkouski.auction.dao.impl.UserDao;
import by.rudkouski.auction.pool.ConnectionPool;
import by.rudkouski.auction.pool.ConnectionPoolException;
import by.rudkouski.auction.pool.ProxyConnection;
import by.rudkouski.auction.service.IBetService;
import by.rudkouski.auction.service.exception.ServiceException;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BetService implements IBetService<Bet> {
    private static final ConnectionPool POOL = ConnectionPool.getInstance();

    @Override
    public boolean addBet(long userId, long lotId, BigDecimal curBet, Date curTime, BigDecimal balance) throws ServiceException {
        ProxyConnection con = null;
        try {
            con = POOL.takeConnection();
            BetDao betDao = new BetDao(con);
            LotDao lotDao = new LotDao(con);
            UserDao userDao = new UserDao(con);
            boolean finishLot = lotDao.checkAndMarkFinishLot(lotId, curTime);
            if (!finishLot) {
                con.setAutoCommit(false);
                User user = userDao.receivePrevMaxBetUser(lotId);
                if (user.getId() == userId) {
                    userDao.updateUserBalanceById(userId, user.getBalance().subtract(curBet));
                } else {
                    userDao.updateUserBalanceById(user.getId(), user.getBalance());
                    userDao.updateUserBalanceById(userId, balance.subtract(curBet));
                }
                betDao.writeNewBet(userId, lotId, curBet, curTime);
                boolean reachPrice = betDao.checkReachBlitzPriceByLotId(lotId, curBet);
                if (reachPrice) {
                    lotDao.markFinishLot(lotId);
                }
                con.commit();
            } else {
                return false;
            }
        } catch (SQLException | DaoException | ConnectionPoolException e) {
            try {
                con.rollback();
            } catch (SQLException e1) {
                throw new ServiceException("SQLException during rollback", e1);
            }
            throw new ServiceException(e);
        } finally {
            try {
                POOL.returnConnection(con);
            } catch (ConnectionPoolException e) {
                throw new ServiceException(e);
            }
        }
        return true;
    }

    @Override
    public List<List<Bet>> receiveBetHistoryByUser(long userId) throws ServiceException {
        ProxyConnection con = null;
        List<Bet> betList;
        List<List<Bet>> betResult = null;
        try {
            con = POOL.takeConnection();
            BetDao betDao = new BetDao(con);
            betList = betDao.receiveBetHistoryByUser(userId);
        } catch (DaoException | ConnectionPoolException e) {
            throw new ServiceException(e);
        } finally {
            try {
                POOL.returnConnection(con);
            } catch (ConnectionPoolException e) {
                throw new ServiceException(e);
            }
        }
        if (betList != null) {
            List<Bet> betListWin = null;
            List<Bet> betListDone = null;
            for (Bet bet : betList) {
                if (bet.isWin()) {
                    if (betListWin == null) {
                        betListWin = new ArrayList<>();
                    }
                    betListWin.add(bet);
                } else {
                    if (betListDone == null) {
                        betListDone = new ArrayList<>();
                    }
                    betListDone.add(bet);
                }
            }
            betResult = new ArrayList<>();
            betResult.add(betListWin);
            betResult.add(betListDone);
        }
        return betResult;
    }
}
