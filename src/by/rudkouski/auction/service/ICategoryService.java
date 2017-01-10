package by.rudkouski.auction.service;

import by.rudkouski.auction.entity.AbstractEntity;
import by.rudkouski.auction.service.exception.ServiceException;

import java.util.List;

public interface ICategoryService<T extends AbstractEntity> {

    List<T> setupCategory() throws ServiceException;

    List<List> setupNewLotData() throws ServiceException;
}
