package entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.sql.Timestamp;
import java.util.List;

@Data
@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class PhieuNhap {
    @Id
    @EqualsAndHashCode.Include
    private String idPN;

    private Timestamp thoiGian;
    private Double tongTien;

    @ManyToOne
    @JoinColumn(name = "idNV")
    private NhanVien nhanVien;

    @ManyToOne
    @JoinColumn(name = "idNCC")
    private NhaCungCap nhaCungCap;

    @OneToMany(mappedBy = "phieuNhap")
    private List<ChiTietPhieuNhap> chiTietPhieuNhap;
}