package dao.impl;

import dao.remote.INhaCungCapDAO;
import entity.NhaCungCap;

import java.rmi.RemoteException;
import java.util.List;

public class NhaCungCapDAO extends GenericDAO<NhaCungCap> implements INhaCungCapDAO {

    public NhaCungCapDAO() throws RemoteException {
        super();
    }

    @Override
    public List<NhaCungCap> getAll() throws RemoteException {
        return findMany("SELECT n FROM NhaCungCap n", NhaCungCap.class);
    }

    @Override
    public NhaCungCap getById(String id) throws RemoteException {
        return findOne("SELECT n FROM NhaCungCap n WHERE n.idNCC = ?1", NhaCungCap.class, id);
    }

    @Override
    public boolean add(NhaCungCap nhaCungCap) {
        return super.add(nhaCungCap);
    }

    @Override
    public boolean update(NhaCungCap nhaCungCap) {
        return super.update(nhaCungCap);
    }

    @Override
    public boolean delete(NhaCungCap nhaCungCap) {
        return super.delete(nhaCungCap);
    }
}