package entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class ChiTietHoaDon {
    @Id
    @EqualsAndHashCode.Include
    @ManyToOne
    @JoinColumn(name = "idHD")
    private HoaDon hoaDon;

    @Id
    @EqualsAndHashCode.Include
    @ManyToOne
    @JoinColumn(name = "idThuoc")
    private Thuoc thuoc;

    private int soLuong;
    private Double donGia;
} 