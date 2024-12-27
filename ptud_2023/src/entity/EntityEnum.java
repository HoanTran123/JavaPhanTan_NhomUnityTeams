package entity;

public class EntityEnum {
    public static String ConvertEnumChucVuToString(int loai) {
        switch (loai) {
            case 0:
                return "Nhân viên";
            case 1:
                return "Quản lý";
            default:
                return "Khác";
        }
    }
}
