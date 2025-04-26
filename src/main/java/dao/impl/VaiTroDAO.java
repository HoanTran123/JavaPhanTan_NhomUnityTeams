package dao.impl;

import dao.remote.IVaiTroDAO;
import entity.VaiTro;

import java.rmi.RemoteException;
import java.util.List;

public class VaiTroDAO extends GenericDAO<VaiTro> implements IVaiTroDAO {

    public VaiTroDAO() throws RemoteException {
        super();
    }

    @Override
    public List<VaiTro> getAll() throws RemoteException {
        return findMany("SELECT v FROM VaiTro v", VaiTro.class);
    }

    @Override
    public VaiTro getById(String id) throws RemoteException {
        return findOne("SELECT v FROM VaiTro v WHERE v.idVaiTro = ?1", VaiTro.class, id);
    }

    @Override
    public boolean add(VaiTro vaiTro) {
        return super.add(vaiTro);
    }

    @Override
    public boolean update(VaiTro vaiTro) {
        return super.update(vaiTro);
    }

    @Override
    public boolean delete(VaiTro vaiTro) {
        return super.delete(vaiTro);
    }
}