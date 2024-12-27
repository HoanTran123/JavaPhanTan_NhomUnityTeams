package util;

import entity.GioiTinhEnum;


import entity.*;


/**
 *
 * @author Hoan
 */
public class ConvertStringToEnum {
    public ConvertStringToEnum() {
        
    }
    

    public GioiTinhEnum GioiTinhtoEnum(String gioiTinh) {
        if(gioiTinh.equals("Nam")) {
            return GioiTinhEnum.NAM;
        } 
        if(gioiTinh.equals("Nữ")) {
            return GioiTinhEnum.NU;
        } 
        if(gioiTinh.equals("Khác")) {
            return GioiTinhEnum.KHAC;
        } 
        return GioiTinhEnum.NAM;
    }
    
    public ChucVuEnum ChucVuToEnum(String chucVu) {
        if(chucVu.equals("Nhân viên")) {
            return ChucVuEnum.NHANVIEN;
        }
        if(chucVu.equals("Quản lý")) {
            return ChucVuEnum.QUANLY;
        }
        return ChucVuEnum.NHANVIEN;
    }


    public TinhTrangNVEnum TinhTrangNVToEnum(String tinhTrangNV) {
        if(tinhTrangNV.equals("Đang làm việc")) {
            return TinhTrangNVEnum.DANGLAMVIEC;
        }
        if(tinhTrangNV.equals("Nghỉ việc")) {
            return TinhTrangNVEnum.NGHIVIEC;
        }
        if(tinhTrangNV.equals("Nghỉ phép")) {
            return TinhTrangNVEnum.NGHIPHEP;
        }
        return TinhTrangNVEnum.DANGLAMVIEC;
    }
    
    
    public TinhTrangHDEnum TinhTrangHDToEnum(String tinhTrangHD) {
        if(tinhTrangHD.equals("Đã thanh toán")) {
            return TinhTrangHDEnum.DATHANHTOAN;
        }
        if(tinhTrangHD.equals("Chưa thanh toán")) {
            return TinhTrangHDEnum.CHUATHANHTOAN;
        }
        return TinhTrangHDEnum.CHUATHANHTOAN;
    }
    
    public HinhThucDoiTraEnum HinhThucDTToEnum(String hinhThuc) {
        if(hinhThuc.equals("Hoàn trả")) {
            return HinhThucDoiTraEnum.HOANTRA;
        }
        if(hinhThuc.equals("Đổi mới")) {
            return HinhThucDoiTraEnum.DOIMOI;
        }
        return HinhThucDoiTraEnum.HOANTRA;
    }
}
