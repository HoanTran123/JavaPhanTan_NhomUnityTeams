/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bus;

import Interface.DoiTra_Interface;
import dao.DoiTra_dao;
import entity.ChiTietDoiTraEntity;
import entity.DoiTraEntity;
import java.sql.Date;
import java.util.ArrayList;

/**
 *
 * @author Admin
 */
public class DoiTra_bus implements DoiTra_Interface{
    private DoiTra_dao dt_dao = new DoiTra_dao();
    
    @Override
    public boolean taoDoiTra(DoiTraEntity dt, ArrayList<ChiTietDoiTraEntity> ctdtList) {
        return dt_dao.taoDoiTra(dt, ctdtList);
    }

    @Override
    public ArrayList<DoiTraEntity> getAllDoiTra() {
        return dt_dao.getAllDoiTra();
    }

    @Override
    public DoiTraEntity getDoiTraTheoMa(String ma) {
        return dt_dao.getDoiTraTheoMa(ma);
    }

    @Override
    public ArrayList<DoiTraEntity> getDoiTraTheoNgayLap(Date ngayLap) {
        return dt_dao.getDoiTraTheoNgayLap(ngayLap);
    }

    @Override
    public DoiTraEntity getDoiTraTheoDieuKien(String ma, Date ngayLap) {
        return dt_dao.getDoiTraTheoDieuKien(ma, ngayLap);
    }

    @Override
    public int getTongSoLuongSPDoiTra(String maHD, String maSP) {
        return dt_dao.getTongSoLuongSPDoiTra(maHD, maSP);
    }

    @Override
    public boolean kiemTraThoiHanDoiTra(String maHD) {
        return dt_dao.kiemTraThoiHanDoiTra(maHD);
    }
    
}
