package by.rudkouski.auction.service.impl;

import by.rudkouski.auction.bean.impl.Bet;
import by.rudkouski.auction.bean.impl.Lot;
import by.rudkouski.auction.dao.impl.BetDao;
import by.rudkouski.auction.dao.impl.LotDao;
import by.rudkouski.auction.pool.ConnectionPool;
import by.rudkouski.auction.pool.ProxyConnection;
import by.rudkouski.auction.service.ILotService;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LotService implements ILotService<Lot> {
    private static final long BLIND_TYPE_ID = 3;
    private static final ConnectionPool POOL = ConnectionPool.getInstance();
    private static final String IMG_FOLDER = File.separator + "img" + File.separator + "lots" + File.separator;
    private static final String POINT_DIVIDER = "\\.";
    private static final String POINT = ".";
    private static final String LOT = "lot";

    @Override
    public List<Lot> setupLot() {
        ProxyConnection con = null;
        List<Lot> lotList;
        try {
            con = POOL.takeConnection();
            LotDao lotDao = new LotDao(con);
            lotList = lotDao.setupLot();
        } finally {
            POOL.returnConnection(con);
        }
        createPhotoPath(lotList);
        return lotList;
    }

    @Override
    public List<Lot> searchLotByCategory(long categoryId, int page) {
        ProxyConnection con = null;
        List<Lot> lotList;
        try {
            con = POOL.takeConnection();
            LotDao lotDao = new LotDao(con);
            lotList = lotDao.searchLotByCategory(categoryId, page);
        } finally {
            POOL.returnConnection(con);
        }
        createPhotoPath(lotList);
        return lotList;
    }

    @Override
    public List<Lot> searchLotByNamePart(String search, int page) {
        ProxyConnection con = null;
        List<Lot> lotList;
        try {
            con = POOL.takeConnection();
            LotDao lotDao = new LotDao(con);
            lotList = lotDao.searchLotByNamePart(search, page);
        } finally {
            POOL.returnConnection(con);
        }
        createPhotoPath(lotList);
        return lotList;
    }

    @Override
    public Lot searchLotById(long lotId) {
        ProxyConnection con = null;
        Lot lot;
        try {
            con = POOL.takeConnection();
            LotDao lotDao = new LotDao(con);
            boolean finishLot = lotDao.checkAndMarkFinishLot(lotId, null);
            lot = finishLot ? lotDao.searchFinishedLotById(lotId) : lotDao.searchNotFinishedLotById(lotId);
            boolean createBetList = false;
            if ((finishLot && lot.getType().getId() == BLIND_TYPE_ID) || lot.getType().getId() != BLIND_TYPE_ID) {
                createBetList = true;
            }
            BetDao betDao = new BetDao(con);
            List<Bet> betList = betDao.receiveBetListByLotId(lotId, createBetList);
            lot.setBetList(betList);
        } finally {
            POOL.returnConnection(con);
        }
        lot.setPhoto(IMG_FOLDER + lot.getPhoto());
        return lot;
    }

    @Override
    public List<List<Lot>> receiveLotHistoryByUser(long userId) {
        ProxyConnection con = null;
        List<Lot> lotList;
        List<List<Lot>> lotResult = null;
        try {
            con = POOL.takeConnection();
            LotDao lotDao = new LotDao(con);
            lotList = lotDao.receiveLotHistoryByUser(userId);
        } finally {
            POOL.returnConnection(con);
        }
        if (lotList != null) {
            List<Lot> lotListFinished = null;
            List<Lot> lotListChecked = null;
            List<Lot> lotListUnchecked = null;
            for (Lot lot : lotList) {
                if (lot.isFinish()) {
                    if (lotListFinished == null) {
                        lotListFinished = new ArrayList<>();
                    }
                    lotListFinished.add(lot);
                } else {
                    if (lot.isCheck()) {
                        if (lotListChecked == null) {
                            lotListChecked = new ArrayList<>();
                        }
                        lotListChecked.add(lot);
                    } else {
                        if (lotListUnchecked == null) {
                            lotListUnchecked = new ArrayList<>();
                        }
                        lotListUnchecked.add(lot);
                    }
                }
            }
            lotResult = new ArrayList<>();
            lotResult.add(lotListFinished);
            lotResult.add(lotListChecked);
            lotResult.add(lotListUnchecked);
        }
        return lotResult;
    }

    @Override
    public BigDecimal determineLotMinBet(long lotId) {
        ProxyConnection con = null;
        BigDecimal minBet;
        try {
            con = POOL.takeConnection();
            LotDao lotDao = new LotDao(con);
            minBet = lotDao.determineLotMinBet(lotId);
        } finally {
            POOL.returnConnection(con);
        }
        return minBet;
    }

    @Override
    public boolean addLot(Lot lot, String appPath) {
        ProxyConnection con = null;
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
            String[] extension = lot.getPhoto().split(POINT_DIVIDER);
            String photoName = LOT + id + POINT + extension[extension.length - 1];
            lot.getPart().write(savePath + File.separator + photoName);
            lotDao.addPhotoByLotId(id, photoName);
            con.commit();
        } catch (SQLException e) {
            //throw new ServiceException("SQLException", e);
            try {
                con.rollback();
            } catch (SQLException e1) {
                //throw new ServiceException("SQLException", e);
            }
        } catch (IOException e) {
            //throw new ServiceException("IOException", e);
        } finally {
            POOL.returnConnection(con);
        }
        return true;
    }

    private void createPhotoPath(List<Lot> lotList) {
        for (Lot lot : lotList) {
            lot.setPhoto(IMG_FOLDER + lot.getPhoto());
        }
    }
}
