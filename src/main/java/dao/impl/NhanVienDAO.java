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

    @Override
    public boolean delete(NhanVien nhanVien) {
        return super.delete(nhanVien);
    }
}