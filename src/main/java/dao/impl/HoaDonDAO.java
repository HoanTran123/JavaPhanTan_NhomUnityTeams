package dao.impl;

import dao.remote.IHoaDonDAO;
import entity.HoaDon;
import entity.KhachHang;
import entity.NhanVien;
import jakarta.persistence.EntityManager;
import util.HibernateUtil;

import java.rmi.RemoteException;
import java.util.List;

public class HoaDonDAO extends GenericDAO<HoaDon> implements IHoaDonDAO {

    private EntityManager entityManager;

    public HoaDonDAO() throws RemoteException {
        super();
        this.entityManager = HibernateUtil.getEntityManager();
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

    public KhachHang getKhachHangById(String id) throws RemoteException {
        return findOne("SELECT k FROM KhachHang k WHERE k.idKH = ?1", KhachHang.class, id);
    }

    private KhachHang findOne(String query, Class<KhachHang> khachHangClass, String id) {
        return entityManager.createQuery(query, khachHangClass)
                .setParameter(1, id)
                .getSingleResult();
    }
    @Override
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
        super.setEntityManager(entityManager);
    }

    public EntityManager getEntityManager() {
        return this.entityManager;
    }

    public NhanVien getNhanVienById(String maNV) {
        return entityManager.createQuery("SELECT n FROM NhanVien n WHERE n.idNV = ?1", NhanVien.class)
                .setParameter(1, maNV)
                .getSingleResult();
    }
}