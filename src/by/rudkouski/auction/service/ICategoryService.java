package by.rudkouski.auction.service;

import by.rudkouski.auction.bean.AbstractEntity;

import java.util.List;

public interface ICategoryService<T extends AbstractEntity> {
    List<T> setupCategory();

    List<List> setupNewLotData();
}
