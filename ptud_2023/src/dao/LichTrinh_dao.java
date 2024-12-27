package dao;

import connectDB.ConnectDB;
import entity.LichTrinhEntity;
import entity.TauEntity;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LichTrinh_dao {
    ConnectDB connect = new ConnectDB();

    public LichTrinhEntity layLichTrinhTheoMa(String id) {
        LichTrinhEntity lichTrinh = null;
        ResultSet rs = null;
        try {
            connect.connect();
            PreparedStatement statement = connect.getConnection()
                    .prepareStatement("SELECT * FROM LichTrinh WHERE maLichTrinh = ?");
            statement.setString(1, id);
            rs = statement.executeQuery();
            // if (rs.next()) {
            // TauEntity tau = new Tau_dao().findOne(rs.getString("maTau"));
            // lichTrinh = new LichTrinhEntity(
            // rs.getString("maLichTrinh"),
            // tau,
            // rs.getString("gaDi"),
            // rs.getString("gaDen"),
            // rs.getDate("ngayDi"),
            // rs.getDate("ngayDen"),
            // rs.getString("trangThai")
            // );
            // }
        } catch (SQLException ex) {
            Logger.getLogger(LichTrinh_dao.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return lichTrinh;
    }

    public boolean capNhat(LichTrinhEntity lichTrinh) {
        String sql = "UPDATE hanh_trinh\n" + //
                "SET gioDi=?, ngayDi=?, gioDen=?, ngayDen=?, loai=?, trangThai=?, ngayTao=getdate(), ngayCapNhat=getdate(), maTau=?\n"
                + //
                "WHERE maHT=?;";
        int n = 0;
        try {
            PreparedStatement statement = ConnectDB.getConnection().prepareStatement(sql);
            statement.setString(1, lichTrinh.getGioDi());
            statement.setDate(2, (lichTrinh.getNgayDi()));
            statement.setString(3, lichTrinh.getGioDen());
            statement.setDate(4, lichTrinh.getNgayDen());

            statement.setInt(5, lichTrinh.getLoai());
            statement.setInt(6, lichTrinh.getTrangThai());
            statement.setString(7, lichTrinh.getTau().getMaTau());

            statement.setInt(8, lichTrinh.getMaHT());
            n = statement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(LichTrinh_dao.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return n > 0;
    }

    public boolean them(LichTrinhEntity lichTrinh) {
        int n = 0;
        String sql = "INSERT INTO hanh_trinh\n" +
                "(gioDi, ngayDi, gioDen, ngayDen, loai, trangThai, ngayTao, ngayCapNhat, maTau)\n" +
                "VALUES( ?, ?, ?, ?, ?, ?, getdate(), getdate(), ?);";
        try {
            PreparedStatement statement = ConnectDB.getConnection().prepareStatement(sql);
            statement.setString(1, lichTrinh.getGioDi());
            statement.setDate(2, (lichTrinh.getNgayDi()));
            statement.setString(3, lichTrinh.getGioDen());
            statement.setDate(4, lichTrinh.getNgayDen());

            statement.setInt(5, lichTrinh.getLoai());
            statement.setInt(6, lichTrinh.getTrangThai());
            statement.setString(7, lichTrinh.getTau().getMaTau());
            n = statement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(LichTrinh_dao.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return n > 0;
    }

    public ArrayList<LichTrinhEntity> layAllHanhTrinh(int trangThai) {
        ArrayList<LichTrinhEntity> dsLichTrinh = new ArrayList<>();

        String sql = "SELECT * FROM hanh_trinh";

        if (trangThai != -1) {
            sql = "SELECT * FROM hanh_trinh WHERE trangThai = ?";
        }

        try {
            PreparedStatement statement = ConnectDB.getConnection().prepareStatement(sql);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                TauEntity tau = new TauEntity(rs.getString("maTau"));

                LichTrinhEntity lichTrinh = new LichTrinhEntity(
                        rs.getInt("maHt"),
                        tau,
                        rs.getString("gioDi"),
                        rs.getString("gioDen"),
                        rs.getDate("ngayDi"),
                        rs.getDate("ngayDen"),
                        rs.getInt("trangThai"),
                        rs.getInt("loai"));
                dsLichTrinh.add(lichTrinh);
            }
        } catch (SQLException ex) {
            Logger.getLogger(LichTrinh_dao.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return dsLichTrinh;
    }

    public ArrayList<LichTrinhEntity> timLichTrinhTheoTuyenDuong(String gaDi, String gaDen) {
        ArrayList<LichTrinhEntity> dsLichTrinh = new ArrayList<>();
        try {
            connect.connect();
            PreparedStatement statement = connect.getConnection()
                    .prepareStatement("SELECT * FROM LichTrinh WHERE gaDi = ? AND gaDen = ?");
            statement.setString(1, gaDi);
            statement.setString(2, gaDen);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                // TauEntity tau = new Tau_dao().findOne(rs.getString("maTau"));
                // LichTrinhEntity lichTrinh = new LichTrinhEntity(
                // rs.getString("maLichTrinh"),
                // tau,
                // rs.getString("gaDi"),
                // rs.getString("gaDen"),
                // rs.getDate("ngayDi"),
                // rs.getDate("ngayDen"),
                // rs.getString("trangThai")
                // );
                // dsLichTrinh.add(lichTrinh);
            }
        } catch (SQLException ex) {
            Logger.getLogger(LichTrinh_dao.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return dsLichTrinh;
    }

    public ArrayList<LichTrinhEntity> timLichTrinhTheoNgay(Date ngay) {
        ArrayList<LichTrinhEntity> dsLichTrinh = new ArrayList<>();
        try {
            connect.connect();
            PreparedStatement statement = connect.getConnection()
                    .prepareStatement("SELECT * FROM LichTrinh WHERE ngayDi = ?");
            statement.setDate(1, new java.sql.Date(ngay.getTime()));
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                // TauEntity tau = new Tau_dao().findOne(rs.getString("maTau"));
                // LichTrinhEntity lichTrinh = new LichTrinhEntity(
                // rs.getString("maLichTrinh"),
                // tau,
                // rs.getString("gaDi"),
                // rs.getString("gaDen"),
                // rs.getDate("ngayDi"),
                // rs.getDate("ngayDen"),
                // rs.getString("trangThai")
                // );
                // dsLichTrinh.add(lichTrinh);
            }
        } catch (SQLException ex) {
            Logger.getLogger(LichTrinh_dao.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return dsLichTrinh;
    }

    public ArrayList<LichTrinhEntity> timKiemLichTrinh(String gaDi, String gaDen, java.sql.Date ngayDi) {

        System.out.println("gaDi" + gaDi + "," + gaDen + ", " + ngayDi);
        ArrayList<LichTrinhEntity> dsLichTrinh = new ArrayList<>();
        try {
            PreparedStatement statement = ConnectDB.getConnection().prepareStatement(
                    "SELECT  ht.maHT, ht.gioDi, ht.ngayDi, ht.gioDen, ht.ngayDen, ht.loai, ht.trangThai, ht.ngayTao, ht.ngayCapNhat, t.maTau  FROM hanh_trinh ht LEFT JOIN  tau t ON ht.maTau = t.maTau WHERE ht.trangThai = 1 AND t.gaDi = ? AND t.gaDen = ? AND ht.ngayDi = ?;");

            statement.setString(1, gaDi);
            statement.setString(2, gaDen);
            statement.setDate(3, ngayDi);

            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                TauEntity tau = new TauEntity(rs.getString("maTau"));

                LichTrinhEntity lichTrinh = new LichTrinhEntity(
                        rs.getInt("maHT"),
                        tau,
                        rs.getString("gioDi"),
                        rs.getString("gioDen"),
                        rs.getDate("ngayDi"),
                        rs.getDate("ngayDen"),
                        rs.getInt("trangThai"),
                        rs.getInt("loai"));
                dsLichTrinh.add(lichTrinh);
            }
        } catch (SQLException ex) {
            Logger.getLogger(LichTrinh_dao.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return dsLichTrinh;
    }

    public ArrayList<LichTrinhEntity> getAllLichTrinh() {
        ArrayList<LichTrinhEntity> dsLichTrinh = new ArrayList<>();
        try {
            PreparedStatement statement = ConnectDB.getConnection().prepareStatement(
                    "SELECT maHT, gioDi, ngayDi, gioDen, ngayDen, loai, trangThai, ngayTao, ngayCapNhat, maTau\n" + //
                            "FROM hanh_trinh;");
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                TauEntity tau = new TauEntity(rs.getString("maTau"));

                LichTrinhEntity lichTrinh = new LichTrinhEntity(
                        rs.getInt("maHT"),
                        tau,
                        rs.getString("gioDi"),
                        rs.getString("gioDen"),
                        rs.getDate("ngayDi"),
                        rs.getDate("ngayDen"),
                        rs.getInt("trangThai"),
                        rs.getInt("loai"));
                dsLichTrinh.add(lichTrinh);
            }
        } catch (SQLException ex) {
            Logger.getLogger(LichTrinh_dao.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return dsLichTrinh;
    }
}