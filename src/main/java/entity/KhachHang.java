package entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

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

    @OneToMany(mappedBy = "khachHang", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PhieuDatHang> phieuDatHang;

    @OneToMany(mappedBy = "khachHang", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<HoaDon> hoaDon;

    @OneToMany(mappedBy = "khachHang", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DoiTra> doiTra;
    public KhachHang(String id, String hoTen, String sdt, Boolean gioiTinh,String email, Date ngayThamGia, String hangMuc, double tongChiTieu) {
        this.idKH = id;
        this.hoTen = hoTen;
        this.sdt = sdt;
        this.gioiTinh = gioiTinh;
        this.ngayThamGia = ngayThamGia;
        this.hangMuc = hangMuc;
        this.tongChiTieu = tongChiTieu;
        this.email = email;
    }
}