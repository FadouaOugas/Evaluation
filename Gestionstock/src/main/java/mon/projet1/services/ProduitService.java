package mon.projet1.services;

import mon.projet1.classes.Produit;
import mon.projet1.classes.Categorie;
import mon.projet1.dao.IDao;
import mon.projet1.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import java.util.Date;
import java.util.List;

public class ProduitService implements IDao<Produit> {

    @Override
    public boolean create(Produit o) {
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
    public boolean update(Produit o) {
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
    public boolean delete(Produit o) {
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
    public Produit findById(int id) {
        Session session = null;
        Produit produit = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            produit = session.get(Produit.class, id);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null) session.close();
        }
        return produit;
    }

    @Override
    public List<Produit> findAll() {
        Session session = null;
        List<Produit> produits = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            produits = session.createQuery("from Produit").list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null) session.close();
        }
        return produits;
    }

    // Méthodes spécifiques

    public List<Produit> findByCategorie(Categorie categorie) {
        Session session = null;
        List<Produit> produits = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            produits = session.createQuery("from Produit p where p.categorie = :cat")
                    .setParameter("cat", categorie)
                    .list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null) session.close();
        }
        return produits;
    }

    public List<Object[]> findProduitsCommandesEntreDates(Date dateDebut, Date dateFin) {
        Session session = null;
        List<Object[]> results = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            String hql = "SELECT p, lcp.quantite, c.date " +
                    "FROM Produit p " +
                    "JOIN p.lignesCommande lcp " +
                    "JOIN lcp.commande c " +
                    "WHERE c.date BETWEEN :debut AND :fin";
            results = session.createQuery(hql)
                    .setParameter("debut", dateDebut)
                    .setParameter("fin", dateFin)
                    .list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null) session.close();
        }
        return results;
    }

    public List<Object[]> findProduitsParCommande(int commandeId) {
        Session session = null;
        List<Object[]> results = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            String hql = "SELECT p.reference, p.prix, lcp.quantite " +
                    "FROM Produit p " +
                    "JOIN p.lignesCommande lcp " +
                    "WHERE lcp.commande.id = :cmdId";
            results = session.createQuery(hql)
                    .setParameter("cmdId", commandeId)
                    .list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null) session.close();
        }
        return results;
    }

    public List<Produit> findByPrixSuperieur(float prix) {
        Session session = null;
        List<Produit> produits = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            produits = session.getNamedQuery("Produit.findByPrixSuperieur")
                    .setParameter("prix", prix)
                    .list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null) session.close();
        }
        return produits;
    }
}