package dao.remote;

import entity.NhaCungCap;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface INhaCungCapDAO extends Remote {
    List<NhaCungCap> getAll() throws RemoteException;

    NhaCungCap getById(String id) throws RemoteException;

    boolean add(NhaCungCap nhaCungCap) throws RemoteException;

    boolean update(NhaCungCap nhaCungCap) throws RemoteException;

    boolean delete(NhaCungCap nhaCungCap) throws RemoteException;
}