package dao.remote;

import entity.PhieuDatHang;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface IPhieuDatHangDAO extends Remote {
    List<PhieuDatHang> getAll() throws RemoteException;

    PhieuDatHang getById(String id) throws RemoteException;

    boolean add(PhieuDatHang phieuDatHang) throws RemoteException;

    boolean update(PhieuDatHang phieuDatHang) throws RemoteException;

    boolean delete(PhieuDatHang phieuDatHang) throws RemoteException;
}