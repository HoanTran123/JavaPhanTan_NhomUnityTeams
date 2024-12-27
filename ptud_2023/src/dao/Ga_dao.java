package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import connectDB.ConnectDB;
import entity.GaTauEntity;

public class Ga_dao {

    public ArrayList<GaTauEntity> getAllGaTau() {
        ArrayList<GaTauEntity> dsGaTau = new ArrayList<GaTauEntity>();
        try {
            Connection con = ConnectDB.getConnection();
            PreparedStatement ps = null;
            String sql = "SELECT maGa, ten, trangThai FROM banve.dbo.ga order by maGa desc;";
            ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String maGa = rs.getString("maGa");
                String ten = rs.getString("ten");
                int trangThai = rs.getInt("trangThai");

                // NhanVienEntity nhanVien = new NhanVienEntity(maNV);

                GaTauEntity gaTau = new GaTauEntity(maGa, ten, trangThai);
                dsGaTau.add(gaTau);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Ga_dao.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return dsGaTau;
    }

    public String layTenGaTheoMa(String maGa) {
        String tenGa = null;
        try {
            ConnectDB.getInstance().connect();
            Connection con = ConnectDB.getConnection();
            PreparedStatement ps = null;

            String sql = "SELECT tenGa FROM ga WHERE maGa = ?";
            ps = con.prepareStatement(sql);
            ps.setString(1, maGa);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                tenGa = rs.getString("tenGa");
            }

            ps.close();
            rs.close();
        } catch (Exception ex) {
            Logger.getLogger(Ga_dao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return tenGa;
    }

    public String layMaGaTheoTen(String tenGa) {
        String maGa = null;
        try {
            ConnectDB.getInstance().connect();
            Connection con = ConnectDB.getConnection();
            PreparedStatement ps = null;
            String sql = "SELECT maGa FROM ga WHERE ten = ?";
            ps = con.prepareStatement(sql);
            ps.setString(1, tenGa);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                maGa = rs.getString("maGa");
            }

            ps.close();
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(Ga_dao.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return maGa;
    }

    // thêm ga
    public boolean themGa(GaTauEntity gaTau) {
        boolean result = false;
        try {
            ConnectDB.getInstance().connect();
            Connection con = ConnectDB.getConnection();
            PreparedStatement ps = null;
            String sql = "INSERT INTO banve.dbo.ga\n" + //
                    "(maGa, ten, maNV, trangThai)\n" + //
                    "VALUES(?, ?, ?,  1);";
            ps = con.prepareStatement(sql);

            ps.setString(1, gaTau.getMaGa());
            ps.setString(2, gaTau.getTenGa());
            ps.setString(3, gaTau.getNhanVien().getMaNV());

            int rowsAffected = ps.executeUpdate();

            if (rowsAffected > 0) {
                result = true;
            }

            ps.close();
        } catch (Exception ex) {
            Logger.getLogger(Ga_dao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    // cập nhật ga
    public boolean capNhatGa(GaTauEntity gaTau) {
        boolean result = false;
        try {
            ConnectDB.getInstance().connect();
            Connection con = ConnectDB.getConnection();
            PreparedStatement ps = null;
            String sql = "UPDATE banve.dbo.ga\n" + //
                    "SET ten = ?, maNV = ?, trangThai = ?\n" + //
                    "WHERE maGa = ?";
            ps = con.prepareStatement(sql);
            ps.setString(1, gaTau.getTenGa());
            ps.setString(2, gaTau.getNhanVien().getMaNV());
            ps.setInt(3, gaTau.getTrangThai());
            ps.setString(4, gaTau.getMaGa());

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                result = true;
            }

            ps.close();
        } catch (Exception ex) {
            Logger.getLogger(Ga_dao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    // check mã ga, tên ga đã tồn tại chưa
    public boolean kiemTraMaGa(String maGa) {
        boolean result = false;
        try {
            ConnectDB.getInstance().connect();
            Connection con = ConnectDB.getConnection();
            PreparedStatement ps = null;
            String sql = "SELECT COUNT(*) FROM banve.dbo.ga WHERE maGa = ? ";
            ps = con.prepareStatement(sql);
            ps.setString(1, maGa);
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

            Logger.getLogger(Ga_dao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    // check mã ga, tên ga đã tồn tại chưa
    public boolean kiemTraTenGa(String tenGa) {
        boolean result = false;
        try {
            ConnectDB.getInstance().connect();
            Connection con = ConnectDB.getConnection();
            PreparedStatement ps = null;
            String sql = "SELECT COUNT(*) FROM banve.dbo.ga WHERE tenGa = ? ";
            ps = con.prepareStatement(sql);
            ps.setString(1, tenGa);
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

            Logger.getLogger(Ga_dao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    public boolean doiTrangThaiGa(GaTauEntity ga) {
        boolean result = false;
        try {
            ConnectDB.getInstance().connect();
            Connection con = ConnectDB.getConnection();
            PreparedStatement ps = null;
            String sql = "UPDATE banve.dbo.ga\n" +
                    " SET trangThai = ? WHERE maGa = ?";
            con.prepareStatement(sql);

            ps = con.prepareStatement(sql);
            ps.setInt(1, ga.getTrangThai() == 1 ? 0 : 1);
            ps.setString(2, ga.getMaGa());

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                result = true;
            }
            ps.close();
        } catch (Exception ex) {

            Logger.getLogger(Ga_dao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
}
