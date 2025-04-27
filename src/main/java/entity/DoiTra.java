package entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class DoiTra {

    @Id
    @EqualsAndHashCode.Include
    private String idDT;

    private Timestamp thoiGian;
    private double tongTien;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idKH")
    private KhachHang khachHang;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idPDH")
    private PhieuDatHang phieuDatHang;

    @OneToMany(mappedBy = "doiTra", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<ChiTietDoiTra> chiTietDoiTra = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idNV")
    private NhanVien nhanVien;

    @Override
    public String toString() {
        return "DoiTra{" +
                "idDT='" + idDT + '\'' +
                ", thoiGian=" + thoiGian +
                ", tongTien=" + tongTien +
                ", idKH='" + (khachHang != null ? khachHang.getIdKH() : "null") + '\'' +
                ", idPDH='" + (phieuDatHang != null ? phieuDatHang.getIdPDH() : "null") + '\'' +
                ", idNV='" + (nhanVien != null ? nhanVien.getIdNV() : "null") + '\'' +
                '}';
    }
}