package by.rudkouski.auction.dao;

import by.rudkouski.auction.bean.AbstractEntity;
import by.rudkouski.auction.pool.ProxyConnection;

import java.util.List;

public interface ICategoryDao<T extends AbstractEntity> {
    List<T> setupCategory();

    List<? extends AbstractEntity> setupTerm();

    List<? extends AbstractEntity> setupCondition();

    List<? extends AbstractEntity> setupType();
}
