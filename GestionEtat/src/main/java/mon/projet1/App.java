package mon.projet1;

import mon.projet1.beans.*;
import mon.projet1.service.*;
import mon.projet1.util.HibernateUtil;

import java.time.LocalDate;
import java.util.List;

public class App {

    public static void main(String[] args) {
        System.out.println(" DÉMARRAGE DE L'APPLICATION DE GESTION DE L'ÉTAT CIVIL");
        try {
            System.out.println("\nConnexion à la base de données en cours...");

            HommeService hommeService = new HommeService();
            FemmeService femmeService = new FemmeService();
            MariageService mariageService = new MariageService();

            System.out.println("\n=== APPLICATION DE GESTION DE L'ÉTAT CIVIL ===\n");

            System.out.println("1  Création des données de test...");
            creerDonneesTest(hommeService, femmeService, mariageService);
            System.out.println(" Données de test créées avec succès !\n");

            System.out.println(" Liste des femmes :");
            afficherListeFemmes(femmeService);

            //  Afficher la femme la plus âgée
            System.out.println("\n Femme la plus âgée :");
            afficherFemmeLaPlusAgee(femmeService);

            //  Afficher les épouses d’un homme entre deux dates
            System.out.println("\nÉpouses d’un homme (ID=1) entre 1990 et 2000 :");
            afficherEpousesHomme(hommeService);

            //  Afficher le nombre d’enfants d’une femme entre deux dates
            System.out.println("\n Nombre d’enfants d’une femme (ID=1) entre 1990 et 2000 :");
            afficherNombreEnfantsFemme(femmeService);

            // Afficher les femmes mariées au moins deux fois
            System.out.println("\nFemmes mariées au moins deux fois :");
            afficherFemmesMariéesPlusieursFois(femmeService);

            // Afficher les hommes mariés à quatre femmes entre deux dates
            System.out.println("\nHommes mariés à quatre femmes entre 1990 et 2000 :");
            afficherHommesMariesQuatreFemmes(femmeService);

            // Afficher les mariages détaillés d’un homme
            System.out.println("\nDétails des mariages d’un homme (ID=1) :");
            afficherDetailsMariagesHomme(hommeService, 1);

        } catch (Exception e) {
            System.err.println("Erreur lors de l'exécution : " + e.getMessage());
            e.printStackTrace();
        } finally {
            HibernateUtil.closeSessionFactory();
            System.out.println("\n Application terminée. Session Hibernate fermée.");
        }
    }

    // -------------------------------
    // MÉTHODES DE TEST ET AFFICHAGE
    // -------------------------------

    private static void creerDonneesTest(HommeService hommeService, FemmeService femmeService, MariageService mariageService) {
        Homme homme1 = new Homme("SAFI", "SAID", "0612345678", "Casablanca", LocalDate.of(1960, 5, 15));
        Homme homme2 = new Homme("ALAMI", "AHMED", "0612345679", "Rabat", LocalDate.of(1965, 8, 20));
        Homme homme3 = new Homme("BENALI", "MOHAMED", "0612345680", "Fès", LocalDate.of(1970, 3, 10));
        Homme homme4 = new Homme("CHRAIBI", "YOUSSEF", "0612345681", "Marrakech", LocalDate.of(1975, 12, 5));
        Homme homme5 = new Homme("DAOUDI", "HASSAN", "0612345682", "Tanger", LocalDate.of(1980, 7, 25));

        hommeService.save(homme1);
        hommeService.save(homme2);
        hommeService.save(homme3);
        hommeService.save(homme4);
        hommeService.save(homme5);

        Femme[] femmes = {
                new Femme("SALIMA", "RAMI", "0612345683", "Casablanca", LocalDate.of(1965, 4, 12)),
                new Femme("AMAL", "ALI", "0612345684", "Rabat", LocalDate.of(1970, 9, 18)),
                new Femme("WAFA", "ALAOUI", "0612345685", "Fès", LocalDate.of(1975, 6, 30)),
                new Femme("KARIMA", "ALAMI", "0612345686", "Marrakech", LocalDate.of(1968, 11, 8)),
                new Femme("FATIMA", "BENALI", "0612345687", "Tanger", LocalDate.of(1972, 2, 14)),
                new Femme("AICHA", "CHRAIBI", "0612345688", "Casablanca", LocalDate.of(1978, 8, 22)),
                new Femme("ZINEB", "DAOUDI", "0612345689", "Rabat", LocalDate.of(1982, 1, 16)),
                new Femme("KHADIJA", "EL FASSI", "0612345690", "Fès", LocalDate.of(1976, 10, 3)),
                new Femme("MALIKA", "BENJELLOUN", "0612345691", "Marrakech", LocalDate.of(1985, 5, 28)),
                new Femme("NADIA", "EL OUARDI", "0612345692", "Tanger", LocalDate.of(1988, 12, 11))
        };
        for (Femme f : femmes) femmeService.save(f);

        // Mariages
        Mariage mariage1 = new Mariage(LocalDate.of(1989, 9, 3), LocalDate.of(1990, 9, 3), 0); // échoué
        mariage1.setHomme(homme1);
        mariage1.setFemme(femmes[3]);
        mariageService.save(mariage1);

        Mariage mariage2 = new Mariage(LocalDate.of(1990, 9, 3), 4);
        mariage2.setHomme(homme1);
        mariage2.setFemme(femmes[0]);
        mariageService.save(mariage2);

        Mariage mariage3 = new Mariage(LocalDate.of(1995, 9, 3), 2);
        mariage3.setHomme(homme1);
        mariage3.setFemme(femmes[1]);
        mariageService.save(mariage3);

        Mariage mariage4 = new Mariage(LocalDate.of(2000, 11, 4), 3);
        mariage4.setHomme(homme1);
        mariage4.setFemme(femmes[2]);
        mariageService.save(mariage4);

        // Autres hommes (abrégé)
        Mariage mariage5 = new Mariage(LocalDate.of(1992, 6, 15), 2);
        mariage5.setHomme(homme2);
        mariage5.setFemme(femmes[4]);
        mariageService.save(mariage5);

        Mariage mariage6 = new Mariage(LocalDate.of(1998, 3, 20), 1);
        mariage6.setHomme(homme2);
        mariage6.setFemme(femmes[5]);
        mariageService.save(mariage6);

        Mariage mariage7 = new Mariage(LocalDate.of(1995, 12, 10), 3);
        mariage7.setHomme(homme3);
        mariage7.setFemme(femmes[6]);
        mariageService.save(mariage7);

        Mariage mariage8 = new Mariage(LocalDate.of(1990, 1, 1), 2);
        mariage8.setHomme(homme4);
        mariage8.setFemme(femmes[7]);
        mariageService.save(mariage8);

        Mariage mariage9 = new Mariage(LocalDate.of(1992, 1, 1), 1);
        mariage9.setHomme(homme4);
        mariage9.setFemme(femmes[8]);
        mariageService.save(mariage9);

        Mariage mariage10 = new Mariage(LocalDate.of(1994, 1, 1), 3);
        mariage10.setHomme(homme4);
        mariage10.setFemme(femmes[9]);
        mariageService.save(mariage10);

        Mariage mariage11 = new Mariage(LocalDate.of(1996, 1, 1), 2);
        mariage11.setHomme(homme4);
        mariage11.setFemme(femmes[0]);
        mariageService.save(mariage11);
    }

    private static void afficherListeFemmes(FemmeService femmeService) {
        femmeService.findAll().forEach(f ->
                System.out.println("- " + f.getNom() + " " + f.getPrenom() + " (Née le " + f.getDateNaissance() + ")")
        );
    }

    private static void afficherFemmeLaPlusAgee(FemmeService femmeService) {
        Femme f = femmeService.getFemmeLaPlusAgee();
        if (f != null)
            System.out.println(" " + f.getNom() + " " + f.getPrenom() + " (Née le " + f.getDateNaissance() + ")");
    }

    private static void afficherEpousesHomme(HommeService hommeService) {
        hommeService.getEpousesEntreDates(1, LocalDate.of(1990, 1, 1), LocalDate.of(2000, 12, 31))
                .forEach(m ->
                        System.out.println("- " + m.getFemme().getNom() + " " + m.getFemme().getPrenom() +
                                " | Mariée le : " + m.getDateDebut() + " | Enfants : " + m.getNbrEnfant()));
    }

    private static void afficherNombreEnfantsFemme(FemmeService femmeService) {
        int nb = femmeService.getNombreEnfantsEntreDates(1, LocalDate.of(1990, 1, 1), LocalDate.of(2000, 12, 31));
        System.out.println("Nombre d'enfants : " + nb);
    }

    private static void afficherFemmesMariéesPlusieursFois(FemmeService femmeService) {
        femmeService.getFemmesMariéesAuMoinsDeuxFois()
                .forEach(f -> System.out.println("- " + f.getNom() + " " + f.getPrenom()));
    }

    private static void afficherHommesMariesQuatreFemmes(FemmeService femmeService) {
        long nb = femmeService.getNombreHommesMariesQuatreFemmesEntreDates(LocalDate.of(1990, 1, 1), LocalDate.of(2000, 12, 31));
        System.out.println("Nombre d'hommes mariés à 4 femmes : " + nb);
    }

    private static void afficherDetailsMariagesHomme(HommeService hommeService, int idHomme) {
        hommeService.afficherMariagesHomme(idHomme);
    }
}
