package dao.remote;

import entity.TaiKhoan;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface ITaiKhoanDAO extends Remote {
    List<TaiKhoan> getAll() throws RemoteException;

    TaiKhoan getById(String id) throws RemoteException;

    boolean add(TaiKhoan taiKhoan) throws RemoteException;

    boolean update(TaiKhoan taiKhoan) throws RemoteException;

    boolean delete(TaiKhoan taiKhoan) throws RemoteException;
}