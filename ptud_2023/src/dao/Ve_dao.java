/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import connectDB.ConnectDB;
import entity.VeEntity;

/**
 *
 * @author ploc2
 */
public class Ve_dao {

    private KhachHang_dao khachHang_dao = new KhachHang_dao();

    public boolean themVe(VeEntity ve) {
        System.out.println("ve" + ve);
        PreparedStatement statement = null;
        try {
            Connection con = ConnectDB.getConnection();
            System.out.println("kahcHAng" + ve.getKhachHang());
            khachHang_dao.themKH(ve.getKhachHang());

            String sql = "INSERT INTO ve\n" + //
                    "(maLoaiVe, trangThai, gia, ngayTao, ngayCapNhat, maHT, maTau, maToa, maGhe, maHK, soGhe, maVe)\n" + //
                    "VALUES(1, 1, ?, getdate(), getdate(), ?, ?, ?, ?, ?, ?, ?);";
            statement = con.prepareStatement(sql);
            statement.setDouble(1, ve.getGia());
            statement.setInt(2, ve.getLichTrinh().getMaHT());
            statement.setString(3, ve.getTau().getMaTau());
            statement.setString(4, ve.getToa().getMaToa());
            statement.setInt(5, ve.getGhe().getMaGhe());
            statement.setString(6, ve.getKhachHang().getMaKH());
            statement.setInt(7, ve.getSoGhe());
            statement.setString(8, ve.getMaVe());

            int ketQua = statement.executeUpdate();

            if (ketQua < 1) {
                return false;
            }

            return true;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
