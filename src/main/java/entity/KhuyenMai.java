package entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@Data
@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class KhuyenMai {

    @Id
    @EqualsAndHashCode.Include
    private String idKM;

    private String tenKhuyenMai;

    @Temporal(TemporalType.DATE)
    private Date ngayBatDau;

    @Temporal(TemporalType.DATE)
    private Date ngayKetThuc;

    private double phanTramGiam;

    private String hangMuc;

    private String moTa;
}