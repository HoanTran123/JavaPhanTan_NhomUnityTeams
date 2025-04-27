package dao.impl;

import dao.remote.IChiTietHoaDonDAO;
import entity.ChiTietHoaDon;

import java.rmi.RemoteException;
import java.util.List;

public class ChiTietHoaDonDAO extends GenericDAO<ChiTietHoaDon> implements IChiTietHoaDonDAO {

    public ChiTietHoaDonDAO() throws RemoteException {
        super();
    }

    @Override
    public List<ChiTietHoaDon> getAll() throws RemoteException {
        return findMany("SELECT c FROM ChiTietHoaDon c", ChiTietHoaDon.class);
    }

    @Override
    public ChiTietHoaDon getById(String idHD, String idThuoc) throws RemoteException {
        return findOne("SELECT c FROM ChiTietHoaDon c WHERE c.hoaDon.idHD = ?1 AND c.thuoc.idThuoc = ?2", ChiTietHoaDon.class, idHD, idThuoc);
    }

    @Override
    public boolean add(ChiTietHoaDon chiTietHoaDon) {
        return super.add(chiTietHoaDon);
    }

    @Override
    public boolean update(ChiTietHoaDon chiTietHoaDon) {
        return super.update(chiTietHoaDon);
    }

    @Override
    public boolean delete(ChiTietHoaDon chiTietHoaDon) {
        return super.delete(chiTietHoaDon);
    }
}