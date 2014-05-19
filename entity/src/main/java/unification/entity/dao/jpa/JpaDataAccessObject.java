package unification.entity.dao.jpa;

import com.google.inject.persist.Transactional;
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
import java.lang.reflect.Type;
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
        Class previousSuperclass = getClass();
        Class superClass = getClass().getSuperclass();
        Type superclassType = getClass().getGenericSuperclass();
        ParameterizedType genericSuperclassType = null;
        while (genericSuperclassType == null) {
            try {
                genericSuperclassType = (ParameterizedType) superclassType;
            } catch (ClassCastException e) {
                superclassType = superClass.getGenericSuperclass();
                previousSuperclass = superClass;
                superClass = superClass.getSuperclass();
            }
            if (superClass == previousSuperclass) {
                throw new IllegalArgumentException("ERROR: Class " + getClass().getName() + " is not a proper subclass of " + JpaDataAccessObject.class.getName());
            }
        }
        this.entityClass = (Class<E>) genericSuperclassType.getActualTypeArguments()[1];
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
        List<E> resultList = null;
        try {
            resultList = query.getResultList();
        } catch (NoResultException ex) {
            throw new EntityNotFoundException("No " + entityClass.getName() + " found");
        }
        if (resultList == null || resultList.isEmpty()) {
            throw new EntityNotFoundException("No " + entityClass.getName() + " found");
        }
        return resultList;
    }

    public abstract List<E> loadByParameters(Map parameters)  throws EntityNotFoundException, DaoException;

    @Transactional
    public E create(E e) throws DaoException {
        entityManagerProvider.get().persist(e);
        return e;
    }

    @Transactional
    public E store(E e) throws DaoException {
        return entityManagerProvider.get().merge(e);
    }

    @Transactional
    public E remove(E e) throws DaoException {
        entityManagerProvider.get().remove(e);
        return e;
    }

}
