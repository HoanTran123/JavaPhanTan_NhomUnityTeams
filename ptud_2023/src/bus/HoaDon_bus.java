
package bus;

import Interface.HoaDon_Interface;
import dao.HoaDon_dao;
import entity.ChiTietHoaDonEntity;
import entity.HoaDonEntity;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author DELL
 */
public class HoaDon_bus implements HoaDon_Interface{
    HoaDon_dao hd_dao = new HoaDon_dao();
    @Override
    public ArrayList<HoaDonEntity> getallHoaDon() {
       return hd_dao.getallHoaDon();
    }

    @Override
    public double getTotalMoney(String maHD) {
        return hd_dao.getTotalMoney(maHD);
    }

    @Override
    public boolean themHoaDon(HoaDonEntity hoaDon, ArrayList<ChiTietHoaDonEntity> danhSachCTHD) {
        return hd_dao.themHoaDon(hoaDon, danhSachCTHD);
    }

    @Override
    public HoaDonEntity getHoaDonTheoMaHD(String maHD) {
        return hd_dao.getHoaDonTheoMaHD(maHD);
    }

    @Override
    public ArrayList<HoaDonEntity> getHoaDonTheoNgayLap(java.sql.Date ngayLap) {
       return hd_dao.getHoaDonTheoNgayLap(ngayLap);
    }

    @Override
    public HoaDonEntity timKiemHoaDonTheoMa(String maHD) {
        return hd_dao.timKiemHoaDonTheoMa(maHD);
    }

    @Override
    public boolean luuTamHoaDon(HoaDonEntity hoaDon, ArrayList<ChiTietHoaDonEntity> danhSachCTHD) {
        return hd_dao.luuTamHoaDon(hoaDon, danhSachCTHD);
    }

    @Override
    public ArrayList<HoaDonEntity> timKiemHoaDonChuaThanhToan(String sdt) {
        return hd_dao.timKiemHoaDonChuaThanhToan(sdt);
    }

    @Override
    public int getSoLuongTonTheoMa(String maSP) {
        return hd_dao.getSoLuongTonTheoMa(maSP);
    }

    @Override
    public boolean themHoaDonLuuTam(HoaDonEntity hoaDon, ArrayList<ChiTietHoaDonEntity> danhSachCTHD) {
        return hd_dao.themHoaDonLuuTam(hoaDon, danhSachCTHD);
    }

    @Override
    public ArrayList<HoaDonEntity> getAllHDChuaThanhToan() {
        return hd_dao.getAllHDChuaThanhToan();
    }

    @Override
    public boolean capNhatHoaDonLuuTam(HoaDonEntity hoaDon, ArrayList<ChiTietHoaDonEntity> danhSachCTHD) {
        return hd_dao.capNhatHoaDonLuuTam(hoaDon, danhSachCTHD);
    }

    @Override
    public ArrayList<HoaDonEntity> getHoaDonTheoMaHDvaNgayLap(String maHD, Date ngayLap) {
        return hd_dao.getHoaDonTheoMaHDvaNgayLap(maHD, ngayLap);
    }

    @Override
    public boolean xoaHoaDon(String maHD) {
        return hd_dao.xoaHoaDon(maHD);
    }
    
}
