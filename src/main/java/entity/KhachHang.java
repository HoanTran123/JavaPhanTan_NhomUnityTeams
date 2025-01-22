package entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;
import java.util.List;

@Data
@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class KhachHang {

    @Id
    @EqualsAndHashCode.Include
    private String maKH;

    private String tenKH;

    private String gioiTinh;

    private String soDienThoai;
    private String diaChi;
    private Date ngayThamGia;
    private String hangMuc;
    private Double tongChiTieu;

//    @OneToMany(mappedBy = "khachHang")
//    private List<DoiTra> dsDoiTra;
}