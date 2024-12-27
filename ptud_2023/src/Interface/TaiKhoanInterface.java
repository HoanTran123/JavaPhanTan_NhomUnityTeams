/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Interface;

import entity.TaiKhoanEntity;
import java.util.ArrayList;

/**
 *
 * @author ploc2
 */
public interface TaiKhoanInterface {
    public boolean insert(TaiKhoanEntity tk);
    public boolean update(TaiKhoanEntity tk);
    public boolean delete(TaiKhoanEntity tk);
    public TaiKhoanEntity findOne(String tenTK);
    public ArrayList<TaiKhoanEntity> findAll();
    public boolean thoiGianDNGN(TaiKhoanEntity tk);
}
