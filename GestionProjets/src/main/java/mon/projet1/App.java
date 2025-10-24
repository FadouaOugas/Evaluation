package mon.projet1;

import mon.projet1.test.TestGestionProjets;

/**
 * Application principale de gestion de projets
 * 
 * Cette application permet de :
 * - Gérer les employés, projets et tâches
 * - Suivre le temps passé sur les projets
 * - Calculer les coûts globaux
 * 
 * Pour exécuter les tests, lancez TestGestionProjets.main()
 */
public class App {
    public static void main(String[] args) {
        System.out.println("=== APPLICATION DE GESTION DE PROJETS ===");
        System.out.println("Lancement des tests...\n");
        
        try {
            // Lancer les tests complets
            TestGestionProjets.main(args);
        } catch (Exception e) {
            System.err.println("Erreur lors de l'exécution : " + e.getMessage());
            e.printStackTrace();
        }
    }
}