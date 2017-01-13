package by.rudkouski.auction.service.impl;

import by.rudkouski.auction.entity.impl.*;
import by.rudkouski.auction.dao.exception.DaoException;
import by.rudkouski.auction.dao.impl.BetDao;
import by.rudkouski.auction.dao.impl.LotDao;
import by.rudkouski.auction.dao.impl.UserDao;
import by.rudkouski.auction.pool.ConnectionPool;
import by.rudkouski.auction.pool.ConnectionPoolException;
import by.rudkouski.auction.pool.ProxyConnection;
import by.rudkouski.auction.service.ILotService;
import by.rudkouski.auction.service.exception.ServiceException;

import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static by.rudkouski.auction.constant.ConstantName.*;

public class LotService implements ILotService<Lot> {
    private static final ConnectionPool POOL = ConnectionPool.getInstance();

    @Override
    public List<Lot> setupLot() throws ServiceException {
        ProxyConnection con = null;
        List<Lot> lotList;
        try {
            con = POOL.takeConnection();
            LotDao lotDao = new LotDao(con);
            lotList = lotDao.setupLot();
        } catch (DaoException | ConnectionPoolException e) {
            throw new ServiceException(e);
        } finally {
            try {
                POOL.returnConnection(con);
            } catch (ConnectionPoolException e) {
                throw new ServiceException(e);
            }
        }
        createPhotoPath(lotList);
        return lotList;
    }

    @Override
    public List<Lot> searchLotByCategory(long categoryId, int page) throws ServiceException {
        ProxyConnection con = null;
        List<Lot> lotList;
        try {
            con = POOL.takeConnection();
            LotDao lotDao = new LotDao(con);
            lotList = lotDao.searchLotByCategory(categoryId, page);
        } catch (DaoException | ConnectionPoolException e) {
            throw new ServiceException(e);
        } finally {
            try {
                POOL.returnConnection(con);
            } catch (ConnectionPoolException e) {
                throw new ServiceException(e);
            }
        }
        createPhotoPath(lotList);
        return lotList;
    }

    @Override
    public List<Lot> searchLotByName(String search, int page) throws ServiceException {
        ProxyConnection con = null;
        List<Lot> lotList;
        try {
            con = POOL.takeConnection();
            LotDao lotDao = new LotDao(con);
            lotList = lotDao.searchLotByName(search, page);
        } catch (DaoException | ConnectionPoolException e) {
            throw new ServiceException(e);
        } finally {
            try {
                POOL.returnConnection(con);
            } catch (ConnectionPoolException e) {
                throw new ServiceException(e);
            }
        }
        createPhotoPath(lotList);
        return lotList;
    }

    @Override
    public Lot searchLotById(long lotId) throws ServiceException {
        ProxyConnection con = null;
        Lot lot;
        try {
            con = POOL.takeConnection();
            LotDao lotDao = new LotDao(con);
            boolean finishLot = lotDao.checkAndMarkFinishLot(lotId, null);
            lot = finishLot ? lotDao.searchFinishedLotById(lotId) : lotDao.searchUnfinishedLotById(lotId);
            boolean createBetList = false;
            if ((finishLot && lot.getType().getId() == BLIND_LOT_TYPE_ID) || lot.getType().getId() != BLIND_LOT_TYPE_ID) {
                createBetList = true;
            }
            BetDao betDao = new BetDao(con);
            List<Bet> betList = betDao.receiveBetListByLotId(lotId, createBetList);
            lot.setBetList(betList);
            if (!lot.isCheck()) {
                UserDao userDao = new UserDao(con);
                User user = userDao.receiveUserById(lot.getUser().getId());
                lot.setUser(user);
            }
        } catch (DaoException | ConnectionPoolException e) {
            throw new ServiceException(e);
        } finally {
            try {
                POOL.returnConnection(con);
            } catch (ConnectionPoolException e) {
                throw new ServiceException(e);
            }
        }
        lot.setPhoto(IMG_FOLDER + lot.getPhoto());
        return lot;
    }

    @Override
    public List<List<Lot>> receiveLotHistoryByUser(long userId) throws ServiceException {
        ProxyConnection con = null;
        List<Lot> lotListFinished;
        List<Lot> lotListUnfinished;
        List<Lot> lotListUnchecked;
        try {
            con = POOL.takeConnection();
            LotDao lotDao = new LotDao(con);
            lotListFinished = lotDao.receiveFinishedLotHistoryByUser(userId);
            lotListUnfinished = lotDao.receiveUnfinishedLotHistoryByUser(userId);
            lotListUnchecked = lotDao.receiveUncheckedLotHistoryByUser(userId);
        } catch (DaoException | ConnectionPoolException e) {
            throw new ServiceException(e);
        } finally {
            try {
                POOL.returnConnection(con);
            } catch (ConnectionPoolException e) {
                throw new ServiceException(e);
            }
        }
        List<List<Lot>> lotResult = new ArrayList<>();
        lotResult.add(lotListFinished);
        lotResult.add(lotListUnfinished);
        lotResult.add(lotListUnchecked);
        return lotResult;
    }

    @Override
    public List<Lot> receiveFinishedLotHistoryByUser(long userId) throws ServiceException {
        ProxyConnection con = null;
        List<Lot> lotList;
        try {
            con = POOL.takeConnection();
            LotDao lotDao = new LotDao(con);
            lotList = lotDao.receiveFinishedLotHistoryByUser(userId);
        } catch (DaoException | ConnectionPoolException e) {
            throw new ServiceException(e);
        } finally {
            try {
                POOL.returnConnection(con);
            } catch (ConnectionPoolException e) {
                throw new ServiceException(e);
            }
        }
        return lotList;
    }

    @Override
    public List<Lot> receiveUnfinishedLotHistoryByUser(long userId) throws ServiceException {
        ProxyConnection con = null;
        List<Lot> lotList;
        try {
            con = POOL.takeConnection();
            LotDao lotDao = new LotDao(con);
            lotList = lotDao.receiveUnfinishedLotHistoryByUser(userId);
        } catch (DaoException | ConnectionPoolException e) {
            throw new ServiceException(e);
        } finally {
            try {
                POOL.returnConnection(con);
            } catch (ConnectionPoolException e) {
                throw new ServiceException(e);
            }
        }
        return lotList;
    }

    @Override
    public List<Lot> receiveUncheckedLotHistoryByUser(long userId) throws ServiceException {
        ProxyConnection con = null;
        List<Lot> lotList;
        try {
            con = POOL.takeConnection();
            LotDao lotDao = new LotDao(con);
            lotList = lotDao.receiveUncheckedLotHistoryByUser(userId);
        } catch (DaoException | ConnectionPoolException e) {
            throw new ServiceException(e);
        } finally {
            try {
                POOL.returnConnection(con);
            } catch (ConnectionPoolException e) {
                throw new ServiceException(e);
            }
        }
        return lotList;
    }

    @Override
    public BigDecimal determineLotMinBet(long lotId) throws ServiceException {
        ProxyConnection con = null;
        BigDecimal minBet;
        try {
            con = POOL.takeConnection();
            LotDao lotDao = new LotDao(con);
            minBet = lotDao.determineLotMinBet(lotId);
        } catch (DaoException | ConnectionPoolException e) {
            throw new ServiceException(e);
        } finally {
            try {
                POOL.returnConnection(con);
            } catch (ConnectionPoolException e) {
                throw new ServiceException(e);
            }
        }
        return minBet;
    }

    @Override
    public boolean addLot(Map<String, String[]> paramMap, Part part, String appPath) throws ServiceException {
        ProxyConnection con = null;
        Lot lot = createLot(paramMap);
        try {
            con = POOL.takeConnection();
            LotDao lotDao = new LotDao(con);
            con.setAutoCommit(false);
            long id = lotDao.addLot(lot);
            String savePath = appPath + IMG_FOLDER;
            File fileSaveDir = new File(savePath);
            if (!fileSaveDir.exists()) {
                fileSaveDir.mkdir();
            }
            String[] extension = lot.getPhoto().split(REGEX_POINT_DIVIDER);
            String photoName = LOT + id + POINT + extension[extension.length - 1];
            part.write(savePath + File.separator + photoName);
            lotDao.addPhotoByLotId(id, photoName);
            con.commit();
        } catch (SQLException | IOException | DaoException | ConnectionPoolException e) {
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
    public boolean editLot(Map<String, String[]> paramMap, Part part, String appPath) throws ServiceException {
        ProxyConnection con = null;
        Lot lot = createLot(paramMap);
        try {
            con = POOL.takeConnection();
            LotDao lotDao = new LotDao(con);
            long id = lot.getId();
            Lot lotTmp = lotDao.searchFinishedLotById(id);
            if (lotTmp.isCheck()) {
                return false;
            }
            con.setAutoCommit(false);
            lotDao.editLot(lot);
            if (lot.getPhoto() != null) {
                String savePath = appPath + IMG_FOLDER;
                File fileSaveDir = new File(savePath);
                if (!fileSaveDir.exists()) {
                    fileSaveDir.mkdir();
                }
                String[] extension = lot.getPhoto().split(REGEX_POINT_DIVIDER);
                String photoName = LOT + id + POINT + extension[extension.length - 1];
                part.write(savePath + File.separator + photoName);
            }
            con.commit();
        } catch (SQLException | IOException | DaoException | ConnectionPoolException e) {
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
    public void checkLot(long lotId) throws ServiceException {
        ProxyConnection con = null;
        try {
            con = POOL.takeConnection();
            LotDao lotDao = new LotDao(con);
            lotDao.checkLot(lotId);
        } catch (DaoException | ConnectionPoolException e) {
            throw new ServiceException(e);
        } finally {
            try {
                POOL.returnConnection(con);
            } catch (ConnectionPoolException e) {
                throw new ServiceException(e);
            }
        }
    }

    private void createPhotoPath(List<Lot> lotList) {
        for (Lot lot : lotList) {
            lot.setPhoto(IMG_FOLDER + lot.getPhoto());
        }
    }

    private Lot createLot(Map<String, String[]> paramMap) {
        Lot lot = new Lot();
        lot.setTimeStart(new Date(System.currentTimeMillis()));
        for (Map.Entry<String, String[]> param : paramMap.entrySet()) {
            switch (param.getKey()) {
                case TITLE:
                    lot.setName(param.getValue()[0]);
                    break;
                case PRICE_START:
                    if (param.getValue()[0] != null && !param.getValue()[0].isEmpty()) {
                        lot.setPrice(new BigDecimal(param.getValue()[0]));
                    }
                    break;
                case PRICE_STEP:
                    if (param.getValue()[0] != null && !param.getValue()[0].isEmpty()) {
                        lot.setStepPrice(new BigDecimal(param.getValue()[0]));
                    }
                    break;
                case PRICE_BLITZ:
                    if (param.getValue()[0] != null && !param.getValue()[0].isEmpty()) {
                        lot.setPriceBlitz(new BigDecimal(param.getValue()[0]));
                    }
                    break;
                case PHOTO:
                    lot.setPhoto(param.getValue()[0]);
                    break;
                case DESCRIPTION:
                    lot.setDescription(param.getValue()[0]);
                    break;
                case CATEGORY:
                    lot.setCategoryId(Long.parseLong(param.getValue()[0]));
                    break;
                case TYPE:
                    Type type = new Type();
                    type.setId(Long.parseLong(param.getValue()[0]));
                    lot.setType(type);
                    break;
                case TERM:
                    Term term = new Term();
                    term.setId(Long.parseLong(param.getValue()[0]));
                    lot.setTerm(term);
                    break;
                case CONDITION:
                    Condition cond = new Condition();
                    cond.setId(Long.parseLong(param.getValue()[0]));
                    lot.setCondition(cond);
                    break;
                case USER_ID:
                    User user = new User();
                    user.setId(Long.parseLong(param.getValue()[0]));
                    lot.setUser(user);
                    break;
                case LOT_ID:
                    lot.setId(Long.parseLong(param.getValue()[0]));
            }
        }
        return lot;
    }
}
