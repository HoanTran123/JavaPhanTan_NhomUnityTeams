package entity;

import java.util.Date;
import java.util.Objects;

public class HoaDonEntity {
    private String maHD;
    private KhachHangEntity maKH;
    private double tongTien;
    private String phuongThucThanhToan;
    private int trangThai;
    private Date ngayTao;
    private Date ngayCapNhat;
    private NhanVienEntity nhanVien;

    public HoaDonEntity(String maHD, KhachHangEntity maKH, double tongTien, String phuongThucThanhToan, int trangThai,
            Date ngayTao, Date ngayCapNhat) {
        this.maHD = maHD;
        this.maKH = maKH;
        this.tongTien = tongTien;
        this.phuongThucThanhToan = phuongThucThanhToan;
        this.trangThai = trangThai;
        this.ngayTao = ngayTao;
        this.ngayCapNhat = ngayCapNhat;
    }

    public HoaDonEntity(String maDH, KhachHangEntity khachHang, double tongTien, double gia, int trangThai, java.sql.Date ngayTao, java.sql.Date ngayCapNhat) {
        this.maHD = maDH;
        this.maKH = khachHang;
        this.tongTien = tongTien;
        this.trangThai = trangThai;
        this.ngayTao = ngayTao;
        this.ngayCapNhat = ngayCapNhat;
        }

    public HoaDonEntity(String mahd, KhachHangEntity kh, double tongTien, int trangThai, java.sql.Date ngayTao, java.sql.Date ngayCapNhat) {
                this.maHD = mahd;
            this.maKH = kh;
            this.tongTien = tongTien;
            this.trangThai = trangThai;
            this.ngayTao = ngayTao;
            this.ngayCapNhat = ngayCapNhat;
    }

    public void setNhanVien(NhanVienEntity nhanVien) {
        this.nhanVien = nhanVien;
    }

    public NhanVienEntity getNhanVien() {
        return nhanVien;
    }

    public HoaDonEntity() {
        // TODO Auto-generated constructor stub
    }

    public String getMaHD() {
        return maHD;
    }

    public void setMaHD(String maHD) {
        this.maHD = maHD;
    }

    public KhachHangEntity getMaKH() {
        return maKH;
    }

    public void setMaKH(KhachHangEntity maKH) {
        this.maKH = maKH;
    }

    public double getTongTien() {
        return tongTien;
    }

    public void setTongTien(double tongTien) {
        this.tongTien = tongTien;
    }

    public String getPhuongThucThanhToan() {
        return phuongThucThanhToan;
    }

    public void setPhuongThucThanhToan(String phuongThucThanhToan) {
        this.phuongThucThanhToan = phuongThucThanhToan;
    }

    public int getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(int trangThai) {
        this.trangThai = trangThai;
    }

    public Date getNgayTao() {
        return ngayTao;
    }

    public void setNgayTao(Date ngayTao) {
        this.ngayTao = ngayTao;
    }

    public Date getNgayCapNhat() {
        return ngayCapNhat;
    }

    public void setNgayCapNhat(Date ngayCapNhat) {
        this.ngayCapNhat = ngayCapNhat;
    }

    @Override
    public String toString() {
        return "HoaDonEntity{" +
                "maHD='" + maHD + '\'' +
                ", maKH='" + maKH + '\'' +
                ", tongTien=" + tongTien +
                ", phuongThucThanhToan='" + phuongThucThanhToan + '\'' +
                ", trangThai=" + trangThai +
                ", ngayTao=" + ngayTao +
                ", ngayCapNhat=" + ngayCapNhat +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(maHD);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        HoaDonEntity other = (HoaDonEntity) obj;
        return Objects.equals(maHD, other.maHD);
    }

}
