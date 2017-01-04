package by.rudkouski.auction.dao.impl;

import by.rudkouski.auction.bean.impl.*;
import by.rudkouski.auction.dao.ILotDao;
import by.rudkouski.auction.pool.ProxyConnection;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class LotDao implements ILotDao<Lot> {
    private ProxyConnection con;
    private static final int COUNT_VIEW = 3;
    private static final long BLIND_AUCTION_TYPE_ID = 3;
    private static final BigDecimal TEN_PERCENT = new BigDecimal(10 / 100);
    private static final String FORMAT_DATE = "yyyy-MM-dd HH:mm:ss";
    private static final String ANY_USER = "%";

    private static final String SQL_SETUP_LOT = "SELECT lot.lot_id, name, url, MAX(bet), priceStart, lot.type_id FROM lot " +
            "JOIN photo ON lot.lot_id = photo.lot_id " +
            "LEFT JOIN bet ON lot.lot_id = bet.lot_id WHERE lot.check = 1 GROUP BY lot.lot_id ORDER BY lot.timeStart DESC LIMIT " + COUNT_VIEW;
    private static final String SQL_SEARCH_LOT_CATEGORY = "SELECT lot.lot_id, name, url, MAX(bet), priceStart, lot.type_id, lot.category_id FROM lot " +
            "JOIN photo ON lot.lot_id = photo.lot_id " +
            "LEFT JOIN bet ON lot.lot_id = bet.lot_id WHERE lot.category_id = ? AND lot.check = 1 GROUP BY lot.lot_id ORDER BY lot.timeStart DESC LIMIT ?, ?";
    private static final String SQL_SEARCH_LOT_FINISHED = "SELECT lot.lot_id, name, url, MAX(bet), priceStart, lot.type_id, description, lot.category_id, lot.user_id, lot.check FROM lot " +
            "JOIN photo ON lot.lot_id = photo.lot_id " +
            "LEFT JOIN bet ON lot.lot_id = bet.lot_id WHERE lot.lot_id = ? GROUP BY lot.lot_id";
    private static final String SQL_SEARCH_LOT_UNFINISHED = "SELECT lot.lot_id, name, url, MAX(bet), priceStart, lot.type_id, description, lot.category_id, " +
            "type.type, lot.term_id, term, ADDDATE(timeStart, term), priceBlitz, step, lot.condition_id, cond.condition, lot.user_id, lot.check, timeStart FROM lot " +
            "JOIN term ON lot.term_id = term.term_id " +
            "JOIN photo ON lot.lot_id = photo.lot_id " +
            "JOIN type ON lot.type_id = type.type_id " +
            "JOIN cond ON lot.condition_id = cond.condition_id " +
            "LEFT JOIN bet ON lot.lot_id = bet.lot_id WHERE lot.lot_id = ? GROUP BY lot.lot_id";
    private static final String SQL_SEARCH_LOT_NAME = "SELECT lot.lot_id, name, url, MAX(bet), priceStart, lot.type_id, lot.category_id FROM lot " +
            "JOIN photo ON lot.lot_id = photo.lot_id " +
            "LEFT JOIN bet ON lot.lot_id = bet.lot_id WHERE name LIKE ? AND lot.check = 1 GROUP BY lot.lot_id ORDER BY lot.timeStart DESC LIMIT ?, ?";
    private static final String SQL_MIN_BET = "SELECT MAX(bet), priceStart, step, lot.type_id FROM lot " +
            "LEFT JOIN bet ON lot.lot_id = bet.lot_id WHERE lot.lot_id = ? GROUP BY lot.lot_id";
    private static final String SQL_CHECK_FINISH_LOT = "SELECT finish, ADDDATE(timeStart, term), lot.check FROM lot " +
            "JOIN term ON lot.term_id = term.term_id WHERE lot.lot_id = ?";
    private static final String SQL_MARK_FINISH_LOT = "UPDATE lot SET finish = 1 WHERE lot_id = ?";
    private static final String SQL_FINISHED_LOT_HISTORY = "SELECT lot.lot_id, name, timeStart, lot.check, finish FROM lot " +
            "WHERE user_id LIKE ? AND finish = 1 AND lot.check = 1 ORDER BY timeStart DESC";
    private static final String SQL_UNFINISHED_LOT_HISTORY = "SELECT lot.lot_id, name, timeStart, lot.check, finish FROM lot " +
            "WHERE user_id LIKE ? AND finish = 0 AND lot.check = 1 ORDER BY timeStart DESC";
    private static final String SQL_UNCHECKED_LOT_HISTORY = "SELECT lot.lot_id, name, timeStart, lot.check, finish FROM lot " +
            "WHERE user_id LIKE ? AND lot.check = 0 ORDER BY timeStart DESC";
    private static final String SQL_ADD_LOT = "INSERT INTO lot (name, description, priceStart, priceBlitz, step, timeStart, " +
            "category_id, user_id, term_id, type_id, condition_id) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String SQL_ADD_PHOTO = "INSERT INTO photo (lot_id, url) VALUES (?, ?)";
    private static final String SQL_EDIT_LOT = "UPDATE lot SET name = ?, description = ?, priceStart = ?, priceBlitz = ?, step = ?, " +
            "timeStart = ?, category_id = ?, user_id = ?, term_id = ?, type_id = ?, condition_id = ? " +
            "WHERE lot_id = ?";
    private static final String SQL_CHECK_LOT = "UPDATE lot SET lot.check = 1, timeStart = ? WHERE lot_id = ?";


    public LotDao(ProxyConnection con) {
        this.con = con;
    }

    @Override
    public List<Lot> setupLot() {
        List<Lot> lotList = null;
        try (Statement st = con.createStatement()) {
            ResultSet res = st.executeQuery(SQL_SETUP_LOT);
            lotList = convertResult(res);
        } catch (SQLException e) {
            //throw new DaoException("SQLException", e);
        }
        return lotList;
    }

    @Override
    public List<Lot> searchLotByCategory(long categoryId, int page) {
        List<Lot> lotList = null;
        try (PreparedStatement prSt = con.prepareStatement(SQL_SEARCH_LOT_CATEGORY)) {
            prSt.setLong(1, categoryId);
            int lineStart = page * COUNT_VIEW;
            int lineQty = lineStart + COUNT_VIEW + 1;
            prSt.setInt(2, lineStart);
            prSt.setInt(3, lineQty);
            ResultSet res = prSt.executeQuery();
            lotList = convertResult(res);
        } catch (SQLException e) {
            //throw new DaoException("SQLException", e);
        }
        return lotList;
    }

    @Override
    public List<Lot> searchLotByName(String search, int page) {
        List<Lot> lotList = null;
        try (PreparedStatement prSt = con.prepareStatement(SQL_SEARCH_LOT_NAME)) {
            prSt.setString(1, "%" + search + "%");
            int lineStart = page * COUNT_VIEW;
            int lineQty = lineStart + COUNT_VIEW + 1;
            prSt.setInt(2, lineStart);
            prSt.setInt(3, lineQty);
            ResultSet res = prSt.executeQuery();
            lotList = convertResult(res);
        } catch (SQLException e) {
            //throw new DaoException("SQLException", e);
        }
        return lotList;
    }

    @Override
    public Lot searchFinishedLotById(long lotId) {
        Lot lot = null;
        try (PreparedStatement prSt = con.prepareStatement(SQL_SEARCH_LOT_FINISHED)) {
            prSt.setLong(1, lotId);
            ResultSet res = prSt.executeQuery();
            while (res.next()) {
                lot = createLot(res);
                Type type = new Type();
                type.setId(res.getLong(6));
                lot.setType(type);
                lot.setDescription(res.getString(7));
                lot.setCategoryId(res.getLong(8));
                lot.setFinish(true);
                if (res.getString(4) != null) {
                    lot.setPrice(new BigDecimal(res.getString(4)));
                }
                lot.setFinish(true);
                User user = new User();
                user.setId(res.getLong(9));
                lot.setUser(user);
                lot.setCheck(res.getBoolean(10));
            }
        } catch (SQLException e) {
            //throw new DaoException("SQLException", e);
        }
        return lot;
    }

    @Override
    public Lot searchUnfinishedLotById(long lotId) {
        Lot lot = null;
        try (PreparedStatement prSt = con.prepareStatement(SQL_SEARCH_LOT_UNFINISHED)) {
            prSt.setLong(1, lotId);
            ResultSet res = prSt.executeQuery();
            while (res.next()) {
                lot = createLot(res);
                lot.setDescription(res.getString(7));
                lot.setCategoryId(res.getLong(8));
                Type type = new Type();
                type.setId(res.getLong(6));
                type.setName(res.getString(9));
                lot.setType(type);
                Term term = new Term();
                term.setId(res.getLong(10));
                term.setName(res.getString(11));
                lot.setTerm(term);
                lot.setTimeEnd(res.getTimestamp(12));
                if (res.getString(13) != null) {
                    lot.setPriceBlitz(new BigDecimal(res.getString(13)));
                }
                if (res.getString(14) != null) {
                    lot.setStepPrice(new BigDecimal(res.getString(14)));
                }
                lot.setMinBet(determineLotMinBet(lotId));
                Condition cond = new Condition();
                cond.setId(res.getLong(15));
                cond.setName(res.getString(16));
                lot.setCondition(cond);
                User user = new User();
                user.setId(res.getLong(17));
                lot.setUser(user);
                lot.setCheck(res.getBoolean(18));
                lot.setTimeStart(res.getTimestamp(19));
            }
        } catch (SQLException e) {
            //throw new DaoException("SQLException", e);
        }
        return lot;
    }

    private List<Lot> convertResult(ResultSet res) throws SQLException {
        List<Lot> lotList = new ArrayList<>();
        while (res.next()) {
            Lot lot = createLot(res);
            lotList.add(lot);
        }
        return lotList;
    }

    private Lot createLot(ResultSet res) throws SQLException {
        Lot lot = new Lot();
        lot.setId(res.getLong(1));
        lot.setName(res.getString(2));
        lot.setPhoto(res.getString(3));
        if (res.getString(4) != null && res.getLong(6) != BLIND_AUCTION_TYPE_ID) {
            lot.setPrice(new BigDecimal(res.getString(4)));
        } else {
            lot.setPrice(new BigDecimal(res.getString(5)));
        }
        return lot;
    }

    @Override
    public boolean checkAndMarkFinishLot(long lotId, Date time) {
        try (PreparedStatement prSt = con.prepareStatement(SQL_CHECK_FINISH_LOT)) {
            prSt.setLong(1, lotId);
            ResultSet res = prSt.executeQuery();
            while (res.next()) {
                if (!res.getBoolean(3)) {
                    return false;
                }
                if (!res.getBoolean(1)) {
                    if (time == null) {
                        time = new Date(System.currentTimeMillis());
                    }
                    if (time.compareTo(res.getTimestamp(2)) >= 0) {
                        markFinishLot(lotId);
                    } else {
                        return false;
                    }
                }
            }
        } catch (SQLException e) {
            //throw new DaoException("SQLException", e);
        }
        return true;
    }

    @Override
    public void markFinishLot(long lotId) {
        try (PreparedStatement prSt = con.prepareStatement(SQL_MARK_FINISH_LOT)) {
            prSt.setLong(1, lotId);
            prSt.executeUpdate();
        } catch (SQLException e) {
            //throw new DaoException("SQLException", e);
        }
    }

    @Override
    public List<Lot> receiveFinishedLotHistoryByUser(long userId) {
        List<Lot> lotList = null;
        try (PreparedStatement prSt = con.prepareStatement(SQL_FINISHED_LOT_HISTORY)) {
            lotList = receiveLotList(userId, prSt);
        } catch (SQLException e) {
            //throw new DaoException("SQLException", e);
        }
        return lotList;
    }

    @Override
    public List<Lot> receiveUnfinishedLotHistoryByUser(long userId) {
        List<Lot> lotList = null;
        try (PreparedStatement prSt = con.prepareStatement(SQL_UNFINISHED_LOT_HISTORY)) {
            lotList = receiveLotList(userId, prSt);
        } catch (SQLException e) {
            //throw new DaoException("SQLException", e);
        }
        return lotList;
    }

    @Override
    public List<Lot> receiveUncheckedLotHistoryByUser(long userId) {
        List<Lot> lotList = null;
        try (PreparedStatement prSt = con.prepareStatement(SQL_UNCHECKED_LOT_HISTORY)) {
            lotList = receiveLotList(userId, prSt);
        } catch (SQLException e) {
            //throw new DaoException("SQLException", e);
        }
        return lotList;
    }

    private List<Lot> receiveLotList(long userId, PreparedStatement prSt) throws SQLException {
        if (userId != 0) {
            prSt.setLong(1, userId);
        } else {
            prSt.setString(1, ANY_USER);
        }
        ResultSet res = prSt.executeQuery();
        List<Lot> lotList = new ArrayList<>();
        while (res.next()) {
            Lot lot = new Lot();
            lot.setId(res.getLong(1));
            lot.setName(res.getString(2));
            lot.setTimeStart(res.getTimestamp(3));
            lot.setCheck(res.getBoolean(4));
            lot.setFinish(res.getBoolean(5));
            lotList.add(lot);
        }
        return lotList;
    }

    @Override
    public BigDecimal determineLotMinBet(long lotId) {
        BigDecimal minBet = null;
        try (PreparedStatement prSt = con.prepareStatement(SQL_MIN_BET)) {
            prSt.setLong(1, lotId);
            ResultSet res = prSt.executeQuery();
            while (res.next()) {
                if (res.getLong(4) == BLIND_AUCTION_TYPE_ID) {
                    minBet = new BigDecimal(res.getString(2));
                } else {
                    if (res.getString(1) != null) {
                        minBet = new BigDecimal(res.getString(1));
                        if (res.getString(3) != null) {
                            BigDecimal step = new BigDecimal(res.getString(3));
                            minBet = minBet.add(step);
                        } else {
                            minBet = minBet.add(res.getBigDecimal(2).multiply(TEN_PERCENT));
                        }
                    } else {
                        minBet = new BigDecimal(res.getString(2));
                    }
                }
            }
        } catch (SQLException e) {
            //throw new DaoException("SQLException", e);
        }
        return minBet;
    }

    @Override
    public long addLot(Lot lot) {
        long newLotId = 0;
        try (PreparedStatement prSt = con.prepareStatement(SQL_ADD_LOT, Statement.RETURN_GENERATED_KEYS)) {
            fillStatementLot(prSt, lot);
            prSt.executeUpdate();
            ResultSet res = prSt.getGeneratedKeys();
            while (res.next()) {
                newLotId = res.getLong(1);
            }
        } catch (SQLException e) {
            //throw new DaoException("SQLException", e);
        }
        return newLotId;
    }

    @Override
    public void addPhotoByLotId(long lotId, String photo) {
        try (PreparedStatement prSt = con.prepareStatement(SQL_ADD_PHOTO)) {
            prSt.setLong(1, lotId);
            prSt.setString(2, photo);
            prSt.executeUpdate();
        } catch (SQLException e) {
            //throw new DaoException("SQLException", e);
        }
    }

    @Override
    public void editLot(Lot lot) {
        try (PreparedStatement prSt = con.prepareStatement(SQL_EDIT_LOT)) {
            fillStatementLot(prSt, lot);
            prSt.setLong(12, lot.getId());
            prSt.executeUpdate();
        } catch (SQLException e) {
            //throw new DaoException("SQLException", e);
        }
    }

    private void fillStatementLot(PreparedStatement prSt, Lot lot) throws SQLException {
        prSt.setString(1, lot.getName());
        prSt.setString(2, lot.getDescription());
        prSt.setBigDecimal(3, lot.getPrice());
        prSt.setBigDecimal(4, lot.getPriceBlitz());
        prSt.setBigDecimal(5, lot.getStepPrice());
        String timeLot = new SimpleDateFormat(FORMAT_DATE).format(lot.getTimeStart());
        prSt.setString(6, timeLot);
        prSt.setLong(7, lot.getCategoryId());
        prSt.setLong(8, lot.getUser().getId());
        prSt.setLong(9, lot.getTerm().getId());
        prSt.setLong(10, lot.getType().getId());
        prSt.setLong(11, lot.getCondition().getId());
    }

    @Override
    public void checkLot(long lotId) {
        try (PreparedStatement prSt = con.prepareStatement(SQL_CHECK_LOT)) {
            String timeLot = new SimpleDateFormat(FORMAT_DATE).format(new Date(System.currentTimeMillis()));
            prSt.setString(1, timeLot);
            prSt.setLong(2, lotId);
            prSt.executeUpdate();
        } catch (SQLException e) {
            //throw new DaoException("SQLException", e);
        }
    }
}
