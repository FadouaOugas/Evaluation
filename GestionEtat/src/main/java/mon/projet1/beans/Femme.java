package mon.projet1.beans;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "femme")
@DiscriminatorValue("FEMME")
public class Femme extends Personne {
    
    @OneToMany(mappedBy = "femme", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Mariage> mariages = new ArrayList<>();
    
    // Constructeurs
    public Femme() {
        super();
    }
    
    public Femme(String nom, String prenom, String telephone, String adresse, java.time.LocalDate dateNaissance) {
        super(nom, prenom, telephone, adresse, dateNaissance);
    }
    
    // Getters et Setters
    public List<Mariage> getMariages() {
        return mariages;
    }
    
    public void setMariages(List<Mariage> mariages) {
        this.mariages = mariages;
    }
    
    public void addMariage(Mariage mariage) {
        mariages.add(mariage);
        mariage.setFemme(this);
    }
    
    public void removeMariage(Mariage mariage) {
        mariages.remove(mariage);
        mariage.setFemme(null);
    }
}
