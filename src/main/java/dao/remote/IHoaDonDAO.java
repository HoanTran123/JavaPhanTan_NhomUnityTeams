package dao.remote;

import entity.HoaDon;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface IHoaDonDAO extends Remote {
    List<HoaDon> getAll() throws RemoteException;

    HoaDon getById(String id) throws RemoteException;

    boolean add(HoaDon hoaDon) throws RemoteException;

    boolean update(HoaDon hoaDon) throws RemoteException;

    boolean delete(HoaDon hoaDon) throws RemoteException;
}