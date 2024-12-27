package entity;

import java.util.Date;
import java.util.Objects;

public class PhieuDatVeEntity {
    private String maPDV;
    private KhachHangEntity maKH;
    private String maVe;
    private Date ngayDat;
    private String trangThai;

    public PhieuDatVeEntity(String maPDV, KhachHangEntity maKH, String maVe, Date ngayDat, String trangThai) {
        this.maPDV = maPDV;
        this.maKH = maKH;
        this.maVe = maVe;
        this.ngayDat = ngayDat;
        this.trangThai = trangThai;
    }

    public String getMaPDV() {
        return maPDV;
    }

    public void setMaPDV(String maPDV) {
        this.maPDV = maPDV;
    }

    public KhachHangEntity getMaKH() {
        return maKH;
    }

    public void setMaKH(KhachHangEntity maKH) {
        this.maKH = maKH;
    }

    public String getMaVe() {
        return maVe;
    }

    public void setMaVe(String maVe) {
        this.maVe = maVe;
    }

    public Date getNgayDat() {
        return ngayDat;
    }

    public void setNgayDat(Date ngayDat) {
        this.ngayDat = ngayDat;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    @Override
    public String toString() {
        return "PhieuDatVeEntity{" +
                "maPDV='" + maPDV + '\'' +
                ", maKH='" + maKH + '\'' +
                ", maVe='" + maVe + '\'' +
                ", ngayDat=" + ngayDat +
                ", trangThai='" + trangThai + '\'' +
                '}';
    }

	@Override
	public int hashCode() {
		return Objects.hash(maPDV);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PhieuDatVeEntity other = (PhieuDatVeEntity) obj;
		return Objects.equals(maPDV, other.maPDV);
	}
    
}
