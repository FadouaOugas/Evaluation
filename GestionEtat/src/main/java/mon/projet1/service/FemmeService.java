package mon.projet1.service;

import mon.projet1.beans.Femme;
import mon.projet1.beans.Mariage;
import mon.projet1.dao.GenericDao;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;
import mon.projet1.util.HibernateUtil;

import java.time.LocalDate;
import java.util.List;

public class FemmeService extends GenericDao<Femme> {
    
    private SessionFactory sessionFactory;
    
    public FemmeService() {
        super(Femme.class);
        this.sessionFactory = HibernateUtil.getSessionFactory();
    }
    
    // Méthode pour exécuter une requête native nommée retournant le nombre d'enfants d'une femme entre deux dates
    public int getNombreEnfantsEntreDates(int femmeId, LocalDate dateDebut, LocalDate dateFin) {
        Session session = sessionFactory.openSession();
        try {
            NativeQuery<Integer> query = session.createNativeQuery(
                "SELECT COALESCE(SUM(nbr_enfant), 0) FROM mariage " +
                "WHERE femme_id = :femmeId " +
                "AND date_debut BETWEEN :dateDebut AND :dateFin"
            );
            query.setParameter("femmeId", femmeId);
            query.setParameter("dateDebut", dateDebut);
            query.setParameter("dateFin", dateFin);
            
            Integer result = query.uniqueResult();
            return result != null ? result : 0;
        } finally {
            session.close();
        }
    }
    
    // Méthode pour exécuter une requête nommée retournant les femmes mariées au moins deux fois
    public List<Femme> getFemmesMariéesAuMoinsDeuxFois() {
        Session session = sessionFactory.openSession();
        try {
            Query<Femme> query = session.createQuery(
                "SELECT f FROM Femme f WHERE f.id IN " +
                "(SELECT m.femme.id FROM Mariage m GROUP BY m.femme.id HAVING COUNT(m) >= 2)", 
                Femme.class
            );
            return query.list();
        } finally {
            session.close();
        }
    }
    
    // Méthode utilisant l'API Criteria pour afficher le nombre d'hommes mariés à quatre femmes entre deux dates
    public long getNombreHommesMariesQuatreFemmesEntreDates(LocalDate dateDebut, LocalDate dateFin) {
        Session session = sessionFactory.openSession();
        try {
            Query<Long> query = session.createQuery(
                "SELECT COUNT(DISTINCT h.id) FROM Homme h " +
                "WHERE h.id IN " +
                "(SELECT m.homme.id FROM Mariage m " +
                "WHERE m.dateDebut BETWEEN :dateDebut AND :dateFin " +
                "GROUP BY m.homme.id HAVING COUNT(DISTINCT m.femme.id) = 4)", 
                Long.class
            );
            query.setParameter("dateDebut", dateDebut);
            query.setParameter("dateFin", dateFin);
            
            Long result = query.uniqueResult();
            return result != null ? result : 0;
        } finally {
            session.close();
        }
    }
    
    // Méthode pour trouver la femme la plus âgée
    public Femme getFemmeLaPlusAgee() {
        Session session = sessionFactory.openSession();
        try {
            Query<Femme> query = session.createQuery(
                "SELECT f FROM Femme f ORDER BY f.dateNaissance ASC", 
                Femme.class
            );
            query.setMaxResults(1);
            return query.uniqueResult();
        } finally {
            session.close();
        }
    }
}
