package entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class NhanVien {
    @Id
    @EqualsAndHashCode.Include
    private String idNV;

    private String hoTen;

    @OneToMany(mappedBy = "nhanVien", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<HoaDon> hoaDon = new ArrayList<>();

    @OneToMany(mappedBy = "nhanVien", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DoiTra> doiTra = new ArrayList<>();

    @OneToMany(mappedBy = "nhanVien", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TaiKhoan> taiKhoan = new ArrayList<>();

    @Override
    public String toString() {
        return "NhanVien{" +
                "idNV='" + idNV + '\'' +
                ", hoTen='" + hoTen + '\'' +
                '}';
    }
}