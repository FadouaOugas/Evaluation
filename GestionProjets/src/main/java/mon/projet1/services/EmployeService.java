package mon.projet1.services;

import mon.projet1.classes.Employe;
import mon.projet1.classes.EmployeTache;
import mon.projet1.classes.Projet;
import mon.projet1.classes.Tache;
import mon.projet1.dao.IDao;
import mon.projet1.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class EmployeService implements IDao<Employe> {
    @Override
    public boolean create(Employe e) {
        Transaction t = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            t = session.beginTransaction();
            session.persist(e);
            t.commit();
            return true;
        } catch (Exception ex) {
            if (t != null) t.rollback();
            ex.printStackTrace();
            return false;
        }
    }
    @Override 
    public boolean update(Employe e) {
        Transaction t = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            t = session.beginTransaction();
            session.merge(e);
            t.commit();
            return true;
        } catch (Exception ex) {
            if (t != null) t.rollback();
            ex.printStackTrace();
            return false;
        }
    }
    
    @Override 
    public boolean delete(Employe e) {
        Transaction t = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            t = session.beginTransaction();
            session.remove(e);
            t.commit();
            return true;
        } catch (Exception ex) {
            if (t != null) t.rollback();
            ex.printStackTrace();
            return false;
        }
    }
    
    @Override 
    public Employe findById(int id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Employe.class, id);
        }
    }
    
    @Override 
    public List<Employe> findAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Employe> query = session.createQuery("FROM Employe", Employe.class);
            return query.getResultList();
        }
    }

    public List<Projet> getProjetsParEmploye(int employeId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Projet> query = session.createQuery(
                    "SELECT DISTINCT t.projet FROM EmployeTache et JOIN et.tache t WHERE et.employe.id = :id", Projet.class);
            query.setParameter("id", employeId);
            return query.getResultList();
        }
    }

    public List<Tache> getTachesParEmploye(int employeId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Tache> query = session.createQuery(
                    "SELECT et.tache FROM EmployeTache et WHERE et.employe.id = :id", Tache.class);
            query.setParameter("id", employeId);
            return query.getResultList();
        }
    }
}
