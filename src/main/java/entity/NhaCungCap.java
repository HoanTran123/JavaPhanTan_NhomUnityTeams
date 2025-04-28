package entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

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

    @OneToMany(mappedBy = "nhaCungCap", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PhieuNhap> phieuNhap;

    // Add this constructor
    public NhaCungCap(String idNCC, String tenNCC, String sdt, String diaChi) {
        this.idNCC = idNCC;
        this.tenNCC = tenNCC;
        this.sdt = sdt;
        this.diaChi = diaChi;
    }

    // Default constructor
    public NhaCungCap() {
    }
    public static String generateRandomId() {
        int randomNum = (int) (Math.random() * 9000) + 1000; // Generate a random 4-digit number
        return "NCC" + randomNum;
    }
    @Override
    public String toString() {
        return "NhaCungCap{idNCC='" + idNCC + "', tenNCC='" + tenNCC + "'}";
    }
}