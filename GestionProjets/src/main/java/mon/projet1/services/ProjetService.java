package mon.projet1.services;

import mon.projet1.classes.Projet;
import mon.projet1.classes.Tache;
import mon.projet1.dao.IDao;
import mon.projet1.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class ProjetService implements IDao<Projet> {
    @Override
    public boolean create(Projet p) {
        Transaction t = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            t = session.beginTransaction();
            session.persist(p);
            t.commit();
            return true;
        } catch (Exception ex) {
            if (t != null) t.rollback();
            ex.printStackTrace();
            return false;
        }
    }

    @Override 
    public boolean update(Projet p) {
        Transaction t = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            t = session.beginTransaction();
            session.merge(p);
            t.commit();
            return true;
        } catch (Exception ex) {
            if (t != null) t.rollback();
            ex.printStackTrace();
            return false;
        }
    }
    
    @Override 
    public boolean delete(Projet p) {
        Transaction t = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            t = session.beginTransaction();
            session.remove(p);
            t.commit();
            return true;
        } catch (Exception ex) {
            if (t != null) t.rollback();
            ex.printStackTrace();
            return false;
        }
    }
    
    @Override 
    public Projet findById(int id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Projet.class, id);
        }
    }
    
    @Override 
    public List<Projet> findAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Projet> query = session.createQuery("FROM Projet", Projet.class);
            return query.getResultList();
        }
    }

    public List<Tache> getTachesPlanifiees(int projetId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Tache> query = session.createQuery("FROM Tache t WHERE t.projet.id = :id", Tache.class);
            query.setParameter("id", projetId);
            return query.getResultList();
        }
    }

    public List<Tache> getTachesRealisees(int projetId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Tache> query = session.createQuery("FROM Tache t WHERE t.projet.id = :id AND t.dateDebutReelle IS NOT NULL", Tache.class);
            query.setParameter("id", projetId);
            return query.getResultList();
        }
    }
}
