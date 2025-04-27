package dao.impl;

import dao.remote.IDanhMucDAO;
import entity.DanhMuc;

import java.rmi.RemoteException;
import java.util.List;

public class DanhMucDAO extends GenericDAO<DanhMuc> implements IDanhMucDAO {

    public DanhMucDAO() throws RemoteException {
        super();
    }

    @Override
    public List<DanhMuc> getAll() throws RemoteException {
        return findMany("SELECT d FROM DanhMuc d", DanhMuc.class);
    }

    @Override
    public DanhMuc getById(String id) throws RemoteException {
        return findOne("SELECT d FROM DanhMuc d WHERE d.idDanhMuc = ?1", DanhMuc.class, id);
    }

    @Override
    public boolean add(DanhMuc danhMuc) {
        return super.add(danhMuc);
    }

    @Override
    public boolean update(DanhMuc danhMuc) {
        return super.update(danhMuc);
    }

    @Override
    public boolean delete(DanhMuc danhMuc) {
        return super.delete(danhMuc);
    }
}