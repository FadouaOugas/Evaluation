package mon.projet1.classes;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "tache")
@NamedQueries({
        @NamedQuery(name="Tache.prixSuperieurA", query="FROM Tache t WHERE t.prix > :prix"),
        @NamedQuery(name="Tache.entreDates", query="FROM Tache t WHERE t.dateDebutReelle >= :debut AND t.dateFinReelle <= :fin")
})
public class Tache {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String nom;

    @Temporal(TemporalType.DATE)
    private Date dateDebutPlanifiee;

    @Temporal(TemporalType.DATE)
    private Date dateFinPlanifiee;

    @Temporal(TemporalType.DATE)
    private Date dateDebutReelle;

    @Temporal(TemporalType.DATE)
    private Date dateFinReelle;

    private double prix;

    @ManyToOne
    @JoinColumn(name="projet_id")
    private Projet projet;

    public Tache() {}

    public Tache(String nom, Date dateDebutPlanifiee, Date dateFinPlanifiee, double prix, Projet projet) {
        this.nom = nom;
        this.dateDebutPlanifiee = dateDebutPlanifiee;
        this.dateFinPlanifiee = dateFinPlanifiee;
        this.prix = prix;
        this.projet = projet;
    }

    // getters et setters
    public int getId() { return id; }
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }
    public Date getDateDebutPlanifiee() { return dateDebutPlanifiee; }
    public void setDateDebutPlanifiee(Date dateDebutPlanifiee) { this.dateDebutPlanifiee = dateDebutPlanifiee; }
    public Date getDateFinPlanifiee() { return dateFinPlanifiee; }
    public void setDateFinPlanifiee(Date dateFinPlanifiee) { this.dateFinPlanifiee = dateFinPlanifiee; }
    public Date getDateDebutReelle() { return dateDebutReelle; }
    public void setDateDebutReelle(Date dateDebutReelle) { this.dateDebutReelle = dateDebutReelle; }
    public Date getDateFinReelle() { return dateFinReelle; }
    public void setDateFinReelle(Date dateFinReelle) { this.dateFinReelle = dateFinReelle; }
    public double getPrix() { return prix; }
    public void setPrix(double prix) { this.prix = prix; }
    public Projet getProjet() { return projet; }
    public void setProjet(Projet projet) { this.projet = projet; }
}
