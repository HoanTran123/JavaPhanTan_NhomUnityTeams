/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package entity;

/**
 *
 * @author ploc2
 */
public enum GioiTinhEnum {
    NAM, NU, KHAC;
    
    @Override
    public String toString() {
        switch (this) {
            case NAM:
                return "Nam";
            case NU:
                return "Nữ";
            default:
                return "Khác";
        }
    }
    
}
