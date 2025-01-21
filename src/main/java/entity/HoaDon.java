package entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

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

    @ManyToOne
    @JoinColumn(name = "idNV")
    private NhanVien nhanVien;

    @ManyToOne
    @JoinColumn(name = "idKH")
    private KhachHang khachHang;

    @OneToMany(mappedBy = "hoaDon")
    private List<ChiTietHoaDon> chiTietHoaDon;
} 