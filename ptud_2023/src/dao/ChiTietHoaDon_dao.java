package dao;

import connectDB.ConnectDB;
import entity.ChiTietHoaDonEntity;
import entity.KhachHangEntity;
import entity.TauEntity;
import entity.ToaTauEntity;
import entity.VeEntity;
import java.util.ArrayList;
import java.sql.*;

public class ChiTietHoaDon_dao {

    public ChiTietHoaDon_dao() {
    }

    public ArrayList<ChiTietHoaDonEntity> getallCTHD() {
        ArrayList<ChiTietHoaDonEntity> dscthd = new ArrayList<>();
        try {
            // Connection con = ConnectDB.getConnection();
            // Statement stmt = con.prepareStatement("SELECT maCTHD, ngayTao, ngayCapNhat,
            // maHK, maDH, maVe\n" + //
            // "FROM chi_tiet_don_hang where maDH = ?;");
            // stmt.setString(1, maDH);

            // ResultSet rs = stmt.executeQuery();

            // while (rs.next()) {
            // VeEntity ve = new VeEntity(rs.getString("maSP"), rs.getInt("loai"),
            // rs.getInt("trangThai"), rs.getDouble("gia"), rs.getDate("ngayTao"),
            // rs.getDate("ngayCapNhat"), new TauEntity(rs.getString("maTau")), new
            // ToaTauEntity(rs.getString("maToa")), new GheEntity(rs.getString("maGhe")));
            // HoaDonEntity hd = new HoaDonEntity(rs.getString("maHD"));
            // int sl = rs.getInt("soLuong");
            // double giaBan = rs.getDouble("giaBan");
            // double thanhTien = rs.getDouble("thanhTien");
            // double giaGoc = rs.getDouble("giaGoc");
            //
            // ChiTietHoaDonEntity cthd = new ChiTietHoaDonEntity(ve, hd, sl, giaGoc,
            // giaBan, thanhTien);
            // dscthd.add(cthd);
            // }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dscthd;
    }

    public ArrayList<ChiTietHoaDonEntity> getallCTHDVoiMaHD(String maDH) {
        ArrayList<ChiTietHoaDonEntity> dscthd = new ArrayList<>();
        try {
            Connection con = ConnectDB.getConnection();
            PreparedStatement stmt = con.prepareStatement(
                    "SELECT tau.ten as tenTau, toa_tau.ten as tenToa, ctdh.maCTHD, ctdh.ngayTao, ctdh.ngayCapNhat, ctdh.maHK, ctdh.maDH, ctdh.maVe, hk.ten, hk.tuoi, ve.gia, ve.soGhe   FROM chi_tiet_don_hang ctdh left join hanh_khach hk  on hk.maHK = ctdh.maHK  LEFT join ve  on ve.maVe  = ctdh.maVe left join tau  on tau.maTau = ve.maTau  left join toa_tau on ve.maToa = toa_tau.maToa where ctdh.maDH = ?;");
            stmt.setString(1, maDH);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {

                VeEntity ve = new VeEntity();
                ChiTietHoaDonEntity cthd = new ChiTietHoaDonEntity();
                cthd.setGiaBan(rs.getDouble("gia"));
                cthd.setMaCTDH(rs.getString("maCTHD"));
                cthd.setNgayTao(rs.getDate("ngayTao"));
                cthd.setNgayCapNhat(rs.getDate("ngayCapNhat"));

                ve.setMaVe(rs.getString("maVe"));
                ve.setGia(rs.getDouble("gia"));
                ve.setSoGhe(rs.getInt("soGhe"));

                TauEntity tau = new TauEntity();
                // tau.setMaTau(rs.getString("maTau"));
                tau.setTenTau(rs.getString("tenTau"));

                ve.setTau(tau);

                ToaTauEntity toa = new ToaTauEntity();
                toa.setTenToa(rs.getString("tenToa"));

                ve.setToa(toa);

                cthd.setVe(ve);

                KhachHangEntity hk = new KhachHangEntity();
                hk.setMaKH(rs.getString("maHK"));
                hk.setHoTen(rs.getString("ten"));
                hk.setTuoi(rs.getInt("tuoi"));

                System.out.println(hk);

                cthd.setKhachHang(hk);

                dscthd.add(cthd);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dscthd;
    }

    public ArrayList<VeEntity> getSanPhamTheoMaVe(String maVe) {
        ArrayList<VeEntity> dsVe = new ArrayList<>();
        String sql = "SELECT * FROM SanPham WHERE maSP = ?";

        try (Connection con = ConnectDB.getConnection();
                PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setString(1, maVe);
            try (ResultSet rs = stmt.executeQuery()) {
                // while (rs.next()) {
                // VeEntity ve = new VeEntity(
                // rs.getString("maSP"),
                // rs.getInt("loai"),
                // rs.getInt("trangThai"),
                // rs.getDouble("gia"),
                // rs.getDate("ngayTao"),
                // rs.getDate("ngayCapNhat"),
                // new TauEntity(rs.getString("maTau")),
                // new ToaTauEntity(rs.getString("maToa")),
                // new GheEntity(rs.getString("maGhe"))
                // );
                // dsVe.add(ve);
                // }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dsVe;
    }

    public ArrayList<VeEntity> getSanPhamTheoMaHD(String maHD) {
        ArrayList<VeEntity> dsVe = new ArrayList<>();
        String sql = "SELECT * FROM SanPham WHERE maSP IN (SELECT maSP FROM ChiTietHoaDon WHERE maHD = ?)";

        try (Connection con = ConnectDB.getConnection();
                PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setString(1, maHD);
            try (ResultSet rs = stmt.executeQuery()) {
                // while (rs.next()) {
                // VeEntity ve = new VeEntity(
                // rs.getString("maSP"),
                // rs.getInt("loai"),
                // rs.getInt("trangThai"),
                // rs.getDouble("gia"),
                // rs.getDate("ngayTao"),
                // rs.getDate("ngayCapNhat"),
                // new TauEntity(rs.getString("maTau")),
                // new ToaTauEntity(rs.getString("maToa")),
                // new GheEntity(rs.getString("maGhe"))
                // );
                // dsVe.add(ve);
                // }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dsVe;
    }

    public boolean themChiTietHoaDon(ChiTietHoaDonEntity cthd) {
        String sql = "INSERT INTO chi_tiet_don_hang\n" + //
                "( ngayTao, ngayCapNhat, maHK, maDH, maVe)\n" + //
                " VALUES(getdate(), getdate(), ?, ?, ?);";

        try {
            Connection con = ConnectDB.getConnection();
            PreparedStatement stmt = con.prepareStatement(sql);

            stmt.setString(1, cthd.getKhachHang().getMaKH());
            stmt.setString(2, cthd.getHoaDon().getMaHD());
            stmt.setString(3, cthd.getVe().getMaVe());

            int ketQua = stmt.executeUpdate();
            return ketQua > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean xoaCTHDTheoMaHoaDon(String maHD) {
        String sql = "DELETE FROM ChiTietHoaDon WHERE maHD=?";

        try (Connection con = ConnectDB.getConnection();
                PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setString(1, maHD);
            int ketQua = stmt.executeUpdate();
            return ketQua > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public ArrayList<ChiTietHoaDonEntity> getAllCTHDTheoMaHD(String maHD) {
        ArrayList<ChiTietHoaDonEntity> cthdList = new ArrayList<>();
        String sql = "SELECT cthd.*, sp.loai, sp.trangThai, sp.gia, sp.ngayTao, sp.ngayCapNhat, sp.maTau, sp.maToa, sp.maGhe FROM ChiTietHoaDon AS cthd INNER JOIN SanPham AS sp ON cthd.maSP=sp.maSP WHERE maHD=?";

        try (Connection con = ConnectDB.getConnection();
                PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setString(1, maHD);
            try (ResultSet rs = stmt.executeQuery()) {
                // while (rs.next()) {
                // HoaDonEntity hd = new HoaDonEntity(rs.getString("maHD"));
                // VeEntity ve = new VeEntity(
                // rs.getString("maSP"),
                // rs.getInt("loai"),
                // rs.getInt("trangThai"),
                // rs.getDouble("gia"),
                // rs.getDate("ngayTao"),
                // rs.getDate("ngayCapNhat"),
                // new TauEntity(rs.getString("maTau")),
                // new ToaTauEntity(rs.getString("maToa")),
                // new GheEntity(rs.getString("maGhe"))
                // );
                //
                // ChiTietHoaDonEntity cthd = new ChiTietHoaDonEntity(
                // ve, hd, rs.getInt("soLuong"), rs.getDouble("giaGoc"),
                // rs.getDouble("giaBan"), rs.getDouble("thanhTien")
                // );
                // cthdList.add(cthd);
                // }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cthdList;
    }

    public int getSoLuongCTHD(String maSP) {
        String sql = "SELECT SUM(cthd.soLuong) AS tongSoLuong FROM ChiTietHoaDon AS cthd INNER JOIN HoaDon AS hd ON cthd.maHD=hd.maHD WHERE cthd.maSP=? AND hd.tinhTrang=N'Chưa thanh toán'";

        try (Connection con = ConnectDB.getConnection();
                PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setString(1, maSP);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("tongSoLuong");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int soluongSP(String maHD, String maSP) {
        String sql = "SELECT soLuong FROM ChiTietHoaDon WHERE maHD = ? AND maSP = ?";

        try (Connection con = ConnectDB.getConnection();
                PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setString(1, maHD);
            stmt.setString(2, maSP);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("soLuong");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public ArrayList<ChiTietHoaDonEntity> getCTHDTheoMaHDvaMaSP(String maHD, String maSP) {
        ArrayList<ChiTietHoaDonEntity> dscthd = new ArrayList<>();
        String sql = "SELECT cthd.*, sp.loai, sp.trangThai, sp.gia, sp.ngayTao, sp.ngayCapNhat, sp.maTau, sp.maToa, sp.maGhe FROM ChiTietHoaDon AS cthd INNER JOIN SanPham AS sp ON cthd.maSP=sp.maSP WHERE cthd.maHD = ? AND cthd.maSP = ?";

        try (Connection con = ConnectDB.getConnection();
                PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setString(1, maHD);
            stmt.setString(2, maSP);
            try (ResultSet rs = stmt.executeQuery()) {
                // while (rs.next()) {
                // VeEntity ve = new VeEntity(
                // rs.getString("maSP"),
                // rs.getInt("loai"),
                // rs.getInt("trangThai"),
                // rs.getDouble("gia"),
                // rs.getDate("ngayTao"),
                // rs.getDate("ngayCapNhat"),
                // new TauEntity(rs.getString("maTau")),
                // new ToaTauEntity(rs.getString("maToa")),
                // new GheEntity(rs.getString("maGhe"))
                // );
                // HoaDonEntity hd = new HoaDonEntity(rs.getString("maHD"));
                // ChiTietHoaDonEntity cthd = new ChiTietHoaDonEntity(
                // ve, hd, rs.getInt("soLuong"), rs.getDouble("giaGoc"),
                // rs.getDouble("giaBan"), rs.getDouble("thanhTien")
                // );
                // dscthd.add(cthd);
                // }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dscthd;
    }
}
