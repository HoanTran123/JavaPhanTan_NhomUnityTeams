package entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class ChiTietPhieuDatHang {
    @Id
    @EqualsAndHashCode.Include
    @ManyToOne
    @JoinColumn(name = "idPDH")
    private PhieuDatHang phieuDatHang;

    @Id
    @EqualsAndHashCode.Include
    @ManyToOne
    @JoinColumn(name = "idThuoc")
    private Thuoc thuoc;

    private int soLuong;
    private Double donGia;
}
