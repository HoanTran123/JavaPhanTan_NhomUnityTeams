package dao.remote;

import entity.DonViTinh;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface IDonViTinhDAO extends Remote {
    List<DonViTinh> getAll() throws RemoteException;

    DonViTinh getById(String id) throws RemoteException;

    boolean add(DonViTinh donViTinh) throws RemoteException;

    boolean update(DonViTinh donViTinh) throws RemoteException;

    boolean delete(DonViTinh donViTinh) throws RemoteException;
}