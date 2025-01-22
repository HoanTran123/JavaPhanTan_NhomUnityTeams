package entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class NhaCungCap {
    @Id
    @EqualsAndHashCode.Include
    private String idNCC;

    private String tenNCC;
    private String sdt;
    private String diaChi;

    @OneToMany(mappedBy = "nhaCungCap")
    private List<PhieuNhap> phieuNhap;
}