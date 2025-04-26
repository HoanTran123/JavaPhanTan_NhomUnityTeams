package dao.remote;

import entity.KhuyenMai;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface IKhuyenMaiDAO extends Remote {
    List<KhuyenMai> getAll() throws RemoteException;

    KhuyenMai getById(String id) throws RemoteException;

    boolean add(KhuyenMai khuyenMai) throws RemoteException;

    boolean update(KhuyenMai khuyenMai) throws RemoteException;

    boolean delete(KhuyenMai khuyenMai) throws RemoteException;

    List<KhuyenMai> getKhuyenMaiByHangMuc(String hangMuc) throws RemoteException;
}