package entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;
import java.util.List;

@Data
@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class PhieuDatHang {
    @Id
    @EqualsAndHashCode.Include
    private String idPDH;

    private Date thoiGian;
    private Double tongTien;
    private String diaChi;
    private String phuongThucThanhToan;
    private String trangThai;

    @ManyToOne
    @JoinColumn(name = "idKH")
    private KhachHang khachHang;

    @OneToMany(mappedBy = "phieuDatHang")
    private List<ChiTietPhieuDatHang> chiTietPhieuDatHang;

    @OneToOne(mappedBy = "phieuDatHang")
    private DoiTra doiTra;
}