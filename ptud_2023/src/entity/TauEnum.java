package entity;

public class TauEnum {
    public enum LoaiTau {
        CHO_NGUOI,
        CHO_HANG,
        KHAC;

        public static String convertLoaiTauToString(int loai) {
            switch (loai) {
                case 0:
                    return "Chở người";
                case 1:
                    return "Chở hàng";
                default:
                    return "Khác";
            }
        }
    }

    public enum TinhTrangTau {
        TAM_NGUNG,
        DANG_SU_DUNG,
        KHAC;

        public static String convertTinhTrangTauToString(int tinhTrang) {
            switch (tinhTrang) {
                case 0:
                    return "Tạm ngưng";
                case 1:
                    return "Đang sử dụng";
                default:
                    return "Khác";
            }
        }
    }


    public enum LoaiToa {
        KHAC,
        TOA_CHO_NGUOI,
        TOA_KI_GUI;

        public static String convertLoaiToaToString(int tinhTrang) {
            switch (tinhTrang) {
                case 0:
                    return "Khác";
                case 1:
                    return "Toa chở người";
                    case 2:
                    return "Toa kí gửi";
                default:
                    return "Khác";
            }
        }
    }

    public enum LoaiGhe {
        KHAC,
        GHE_NGOI,
        GHE_NAM;

        public static String convertLoaiGheToString(int tinhTrang) {
            switch (tinhTrang) {
                case 0:
                    return "Khác";
                case 1:
                    return "Ghế ngồi";
                    case 2:
                    return "Ghế nằm";
                default:
                    return "Khác";
            }
        }
    }

    public enum TinhTrangGhe {
        TRONG,
        DA_DAT,
        DA_THANH_TOAN;

        public static String convertLoaiGheToString(int tinhTrang) {
            switch (tinhTrang) {
                case 0:
                    return "Trống";
                case 1:
                    return "Đã đặt";
                    case 2:
                    return "Đã thanh toán";
                default:
                    return "Trống";
            }
        }
    }
    
        public enum TrangThaiLichTrinh {
        TAM_NGUNG,
        DANG_SU_DUNG;

        public static String convertTrangThaiToString(int tinhTrang) {
            switch (tinhTrang) {
                case 0:
                    return "Tạm ngưng";
                case 1:
                    return "Đang sử dụng";
                   
                default:
                    return "Tạm ngưng";
            }
        }
    }
        public enum LoaiLichTrinh {
        MOT_CHIEU,
        KHU_HOI;

        public static String convertLoaiLichTrinhToString(int tinhTrang) {
            switch (tinhTrang) {
                case 0:
                    return "Một chiều";
                case 1:
                    return "Khứ hồi";
                 
                default:
                    return "Một chiều";
            }
        }
    }


}
