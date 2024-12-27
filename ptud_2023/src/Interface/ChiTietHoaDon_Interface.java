
package Interface;

import entity.ChiTietHoaDonEntity;
import java.util.ArrayList;

/**
 *
 * @author DELL
 */
public interface ChiTietHoaDon_Interface {
    public ArrayList<ChiTietHoaDonEntity> getallCTHD();
    public int soluongSP(String maHD,String maSP);
    public ArrayList<ChiTietHoaDonEntity> getCTHDTheoMaHDvaMaSP(String maHD,String maSP);
    
    // Nguyen Huy Hoang
    public boolean themChiTietHoaDon(ChiTietHoaDonEntity cthd);
    public ArrayList<ChiTietHoaDonEntity> getAllCTHDTheoMaHD(String maHD);
    public boolean xoaCTHDTheoMaHoaDon(String maHD);
    public int getSoLuongCTHD(String maSP);
}
