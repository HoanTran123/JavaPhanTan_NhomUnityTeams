/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import Interface.ChiTietDoiTra_Interface;
import connectDB.ConnectDB;
import entity.ChiTietDoiTraEntity;
import entity.DoiTraEntity;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
//import util.ConvertStringToEnum;

/**
 *
 * @author ploc2
 */
public class ChiTietDoiTra_dao implements ChiTietDoiTra_Interface {

    public ChiTietDoiTra_dao() {

    }

    @Override
    public boolean themChiTietDoiTra(ChiTietDoiTraEntity ctdt) {
        PreparedStatement statement = null;
        try {
            ConnectDB.getInstance().connect();
            Connection con = ConnectDB.getConnection();

            String sql = "Insert into ChiTietDoiTra(maSP, maDT, soLuong, giaBan, thanhTien) values (?, ?, ?, ?, ?)";
            statement = con.prepareStatement(sql);
            // statement.setString(1, ctdt.getSanPham().getMaSP());
            statement.setString(2, ctdt.getDoiTra().getMaDT());
            statement.setInt(3, ctdt.getSoLuong());
            statement.setDouble(4, ctdt.getGiaBan());
            statement.setDouble(5, ctdt.getThanhTien());

            int ketQua = statement.executeUpdate();

            if (ketQua < 1) {
                return false;
            }

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            try {
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public ArrayList<ChiTietDoiTraEntity> getAllCTDTTheoMaDT(String ma) {
        try {
            ConnectDB.getInstance().connect();
        } catch (SQLException ex) {
            Logger.getLogger(HoaDon_dao.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        Connection con = ConnectDB.getConnection();
        PreparedStatement statement = null;

        try {
            String sql = "Select ctdt.*, sp.tenSP, sp.kichThuoc, sp.mauSac from ChiTietDoiTra as ctdt inner join SanPham as sp on ctdt.maSP=sp.maSP where maDT=?";
            statement = con.prepareStatement(sql);
            statement.setString(1, ma);

            ResultSet rs = statement.executeQuery();
            ArrayList<ChiTietDoiTraEntity> ctdtList = new ArrayList<ChiTietDoiTraEntity>();
            while (rs.next()) {
                String madt = rs.getString("maDT");
                DoiTraEntity dt = new DoiTraEntity(madt);
                String masp = rs.getString("maSP");
                String tensp = rs.getString("tenSP");
                String kichThuoc = rs.getString("kichThuoc");
                String mauSac = rs.getString("mauSac");
                // SanPhamEntity sp = new SanPhamEntity(masp);
                // sp.setTenSP(tensp);
                // ConvertStringToEnum toEnum = new ConvertStringToEnum();
                // sp.setKichThuoc(toEnum.KichThuoctoEnum(kichThuoc));
                // sp.setMauSac(toEnum.MauSactoEnum(mauSac));
                int soLuong = rs.getInt("soLuong");
                double giaBan = rs.getDouble("giaBan");
                double thanhTien = rs.getDouble("thanhTien");

                // ChiTietDoiTraEntity cthd = new ChiTietDoiTraEntity(sp, dt, soLuong, giaBan,
                // thanhTien);

                // ctdtList.add(cthd);
            }
            return ctdtList;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException ex) {
                    Logger.getLogger(HoaDon_dao.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
}
