package by.rudkouski.auction.service.impl;

import by.rudkouski.auction.bean.impl.Bet;
import by.rudkouski.auction.bean.impl.User;
import by.rudkouski.auction.dao.impl.BetDao;
import by.rudkouski.auction.dao.impl.LotDao;
import by.rudkouski.auction.dao.impl.UserDao;
import by.rudkouski.auction.pool.ConnectionPool;
import by.rudkouski.auction.pool.ProxyConnection;
import by.rudkouski.auction.service.IBetService;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BetService implements IBetService<Bet> {
    private static final ConnectionPool POOL = ConnectionPool.getInstance();

    @Override
    public boolean addBet(Bet bet, BigDecimal balance) {
        ProxyConnection con = null;
        try {
            con = POOL.takeConnection();
            BetDao betDao = new BetDao(con);
            LotDao lotDao = new LotDao(con);
            UserDao userDao = new UserDao(con);
            boolean finishLot = lotDao.checkAndMarkFinishLot(bet.getLot().getId(), bet.getTime());
            if (!finishLot) {
                con.setAutoCommit(false);
                User user = userDao.receivePrevMaxBetUser(bet.getLot().getId());
                if (user.getId() == bet.getUser().getId()) {
                    userDao.updateUserBalanceById(bet.getUser().getId(), user.getBalance().subtract(bet.getAmount()));
                } else {
                    userDao.updateUserBalanceById(user.getId(), user.getBalance());
                    userDao.updateUserBalanceById(bet.getUser().getId(), balance.subtract(bet.getAmount()));
                }
                betDao.writeNewBet(bet);
                boolean reachPrice = betDao.checkReachBlitzPriceByLotId(bet.getLot().getId(), bet.getAmount());
                if (reachPrice) {
                    lotDao.markFinishLot(bet.getLot().getId());
                }
                con.commit();
            }
        } catch (SQLException e) {
            //throw new ServiceException("SQLException", e);
            try {
                con.rollback();
            } catch (SQLException e1) {
                //throw new DaoException("SQLException", e);
            }
        } finally {
            POOL.returnConnection(con);
        }
        return true;
    }

    @Override
    public List<List<Bet>> receiveBetHistoryByUser(long userId) {
        ProxyConnection con = null;
        List<Bet> betList;
        List<List<Bet>> betResult = null;
        try {
            con = POOL.takeConnection();
            BetDao betDao = new BetDao(con);
            betList = betDao.receiveBetHistoryByUser(userId);
        } finally {
            POOL.returnConnection(con);
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
