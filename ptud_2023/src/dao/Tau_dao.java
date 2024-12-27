/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import connectDB.ConnectDB;
import entity.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ploc2
 */
public class Tau_dao {

    public ArrayList<TauEntity> getAllTau() {
        ArrayList<TauEntity> dsTau = new ArrayList<TauEntity>();
        try {
            Connection con = ConnectDB.getConnection();
            String sql = " SELECT \n" +
                    "    tau.maTau,\n" +
                    "    tau.ten ,\n" +
                    "    tau.soToa,\n" +
                    "    tau.loai,\n" +
                    "    tau.trangThai,\n" +
                    "    tau.ngayTao,\n" +
                    "    tau.ngayCapNhat,\n" +
                    "    gaDi.maGa AS maGaDi,\n" +
                    "    gaDi.ten AS tenGaDi,\n" +
                    "    gaDen.maGa AS maGaDen,\n" +
                    "    gaDen.ten AS tenGaDen,\n" +
                    "    nv.maNV AS maNhanVien,\n" +
                    "    nv.ten AS tenNhanVien\n" +
                    "FROM \n" +
                    "    banve.dbo.tau tau\n" +
                    "LEFT JOIN \n" +
                    "    banve.dbo.ga gaDi ON tau.gaDi = gaDi.maGa\n" +
                    "LEFT JOIN \n" +
                    "    banve.dbo.ga gaDen ON tau.gaDen = gaDen.maGa\n" +
                    "LEFT JOIN \n" +
                    "    banve.dbo.nhan_vien nv ON tau.maNV = nv.maNV \n"
                    + " order by tau.ngayTao desc;";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String maTau = rs.getString("maTau");
                String ten = rs.getString("ten");
                int soToa = rs.getInt("soToa");
                int loai = rs.getInt("loai");
                int trangThai = rs.getInt("trangThai");
                Date ngayTao = rs.getDate("ngayTao");
                Date ngayCapNhat = rs.getDate("ngayCapNhat");
                String maNV = rs.getString("maNhanVien");
                String tenNhanVien = rs.getString("tenNhanVien");
                String tenGaDi = rs.getString("tenGaDi");
                String tenGaDen = rs.getString("tenGaDen");
                String maGaDi = rs.getString("maGaDi");
                String maGaDen = rs.getString("maGaDen");

                GaTauEntity gaDi = new GaTauEntity(maGaDi, tenGaDi);
                GaTauEntity gaDen = new GaTauEntity(maGaDen, tenGaDen);

                NhanVienEntity nhanVien = new NhanVienEntity(maNV, tenNhanVien);

                // Tạo đối tượng TauEntity với thông tin từ ResultSet

                TauEntity tau = new TauEntity(maTau, ten, gaDi, gaDen, soToa, loai, trangThai, ngayTao, ngayCapNhat,
                        nhanVien);
                dsTau.add(tau);
            }

            ps.close();
            rs.close();
        } catch (Exception ex) {
            Logger.getLogger(Tau_dao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return dsTau;
    }

    public ArrayList<TauEntity> getAllTauDatVe(String dsMaTau, String hanhTrinhString) {
        System.out.println("dassssssss" + dsMaTau);
        ArrayList<TauEntity> dsTau = new ArrayList<TauEntity>();
        try {
            Connection con = ConnectDB.getConnection();
            String sql = "SELECT \r\n" + //
                    "    ht.maHT,\r\n" + //
                    "    ht.gioDi,\r\n" + //
                    "    ht.ngayDi,\r\n" + //
                    "    ht.gioDen,\r\n" + //
                    "    ht.ngayDen,\r\n" + //
                    "    ht.loai,\r\n" + //
                    "    ht.trangThai,\r\n" + //
                    "    ht.ngayTao,\r\n" + //
                    "    ht.ngayCapNhat,\r\n" + //
                    "    t.maTau,\r\n" + //
                    "    t.ten,\r\n" + //
                    "    t.gaDi,\r\n" + //
                    "    t.gaDen,\r\n" + //
                    "    t.soToa,\r\n" + //
                    "    t.loai AS loaiTau,\r\n" + //
                    "    COUNT(g.maGhe) AS TongSoGhe,\r\n" + //
                    "    SUM(CASE WHEN v.maVe IS NOT NULL THEN 1 ELSE 0 END) AS GheDaDat, -- Ghế đã đặt\r\n" + //
                    "    SUM(CASE WHEN v.maVe IS NULL THEN 1 ELSE 0 END) AS GheTrong -- Ghế trống\r\n" + //
                    "FROM \r\n" + //
                    "    banve.dbo.hanh_trinh ht\r\n" + //
                    "LEFT JOIN \r\n" + //
                    "    banve.dbo.tau t ON ht.maTau = t.maTau\r\n" + //
                    "LEFT JOIN \r\n" + //
                    "    banve.dbo.toa_tau tt ON t.maTau = tt.maTau\r\n" + //
                    "LEFT JOIN \r\n" + //
                    "    banve.dbo.ghe g ON tt.maToa = g.maToa \r\n" + //
                    "LEFT JOIN \r\n" + //
                    "    banve.dbo.ve v ON g.maGhe = v.maGhe AND ht.maHT  = v.maHT\r\n" + //
                    " where t.maTau IN (" + dsMaTau + ") AND ht.maHT IN (" + hanhTrinhString + ")\r\n" + //
                    "GROUP BY \r\n" + //
                    "    ht.maHT,\r\n" + //
                    "    ht.gioDi,\r\n" + //
                    "    ht.ngayDi,\r\n" + //
                    "    ht.gioDen,\r\n" + //
                    "    ht.ngayDen,\r\n" + //
                    "    ht.loai,\r\n" + //
                    "    ht.trangThai,\r\n" + //
                    "    ht.ngayTao,\r\n" + //
                    "    ht.ngayCapNhat,\r\n" + //
                    "    t.maTau,\r\n" + //
                    "    t.ten,\r\n" + //
                    "    t.gaDi,\r\n" + //
                    "    t.gaDen,\r\n" + //
                    "    t.soToa,\r\n" + //
                    "    t.loai;\r\n" + //
                    "";

            System.out.println(sql + dsMaTau + hanhTrinhString);

            PreparedStatement ps = con.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String maTau = rs.getString("maTau");
                String ten = rs.getString("ten");
                int soToa = rs.getInt("soToa");
                int loai = rs.getInt("loai");
                int trangThai = rs.getInt("trangThai");
                Date ngayTao = rs.getDate("ngayTao");
                Date ngayCapNhat = rs.getDate("ngayCapNhat");
                String maGaDi = rs.getString("gaDi");
                String maGaDen = rs.getString("gaDen");

                int GheDaDat = rs.getInt("GheDaDat");
                int GheTrong = rs.getInt("GheTrong");
                int TongSoGhe = rs.getInt("TongSoGhe");

                GaTauEntity gaDi = new GaTauEntity(maGaDi);
                GaTauEntity gaDen = new GaTauEntity(maGaDen);

                NhanVienEntity nhanVien = new NhanVienEntity();

                // Tạo đối tượng TauEntity với thông tin từ ResultSet

                TauEntity tau = new TauEntity(maTau, ten, gaDi, gaDen, soToa, loai, trangThai, ngayTao, ngayCapNhat,
                        nhanVien);

                tau.setSoLuongGheTrong(GheTrong);
                tau.setSoLuongDaDat(GheDaDat);

                dsTau.add(tau);
            }

            ps.close();
            rs.close();
        } catch (Exception ex) {
            Logger.getLogger(Tau_dao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return dsTau;
    }

    public ArrayList<TauEntity> getAllTauDangHoatDong() {
        ArrayList<TauEntity> dsTau = new ArrayList<TauEntity>();
        try {
            Connection con = ConnectDB.getConnection();
            String sql = " SELECT \n" +
                    "    tau.maTau,\n" +
                    "    tau.ten ,\n" +
                    "    tau.soToa,\n" +
                    "    tau.loai,\n" +
                    "    tau.trangThai,\n" +
                    "    tau.ngayTao,\n" +
                    "    tau.ngayCapNhat,\n" +
                    "    gaDi.maGa AS maGaDi,\n" +
                    "    gaDi.ten AS tenGaDi,\n" +
                    "    gaDen.maGa AS maGaDen,\n" +
                    "    gaDen.ten AS tenGaDen,\n" +
                    "    nv.maNV AS maNhanVien,\n" +
                    "    nv.ten AS tenNhanVien\n" +
                    "FROM \n" +
                    "    banve.dbo.tau tau\n" +
                    "JOIN \n" +
                    "    banve.dbo.ga gaDi ON tau.gaDi = gaDi.maGa\n" +
                    "JOIN \n" +
                    "    banve.dbo.ga gaDen ON tau.gaDen = gaDen.maGa\n" +
                    "JOIN \n" +
                    "    banve.dbo.nhan_vien nv ON tau.maNV = nv.maNV \n"
                    + " where tau.trangThai = 1 order by tau.ngayTao desc;";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String maTau = rs.getString("maTau");
                String ten = rs.getString("ten");
                int soToa = rs.getInt("soToa");
                int loai = rs.getInt("loai");
                int trangThai = rs.getInt("trangThai");
                Date ngayTao = rs.getDate("ngayTao");
                Date ngayCapNhat = rs.getDate("ngayCapNhat");
                String maNV = rs.getString("maNhanVien");
                String tenNhanVien = rs.getString("tenNhanVien");
                String tenGaDi = rs.getString("tenGaDi");
                String tenGaDen = rs.getString("tenGaDen");
                String maGaDi = rs.getString("maGaDi");
                String maGaDen = rs.getString("maGaDen");

                GaTauEntity gaDi = new GaTauEntity(maGaDi, tenGaDi);
                GaTauEntity gaDen = new GaTauEntity(maGaDen, tenGaDen);

                NhanVienEntity nhanVien = new NhanVienEntity(maNV, tenNhanVien);

                // Tạo đối tượng TauEntity với thông tin từ ResultSet

                TauEntity tau = new TauEntity(maTau, ten, gaDi, gaDen, soToa, loai, trangThai, ngayTao, ngayCapNhat,
                        nhanVien);
                dsTau.add(tau);
            }

            ps.close();
            rs.close();
        } catch (Exception ex) {
            Logger.getLogger(Tau_dao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return dsTau;
    }

    public boolean themTau(TauEntity sp) {
        try {
            // Kết nối cơ sở dữ liệu
            Connection con = ConnectDB.getConnection();
            PreparedStatement ps = null;

            // Câu lệnh SQL để thêm tàu vào bảng 'tau'
            String sql = "INSERT INTO banve.dbo.tau " +
                    "(ten, gaDen, gaDi, soToa, loai, trangThai, ngayTao, ngayCapNhat, maNV, maTau) " +
                    "VALUES (?, ?, ?, ?, ?, ?, getdate(), getdate(), ?, ?)";

            ps = con.prepareStatement(sql);

            // Thêm các giá trị vào câu lệnh SQL từ đối tượng 'TauEntity'
            ps.setString(1, sp.getTenTau());
            ps.setString(2, sp.getGaDen().getMaGa());
            ps.setString(3, sp.getGaDi().getMaGa());
            ps.setInt(4, sp.getSoToa());
            ps.setInt(5, sp.getLoai());
            ps.setInt(6, sp.getTrangThai());
            ps.setString(7, sp.getNhanVien().getMaNV());
            ps.setString(8, sp.getMaTau());

            // Thực thi câu lệnh SQL và đóng kết nối
            int check = ps.executeUpdate();
            ps.close();

            return check > 0; // Trả về 'true' nếu thêm thành công

        } catch (Exception ex) {
            Logger.getLogger(Tau_dao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false; // Trả về 'false' nếu có lỗi
    }

    public ArrayList<TauEntity> timTau(String ma) {
        ArrayList<TauEntity> dsTau = new ArrayList<TauEntity>();
        try {
            Connection con = ConnectDB.getConnection();
            PreparedStatement ps = null;

            // Updated SQL query
            String sql = " SELECT \n" +
                    "    tau.maTau,\n" +
                    "    tau.ten AS tenTau,\n" +
                    "    tau.soToa,\n" +
                    "    tau.loai,\n" +
                    "    tau.trangThai,\n" +
                    "    tau.ngayTao,\n" +
                    "    tau.ngayCapNhat,\n" +
                    "    gaDi.maGa AS maGaDi,\n" +
                    "    gaDi.ten AS tenGaDi,\n" +
                    "    gaDen.maGa AS maGaDen,\n" +
                    "    gaDen.ten AS tenGaDen,\n" +
                    "    nv.maNV AS maNhanVien,\n" +
                    "    nv.ten AS tenNhanVien\n" +
                    "FROM \n" +
                    "    banve.dbo.tau tau\n" +
                    "JOIN \n" +
                    "    banve.dbo.ga gaDi ON tau.gaDi = gaDi.maGa\n" +
                    "JOIN \n" +
                    "    banve.dbo.ga gaDen ON tau.gaDen = gaDen.maGa\n" +
                    "JOIN \n" +
                    "    banve.dbo.nhan_vien nv ON tau.maNV = nv.maNV " +
                    "WHERE tau.maTau = ?";

            ps = con.prepareStatement(sql);
            ps.setString(1, ma);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String maTau = rs.getString("maTau");
                String tenTau = rs.getString("tenTau"); // Updated field name
                int soToa = rs.getInt("soToa"); // New field
                int loai = rs.getInt("loai"); // New field
                int trangThai = rs.getInt("trangThai"); // New field
                Date ngayTao = rs.getDate("ngayTao");
                Date ngayCapNhat = rs.getDate("ngayCapNhat");
                String maGaDi = rs.getString("maGaDi"); // New field
                String tenGaDi = rs.getString("tenGaDi"); // New field
                String maGaDen = rs.getString("maGaDen"); // New field
                String tenGaDen = rs.getString("tenGaDen"); // New field
                String maNhanVien = rs.getString("maNhanVien"); // New field
                String tenNhanVien = rs.getString("tenNhanVien"); // New field

                GaTauEntity gaDi = new GaTauEntity(maGaDi, tenGaDi);
                GaTauEntity gaDen = new GaTauEntity(maGaDen, tenGaDen);

                NhanVienEntity nhanVien = new NhanVienEntity(maNhanVien, tenNhanVien);
                // Creating TauEntity with new data fields
                TauEntity tau = new TauEntity(maTau, tenTau, gaDi, gaDen, soToa, loai, trangThai, ngayTao, ngayCapNhat,
                        nhanVien);
                dsTau.add(tau);
            }
            ps.close();
            rs.close();
        } catch (Exception ex) {
            Logger.getLogger(Tau_dao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return dsTau;
    }

    public boolean capNhatTau(TauEntity sp) {
        try {
            Connection con = ConnectDB.getConnection();
            PreparedStatement ps = null;

            // Updated SQL query to match the fields available
            String sql = "UPDATE banve.dbo.tau SET " +
                    "ten=?, gaDi=?, gaDen=?, soToa=?, loai=?, trangThai=?, " +
                    " maNV=? " +
                    "WHERE maTau=?";

            ps = con.prepareStatement(sql);
            ps.setString(1, sp.getTenTau()); // Assuming the name is `ten` in TauEntity
            ps.setString(2, sp.getGaDi().getMaGa()); // Assuming you have a method to get the originating station
            ps.setString(3, sp.getGaDen().getMaGa()); // Assuming you have a method to get the destination station
            ps.setInt(4, sp.getSoToa()); // Updated for number of carriages
            ps.setInt(5, sp.getLoai()); // Assuming you have a method to get type
            ps.setInt(6, sp.getTrangThai()); // Assuming you have a method to get status
            ps.setString(7, sp.getNhanVien().getMaNV()); // Assuming you have a method to get employee ID.
            ps.setString(8, sp.getMaTau()); // Unique identifier for the train

            ps.executeUpdate();
            ps.close();
            return true;
        } catch (Exception ex) {
            Logger.getLogger(Tau_dao.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public ArrayList<TauEntity> kiemTraTonKho() {
        ArrayList<TauEntity> dsSP = new ArrayList<TauEntity>();
        try {
        } catch (Exception ex) {
            Logger.getLogger(Tau_dao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return dsSP;
    }

    public boolean capNhatSoLuongTonSauKhiTaoHD(String maTau, int soLuong) {
        PreparedStatement statement = null;
        try {
            Connection con = ConnectDB.getConnection();

            String sql = "Update Tau set soLuongTonKho=soLuongTonKho-? where maTau=?";
            statement = con.prepareStatement(sql);
            statement.setInt(1, soLuong);
            statement.setString(2, maTau);

            int rs = statement.executeUpdate();
            TauEntity Tau = null;
            if (rs < 1) {

                return false;
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                statement.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public int laySoLuongTonKhoTheomaTau(String maTau) {
        int soLuongHienTai = 0;
        try {
            Connection con = ConnectDB.getConnection();
            PreparedStatement ps = null;
            String sql = "SELECT soLuongTonKho FROM Tau WHERE maTau =?";
            ps = con.prepareStatement(sql);
            ps.setString(1, maTau);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                soLuongHienTai = rs.getInt("soLuongTonKho");
            }
            ps.close();
            rs.close();
        } catch (Exception ex) {
            Logger.getLogger(Tau_dao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return soLuongHienTai;
    }

    public boolean capNhatSoLuong(String maTau, int soLuongNhap) {
        try {
            Connection con = ConnectDB.getConnection();
            PreparedStatement ps = null;
            String sql = "update Tau set soLuongTonKho=? WHERE maTau =?";
            ps = con.prepareStatement(sql);
            ps.setInt(1, soLuongNhap);
            ps.setString(2, maTau);
            int kq = ps.executeUpdate();
            ps.close();
            return kq > 0;
        } catch (Exception ex) {
            Logger.getLogger(Tau_dao.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean kiemTraMaTauTonTai(String maTau) {
        try {
            Connection con = ConnectDB.getConnection();
            PreparedStatement ps = null;
            String sql = "SELECT COUNT(*) FROM Tau WHERE maTau =?";
            ps = con.prepareStatement(sql);
            ps.setString(1, maTau);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int sl = rs.getInt(1);
                return sl > 0;
            }
            ps.close();
            rs.close();
        } catch (Exception ex) {
            Logger.getLogger(Tau_dao.class.getName()).log(Level.SEVERE, null, ex);

        }
        return false;
    }

}
