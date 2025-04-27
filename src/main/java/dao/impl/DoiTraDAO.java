package dao.impl;

import dao.remote.IDoiTraDAO;
import entity.DoiTra;

import java.rmi.RemoteException;
import java.util.List;

public class DoiTraDAO extends GenericDAO<DoiTra> implements IDoiTraDAO {

    public DoiTraDAO() throws RemoteException {
        super();
    }

    @Override
    public List<DoiTra> getAll() throws RemoteException {
        return findMany("SELECT d FROM DoiTra d", DoiTra.class);
    }

    @Override
    public DoiTra getById(String id) throws RemoteException {
        return findOne("SELECT d FROM DoiTra d WHERE d.idDT = ?1", DoiTra.class, id);
    }

    @Override
    public boolean add(DoiTra doiTra) {
        return super.add(doiTra);
    }

    @Override
    public boolean update(DoiTra doiTra) {
        return super.update(doiTra);
    }

    @Override
    public boolean delete(DoiTra doiTra) {
        return super.delete(doiTra);
    }
}