package dao.impl;

import dao.remote.IChiTietPhieuDatHangDAO;
import entity.ChiTietPhieuDatHang;

import java.rmi.RemoteException;
import java.util.List;

public class ChiTietPhieuDatHangDAO extends GenericDAO<ChiTietPhieuDatHang> implements IChiTietPhieuDatHangDAO {

    public ChiTietPhieuDatHangDAO() throws RemoteException {
        super();
    }

    @Override
    public List<ChiTietPhieuDatHang> getAll() throws RemoteException {
        return findMany("SELECT c FROM ChiTietPhieuDatHang c", ChiTietPhieuDatHang.class);
    }

    @Override
    public ChiTietPhieuDatHang getById(String idPDH, String idThuoc) throws RemoteException {
        return findOne("SELECT c FROM ChiTietPhieuDatHang c WHERE c.phieuDatHang.idPDH = ?1 AND c.thuoc.idThuoc = ?2", ChiTietPhieuDatHang.class, idPDH, idThuoc);
    }

    @Override
    public boolean add(ChiTietPhieuDatHang chiTietPhieuDatHang) {
        return super.add(chiTietPhieuDatHang);
    }

    @Override
    public boolean update(ChiTietPhieuDatHang chiTietPhieuDatHang) {
        return super.update(chiTietPhieuDatHang);
    }

    @Override
    public boolean delete(ChiTietPhieuDatHang chiTietPhieuDatHang) {
        return super.delete(chiTietPhieuDatHang);
    }
}