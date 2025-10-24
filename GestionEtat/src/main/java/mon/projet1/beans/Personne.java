package mon.projet1.beans;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "personne")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "type_personne")
public abstract class Personne {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected int id;
    
    @Column(name = "nom")
    protected String nom;
    
    @Column(name = "prenom")
    protected String prenom;
    
    @Column(name = "telephone")
    protected String telephone;
    
    @Column(name = "adresse")
    protected String adresse;
    
    @Column(name = "date_naissance")
    protected LocalDate dateNaissance;
    
    // Constructeurs
    public Personne() {}
    
    public Personne(String nom, String prenom, String telephone, String adresse, LocalDate dateNaissance) {
        this.nom = nom;
        this.prenom = prenom;
        this.telephone = telephone;
        this.adresse = adresse;
        this.dateNaissance = dateNaissance;
    }
    
    // Getters et Setters
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public String getNom() {
        return nom;
    }
    
    public void setNom(String nom) {
        this.nom = nom;
    }
    
    public String getPrenom() {
        return prenom;
    }
    
    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }
    
    public String getTelephone() {
        return telephone;
    }
    
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }
    
    public String getAdresse() {
        return adresse;
    }
    
    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }
    
    public LocalDate getDateNaissance() {
        return dateNaissance;
    }
    
    public void setDateNaissance(LocalDate dateNaissance) {
        this.dateNaissance = dateNaissance;
    }
    
    @Override
    public String toString() {
        return nom + " " + prenom;
    }
}
