package mon.projet1.test;

import mon.projet1.classes.*;
import mon.projet1.services.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class Test {
    public static void main(String[] args) {
        // Initialiser les services
        CategorieService cs = new CategorieService();
        ProduitService ps = new ProduitService();
        CommandeService cms = new CommandeService();
        LigneCommandeService lcs = new LigneCommandeService();

        try {
            // 1. Créer des catégories
            Categorie c1 = new Categorie("INFO", "Informatique");
            Categorie c2 = new Categorie("ELECT", "Électronique");
            cs.create(c1);
            cs.create(c2);

            // 2. Créer des produits
            Produit p1 = new Produit("ES12", 120f);
            p1.setCategorie(c1);
            ps.create(p1);

            Produit p2 = new Produit("ZR85", 100f);
            p2.setCategorie(c1);
            ps.create(p2);

            Produit p3 = new Produit("EE85", 200f);
            p3.setCategorie(c2);
            ps.create(p3);

            // 3. Créer une commande
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            Date date = sdf.parse("27/03/2024");
            Commande cmd = new Commande(date);
            cms.create(cmd);

            // 4. Créer des lignes de commande
            LigneCommande lcp1 = new LigneCommande(7, p1, cmd);
            LigneCommande lcp2 = new LigneCommande(14, p2, cmd);
            LigneCommande lcp3 = new LigneCommande(5, p3, cmd);
            lcs.create(lcp1);
            lcs.create(lcp2);
            lcs.create(lcp3);

            // 5. Afficher la commande complète avec ses produits
            System.out.println("\n=== Détail de la commande ===");
            cms.afficherCommande(cmd.getId());

            // 6. Afficher les produits par catégorie
            System.out.println("\n=== Produits par catégorie Informatique ===");
            List<Produit> produits = ps.findByCategorie(c1);
            for (Produit p : produits) {
                System.out.println(p.getReference() + " - " + p.getPrix() + " DH");
            }

            // 7. Afficher les produits avec prix > 100 DH
            System.out.println("\n=== Produits avec prix > 100 DH ===");
            List<Produit> produitsChers = ps.findByPrixSuperieur(100f);
            for (Produit p : produitsChers) {
                System.out.println(p.getReference() + " - " + p.getPrix() + " DH");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
