package mon.projet1.services;

import mon.projet1.classes.EmployeTache;
import mon.projet1.dao.IDao;
import mon.projet1.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class EmployeTacheService implements IDao<EmployeTache> {
    @Override
    public boolean create(EmployeTache et) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.persist(et);
            tx.commit();
            return true;
        } catch (Exception ex) {
            if (tx != null) tx.rollback();
            ex.printStackTrace();
            return false;
        }
    }

    @Override 
    public boolean update(EmployeTache et) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.merge(et);
            tx.commit();
            return true;
        } catch (Exception ex) {
            if (tx != null) tx.rollback();
            ex.printStackTrace();
            return false;
        }
    }
    
    @Override 
    public boolean delete(EmployeTache et) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.remove(et);
            tx.commit();
            return true;
        } catch (Exception ex) {
            if (tx != null) tx.rollback();
            ex.printStackTrace();
            return false;
        }
    }
    
    @Override 
    public EmployeTache findById(int id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(EmployeTache.class, id);
        }
    }
    
    @Override 
    public List<EmployeTache> findAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<EmployeTache> query = session.createQuery("FROM EmployeTache", EmployeTache.class);
            return query.getResultList();
        }
    }
}
