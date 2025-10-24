package mon.projet1.services;

import mon.projet1.classes.Commande;
import mon.projet1.classes.LigneCommande;
import mon.projet1.dao.IDao;
import mon.projet1.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class CommandeService implements IDao<Commande> {

    @Override
    public boolean create(Commande o) {
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
    public boolean update(Commande o) {
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
    public boolean delete(Commande o) {
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
    public Commande findById(int id) {
        Session session = null;
        Commande commande = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            commande = session.get(Commande.class, id);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null) session.close();
        }
        return commande;
    }

    @Override
    public List<Commande> findAll() {
        Session session = null;
        List<Commande> commandes = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            commandes = session.createQuery("from Commande").list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null) session.close();
        }
        return commandes;
    }

    // Méthodes spécifiques
    public List<Commande> findCommandesEntreDates(Date debut, Date fin) {
        Session session = null;
        List<Commande> commandes = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            commandes = session.createQuery("from Commande c where c.date between :debut and :fin")
                    .setParameter("debut", debut)
                    .setParameter("fin", fin)
                    .list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null) session.close();
        }
        return commandes;
    }

    // Nouvelle méthode pour afficher une commande avec ses produits
    public void afficherCommande(int commandeId) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            Commande commande = session.get(Commande.class, commandeId);
            if (commande != null) {
                SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM yyyy");
                System.out.println("Commande : " + commande.getId() +
                        "     Date : " + sdf.format(commande.getDate()));
                System.out.println("Liste des produits :");
                System.out.println("Référence\tPrix\tQuantité");

                for (LigneCommande lc : commande.getLignesCommande()) {
                    System.out.println(lc.getProduit().getReference() + "\t\t" +
                            lc.getProduit().getPrix() + " DH\t" +
                            lc.getQuantite());
                }
            } else {
                System.out.println("Commande introuvable !");
            }
        } finally {
            session.close();
        }
    }
}
