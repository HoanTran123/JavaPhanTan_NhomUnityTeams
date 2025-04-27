package entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class HoaDon {
    @Id
    @EqualsAndHashCode.Include
    private String idHD;

    private Date thoiGian;
    private Double tongTien;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idNV", nullable = true)
    private NhanVien nhanVien;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idKH", nullable = true)
    private KhachHang khachHang;

    @OneToMany(mappedBy = "hoaDon", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<ChiTietHoaDon> chiTietHoaDon = new ArrayList<>();

    @Override
    public String toString() {
        return "HoaDon{" +
                "idHD='" + idHD + '\'' +
                ", thoiGian=" + thoiGian +
                ", tongTien=" + tongTien +
                ", idNV='" + (nhanVien != null ? nhanVien.getIdNV() : "null") + '\'' +
                ", idKH='" + (khachHang != null ? khachHang.getIdKH() : "null") + '\'' +
                '}';
    }
}