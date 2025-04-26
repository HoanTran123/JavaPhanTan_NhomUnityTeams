package dao.remote;

import entity.ChiTietPhieuDatHang;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface IChiTietPhieuDatHangDAO extends Remote {
    List<ChiTietPhieuDatHang> getAll() throws RemoteException;

    ChiTietPhieuDatHang getById(String idPDH, String idThuoc) throws RemoteException;

    boolean add(ChiTietPhieuDatHang chiTietPhieuDatHang) throws RemoteException;

    boolean update(ChiTietPhieuDatHang chiTietPhieuDatHang) throws RemoteException;

    boolean delete(ChiTietPhieuDatHang chiTietPhieuDatHang) throws RemoteException;
}