package dao.impl;

import dao.remote.IThuocDAO;
import entity.Thuoc;
import jakarta.persistence.EntityManager;
import util.HibernateUtil;

import java.rmi.RemoteException;
import java.util.List;

public class ThuocDAO extends GenericDAO<Thuoc> implements IThuocDAO {

    public ThuocDAO() throws RemoteException {
        super();
        setEntityManager(HibernateUtil.getEntityManager());
    }

    @Override
    public boolean update(Thuoc thuoc) {
        try {
            EntityManager entityManager = HibernateUtil.getEntityManager();
            entityManager.getTransaction().begin();
            entityManager.merge(thuoc); // Use merge instead of update
            entityManager.getTransaction().commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    @Override
    public List<Thuoc> getAll() throws RemoteException {
        return findMany("SELECT t FROM Thuoc t", Thuoc.class);
    }

    @Override
    public Thuoc getById(String id) throws RemoteException {
        return findOne("SELECT t FROM Thuoc t WHERE t.idThuoc = ?1", Thuoc.class, id);
    }

    @Override
    public boolean add(Thuoc thuoc) {
        return super.add(thuoc);
    }

    @Override
    public boolean delete(Thuoc thuoc) {
        return super.delete(thuoc);
    }
}