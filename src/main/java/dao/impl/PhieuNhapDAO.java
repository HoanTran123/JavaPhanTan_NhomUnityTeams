package dao.impl;

import dao.remote.IPhieuNhapDAO;
import entity.ChiTietPhieuNhap;
import entity.PhieuNhap;
import util.EntityManagerUtil;
import util.HibernateUtil;

import java.rmi.RemoteException;
import java.util.List;

public class PhieuNhapDAO extends GenericDAO<PhieuNhap> implements IPhieuNhapDAO {

    public PhieuNhapDAO() {
        setEntityManager(HibernateUtil.getEntityManager());
    }

    public List<PhieuNhap> getAll() {
        return findMany("SELECT pn FROM PhieuNhap pn", PhieuNhap.class);
    }

    public List<ChiTietPhieuNhap> getChiTietByPhieuNhap(String idPN) {
        return findMany("SELECT ct FROM ChiTietPhieuNhap ct WHERE ct.phieuNhap.idPN = :idPN",
                ChiTietPhieuNhap.class, idPN);
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


    public List<ChiTietPhieuNhap> findMany(String query, Class<ChiTietPhieuNhap> chiTietPhieuNhapClass, String idPN) {
        return super.findMany(query, chiTietPhieuNhapClass, idPN);
    }
}