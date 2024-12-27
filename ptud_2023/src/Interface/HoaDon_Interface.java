
package Interface;

import entity.ChiTietHoaDonEntity;
import entity.HoaDonEntity;
import java.util.ArrayList;
import java.util.Date;


/**
 *
 * @author DELL
 */
public interface HoaDon_Interface {
    //Đinh Nguyên Chung
    public ArrayList<HoaDonEntity> getallHoaDon();
    public double getTotalMoney(String maHD);
    public HoaDonEntity getHoaDonTheoMaHD(String maHD);
    public ArrayList<HoaDonEntity> getHoaDonTheoNgayLap(java.sql.Date ngayLap);
    public ArrayList<HoaDonEntity> getHoaDonTheoMaHDvaNgayLap(String maHD,Date ngayLap);
    
    // Nguyen Huy Hoang
    public boolean themHoaDon(HoaDonEntity hoaDon, ArrayList<ChiTietHoaDonEntity> danhSachCTHD);
    public boolean luuTamHoaDon(HoaDonEntity hoaDon, ArrayList<ChiTietHoaDonEntity> danhSachCTHD);
    public boolean capNhatHoaDonLuuTam(HoaDonEntity hoaDon, ArrayList<ChiTietHoaDonEntity> danhSachCTHD);
    public boolean themHoaDonLuuTam(HoaDonEntity hoaDon, ArrayList<ChiTietHoaDonEntity> danhSachCTHD);
    public HoaDonEntity timKiemHoaDonTheoMa(String maHD);
    public ArrayList<HoaDonEntity> timKiemHoaDonChuaThanhToan(String sdt);
    public int getSoLuongTonTheoMa(String maSP);
    public ArrayList<HoaDonEntity> getAllHDChuaThanhToan();
    public boolean xoaHoaDon(String maHD);
}
