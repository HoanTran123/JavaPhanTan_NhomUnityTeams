package dao.impl;

import dao.remote.ITaiKhoanDAO;
import entity.TaiKhoan;

import java.rmi.RemoteException;
import java.util.List;

public class TaiKhoanDAO extends GenericDAO<TaiKhoan> implements ITaiKhoanDAO {

    public TaiKhoanDAO() throws RemoteException {
        super();
    }

    @Override
    public List<TaiKhoan> getAll() throws RemoteException {
        return findMany("SELECT t FROM TaiKhoan t", TaiKhoan.class);
    }

    @Override
    public TaiKhoan getById(String id) throws RemoteException {
        return findOne("SELECT t FROM TaiKhoan t WHERE t.idTaiKhoan = ?1", TaiKhoan.class, id);
    }

    @Override
    public boolean add(TaiKhoan taiKhoan) {
        return super.add(taiKhoan);
    }

    @Override
    public boolean update(TaiKhoan taiKhoan) {
        return super.update(taiKhoan);
    }

    @Override
    public boolean delete(TaiKhoan taiKhoan) {
        return super.delete(taiKhoan);
    }
}