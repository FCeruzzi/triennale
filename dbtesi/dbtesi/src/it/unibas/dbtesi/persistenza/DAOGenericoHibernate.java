package it.unibas.dbtesi.persistenza;

import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.LockOptions;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DAOGenericoHibernate<T> implements IDAOGenerico<T> {

    private final static Logger logger = LoggerFactory.getLogger(DAOGenericoHibernate.class);
    private Class<T> persistentClass;

    @SuppressWarnings("unchecked")
    public DAOGenericoHibernate(Class<T> persistentClass) {
        this.persistentClass = persistentClass;
    }

    protected Class<T> getPersistentClass() {
        return persistentClass;
    }

    protected static Session getSession() throws DAOException {
        try {
            return DAOUtilHibernate.getCurrentSession();
        } catch (HibernateException ex) {
            logger.error(ex.getLocalizedMessage());
            throw new DAOException(ex);
        }
    }

    @SuppressWarnings("unchecked")
    public T makePersistent(T entity) throws DAOException {
        try {
            getSession().saveOrUpdate(entity);
        } catch (HibernateException ex) {
            logger.error(ex.getLocalizedMessage());
            throw new DAOException(ex);
        }
        return entity;
    }

    public void makeTransient(T entity) throws DAOException {
        try {
            getSession().delete(entity);
        } catch (HibernateException ex) {
            logger.error(ex.getLocalizedMessage());
            throw new DAOException(ex);
        }
    }

    @SuppressWarnings("unchecked")
    public void lock(T entity) throws DAOException {
        try {
            getSession().buildLockRequest(LockOptions.UPGRADE).lock(entity);
        } catch (HibernateException ex) {
            logger.error(ex.getLocalizedMessage());
            throw new DAOException(ex);
        }
    }

    @SuppressWarnings("unchecked")
    public T findById(String id) throws DAOException {
        Long idValue = Long.parseLong(id);
        List<T> lista = findByCriteria(Restrictions.eq("id", idValue));
        if (!lista.isEmpty()) {
            return lista.get(0);
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    public T findById(Long id, boolean lock) throws DAOException {
        T entity;
        try {
            if (lock) {
                entity = (T) getSession().get(getPersistentClass(), id, LockOptions.UPGRADE);
            } else {
                entity = (T) getSession().get(getPersistentClass(), id);
            }
//            if (lock) {
//                entity = (T) getSession().load(getPersistentClass(), id, LockOptions.UPGRADE);
//            } else {
//                entity = (T) getSession().load(getPersistentClass(), id);
//            }
        } catch (HibernateException ex) {
            logger.error(ex.getLocalizedMessage());
            throw new DAOException(ex);
        }
        return entity;
    }

    @SuppressWarnings("unchecked")
    public List<T> findAll() throws DAOException {
        return findByCriteria();
    }

    @SuppressWarnings("unchecked")
    public List<T> findAll(int offset, int limite) throws DAOException {
        return findByCriteria(offset, limite);
    }

    @SuppressWarnings("unchecked")
    public List<T> findByCriteria(Criterion... criterion) throws DAOException {
        try {
            Criteria crit = getSession().createCriteria(getPersistentClass());
            for (Criterion c : criterion) {
                crit.add(c);
            }
            return crit.list();
        } catch (HibernateException ex) {
            logger.error(ex.getLocalizedMessage());
            throw new DAOException(ex);
        }
    }

    @SuppressWarnings("unchecked")
    public List<T> findByCriteria(int offset, int limite, Criterion... criterion) throws DAOException {
        try {
            Criteria crit = getSession().createCriteria(getPersistentClass());
            for (Criterion c : criterion) {
                crit.add(c);
            }
            crit.setFirstResult(offset);
            crit.setMaxResults(limite);
            return crit.list();
        } catch (HibernateException ex) {
            logger.error(ex.getLocalizedMessage());
            throw new DAOException(ex);
        }
    }

    @SuppressWarnings("unchecked")
    public T saveOrMerge(T obj, Long id) throws DAOException {
        try {
            T persistentObject = (T) getSession().get(persistentClass, id);
            if (persistentObject != null) {
                if (logger.isDebugEnabled()) logger.debug("Get ha trovato l'oggetto con id " + id);
                return persistentObject;
            } else {
                makePersistent(obj);
                return obj;
            }
        } catch (Exception ex) {
            logger.error(ex.getLocalizedMessage());
            throw new DAOException(ex);
        }
    }

    @SuppressWarnings("unchecked")
    public T merge(T obj) throws DAOException {
        try {
            T persistentObject = (T) getSession().merge(obj);
            return persistentObject;
        } catch (Exception ex) {
            logger.error(ex.getLocalizedMessage());
            throw new DAOException(ex);
        }
    }

}
