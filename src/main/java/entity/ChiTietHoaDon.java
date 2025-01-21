package entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class ChiTietHoaDon {
    @Id
    @ManyToOne
    @JoinColumn(name = "idHD")
    private HoaDon hoaDon;

    @Id
    @ManyToOne
    @JoinColumn(name = "idThuoc")
    private Thuoc thuoc;

    private int soLuong;
    private Double donGia;
} 