package bus;

import dao.KhachHang_dao;
import entity.KhachHangEntity;
import java.util.ArrayList;
import util.GenerateID;
import Interface.KhachHang_Interface;

public class KhachHang_bus implements KhachHang_Interface {
    private KhachHang_dao khachHangDAO;

    public KhachHang_bus() {
        khachHangDAO = new KhachHang_dao();
    }

    @Override
    public KhachHangEntity findOne(String id) {
        return khachHangDAO.findOne(id);
    }

    @Override
    public boolean update(KhachHangEntity updateKH) {
        return khachHangDAO.update(updateKH);
    }

    @Override
    public boolean insert(KhachHangEntity insertKH) {
        String maKH = GenerateID.sinhMa("KH");
        insertKH.setMaKH(maKH);
        return khachHangDAO.insert(insertKH);
    }

    @Override
    public ArrayList<KhachHangEntity> findAll() {
        return khachHangDAO.findAll();
    }

    @Override
    public KhachHangEntity timKiemTheoSDT(String sdt) {
        return khachHangDAO.timKiemTheoSDT(sdt);
    }

    @Override
    public KhachHangEntity getKhachHang(String maKH) {
        return khachHangDAO.findOne(maKH);
    }
}
