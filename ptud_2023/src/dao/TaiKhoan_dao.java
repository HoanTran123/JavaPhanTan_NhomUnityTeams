package dao;

import Interface.TaiKhoanInterface;
import connectDB.ConnectDB;
import entity.TaiKhoanEntity;
import entity.NhanVienEntity;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TaiKhoan_dao {
    private ConnectDB connect = new ConnectDB();
    private NhanVien_dao nhanVienDAO = new NhanVien_dao();

    public TaiKhoanEntity getTaiKhoan(String taiKhoan, String matKhau) {
        try {
            ConnectDB.getInstance().connect();
            Connection con = ConnectDB.getConnection();
            PreparedStatement statement = null;

            String sql = "SELECT * FROM taikhoan WHERE taiKhoan = ? AND matKhau = ?";
            statement = con.prepareStatement(sql);
            statement.setString(1, taiKhoan);
            statement.setString(2, matKhau);
            
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                NhanVienEntity nv = nhanVienDAO.findOne(rs.getString("maNV"));
                return new TaiKhoanEntity(
                    rs.getString("taiKhoan"),
                    rs.getString("matKhau"),
                    nv,
                    rs.getInt("trangThai")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean insert(TaiKhoanEntity tk) {
        try {
            ConnectDB.getInstance().connect();
            Connection con = ConnectDB.getConnection();
            PreparedStatement statement = con.prepareStatement(
                "INSERT INTO TaiKhoan(taiKhoan, matKhau, maNV, trangThai) VALUES(?, ?, ?, ?)"
            );
            
            statement.setString(1, tk.getTaiKhoan());
            statement.setString(2, tk.getMatKhau());
            statement.setString(3, tk.getNhanVien() != null ? tk.getNhanVien().getMaNV() : null);
            statement.setInt(4, tk.getTrangThai());

            return statement.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean update(TaiKhoanEntity tk) {
        try {
            ConnectDB.getInstance().connect();
            Connection con = ConnectDB.getConnection();
            PreparedStatement statement = con.prepareStatement(
                "UPDATE TaiKhoan SET matKhau = ?, maNV = ?, trangThai = ? WHERE taiKhoan = ?"
            );
            
            statement.setString(1, tk.getMatKhau());
            statement.setString(2, tk.getNhanVien() != null ? tk.getNhanVien().getMaNV() : null);
            statement.setInt(3, tk.getTrangThai());
            statement.setString(4, tk.getTaiKhoan());

            return statement.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean delete(TaiKhoanEntity tk) {
        try {
            ConnectDB.getInstance().connect();
            Connection con = ConnectDB.getConnection();
            PreparedStatement statement = con.prepareStatement(
                "DELETE FROM TaiKhoan WHERE taiKhoan = ?"
            );
            
            statement.setString(1, tk.getTaiKhoan());
            return statement.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public TaiKhoanEntity findOne(String taiKhoan) {
        try {
            ConnectDB.getInstance().connect();
            Connection con = ConnectDB.getConnection();
            PreparedStatement statement = con.prepareStatement(
                "SELECT * FROM TaiKhoan WHERE taiKhoan = ?"
            );
            
            statement.setString(1, taiKhoan);
            ResultSet rs = statement.executeQuery();
            
            if (rs.next()) {
                NhanVienEntity nv = nhanVienDAO.findOne(rs.getString("maNV"));
                return new TaiKhoanEntity(
                    rs.getString("taiKhoan"),
                    rs.getString("matKhau"),
                    nv,
                    rs.getInt("trangThai")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<TaiKhoanEntity> findAll() {
        ArrayList<TaiKhoanEntity> listTK = new ArrayList<>();
        try {
            ConnectDB.getInstance().connect();
            Connection con = ConnectDB.getConnection();
            PreparedStatement statement = con.prepareStatement("SELECT * FROM TaiKhoan");
            ResultSet rs = statement.executeQuery();
            
            while (rs.next()) {
                NhanVienEntity nv = nhanVienDAO.findOne(rs.getString("maNV"));
                TaiKhoanEntity tk = new TaiKhoanEntity(
                    rs.getString("taiKhoan"),
                    rs.getString("matKhau"),
                    nv,
                    rs.getInt("trangThai")
                );
                listTK.add(tk);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listTK;
    }

    public boolean updateTrangThai(String taiKhoan, int trangThai) {
        try {
            ConnectDB.getInstance().connect();
            Connection con = ConnectDB.getConnection();
            PreparedStatement statement = con.prepareStatement(
                "UPDATE TaiKhoan SET trangThai = ? WHERE taiKhoan = ?"
            );
            
            statement.setInt(1, trangThai);
            statement.setString(2, taiKhoan);

            return statement.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateMatKhau(String taiKhoan, String matKhauMoi) {
        try {
            ConnectDB.getInstance().connect();
            Connection con = ConnectDB.getConnection();
            PreparedStatement statement = con.prepareStatement(
                "UPDATE TaiKhoan SET matKhau = ? WHERE taiKhoan = ?"
            );
            
            statement.setString(1, matKhauMoi);
            statement.setString(2, taiKhoan);

            return statement.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
