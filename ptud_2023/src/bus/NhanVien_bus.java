package bus;

import Interface.NhanVienInterface;
import dao.NhanVien_dao;
import entity.NhanVienEntity;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author ploc
 */
public class NhanVien_bus implements NhanVienInterface{
    
    NhanVien_dao NVdao = new NhanVien_dao();

    @Override
    public NhanVienEntity findOne(String id) {
        return NVdao.findOne(id);
    }

    @Override
    public boolean update(NhanVienEntity updateNV) {
        return NVdao.update(updateNV);
    }

    @Override
    public boolean insert(NhanVienEntity insertNV) {
        return NVdao.insert(insertNV);
    }

    @Override
    public ArrayList<NhanVienEntity> findAll() {
        return NVdao.findAll();
    }

    @Override
    public Boolean checkNV(String email, String sdt) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); 
    }

    @Override
    public NhanVienEntity getNV(String sdt) throws SQLException {
        return NVdao.getNV(sdt);
    }
    
}
