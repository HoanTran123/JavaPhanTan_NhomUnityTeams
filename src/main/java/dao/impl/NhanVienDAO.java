package dao.impl;

import dao.remote.INhanVienDAO;
import entity.NhanVien;

import java.rmi.RemoteException;
import java.util.List;

public class NhanVienDAO extends GenericDAO<NhanVien> implements INhanVienDAO {

    public NhanVienDAO() throws RemoteException {
        super();
    }

    @Override
    public List<NhanVien> getAll() throws RemoteException {
        return findMany("SELECT n FROM NhanVien n", NhanVien.class);
    }

    @Override
    public NhanVien getById(String id) throws RemoteException {
        return findOne("SELECT n FROM NhanVien n WHERE n.idNV = ?1", NhanVien.class, id);
    }

    @Override
    public List<NhanVien> getByName(String name) throws RemoteException {
        return findMany("SELECT n FROM NhanVien n WHERE n.hoTen LIKE ?1", NhanVien.class, "%" + name + "%");
    }

    @Override
    public boolean add(NhanVien nhanVien) {
        return super.add(nhanVien);
    }

    @Override
    public boolean update(NhanVien nhanVien) {
        return super.update(nhanVien);
    }

    public boolean updateWithoutTransaction(NhanVien nhanVien) {
        if (entityManager == null) {
            throw new IllegalStateException("EntityManager has not been set.");
        }
        try {
            entityManager.merge(nhanVien);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete(NhanVien nhanVien) {
        return super.delete(nhanVien);
    }

    public boolean deleteWithoutTransaction(NhanVien nhanVien) {
        if (entityManager == null) {
            throw new IllegalStateException("EntityManager has not been set.");
        }
        try {
            entityManager.remove(entityManager.contains(nhanVien) ? nhanVien : entityManager.merge(nhanVien));
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean saveWithoutTransaction(NhanVien nhanVien) {
        if (entityManager == null) {
            throw new IllegalStateException("EntityManager has not been set.");
        }
        try {
            entityManager.persist(nhanVien);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public int countTotal() {
        return ((Long) entityManager.createQuery("SELECT COUNT(n) FROM NhanVien n").getSingleResult()).intValue();
    }

    public void deleteRelatedTaiKhoan(String maNV) {
        entityManager.createQuery("DELETE FROM TaiKhoan t WHERE t.nhanVien.idNV = :maNV")
                .setParameter("maNV", maNV)
                .executeUpdate();
    }

    public void deleteRelatedChiTietHoaDon(String maNV) {
        entityManager.createQuery("DELETE FROM ChiTietHoaDon ct WHERE ct.hoaDon IN (SELECT h FROM HoaDon h WHERE h.nhanVien.idNV = :maNV)")
                .setParameter("maNV", maNV)
                .executeUpdate();
    }

    public void deleteRelatedHoaDon(String maNV) {
        deleteRelatedChiTietHoaDon(maNV);
        entityManager.createQuery("DELETE FROM HoaDon h WHERE h.nhanVien.idNV = :maNV")
                .setParameter("maNV", maNV)
                .executeUpdate();
    }

    public void deleteRelatedChiTietPhieuNhap(String maNV) {
        entityManager.createQuery("DELETE FROM ChiTietPhieuNhap ctpn WHERE ctpn.phieuNhap IN (SELECT pn FROM PhieuNhap pn WHERE pn.nhanVien.idNV = :maNV)")
                .setParameter("maNV", maNV)
                .executeUpdate();
    }

    public void deleteRelatedPhieuNhap(String maNV) {
        deleteRelatedChiTietPhieuNhap(maNV);
        entityManager.createQuery("DELETE FROM PhieuNhap pn WHERE pn.nhanVien.idNV = :maNV")
                .setParameter("maNV", maNV)
                .executeUpdate();
    }

    public boolean save(NhanVien nv) {
        return saveWithoutTransaction(nv);
    }
}