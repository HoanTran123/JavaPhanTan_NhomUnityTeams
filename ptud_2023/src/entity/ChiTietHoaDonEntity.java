package entity;

import java.sql.Date;
import java.util.Objects;

public class ChiTietHoaDonEntity {

    private String maCTDH;
    private VeEntity ve;
    private HoaDonEntity hoaDon;
    private int soLuong;
    private double giaGoc;
    private double giaBan;
    private double thanhTien;
    private KhachHangEntity khachHang;
    private Date ngayTao;
    private Date ngayCapNhat;

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

    public KhachHangEntity getKhachHang() {
        return khachHang;
    }

    public void setKhachHang(KhachHangEntity khachHang) {
        this.khachHang = khachHang;
    }

    public ChiTietHoaDonEntity(VeEntity ve, HoaDonEntity hoaDon, int soLuong, double giaGoc, double giaBan,
            double thanhTien) {
        this.ve = ve;
        this.hoaDon = hoaDon;
        this.soLuong = soLuong;
        this.giaGoc = giaGoc;
        this.giaBan = giaBan;
        this.thanhTien = thanhTien;
    }

    public String getMaCTDH() {
        return this.maCTDH;
    }

    public void setMaCTDH(String maString) {
        this.maCTDH = maString;
    }

    public ChiTietHoaDonEntity() {
    }

    public VeEntity getVe() {
        return ve;
    }

    public void setVe(VeEntity ve) {
        this.ve = ve;
    }

    public HoaDonEntity getHoaDon() {
        return hoaDon;
    }

    public void setHoaDon(HoaDonEntity hoaDon) {
        this.hoaDon = hoaDon;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public double getGiaGoc() {
        return giaGoc;
    }

    public void setGiaGoc(double giaGoc) {
        this.giaGoc = giaGoc;
    }

    public void setGiaBan(double giaBan) {
        this.giaBan = giaBan;
    }

    public void setThanhTien(double thanhTien) {
        this.thanhTien = thanhTien;
    }

    public double getGiaBan() {
        return giaBan;
    }

    public double getThanhTien() {
        return thanhTien;
    }

    public void setThanhTien() {
        this.thanhTien = giaBan * soLuong;
    }

    @Override
    public String toString() {
        return "ChiTietHoaDonEntity{" + "ve=" + ve + ", hoaDon=" + hoaDon + ", soLuong=" + soLuong + ", giaGoc="
                + giaGoc + ", giaBan=" + giaBan + ", thanhTien=" + thanhTien + '}';
    }

}
