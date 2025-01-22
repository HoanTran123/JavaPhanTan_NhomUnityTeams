import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;

public class Runner {
    public static void main(String[] args) {
<<<<<<< HEAD
        System.out.println("Hello World!");
    }
}
=======
        EntityManager em = Persistence.createEntityManagerFactory("mariadb").createEntityManager();
        em.getTransaction().begin();
        em.getTransaction().commit();
        em.close();
    }
}
>>>>>>> origin/Hoan
