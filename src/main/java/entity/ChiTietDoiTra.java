package entity;

<<<<<<< HEAD
public class ChiTietDoiTra {
=======
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class ChiTietDoiTra {
    @Id
    @EqualsAndHashCode.Include
    @ManyToOne
    @JoinColumn(name = "idDT")
    private DoiTra doiTra;

    @Id
    @EqualsAndHashCode.Include
    @ManyToOne
    @JoinColumn(name = "idThuoc")
    private Thuoc thuoc;

    private Double soLuong;
    private Double donGia;
>>>>>>> origin/Hoan
}
