package dao.remote;

import entity.VaiTro;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface IVaiTroDAO extends Remote {
    List<VaiTro> getAll() throws RemoteException;

    VaiTro getById(String id) throws RemoteException;

    boolean add(VaiTro vaiTro) throws RemoteException;

    boolean update(VaiTro vaiTro) throws RemoteException;

    boolean delete(VaiTro vaiTro) throws RemoteException;
}