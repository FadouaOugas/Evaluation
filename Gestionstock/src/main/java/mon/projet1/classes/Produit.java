package mon.projet1.classes;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "produit")
@NamedQuery(
        name = "Produit.findByPrixSuperieur",
        query = "SELECT p FROM Produit p WHERE p.prix > :prix"
)
public class Produit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String reference;
    private float prix;

    @ManyToOne
    @JoinColumn(name = "categorie_id")
    private Categorie categorie;

    @OneToMany(mappedBy = "produit")
    private List<LigneCommande> lignesCommande;

    // Constructeurs
    public Produit() {}

    public Produit(String reference, float prix) {
        this.reference = reference;
        this.prix = prix;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public float getPrix() {
        return prix;
    }

    public void setPrix(float prix) {
        this.prix = prix;
    }

    public Categorie getCategorie() {
        return categorie;
    }

    public void setCategorie(Categorie categorie) {
        this.categorie = categorie;
    }

    public List<LigneCommande> getLignesCommande() {
        return lignesCommande;
    }

    public void setLignesCommande(List<LigneCommande> lignesCommande) {
        this.lignesCommande = lignesCommande;
    }
}