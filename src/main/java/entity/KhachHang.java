package entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@AllArgsConstructor
@NoArgsConstructor
public class KhachHang {

    @Id
    @EqualsAndHashCode.Include
    private String idKH;

    private String hoTen;
    private String sdt;
    private Boolean gioiTinh;
    private String email;
    private Date ngayThamGia;
    private String hangMuc;
    private Double tongChiTieu;

    @OneToMany(mappedBy = "khachHang", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<PhieuDatHang> phieuDatHang = new ArrayList<>();

    @OneToMany(mappedBy = "khachHang", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<HoaDon> hoaDon = new ArrayList<>();

    @OneToMany(mappedBy = "khachHang", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<DoiTra> doiTra = new ArrayList<>();

    public KhachHang(String id, String hoTen, String sdt, Boolean gioiTinh, String email, Date ngayThamGia, String hangMuc, double tongChiTieu) {
        this.idKH = id;
        this.hoTen = hoTen;
        this.sdt = sdt;
        this.gioiTinh = gioiTinh;
        this.email = email;
        this.ngayThamGia = ngayThamGia;
        this.hangMuc = hangMuc;
        this.tongChiTieu = tongChiTieu;
        this.phieuDatHang = new ArrayList<>();
        this.hoaDon = new ArrayList<>();
        this.doiTra = new ArrayList<>();
    }

    @Override
    public String toString() {
        return "KhachHang{" +
                "idKH='" + idKH + '\'' +
                ", hoTen='" + hoTen + '\'' +
                ", sdt='" + sdt + '\'' +
                ", gioiTinh=" + (gioiTinh == null ? "null" : gioiTinh ? "Nam" : "Nữ") +
                ", email='" + email + '\'' +
                ", ngayThamGia=" + ngayThamGia +
                ", hangMuc='" + hangMuc + '\'' +
                ", tongChiTieu=" + tongChiTieu +
                '}';
    }
}