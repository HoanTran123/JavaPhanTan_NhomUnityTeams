package dao.impl;

import dao.remote.IXuatXuDAO;
import entity.XuatXu;

import java.rmi.RemoteException;
import java.util.List;

public class XuatXuDAO extends GenericDAO<XuatXu> implements IXuatXuDAO {

    public XuatXuDAO() throws RemoteException {
        super();
    }

    @Override
    public List<XuatXu> getAll() throws RemoteException {
        return findMany("SELECT x FROM XuatXu x", XuatXu.class);
    }

    @Override
    public XuatXu getById(String id) throws RemoteException {
        return findOne("SELECT x FROM XuatXu x WHERE x.idXuatXu = ?1", XuatXu.class, id);
    }

    @Override
    public boolean add(XuatXu xuatXu) {
        return super.add(xuatXu);
    }

    @Override
    public boolean update(XuatXu xuatXu) {
        return super.update(xuatXu);
    }

    @Override
    public boolean delete(XuatXu xuatXu) {
        return super.delete(xuatXu);
    }
}