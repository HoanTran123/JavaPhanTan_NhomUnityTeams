package dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import lombok.AllArgsConstructor;
import entity.NhanVien;

import java.util.List;

@AllArgsConstructor
public class NhanVien_dao {

    private EntityManager em;

    public boolean save(NhanVien nhanVien) {
        EntityTransaction tr = em.getTransaction();
        try {
            tr.begin();
            em.persist(nhanVien);
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

    public boolean update(NhanVien nhanVien) {
        EntityTransaction tr = em.getTransaction();
        try {
            tr.begin();
            em.merge(nhanVien);
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

    public boolean delete(String idNV) {
        EntityTransaction tr = em.getTransaction();
        try {
            tr.begin();
            NhanVien nhanVien = em.find(NhanVien.class, idNV);
            if (nhanVien != null) {
                em.remove(nhanVien);
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

    public List<NhanVien> findAll() {
        String query = "SELECT nv FROM NhanVien nv";
        return em.createQuery(query, NhanVien.class).getResultList();
    }

    public NhanVien findById(String maNV) {
        return em.find(NhanVien.class, maNV);
    }

    public List<NhanVien> findByName(String hoTen) {
        String query = "SELECT nv FROM NhanVien nv WHERE nv.hoTen LIKE :hoTen";
        return em.createQuery(query, NhanVien.class)
                .setParameter("hoTen", "%" + hoTen + "%")
                .getResultList();
    }
}