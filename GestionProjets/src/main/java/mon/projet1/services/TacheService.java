package mon.projet1.services;

import mon.projet1.classes.Tache;
import mon.projet1.dao.IDao;
import mon.projet1.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.Date;
import java.util.List;

public class TacheService implements IDao<Tache> {
    @Override
    public boolean create(Tache t) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.persist(t);
            tx.commit();
            return true;
        } catch (Exception ex) {
            if (tx != null) tx.rollback();
            ex.printStackTrace();
            return false;
        }
    }
    @Override 
    public boolean update(Tache t) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.merge(t);
            tx.commit();
            return true;
        } catch (Exception ex) {
            if (tx != null) tx.rollback();
            ex.printStackTrace();
            return false;
        }
    }
    
    @Override 
    public boolean delete(Tache t) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.remove(t);
            tx.commit();
            return true;
        } catch (Exception ex) {
            if (tx != null) tx.rollback();
            ex.printStackTrace();
            return false;
        }
    }
    
    @Override 
    public Tache findById(int id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Tache.class, id);
        }
    }
    
    @Override 
    public List<Tache> findAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Tache> query = session.createQuery("FROM Tache", Tache.class);
            return query.getResultList();
        }
    }

    public List<Tache> getTachesPrixSuperieurA(double prix) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Tache> query = session.getNamedQuery("Tache.prixSuperieurA");
            query.setParameter("prix", prix);
            return query.getResultList();
        }
    }

    public List<Tache> getTachesEntreDates(Date debut, Date fin) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Tache> query = session.getNamedQuery("Tache.entreDates");
            query.setParameter("debut", debut);
            query.setParameter("fin", fin);
            return query.getResultList();
        }
    }
}
