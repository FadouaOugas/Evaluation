package mon.projet1.classes;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "employe")
public class Employe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String nom;
    private String prenom;

    @OneToMany(mappedBy = "employe")
    private List<EmployeTache> employeTaches;

    public Employe() {}

    public Employe(String nom, String prenom) {
        this.nom = nom;
        this.prenom = prenom;
    }

    // getters et setters
    public int getId() { return id; }
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }
    public String getPrenom() { return prenom; }
    public void setPrenom(String prenom) { this.prenom = prenom; }
    public List<EmployeTache> getEmployeTaches() { return employeTaches; }
    public void setEmployeTaches(List<EmployeTache> employeTaches) { this.employeTaches = employeTaches; }
}
