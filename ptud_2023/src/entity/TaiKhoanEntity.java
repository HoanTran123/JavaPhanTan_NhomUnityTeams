package entity;

import java.util.Objects;

public class TaiKhoanEntity {
    private String taiKhoan;
    private String matKhau;
    private NhanVienEntity nhanVien;
    private int trangThai;

    public TaiKhoanEntity() {
    }

    public TaiKhoanEntity(String taiKhoan, String matKhau, NhanVienEntity nhanVien, int trangThai) {
        this.taiKhoan = taiKhoan;
        this.matKhau = matKhau;
        this.nhanVien = nhanVien;
        this.trangThai = trangThai;
    }
    
	public String getTaiKhoan() {
        return taiKhoan;
    }

    public void setTaiKhoan(String taiKhoan) {
        this.taiKhoan = taiKhoan;
    }

    public String getMatKhau() {
        return matKhau;
    }

    public void setMatKhau(String matKhau) {
        this.matKhau = matKhau;
    }

    public NhanVienEntity getNhanVien() {
        return nhanVien;
    }

    public void setNhanVien(NhanVienEntity nhanVien) {
        this.nhanVien = nhanVien;
    }

    public int getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(int trangThai) {
        this.trangThai = trangThai;
    }

    @Override
    public int hashCode() {
        return Objects.hash(taiKhoan);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        TaiKhoanEntity other = (TaiKhoanEntity) obj;
        return Objects.equals(taiKhoan, other.taiKhoan);
    }

    @Override
    public String toString() {
        return "TaiKhoanEntity [taiKhoan=" + taiKhoan + ", matKhau=" + matKhau + ", nhanVien=" + nhanVien + ", trangThai=" + trangThai + "]";
    }
}
