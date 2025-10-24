package mon.projet1.service;

import mon.projet1.beans.Mariage;
import mon.projet1.dao.GenericDao;

public class MariageService extends GenericDao<Mariage> {
    
    public MariageService() {
        super(Mariage.class);
    }
}
