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
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idHD", nullable = false)
    private HoaDon hoaDon;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idThuoc", nullable = false)
    private Thuoc thuoc;

    private int soLuong;
    private Double donGia;

    @PrePersist
    public void generateId() {
        if (id == null) {
            id = System.currentTimeMillis(); // Simple example; replace with better strategy
        }
    }

    @Override
    public String toString() {
        return "ChiTietHoaDon{" +
                "id=" + id +
                ", idHD='" + (hoaDon != null ? hoaDon.getIdHD() : "null") + '\'' +
                ", idThuoc='" + (thuoc != null ? thuoc.getIdThuoc() : "null") + '\'' +
                ", soLuong=" + soLuong +
                ", donGia=" + donGia +
                '}';
    }
}