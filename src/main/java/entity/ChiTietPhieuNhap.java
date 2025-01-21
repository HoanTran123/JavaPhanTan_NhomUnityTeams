package entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class ChiTietPhieuNhap {
    @Id
    @EqualsAndHashCode.Include
    @ManyToOne
    @JoinColumn(name = "idPN")
    private PhieuNhap phieuNhap;

    @Id
    @EqualsAndHashCode.Include
    @ManyToOne
    @JoinColumn(name = "idThuoc")
    private Thuoc thuoc;

    private Double soLuong;
    private Double donGia;
} 