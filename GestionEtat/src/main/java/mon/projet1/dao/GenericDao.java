package mon.projet1.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import mon.projet1.util.HibernateUtil;

import java.util.List;

public class GenericDao<T> implements IDao<T> {
    
    private Class<T> entityClass;
    private SessionFactory sessionFactory;
    
    public GenericDao(Class<T> entityClass) {
        this.entityClass = entityClass;
        this.sessionFactory = HibernateUtil.getSessionFactory();
    }
    
    @Override
    public void save(T entity) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.persist(entity);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
    }
    
    @Override
    public void update(T entity) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.merge(entity);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
    }
    
    @Override
    public void delete(T entity) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.remove(entity);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
    }
    
    @Override
    public T findById(int id) {
        Session session = sessionFactory.openSession();
        try {
            return session.get(entityClass, id);
        } finally {
            session.close();
        }
    }
    
    @Override
    public List<T> findAll() {
        Session session = sessionFactory.openSession();
        try {
            return session.createQuery("FROM " + entityClass.getSimpleName(), entityClass).list();
        } finally {
            session.close();
        }
    }
    
    @Override
    public void deleteById(int id) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            T entity = session.get(entityClass, id);
            if (entity != null) {
                session.remove(entity);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
    }
}
