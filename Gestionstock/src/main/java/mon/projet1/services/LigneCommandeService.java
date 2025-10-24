package mon.projet1.services;

import mon.projet1.classes.LigneCommande;
import mon.projet1.dao.IDao;
import mon.projet1.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import java.util.List;

public class LigneCommandeService implements IDao<LigneCommande> {

    @Override
    public boolean create(LigneCommande o) {
        Session session = null;
        Transaction tx = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            tx = session.beginTransaction();
            session.save(o);
            tx.commit();
            return true;
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
            return false;
        } finally {
            if (session != null) session.close();
        }
    }

    @Override
    public boolean update(LigneCommande o) {
        Session session = null;
        Transaction tx = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            tx = session.beginTransaction();
            session.update(o);
            tx.commit();
            return true;
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            return false;
        } finally {
            if (session != null) session.close();
        }
    }

    @Override
    public boolean delete(LigneCommande o) {
        Session session = null;
        Transaction tx = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            tx = session.beginTransaction();
            session.delete(o);
            tx.commit();
            return true;
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            return false;
        } finally {
            if (session != null) session.close();
        }
    }

    @Override
    public LigneCommande findById(int id) {
        Session session = null;
        LigneCommande lc = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            lc = session.get(LigneCommande.class, id);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null) session.close();
        }
        return lc;
    }

    @Override
    public List<LigneCommande> findAll() {
        Session session = null;
        List<LigneCommande> lignes = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            lignes = session.createQuery("from LigneCommande").list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null) session.close();
        }
        return lignes;
    }

    // Méthodes spécifiques
    public List<LigneCommande> findByCommandeId(int commandeId) {
        Session session = null;
        List<LigneCommande> lignes = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            lignes = session.createQuery("from LigneCommande l where l.commande.id = :cmdId")
                    .setParameter("cmdId", commandeId)
                    .list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null) session.close();
        }
        return lignes;
    }
}
