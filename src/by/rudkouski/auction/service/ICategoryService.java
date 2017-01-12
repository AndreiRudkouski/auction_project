package by.rudkouski.auction.service;

import by.rudkouski.auction.entity.AbstractEntity;
import by.rudkouski.auction.service.exception.ServiceException;

import java.util.List;

public interface ICategoryService {

    List<? extends AbstractEntity> setupCategory() throws ServiceException;

    List<List<? extends AbstractEntity>> setupNewLotData() throws ServiceException;
}
