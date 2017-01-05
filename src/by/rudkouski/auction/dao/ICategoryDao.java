package by.rudkouski.auction.dao;

import by.rudkouski.auction.bean.AbstractEntity;
import by.rudkouski.auction.dao.exception.DaoException;
import by.rudkouski.auction.pool.ProxyConnection;

import java.util.List;

public interface ICategoryDao<T extends AbstractEntity> {
    List<T> setupCategory() throws DaoException;

    List<? extends AbstractEntity> setupTerm() throws DaoException;

    List<? extends AbstractEntity> setupCondition() throws DaoException;

    List<? extends AbstractEntity> setupType() throws DaoException;
}
