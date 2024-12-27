package entity;

import java.sql.Date;
import java.util.Objects;

public class DoiTraEntity {
	private String maDT;
	private HoaDonEntity hoaDon;
	private NhanVienEntity nhanVien;
	private HinhThucDoiTraEnum hinhThucDoiTra;
	private Date thoiGianDoiTra;
        private double tongTien;

        public DoiTraEntity() {
        }
        
        public DoiTraEntity(String maDT) {
            this.maDT = maDT;
        }

        public DoiTraEntity(String maDT, HoaDonEntity hoaDon, NhanVienEntity nhanVien, HinhThucDoiTraEnum hinhThucDoiTra, Date thoiGianDoiTra, double tongTien) {
            this.maDT = maDT;
            this.hoaDon = hoaDon;
            this.nhanVien = nhanVien;
            this.hinhThucDoiTra = hinhThucDoiTra;
            this.thoiGianDoiTra = thoiGianDoiTra;
            this.tongTien = tongTien;
        }

        public String getMaDT() {
            return maDT;
        }

        public void setMaDT(String maDT) {
            this.maDT = maDT;
        }

        public HoaDonEntity getHoaDon() {
            return hoaDon;
        }

        public void setHoaDon(HoaDonEntity hoaDon) {
            this.hoaDon = hoaDon;
        }

        public NhanVienEntity getNhanVien() {
            return nhanVien;
        }

        public void setNhanVien(NhanVienEntity nhanVien) {
            this.nhanVien = nhanVien;
        }

        public HinhThucDoiTraEnum getHinhThucDoiTra() {
            return hinhThucDoiTra;
        }

        public void setHinhThucDoiTra(HinhThucDoiTraEnum hinhThucDoiTra) {
            this.hinhThucDoiTra = hinhThucDoiTra;
        }

        public Date getThoiGianDoiTra() {
            return thoiGianDoiTra;
        }

        public void setThoiGianDoiTra(Date thoiGianDoiTra) {
            this.thoiGianDoiTra = thoiGianDoiTra;
        }

        public double getTongTien() {
            return tongTien;
        }

        public void setTongTien(double tongTien) {
            this.tongTien = tongTien;
        }

        @Override
        public String toString() {
            return "DoiTraEntity{" + "maDT=" + maDT + ", hoaDon=" + hoaDon + ", nhanVien=" + nhanVien + ", hinhThucDoiTra=" + hinhThucDoiTra + ", thoiGianDoiTra=" + thoiGianDoiTra + ", tongTien=" + tongTien + '}';
        }

	@Override
	public int hashCode() {
		return Objects.hash(maDT);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DoiTraEntity other = (DoiTraEntity) obj;
		return Objects.equals(maDT, other.maDT);
	}
	
	
}
