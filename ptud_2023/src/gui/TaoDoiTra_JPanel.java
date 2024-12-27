/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package gui;

import bus.ChiTietHoaDon_bus;
import bus.DoiTra_bus;
import bus.HoaDon_bus;
import entity.ChiTietDoiTraEntity;
import entity.ChiTietHoaDonEntity;
import entity.HoaDonEntity;
import entity.NhanVienEntity;
import java.awt.Image;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.net.URL;
import java.util.ArrayList;
import java.sql.Date;
import java.time.LocalDate;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.table.DefaultTableModel;
import util.ConvertDoubleToMoney;
import util.GenerateID;
import util.ToanCuc;

/**
 *
 * @author 84335
 */
public class TaoDoiTra_JPanel extends javax.swing.JPanel {

    /**
     * Creates new form DoiTra_JPanel
     */
    private HoaDon_bus hd_bus = new HoaDon_bus();
    private ChiTietHoaDon_bus cthd_bus = new ChiTietHoaDon_bus();
    private DefaultTableModel tableModel_HoaDon;
    private DefaultTableModel tableModel_GioHang;
    private GenerateID generateID = new GenerateID();
    private ToanCuc tc = new ToanCuc();
    private DoiTra_bus dt_bus = new DoiTra_bus();
    private SpinnerNumberModel spinnerModel;
    private ConvertDoubleToMoney convert = new ConvertDoubleToMoney();
    
    public TaoDoiTra_JPanel() {
        initComponents();
        setBounds(0, 0, 1173, 699);
        
        URL image_btnTimKiemHoaDon = TaoDoiTra_JPanel.class.getResource("/pic/icon/buttonTimKiem.png");
        ImageIcon img_btnTimKiemHoaDon = new ImageIcon(image_btnTimKiemHoaDon);
        Image scaled_btnTimKiemHoaDon = img_btnTimKiemHoaDon.getImage().getScaledInstance(15, 15, Image.SCALE_SMOOTH);
        img_btnTimKiemHoaDon = new ImageIcon(scaled_btnTimKiemHoaDon);
        btn_TimKiemHoaDon.setIcon(img_btnTimKiemHoaDon);
        
        URL image_btnThemVaoGio = TaoDoiTra_JPanel.class.getResource("/pic/icon/buttonThem.png");
        ImageIcon img_btnThemVaoGio = new ImageIcon(image_btnThemVaoGio);
        Image scaled_btnThemVaoGio = img_btnThemVaoGio.getImage().getScaledInstance(15, 15, Image.SCALE_SMOOTH);
        img_btnThemVaoGio = new ImageIcon(scaled_btnThemVaoGio);
        btn_ThemVaoGio.setIcon(img_btnThemVaoGio);
        
        URL image_btnCapNhatSoLuong = TaoDoiTra_JPanel.class.getResource("/pic/icon/buttonCapNhat.png");
        ImageIcon img_btnCapNhatSoLuong = new ImageIcon(image_btnCapNhatSoLuong);
        Image scaled_btnCapNhatSoLuong = img_btnCapNhatSoLuong.getImage().getScaledInstance(15, 15, Image.SCALE_SMOOTH);
        img_btnCapNhatSoLuong = new ImageIcon(scaled_btnCapNhatSoLuong);
        btn_CapNhatSoLuong.setIcon(img_btnCapNhatSoLuong);
        
        URL image_btnXoaKhoiGio = TaoDoiTra_JPanel.class.getResource("/pic/icon/buttonXoa.png");
        ImageIcon img_btnXoaKhoiGio = new ImageIcon(image_btnXoaKhoiGio);
        Image scaled_btnXoaKhoiGio = img_btnXoaKhoiGio.getImage().getScaledInstance(15, 15, Image.SCALE_SMOOTH);
        img_btnXoaKhoiGio = new ImageIcon(scaled_btnXoaKhoiGio);
        btn_XoaKhoiGio.setIcon(img_btnXoaKhoiGio);
        
        URL image_btnTaoDoiTra = TaoDoiTra_JPanel.class.getResource("/pic/icon/buttonThem.png");
        ImageIcon img_btnTaoDoiTra = new ImageIcon(image_btnTaoDoiTra);
        Image scaled_btnTaoDoiTra = img_btnTaoDoiTra.getImage().getScaledInstance(15, 15, Image.SCALE_SMOOTH);
        img_btnTaoDoiTra = new ImageIcon(scaled_btnTaoDoiTra);
        btn_TaoDoiTra.setIcon(img_btnTaoDoiTra);
        
        URL image_btnLamMoi = TaoDoiTra_JPanel.class.getResource("/pic/icon/buttonLamMoi.png");
        ImageIcon img_btnLamMoi = new ImageIcon(image_btnLamMoi);
        Image scaled_btnLamMoi = img_btnLamMoi.getImage().getScaledInstance(15, 15, Image.SCALE_SMOOTH);
        img_btnLamMoi = new ImageIcon(scaled_btnLamMoi);
        btn_LamMoi.setIcon(img_btnLamMoi); 
        
        // Table
        String[] cols_hd = {"Mã", "Tên sản phẩm", "Kích thước", "Màu sắc", "Số lượng", "Giá gốc","Giá bán", "Thành tiền"};
        tableModel_HoaDon = new DefaultTableModel(cols_hd, 0);
        table_HoaDon.setModel(tableModel_HoaDon);
        
        String[] cols_gh = {"Mã", "Tên sản phẩm", "Kích thước", "Màu sắc", "Số lượng","Giá bán", "Thành tiền"};
        tableModel_GioHang = new DefaultTableModel(cols_gh, 0);
        table_GioHang.setModel(tableModel_GioHang);
        
        // Spinner
        spinnerModel = new SpinnerNumberModel(0, 0, 100, 1);
        spinner_SoLuong.setModel(spinnerModel);
        spinner_SoLuong.setEnabled(false);
        
        JComponent editor = spinner_SoLuong.getEditor();
            if(editor instanceof JSpinner.DefaultEditor) {
                JTextField textField = ((JSpinner.DefaultEditor) editor).getTextField();

                textField.addKeyListener(new KeyAdapter() {
                    @Override
                    public void keyTyped(KeyEvent e) {
                        char typedChar = e.getKeyChar();
                        if (!Character.isDigit(typedChar)) {                        
                            e.consume(); 
                        }
                    }
                    
                     @Override
                    public void keyReleased(KeyEvent e) {
                         try {
                             int val = Integer.parseInt(textField.getText());
                             spinnerModel.setValue(val);
                         } catch (NumberFormatException evt) {
                             JOptionPane.showMessageDialog(null, "Số lượng nhập phải là chữ số!");
                         }
                    }
                });

                textField.addFocusListener(new FocusAdapter() {
                    @Override
                    public void focusLost(FocusEvent e) {
                        kiemTraSoLuongNhap();
                    }
                });
            }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup = new javax.swing.ButtonGroup();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        txt_MaHoaDon = new javax.swing.JTextField();
        btn_TimKiemHoaDon = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        lbl_MaKhachHang = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        lbl_TenKhachHang = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        lbl_NgayLap = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        lbl_SoDienThoai = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        lbl_TongTien = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        lbl_TienKhuyenMai = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        lbl_TienThanhToan = new javax.swing.JLabel();
        lbl_tongTien1 = new javax.swing.JLabel();
        lbl_KhuyenMai2 = new javax.swing.JLabel();
        lbl_thanhToan1 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        table_HoaDon = new javax.swing.JTable();
        btn_ThemVaoGio = new javax.swing.JButton();
        jLabel25 = new javax.swing.JLabel();
        spinner_SoLuong = new javax.swing.JSpinner();
        btn_CapNhatSoLuong = new javax.swing.JButton();
        btn_XoaKhoiGio = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel26 = new javax.swing.JLabel();
        radiobtn_HoanTra = new javax.swing.JRadioButton();
        radiobtn_DoiMoi = new javax.swing.JRadioButton();
        jLabel24 = new javax.swing.JLabel();
        lbl_TienHoanTra = new javax.swing.JLabel();
        btn_TaoDoiTra = new javax.swing.JButton();
        lbl_TienHoanTra1 = new javax.swing.JLabel();
        btn_LamMoi = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        table_GioHang = new javax.swing.JTable();

        setBackground(new java.awt.Color(187, 205, 197));
        setPreferredSize(new java.awt.Dimension(1170, 690));
        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBackground(new java.awt.Color(187, 205, 197));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Thông tin hoá đơn", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 1, 14))); // NOI18N

        jLabel2.setFont(new java.awt.Font("Times New Roman", 0, 16)); // NOI18N
        jLabel2.setText("Mã hoá đơn");

        txt_MaHoaDon.setFont(new java.awt.Font("Times New Roman", 0, 16)); // NOI18N

        btn_TimKiemHoaDon.setBackground(new java.awt.Color(0, 51, 51));
        btn_TimKiemHoaDon.setFont(new java.awt.Font("Times New Roman", 1, 15)); // NOI18N
        btn_TimKiemHoaDon.setForeground(new java.awt.Color(255, 255, 255));
        btn_TimKiemHoaDon.setText("Tìm kiếm");
        btn_TimKiemHoaDon.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btn_TimKiemHoaDon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_TimKiemHoaDonActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Times New Roman", 1, 20)); // NOI18N
        jLabel3.setText("Mã khách hàng");

        lbl_MaKhachHang.setFont(new java.awt.Font("Times New Roman", 2, 20)); // NOI18N
        lbl_MaKhachHang.setText(" ");

        jLabel5.setFont(new java.awt.Font("Times New Roman", 1, 20)); // NOI18N
        jLabel5.setText("Tên khách hàng");

        lbl_TenKhachHang.setFont(new java.awt.Font("Times New Roman", 2, 20)); // NOI18N
        lbl_TenKhachHang.setText(" ");

        jLabel7.setFont(new java.awt.Font("Times New Roman", 1, 20)); // NOI18N
        jLabel7.setText("Ngày lập");

        lbl_NgayLap.setFont(new java.awt.Font("Times New Roman", 2, 20)); // NOI18N
        lbl_NgayLap.setText(" ");

        jLabel9.setFont(new java.awt.Font("Times New Roman", 1, 20)); // NOI18N
        jLabel9.setText("Số điện thoại");

        lbl_SoDienThoai.setFont(new java.awt.Font("Times New Roman", 2, 20)); // NOI18N
        lbl_SoDienThoai.setText(" ");

        jLabel11.setFont(new java.awt.Font("Times New Roman", 1, 20)); // NOI18N
        jLabel11.setText("Tổng tiền");

        lbl_TongTien.setFont(new java.awt.Font("Times New Roman", 2, 20)); // NOI18N
        lbl_TongTien.setText(" ");

        jLabel13.setFont(new java.awt.Font("Times New Roman", 1, 20)); // NOI18N
        jLabel13.setText("Tiền khuyến mãi");

        lbl_TienKhuyenMai.setFont(new java.awt.Font("Times New Roman", 2, 20)); // NOI18N
        lbl_TienKhuyenMai.setText(" ");

        jLabel17.setFont(new java.awt.Font("Times New Roman", 1, 20)); // NOI18N
        jLabel17.setText("Tiền phải thanh toán");

        lbl_TienThanhToan.setFont(new java.awt.Font("Times New Roman", 2, 20)); // NOI18N
        lbl_TienThanhToan.setText(" ");

        lbl_tongTien1.setFont(new java.awt.Font("Times New Roman", 2, 20)); // NOI18N
        lbl_tongTien1.setText("VND");

        lbl_KhuyenMai2.setFont(new java.awt.Font("Times New Roman", 2, 20)); // NOI18N
        lbl_KhuyenMai2.setText("VND");

        lbl_thanhToan1.setFont(new java.awt.Font("Times New Roman", 2, 20)); // NOI18N
        lbl_thanhToan1.setText("VND");

        table_HoaDon.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        table_HoaDon.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                table_HoaDonMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(table_HoaDon);
        if (table_HoaDon.getColumnModel().getColumnCount() > 0) {
            table_HoaDon.getColumnModel().getColumn(1).setPreferredWidth(200);
            table_HoaDon.getColumnModel().getColumn(2).setPreferredWidth(20);
            table_HoaDon.getColumnModel().getColumn(3).setPreferredWidth(50);
            table_HoaDon.getColumnModel().getColumn(4).setPreferredWidth(20);
        }

        btn_ThemVaoGio.setBackground(new java.awt.Color(0, 51, 51));
        btn_ThemVaoGio.setFont(new java.awt.Font("Times New Roman", 1, 15)); // NOI18N
        btn_ThemVaoGio.setForeground(new java.awt.Color(255, 255, 255));
        btn_ThemVaoGio.setText("Thêm vào giỏ");
        btn_ThemVaoGio.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btn_ThemVaoGio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_ThemVaoGioActionPerformed(evt);
            }
        });

        jLabel25.setFont(new java.awt.Font("Times New Roman", 0, 16)); // NOI18N
        jLabel25.setText("Số lượng");

        spinner_SoLuong.setFont(new java.awt.Font("Times New Roman", 0, 15)); // NOI18N

        btn_CapNhatSoLuong.setBackground(new java.awt.Color(0, 51, 51));
        btn_CapNhatSoLuong.setFont(new java.awt.Font("Times New Roman", 1, 15)); // NOI18N
        btn_CapNhatSoLuong.setForeground(new java.awt.Color(255, 255, 255));
        btn_CapNhatSoLuong.setText("Cập nhật số lượng");
        btn_CapNhatSoLuong.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btn_CapNhatSoLuong.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_CapNhatSoLuongActionPerformed(evt);
            }
        });

        btn_XoaKhoiGio.setBackground(new java.awt.Color(204, 0, 0));
        btn_XoaKhoiGio.setFont(new java.awt.Font("Times New Roman", 1, 15)); // NOI18N
        btn_XoaKhoiGio.setForeground(new java.awt.Color(255, 255, 255));
        btn_XoaKhoiGio.setText("Xoá khỏi giỏ");
        btn_XoaKhoiGio.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btn_XoaKhoiGio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_XoaKhoiGioActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addGap(18, 18, 18)
                                .addComponent(lbl_MaKhachHang, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(txt_MaHoaDon, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addGap(18, 18, 18)
                                .addComponent(lbl_TenKhachHang, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel9)
                                .addGap(18, 18, 18)
                                .addComponent(lbl_SoDienThoai, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel7)
                                .addGap(18, 18, 18)
                                .addComponent(lbl_NgayLap, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addComponent(btn_TimKiemHoaDon, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel25, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(spinner_SoLuong, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btn_ThemVaoGio, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btn_CapNhatSoLuong, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(12, 12, 12)
                                .addComponent(btn_XoaKhoiGio, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(5, 5, 5))))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lbl_TongTien, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lbl_tongTien1)
                        .addGap(43, 43, 43)
                        .addComponent(jLabel13)
                        .addGap(18, 18, 18)
                        .addComponent(lbl_TienKhuyenMai, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lbl_KhuyenMai2)
                        .addGap(40, 40, 40)
                        .addComponent(jLabel17)
                        .addGap(26, 26, 26)
                        .addComponent(lbl_TienThanhToan, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(lbl_thanhToan1)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 1149, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 5, Short.MAX_VALUE))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txt_MaHoaDon, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_TimKiemHoaDon, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_ThemVaoGio, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel25)
                    .addComponent(spinner_SoLuong, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_CapNhatSoLuong, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_XoaKhoiGio, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_MaKhachHang, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_NgayLap, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_TenKhachHang, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_SoDienThoai, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(9, 9, 9)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_TongTien, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_TienKhuyenMai, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_tongTien1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_KhuyenMai2, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_TienThanhToan, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_thanhToan1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1170, 350));

        jPanel3.setBackground(new java.awt.Color(187, 205, 197));
        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Thông tin đơn đổi trả", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 1, 14))); // NOI18N

        jLabel26.setFont(new java.awt.Font("Times New Roman", 0, 16)); // NOI18N
        jLabel26.setText("Hình thức đổi trả");

        radiobtn_HoanTra.setBackground(new java.awt.Color(187, 205, 197));
        buttonGroup.add(radiobtn_HoanTra);
        radiobtn_HoanTra.setFont(new java.awt.Font("Times New Roman", 2, 16)); // NOI18N
        radiobtn_HoanTra.setText("Hoàn trả");
        radiobtn_HoanTra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radiobtn_HoanTraActionPerformed(evt);
            }
        });

        radiobtn_DoiMoi.setBackground(new java.awt.Color(187, 205, 197));
        buttonGroup.add(radiobtn_DoiMoi);
        radiobtn_DoiMoi.setFont(new java.awt.Font("Times New Roman", 2, 16)); // NOI18N
        radiobtn_DoiMoi.setText("Đổi mới");
        radiobtn_DoiMoi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radiobtn_DoiMoiActionPerformed(evt);
            }
        });

        jLabel24.setFont(new java.awt.Font("Times New Roman", 1, 20)); // NOI18N
        jLabel24.setText("Tiền hoàn trả");

        lbl_TienHoanTra.setFont(new java.awt.Font("Times New Roman", 2, 20)); // NOI18N
        lbl_TienHoanTra.setText(" ");

        btn_TaoDoiTra.setBackground(new java.awt.Color(0, 51, 51));
        btn_TaoDoiTra.setFont(new java.awt.Font("Times New Roman", 1, 15)); // NOI18N
        btn_TaoDoiTra.setForeground(new java.awt.Color(255, 255, 255));
        btn_TaoDoiTra.setText("Tạo đơn đổi trả");
        btn_TaoDoiTra.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btn_TaoDoiTra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_TaoDoiTraActionPerformed(evt);
            }
        });

        lbl_TienHoanTra1.setFont(new java.awt.Font("Times New Roman", 2, 20)); // NOI18N
        lbl_TienHoanTra1.setText("VND");

        btn_LamMoi.setBackground(new java.awt.Color(0, 51, 51));
        btn_LamMoi.setFont(new java.awt.Font("Times New Roman", 1, 15)); // NOI18N
        btn_LamMoi.setForeground(new java.awt.Color(255, 255, 255));
        btn_LamMoi.setText("Làm mới");
        btn_LamMoi.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btn_LamMoi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_LamMoiActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel26)
                        .addGap(18, 18, 18)
                        .addComponent(radiobtn_HoanTra)
                        .addGap(18, 18, 18)
                        .addComponent(radiobtn_DoiMoi)
                        .addGap(32, 32, 32)
                        .addComponent(btn_TaoDoiTra, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btn_LamMoi, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel24)
                        .addGap(18, 18, 18)
                        .addComponent(lbl_TienHoanTra, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(lbl_TienHoanTra1)))
                .addContainerGap(528, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel26)
                    .addComponent(radiobtn_HoanTra)
                    .addComponent(radiobtn_DoiMoi)
                    .addComponent(btn_TaoDoiTra, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_LamMoi, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel24)
                    .addComponent(lbl_TienHoanTra, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_TienHoanTra1))
                .addContainerGap())
        );

        add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 586, 1170, 100));

        jPanel1.setBackground(new java.awt.Color(187, 205, 197));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Giỏ hàng", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 1, 14))); // NOI18N

        table_GioHang.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        table_GioHang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                table_GioHangMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(table_GioHang);
        if (table_GioHang.getColumnModel().getColumnCount() > 0) {
            table_GioHang.getColumnModel().getColumn(1).setPreferredWidth(200);
            table_GioHang.getColumnModel().getColumn(2).setPreferredWidth(20);
            table_GioHang.getColumnModel().getColumn(3).setPreferredWidth(50);
            table_GioHang.getColumnModel().getColumn(4).setPreferredWidth(20);
        }

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1148, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 190, Short.MAX_VALUE)
                .addContainerGap())
        );

        add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 360, 1170, 220));
    }// </editor-fold>//GEN-END:initComponents

    private void radiobtn_HoanTraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radiobtn_HoanTraActionPerformed
        tinhTienHoanTra();
    }//GEN-LAST:event_radiobtn_HoanTraActionPerformed

    private void btn_TaoDoiTraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_TaoDoiTraActionPerformed
        taoDoiTra();
    }//GEN-LAST:event_btn_TaoDoiTraActionPerformed

    private void table_GioHangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_table_GioHangMouseClicked
        table_HoaDon.clearSelection();
        int row = table_GioHang.getSelectedRow();
        String maSP = tableModel_GioHang.getValueAt(row, 0).toString();
        int soLuong = Integer.parseInt(tableModel_GioHang.getValueAt(row, 4).toString());
        spinnerModel.setMinimum(0);
        spinnerModel.setValue(soLuong);
        
        for (int i = 0; i < tableModel_HoaDon.getRowCount(); i++) {
            String ma = tableModel_HoaDon.getValueAt(i, 0).toString();
            if(ma.equals(maSP)) {
                int sl = Integer.parseInt(tableModel_HoaDon.getValueAt(row, 4).toString());
                spinnerModel.setMaximum(sl);
                break;
            }
        }
    }//GEN-LAST:event_table_GioHangMouseClicked

    private void btn_TimKiemHoaDonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_TimKiemHoaDonActionPerformed
        timKiemHoaDon();
    }//GEN-LAST:event_btn_TimKiemHoaDonActionPerformed

    private void radiobtn_DoiMoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radiobtn_DoiMoiActionPerformed
        lbl_TienHoanTra.setText("0");
    }//GEN-LAST:event_radiobtn_DoiMoiActionPerformed

    private void table_HoaDonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_table_HoaDonMouseClicked
        table_GioHang.clearSelection();
        int row = table_HoaDon.getSelectedRow();
        int sl = Integer.parseInt(table_HoaDon.getValueAt(row, 4).toString());
        spinner_SoLuong.setEnabled(true);
        spinnerModel.setValue(sl);
        spinnerModel.setMinimum(0);
        spinnerModel.setMaximum(sl);
    }//GEN-LAST:event_table_HoaDonMouseClicked

    private void btn_ThemVaoGioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_ThemVaoGioActionPerformed
        themVaoGioHang();
    }//GEN-LAST:event_btn_ThemVaoGioActionPerformed

    private void btn_CapNhatSoLuongActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_CapNhatSoLuongActionPerformed
        capNhatSoLuong();
    }//GEN-LAST:event_btn_CapNhatSoLuongActionPerformed

    private void btn_XoaKhoiGioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_XoaKhoiGioActionPerformed
        xoaKhoiGioHang();
    }//GEN-LAST:event_btn_XoaKhoiGioActionPerformed

    private void btn_LamMoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_LamMoiActionPerformed
        lamMoi();
    }//GEN-LAST:event_btn_LamMoiActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_CapNhatSoLuong;
    private javax.swing.JButton btn_LamMoi;
    private javax.swing.JButton btn_TaoDoiTra;
    private javax.swing.JButton btn_ThemVaoGio;
    private javax.swing.JButton btn_TimKiemHoaDon;
    private javax.swing.JButton btn_XoaKhoiGio;
    private javax.swing.ButtonGroup buttonGroup;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lbl_KhuyenMai2;
    private javax.swing.JLabel lbl_MaKhachHang;
    private javax.swing.JLabel lbl_NgayLap;
    private javax.swing.JLabel lbl_SoDienThoai;
    private javax.swing.JLabel lbl_TenKhachHang;
    private javax.swing.JLabel lbl_TienHoanTra;
    private javax.swing.JLabel lbl_TienHoanTra1;
    private javax.swing.JLabel lbl_TienKhuyenMai;
    private javax.swing.JLabel lbl_TienThanhToan;
    private javax.swing.JLabel lbl_TongTien;
    private javax.swing.JLabel lbl_thanhToan1;
    private javax.swing.JLabel lbl_tongTien1;
    private javax.swing.JRadioButton radiobtn_DoiMoi;
    private javax.swing.JRadioButton radiobtn_HoanTra;
    private javax.swing.JSpinner spinner_SoLuong;
    private javax.swing.JTable table_GioHang;
    private javax.swing.JTable table_HoaDon;
    private javax.swing.JTextField txt_MaHoaDon;
    // End of variables declaration//GEN-END:variables
    
    public void timKiemHoaDon() {
        String maHD = txt_MaHoaDon.getText().trim();
        if(maHD.equals("")) {
            JOptionPane.showMessageDialog(this, "Mã hoá đơn chưa được nhập!");
            return;
        }
        
        HoaDonEntity hoaDon = hd_bus.timKiemHoaDonTheoMa(maHD);
        if(hoaDon != null) {
            boolean kq = dt_bus.kiemTraThoiHanDoiTra(hoaDon.getMaHD());
            if(!kq) {
                JOptionPane.showMessageDialog(this, "Hoá đơn đã hết thời hạn đổi trả!");
                return;
            }
            
//            if(hoaDon.getKhachHang() != null) {
//                lbl_MaKhachHang.setText(hoaDon.getKhachHang().getMaKH());
//                lbl_TenKhachHang.setText(hoaDon.getKhachHang().getHoTen());
//                lbl_SoDienThoai.setText(hoaDon.getKhachHang().getSoDienThoai());
//            }
            
//            lbl_NgayLap.setText(hoaDon.getNgayLapHD().toString());
            lbl_TongTien.setText(convert.toMoney(hoaDon.getTongTien()));
//            lbl_TienKhuyenMai.setText(convert.toMoney(hoaDon.getTienKhuyenMai()));
//            lbl_TienThanhToan.setText(convert.toMoney(hoaDon.getTienThanhToan()));
            ArrayList<ChiTietHoaDonEntity> cthdList = cthd_bus.getAllCTHDTheoMaHD(maHD);
            if(cthdList != null) {
                tableModel_HoaDon.setRowCount(0);
                for (ChiTietHoaDonEntity cthd : cthdList) {
//                    String[] data = {cthd.getSanPham().getMaSP(), cthd.getSanPham().getTenSP(), cthd.getSanPham().getKichThuoc().toString(), cthd.getSanPham().getMauSac().toString(), 
//                    cthd.getSoLuong()+"", convert.toMoney(cthd.getGiaGoc()), convert.toMoney(cthd.getGiaBan()), convert.toMoney(cthd.getThanhTien())};
//                    tableModel_HoaDon.addRow(data);
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Hoá đơn không tồn tại!");
        }
    }
    
    public boolean kiemTraSoLuongNhap() {
        Object val = spinner_SoLuong.getValue();

        if(val instanceof Number) {
            int sl = ((Number) val).intValue();
            Object slMax = spinnerModel.getMaximum();
            if(sl <= 0) {
                JOptionPane.showMessageDialog(null, "Số lượng nhập lớn hơn 0");
                return false;
            } else if(sl > ((int) slMax)) {
                JOptionPane.showMessageDialog(null, "Số lượng nhập nhỏ hơn hoặc bằng "+slMax);
                return false;
            }
        } else {
            JOptionPane.showMessageDialog(this, "Số lượng nhập phải là chữ số!");
            return false;
        }
        return true;
    }
    
    public void themVaoGioHang() {
        int row = table_HoaDon.getSelectedRow();
        if(row < 0) {
            JOptionPane.showMessageDialog(this, "Sản phẩm đổi trả trong hoá đơn chưa được chọn!");
            return;
        }
        
        if(!kiemTraSoLuongNhap()) return;
        
        String maSP = tableModel_HoaDon.getValueAt(row, 0).toString();
        for (int i = 0; i < table_GioHang.getRowCount(); i++) {
            String ma = tableModel_GioHang.getValueAt(i, 0).toString();
            if(ma.equals(maSP)) {
                JOptionPane.showMessageDialog(this, "Sản phẩm đã tồn tại trong giỏ hàng");
                return;
            }
        }
        String tenSP = tableModel_HoaDon.getValueAt(row, 1).toString();
        String kichThuoc = tableModel_HoaDon.getValueAt(row, 2).toString();
        String mauSac = tableModel_HoaDon.getValueAt(row, 3).toString();
        String soLuong = spinner_SoLuong.getValue().toString();
        // Xu ly so luong
        int soLuongMax = Integer.parseInt(spinnerModel.getMaximum().toString());
        String maHD = txt_MaHoaDon.getText().trim();
        int tongSoLuong = dt_bus.getTongSoLuongSPDoiTra(maHD, maSP);
        int soLuongConLai = soLuongMax - tongSoLuong;
        if(soLuongConLai == 0) {
            JOptionPane.showMessageDialog(this, "Sản phẩm này đã được hoàn trả hết số lượng!");
            table_HoaDon.clearSelection();
            return;
        }
        if(soLuongConLai < Integer.parseInt(soLuong)) {
            JOptionPane.showMessageDialog(this, "Số lượng hoàn trả của sản phẩm này tối đa là "+soLuongConLai);
            return;
        }
        String giaBan = tableModel_HoaDon.getValueAt(row, 5).toString();
        double thanhTien = convert.toDouble(giaBan) * Integer.parseInt(soLuong);
        
        String[] data = {maSP, tenSP, kichThuoc, mauSac, soLuong, giaBan, convert.toMoney(thanhTien)};
        tableModel_GioHang.addRow(data);
        table_HoaDon.clearSelection();
        
        if(radiobtn_HoanTra.isSelected()) {
            tinhTienHoanTra();
        }
    }
    
    public void capNhatSoLuong() {
        int row = table_GioHang.getSelectedRow();
        if(row < 0) {
            JOptionPane.showMessageDialog(this, "Sản phẩm trong giỏ hàng chưa được chọn!");
            return;
        }
        if(!kiemTraSoLuongNhap()) return;
        int sl = Integer.parseInt(spinnerModel.getValue().toString());
        String maSP = tableModel_GioHang.getValueAt(row, 0).toString();
        // Xu ly so luong
        int soLuongMax = Integer.parseInt(spinnerModel.getMaximum().toString());
        String maHD = txt_MaHoaDon.getText().trim();
        int tongSoLuong = dt_bus.getTongSoLuongSPDoiTra(maHD, maSP);
        int soLuongConLai = soLuongMax - tongSoLuong;
        if(soLuongConLai == 0) {
            JOptionPane.showMessageDialog(this, "Sản phẩm này đã được hoàn trả hết số lượng!");
            return;
        }
        if(soLuongConLai < sl) {
            JOptionPane.showMessageDialog(this, "Số lượng hoàn trả của sản phẩm này tối đa là "+soLuongConLai);
            return;
        }
        table_GioHang.setValueAt(sl, row, 4);
        double giaBan = convert.toDouble(table_GioHang.getValueAt(row, 5).toString());
        table_GioHang.setValueAt(convert.toMoney(giaBan*sl), row, 6);
        
        table_GioHang.clearSelection();
        
        if(radiobtn_HoanTra.isSelected()) {
            tinhTienHoanTra();
        }
    }
    
    public void xoaKhoiGioHang() {
        int row = table_GioHang.getSelectedRow();
        if(row < 0) {
            JOptionPane.showMessageDialog(this, "Sản phẩm trong giỏ hàng chưa được chọn!");
            return;
        }
        
        tableModel_GioHang.removeRow(row);
        
        if(radiobtn_HoanTra.isSelected()) {
            tinhTienHoanTra();
        }
    }
    
    public void tinhTienHoanTra() {
        double tongTien = 0;
        for (int i = 0; i < tableModel_GioHang.getRowCount(); i++) {
            double thanhTien = convert.toDouble(tableModel_GioHang.getValueAt(i, 6).toString());
            tongTien+=thanhTien;
        }
        lbl_TienHoanTra.setText(convert.toMoney(tongTien));
    }
    
    public void taoDoiTra() {
        if(!radiobtn_HoanTra.isSelected() && !radiobtn_DoiMoi.isSelected()) {
            JOptionPane.showMessageDialog(this, "Hình thức đổi trả chưa chọn!");
            return;
        }
        
        if(tableModel_GioHang.getRowCount() <= 0) {
            JOptionPane.showMessageDialog(this, "Giỏ hàng đổi trả không được rỗng!");
            return;
        }
        
        String maDT = generateID.sinhMa("DT");
        String maHD = txt_MaHoaDon.getText().trim();
//        HoaDonEntity hd = new HoaDonEntity(maHD);
        String maNV = tc.getMa();
        NhanVienEntity nv = new NhanVienEntity(maNV);
        Date thoiGian = Date.valueOf(LocalDate.now());
        
//        ConvertStringToEnum toEnum = new ConvertStringToEnum();
        String hinhThuc = "Hoàn trả";
        if(radiobtn_DoiMoi.isSelected()) {
            hinhThuc = "Đổi mới";
        }
        double tongTien = convert.toDouble(lbl_TienHoanTra.getText().trim().toString());
        
//        DoiTraEntity dt = new DoiTraEntity(maDT, hd, nv, (HinhThucDoiTraEnum) toEnum.HinhThucDTToEnum(hinhThuc), thoiGian, tongTien);
        
        ArrayList<ChiTietDoiTraEntity> ctdtList = new ArrayList<ChiTietDoiTraEntity>();
        for (int i = 0; i < tableModel_GioHang.getRowCount(); i++) {
            String maSP = tableModel_GioHang.getValueAt(i, 0).toString();
//            SanPhamEntity sanPham = new SanPhamEntity(maSP);
            int soLuong = Integer.parseInt(tableModel_GioHang.getValueAt(i, 4).toString());
            double giaBan = convert.toDouble(tableModel_GioHang.getValueAt(i, 5).toString());
            double thanhTien = convert.toDouble(tableModel_GioHang.getValueAt(i, 6).toString());
            
//            ChiTietDoiTraEntity ctdt = new ChiTietDoiTraEntity(sanPham, dt, soLuong, giaBan, thanhTien);
            
//            ctdtList.add(ctdt);
        }
        
//        boolean kq = dt_bus.taoDoiTra(dt, ctdtList);
//        if(kq) {
//            lamMoi();
//            new ThongTinDoiTra_GUI(dt.getMaDT()).setVisible(true);
//        } else {
//            JOptionPane.showMessageDialog(this, "Tạo đơn đổi trả thất bại!");
//        }
    }
    
    public void lamMoi() {
        txt_MaHoaDon.setText("");
        lbl_MaKhachHang.setText("");
        lbl_TenKhachHang.setText("");
        lbl_SoDienThoai.setText("");
        lbl_NgayLap.setText("");
        lbl_TongTien.setText("");
        lbl_TienKhuyenMai.setText("");
        lbl_TienThanhToan.setText("");
        spinner_SoLuong.setValue(0);
        spinner_SoLuong.setEnabled(false);
        
        tableModel_HoaDon.setRowCount(0);
        tableModel_GioHang.setRowCount(0);
        buttonGroup.clearSelection();
        lbl_TienHoanTra.setText("");
   }
}
