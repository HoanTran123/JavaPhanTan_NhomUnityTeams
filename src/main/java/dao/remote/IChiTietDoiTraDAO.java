package dao.remote;

import entity.ChiTietDoiTra;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface IChiTietDoiTraDAO extends Remote {
    List<ChiTietDoiTra> getAll() throws RemoteException;

    ChiTietDoiTra getById(String idDT, String idThuoc) throws RemoteException;

    boolean add(ChiTietDoiTra chiTietDoiTra) throws RemoteException;

    boolean update(ChiTietDoiTra chiTietDoiTra) throws RemoteException;

    boolean delete(ChiTietDoiTra chiTietDoiTra) throws RemoteException;
}