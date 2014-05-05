package unification.entity.dao.jpa;

import unification.entity.dao.DataAccessObject;
import unification.entity.dao.exception.DaoException;
import unification.entity.dao.exception.EntityNotFoundException;
import unification.configuration.Log;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Map;

/**
 * User: cjohannsen
 * Date: 5/2/14
 * Time: 1:49 PM
 */
public abstract class JpaDataAccessObject<K,E> implements DataAccessObject<K,E> {

    @Log
    org.slf4j.Logger mLogger;

    protected Provider<EntityManager> entityManagerProvider;

    protected Class<E> entityClass;

    /**
     *
     */
    @Inject
    public JpaDataAccessObject(Provider<EntityManager> entityManagerProvider) {
        this.entityManagerProvider = entityManagerProvider;
        ParameterizedType genericSuperclass = (ParameterizedType) getClass().getGenericSuperclass();
        this.entityClass = (Class<E>) genericSuperclass.getActualTypeArguments()[1];
    }

    public E loadById(K id) throws EntityNotFoundException, DaoException {
        E e = entityManagerProvider.get().find(entityClass, id);
        if (e == null) {
            throw new EntityNotFoundException("No " + entityClass.getName() + " found for id " + id);
        }
        return e;
    }

    public List<E> loadAll()  throws EntityNotFoundException, DaoException {
        TypedQuery<E> query = entityManagerProvider.get().createQuery("SELECT e FROM " + entityClass.getName() + " e",
                entityClass);
        try {
            List<E> resultList = query.getResultList();
            return resultList;
        } catch (NoResultException ex) {
            throw new EntityNotFoundException("No " + entityClass.getName() + " found");
        }
    }

    public abstract List<E> loadByParameters(Map parameters)  throws EntityNotFoundException, DaoException;

    public E create(E e) throws DaoException {
        entityManagerProvider.get().persist(e);
        return e;
    }

    public E store(E e) throws DaoException {
        return entityManagerProvider.get().merge(e);
    }

    public E remove(E e) throws DaoException {
        entityManagerProvider.get().remove(e);
        return e;
    }

}
