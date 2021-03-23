package it.unibas.dbtesi.persistenza;

import it.unibas.dbtesi.persistenza.DAOException;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DAOUtilHibernate {

    private final static Logger logger = LoggerFactory.getLogger(DAOUtilHibernate.class);

    private static SessionFactory sessionFactory;
    private static ServiceRegistry serviceRegistry;

    static {
        try {
            Configuration configuration = new Configuration();
            configuration.configure();
            serviceRegistry = (ServiceRegistry) new ServiceRegistryBuilder().applySettings(configuration.getProperties()).buildServiceRegistry();
            sessionFactory = configuration.buildSessionFactory(serviceRegistry);
//            Configuration configuration = new Configuration();
//            sessionFactory = configuration.configure().buildSessionFactory();
        } catch (Throwable ex) {
            logger.error("Building SessionFactory failed.", ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static Session getCurrentSession() throws DAOException {
        try {
            return sessionFactory.getCurrentSession();
        } catch (HibernateException ex) {
            logger.error(ex.getLocalizedMessage());
            throw new DAOException(ex);
        }
    }

    public static void beginTransaction() throws DAOException {
        try {
            sessionFactory.getCurrentSession().beginTransaction();
        } catch (HibernateException ex) {
            logger.error(ex.getLocalizedMessage());
            throw new DAOException(ex);
        }
    }

    public static void commit() throws DAOException {
        try {
            sessionFactory.getCurrentSession().getTransaction().commit();
        } catch (HibernateException ex) {
            logger.error(ex.getLocalizedMessage());
            throw new DAOException(ex);
        }
    }

    public static void rollback() {
        try {
            sessionFactory.getCurrentSession().getTransaction().rollback();
        } catch (HibernateException ex) {
            logger.error(ex.getLocalizedMessage());
        }
    }
}
