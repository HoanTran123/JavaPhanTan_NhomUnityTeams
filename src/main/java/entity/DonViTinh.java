package entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;
@Data
@Entity
@Table
@Getter
@Setter
@Inheritance(strategy = InheritanceType.JOINED)
public class DonViTinh {
    @Id
    @Column(columnDefinition = "varchar(45)", unique = true, nullable = false)
    private String idDVT;

    @Column(columnDefinition = "varchar(255)",nullable = false)
    private String tenDVT;

    // Quan hệ 1-n với thuốc
    @OneToMany(mappedBy = "donvi")
    private Set<Thuoc> thuoc;
}
