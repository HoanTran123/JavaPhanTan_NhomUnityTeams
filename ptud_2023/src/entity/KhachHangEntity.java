/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

import java.util.Calendar;
import java.util.Date;
import java.util.Objects;


public class KhachHangEntity {
    private String maKH;
    private String hoTen;
    private GioiTinhEnum gioiTinh;
    private String soDienThoai;
    private String diaChi;
    private String soCCCD;
    private Date ngayTao;
    private Date ngayCapNhat;
				private int tuoi;

    public int getTuoi() {
        return tuoi;
    }

    public void setTuoi(int tuoi) {
        this.tuoi = tuoi;
    }
				
    
    
	public KhachHangEntity(String maKH, String hoTen, GioiTinhEnum gioiTinh, String soDienThoai, String diaChi,
			String soCCCD, Date ngayTao, Date ngayCapNhat) {
		super();
		this.maKH = maKH;
		this.hoTen = hoTen;
		this.gioiTinh = gioiTinh;
		this.soDienThoai = soDienThoai;
		this.diaChi = diaChi;
		this.soCCCD = soCCCD;
		this.ngayTao = ngayTao;
		this.ngayCapNhat = ngayCapNhat;
	}
	
	public KhachHangEntity(String maKH) {
		super();
		this.maKH = maKH;
	}
	

	public KhachHangEntity() {
		super();
	}

	public String getMaKH() {
		return maKH;
	}
	public void setMaKH(String maKH) {
		this.maKH = maKH;
	}
	public String getHoTen() {
		return hoTen;
	}
	public void setHoTen(String hoTen) {
		this.hoTen = hoTen;
	}
	public GioiTinhEnum getGioiTinh() {
		return gioiTinh;
	}
	public void setGioiTinh(GioiTinhEnum gioiTinh) {
		this.gioiTinh = gioiTinh;
	}
	public String getSoDienThoai() {
		return soDienThoai;
	}
	public void setSoDienThoai(String soDienThoai) {
		this.soDienThoai = soDienThoai;
	}
	public String getDiaChi() {
		return diaChi;
	}
	public void setDiaChi(String diaChi) {
		this.diaChi = diaChi;
	}
	public String getSoCCCD() {
		return soCCCD;
	}
	public void setSoCCCD(String soCCCD) {
		this.soCCCD = soCCCD;
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
	public int hashCode() {
        int hash = 7;
        hash = 79 * hash + Objects.hashCode(this.maKH);
        hash = 79 * hash + Objects.hashCode(this.soDienThoai);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final KhachHangEntity other = (KhachHangEntity) obj;

        return Objects.equals(this.soDienThoai, other.soDienThoai);
    }

	@Override
	public String toString() {
		return "KhachHangEntity [maKH=" + maKH + ", hoTen=" + hoTen + ", gioiTinh=" + gioiTinh + ", soDienThoai="
				+ soDienThoai + ", diaChi=" + diaChi + ", soCCCD=" + soCCCD + ", ngayTao=" + ngayTao + ", ngayCapNhat="
				+ ngayCapNhat + "]";
	}
    
}