package dao.remote;

import entity.PhieuNhap;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface IPhieuNhapDAO extends Remote {
    List<PhieuNhap> getAll() throws RemoteException;

    PhieuNhap getById(String id) throws RemoteException;

    boolean add(PhieuNhap phieuNhap) throws RemoteException;

    boolean update(PhieuNhap phieuNhap) throws RemoteException;

    boolean delete(PhieuNhap phieuNhap) throws RemoteException;
}