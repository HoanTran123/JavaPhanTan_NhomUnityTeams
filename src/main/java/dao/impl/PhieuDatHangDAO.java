package dao.impl;

import dao.remote.IPhieuDatHangDAO;
import entity.PhieuDatHang;

import java.rmi.RemoteException;
import java.util.List;

public class PhieuDatHangDAO extends GenericDAO<PhieuDatHang> implements IPhieuDatHangDAO {

    public PhieuDatHangDAO() throws RemoteException {
        super();
    }

    @Override
    public List<PhieuDatHang> getAll() throws RemoteException {
        return findMany("SELECT p FROM PhieuDatHang p", PhieuDatHang.class);
    }

    @Override
    public PhieuDatHang getById(String id) throws RemoteException {
        return findOne("SELECT p FROM PhieuDatHang p WHERE p.idPDH = ?1", PhieuDatHang.class, id);
    }

    @Override
    public boolean add(PhieuDatHang phieuDatHang) {
        return super.add(phieuDatHang);
    }

    @Override
    public boolean update(PhieuDatHang phieuDatHang) {
        return super.update(phieuDatHang);
    }

    @Override
    public boolean delete(PhieuDatHang phieuDatHang) {
        return super.delete(phieuDatHang);
    }
}