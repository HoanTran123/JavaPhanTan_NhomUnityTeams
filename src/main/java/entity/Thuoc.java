package entity;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Data
@Entity
@Table
@Getter
@Setter
@Inheritance(strategy = InheritanceType.JOINED)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
public class Thuoc {
    @Id
    @EqualsAndHashCode.Include
    @Column(columnDefinition = "varchar(45)", unique = true, nullable = false)
    private String idThuoc;

    @Column(columnDefinition = "varchar(255)",nullable = false)
    private String tenThuoc;

    @Column(columnDefinition = "varchar(255)",nullable = false)
    private String thanhPhan;

    @ManyToOne
    @JoinColumn(name ="idDVT")
    private DonViTinh donvi;

    @ManyToOne
    @JoinColumn(name ="idDM")
    private DanhMuc danhmuc;

    @ManyToOne
    @JoinColumn(name ="idXX")
    private XuatXu xuatxu;


    private int soLuongTon;
    private double giaNhap;
    private double donGia;
    private LocalDate hanSuDung;


}
