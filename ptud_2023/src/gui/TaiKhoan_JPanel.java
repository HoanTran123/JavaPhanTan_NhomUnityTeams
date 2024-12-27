package gui;

import dao.TaiKhoan_dao;
import dao.NhanVien_dao;
import entity.TaiKhoanEntity;
import entity.NhanVienEntity;
import java.awt.Image;
import java.net.URL;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class TaiKhoan_JPanel extends javax.swing.JPanel {
    private TaiKhoan_dao tkDao;
    private NhanVien_dao nvDao;
    private DefaultTableModel model;

    public TaiKhoan_JPanel() {
        initComponents();
        setBounds(0, 0, 1186, 748);
        
        setUpIcons();
        
        tkDao = new TaiKhoan_dao();
        nvDao = new NhanVien_dao();
        
        model = (DefaultTableModel) tbl_TaiKhoan.getModel();
        loadDataToTable();
        
        // Không cho phép edit trực tiếp trên table
        tbl_TaiKhoan.setDefaultEditor(Object.class, null);
    }
    
    private void setUpIcons() {
        try {
            setButtonIcon(btn_TimKiem, "/pic/icon/buttonTimKiem.png");
            setButtonIcon(btn_LamMoi, "/pic/icon/buttonLamMoi.png");
            setButtonIcon(btn_Them, "/pic/icon/buttonThem.png");
            setButtonIcon(btn_CapNhat, "/pic/icon/buttonCapNhat.png");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private void setButtonIcon(javax.swing.JButton button, String iconPath) {
        try {
            URL iconURL = getClass().getResource(iconPath);
            if (iconURL != null) {
                ImageIcon icon = new ImageIcon(iconURL);
                Image scaledImage = icon.getImage().getScaledInstance(15, 15, Image.SCALE_SMOOTH);
                button.setIcon(new ImageIcon(scaledImage));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadDataToTable() {
        model.setRowCount(0);
        ArrayList<TaiKhoanEntity> dsTK = tkDao.findAll();
        
        for(TaiKhoanEntity tk : dsTK) {
            String tenNV = tk.getNhanVien() != null ? tk.getNhanVien().getTen() : "";
            model.addRow(new Object[] {
                tk.getTaiKhoan(),
                tk.getMatKhau(),
                tenNV,
                getTrangThaiText(tk.getTrangThai())
            });
        }
    }
    
    private String getTrangThaiText(int trangThai) {
        switch(trangThai) {
            case 0: return "Ngưng hoạt động";
            case 1: return "Đang hoạt động";
            default: return "Không xác định";
        }
    }
    
    private void btn_TimKiemActionPerformed(java.awt.event.ActionEvent evt) {
        String taiKhoan = txt_TimKiem.getText().trim();
        if(taiKhoan.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập tài khoản cần tìm!");
            return;
        }
        
        TaiKhoanEntity tk = tkDao.findOne(taiKhoan);
        if(tk == null) {
            JOptionPane.showMessageDialog(this, "Không tìm thấy tài khoản!");
            return;
        }
        
        model.setRowCount(0);
        String tenNV = tk.getNhanVien() != null ? tk.getNhanVien().getTen() : "";
        model.addRow(new Object[] {
            tk.getTaiKhoan(),
            tk.getMatKhau(),
            tenNV,
            getTrangThaiText(tk.getTrangThai())
        });
    }
    
    private void btn_LamMoiActionPerformed(java.awt.event.ActionEvent evt) {
        txt_TimKiem.setText("");
        txt_TaiKhoan.setText("");
        txt_MatKhau.setText("");
        txt_MaNV.setText("");
        cmb_TrangThai.setSelectedIndex(0);
        loadDataToTable();
    }
    
    private void btn_ThemActionPerformed(java.awt.event.ActionEvent evt) {
        if(!validateInput()) return;
        
        String taiKhoan = txt_TaiKhoan.getText().trim();
        String matKhau = txt_MatKhau.getText().trim();
        String maNV = txt_MaNV.getText().trim();
        int trangThai = cmb_TrangThai.getSelectedIndex();
        
        NhanVienEntity nv = nvDao.findOne(maNV);
        if(nv == null) {
            JOptionPane.showMessageDialog(this, "Không tìm thấy nhân viên!");
            return;
        }
        
        TaiKhoanEntity tk = new TaiKhoanEntity(taiKhoan, matKhau, nv, trangThai);
        
        if(tkDao.insert(tk)) {
            JOptionPane.showMessageDialog(this, "Thêm tài khoản thành công!");
            loadDataToTable();
            btn_LamMoiActionPerformed(evt);
        } else {
            JOptionPane.showMessageDialog(this, "Thêm tài khoản thất bại!");
        }
    }
    
    private void btn_CapNhatActionPerformed(java.awt.event.ActionEvent evt) {
        int row = tbl_TaiKhoan.getSelectedRow();
        if(row < 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn tài khoản cần cập nhật!");
            return;
        }
        
        if(!validateInput()) return;
        
        String taiKhoan = txt_TaiKhoan.getText().trim();
        String matKhau = txt_MatKhau.getText().trim();
        String maNV = txt_MaNV.getText().trim();
        int trangThai = cmb_TrangThai.getSelectedIndex();
        
        NhanVienEntity nv = nvDao.findOne(maNV);
        if(nv == null) {
            JOptionPane.showMessageDialog(this, "Không tìm thấy nhân viên!");
            return;
        }
        
        TaiKhoanEntity tk = new TaiKhoanEntity(taiKhoan, matKhau, nv, trangThai);
        
        if(tkDao.update(tk)) {
            JOptionPane.showMessageDialog(this, "Cập nhật tài khoản thành công!");
            loadDataToTable();
            btn_LamMoiActionPerformed(evt);
        } else {
            JOptionPane.showMessageDialog(this, "Cập nhật tài khoản thất bại!");
        }
    }
    
    private void tbl_TaiKhoanMouseClicked(java.awt.event.MouseEvent evt) {
        int row = tbl_TaiKhoan.getSelectedRow();
        if(row < 0) return;
        
        String taiKhoan = tbl_TaiKhoan.getValueAt(row, 0).toString();
        TaiKhoanEntity tk = tkDao.findOne(taiKhoan);
        if(tk == null) return;
        
        txt_TaiKhoan.setText(tk.getTaiKhoan());
        txt_MatKhau.setText(tk.getMatKhau());
        txt_MaNV.setText(tk.getNhanVien() != null ? tk.getNhanVien().getMaNV() : "");
        cmb_TrangThai.setSelectedIndex(tk.getTrangThai());
    }
    
    private boolean validateInput() {
        String taiKhoan = txt_TaiKhoan.getText().trim();
        String matKhau = txt_MatKhau.getText().trim();
        String maNV = txt_MaNV.getText().trim();
        
        if(taiKhoan.isEmpty() || matKhau.isEmpty() || maNV.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin!");
            return false;
        }
        
        if(!taiKhoan.matches("^[a-zA-Z0-9]{6,20}$")) {
            JOptionPane.showMessageDialog(this, "Tài khoản phải từ 6-20 ký tự và không chứa ký tự đặc biệt!");
            return false;
        }
        
        if(!matKhau.matches("^[a-zA-Z0-9]{6,20}$")) {
            JOptionPane.showMessageDialog(this, "Mật khẩu phải từ 6-20 ký tự và không chứa ký tự đặc biệt!");
            return false;
        }
        
        return true;
    }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {
        // Generated code here
    }// </editor-fold>                        

    // Variables declaration - do not modify                     
    private javax.swing.JButton btn_CapNhat;
    private javax.swing.JButton btn_LamMoi;
    private javax.swing.JButton btn_Them;
    private javax.swing.JButton btn_TimKiem;
    private javax.swing.JComboBox<String> cmb_TrangThai;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tbl_TaiKhoan;
    private javax.swing.JTextField txt_MaNV;
    private javax.swing.JTextField txt_MatKhau;
    private javax.swing.JTextField txt_TaiKhoan;
    private javax.swing.JTextField txt_TimKiem;
    // End of variables declaration                   
}
