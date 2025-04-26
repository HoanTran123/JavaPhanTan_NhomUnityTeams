package dao.impl;

import dao.remote.IChiTietPhieuNhapDAO;
import entity.ChiTietPhieuNhap;

import java.rmi.RemoteException;
import java.util.List;

public class ChiTietPhieuNhapDAO extends GenericDAO<ChiTietPhieuNhap> implements IChiTietPhieuNhapDAO {

    public ChiTietPhieuNhapDAO() throws RemoteException {
        super();
    }

    @Override
    public List<ChiTietPhieuNhap> getAll() throws RemoteException {
        return findMany("SELECT c FROM ChiTietPhieuNhap c", ChiTietPhieuNhap.class);
    }

    @Override
    public ChiTietPhieuNhap getById(String idPN, String idThuoc) throws RemoteException {
        return findOne("SELECT c FROM ChiTietPhieuNhap c WHERE c.phieuNhap.idPN = ?1 AND c.thuoc.idThuoc = ?2",
                ChiTietPhieuNhap.class, idPN, idThuoc);
    }

    @Override
    public boolean add(ChiTietPhieuNhap chiTietPhieuNhap) {
        return super.add(chiTietPhieuNhap);
    }

    @Override
    public boolean update(ChiTietPhieuNhap chiTietPhieuNhap) {
        return super.update(chiTietPhieuNhap);
    }

    @Override
    public boolean delete(ChiTietPhieuNhap chiTietPhieuNhap) {
        return super.delete(chiTietPhieuNhap);
    }
}