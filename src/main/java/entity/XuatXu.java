package modal;

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
public class XuatXu {
    @Id
    @Column(columnDefinition = "varchar(45)", unique = true, nullable = false)
    private String idXX;

    @Column(columnDefinition = "varchar(255)",nullable = false)
    private String tenXX;


    // Quan hệ 1-n với thuốc
    @OneToMany(mappedBy = "xuatxu")
    private Set<Thuoc> thuoc;
}
