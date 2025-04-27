package dao.remote;

import entity.ChiTietHoaDon;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface IChiTietHoaDonDAO extends Remote {
    List<ChiTietHoaDon> getAll() throws RemoteException;

    ChiTietHoaDon getById(String idHD, String idThuoc) throws RemoteException;

    boolean add(ChiTietHoaDon chiTietHoaDon) throws RemoteException;

    boolean update(ChiTietHoaDon chiTietHoaDon) throws RemoteException;

    boolean delete(ChiTietHoaDon chiTietHoaDon) throws RemoteException;
}