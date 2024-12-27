package entity;

import java.util.Date;
import java.util.Objects;

public class VeEntity {
    private String maVe;
    private int loai;
    private int trangThai;
    private double gia;
    private Date ngayTao;
    private Date ngayCapNhat;
    private TauEntity tau;
    private ToaTauEntity toa;
    private GheEntity ghe;
    private LichTrinhEntity lichTrinh;

    private KhachHangEntity khachHang;

    private int soGhe;

    public int getSoGhe() {
        return soGhe;
    }

    public void setSoGhe(int soGhe) {
        this.soGhe = soGhe;
    }

    public LichTrinhEntity getLichTrinh() {
        return lichTrinh;
    }

    public void setLichTrinh(LichTrinhEntity lichTrinh) {
        this.lichTrinh = lichTrinh;
    }

    public void setKhachHang(KhachHangEntity khachHang) {
        this.khachHang = khachHang;
    }

    public KhachHangEntity getKhachHang() {
        return this.khachHang;
    }

    public VeEntity() {
    }

    public VeEntity(String maVe, int loai, int trangThai, double gia, Date ngayTao, Date ngayCapNhat, TauEntity maTau,
            ToaTauEntity maToa, GheEntity maGhe) {
        this.maVe = maVe;
        this.loai = loai;
        this.trangThai = trangThai;
        this.gia = gia;
        this.ngayTao = ngayTao;
        this.ngayCapNhat = ngayCapNhat;
        this.tau = maTau;
        this.toa = maToa;
        this.ghe = maGhe;
    }

    public String getMaVe() {
        return maVe;
    }

    public void setMaVe(String maVe) {
        this.maVe = maVe;
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

    public double getGia() {
        return gia;
    }

    public void setGia(double gia) {
        this.gia = gia;
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

    public TauEntity getTau() {
        return tau;
    }

    public void setTau(TauEntity maTau) {
        this.tau = maTau;
    }

    public ToaTauEntity getToa() {
        return toa;
    }

    public void setToa(ToaTauEntity maToa) {
        this.toa = maToa;
    }

    public GheEntity getGhe() {
        return ghe;
    }

    public void setGhe(GheEntity maGhe) {
        this.ghe = maGhe;
    }

    @Override
    public String toString() {
        return "VeEntity{" +
                "maVe='" + maVe + '\'' +
                ", loai=" + loai +
                ", trangThai=" + trangThai +
                ", gia=" + gia +
                ", ngayTao=" + ngayTao +
                ", ngayCapNhat=" + ngayCapNhat +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(maVe);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        VeEntity other = (VeEntity) obj;
        return Objects.equals(maVe, other.maVe);
    }

}
