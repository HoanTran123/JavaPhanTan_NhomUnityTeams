package dao.impl;

import dao.remote.IHoaDonDAO;
import entity.HoaDon;
import entity.KhachHang;
import jakarta.persistence.EntityManager;
import util.HibernateUtil;

import java.rmi.RemoteException;
import java.util.List;
import java.util.logging.Handler;

public class HoaDonDAO extends GenericDAO<HoaDon> implements IHoaDonDAO {

    public HoaDonDAO() throws RemoteException {
        super();
        setEntityManager(HibernateUtil.getEntityManager());
    }

    @Override
    public List<HoaDon> getAll() throws RemoteException {
        return findMany("SELECT h FROM HoaDon h", HoaDon.class);
    }

    @Override
    public HoaDon getById(String id) throws RemoteException {
        return findOne("SELECT h FROM HoaDon h WHERE h.idHD = ?1", HoaDon.class, id);
    }

    public List<HoaDon> getByCustomerId(String customerId) throws RemoteException {
        return findMany("SELECT h FROM HoaDon h WHERE h.khachHang.idKH = ?1", HoaDon.class, customerId);
    }

    public List<HoaDon> getByDateRange(String startDate, String endDate) throws RemoteException {
        return findMany("SELECT h FROM HoaDon h WHERE h.thoiGian BETWEEN ?1 AND ?2", HoaDon.class, startDate, endDate);
    }

    @Override
    public boolean add(HoaDon hoaDon) {
        return super.add(hoaDon);
    }

    @Override
    public boolean update(HoaDon hoaDon) {
        return super.update(hoaDon);
    }

    @Override
    public boolean delete(HoaDon hoaDon) {
        return super.delete(hoaDon);
    }

    public int countTotal() throws RemoteException {
        return count("SELECT COUNT(h) FROM HoaDon h");
    }

    public int countByCustomerId(String customerId) throws RemoteException {
        return count("SELECT COUNT(h) FROM HoaDon h WHERE h.khachHang.idKH = ?1", customerId);
    }

    public KhachHang getKhachHangById(String text) {
        return findOne("SELECT k FROM KhachHang k WHERE k.idKH = ?1", KhachHang.class, text);
    }

    private KhachHang findOne(String query, Class<KhachHang> khachHangClass, String text) {
        // Implement the logic to execute the query and return a KhachHang object
        EntityManager entityManager = HibernateUtil.getEntityManager();
        return entityManager.createQuery(query, khachHangClass)
                .setParameter(1, text)
                .getSingleResult();
    }

    public EntityManager getEntityManager() {
        return HibernateUtil.getEntityManager();
    }
}