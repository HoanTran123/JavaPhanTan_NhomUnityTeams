package dao.impl;

import jakarta.persistence.EntityManager;
import util.HibernateUtil;

import java.util.List;

public class ThongKeDAO {

    public List<Object[]> getRevenueByMonth(int year) {
        EntityManager entityManager = null;
        try {
            entityManager = HibernateUtil.getEntityManager();
            String query = "SELECT MONTH(h.thoiGian) AS month, SUM(h.tongTien) AS revenue " +
                    "FROM HoaDon h " +
                    "WHERE YEAR(h.thoiGian) = :year " +
                    "GROUP BY MONTH(h.thoiGian) " +
                    "ORDER BY MONTH(h.thoiGian)";
            return entityManager.createQuery(query, Object[].class)
                    .setParameter("year", year)
                    .getResultList();
        } finally {
            if (entityManager != null && entityManager.isOpen()) {
                entityManager.close();
            }
        }
    }

    public List<Object[]> getTopSellingProducts(int limit) {
        EntityManager entityManager = null;
        try {
            entityManager = HibernateUtil.getEntityManager();
            String query = "SELECT ct.thuoc.tenThuoc, SUM(ct.soLuong) AS totalSold " +
                    "FROM ChiTietHoaDon ct " +
                    "GROUP BY ct.thuoc.idThuoc, ct.thuoc.tenThuoc " +
                    "ORDER BY totalSold DESC";
            return entityManager.createQuery(query, Object[].class)
                    .setMaxResults(limit)
                    .getResultList();
        } finally {
            if (entityManager != null && entityManager.isOpen()) {
                entityManager.close();
            }
        }
    }

    public List<Object[]> getEmployeePerformance(int year) {
        EntityManager entityManager = null;
        try {
            entityManager = HibernateUtil.getEntityManager();
            String query = "SELECT h.nhanVien.hoTen, COUNT(h.idHD) AS totalBills, SUM(h.tongTien) AS totalRevenue " +
                    "FROM HoaDon h " +
                    "WHERE YEAR(h.thoiGian) = :year " +
                    "GROUP BY h.nhanVien.idNV, h.nhanVien.hoTen " +
                    "ORDER BY totalRevenue DESC";
            return entityManager.createQuery(query, Object[].class)
                    .setParameter("year", year)
                    .getResultList();
        } finally {
            if (entityManager != null && entityManager.isOpen()) {
                entityManager.close();
            }
        }
    }
}