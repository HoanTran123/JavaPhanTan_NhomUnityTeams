package entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class VaiTro {
    @Id
    @EqualsAndHashCode.Include
    private String idVT;

    private String ten;
} 