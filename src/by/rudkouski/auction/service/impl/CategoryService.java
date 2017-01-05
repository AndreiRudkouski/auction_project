package by.rudkouski.auction.service.impl;

import by.rudkouski.auction.bean.impl.Category;
import by.rudkouski.auction.bean.impl.Condition;
import by.rudkouski.auction.bean.impl.Term;
import by.rudkouski.auction.bean.impl.Type;
import by.rudkouski.auction.dao.exception.DaoException;
import by.rudkouski.auction.dao.impl.CategoryDao;
import by.rudkouski.auction.pool.ConnectionPool;
import by.rudkouski.auction.pool.ProxyConnection;
import by.rudkouski.auction.service.ICategoryService;
import by.rudkouski.auction.service.exception.ServiceException;

import java.util.ArrayList;
import java.util.List;

public class CategoryService implements ICategoryService<Category> {
    private static final ConnectionPool POOL = ConnectionPool.getInstance();

    @Override
    public List<Category> setupCategory() throws ServiceException {
        ProxyConnection con = null;
        List<Category> categoryList;
        try {
            con = POOL.takeConnection();
            CategoryDao catalogDao = new CategoryDao(con);
            categoryList = catalogDao.setupCategory();
        } catch (DaoException e) {
            throw new ServiceException(e);
        } finally {
            POOL.returnConnection(con);
        }
        return categoryList;
    }

    @Override
    public List<List> setupNewLotData() throws ServiceException {
        ProxyConnection con = null;
        List<List> setupList;
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
        } catch (DaoException e) {
            throw new ServiceException(e);
        } finally {
            POOL.returnConnection(con);
        }
        return setupList;
    }
}
