package dao;

import entity.HoaDonEntity;
import entity.KhachHangEntity;
import entity.TauEnum;

import java.util.ArrayList;
import connectDB.ConnectDB;
import entity.ChiTietHoaDonEntity;
import entity.NhanVienEntity;
import java.sql.Date;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HoaDon_dao {

    public HoaDon_dao() {

    }

    public ArrayList<HoaDonEntity> getallHoaDon() {
        ArrayList<HoaDonEntity> dshd = new ArrayList<>();
        try {
            Connection con = ConnectDB.getConnection();
            if (con == null) {
                System.out.println("Kết nối database thất bại!");
                return dshd;
            }

            String sql = "SELECT dh.maDH, dh.tongTien, dh.trangThai, dh.ngayTao, dh.ngayCapNhat, " +
                        "hk.maHK, hk.ten AS tenHK, hk.soDienThoai " +
                        "FROM don_hang dh " +
                        "LEFT JOIN hanh_khach hk ON dh.maHK = hk.maHK " +
                        "ORDER BY dh.ngayTao DESC";

            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                try {
                    // Lấy thông tin khách hàng
                    String maKH = rs.getString("maHK");
                    KhachHangEntity khachHang = null;
                    if (maKH != null) {
                        khachHang = new KhachHangEntity(maKH);
                        khachHang.setHoTen(rs.getString("tenHK"));
                        khachHang.setSoDienThoai(rs.getString("soDienThoai"));
                    }

                    // Tạo đối tượng hóa đơn
                    HoaDonEntity hd = new HoaDonEntity(
                        rs.getString("maDH"),
                        khachHang,
                        rs.getDouble("tongTien"),
                        "Tiền mặt", // Mặc định phương thức thanh toán
                        rs.getInt("trangThai"),
                        rs.getDate("ngayTao"),
                        rs.getDate("ngayCapNhat")
                    );

                    dshd.add(hd);
                    System.out.println("Đã thêm hóa đơn: " + hd.getMaHD());
                } catch (Exception e) {
                    System.err.println("Lỗi khi xử lý hóa đơn: " + e.getMessage());
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            System.err.println("Lỗi truy vấn database: " + e.getMessage());
            e.printStackTrace();
        }
        return dshd;
    }

    public HoaDonEntity getHoaDonTheoMaHD(String maHD) {
        HoaDonEntity hd = null;
        try {
            Connection con = ConnectDB.getConnection();
            PreparedStatement stmt = con.prepareCall(
                    "SELECT  dh.maDH, dh.tongTien, dh.gia, dh.trangThai, dh.ngayTao, dh.ngayCapNhat, dh.maHK, nv.maNV, nv.ten AS tenNV,  kh.maHK, kh.soDienThoai,  kh.ten AS tenHK FROM  don_hang dh JOIN     nhan_vien nv ON dh.maNV = nv.maNV JOIN hanh_khach kh ON dh.maHK = kh.maHK WHERE  dh.trangThai = 1 and dh.maDH = ?  order by dh.ngayTao DESC;");
            stmt.setString(1, maHD);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String mahd = rs.getString("maDH");
                String makh = rs.getString("maHK");
                KhachHangEntity kh = new KhachHangEntity(makh);
                kh.setHoTen(rs.getString("tenHK"));
                kh.setSoDienThoai(rs.getString("soDienThoai"));
                double tongTien = rs.getDouble("tongTien");
                int trangThai = rs.getInt("trangThai");
                Date ngayTao = rs.getDate("ngayTao");
                Date ngayCapNhat = rs.getDate("NgayCapNhat");

                NhanVienEntity nv = new NhanVienEntity();
                nv.setMaNV(rs.getString("maNV"));
                nv.setTen(rs.getString("tenNV"));

                hd = new HoaDonEntity(mahd, kh, tongTien, trangThai, ngayTao, ngayCapNhat);
                hd.setNhanVien(nv);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return hd;
    }

    public double getTotalMoney(String maHD) {
        double total = 0;
        String sql = "select tongTien from HoaDon where maHD = ? ";
        try {
            Connection con = ConnectDB.getConnection();
            PreparedStatement sta = null;
            sta = con.prepareStatement(sql);
            sta.setString(1, maHD);
            ResultSet rs = sta.executeQuery();
            while (rs.next()) {
                total = rs.getInt("tongTien");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return total;
    }

    public boolean themHoaDon(HoaDonEntity hoaDon, ArrayList<ChiTietHoaDonEntity> danhSachCTHD) {
        PreparedStatement statement = null;
        try {
            Connection con = ConnectDB.getConnection();

            String sql = "INSERT INTO don_hang\n" + //
                    "(maDH, tongTien, gia, trangThai, ngayTao, ngayCapNhat, maHK, maNV, maTT, maKM)\n" + //
                    "VALUES(?, ?, ?, ?, getdate(), getdate(), ?, ?, ?, ?);";
            statement = con.prepareStatement(sql);

            statement.setString(1, hoaDon.getMaHD());
            // statement.setString(2, hoaDon.getKhachHang().getMaKH());
            // statement.setDouble(3, hoaDon.getTongTien());
            // statement.setInt(4, hoaDon.getPhuongThucThanhToan());
            // statement.setString(5, hoaDon.getTrangThai().toString());
            statement.setDate(6, new java.sql.Date(hoaDon.getNgayTao().getTime()));
            statement.setDate(7, new java.sql.Date(hoaDon.getNgayCapNhat().getTime()));

            int ketQua = statement.executeUpdate();
            if (ketQua < 1) {
                return false;
            }

            ChiTietHoaDon_dao cthd_dao = new ChiTietHoaDon_dao();
            for (ChiTietHoaDonEntity cthd : danhSachCTHD) {
                if (!cthd_dao.themChiTietHoaDon(cthd)) {
                    return false;
                }
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

    public boolean themHoaDon2(HoaDonEntity hoaDon) {
        System.out.println("Hoa don" + hoaDon);
        PreparedStatement statement = null;
        try {
            Connection con = ConnectDB.getConnection();

            String sql = "INSERT INTO don_hang\n" + //
                    "(maDH, tongTien, trangThai, ngayTao, ngayCapNhat, maHK, maNV, maTT, maKM)\n" + //
                    "VALUES(?, ?, ?, getdate(), getdate(), ?, ?, ?, ?);";
            statement = con.prepareStatement(sql);

            statement.setString(1, hoaDon.getMaHD());
            statement.setDouble(2, hoaDon.getTongTien());
            statement.setDouble(3, hoaDon.getTrangThai());
            statement.setString(4, hoaDon.getMaKH().getMaKH());
            statement.setString(5, hoaDon.getNhanVien().getMaNV());
            statement.setInt(6, 2);
            statement.setString(7, "1");

            int ketQua = statement.executeUpdate();
            if (ketQua < 1) {
                return false;
            }

            // ChiTietHoaDon_dao cthd_dao = new ChiTietHoaDon_dao();
            // for (ChiTietHoaDonEntity cthd : danhSachCTHD) {
            // if (!cthd_dao.themChiTietHoaDon(cthd)) {
            // return false;
            // }
            // }
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

    public boolean luuTamHoaDon(HoaDonEntity hoaDon, ArrayList<ChiTietHoaDonEntity> danhSachCTHD) {
        PreparedStatement statement = null;
        try {
            ConnectDB.getInstance().connect();
            Connection con = ConnectDB.getConnection();

            String sql = "Insert into HoaDon(maHD, maKH, tongTien, phuongThucThanhToan, trangThai, ngayTao, NgayCapNhat) values (?, ?, ?, ?, ?, ?, ?)";
            statement = con.prepareStatement(sql);

            statement.setString(1, hoaDon.getMaHD());
            // statement.setString(2, hoaDon.getKhachHang().getMaKH());
            statement.setDouble(3, hoaDon.getTongTien());
            // statement.setInt(4, hoaDon.getPhuongThucThanhToan());
            statement.setString(5, "Chưa thanh toán");
            statement.setDate(6, new java.sql.Date(hoaDon.getNgayTao().getTime()));
            statement.setDate(7, new java.sql.Date(hoaDon.getNgayCapNhat().getTime()));

            int ketQua = statement.executeUpdate();
            if (ketQua < 1) {
                return false;
            }

            ChiTietHoaDon_dao cthd_dao = new ChiTietHoaDon_dao();
            for (ChiTietHoaDonEntity cthd : danhSachCTHD) {
                if (!cthd_dao.themChiTietHoaDon(cthd)) {
                    return false;
                }
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean capNhatHoaDonLuuTam(HoaDonEntity hoaDon, ArrayList<ChiTietHoaDonEntity> danhSachCTHD) {
        PreparedStatement statement = null;
        try {
            ConnectDB.getInstance().connect();
            Connection con = ConnectDB.getConnection();

            String sql = "Update HoaDon set tongTien=?, phuongThucThanhToan=?, trangThai=?, NgayCapNhat=? where maHD=? and maKH=?";
            statement = con.prepareStatement(sql);

            statement.setDouble(1, hoaDon.getTongTien());
            // statement.setInt(2, hoaDon.getPhuongThucThanhToan());
            statement.setString(3, "Chưa thanh toán");
            statement.setDate(4, new java.sql.Date(hoaDon.getNgayCapNhat().getTime()));
            statement.setString(5, hoaDon.getMaHD());
            // statement.setString(6, hoaDon.getKhachHang().getMaKH());

            int ketQua = statement.executeUpdate();
            if (ketQua < 1) {
                return false;
            }

            ChiTietHoaDon_dao cthd_dao = new ChiTietHoaDon_dao();
            boolean kq = cthd_dao.xoaCTHDTheoMaHoaDon(hoaDon.getMaHD());

            if (kq) {
                for (ChiTietHoaDonEntity cthd : danhSachCTHD) {
                    if (!cthd_dao.themChiTietHoaDon(cthd)) {
                        return false;
                    }
                }
            } else {
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

    public boolean themHoaDonLuuTam(HoaDonEntity hoaDon, ArrayList<ChiTietHoaDonEntity> danhSachCTHD) {
        PreparedStatement statement = null;
        try {
            ConnectDB.getInstance().connect();
            Connection con = ConnectDB.getConnection();

            String sql = "Update HoaDon set tongTien=?, phuongThucThanhToan=?, trangThai=?, NgayCapNhat=? where maHD=? and maKH=?";
            statement = con.prepareStatement(sql);

            statement.setDouble(1, hoaDon.getTongTien());
            // statement.setInt(2, hoaDon.getPhuongThucThanhToan());
            statement.setString(3, "Đã thanh toán");
            statement.setDate(4, new java.sql.Date(hoaDon.getNgayCapNhat().getTime()));
            statement.setString(5, hoaDon.getMaHD());
            // statement.setString(6, hoaDon.getKhachHang().getMaKH());

            int ketQua = statement.executeUpdate();
            if (ketQua < 1) {
                return false;
            }

            ChiTietHoaDon_dao cthd_dao = new ChiTietHoaDon_dao();
            boolean kq = cthd_dao.xoaCTHDTheoMaHoaDon(hoaDon.getMaHD());

            if (kq) {
                for (ChiTietHoaDonEntity cthd : danhSachCTHD) {
                    if (!cthd_dao.themChiTietHoaDon(cthd)) {
                        return false;
                    }
                }
            } else {
                return false;
            }

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public ArrayList<HoaDonEntity> timKiemHoaDonChuaThanhToan(String sdt) {
        try {
            ConnectDB.getInstance().connect();
        } catch (Exception ex) {
            Logger.getLogger(HoaDon_dao.class.getName()).log(Level.SEVERE, null, ex);
        }
        Connection con = ConnectDB.getConnection();
        PreparedStatement statement = null;

        try {
            String sql = "Select hd.*, kh.hoTen, kh.gioiTinh, kh.soDienThoai, kh.diaChi from HoaDon as hd inner join KhachHang as kh on hd.maKH=kh.maKH "
                    +
                    "where kh.soDienThoai=? and hd.trangThai=N'Chưa thanh toán' ";
            statement = con.prepareStatement(sql);
            statement.setString(1, sdt);

            ResultSet rs = statement.executeQuery();
            ArrayList<HoaDonEntity> hdList = new ArrayList<>();
            while (rs.next()) {
                String mahd = rs.getString("maHD");
                String makh = rs.getString("maKH");
                String hoTen = rs.getString("hoTen");
                String gioiTinh = rs.getString("gioiTinh");
                String soDienThoai = rs.getString("soDienThoai");
                String diaChi = rs.getString("diaChi");
                // KhachHangEntity kh = new KhachHangEntity();
                // kh.setMaKH(makh);
                // kh.setHoTen(hoTen);
                // kh.setGioiTinh(toEnum.GioiTinhtoEnum(gioiTinh));
                // kh.setSoDienThoai(soDienThoai);
                // kh.setDiaChi(diaChi);

                double tongTien = rs.getDouble("tongTien");
                int phuongThucThanhToan = rs.getInt("phuongThucThanhToan");
                Date ngayTao = rs.getDate("ngayTao");
                Date ngayCapNhat = rs.getDate("NgayCapNhat");

                // HoaDonEntity hd = new HoaDonEntity(mahd, kh, tongTien, phuongThucThanhToan,
                // toEnum.TinhTrangHDToEnum("Chưa thanh toán"), ngayTao, ngayCapNhat);
                // hdList.add(hd);
            }
            return hdList;
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

    public HoaDonEntity timKiemHoaDonTheoMa(String maHD) {
        try {
            ConnectDB.getInstance().connect();
        } catch (Exception ex) {
            Logger.getLogger(HoaDon_dao.class.getName()).log(Level.SEVERE, null, ex);
        }
        Connection con = ConnectDB.getConnection();
        PreparedStatement statement = null;

        try {
            String sql = "Select * from HoaDon where maHD=? and trangThai=N'Đã thanh toán' ";
            statement = con.prepareStatement(sql);
            statement.setString(1, maHD);

            ResultSet rs = statement.executeQuery();
            HoaDonEntity hd = null;
            if (rs.next()) {
                String mahd = rs.getString("maHD");
                String makh = rs.getString("maKH");
                double tongTien = rs.getDouble("tongTien");
                int phuongThucThanhToan = rs.getInt("phuongThucThanhToan");
                String trangThai = rs.getString("trangThai");
                Date ngayTao = rs.getDate("ngayTao");
                Date ngayCapNhat = rs.getDate("NgayCapNhat");
                // KhachHangEntity kh = new KhachHangEntity();

                if (makh != null) {
                    // kh.setMaKH(makh);
                    String sql_kh = "Select hoTen, soDienThoai from KhachHang where maKH=?";
                    statement = con.prepareStatement(sql_kh);
                    statement.setString(1, makh);

                    rs = statement.executeQuery();
                    if (rs.next()) {
                        String hoTen = rs.getString("hoTen");
                        String soDienThoai = rs.getString("soDienThoai");
                        // kh.setHoTen(hoTen);
                        // kh.setSoDienThoai(soDienThoai);
                    }
                }

                // hd = new HoaDonEntity(mahd, kh, tongTien, phuongThucThanhToan,
                // toEnum.TinhTrangHDToEnum(trangThai), ngayTao, ngayCapNhat);
            }
            return hd;
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

    public int getSoLuongTonTheoMa(String maSP) {
        try {
            ConnectDB.getInstance().connect();
        } catch (Exception ex) {
            Logger.getLogger(HoaDon_dao.class.getName()).log(Level.SEVERE, null, ex);
        }
        Connection con = ConnectDB.getConnection();
        PreparedStatement statement = null;

        try {
            String sql = "Select soLuongTonKho from SanPham where maSP=?";
            statement = con.prepareStatement(sql);
            statement.setString(1, maSP);

            ResultSet rs = statement.executeQuery();
            int soLuong = -1;
            if (rs.next()) {
                soLuong = rs.getInt("soLuongTonKho");

            }
            return soLuong;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
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

    public ArrayList<HoaDonEntity> getAllHDChuaThanhToan() {
        try {
            ConnectDB.getInstance().connect();
        } catch (Exception ex) {
            Logger.getLogger(HoaDon_dao.class.getName()).log(Level.SEVERE, null, ex);
        }
        Connection con = ConnectDB.getConnection();
        PreparedStatement statement = null;

        try {
            String sql_del = "Delete from HoaDon where year(ngayTao) = year(getdate()) and month(ngayTao) = month(getdate()) and day(ngayTao)<day(getdate()) and trangThai=N'Chưa thanh toán' ";
            statement = con.prepareStatement(sql_del);

            statement.executeUpdate();

            String sql_sel = "Select hd.*, kh.hoTen, kh.gioiTinh, kh.soDienThoai, kh.diaChi from HoaDon as hd inner join KhachHang as kh on hd.maKH=kh.maKH "
                    +
                    "where hd.trangThai=N'Chưa thanh toán' ";
            statement = con.prepareStatement(sql_sel);

            ResultSet rs = statement.executeQuery();
            ArrayList<HoaDonEntity> hdList = new ArrayList<>();
            while (rs.next()) {
                String mahd = rs.getString("maHD");
                String makh = rs.getString("maKH");
                String hoTen = rs.getString("hoTen");
                String gioiTinh = rs.getString("gioiTinh");
                String soDienThoai = rs.getString("soDienThoai");
                String diaChi = rs.getString("diaChi");
                // KhachHangEntity kh = new KhachHangEntity();
                // kh.setMaKH(makh);
                // kh.setHoTen(hoTen);
                // kh.setGioiTinh(toEnum.GioiTinhtoEnum(gioiTinh));
                // kh.setSoDienThoai(soDienThoai);
                // kh.setDiaChi(diaChi);

                double tongTien = rs.getDouble("tongTien");
                int phuongThucThanhToan = rs.getInt("phuongThucThanhToan");
                Date ngayTao = rs.getDate("ngayTao");
                Date ngayCapNhat = rs.getDate("NgayCapNhat");

                // HoaDonEntity hd = new HoaDonEntity(mahd, kh, tongTien, phuongThucThanhToan,
                // toEnum.TinhTrangHDToEnum("Chưa thanh toán"), ngayTao, ngayCapNhat);
                // hdList.add(hd);
            }
            return hdList;
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

    public ArrayList<HoaDonEntity> getHoaDonTheoNgayLap(Date ngayLap) {
        ArrayList<HoaDonEntity> dshd = new ArrayList<HoaDonEntity>();
        try {
            ConnectDB.getInstance().connect();
            Connection con = ConnectDB.getConnection();
            PreparedStatement stmt = con.prepareCall("select * from HoaDon where ngayTao = ?");
            stmt.setDate(1, ngayLap);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String mahd = rs.getString("maHD");
                String makh = rs.getString("maKH");
                // KhachHangEntity kh = new KhachHangEntity(makh);
                double tongTien = rs.getDouble("tongTien");
                int phuongThucThanhToan = rs.getInt("phuongThucThanhToan");
                String trangThai = rs.getString("trangThai");
                Date ngayTao = rs.getDate("ngayTao");
                Date ngayCapNhat = rs.getDate("NgayCapNhat");

                // HoaDonEntity hd = new HoaDonEntity(mahd, kh, tongTien, phuongThucThanhToan,
                // toEnum.TinhTrangHDToEnum(trangThai), ngayTao, ngayCapNhat);
                // dshd.add(hd);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return dshd;
    }

    public ArrayList<HoaDonEntity> getHoaDonTheoMaHDvaNgayLap(String maHD, java.util.Date ngayLap) {
        try {
            ConnectDB.getInstance().connect();
        } catch (Exception ex) {
            Logger.getLogger(HoaDon_dao.class.getName()).log(Level.SEVERE, null, ex);
        }
        Connection con = ConnectDB.getConnection();
        PreparedStatement statement = null;

        try {
            String sql = "Select * from HoaDon where maHD=? and ngayTao=? ";
            statement = con.prepareStatement(sql);
            statement.setString(1, maHD);
            statement.setDate(2, new java.sql.Date(ngayLap.getTime()));
            ResultSet rs = statement.executeQuery();
            ArrayList<HoaDonEntity> dshd = new ArrayList<>();
            if (rs.next()) {
                String mahd = rs.getString("maHD");
                String makh = rs.getString("maKH");
                double tongTien = rs.getDouble("tongTien");
                int phuongThucThanhToan = rs.getInt("phuongThucThanhToan");
                String trangThai = rs.getString("trangThai");
                Date ngayTao = rs.getDate("ngayTao");
                Date ngayCapNhat = rs.getDate("NgayCapNhat");
                // KhachHangEntity kh = new KhachHangEntity();

                if (makh != null) {
                    // kh.setMaKH(makh);
                    String sql_kh = "Select hoTen, soDienThoai from KhachHang where maKH=?";
                    statement = con.prepareStatement(sql_kh);
                    statement.setString(1, makh);

                    rs = statement.executeQuery();
                    if (rs.next()) {
                        String hoTen = rs.getString("hoTen");
                        String soDienThoai = rs.getString("soDienThoai");
                        // kh.setHoTen(hoTen);
                        // kh.setSoDienThoai(soDienThoai);
                    }
                }

                // HoaDonEntity hd = new HoaDonEntity(mahd, kh, tongTien, phuongThucThanhToan,
                // toEnum.TinhTrangHDToEnum(trangThai), ngayTao, ngayCapNhat);
                // dshd.add(hd);
            }
            return dshd;
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

    public boolean xoaHoaDon(String maHD) {
        try {
            ConnectDB.getInstance().connect();
        } catch (Exception ex) {
            Logger.getLogger(HoaDon_dao.class.getName()).log(Level.SEVERE, null, ex);
        }
        Connection con = ConnectDB.getConnection();
        PreparedStatement statement = null;

        try {
            String sql = "Delete from HoaDon where maHD=?";
            statement = con.prepareStatement(sql);
            statement.setString(1, maHD);

            int rs = statement.executeUpdate();
            if (rs > 0) {
                return true;
            }

            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
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
