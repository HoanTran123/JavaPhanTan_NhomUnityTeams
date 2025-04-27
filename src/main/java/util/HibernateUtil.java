package util;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.hibernate.SessionFactory;

public class HibernateUtil {
    private static final EntityManagerFactory emf;
    private static final SessionFactory sessionFactory;

    static {
        try {
            emf = Persistence.createEntityManagerFactory("mariadb");
            sessionFactory = emf.unwrap(SessionFactory.class);
        } catch (Throwable ex) {
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static void closeEntityManagerFactory() {
        if (emf != null && emf.isOpen()) {
            emf.close();
        }
    }
}