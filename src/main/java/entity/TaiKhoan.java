package entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class TaiKhoan {
    @Id
    @EqualsAndHashCode.Include
    private String idTK;

    private String tenDangNhap;
    private String matKhau;

    @ManyToOne
    @JoinColumn(name = "idNV")
    private NhanVien nhanVien;

    @Override
    public String toString() {
        return "TaiKhoan{" +
                "idTK='" + idTK + '\'' +
                ", tenDangNhap='" + tenDangNhap + '\'' +
                ", idNV='" + (nhanVien != null ? nhanVien.getIdNV() : "null") + '\'' +
                '}';
    }
}