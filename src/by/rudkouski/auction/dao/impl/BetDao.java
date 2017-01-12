package by.rudkouski.auction.dao.impl;

import by.rudkouski.auction.entity.impl.Bet;
import by.rudkouski.auction.entity.impl.Lot;
import by.rudkouski.auction.entity.impl.User;
import by.rudkouski.auction.dao.IBetDao;
import by.rudkouski.auction.dao.exception.DaoException;
import by.rudkouski.auction.pool.ProxyConnection;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static by.rudkouski.auction.constant.ConstantName.FORMAT_DATE;

public class BetDao implements IBetDao<Bet> {
    private ProxyConnection con;

    private static final String SQL_ADD_BET = "INSERT INTO bet (bet, timeBet, lot_id, user_id) VALUES (?, ?, ?, ?)";
    private static final String SQL_BLITZ_PRICE = "SELECT priceBlitz FROM lot WHERE lot_id = ?";
    private static final String SQL_LOT_BET = "SELECT bet_id, bet, timeBet, login, email FROM bet, user WHERE bet.user_id = user.user_id AND bet.lot_id = ? ORDER BY bet DESC";
    private static final String SQL_BET_HISTORY = "SELECT bet.bet_id, timeBet, bet, bet.lot_id, lot.name, lots FROM bet " +
            "JOIN lot ON bet.lot_id = lot.lot_id " +
            "LEFT JOIN (SELECT bet_id, lot_id AS lots FROM bet, (SELECT lot.lot_id AS lotId, MAX(bet) AS max FROM lot " +
            "LEFT JOIN bet ON lot.lot_id = bet.lot_id WHERE lot.finish = 1 GROUP BY lot.lot_id) AS maxBet " +
            "WHERE bet.lot_id = maxBet.lotId AND bet.bet = maxBet.max) AS bets ON  bets.bet_id = bet.bet_id " +
            "WHERE bet.user_id = ? ORDER BY timeBet DESC;";

    public BetDao(ProxyConnection con) {
        this.con = con;
    }

    @Override
    public List<Bet> receiveBetListByLotId(long lotId, boolean createBetList) throws DaoException {
        List<Bet> betList;
        try (PreparedStatement prSt = con.prepareStatement(SQL_LOT_BET)) {
            prSt.setLong(1, lotId);
            ResultSet res = prSt.executeQuery();
            betList = new ArrayList<>();
            while (res.next()) {
                Bet bet = new Bet();
                if (createBetList) {
                    bet.setId(res.getLong(1));
                    bet.setAmount(new BigDecimal(res.getString(2)));
                    bet.setTime(res.getTimestamp(3));
                    User user = new User();
                    user.setLogin(res.getString(4));
                    user.setMail(res.getString(5));
                    bet.setUser(user);
                }
                betList.add(bet);
            }
        } catch (SQLException e) {
            throw new DaoException("SQLException", e);
        }
        return betList;
    }

    @Override
    public void writeNewBet(long userId, long lotId, BigDecimal curBet, Date curTime) throws DaoException {
        try (PreparedStatement prSt = con.prepareStatement(SQL_ADD_BET)) {
            prSt.setBigDecimal(1, curBet);
            String timeBet = new SimpleDateFormat(FORMAT_DATE).format(curTime);
            prSt.setString(2, timeBet);
            prSt.setLong(3, lotId);
            prSt.setLong(4, userId);
            prSt.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("SQLException", e);
        }
    }

    @Override
    public boolean checkReachBlitzPriceByLotId(long lotId, BigDecimal bet) throws DaoException {
        try (PreparedStatement prSt = con.prepareStatement(SQL_BLITZ_PRICE)) {
            prSt.setLong(1, lotId);
            ResultSet res = prSt.executeQuery();
            BigDecimal priceBlitz = null;
            while (res.next()) {
                priceBlitz = res.getBigDecimal(1);
            }
            if (priceBlitz == null || bet.compareTo(priceBlitz) < 0) {
                return false;
            }
        } catch (SQLException e) {
            throw new DaoException("SQLException", e);
        }
        return true;
    }

    @Override
    public List<Bet> receiveBetHistoryByUser(long userId) throws DaoException {
        List<Bet> betList;
        try (PreparedStatement prSt = con.prepareStatement(SQL_BET_HISTORY)) {
            prSt.setLong(1, userId);
            ResultSet res = prSt.executeQuery();
            betList = new ArrayList<>();
            while (res.next()) {
                Bet bet = new Bet();
                bet.setId(res.getLong(1));
                bet.setTime(res.getTimestamp(2));
                bet.setAmount(res.getBigDecimal(3));
                Lot lot = new Lot();
                lot.setId(res.getLong(4));
                lot.setName(res.getString(5));
                bet.setLot(lot);
                if (res.getString(6) != null) {
                    bet.setWin(true);
                }
                betList.add(bet);
            }
        } catch (SQLException e) {
            throw new DaoException("SQLException", e);
        }
        return betList;
    }
}
