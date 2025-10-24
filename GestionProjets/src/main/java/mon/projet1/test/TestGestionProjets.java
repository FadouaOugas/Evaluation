package mon.projet1.test;

import mon.projet1.classes.*;
import mon.projet1.services.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class TestGestionProjets {
    
    private static EmployeService employeService = new EmployeService();
    private static ProjetService projetService = new ProjetService();
    private static TacheService tacheService = new TacheService();
    private static EmployeTacheService employeTacheService = new EmployeTacheService();
    
    public static void main(String[] args) {
        System.out.println("=== TEST DE L'APPLICATION DE GESTION DE PROJETS ===\n");
        
        try {
            // 1. Création des données de test
            creerDonneesTest();
            
            // 2. Tests des fonctionnalités demandées
            testAffichageProjetAvecTaches();
            testTachesParEmploye();
            testProjetsParEmploye();
            testTachesPrixSuperieurA();
            testTachesEntreDates();
            
            System.out.println("\n=== TOUS LES TESTS SONT TERMINÉS AVEC SUCCÈS ===");
            
        } catch (Exception e) {
            System.err.println("Erreur lors des tests : " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private static void creerDonneesTest() throws Exception {
        System.out.println("1. Création des données de test...");
        
        // Création des employés
        Employe emp1 = new Employe("Dupont", "Jean");
        Employe emp2 = new Employe("Martin", "Marie");
        Employe emp3 = new Employe("Durand", "Pierre");
        
        employeService.create(emp1);
        employeService.create(emp2);
        employeService.create(emp3);
        
        // Création des projets
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Projet projet1 = new Projet("Gestion de stock", sdf.parse("14/01/2013"), sdf.parse("30/06/2013"));
        Projet projet2 = new Projet("Système de facturation", sdf.parse("01/02/2013"), sdf.parse("31/08/2013"));
        
        projetService.create(projet1);
        projetService.create(projet2);
        
        // Création des tâches pour le projet 1
        Tache tache1 = new Tache("Analyse", sdf.parse("15/01/2013"), sdf.parse("25/01/2013"), 1500.0, projet1);
        tache1.setDateDebutReelle(sdf.parse("10/02/2013"));
        tache1.setDateFinReelle(sdf.parse("20/02/2013"));
        
        Tache tache2 = new Tache("Conception", sdf.parse("26/01/2013"), sdf.parse("15/02/2013"), 2000.0, projet1);
        tache2.setDateDebutReelle(sdf.parse("10/03/2013"));
        tache2.setDateFinReelle(sdf.parse("15/03/2013"));
        
        Tache tache3 = new Tache("Développement", sdf.parse("16/02/2013"), sdf.parse("30/03/2013"), 3000.0, projet1);
        tache3.setDateDebutReelle(sdf.parse("10/04/2013"));
        tache3.setDateFinReelle(sdf.parse("25/04/2013"));
        
        Tache tache4 = new Tache("Tests", sdf.parse("01/04/2013"), sdf.parse("15/04/2013"), 800.0, projet1);
        
        tacheService.create(tache1);
        tacheService.create(tache2);
        tacheService.create(tache3);
        tacheService.create(tache4);
        
        // Création des tâches pour le projet 2
        Tache tache5 = new Tache("Analyse fonctionnelle", sdf.parse("01/02/2013"), sdf.parse("15/02/2013"), 1200.0, projet2);
        Tache tache6 = new Tache("Développement module", sdf.parse("16/02/2013"), sdf.parse("30/04/2013"), 2500.0, projet2);
        
        tacheService.create(tache5);
        tacheService.create(tache6);
        
        // Association employés-tâches
        employeTacheService.create(new EmployeTache(emp1, tache1));
        employeTacheService.create(new EmployeTache(emp1, tache2));
        employeTacheService.create(new EmployeTache(emp2, tache3));
        employeTacheService.create(new EmployeTache(emp2, tache4));
        employeTacheService.create(new EmployeTache(emp3, tache5));
        employeTacheService.create(new EmployeTache(emp3, tache6));
        
        System.out.println("✓ Données de test créées avec succès\n");
    }
    
    private static void testAffichageProjetAvecTaches() {
        System.out.println("2. Test d'affichage d'un projet avec ses tâches réalisées :");
        
        Projet projet = projetService.findById(1);
        if (projet != null) {
            System.out.println("Projet : " + projet.getId() + 
                            "      Nom : " + projet.getNom() + 
                            "     Date début : " + new SimpleDateFormat("dd MMMM yyyy").format(projet.getDateDebut()));
            
            List<Tache> tachesRealisees = projetService.getTachesRealisees(projet.getId());
            System.out.println("Liste des tâches:");
            System.out.println("Num Nom            Date Début Réelle   Date Fin Réelle");
            
            for (Tache tache : tachesRealisees) {
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                System.out.printf("%-3d %-15s %-18s %s%n", 
                    tache.getId(), 
                    tache.getNom(),
                    sdf.format(tache.getDateDebutReelle()),
                    sdf.format(tache.getDateFinReelle()));
            }
        }
        System.out.println();
    }
    
    private static void testTachesParEmploye() {
        System.out.println("3. Test des tâches réalisées par un employé :");
        
        List<Tache> taches = employeService.getTachesParEmploye(1);
        System.out.println("Tâches réalisées par l'employé ID 1 :");
        for (Tache tache : taches) {
            System.out.println("- " + tache.getNom() + " (Projet: " + tache.getProjet().getNom() + ")");
        }
        System.out.println();
    }
    
    private static void testProjetsParEmploye() {
        System.out.println("4. Test des projets gérés par un employé :");
        
        List<Projet> projets = employeService.getProjetsParEmploye(1);
        System.out.println("Projets gérés par l'employé ID 1 :");
        for (Projet projet : projets) {
            System.out.println("- " + projet.getNom());
        }
        System.out.println();
    }
    
    private static void testTachesPrixSuperieurA() {
        System.out.println("5. Test des tâches dont le prix est supérieur à 1000 DH :");
        
        List<Tache> taches = tacheService.getTachesPrixSuperieurA(1000.0);
        System.out.println("Tâches avec prix > 1000 DH :");
        for (Tache tache : taches) {
            System.out.println("- " + tache.getNom() + " : " + tache.getPrix() + " DH");
        }
        System.out.println();
    }
    
    private static void testTachesEntreDates() {
        System.out.println("6. Test des tâches réalisées entre deux dates :");
        
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            Date debut = sdf.parse("01/02/2013");
            Date fin = sdf.parse("31/03/2013");
            
            List<Tache> taches = tacheService.getTachesEntreDates(debut, fin);
            System.out.println("Tâches réalisées entre " + sdf.format(debut) + " et " + sdf.format(fin) + " :");
            for (Tache tache : taches) {
                if (tache.getDateDebutReelle() != null && tache.getDateFinReelle() != null) {
                    System.out.println("- " + tache.getNom() + 
                                    " (Début: " + sdf.format(tache.getDateDebutReelle()) + 
                                    ", Fin: " + sdf.format(tache.getDateFinReelle()) + ")");
                }
            }
        } catch (Exception e) {
            System.err.println("Erreur lors du test des dates : " + e.getMessage());
        }
        System.out.println();
    }
}


