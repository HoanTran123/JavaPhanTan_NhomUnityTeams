package dao.remote;

import entity.NhanVien;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface INhanVienDAO extends Remote {
    List<NhanVien> getAll() throws RemoteException;

    NhanVien getById(String id) throws RemoteException;

    List<NhanVien> getByName(String name) throws RemoteException;

    boolean add(NhanVien nhanVien) throws RemoteException;

    boolean update(NhanVien nhanVien) throws RemoteException;

    boolean delete(NhanVien nhanVien) throws RemoteException;
}