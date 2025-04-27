package dao.remote;

import entity.DoiTra;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface IDoiTraDAO extends Remote {
    List<DoiTra> getAll() throws RemoteException;

    DoiTra getById(String id) throws RemoteException;

    boolean add(DoiTra doiTra) throws RemoteException;

    boolean update(DoiTra doiTra) throws RemoteException;

    boolean delete(DoiTra doiTra) throws RemoteException;
}