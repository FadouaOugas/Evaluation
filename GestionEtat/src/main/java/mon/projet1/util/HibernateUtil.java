package mon.projet1.util;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
    
    private static SessionFactory sessionFactory;
    
    static {
        try {
            // Configuration Hibernate à partir du fichier hibernate.cfg.xml
            Configuration configuration = new Configuration();
            configuration.configure("hibernate.cfg.xml");
            
            sessionFactory = configuration.buildSessionFactory();
            
        } catch (Exception e) {
            System.err.println("Erreur lors de l'initialisation de Hibernate: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
    
    public static void closeSessionFactory() {
        if (sessionFactory != null && !sessionFactory.isClosed()) {
            sessionFactory.close();
        }
    }
}
