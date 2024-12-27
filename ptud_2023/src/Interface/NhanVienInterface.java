package Interface;

import entity.NhanVienEntity;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author ploc2
 */
public interface NhanVienInterface {
    public Boolean checkNV(String email, String sdt) throws SQLException ;
    public NhanVienEntity getNV( String sdt) throws SQLException ;
    NhanVienEntity findOne(String id);
    boolean update(NhanVienEntity updateNV);
    boolean insert(NhanVienEntity insertNV);
    ArrayList<NhanVienEntity> findAll();
//    int count(String id);
}
