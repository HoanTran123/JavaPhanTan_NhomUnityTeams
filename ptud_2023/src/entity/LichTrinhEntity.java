package entity;

import java.sql.Date;
import java.util.Objects;

public class LichTrinhEntity {
    private int maHT;
    private TauEntity tau;
    private String gioDi;
    private String gioDen;
    private Date ngayDi;
    private Date ngayDen;
    private int trangThai;
    private int loai;

    public LichTrinhEntity(int maLichTrinh, TauEntity maTau, String gioDi, String gioDen, Date ngayDi, Date ngayDen, int trangThai, int loai) {
        this.maHT = maLichTrinh;
        this.tau = maTau;
        this.gioDi = gioDi;
        this.gioDen = gioDen;
        this.ngayDi = ngayDi;
        this.ngayDen = ngayDen;
        this.trangThai = trangThai;
        this.loai = loai;
    }

    public LichTrinhEntity() {
        //TODO Auto-generated constructor stub
    }

    public int getMaHT() {
        return maHT;
    }

    public void setMaHT(int maHT) {
        this.maHT = maHT;
    }

    public TauEntity getTau() {
        return tau;
    }

    public void setTau(TauEntity maTau) {
        this.tau = maTau;
    }

    public String getGioDi() {
        return gioDi;
    }

    public void setGioDi(String gioDi) {
        this.gioDi = gioDi;
    }

    public String getGioDen() {
        return gioDen;
    }

    public void setGioDen(String gioDen) {
        this.gioDen = gioDen;
    }

    public int getLoai() {
        return loai;
    }

    public void setLoai(int loai) {
        this.loai = loai;
    }

    public Date getNgayDi() {
        return ngayDi;
    }

    public void setNgayDi(Date ngayDi) {
        this.ngayDi = ngayDi;
    }

    public Date getNgayDen() {
        return ngayDen;
    }

    public void setNgayDen(Date ngayDen) {
        this.ngayDen = ngayDen;
    }

    public int getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(int trangThai) {
        this.trangThai = trangThai;
    }

    @Override
    public String toString() {
        return "LichTrinhEntity{" + "maHT=" + maHT + ", tau=" + tau + ", gioDi=" + gioDi + ", gioDen=" + gioDen + ", ngayDi=" + ngayDi + ", ngayDen=" + ngayDen + ", trangThai=" + trangThai + ", loai=" + loai + '}';
    }


	@Override
	public int hashCode() {
		return Objects.hash(maHT);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		LichTrinhEntity other = (LichTrinhEntity) obj;
		return Objects.equals(maHT, other.maHT);
	}
    
}
