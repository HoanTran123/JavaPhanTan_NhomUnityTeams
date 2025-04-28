package dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import lombok.AllArgsConstructor;
import entity.Thuoc;

import java.util.List;

@AllArgsConstructor
public class Thuoc_dao {

    private EntityManager em;

    public boolean save(Thuoc thuoc) {
        EntityTransaction tr = em.getTransaction();
        try {
            tr.begin();
            em.persist(thuoc);
            tr.commit();
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            if (tr.isActive()) {
                tr.rollback();
            }
        }
        return false;
    }

    public boolean update(Thuoc thuoc) {
        EntityTransaction tr = em.getTransaction();
        try {
            tr.begin();
            em.merge(thuoc);
            tr.commit();
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            if (tr.isActive()) {
                tr.rollback();
            }
        }
        return false;
    }

    public boolean delete(String idThuoc) {
        EntityTransaction tr = em.getTransaction();
        try {
            tr.begin();
            Thuoc thuoc = em.find(Thuoc.class, idThuoc);
            if (thuoc != null) {
                em.remove(thuoc);
                tr.commit();
                return true;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            if (tr.isActive()) {
                tr.rollback();
            }
        }
        return false;
    }

    public List<Thuoc> findAll() {
        String query = "SELECT thuoc FROM Thuoc thuoc";
        return em.createQuery(query, Thuoc.class).getResultList();
    }

    public Thuoc findById(String maThuoc) {
        return em.find(Thuoc.class, maThuoc);
    }

    public List<Thuoc> findByName(String hoTen) {
        String query = "SELECT thuoc FROM Thuoc thuoc WHERE thuoc.hoTen LIKE :hoTen";
        return em.createQuery(query, Thuoc.class)
                .setParameter("hoTen", "%" + hoTen + "%")
                .getResultList();
    }
}
