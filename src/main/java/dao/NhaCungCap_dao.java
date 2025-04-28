package dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import lombok.AllArgsConstructor;
import entity.NhaCungCap;

import java.util.List;

@AllArgsConstructor
public class NhaCungCap_dao {

    private EntityManager em;

    public boolean save(NhaCungCap nhaCungCap) {
        EntityTransaction tr = em.getTransaction();
        try {
            tr.begin();
            em.persist(nhaCungCap);
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

    public boolean update(NhaCungCap nhaCungCap) {
        EntityTransaction tr = em.getTransaction();
        try {
            tr.begin();
            em.merge(nhaCungCap);
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

    public boolean delete(String idNCC) {
        EntityTransaction tr = em.getTransaction();
        try {
            tr.begin();
            NhaCungCap nhaCungCap = em.find(NhaCungCap.class, idNCC);
            if (nhaCungCap != null) {
                em.remove(nhaCungCap);
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

    public List<NhaCungCap> findAll() {
        String query = "SELECT ncc FROM NhaCungCap ncc";
        return em.createQuery(query, NhaCungCap.class).getResultList();
    }

    public NhaCungCap findById(String maNCC) {
        return em.find(NhaCungCap.class, maNCC);
    }

    public List<NhaCungCap> findByName(String hoTen) {
        String query = "SELECT ncc FROM NhaCungCap ncc WHERE ncc.hoTen LIKE :hoTen";
        return em.createQuery(query, NhaCungCap.class)
                .setParameter("hoTen", "%" + hoTen + "%")
                .getResultList();
    }
}