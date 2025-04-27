package util;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import view.MainFrame;

public class EntityManagerUtil {
    private static final EntityManagerFactory entityManagerFactory;

    static {
        // Use the correct persistence unit name from persistence.xml
        entityManagerFactory = Persistence.createEntityManagerFactory("mariadb");
    }

    public static EntityManager getEntityManager() {
        return entityManagerFactory.createEntityManager();
    }

    public static void close() {
        if (entityManagerFactory != null) {
            entityManagerFactory.close();
        }
    }

    public static void main(String[] args) {
        try {
            MainFrame frame = new MainFrame();
            frame.setVisible(true);
        } finally {
            util.EntityManagerUtil.close(); // Close the EntityManagerFactory
        }
    }

}