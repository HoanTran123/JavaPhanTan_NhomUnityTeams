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

    private String username;
    private String password;

    @ManyToOne
    @JoinColumn(name = "idNV")
    private NhanVien nhanVien;

    @ManyToOne
    @JoinColumn(name = "idVT")
    private VaiTro vaiTro;
} 