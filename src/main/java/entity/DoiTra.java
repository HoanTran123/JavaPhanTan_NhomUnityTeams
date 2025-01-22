package entity;

<<<<<<< HEAD
public class DoiTra {
=======
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.sql.Timestamp;
import java.util.List;

@Data
@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class DoiTra {

    @Id
    @EqualsAndHashCode.Include
    private String idDT;

    private Timestamp thoiGian;
    private double tongTien;

    @ManyToOne
    @JoinColumn(name = "idKH")
    private KhachHang khachHang;

    @OneToOne
    @JoinColumn(name = "idPDH")
    private PhieuDatHang phieuDatHang;

    @OneToMany(mappedBy = "doiTra")
    private List<ChiTietDoiTra> chiTietDoiTra;

    @ManyToOne
    @JoinColumn(name = "idNV")
    private NhanVien nhanVien;

>>>>>>> origin/Hoan
}
