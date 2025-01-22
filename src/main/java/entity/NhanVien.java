package entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;
import java.util.List;

@Data
@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class NhanVien {
    @Id
    @EqualsAndHashCode.Include
    private String idNV;

    private String hoTen;
    private String sdt;
    private String email;
    private String gioiTinh;
    private int namSinh;
    private Date ngayVaoLam;
    private String chucVu;

    @OneToMany(mappedBy = "nhanVien")
    private List<PhieuNhap> phieuNhap;

    @OneToMany(mappedBy = "nhanVien")
    private List<TaiKhoan> taiKhoan;

    @OneToMany(mappedBy = "nhanVien")
    private List<HoaDon> hoaDon;

    @OneToMany(mappedBy = "nhanVien")
    private List<DoiTra> doiTra;
}