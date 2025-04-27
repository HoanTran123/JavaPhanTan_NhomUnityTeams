package dao.impl;

import dao.remote.IKhuyenMaiDAO;
import entity.KhuyenMai;

import java.rmi.RemoteException;
import java.util.List;

public class KhuyenMaiDAO extends GenericDAO<KhuyenMai> implements IKhuyenMaiDAO {

    public KhuyenMaiDAO() throws RemoteException {
        super();
    }

    @Override
    public List<KhuyenMai> getAll() throws RemoteException {
        return findMany("SELECT k FROM KhuyenMai k", KhuyenMai.class);
    }

    @Override
    public KhuyenMai getById(String id) throws RemoteException {
        return findOne("SELECT k FROM KhuyenMai k WHERE k.idKM = ?1", KhuyenMai.class, id);
    }

    @Override
    public boolean add(KhuyenMai khuyenMai) {
        return super.add(khuyenMai);
    }

    @Override
    public boolean update(KhuyenMai khuyenMai) {
        return super.update(khuyenMai);
    }

    @Override
    public boolean delete(KhuyenMai khuyenMai) {
        return super.delete(khuyenMai);
    }

    @Override
    public List<KhuyenMai> getKhuyenMaiByHangMuc(String hangMuc) throws RemoteException {
        return findMany("SELECT k FROM KhuyenMai k WHERE k.hangMuc = ?1", KhuyenMai.class, hangMuc);
    }
}