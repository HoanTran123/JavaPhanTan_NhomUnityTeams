package entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@AllArgsConstructor
@NoArgsConstructor
public class VaiTro {
    @Id
    @EqualsAndHashCode.Include
    private String idVT;

    private String ten;

    public VaiTro(String idVT) {
    }
}