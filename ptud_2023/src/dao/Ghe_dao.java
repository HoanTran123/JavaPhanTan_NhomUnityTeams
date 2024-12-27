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
import entity.GheEntity;
import entity.ToaTauEntity;

public class Ghe_dao {

    // Retrieve all seats for a specific coach
    public ArrayList<GheEntity> getAllGhe(String maToa) {
        ArrayList<GheEntity> dsGhe = new ArrayList<>();
        try {
            Connection con = ConnectDB.getConnection();
            String sql = "SELECT maGhe, ten, loai, trangThai, ngayTao, ngayCapNhat, maToa, gia " +
                    "FROM banve.dbo.ghe WHERE maToa = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, maToa);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int maGhe = rs.getInt("maGhe");
                int loai = (rs.getInt("loai"));
                int trangThai = rs.getInt("trangThai");
                Date ngayTao = rs.getDate("ngayTao");
                Date ngayCapNhat = rs.getDate("ngayCapNhat");
                String ten = rs.getString("ten");
                int gia = rs.getInt("gia");

                ToaTauEntity toa = new ToaTauEntity(maToa);

                GheEntity ghe = new GheEntity(maGhe, ten, loai, trangThai, ngayTao, ngayCapNhat, toa);
                ghe.setGia(gia);
                dsGhe.add(ghe);
            }
            rs.close();
            ps.close();
        } catch (SQLException ex) {
            Logger.getLogger(Ghe_dao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return dsGhe;
    }

    public ArrayList<GheEntity> getAllGheVaTrangThaiHienTai(String maToa, int maHT) {
        ArrayList<GheEntity> dsGhe = new ArrayList<>();
        try {
            Connection con = ConnectDB.getConnection();
            String sql = "SELECT ghe.maGhe, ghe.ten, ghe.loai, ghe.trangThai, ghe.ngayTao, ghe.ngayCapNhat, ghe.maToa, ghe.gia, "
                    +
                    "CASE WHEN ve.maGhe IS NOT NULL THEN 1 ELSE 0 END AS isBooked " + // New column to indicate booking
                                                                                      // status
                    "FROM banve.dbo.ghe " +
                    "LEFT JOIN banve.dbo.ve ON ghe.maGhe = ve.maGhe AND ve.maHT = ? AND ve.maToa = ? where ghe.maToa = ? order by ghe.maGhe, ghe.ngayTao asc";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, maHT);
            ps.setString(2, maToa);
            ps.setString(3, maToa);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int maGhe = rs.getInt("maGhe");
                String ten = rs.getString("ten");
                int loai = rs.getInt("loai");
                int trangThai = rs.getInt("trangThai");
                Date ngayTao = rs.getDate("ngayTao");
                Date ngayCapNhat = rs.getDate("ngayCapNhat");
                int gia = rs.getInt("gia");
                int isBooked = rs.getInt("isBooked"); // Check booking status

                ToaTauEntity toa = new ToaTauEntity(maToa);
                GheEntity ghe = new GheEntity(maGhe, ten, loai, trangThai, ngayTao, ngayCapNhat, toa);
                ghe.setGia(gia);
                ghe.setTrangThaiHienTai(isBooked); // Assuming you have a setBooked method in GheEntity
                dsGhe.add(ghe);
            }
            // Close resources
            rs.close();
            ps.close();
        } catch (SQLException ex) {
            Logger.getLogger(Ghe_dao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return dsGhe;
    }

    // Get seat type by seat ID and coach ID
    public int layLoaiGheTheoMa(int maGhe, int maToa) {
        int loaiGhe = -1;
        try {
            Connection con = ConnectDB.getConnection();
            String sql = "SELECT loai FROM banve.dbo.ghe WHERE maGhe = ? AND maToa = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, maGhe);
            ps.setInt(2, maToa);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                loaiGhe = rs.getInt("loai");
            }
            rs.close();
            ps.close();
        } catch (SQLException ex) {
            Logger.getLogger(Ghe_dao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return loaiGhe;
    }

    // Add a new seat
    public boolean themGhe(GheEntity ghe) {
        boolean result = false;
        try {
            Connection con = ConnectDB.getConnection();
            String sql = "INSERT INTO banve.dbo.ghe (ten, loai, trangThai, ngayTao, ngayCapNhat, maToa, gia) " +
                    "VALUES (?, ?, ?, getdate(), getdate(), ?, ?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, ghe.getTen());
            ps.setInt(2, ghe.getLoai());
            ps.setInt(3, ghe.getTrangThai());
            ps.setInt(4, Integer.parseInt(ghe.getToa().getMaToa()));
            ps.setInt(5, ghe.getGia());

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                result = true;
            }
            ps.close();
        } catch (SQLException ex) {
            Logger.getLogger(Ghe_dao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    // Update an existing seat
    public boolean capNhatGhe(GheEntity ghe) {
        boolean result = false;
        try {
            Connection con = ConnectDB.getConnection();
            String sql = "UPDATE banve.dbo.ghe " +
                    "SET ten = ?, loai = ?, trangThai = ?, ngayCapNhat = getdate() " +
                    "WHERE maGhe = ? AND maToa = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, ghe.getTen());
            ps.setInt(2, ghe.getLoai());
            ps.setInt(3, ghe.getTrangThai());
            ps.setInt(4, ghe.getMaGhe());
            ps.setString(5, ghe.getToa().getMaToa());

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                result = true;
            }
            ps.close();
        } catch (SQLException ex) {
            Logger.getLogger(Ghe_dao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    // Check if a seat exists by its ID and coach ID
    public boolean kiemTraMaGhe(int maGhe, int maToa) {
        boolean exists = false;
        try {
            Connection con = ConnectDB.getConnection();
            String sql = "SELECT COUNT(*) FROM banve.dbo.ghe WHERE maGhe = ? AND maToa = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, maGhe);
            ps.setInt(2, maToa);
            ResultSet rs = ps.executeQuery();

            if (rs.next() && rs.getInt(1) > 0) {
                exists = true;
            }
            rs.close();
            ps.close();
        } catch (SQLException ex) {
            Logger.getLogger(Ghe_dao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return exists;
    }

    // kiểm tra tên ghế
    public boolean kiemTraTenGhe(String tenGhe, String maToa) {
        boolean exists = false;
        try {
            Connection con = ConnectDB.getConnection();
            String sql = "SELECT COUNT(*) as soLuong FROM banve.dbo.ghe WHERE ten = ? AND maToa = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, tenGhe);
            ps.setString(2, maToa);
            ResultSet rs = ps.executeQuery();
            if (rs.next() && rs.getInt("soLuong") > 0) {
                exists = true;
            }
            rs.close();
            ps.close();
        } catch (SQLException ex) {
            Logger.getLogger(Ghe_dao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return exists;
    }

    // Change seat status
    public boolean doiTrangThaiGhe(String maGhe, int maToa) {
        boolean result = false;
        try {
            Connection con = ConnectDB.getConnection();
            String sql = "UPDATE banve.dbo.ghe SET trangThai = (CASE WHEN trangThai = 0 THEN 1 ELSE 0 END) " +
                    "WHERE maGhe = ? AND maToa = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, maGhe);
            ps.setInt(2, maToa);

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                result = true;
            }
            ps.close();
        } catch (SQLException ex) {
            Logger.getLogger(Ghe_dao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    // check số lượng toa theo mã tau
    public int laySoLuongGheTheoMaToa(String maToa) {
        int soLuong = 0;
        try {
            Connection con = ConnectDB.getConnection();
            PreparedStatement ps = null;
            String sql = "SELECT COUNT(*) as soLuong FROM banve.dbo.toa_tau WHERE maToa = ?";
            ps = con.prepareStatement(sql);
            ps.setString(1, maToa);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                soLuong = rs.getInt("soLuong");
                System.out.println("soLuong " + soLuong);
            }

            ps.close();
            rs.close();
        } catch (Exception ex) {

            Logger.getLogger(Toa_dao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return soLuong;
    }

}
