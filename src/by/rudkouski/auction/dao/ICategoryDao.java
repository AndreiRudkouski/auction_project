package by.rudkouski.auction.dao;

import by.rudkouski.auction.entity.AbstractEntity;
import by.rudkouski.auction.dao.exception.DaoException;

import java.util.List;

/**
 * This interface contains methods for working with different entity
 */
public interface ICategoryDao {

    /**
     * Returns list of Categories of lot from database
     *
     * @return list containing Categories which are in database
     * @throws DaoException if SQLException is thrown
     */
    List<? extends AbstractEntity> setupCategory() throws DaoException;

    /**
     * Returns list of Terms of lot from database
     *
     * @return list containing Terms which are in database
     * @throws DaoException if SQLException is thrown
     */
    List<? extends AbstractEntity> setupTerm() throws DaoException;

    /**
     * Returns list of Conditions of lot from database
     *
     * @return list containing Conditions which are in database
     * @throws DaoException if SQLException is thrown
     */
    List<? extends AbstractEntity> setupCondition() throws DaoException;

    /**
     * Returns list of Types of lot from database
     *
     * @return list containing Types which are in database
     * @throws DaoException if SQLException is thrown
     */
    List<? extends AbstractEntity> setupType() throws DaoException;
}
