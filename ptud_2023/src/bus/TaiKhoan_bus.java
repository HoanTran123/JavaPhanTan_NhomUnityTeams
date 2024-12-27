/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bus;

import Interface.TaiKhoanInterface;
import dao.TaiKhoan_dao;
import entity.TaiKhoanEntity;
import java.util.ArrayList;

/**
 *
 * @author ploc2
 *  */
public class TaiKhoan_bus implements TaiKhoanInterface{
    private final TaiKhoan_dao dao;
    
    public TaiKhoan_bus() {
        dao = new TaiKhoan_dao();
    }
    
    @Override
    public boolean insert(TaiKhoanEntity tk) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean update(TaiKhoanEntity tk) {
        return dao.update(tk);
    }

    @Override
    public boolean delete(TaiKhoanEntity tk) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public TaiKhoanEntity findOne(String tenTK) {
        return dao.findOne(tenTK);
    }

    @Override
    public ArrayList<TaiKhoanEntity> findAll() {
        return dao.findAll();
    }
//
//    @Override
//    public boolean thoiGianDNGN(TaiKhoanEntity tk) {
//        return dao.thoiGianDNGN(tk);// Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
//    }

    @Override
    public boolean thoiGianDNGN(TaiKhoanEntity tk) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    
    
}
