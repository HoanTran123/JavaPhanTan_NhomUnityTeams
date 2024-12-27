package Interface;

import java.util.ArrayList;

public interface ThongKe_Interface {
    public ArrayList<Object[]> thongKeVeTheoNhanVien(String nam);
    public int thongKeTongVeDaBan(String nam);
    public ArrayList<Object[]> thongKeNhanVien();
    public ArrayList<Object[]> thongKeVeTheoTau(String nam);
    public ArrayList<Object[]> thongKeTop5NhanVien(String thang, String nam);
}
