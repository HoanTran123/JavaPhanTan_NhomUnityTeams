package dao.remote;

import entity.DanhMuc;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface IDanhMucDAO extends Remote {
    List<DanhMuc> getAll() throws RemoteException;

    DanhMuc getById(String id) throws RemoteException;

    boolean add(DanhMuc danhMuc) throws RemoteException;

    boolean update(DanhMuc danhMuc) throws RemoteException;

    boolean delete(DanhMuc danhMuc) throws RemoteException;
}