package dao.impl;

import dao.remote.IPhieuNhapDAO;
import entity.PhieuNhap;

import java.rmi.RemoteException;
import java.util.List;

public class PhieuNhapDAO extends GenericDAO<PhieuNhap> implements IPhieuNhapDAO {

    public PhieuNhapDAO() throws RemoteException {
        super();
    }

    @Override
    public List<PhieuNhap> getAll() throws RemoteException {
        return findMany("SELECT p FROM PhieuNhap p", PhieuNhap.class);
    }

    @Override
    public PhieuNhap getById(String id) throws RemoteException {
        return findOne("SELECT p FROM PhieuNhap p WHERE p.idPN = ?1", PhieuNhap.class, id);
    }

    @Override
    public boolean add(PhieuNhap phieuNhap) {
        return super.add(phieuNhap);
    }

    @Override
    public boolean update(PhieuNhap phieuNhap) {
        return super.update(phieuNhap);
    }

    @Override
    public boolean delete(PhieuNhap phieuNhap) {
        return super.delete(phieuNhap);
    }
}