package entity;

import java.util.Date;

public class ThanhToanEntity {
    private String maThanhToan;
    private HoaDonEntity maHoaDon;
    private double soTien;
    private String hinhThucThanhToan;
    private Date ngayThanhToan;
    private String trangThai;

    public ThanhToanEntity(String maThanhToan, HoaDonEntity maHoaDon, double soTien, String hinhThucThanhToan, Date ngayThanhToan, String trangThai) {
        this.maThanhToan = maThanhToan;
        this.maHoaDon = maHoaDon;
        this.soTien = soTien;
        this.hinhThucThanhToan = hinhThucThanhToan;
        this.ngayThanhToan = ngayThanhToan;
        this.trangThai = trangThai;
    }

    public String getMaThanhToan() {
        return maThanhToan;
    }

    public void setMaThanhToan(String maThanhToan) {
        this.maThanhToan = maThanhToan;
    }

    public HoaDonEntity getMaHoaDon() {
        return maHoaDon;
    }

    public void setMaHoaDon(HoaDonEntity maHoaDon) {
        this.maHoaDon = maHoaDon;
    }

    public double getSoTien() {
        return soTien;
    }

    public void setSoTien(double soTien) {
        this.soTien = soTien;
    }

    public String getHinhThucThanhToan() {
        return hinhThucThanhToan;
    }

    public void setHinhThucThanhToan(String hinhThucThanhToan) {
        this.hinhThucThanhToan = hinhThucThanhToan;
    }

    public Date getNgayThanhToan() {
        return ngayThanhToan;
    }

    public void setNgayThanhToan(Date ngayThanhToan) {
        this.ngayThanhToan = ngayThanhToan;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    @Override
    public String toString() {
        return "ThanhToanEntity{" +
                "maThanhToan='" + maThanhToan + '\'' +
                ", maHoaDon='" + maHoaDon + '\'' +
                ", soTien=" + soTien +
                ", hinhThucThanhToan='" + hinhThucThanhToan + '\'' +
                ", ngayThanhToan=" + ngayThanhToan +
                ", trangThai='" + trangThai + '\'' +
                '}';
    }
    
    
}
