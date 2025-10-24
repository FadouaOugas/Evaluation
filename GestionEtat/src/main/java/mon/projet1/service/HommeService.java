package mon.projet1.service;

import mon.projet1.beans.Homme;
import mon.projet1.beans.Mariage;
import mon.projet1.dao.GenericDao;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import mon.projet1.util.HibernateUtil;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class HommeService extends GenericDao<Homme> {
    
    private SessionFactory sessionFactory;
    
    public HommeService() {
        super(Homme.class);
        this.sessionFactory = HibernateUtil.getSessionFactory();
    }
    
    // Méthode pour afficher les épouses d'un homme entre deux dates
    public List<Mariage> getEpousesEntreDates(int hommeId, LocalDate dateDebut, LocalDate dateFin) {
        Session session = sessionFactory.openSession();
        try {
            return session.createQuery(
                "SELECT m FROM Mariage m WHERE m.homme.id = :hommeId " +
                "AND m.dateDebut BETWEEN :dateDebut AND :dateFin", Mariage.class)
                .setParameter("hommeId", hommeId)
                .setParameter("dateDebut", dateDebut)
                .setParameter("dateFin", dateFin)
                .list();
        } finally {
            session.close();
        }
    }
    
    // Méthode pour afficher les mariages d'un homme avec tous les détails
    public void afficherMariagesHomme(int hommeId) {
        Session session = sessionFactory.openSession();
        try {
            Homme homme = session.get(Homme.class, hommeId);
            if (homme == null) {
                System.out.println("Homme non trouvé avec l'ID: " + hommeId);
                return;
            }
            
            System.out.println("Nom : " + homme.getNom() + " " + homme.getPrenom());
            DateTimeFormatter df = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            
            // Mariages en cours
            List<Mariage> mariagesEnCours = session.createQuery(
                "SELECT m FROM Mariage m WHERE m.homme.id = :hommeId AND m.dateFin IS NULL", Mariage.class)
                .setParameter("hommeId", hommeId)
                .list();
            
            System.out.println("Mariages En Cours :");
            int index = 1;
            for (Mariage mariage : mariagesEnCours) {
                System.out.println(index + ". Femme : " + mariage.getFemme().getNom() + " " + 
                    mariage.getFemme().getPrenom() + "   Date Début : " + 
                    (mariage.getDateDebut() != null ? df.format(mariage.getDateDebut()) : "") + 
                    "    Nbr Enfants : " + mariage.getNbrEnfant());
                index++;
            }
            
            // Mariages échoués
            List<Mariage> mariagesEchoues = session.createQuery(
                "SELECT m FROM Mariage m WHERE m.homme.id = :hommeId AND m.dateFin IS NOT NULL", Mariage.class)
                .setParameter("hommeId", hommeId)
                .list();
            
            System.out.println("\nMariages échoués :");
            index = 1;
            for (Mariage mariage : mariagesEchoues) {
                System.out.println(index + ". Femme : " + mariage.getFemme().getNom() + " " + 
                    mariage.getFemme().getPrenom() + "  Date Début : " + 
                    (mariage.getDateDebut() != null ? df.format(mariage.getDateDebut()) : "") + 
                    "    Date Fin : " + 
                    (mariage.getDateFin() != null ? df.format(mariage.getDateFin()) : "") + 
                    "    Nbr Enfants : " + mariage.getNbrEnfant());
                index++;
            }
            
        } finally {
            session.close();
        }
    }
}
