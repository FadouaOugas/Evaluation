package mon.projet1.beans;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "homme")
@DiscriminatorValue("HOMME")
public class Homme extends Personne {
    
    @OneToMany(mappedBy = "homme", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Mariage> mariages = new ArrayList<>();
    
    // Constructeurs
    public Homme() {
        super();
    }
    
    public Homme(String nom, String prenom, String telephone, String adresse, java.time.LocalDate dateNaissance) {
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
        mariage.setHomme(this);
    }
    
    public void removeMariage(Mariage mariage) {
        mariages.remove(mariage);
        mariage.setHomme(null);
    }
}
