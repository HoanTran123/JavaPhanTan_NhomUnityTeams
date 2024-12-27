
package bus;

import Interface.ChiTietHoaDon_Interface;
import dao.ChiTietHoaDon_dao;
import entity.ChiTietHoaDonEntity;
import java.util.ArrayList;

/**
 *
 * @author DELL
 */
public class ChiTietHoaDon_bus implements ChiTietHoaDon_Interface{
    public ChiTietHoaDon_bus(){
        
    }
    ChiTietHoaDon_dao cthd_dao = new ChiTietHoaDon_dao();
    @Override
    public ArrayList<ChiTietHoaDonEntity> getallCTHD() {
        return cthd_dao.getallCTHD();
    }
//
//    @Override
//    public ArrayList<SanPhamEntity> getSanPhamTheoMaSP(String maSP) {
//        return cthd_dao.getSanPhamTheoMaSP(maSP);
//    }
//
//    @Override
//    public ArrayList<SanPhamEntity> getSanPhamTheoMaHD(String maHD) {
//        return cthd_dao.getSanPhamTheoMaHD(maHD);
//    }
    
    
    // Nguyen Huy Hoang
    @Override
    public boolean themChiTietHoaDon(ChiTietHoaDonEntity cthd) {
        return cthd_dao.themChiTietHoaDon(cthd);
    }
    
    @Override
    public boolean xoaCTHDTheoMaHoaDon(String maHD) {
        return cthd_dao.xoaCTHDTheoMaHoaDon(maHD);
    }

    @Override
    public int soluongSP(String maHD, String maSP) {
        return cthd_dao.soluongSP(maHD, maSP);
    }

    @Override
    public ArrayList<ChiTietHoaDonEntity> getCTHDTheoMaHDvaMaSP(String maHD, String maSP) {
        return cthd_dao.getCTHDTheoMaHDvaMaSP(maHD, maSP);
    }

    @Override
    public ArrayList<ChiTietHoaDonEntity> getAllCTHDTheoMaHD(String maHD) {
        return cthd_dao.getAllCTHDTheoMaHD(maHD);
    }

    @Override
    public int getSoLuongCTHD(String maSP) {
        return cthd_dao.getSoLuongCTHD(maSP);
    }

}
