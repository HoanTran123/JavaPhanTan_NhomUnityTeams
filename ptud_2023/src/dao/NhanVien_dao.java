package dao;

import connectDB.ConnectDB;
import entity.*;
import util.EntityMapper;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class NhanVien_dao {
    private Connection con;
    public NhanVien_dao() {
        con = ConnectDB.getInstance().getConnection();
    }
    public ResultSet getResultSet(String StoreName)throws Exception {
        ResultSet rs = null;
        try {
            String callStore;
            callStore = "{Call " + StoreName +"}";
            CallableStatement cs = this.con.prepareCall(callStore);
            cs.executeQuery();
            rs = cs.getResultSet();
        } catch (Exception e) {
            throw new Exception("Error get Store " + e.getMessage());
        }
        return rs;
    }

    public NhanVienEntity dangNhap(String soDienThoai, String matKhau) {
        NhanVienEntity nv = null;
        PreparedStatement statement = null;
        try {
            if (con == null || con.isClosed()) {
                ConnectDB.getInstance().connect();
                con = ConnectDB.getInstance().getConnection();
            }

            String sql = "SELECT * FROM nhan_vien WHERE soDienThoai = ? AND matKhau = ?";
            statement = con.prepareStatement(sql);
            statement.setString(1, soDienThoai);
            statement.setString(2, matKhau);
            ResultSet rs = statement.executeQuery();
            
            if (rs.next()) {
                nv = new NhanVienEntity();
                nv.setMaNV(rs.getString("maNV"));
                nv.setTen(rs.getString("ten"));
                nv.setLoai(rs.getInt("loai"));
                nv.setGioiTinh(GioiTinhEnum.values()[rs.getInt("gioiTinh")]);
                nv.setEmail(rs.getString("email"));
                nv.setSoDienThoai(rs.getString("soDienThoai"));
                nv.setDiaChi(rs.getString("diaChi"));
                nv.setTrangThai(TinhTrangNVEnum.values()[rs.getInt("trangThai")]);
                nv.setNgayTao(rs.getDate("ngayTao"));
                nv.setNgayCapNhat(rs.getDate("ngayCapNhat"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return nv;
    }

    public Boolean checkNV(String email, String soDienThoai) {
        
        try (Connection con = ConnectDB.getConnection();
             PreparedStatement statement = con.prepareStatement("SELECT 1 FROM banve.dbo.nhan_vien WHERE email = ? AND soDienThoai = ?")) {
            
            statement.setString(1, email);
            statement.setString(2, soDienThoai);
            
            try (ResultSet rs = statement.executeQuery()) {
                return rs.next(); // Returns true if a record is found
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return false;
    }


    public NhanVienEntity getNV(String soDienThoai) {
        NhanVienEntity nv = null;
        PreparedStatement statement = null;
        try {
            statement = con.prepareStatement("SELECT * FROM nhan_vien WHERE soDienThoai = ?");
            statement.setString(1, soDienThoai);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                nv = new NhanVienEntity();
                nv.setMaNV(rs.getString("maNV"));
                nv.setTen(rs.getString("ten"));
                nv.setLoai(rs.getInt("loai"));
                nv.setGioiTinh(GioiTinhEnum.values()[rs.getInt("gioiTinh")]);
                nv.setEmail(rs.getString("email"));
                nv.setSoDienThoai(rs.getString("soDienThoai"));
                nv.setDiaChi(rs.getString("diaChi"));
                nv.setTrangThai(TinhTrangNVEnum.values()[rs.getInt("trangThai")]);
                nv.setNgayTao(rs.getDate("ngayTao"));
                nv.setNgayCapNhat(rs.getDate("ngayCapNhat"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        
        }
        return nv;
    }

    public NhanVienEntity findOne(String maNV) {
        NhanVienEntity nv = null;
        PreparedStatement statement = null;
        try {
            statement = con.prepareStatement("SELECT * FROM nhan_vien WHERE maNV = ?");
            statement.setString(1, maNV);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                nv = new NhanVienEntity();
                nv.setMaNV(rs.getString("maNV"));
                nv.setTen(rs.getString("ten"));
                nv.setLoai(rs.getInt("loai"));
                nv.setGioiTinh(GioiTinhEnum.values()[rs.getInt("gioiTinh")]);
                nv.setEmail(rs.getString("email"));
                nv.setSoDienThoai(rs.getString("soDienThoai"));
                nv.setDiaChi(rs.getString("diaChi"));
                nv.setTrangThai(TinhTrangNVEnum.values()[rs.getInt("trangThai")]);
                nv.setNgayTao(rs.getDate("ngayTao"));
                nv.setNgayCapNhat(rs.getDate("ngayCapNhat"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } 
        
        return nv;
    }

    public boolean update(NhanVienEntity newNV) {
        PreparedStatement statement = null;
        String sql = "UPDATE nhan_vien SET ten = ?, loai = ?, gioiTinh = ?, email = ?, soDienThoai = ?, diaChi = ?, trangThai = ?, ngayCapNhat = ? WHERE maNV = ?";
        
        try {
            statement = con.prepareStatement(sql);
            
            statement.setString(1, newNV.getTen());
            statement.setInt(2, newNV.getLoai());
            statement.setInt(3, newNV.getGioiTinh().ordinal());
            statement.setString(4, newNV.getEmail());
            statement.setString(5, newNV.getSoDienThoai());
            statement.setString(6, newNV.getDiaChi());
            statement.setInt(7, newNV.getTrangThai().ordinal());
            statement.setDate(8, new java.sql.Date(newNV.getNgayCapNhat().getTime()));
            statement.setString(9, newNV.getMaNV());

            int rowsAffected = statement.executeUpdate();
            
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean insert(NhanVienEntity nv) {
        String sql = "INSERT INTO nhan_vien (maNV, ten, loai, gioiTinh, email, soDienThoai, diaChi, trangThai, ngayTao, ngayCapNhat) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                     
        try (PreparedStatement statement = con.prepareStatement(sql)) {
            statement.setString(1, truncate(nv.getMaNV(), 14));
            statement.setNString(2, truncate(nv.getTen(), 100));
            statement.setInt(3, nv.getLoai());
            statement.setInt(4, nv.getGioiTinh().ordinal());
            statement.setNString(5, truncate(nv.getEmail(), 100));
            statement.setNString(6, truncate(nv.getSoDienThoai(), 11));
            statement.setNString(7, truncate(nv.getDiaChi(), 255));
            statement.setInt(8, nv.getTrangThai().ordinal());
            statement.setTimestamp(9, new java.sql.Timestamp(nv.getNgayTao().getTime()));
            statement.setTimestamp(10, new java.sql.Timestamp(nv.getNgayCapNhat().getTime()));
            
            return statement.executeUpdate() > 0;
            
        } catch (SQLException e) {
            if (e.getMessage().contains("UQ_nhan_vien_email")) {
                System.err.println("Email đã tồn tại trong hệ thống: " + nv.getEmail());
            }
            e.printStackTrace();
            return false;
        }
    }

    private String truncate(String value, int maxLength) {
        if (value != null && value.length() > maxLength) {
            return value.substring(0, maxLength);
        }
        return value;
    }

    public ArrayList<NhanVienEntity> findAll() {
        ArrayList<NhanVienEntity> listNV = new ArrayList<>();
        try {
        	String sql = "SELECT * FROM nhan_vien";
            PreparedStatement stmt = con.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                NhanVienEntity nv = new NhanVienEntity();
                nv.setMaNV(rs.getString("maNV"));
                nv.setTen(rs.getString("ten"));
                nv.setLoai(rs.getInt("loai"));
                int gioiTinh = rs.getInt("gioiTinh");
                nv.setGioiTinh(GioiTinhEnum.values()[gioiTinh]);
                nv.setEmail(rs.getString("email"));
                nv.setSoDienThoai(rs.getString("soDienThoai"));
                nv.setDiaChi(rs.getString("diaChi"));
                int trangThai = rs.getInt("trangThai");
                if (trangThai >= 0 && trangThai < TinhTrangNVEnum.values().length) {
                    nv.setTrangThai(TinhTrangNVEnum.values()[trangThai]);
                } else {
                    nv.setTrangThai(null);
                }
                nv.setNgayTao(rs.getDate("ngayTao"));
                nv.setNgayCapNhat(rs.getDate("ngayCapNhat"));
             
                listNV.add(nv);
                
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listNV;
    }

}
