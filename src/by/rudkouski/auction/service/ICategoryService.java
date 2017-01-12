package by.rudkouski.auction.service;

import by.rudkouski.auction.entity.AbstractEntity;
import by.rudkouski.auction.service.exception.ServiceException;

import java.util.List;

/**
 * This interface contains methods for data conversion and sending data to other layer.
 * Provides performance of the main application logic.
 * Methods are associated with different entity
 */

public interface ICategoryService {

    /**
     * Returns list of Categories of lot
     *
     * @return list containing Categories
     * @throws ServiceException if DaoException or ConnectionPoolException is thrown
     */
    List<? extends AbstractEntity> setupCategory() throws ServiceException;

    /**
     * Returns list of lists which contain Types, Term and Condition.
     *
     * @return list containing three lists: the first list is Type list, the second list is Term list and the third list - Condition list
     * @throws ServiceException if DaoException or ConnectionPoolException is thrown
     */
    List<List<? extends AbstractEntity>> setupNewLotData() throws ServiceException;
}
