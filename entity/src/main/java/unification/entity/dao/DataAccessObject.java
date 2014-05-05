package unification.entity.dao;

import unification.entity.dao.exception.DaoException;
import unification.entity.dao.exception.EntityNotFoundException;

import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Map;

/**
 * User: cjohannsen
 * Date: 5/2/14
 * Time: 2:51 PM
 */
public interface DataAccessObject<K,E> {

    /**
     * Returns the entity with the given ID
     * @param id
     * @return
     * @throws EntityNotFoundException
     * @throws DaoException
     */
    public E loadById(K id) throws EntityNotFoundException, DaoException;

    /**
     * Returns all entities
     * @return
     * @throws EntityNotFoundException
     * @throws DaoException
     */
    public List<E> loadAll()  throws EntityNotFoundException, DaoException;

    /**
     * Returns all entities matching the parameters in the given map
     * @param parameters
     * @return
     * @throws EntityNotFoundException
     * @throws DaoException
     */
    public List<E> loadByParameters(Map parameters)  throws EntityNotFoundException, DaoException;

    /**
     * Creates the entity as a persistent object
     * @param e
     * @return
     * @throws DaoException
     */
    public E create(E e) throws DaoException;

    /**
     * Stores the given persistent entity
     * @param e
     * @return
     * @throws DaoException
     */
    public E store(E e) throws DaoException;

    /**
     * Remote the given persistent entity
     * @param e
     * @return
     * @throws DaoException
     */
    public E remove(E e) throws DaoException;
}
