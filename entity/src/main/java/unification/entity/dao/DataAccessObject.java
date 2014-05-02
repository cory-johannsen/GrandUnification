package main.java.unification.entity.dao;

import main.java.unification.entity.dao.exception.DaoException;
import main.java.unification.entity.dao.exception.EntityNotFoundException;

import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Map;

/**
 * User: cjohannsen
 * Date: 5/2/14
 * Time: 2:51 PM
 */
public interface DataAccessObject<K,E> {

    public E loadById(K id) throws EntityNotFoundException, DaoException;

    public List<E> loadAll()  throws EntityNotFoundException, DaoException;

    public List<E> loadByParameters(Map parameters)  throws EntityNotFoundException, DaoException;

    public E create(E e) throws DaoException;

    public E store(E e) throws DaoException;

    public E remove(E e) throws DaoException;
}
