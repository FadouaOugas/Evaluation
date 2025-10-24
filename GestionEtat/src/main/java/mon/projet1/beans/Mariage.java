package mon.projet1.beans;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "mariage")
public class Mariage {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    @Column(name = "date_debut")
    private LocalDate dateDebut;
    
    @Column(name = "date_fin")
    private LocalDate dateFin;
    
    @Column(name = "nbr_enfant")
    private int nbrEnfant;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "homme_id")
    private Homme homme;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "femme_id")
    private Femme femme;
    
    // Constructeurs
    public Mariage() {}
    
    public Mariage(LocalDate dateDebut, LocalDate dateFin, int nbrEnfant) {
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.nbrEnfant = nbrEnfant;
    }
    
    public Mariage(LocalDate dateDebut, int nbrEnfant) {
        this.dateDebut = dateDebut;
        this.nbrEnfant = nbrEnfant;
    }
    
    // Getters et Setters
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public LocalDate getDateDebut() {
        return dateDebut;
    }
    
    public void setDateDebut(LocalDate dateDebut) {
        this.dateDebut = dateDebut;
    }
    
    public LocalDate getDateFin() {
        return dateFin;
    }
    
    public void setDateFin(LocalDate dateFin) {
        this.dateFin = dateFin;
    }
    
    public int getNbrEnfant() {
        return nbrEnfant;
    }
    
    public void setNbrEnfant(int nbrEnfant) {
        this.nbrEnfant = nbrEnfant;
    }
    
    public Homme getHomme() {
        return homme;
    }
    
    public void setHomme(Homme homme) {
        this.homme = homme;
    }
    
    public Femme getFemme() {
        return femme;
    }
    
    public void setFemme(Femme femme) {
        this.femme = femme;
    }
    
    public boolean estEnCours() {
        return dateFin == null;
    }
    
    @Override
    public String toString() {
        return "Mariage{" +
                "id=" + id +
                ", dateDebut=" + dateDebut +
                ", dateFin=" + dateFin +
                ", nbrEnfant=" + nbrEnfant +
                '}';
    }
}
