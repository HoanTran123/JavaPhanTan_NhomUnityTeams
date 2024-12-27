package entity;

import java.util.Date;
import java.util.Objects;

public class GheEntity {
    private int maGhe;
    private String ten;
    private ToaTauEntity toa;
    private int loai;
    private int trangThai;
    private int gia;
    private Date ngayTao;
    private Date ngayCapNhat;
    private int trangThaiHienTai;
    private int soGhe;

    public int getSoGhe() {
        return soGhe;
    }

    public void setSoGhe(int soGhe) {
        this.soGhe = soGhe;
    }

    public int getTrangThaiHienTai() {
        return trangThaiHienTai;
    }

    public void setTrangThaiHienTai(int trangThaiHienTai) {
        this.trangThaiHienTai = trangThaiHienTai;
    }

    public GheEntity(int maGhe, String ten, String maToa, int loai, int trangThai, ToaTauEntity toa) {
        this.maGhe = maGhe;
        this.ten = ten;
        this.toa = toa;
        this.loai = loai;
        this.trangThai = trangThai;
    }

    public GheEntity(int maGhe, String ten, int loai, int trangThai, Date ngayTao, Date ngayCapNhat, ToaTauEntity toa) {
        this.ten = ten;
        this.toa = toa;
        this.loai = loai;
        this.trangThai = trangThai;
        this.ngayTao = ngayTao;
        this.ngayCapNhat = ngayCapNhat;
        this.maGhe = maGhe;
    }

    public GheEntity() {
        // TODO Auto-generated constructor stub
    }

    public GheEntity(int maGhe) {
        this.maGhe = maGhe;
    }

    public int getMaGhe() {
        return maGhe;
    }

    public void setMaGhe(int maGhe) {
        this.maGhe = maGhe;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public ToaTauEntity getToa() {
        return toa;
    }

    public void setToa(ToaTauEntity toa) {
        this.toa = toa;
    }

    public int getLoai() {
        return loai;
    }

    public void setLoai(int loai) {
        this.loai = loai;
    }

    public int getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(int trangThai) {
        this.trangThai = trangThai;
    }

    @Override
    public String toString() {
        return "GheEntity{" + "maGhe=" + maGhe + ", toa=" + toa + ", loai=" + loai + ", trangThai=" + trangThai
                + ", ngayTao=" + ngayTao + ", ngayCapNhat=" + ngayCapNhat + '}';
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

    public int getGia() {
        return gia;
    }

    public void setGia(int gia) {
        this.gia = gia;
    }

    @Override
    public int hashCode() {
        return Objects.hash(maGhe);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        GheEntity other = (GheEntity) obj;
        return Objects.equals(maGhe, other.maGhe);
    }

}
