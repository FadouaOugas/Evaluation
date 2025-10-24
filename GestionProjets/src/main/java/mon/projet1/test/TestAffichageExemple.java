package mon.projet1.test;

import mon.projet1.classes.*;
import mon.projet1.services.*;
import java.text.SimpleDateFormat;
import java.util.List;

public class TestAffichageExemple {
    public static void main(String[] args) throws Exception {
        System.out.println("=== TEST D'AFFICHAGE EXEMPLE ===");
        
        // Créer les données exactes de l'exemple
        creerDonneesExemple();
        
        // Afficher comme dans l'exemple
        afficherProjetAvecTaches();
        
        System.out.println("\n=== FIN DU TEST ===");
    }
    
    private static void creerDonneesExemple() throws Exception {
        System.out.println("Création des données de l'exemple...");
        
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        ProjetService projetService = new ProjetService();
        TacheService tacheService = new TacheService();
        EmployeService employeService = new EmployeService();
        EmployeTacheService employeTacheService = new EmployeTacheService();
        
        // Créer un employé
        Employe employe = new Employe("Dupont", "Jean");
        employeService.create(employe);
        
        // Créer le projet "Gestion de stock" avec date 14 Janvier 2013
        Projet projet = new Projet("Gestion de stock", sdf.parse("14/01/2013"), sdf.parse("30/06/2013"));
        projetService.create(projet);
        
        // Créer les tâches avec les dates exactes de l'exemple
        Tache tache1 = new Tache("Analyse", sdf.parse("15/01/2013"), sdf.parse("25/01/2013"), 1500.0, projet);
        tache1.setDateDebutReelle(sdf.parse("10/02/2013"));
        tache1.setDateFinReelle(sdf.parse("20/02/2013"));
        
        Tache tache2 = new Tache("Conception", sdf.parse("26/01/2013"), sdf.parse("15/02/2013"), 2000.0, projet);
        tache2.setDateDebutReelle(sdf.parse("10/03/2013"));
        tache2.setDateFinReelle(sdf.parse("15/03/2013"));
        
        Tache tache3 = new Tache("Développement", sdf.parse("16/02/2013"), sdf.parse("30/03/2013"), 3000.0, projet);
        tache3.setDateDebutReelle(sdf.parse("10/04/2013"));
        tache3.setDateFinReelle(sdf.parse("25/04/2013"));
        
        tacheService.create(tache1);
        tacheService.create(tache2);
        tacheService.create(tache3);
        
        // Associer employé et tâches
        employeTacheService.create(new EmployeTache(employe, tache1));
        employeTacheService.create(new EmployeTache(employe, tache2));
        employeTacheService.create(new EmployeTache(employe, tache3));
        
        System.out.println("✓ Données créées avec succès");
    }
    
    private static void afficherProjetAvecTaches() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat sdfLong = new SimpleDateFormat("dd MMMM yyyy");
        ProjetService projetService = new ProjetService();
        
        // Récupérer le projet créé
        Projet projet = projetService.findById(1);
        
        if (projet != null) {
            System.out.println("\nProjet : " + projet.getId() +
                    "      Nom : " + projet.getNom() +
                    "     Date début : " + sdfLong.format(projet.getDateDebut()));
            System.out.println("Liste des tâches:");
            System.out.printf("%-3s %-15s %-18s %-18s%n", "Num", "Nom", "Date Début Réelle", "Date Fin Réelle");
            
            // Récupérer toutes les tâches réalisées pour ce projet
            List<Tache> taches = projetService.getTachesRealisees(projet.getId());
            
            for (Tache t : taches) {
                String debutReelle = t.getDateDebutReelle() != null ? sdf.format(t.getDateDebutReelle()) : "-";
                String finReelle = t.getDateFinReelle() != null ? sdf.format(t.getDateFinReelle()) : "-";
                
                System.out.printf("%-3d %-15s %-18s %-18s%n", t.getId(), t.getNom(), debutReelle, finReelle);
            }
        } else {
            System.out.println("Projet non trouvé !");
        }
    }
}
