package entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;
import java.util.List;

@Data
@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class KhachHang {

    @Id
    @EqualsAndHashCode.Include
    private String idKH;

    private String hoTen;
    private String sdt;
    private String gioiTinh;
    private Date ngayThamGia;
    private String hangMuc;
    private Double tongChiTieu;

    @OneToMany(mappedBy = "khachHang")
    private List<PhieuDatHang> phieuDatHang;

    @OneToMany(mappedBy = "khachHang")
    private List<HoaDon> hoaDon;

    @OneToMany(mappedBy = "khachHang")
    private List<DoiTra> doiTra;
}