package dao.impl;

import dao.remote.IDonViTinhDAO;
import entity.DonViTinh;

import java.rmi.RemoteException;
import java.util.List;

public class DonViTinhDAO extends GenericDAO<DonViTinh> implements IDonViTinhDAO {

    public DonViTinhDAO() throws RemoteException {
        super();
    }

    @Override
    public List<DonViTinh> getAll() throws RemoteException {
        return findMany("SELECT d FROM DonViTinh d", DonViTinh.class);
    }

    @Override
    public DonViTinh getById(String id) throws RemoteException {
        return findOne("SELECT d FROM DonViTinh d WHERE d.id = ?1", DonViTinh.class, id);
    }

    @Override
    public boolean add(DonViTinh donViTinh) {
        return super.add(donViTinh);
    }

    @Override
    public boolean update(DonViTinh donViTinh) {
        return super.update(donViTinh);
    }

    @Override
    public boolean delete(DonViTinh donViTinh) {
        return super.delete(donViTinh);
    }
}