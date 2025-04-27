package dao.remote;

import entity.XuatXu;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface IXuatXuDAO extends Remote {
    List<XuatXu> getAll() throws RemoteException;

    XuatXu getById(String id) throws RemoteException;

    boolean add(XuatXu xuatXu) throws RemoteException;

    boolean update(XuatXu xuatXu) throws RemoteException;

    boolean delete(XuatXu xuatXu) throws RemoteException;
}