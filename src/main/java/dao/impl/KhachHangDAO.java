package dao.impl;

import dao.remote.IKhachHangDAO;
import entity.KhachHang;
import jakarta.persistence.EntityManager;

import java.rmi.RemoteException;
import java.util.*;

public class KhachHangDAO extends GenericDAO<KhachHang> implements IKhachHangDAO {

    public KhachHangDAO() throws RemoteException {
        super();
    }

    @Override
    public List<KhachHang> getAll() throws RemoteException {
        return findMany("SELECT k FROM KhachHang k", KhachHang.class);
    }

    @Override
    public KhachHang getBySdt(String sdt) throws RemoteException {
        return findOne("SELECT k FROM KhachHang k WHERE k.sdt = ?1", KhachHang.class, sdt);
    }

    @Override
    public List<KhachHang> getByName(String name) throws RemoteException {
        return findMany("SELECT k FROM KhachHang k WHERE k.hoTen LIKE ?1", KhachHang.class, "%" + name + "%");
    }

    @Override
    public List<KhachHang> getAllWithPagination(int offset, int limit) throws RemoteException {
        return findManyWithPagination("SELECT k FROM KhachHang k ORDER BY k.idKH", KhachHang.class, null, offset, limit);
    }

    @Override
    public List<KhachHang> getAllWithPaginationBySdt(String sdt, int offset, int limit) throws RemoteException {
        Map<String, Object> params = new HashMap<>();
        params.put("sdt", sdt + "%");
        return findManyWithPagination("SELECT k FROM KhachHang k WHERE k.sdt LIKE :sdt ORDER BY k.idKH", KhachHang.class, params, offset, limit);
    }

    @Override
    public List<KhachHang> getAllWithPaginationByName(String name, int offset, int limit) throws RemoteException {
        Map<String, Object> params = new HashMap<>();
        params.put("name", "%" + name + "%");
        return findManyWithPagination("SELECT k FROM KhachHang k WHERE k.hoTen LIKE :name ORDER BY k.idKH", KhachHang.class, params, offset, limit);
    }

    @Override
    public List<KhachHang> getKhachHangByNgayThamGia(Date date) throws RemoteException {
        return findMany("SELECT k FROM KhachHang k WHERE k.ngayThamGia = ?1", KhachHang.class, date);
    }

    @Override
    public int countTotal() throws RemoteException {
        return count("SELECT COUNT(k) FROM KhachHang k");
    }

    @Override
    public int countTotalBySdt(String sdt) throws RemoteException {
        return count("SELECT COUNT(k) FROM KhachHang k WHERE k.sdt LIKE ?1", sdt + "%");
    }

    @Override
    public int countTotalByName(String name) throws RemoteException {
        return count("SELECT COUNT(k) FROM KhachHang k WHERE k.hoTen LIKE ?1", "%" + name + "%");
    }

    @Override
    public KhachHang getById(String id) throws RemoteException {
        return findOne("SELECT k FROM KhachHang k WHERE k.idKH = ?1", KhachHang.class, id);
    }
    @Override
    public List<KhachHang> getAllKhachHang() {
        KhachHangDAO dao;
        try {
            dao = new KhachHangDAO();
            return dao.getAll();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    @Override
    public void setEntityManager(EntityManager entityManager) {
        super.setEntityManager(entityManager);
    }


    public void deleteRelatedRecords(String idKH) {
        entityManager.getTransaction().begin();
        entityManager.createQuery("DELETE FROM HoaDon h WHERE h.khachHang.idKH = :idKH")
                .setParameter("idKH", idKH)
                .executeUpdate();
        entityManager.createQuery("DELETE FROM PhieuDatHang p WHERE p.khachHang.idKH = :idKH")
                .setParameter("idKH", idKH)
                .executeUpdate();
        entityManager.createQuery("DELETE FROM DoiTra d WHERE d.khachHang.idKH = :idKH")
                .setParameter("idKH", idKH)
                .executeUpdate();
        entityManager.getTransaction().commit();
    }

}