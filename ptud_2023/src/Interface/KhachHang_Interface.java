package Interface;

import entity.KhachHangEntity;
import java.util.ArrayList;

public interface KhachHang_Interface {

    KhachHangEntity findOne(String id);

    boolean update(KhachHangEntity updateKH);

 
    boolean insert(KhachHangEntity insertKH);


    ArrayList<KhachHangEntity> findAll();


    KhachHangEntity timKiemTheoSDT(String sdt);
    KhachHangEntity getKhachHang(String maKH);
}
