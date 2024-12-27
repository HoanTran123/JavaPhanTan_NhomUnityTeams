package entity;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

public class NhanVienEntity {

    private String maNV;
    private String ten;
    private int loai;
    private GioiTinhEnum gioiTinh;
    private String email;
    private String soDienThoai;
    private String diaChi;
    private TinhTrangNVEnum trangThai;
    private Date ngayTao;
    private Date ngayCapNhat;

   
	public NhanVienEntity(String maNV, String ten, int loai, GioiTinhEnum gioiTinh, String email, String soDienThoai,
			String diaChi, TinhTrangNVEnum trangThai, Date ngayTao, Date ngayCapNhat) {
		super();
		this.maNV = maNV;
		this.ten = ten;
		this.loai = loai;
		this.gioiTinh = gioiTinh;
		this.email = email;
		this.soDienThoai = soDienThoai;
		this.diaChi = diaChi;
		this.trangThai = trangThai;
		this.ngayTao = ngayTao;
		this.ngayCapNhat = ngayCapNhat;
	}

	public NhanVienEntity(String maNV) {
        super();
        this.maNV = maNV;
    }

    public NhanVienEntity() {
        super();
    }

    public NhanVienEntity(String maNV, String tenNhanVien) {
        this.maNV = maNV;
        this.ten = tenNhanVien;
    }

    public String getMaNV() {
        return maNV;
    }

    public void setMaNV(String maNV) {
        this.maNV = maNV;
    }

    
    public GioiTinhEnum getGioiTinh() {
		return gioiTinh;
	}

	public void setGioiTinh(GioiTinhEnum gioiTinh) {
		this.gioiTinh = gioiTinh;
	}

	public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public int getLoai() {
        return loai;
    }

    public void setLoai(int loai) {
        this.loai = loai;
    }

   
    public TinhTrangNVEnum getTrangThai() {
		return trangThai;
	}

	public void setTrangThai(TinhTrangNVEnum trangThai) {
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
        return "NhanVienEntity{" +
                "maNV='" + maNV + '\'' +
                ", ten='" + ten + '\'' +
                ", loai=" + loai +
                ", gioiTinh=" + gioiTinh +
                ", email='" + email + '\'' +
                ", soDienThoai='" + soDienThoai + '\'' +
                ", diaChi='" + diaChi + '\'' +
                ", trangThai=" + trangThai +
                ", ngayTao=" + ngayTao +
                ", ngayCapNhat=" + ngayCapNhat +

                '}';
    }

	@Override
	public int hashCode() {
		return Objects.hash(maNV);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		NhanVienEntity other = (NhanVienEntity) obj;
		return Objects.equals(maNV, other.maNV);
	}
    
}
