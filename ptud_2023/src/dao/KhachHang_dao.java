package dao;

import connectDB.ConnectDB;
import entity.GioiTinhEnum;
import entity.KhachHangEntity;
import entity.NhanVienEntity;
import entity.TinhTrangNVEnum;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import Interface.KhachHang_Interface;
import util.ConvertStringToEnum;

public class KhachHang_dao {
    private Connection con;

    public KhachHang_dao() {
        con = ConnectDB.getInstance().getConnection();
    }

    public ResultSet getResultSet(String StoreName) throws Exception {
        ResultSet rs = null;
        try {
            String callStore;
            callStore = "{Call " + StoreName + "}";
            CallableStatement cs = this.con.prepareCall(callStore);
            cs.executeQuery();
            rs = cs.getResultSet();
        } catch (Exception e) {
            throw new Exception("Error get Store " + e.getMessage());
        }
        return rs;
    }

    public KhachHangEntity findOne(String id) {
        KhachHangEntity khachHang = null;
        PreparedStatement statement = null;

        try {
            statement = con.prepareStatement("SELECT * FROM hanh_khach WHERE maHK = ?");
            statement.setString(1, id);
            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                khachHang = new KhachHangEntity();
                khachHang.setMaKH(rs.getString("maHK"));
                khachHang.setHoTen(rs.getString("ten"));
                khachHang.setGioiTinh(GioiTinhEnum.values()[rs.getInt("gioiTinh")]);
                khachHang.setSoDienThoai(rs.getString("soDienThoai"));
                khachHang.setDiaChi(rs.getString("diaChi"));
                khachHang.setSoCCCD(rs.getString("soCmnd"));
                khachHang.setNgayTao(rs.getDate("ngayTao"));
                khachHang.setNgayCapNhat(rs.getDate("ngayCapNhat"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (statement != null)
                    statement.close();
                con.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return khachHang;
    }

    public boolean update(KhachHangEntity newKH) {
        PreparedStatement statement = null;
        String sql = "UPDATE hanh_khach SET ten = ?, gioiTinh = ?, soDienThoai = ?, diaChi = ?, soCmnd = ?, ngayCapNhat = ? WHERE maHK = ?";

        try {
            statement = con.prepareStatement(sql);

            statement.setString(1, newKH.getHoTen());
            statement.setInt(2, newKH.getGioiTinh().ordinal());
            statement.setString(3, newKH.getSoDienThoai());
            statement.setString(4, newKH.getDiaChi());
            statement.setString(5, newKH.getSoCCCD());
            statement.setDate(6, new java.sql.Date(newKH.getNgayCapNhat().getTime()));
            statement.setString(7, newKH.getMaKH());

            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean insert(KhachHangEntity KH) {
        PreparedStatement statement = null;
        String sql = "INSERT INTO hanh_khach(maHK, ten, gioiTinh, soDienThoai, diaChi, soCmnd, ngayTao, ngayCapNhat) VALUES(?, ?, ?, ?, ?, ?, ?, ?)";

        try {
            statement = con.prepareStatement(sql);
            statement.setString(1, KH.getMaKH());
            statement.setString(2, KH.getHoTen());
            statement.setInt(3, KH.getGioiTinh().ordinal());
            statement.setString(4, KH.getSoDienThoai());
            statement.setString(5, KH.getDiaChi());
            statement.setString(6, KH.getSoCCCD());
            statement.setDate(7, new java.sql.Date(KH.getNgayTao().getTime()));
            statement.setDate(8, new java.sql.Date(KH.getNgayCapNhat().getTime()));

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean themKH(KhachHangEntity KH) {
        System.out.println("HK THEM HK" + KH);
        PreparedStatement statement = null;
        String sql = "INSERT INTO hanh_khach(maHK, ten, soDienThoai, soCmnd, tuoi) VALUES(?, ?, ?, ? , ?)";

        try {
            statement = con.prepareStatement(sql);
            statement.setString(1, KH.getMaKH());
            statement.setString(2, KH.getHoTen());
            String soDienThoai = KH.getSoDienThoai();

            if (soDienThoai != null) {
                statement.setString(3, soDienThoai);
            } else {
                statement.setString(3, ""); // Or handle the null case as desired
            }
            statement.setString(4, KH.getSoCCCD());

            int tuoi = KH.getTuoi();

            if (tuoi > 0) {
                statement.setInt(5, tuoi);
            } else {
                statement.setInt(5, 0); // Or handle the null case as desired
            }

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public ArrayList<KhachHangEntity> findAll() {
        ArrayList<KhachHangEntity> listKH = new ArrayList<>();

        try {
            PreparedStatement statement = con.prepareStatement("SELECT * FROM hanh_khach");
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                KhachHangEntity khachHang = new KhachHangEntity();
                khachHang.setMaKH(rs.getString("maHK"));
                khachHang.setHoTen(rs.getString("ten"));
                khachHang.setGioiTinh(GioiTinhEnum.values()[rs.getInt("gioiTinh")]);
                khachHang.setSoDienThoai(rs.getString("soDienThoai"));
                khachHang.setDiaChi(rs.getString("diaChi"));
                khachHang.setSoCCCD(rs.getString("soCmnd"));
                khachHang.setNgayTao(rs.getDate("ngayTao"));
                khachHang.setNgayCapNhat(rs.getDate("ngayCapNhat"));
                listKH.add(khachHang);
            }
            System.out.println("Số lượng nhân viên: " + listKH.size());
            for (KhachHangEntity khachHang : listKH) {
                System.out.println(khachHang.toString());
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listKH;
    }

    public KhachHangEntity timKiemTheoSDT(String sdt) {
        KhachHangEntity khachHang = null;
        PreparedStatement statement = null;

        try {
            statement = con.prepareStatement("SELECT * FROM hanh_khach WHERE soDienThoai = ?");
            statement.setString(1, sdt);
            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                khachHang = new KhachHangEntity();
                khachHang.setMaKH(rs.getString("maHK"));
                khachHang.setHoTen(rs.getString("ten"));
                khachHang.setGioiTinh(GioiTinhEnum.values()[rs.getInt("gioiTinh")]);
                khachHang.setSoDienThoai(rs.getString("soDienThoai"));
                khachHang.setDiaChi(rs.getString("diaChi"));
                khachHang.setSoCCCD(rs.getString("soCmnd"));
                khachHang.setNgayTao(rs.getDate("ngayTao"));
                khachHang.setNgayCapNhat(rs.getDate("ngayCapNhat"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (statement != null)
                    statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return khachHang;
    }

    public KhachHangEntity getKhachHang(String soDienThoai) {
        KhachHangEntity kh = null;
        PreparedStatement statement = null;
        try {
            statement = con.prepareStatement("SELECT * FROM hanh_khach WHERE soDienThoai = ?");
            statement.setString(1, soDienThoai);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                kh = new KhachHangEntity();
                kh.setMaKH(rs.getString(1));
                kh.setHoTen(rs.getString(2));
                kh.setGioiTinh(GioiTinhEnum.values()[rs.getInt(3)]);
                kh.setSoDienThoai(rs.getString(4));
                kh.setDiaChi(rs.getString(5));
                kh.setSoCCCD(rs.getString(6));
                kh.setNgayTao(rs.getDate(7));
                kh.setNgayCapNhat(rs.getDate(8));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (statement != null)
                    statement.close();
                con.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return kh;
    }

    public boolean checkKhachHang(String cccd) {
        boolean result = false;
        PreparedStatement statement = null;
        try {
            statement = con.prepareStatement("SELECT * FROM hanh_khach WHERE soCmnd = ?");
            statement.setString(1, cccd);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                result = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return result;
        }	

        return result;
    }

}