package dao.impl;

import dao.remote.IChiTietDoiTraDAO;
import entity.ChiTietDoiTra;

import java.rmi.RemoteException;
import java.util.List;

public class ChiTietDoiTraDAO extends GenericDAO<ChiTietDoiTra> implements IChiTietDoiTraDAO {

    public ChiTietDoiTraDAO() throws RemoteException {
        super();
    }

    @Override
    public List<ChiTietDoiTra> getAll() throws RemoteException {
        return findMany("SELECT c FROM ChiTietDoiTra c", ChiTietDoiTra.class);
    }

    @Override
    public ChiTietDoiTra getById(String idDT, String idThuoc) throws RemoteException {
        return findOne("SELECT c FROM ChiTietDoiTra c WHERE c.doiTra.idDT = ?1 AND c.thuoc.idThuoc = ?2", ChiTietDoiTra.class, idDT, idThuoc);
    }

    @Override
    public boolean add(ChiTietDoiTra chiTietDoiTra) {
        return super.add(chiTietDoiTra);
    }

    @Override
    public boolean update(ChiTietDoiTra chiTietDoiTra) {
        return super.update(chiTietDoiTra);
    }

    @Override
    public boolean delete(ChiTietDoiTra chiTietDoiTra) {
        return super.delete(chiTietDoiTra);
    }
}