package dao.remote;

import entity.KhachHang;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Date;
import java.util.List;

public interface IKhachHangDAO extends Remote {
    List<KhachHang> getAll() throws RemoteException;

    KhachHang getBySdt(String sdt) throws RemoteException;

    List<KhachHang> getByName(String name) throws RemoteException;

    List<KhachHang> getAllWithPagination(int offset, int limit) throws RemoteException;

    List<KhachHang> getAllWithPaginationBySdt(String sdt, int offset, int limit) throws RemoteException;

    List<KhachHang> getAllWithPaginationByName(String name, int offset, int limit) throws RemoteException;

    List<KhachHang> getKhachHangByNgayThamGia(Date date) throws RemoteException;

    int countTotal() throws RemoteException;

    int countTotalBySdt(String sdt) throws RemoteException;

    int countTotalByName(String name) throws RemoteException;

    KhachHang getById(String id) throws RemoteException;

    boolean add(KhachHang kh) throws RemoteException;

    boolean update(KhachHang kh) throws RemoteException;

    boolean delete(KhachHang kh) throws RemoteException;

    List<KhachHang> getAllKhachHang();
}