package mon.projet1.classes;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "employe_tache")
public class EmployeTache implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name="employe_id")
    private Employe employe;

    @ManyToOne
    @JoinColumn(name="tache_id")
    private Tache tache;

    // Constructeur par défaut obligatoire pour Hibernate
    public EmployeTache() {}

    // Constructeur que tu peux utiliser dans ton TestApp
    public EmployeTache(Employe employe, Tache tache) {
        this.employe = employe;
        this.tache = tache;
    }

    // getters et setters
    public int getId() { return id; }
    public Employe getEmploye() { return employe; }
    public void setEmploye(Employe employe) { this.employe = employe; }
    public Tache getTache() { return tache; }
    public void setTache(Tache tache) { this.tache = tache; }
}
