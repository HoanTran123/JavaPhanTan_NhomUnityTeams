package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import connectDB.ConnectDB;
import entity.TauEntity;
import entity.ToaTauEntity;

public class Toa_dao {

    public ArrayList<ToaTauEntity> getAllToaTau(String maTau) {
        ArrayList<ToaTauEntity> dsToaTau = new ArrayList<ToaTauEntity>();
        try {

            Connection con = ConnectDB.getConnection();
            PreparedStatement ps = null;
            String sql = "SELECT maToa, ten, soGhe, trangThai, soDayGhe, soDay, loai, ngayTao, ngayCapNhat, maTau\n" + //
                    "FROM banve.dbo.toa_tau where maTau = ? order by ngayTao asc";
            ps = con.prepareStatement(sql);
            ps.setString(1, maTau);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String maToa = rs.getString("maToa");
                String ten = rs.getString("ten");
                int trangThai = rs.getInt("trangThai");
                int soGhe = rs.getInt("soGhe");
                int loai = rs.getInt("loai");
                Date ngayTao = rs.getDate("ngayTao");
                Date ngayCapNhat = rs.getDate("ngayCapNhat");

                // NhanVienEntity nhanVien = new NhanVienEntity(maNV);
                TauEntity tau = new TauEntity(maTau);

                ToaTauEntity ToaTau = new ToaTauEntity(maToa, ten, trangThai, soGhe, loai, ngayTao, ngayCapNhat, tau);
                dsToaTau.add(ToaTau);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Toa_dao.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return dsToaTau;
    }

    // public ToaTauEntity getToaTauByMaToa(String maToa) {
    // ToaTauEntity toaTau = null; // Initialize the return variable
    // try {
    // Connection con = ConnectDB.getConnection();
    // PreparedStatement ps = null;
    // String sql = "SELECT maToa, ten, soGhe, trangThai, soDayGhe, soDay, loai,
    // ngayTao, ngayCapNhat, maTau " +
    // "FROM banve.dbo.toa_tau WHERE maToa = ?";
    // ps = con.prepareStatement(sql);
    // ps.setString(1, maToa);
    // ResultSet rs = ps.executeQuery();
    // System.out.println("1");

    // if (rs.next()) { // Check if a record was found
    // String ten = rs.getString("ten");
    // System.out.println("1");
    // int trangThai = rs.getInt("trangThai");
    // System.out.println("2");

    // int soGhe = rs.getInt("soGhe");
    // int loai = rs.getInt("loai");
    // System.out.println("1");

    // Date ngayTao = rs.getDate("ngayTao");
    // Date ngayCapNhat = rs.getDate("ngayCapNhat");
    // System.out.println("1");

    // String maTau = rs.getString("maTau");

    // // Create TauEntity using maTau
    // TauEntity tau = new TauEntity(maTau);

    // // Create ToaTauEntity using retrieved values
    // toaTau = new ToaTauEntity(maToa, ten, trangThai, soGhe, loai, ngayTao,
    // ngayCapNhat, tau);
    // }
    // } catch (SQLException ex) {
    // Logger.getLogger(Toa_dao.class.getName()).log(Level.SEVERE, null, ex);
    // } catch (Exception e) {
    // throw new RuntimeException(e);
    // }
    // return toaTau;
    // }

    public ToaTauEntity getToaTauByMaToa(String maToa) { // Changed to int
        ToaTauEntity toaTau = null; // Initialize the return variable
        try {
            Connection con = ConnectDB.getConnection();
            PreparedStatement ps = null;
            String sql = "SELECT maToa, ten, soGhe, trangThai, soDayGhe, soDay, loai, ngayTao, ngayCapNhat, maTau " +
                    "FROM banve.dbo.toa_tau WHERE maToa = ?";
            ps = con.prepareStatement(sql);
            ps.setString(1, maToa); // Set as int instead of string
            ResultSet rs = ps.executeQuery();

            if (rs.next()) { // Check if a record was found
                String ten = rs.getString("ten");
                int trangThai = rs.getInt("trangThai");
                int soGhe = rs.getInt("soGhe");
                int loai = rs.getInt("loai");
                Date ngayTao = rs.getDate("ngayTao");
                Date ngayCapNhat = rs.getDate("ngayCapNhat");
                String maTau = rs.getString("maTau");
                String maToa1 = rs.getString("maToa");

                // Create TauEntity using maTau
                TauEntity tau = new TauEntity(maTau);

                // Create ToaTauEntity using retrieved values
                toaTau = new ToaTauEntity(maToa1, ten, soGhe, trangThai, loai, ngayTao, ngayCapNhat, tau);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Toa_dao.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return toaTau;
    }

    public ToaTauEntity getToaTauByTenToa(String tenToa, TauEntity tau) {
        ToaTauEntity toaTau = null; // Initialize the return variable
        try {
            Connection con = ConnectDB.getConnection();
            PreparedStatement ps = null;
            String sql = "SELECT maToa, ten, soGhe, trangThai, soDayGhe, soDay, loai, ngayTao, ngayCapNhat, maTau " +
                    "FROM banve.dbo.toa_tau WHERE ten = ? and maTau = ?";
            ps = con.prepareStatement(sql);
            ps.setString(1, tenToa);
            ps.setString(2, tau.getMaTau());
            ResultSet rs = ps.executeQuery();

            if (rs.next()) { // Check if a record was found
                String ten = rs.getString("ten");
                int trangThai = rs.getInt("trangThai");
                int soGhe = rs.getInt("soGhe");
                int loai = rs.getInt("loai");
                Date ngayTao = rs.getDate("ngayTao");
                Date ngayCapNhat = rs.getDate("ngayCapNhat");
                String maToa = rs.getString("maToa");

                // Create ToaTauEntity using retrieved values
                toaTau = new ToaTauEntity(maToa, ten, trangThai, soGhe, loai, ngayTao, ngayCapNhat, tau);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Toa_dao.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return toaTau;
    }

    public String layTenToaTheoMa(String maTau, String maToa) {
        String tenToa = null;
        try {

            Connection con = ConnectDB.getConnection();
            PreparedStatement ps = null;

            String sql = "SELECT tenToa FROM Toa WHERE maToa = ? and maTau = ?";
            ps = con.prepareStatement(sql);
            ps.setString(1, maToa);
            ps.setString(2, maTau);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                tenToa = rs.getString("tenToa");
            }

            ps.close();
            rs.close();
        } catch (Exception ex) {
            Logger.getLogger(Toa_dao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return tenToa;
    }

    public String layMaToaTheoTen(String maTau, String tenToa) {
        String maToa = null;
        try {

            Connection con = ConnectDB.getConnection();
            PreparedStatement ps = null;
            String sql = "SELECT maToa FROM toa_tau WHERE ten = ? and maTau = ?";
            ps = con.prepareStatement(sql);
            ps.setString(1, tenToa);
            ps.setString(2, maTau);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                maToa = rs.getString("maToa");
            }

            ps.close();
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(Toa_dao.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return maToa;
    }

    // check số lượng toa theo mã tau
    public int laySoLuongToaTheoTau(String maTau) {
        int soLuong = 0;
        try {

            Connection con = ConnectDB.getConnection();
            PreparedStatement ps = null;
            String sql = "SELECT COUNT(*) FROM banve.dbo.toa_tau WHERE maTau = ?";
            ps = con.prepareStatement(sql);
            ps.setString(1, maTau);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                soLuong = rs.getInt(1);
            }

            ps.close();
            rs.close();
        } catch (Exception ex) {

            Logger.getLogger(Toa_dao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return soLuong;
    }

    // thêm Toa
    public boolean themToa(ToaTauEntity ToaTau) {
        boolean result = false;
        try {

            Connection con = ConnectDB.getConnection();
            PreparedStatement ps = null;
            String sql = "INSERT INTO banve.dbo.toa_tau\n" + //
                    "( ten, soGhe, trangThai, loai, ngayTao, ngayCapNhat, maTau)\n" + //
                    "VALUES( ?, ?, ?, ?, getdate(), getdate(), ?);";
            ps = con.prepareStatement(sql);

            // ps.setString(1,ToaTau.getMaToa());
            ps.setString(1, ToaTau.getTenToa());
            ps.setInt(2, ToaTau.getSoGhe());
            ps.setInt(3, ToaTau.getTrangThai());
            ps.setInt(4, ToaTau.getLoai());
            ps.setString(5, ToaTau.getTau().getMaTau());

            int rowsAffected = ps.executeUpdate();

            if (rowsAffected > 0) {
                result = true;
            }

            ps.close();
        } catch (Exception ex) {
            Logger.getLogger(Toa_dao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    // cập nhật Toa
    public boolean capNhatToa(ToaTauEntity ToaTau) {
        boolean result = false;
        try {

            Connection con = ConnectDB.getConnection();
            PreparedStatement ps = null;
            String sql = "UPDATE banve.dbo.toa_tau\n" + //
                    "SET ten= ?, soGhe= ?, trangThai= ?, loai= ?, ngayTao=getdate(), ngayCapNhat=getdate(), maTau= ?\n"
                    + //
                    "WHERE maToa= ?;";
            ps = con.prepareStatement(sql);
            ps.setString(1, ToaTau.getTenToa());
            // ps.setString(2, ToaTau.getNhanVien().getMaNV());
            ps.setInt(3, ToaTau.getTrangThai());
            ps.setString(4, ToaTau.getMaToa());

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                result = true;
            }

            ps.close();
        } catch (Exception ex) {
            Logger.getLogger(Toa_dao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    // check mã Toa, tên Toa đã tồn tại chưa
    public boolean kiemTraMaToa(String maToa, TauEntity tau) {

        boolean result = false;
        try {

            Connection con = ConnectDB.getConnection();
            PreparedStatement ps = null;
            String sql = "SELECT COUNT(*) FROM banve.dbo.toa_tau WHERE maToa = ? and maTau = ?";
            ps = con.prepareStatement(sql);
            ps.setString(1, maToa);
            ps.setString(2, tau.getMaTau());
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                int count = rs.getInt(1);
                if (count > 0) {
                    result = true;
                }
            }
            ps.close();
            rs.close();
        } catch (Exception ex) {

            Logger.getLogger(Toa_dao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    // check mã Toa, tên Toa đã tồn tại chưa
    public boolean kiemTraTenToa(String tenToa, TauEntity tau) {
        boolean result = false;
        try {

            Connection con = ConnectDB.getConnection();
            PreparedStatement ps = null;
            String sql = "SELECT COUNT(*) FROM banve.dbo.toa_tau WHERE ten = ? and maTau = ? ";
            ps = con.prepareStatement(sql);
            ps.setString(1, tenToa);
            ps.setString(2, tau.getMaTau());

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                int count = rs.getInt(1);
                if (count > 0) {
                    result = true;
                }
            }
            ps.close();
            rs.close();
        } catch (Exception ex) {

            Logger.getLogger(Toa_dao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    public boolean doiTrangThaiToa(ToaTauEntity Toa, TauEntity tau) {
        boolean result = false;
        try {

            Connection con = ConnectDB.getConnection();
            PreparedStatement ps = null;
            String sql = "UPDATE banve.dbo.toa_tau\n" +
                    " SET trangThai = ? WHERE maToa = ? and maTau = ?";
            con.prepareStatement(sql);

            ps = con.prepareStatement(sql);
            ps.setInt(1, Toa.getTrangThai() == 1 ? 0 : 1);
            ps.setString(2, Toa.getMaToa());
            ps.setString(3, tau.getMaTau());

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                result = true;
            }
            ps.close();
        } catch (Exception ex) {

            Logger.getLogger(Toa_dao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

}
