package entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class ChiTietDoiTra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idDT", nullable = false)
    private DoiTra doiTra;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idThuoc", nullable = false)
    private Thuoc thuoc;

    private Double soLuong;
    private Double donGia;

    @Override
    public String toString() {
        return "ChiTietDoiTra{" +
                "id=" + id +
                ", idDT='" + (doiTra != null ? doiTra.getIdDT() : "null") + '\'' +
                ", idThuoc='" + (thuoc != null ? thuoc.getIdThuoc() : "null") + '\'' +
                ", soLuong=" + soLuong +
                ", donGia=" + donGia +
                '}';
    }
}