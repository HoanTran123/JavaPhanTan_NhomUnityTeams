package entity;

import java.util.Date;
import java.util.Objects;


public class ToaTauEntity {
    private String maToa;
    private String tenToa;
    private int soGhe;
    private int loai;
    private int trangThai;
    private Date ngayTao;
	private Date ngayCapNhat;
    private TauEntity tau;

    public ToaTauEntity(String maToa, String tenToa, int soGhe, int loai, int trangThai, TauEntity tau) {
        this.maToa = maToa;
        this.tenToa = tenToa;
        this.soGhe = soGhe;
        this.loai = loai;
        this.trangThai = trangThai;
        this.tau = tau;
    }

    public ToaTauEntity(String tenToa, int soGhe, int loai, int trangThai, TauEntity tau) {
        this.tenToa = tenToa;
        this.soGhe = soGhe;
        this.loai = loai;
        this.trangThai = trangThai;
        this.tau = tau;
    }

    public ToaTauEntity(String maToa) {
        this.maToa = maToa;
    }

    public ToaTauEntity(String maToa, String tenToa, int soGhe, int loai, int trangThai, Date ngayTao, Date ngayCapNhat, TauEntity tau) {
        this.maToa = maToa;
        this.tenToa = tenToa;
        this.soGhe = soGhe;
        this.loai = loai;
        this.trangThai = trangThai;
        this.tau = tau;
        this.ngayTao = ngayTao;
        this.ngayCapNhat = ngayCapNhat;
    }

    public ToaTauEntity() {
    }



    public String getMaToa() {
        return maToa;
    }

    public void setMaToa(String maToa) {
        this.maToa = maToa;
    }

    public String getTenToa() {
        return tenToa;
    }

    public void setTenToa(String tenToa) {
        this.tenToa = tenToa;
    }

    public int getSoGhe() {
        return soGhe;
    }

    public void setSoGhe(int soGhe) {
        this.soGhe = soGhe;
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

    public TauEntity getTau() {
        return tau;
    }

    public void setMaTau(TauEntity maTau) {
        this.tau = maTau;
    }

    public Date getNgayTao () {
        return this.ngayTao;
    }

    public Date getNgayCapNhat () {
        return this.ngayCapNhat;
    }

    public void setNgayTao(Date ngayTao) {
		this.ngayTao = ngayTao;
	}
	public void setNgayCapNhat(Date ngayCapNhat) {
		this.ngayCapNhat = ngayCapNhat;
	}

    @Override
    public String toString() {
        return "ToaTauEntity{" +
                "maToa='" + maToa + '\'' +
                ", tenToa='" + tenToa + '\'' +
                ", soGhe=" + soGhe +
                ", loai='" + loai + '\'' +
                ", trangThai=" + trangThai +
                '}';
    }

	@Override
	public int hashCode() {
		return Objects.hash(maToa);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ToaTauEntity other = (ToaTauEntity) obj;
		return Objects.equals(maToa, other.maToa);
	}
    
}
