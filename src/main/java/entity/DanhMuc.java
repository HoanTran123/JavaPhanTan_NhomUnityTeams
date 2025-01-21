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
public class DanhMuc {
    @Id
    @Column(columnDefinition = "varchar(45)", unique = true, nullable = false)
    private String idDM;

    @Column(columnDefinition = "varchar(255)",nullable = false)
    private String tenDM;


    // Quan hệ 1-n với thuốc
    @OneToMany(mappedBy = "danhmuc")
    private Set<Thuoc> thuoc;
}
