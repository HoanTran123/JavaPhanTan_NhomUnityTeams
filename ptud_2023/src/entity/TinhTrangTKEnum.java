/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package entity;

/**
 *
 * @author ploc2
 */
public enum TinhTrangTKEnum {
    DANG_HOAT_DONG, NGUNG_HOAT_DONG;

    @Override
    public String toString() {
        return switch (this) {
                case DANG_HOAT_DONG -> "Đang hoạt động";
                default -> "Ngưng hoạt động";
            };
    }
    
    
}
