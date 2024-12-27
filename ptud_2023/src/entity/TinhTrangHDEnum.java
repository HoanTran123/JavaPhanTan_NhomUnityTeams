/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package entity;

/**
 *
 * @author ploc2
 */
public enum TinhTrangHDEnum {
    DATHANHTOAN, CHUATHANHTOAN;

    @Override
    public String toString() {
        switch (this) {
            case DATHANHTOAN:
                return "Đã thanh toán";
            case CHUATHANHTOAN:
                return "Chưa thanh toán";
            default:
                return "Chưa thanh toán";
        }
    }
    
    
}
