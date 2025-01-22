package dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import lombok.AllArgsConstructor;
import entity.KhachHang;

import java.util.List;

@AllArgsConstructor
public class KhachHang_dao {
    private EntityManager em;

    public boolean save(KhachHang khachHang) {
        EntityTransaction tr = em.getTransaction();
        try {
            tr.begin();
            em.persist(khachHang);
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

    public boolean update(KhachHang khachHang) {
        EntityTransaction tr = em.getTransaction();
        try {
            tr.begin();
            em.merge(khachHang);
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

    public boolean delete(String idKH) {
        EntityTransaction tr = em.getTransaction();
        try {
            tr.begin();
            KhachHang khachHang = em.find(KhachHang.class, idKH);
            if (khachHang != null) {
                em.remove(khachHang);
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

    public List<KhachHang> findAll() {
        String query = "SELECT kh FROM KhachHang kh";
        return em.createQuery(query, KhachHang.class).getResultList();
    }

    public KhachHang findById(String idKH) {
        return em.find(KhachHang.class, idKH);
    }

    public List<KhachHang> findByName(String hoTen) {
        String query = "SELECT kh FROM KhachHang kh WHERE kh.hoTen LIKE :hoTen";
        return em.createQuery(query, KhachHang.class)
                .setParameter("hoTen", "%" + hoTen + "%")
                .getResultList();
    }
} 