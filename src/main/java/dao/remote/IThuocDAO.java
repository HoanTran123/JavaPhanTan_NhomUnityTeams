package dao.remote;

import entity.Thuoc;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface IThuocDAO extends Remote {
    List<Thuoc> getAll() throws RemoteException;

    Thuoc getById(String id) throws RemoteException;

    boolean add(Thuoc thuoc) throws RemoteException;

    boolean update(Thuoc thuoc) throws RemoteException;

    boolean delete(Thuoc thuoc) throws RemoteException;
}