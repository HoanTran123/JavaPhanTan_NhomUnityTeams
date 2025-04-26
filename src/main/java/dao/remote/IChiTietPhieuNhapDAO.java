package dao.remote;

import entity.ChiTietPhieuNhap;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface IChiTietPhieuNhapDAO extends Remote {
    List<ChiTietPhieuNhap> getAll() throws RemoteException;

    ChiTietPhieuNhap getById(String idPN, String idThuoc) throws RemoteException;

    boolean add(ChiTietPhieuNhap chiTietPhieuNhap) throws RemoteException;

    boolean update(ChiTietPhieuNhap chiTietPhieuNhap) throws RemoteException;

    boolean delete(ChiTietPhieuNhap chiTietPhieuNhap) throws RemoteException;
}