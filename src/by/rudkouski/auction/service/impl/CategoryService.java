package by.rudkouski.auction.service.impl;

import by.rudkouski.auction.entity.AbstractEntity;
import by.rudkouski.auction.entity.impl.Category;
import by.rudkouski.auction.entity.impl.Condition;
import by.rudkouski.auction.entity.impl.Term;
import by.rudkouski.auction.entity.impl.Type;
import by.rudkouski.auction.dao.exception.DaoException;
import by.rudkouski.auction.dao.impl.CategoryDao;
import by.rudkouski.auction.pool.ConnectionPool;
import by.rudkouski.auction.pool.ConnectionPoolException;
import by.rudkouski.auction.pool.ProxyConnection;
import by.rudkouski.auction.service.ICategoryService;
import by.rudkouski.auction.service.exception.ServiceException;

import java.util.ArrayList;
import java.util.List;

public class CategoryService implements ICategoryService {
    private static final ConnectionPool POOL = ConnectionPool.getInstance();

    @Override
    public List<Category> setupCategory() throws ServiceException {
        ProxyConnection con = null;
        List<Category> categoryList;
        try {
            con = POOL.takeConnection();
            CategoryDao catalogDao = new CategoryDao(con);
            categoryList = catalogDao.setupCategory();
        } catch (DaoException | ConnectionPoolException e) {
            throw new ServiceException(e);
        } finally {
            try {
                POOL.returnConnection(con);
            } catch (ConnectionPoolException e) {
                throw new ServiceException(e);
            }
        }
        return categoryList;
    }

    @Override
    public List<List<? extends AbstractEntity>> setupNewLotData() throws ServiceException {
        ProxyConnection con = null;
        List<List<? extends AbstractEntity>> setupList;
        try {
            con = POOL.takeConnection();
            CategoryDao catalogDao = new CategoryDao(con);
            List<Type> typeList = catalogDao.setupType();
            List<Term> termList = catalogDao.setupTerm();
            List<Condition> condList = catalogDao.setupCondition();
            setupList = new ArrayList<>();
            setupList.add(typeList);
            setupList.add(termList);
            setupList.add(condList);
        } catch (DaoException | ConnectionPoolException e) {
            throw new ServiceException(e);
        } finally {
            try {
                POOL.returnConnection(con);
            } catch (ConnectionPoolException e) {
                throw new ServiceException(e);
            }
        }
        return setupList;
    }
}
