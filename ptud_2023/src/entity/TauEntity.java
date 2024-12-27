package entity;

import java.util.Date;
import java.util.Objects;

public class TauEntity {
	private String maTau;
	private String tenTau;
	private GaTauEntity gaDi;
	private GaTauEntity gaDen;
	private Date thoiGianChay;
	private int soToa;
	private int loai;
	private int trangThai;
	private Date ngayTao;
	private Date ngayCapNhat;
	private NhanVienEntity nhanVien;

	private int soLuongGheTrong;
	private int soLuongDaDat;

    public TauEntity(String maTau, String tenTau, GaTauEntity gaDi, GaTauEntity gaDen, Date thoiGianChay, int soToa, int loai, int trangThai, Date ngayTao, Date ngayCapNhat, NhanVienEntity nhanVien, int soLuongGheTrong, int soLuongDaDat) {
        this.maTau = maTau;
        this.tenTau = tenTau;
        this.gaDi = gaDi;
        this.gaDen = gaDen;
        this.thoiGianChay = thoiGianChay;
        this.soToa = soToa;
        this.loai = loai;
        this.trangThai = trangThai;
        this.ngayTao = ngayTao;
        this.ngayCapNhat = ngayCapNhat;
        this.nhanVien = nhanVien;
        this.soLuongGheTrong = soLuongGheTrong;
        this.soLuongDaDat = soLuongDaDat;
    }

    public int getSoLuongGheTrong() {
        return soLuongGheTrong;
    }

    public void setSoLuongGheTrong(int soLuongGheTrong) {
        this.soLuongGheTrong = soLuongGheTrong;
    }

    public int getSoLuongDaDat() {
        return soLuongDaDat;
    }

    public void setSoLuongDaDat(int soLuongDaDat) {
        this.soLuongDaDat = soLuongDaDat;
    }

	public TauEntity(String maTau, String tenTau, GaTauEntity gaDi, GaTauEntity gaDen, int soToa, int loai, int trangThai,
			Date ngayTao, Date ngayCapNhat) {
		this.maTau = maTau;
		this.tenTau = tenTau;
		this.gaDi = gaDi;
		this.gaDen = gaDen;
		// this.thoiGianChay = thoiGianChay;
		this.soToa = soToa;
		this.loai = loai;
		this.trangThai = trangThai;
		this.ngayTao = ngayTao;
		this.ngayCapNhat = ngayCapNhat;
	}

	public TauEntity(String maTau, String tenTau, GaTauEntity gaDi, GaTauEntity gaDen, int soToa, int loai,
			int trangThai, Date ngayTao, Date ngayCapNhat, NhanVienEntity nhanVien) {
		this.maTau = maTau;
		this.tenTau = tenTau;
		this.gaDi = gaDi;
		this.gaDen = gaDen;
		this.soToa = soToa;
		this.loai = loai;
		this.trangThai = trangThai;
		this.ngayTao = ngayTao;
		this.ngayCapNhat = ngayCapNhat;
		this.nhanVien = nhanVien;
	}

	public TauEntity(String maTau, String tenTau, GaTauEntity gaDi, GaTauEntity gaDen, int soToa, int loai,
			int trangThai, NhanVienEntity nhanVien) {
		this.maTau = maTau;
		this.tenTau = tenTau;
		this.gaDi = gaDi;
		this.gaDen = gaDen;
		this.soToa = soToa;
		this.loai = loai;
		this.trangThai = trangThai;
		this.nhanVien = nhanVien;
	}

	public TauEntity(String maTau2) {
		this.maTau = maTau2;
	}

	public TauEntity(String maTau2, String tenTau, int soToa) {
		this.maTau = maTau2;
		this.tenTau = tenTau;
		this.soToa = soToa;

	}

	public TauEntity() {
																																																																	// nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
	}

	public String getMaTau() {
		return maTau;
	}

	public void setMaTau(String maTau) {
		this.maTau = maTau;
	}

	public String getTenTau() {
		return tenTau;
	}

	public void setTenTau(String tenTau) {
		this.tenTau = tenTau;
	}

	public GaTauEntity getGaDi() {
		return gaDi;
	}

	public void setGaDi(GaTauEntity gaDi) {
		this.gaDi = gaDi;
	}

	public GaTauEntity getGaDen() {
		return gaDen;
	}

	public void setGaDen(GaTauEntity gaDen) {
		this.gaDen = gaDen;
	}

	public Date getThoiGianChay() {
		return thoiGianChay;
	}

	public void setThoiGianChay(Date thoiGianChay) {
		this.thoiGianChay = thoiGianChay;
	}

	public int getSoToa() {
		return soToa;
	}

	public void setSoToa(int soToa) {
		this.soToa = soToa;
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
		return "TauEntity{" + "maTau='" + maTau + '\'' + ", tenTau='" + tenTau + '\'' + ", gaDi='" + gaDi + '\''
				+ ", gaDen='" + gaDen + '\'' + ", thoiGianChay=" + thoiGianChay + ", soToa=" + soToa + ", loai='" + loai
				+ '\'' + ", trangThai=" + trangThai + ", ngayTao=" + ngayTao + ", ngayCapNhat=" + ngayCapNhat + '}';
	}

	@Override
	public int hashCode() {
		return Objects.hash(maTau);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TauEntity other = (TauEntity) obj;
		return Objects.equals(maTau, other.maTau);
	}

	public NhanVienEntity getNhanVien() {
		return nhanVien;
	}

	public void setNhanVien(NhanVienEntity nhanVien) {
		this.nhanVien = nhanVien;
	}
}
