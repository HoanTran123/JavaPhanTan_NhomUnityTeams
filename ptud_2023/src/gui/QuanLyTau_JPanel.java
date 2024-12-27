package gui;

import java.awt.Component;
import java.awt.Desktop;
import java.awt.Font;
import java.awt.Image;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import dao.Ga_dao;
import dao.Tau_dao;
import entity.GaTauEntity;
import entity.NhanVienEntity;
import entity.TauEntity;
import entity.TauEnum;
import util.GenerateID;
import util.ToanCuc;

/**
 *
 * @author ploc
 */
public class QuanLyTau_JPanel extends javax.swing.JPanel {

    private String duongDanAnh = null;
    private Tau_dao tau_dao;
    private Ga_dao ga_dao;

    private String maTauHienTai = "";
    private TauEntity tauHienTai = null;

    // private DefaultTableModel model;

    /**
     * Creates new form SanPham_JPanel
     */
    public QuanLyTau_JPanel() {
        initComponents();
        // Khởi tạo
        // model = new DefaultTableModel();
        model = new DefaultTableModel(new String[] {
                "Mã", "Tên", "Loại tàu", "Ga đi", "Ga đến", "Trạng thái", "Số toa", "Đơn giá", "Nhân viên", "Ngày tạo",
                "Ngày cập nhật"
        }, 0);

        tau_dao = new Tau_dao();
        ga_dao = new Ga_dao();

        // ----------
        setBounds(0, 0, 1186, 748);
        URL urlBtnTimKiem = getClass().getResource("/pic/icon/buttonTimKiem.png");
        ImageIcon img_btnTimKiem = new ImageIcon(urlBtnTimKiem);
        Image scaled_btnTimKiem = img_btnTimKiem.getImage().getScaledInstance(15, 15, Image.SCALE_SMOOTH);
        img_btnTimKiem = new ImageIcon(scaled_btnTimKiem);
        btn_TimKiem.setIcon(img_btnTimKiem);

        URL urlBtnLamMoi = getClass().getResource("/pic/icon/buttonLamMoi.png");
        ImageIcon img_btnLamMoi = new ImageIcon(urlBtnLamMoi);
        Image scaled_btnLamMoi = img_btnLamMoi.getImage().getScaledInstance(15, 15, Image.SCALE_SMOOTH);
        img_btnLamMoi = new ImageIcon(scaled_btnLamMoi);
        btn_LamMoi.setIcon(img_btnLamMoi);

        URL urlBtnThem = getClass().getResource("/pic/icon/buttonThem.png");
        ImageIcon img_btnThem = new ImageIcon(urlBtnThem);
        Image scaled_btnThem = img_btnThem.getImage().getScaledInstance(15, 15, Image.SCALE_SMOOTH);
        img_btnThem = new ImageIcon(scaled_btnThem);
        btn_Them.setIcon(img_btnThem);

        URL urlBtnCapNhat = getClass().getResource("/pic/icon/buttonCapNhat.png");
        ImageIcon img_btnCapNhat = new ImageIcon(urlBtnCapNhat);
        Image scaled_btnCapNhat = img_btnCapNhat.getImage().getScaledInstance(15, 15, Image.SCALE_SMOOTH);
        img_btnCapNhat = new ImageIcon(scaled_btnCapNhat);
        btn_CapNhat.setIcon(img_btnCapNhat);

        URL urlBtnNhapExcel = getClass().getResource("/pic/icon/buttonNhapExcel.png");
        ImageIcon img_btnNhapExcel = new ImageIcon(urlBtnNhapExcel);
        Image scaled_btnNhapExcel = img_btnNhapExcel.getImage().getScaledInstance(15, 15, Image.SCALE_SMOOTH);
        img_btnNhapExcel = new ImageIcon(scaled_btnNhapExcel);
        btn_NhapExcel.setIcon(img_btnNhapExcel);

        URL urlBtnXuatExcel = getClass().getResource("/pic/icon/buttonXuatExcel.png");
        ImageIcon img_btnXuatExcel = new ImageIcon(urlBtnXuatExcel);
        Image scaled_btnXuatExcel = img_btnXuatExcel.getImage().getScaledInstance(15, 15, Image.SCALE_SMOOTH);
        img_btnXuatExcel = new ImageIcon(scaled_btnXuatExcel);
        btn_XuatExcel.setIcon(img_btnXuatExcel);

        URL urlBtnQuanLyGa = getClass().getResource("/pic/icon/quan-ly-toa2.png");
        Image scaled_btn = new ImageIcon(urlBtnQuanLyGa).getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH);
        ImageIcon img = new ImageIcon(scaled_btn);
        btn_QuanLyToa.setIcon(img);

        URL urlBtnQuanLyGa1 = getClass().getResource("/pic/icon/seat-icon-vector.jpg");
        Image scaled_btn2 = new ImageIcon(urlBtnQuanLyGa1).getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH);
        ImageIcon img1 = new ImageIcon(scaled_btn2);
        btn_QuanLyGhe.setIcon(img1);

        URL urlBtnQuanLyGa2 = getClass().getResource("/pic/icon/quan-ly-ga.png");
        Image scaled_btn3 = new ImageIcon(urlBtnQuanLyGa2).getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH);
        ImageIcon img2 = new ImageIcon(scaled_btn3);
        btn_QuanLyGa.setIcon(img2);

        btn_QuanLyToa.setEnabled(false);
        btn_QuanLyGhe.setEnabled(false);

        loadDuLieuTuDataLenTable();

        ArrayList<GaTauEntity> dsGaTau = ga_dao.getAllGaTau();
        duaDuLieuVaoComboBox(cbo_GaDi, dsGaTau, "TenGa");
        duaDuLieuVaoComboBox(cbo_GaDen, dsGaTau, "TenGa");
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated
    // Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane2 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        panel_ThongTin = new javax.swing.JPanel();
        lbl_MaSanPham = new javax.swing.JLabel();
        lbl_TenSanPham = new javax.swing.JLabel();
        txt_MaTau = new javax.swing.JTextField();
        txt_TenTau = new javax.swing.JTextField();
        lbl_ThuongHieu = new javax.swing.JLabel();
        txt_SoToa = new javax.swing.JTextField();
        lbl_DanhMuc = new javax.swing.JLabel();
        lbl_KichThuoc = new javax.swing.JLabel();
        cbo_LoaiTau = new javax.swing.JComboBox<>();
        lbl_ChatLieu = new javax.swing.JLabel();
        lbl_TinhTrang = new javax.swing.JLabel();
        cbo_TinhTrang = new javax.swing.JComboBox<>();
        lbl_DonGia = new javax.swing.JLabel();
        txt_DonGia = new javax.swing.JTextField();
        cbo_GaDi = new javax.swing.JComboBox<>();
        cbo_GaDen = new javax.swing.JComboBox<>();
        btn_QuanLyGa = new javax.swing.JButton();
        btn_QuanLyGhe = new javax.swing.JButton();
        btn_QuanLyToa = new javax.swing.JButton();
        panel_ThaoTac = new javax.swing.JPanel();
        lbl_MaSanPham_Search = new javax.swing.JLabel();
        btn_TimKiem = new javax.swing.JButton();
        btn_LamMoi = new javax.swing.JButton();
        btn_Them = new javax.swing.JButton();
        btn_CapNhat = new javax.swing.JButton();
        txt_MaTau_Search = new javax.swing.JTextField();
        btn_XuatExcel = new javax.swing.JButton();
        btn_NhapExcel = new javax.swing.JButton();
        panel_DanhSachSanPham = new javax.swing.JPanel();
        scroll_TableSanPham = new javax.swing.JScrollPane();
        table_DanhSachTau = new javax.swing.JTable();
        lbl_TieuDe = new javax.swing.JLabel();

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][] {
                        { null, null, null, null },
                        { null, null, null, null },
                        { null, null, null, null },
                        { null, null, null, null }
                },
                new String[] {
                        "Title 1", "Title 2", "Title 3", "Title 4"
                }));
        jScrollPane2.setViewportView(jTable1);

        setBackground(new java.awt.Color(187, 205, 197));
        setMaximumSize(new java.awt.Dimension(1186, 748));
        setPreferredSize(new java.awt.Dimension(1186, 748));

        panel_ThongTin.setBackground(new java.awt.Color(187, 205, 197));
        panel_ThongTin.setBorder(javax.swing.BorderFactory.createTitledBorder(
                javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Thông tin Tàu",
                javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION,
                new java.awt.Font("Times New Roman", 0, 12))); // NOI18N
        panel_ThongTin.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lbl_MaSanPham.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        lbl_MaSanPham.setText("Mã tàu");
        lbl_MaSanPham.setPreferredSize(new java.awt.Dimension(85, 15));
        panel_ThongTin.add(lbl_MaSanPham, new org.netbeans.lib.awtextra.AbsoluteConstraints(11, 22, 100, 25));

        lbl_TenSanPham.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        lbl_TenSanPham.setText("Tên tàu");
        lbl_TenSanPham.setPreferredSize(new java.awt.Dimension(85, 15));
        panel_ThongTin.add(lbl_TenSanPham, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, 100, 25));

        txt_MaTau.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        txt_MaTau.setEditable(false);
        panel_ThongTin.add(txt_MaTau, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 20, 200, 30));

        txt_TenTau.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        panel_ThongTin.add(txt_TenTau, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 70, 200, 30));

        lbl_ThuongHieu.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        lbl_ThuongHieu.setText("Ga đến");
        lbl_ThuongHieu.setPreferredSize(new java.awt.Dimension(85, 15));
        panel_ThongTin.add(lbl_ThuongHieu, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 70, -1, 25));

        txt_SoToa.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        txt_SoToa.setText("0");
        txt_SoToa.setEditable(false);
        panel_ThongTin.add(txt_SoToa, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 170, 200, 30));

        lbl_DanhMuc.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        lbl_DanhMuc.setText("Số lượng toa");
        lbl_DanhMuc.setPreferredSize(new java.awt.Dimension(85, 15));
        panel_ThongTin.add(lbl_DanhMuc, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 170, 90, 25));

        lbl_KichThuoc.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        lbl_KichThuoc.setText("Loại Tàu");
        lbl_KichThuoc.setPreferredSize(new java.awt.Dimension(85, 15));
        panel_ThongTin.add(lbl_KichThuoc, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 120, 90, 25));

        cbo_LoaiTau.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        cbo_LoaiTau.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Chở người", "Chở hàng" }));
        cbo_LoaiTau.setSelectedItem("Chở khách");
        panel_ThongTin.add(cbo_LoaiTau, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 120, 200, 30));

        lbl_ChatLieu.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        lbl_ChatLieu.setText("Ga đi");
        lbl_ChatLieu.setPreferredSize(new java.awt.Dimension(85, 15));
        panel_ThongTin.add(lbl_ChatLieu, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 20, 68, 25));

        lbl_TinhTrang.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        lbl_TinhTrang.setText("Trạng thái");
        lbl_TinhTrang.setPreferredSize(new java.awt.Dimension(85, 15));
        panel_ThongTin.add(lbl_TinhTrang, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 120, 117, 25));

        cbo_TinhTrang.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        cbo_TinhTrang.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Tạm ngưng", "Đang sử dụng" }));
        cbo_TinhTrang.setSelectedItem("Đang sử dụng");
        cbo_TinhTrang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbo_TinhTrangActionPerformed(evt);
            }
        });
        panel_ThongTin.add(cbo_TinhTrang, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 120, 200, 30));

        lbl_DonGia.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        lbl_DonGia.setText("Đơn giá vé");
        lbl_DonGia.setMaximumSize(new java.awt.Dimension(82, 15));
        lbl_DonGia.setMinimumSize(new java.awt.Dimension(82, 15));
        lbl_DonGia.setPreferredSize(new java.awt.Dimension(85, 15));
        panel_ThongTin.add(lbl_DonGia, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 170, 90, 25));

        txt_DonGia.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        panel_ThongTin.add(txt_DonGia, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 170, 200, 30));

        cbo_GaDi.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        panel_ThongTin.add(cbo_GaDi, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 20, 200, 30));

        cbo_GaDen.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        panel_ThongTin.add(cbo_GaDen, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 70, 200, 30));

        btn_QuanLyGa.setBackground(new java.awt.Color(0, 51, 51));
        btn_QuanLyGa.setFont(new java.awt.Font("Times New Roman", 1, 15)); // NOI18N
        btn_QuanLyGa.setForeground(java.awt.Color.white);
        btn_QuanLyGa.setText("Quản lý Ga");
        btn_QuanLyGa.setHorizontalAlignment(javax.swing.SwingConstants.LEADING);
        btn_QuanLyGa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_QuanLyGaActionPerformed(evt);
            }
        });
        panel_ThongTin.add(btn_QuanLyGa, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 20, 180, 30));

        btn_QuanLyGhe.setBackground(new java.awt.Color(0, 51, 51));
        btn_QuanLyGhe.setFont(new java.awt.Font("Times New Roman", 1, 15)); // NOI18N
        btn_QuanLyGhe.setForeground(java.awt.Color.white);
        btn_QuanLyGhe.setText("Quản lý Ghế");
        btn_QuanLyGhe.setHorizontalAlignment(javax.swing.SwingConstants.LEADING);
        btn_QuanLyGhe.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_QuanLyGheActionPerformed(evt);
            }
        });
        panel_ThongTin.add(btn_QuanLyGhe, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 120, 180, 30));

        btn_QuanLyToa.setBackground(new java.awt.Color(0, 51, 51));
        btn_QuanLyToa.setFont(new java.awt.Font("Times New Roman", 1, 15)); // NOI18N
        btn_QuanLyToa.setForeground(java.awt.Color.white);
        btn_QuanLyToa.setText("Quản lý Toa");
        btn_QuanLyToa.setHorizontalAlignment(javax.swing.SwingConstants.LEADING);
        btn_QuanLyToa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_QuanLyToaActionPerformed(evt);
            }
        });
        panel_ThongTin.add(btn_QuanLyToa, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 70, 180, 30));

        panel_ThaoTac.setBackground(new java.awt.Color(187, 205, 197));
        panel_ThaoTac.setBorder(javax.swing.BorderFactory.createTitledBorder(
                javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Các thao tác",
                javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION,
                new java.awt.Font("Times New Roman", 0, 12))); // NOI18N
        panel_ThaoTac.setPreferredSize(new java.awt.Dimension(932, 60));

        lbl_MaSanPham_Search.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        lbl_MaSanPham_Search.setText("Điều kiện tìm");

        btn_TimKiem.setBackground(new java.awt.Color(0, 51, 51));
        btn_TimKiem.setFont(new java.awt.Font("Times New Roman", 1, 15)); // NOI18N
        btn_TimKiem.setForeground(java.awt.Color.white);
        btn_TimKiem.setText("Tìm kiếm");
        btn_TimKiem.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_TimKiem.setHorizontalAlignment(javax.swing.SwingConstants.LEADING);
        btn_TimKiem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_TimKiemActionPerformed(evt);
            }
        });

        btn_LamMoi.setBackground(new java.awt.Color(0, 51, 51));
        btn_LamMoi.setFont(new java.awt.Font("Times New Roman", 1, 15)); // NOI18N
        btn_LamMoi.setForeground(java.awt.Color.white);
        btn_LamMoi.setText("Làm mới");
        btn_LamMoi.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_LamMoi.setHorizontalAlignment(javax.swing.SwingConstants.LEADING);
        btn_LamMoi.setPreferredSize(new java.awt.Dimension(90, 31));
        btn_LamMoi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_LamMoiActionPerformed(evt);
            }
        });

        btn_Them.setBackground(new java.awt.Color(0, 51, 51));
        btn_Them.setFont(new java.awt.Font("Times New Roman", 1, 15)); // NOI18N
        btn_Them.setForeground(java.awt.Color.white);
        btn_Them.setText("Thêm");
        btn_Them.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_Them.setHorizontalAlignment(javax.swing.SwingConstants.LEADING);
        btn_Them.setPreferredSize(new java.awt.Dimension(90, 31));
        btn_Them.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_ThemActionPerformed(evt);
            }
        });

        btn_CapNhat.setBackground(new java.awt.Color(0, 51, 51));
        btn_CapNhat.setFont(new java.awt.Font("Times New Roman", 1, 15)); // NOI18N
        btn_CapNhat.setForeground(java.awt.Color.white);
        btn_CapNhat.setText("Cập nhật");
        btn_CapNhat.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_CapNhat.setHorizontalAlignment(javax.swing.SwingConstants.LEADING);
        btn_CapNhat.setPreferredSize(new java.awt.Dimension(90, 31));
        btn_CapNhat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_CapNhatActionPerformed(evt);
            }
        });

        txt_MaTau_Search.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N

        btn_XuatExcel.setBackground(new java.awt.Color(0, 51, 51));
        btn_XuatExcel.setFont(new java.awt.Font("Times New Roman", 1, 15)); // NOI18N
        btn_XuatExcel.setForeground(java.awt.Color.white);
        btn_XuatExcel.setText("Xuất");
        btn_XuatExcel.setHorizontalAlignment(javax.swing.SwingConstants.LEADING);
        btn_XuatExcel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_XuatExcelActionPerformed(evt);
            }
        });

        btn_NhapExcel.setBackground(new java.awt.Color(0, 51, 51));
        btn_NhapExcel.setFont(new java.awt.Font("Times New Roman", 1, 15)); // NOI18N
        btn_NhapExcel.setForeground(java.awt.Color.white);
        btn_NhapExcel.setText("Nhập");
        btn_NhapExcel.setHorizontalAlignment(javax.swing.SwingConstants.LEADING);
        btn_NhapExcel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_NhapExcelActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panel_ThaoTacLayout = new javax.swing.GroupLayout(panel_ThaoTac);
        panel_ThaoTac.setLayout(panel_ThaoTacLayout);
        panel_ThaoTacLayout.setHorizontalGroup(
                panel_ThaoTacLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(panel_ThaoTacLayout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addComponent(lbl_MaSanPham_Search)
                                .addGap(18, 18, 18)
                                .addComponent(txt_MaTau_Search, javax.swing.GroupLayout.PREFERRED_SIZE, 140,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(29, 29, 29)
                                .addComponent(btn_TimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 120,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btn_LamMoi, javax.swing.GroupLayout.PREFERRED_SIZE, 120,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btn_Them, javax.swing.GroupLayout.PREFERRED_SIZE, 96,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btn_CapNhat, javax.swing.GroupLayout.PREFERRED_SIZE, 120,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btn_NhapExcel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btn_XuatExcel)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
        panel_ThaoTacLayout.setVerticalGroup(
                panel_ThaoTacLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(panel_ThaoTacLayout.createSequentialGroup()
                                .addGap(3, 3, 3)
                                .addGroup(panel_ThaoTacLayout
                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(panel_ThaoTacLayout
                                                .createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                .addComponent(btn_NhapExcel, javax.swing.GroupLayout.PREFERRED_SIZE, 30,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(btn_XuatExcel, javax.swing.GroupLayout.PREFERRED_SIZE, 30,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(btn_CapNhat, javax.swing.GroupLayout.PREFERRED_SIZE, 30,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(btn_Them, javax.swing.GroupLayout.PREFERRED_SIZE, 30,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(btn_LamMoi, javax.swing.GroupLayout.PREFERRED_SIZE, 30,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(btn_TimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 30,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(panel_ThaoTacLayout
                                                .createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                .addComponent(lbl_MaSanPham_Search)
                                                .addComponent(txt_MaTau_Search, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                        30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(8, 8, 8)));

        panel_DanhSachSanPham.setBackground(new java.awt.Color(187, 205, 197));
        panel_DanhSachSanPham.setBorder(javax.swing.BorderFactory.createTitledBorder(
                javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Bảng danh sách tàu",
                javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION,
                new java.awt.Font("Times New Roman", 0, 12))); // NOI18N
        panel_DanhSachSanPham.setPreferredSize(new java.awt.Dimension(1008, 317));

        JTableHeader tableHeader = table_DanhSachTau.getTableHeader();
        tableHeader.setFont(new Font("Times New Roman", Font.BOLD, 13));
        table_DanhSachTau.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        table_DanhSachTau.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][] {
                        { null, null, null, null, null, null, null, null, null, null, null },
                        { null, null, null, null, null, null, null, null, null, null, null }
                },
                new String[] {
                        "Mã", "Tên", "Loại tàu", "Ga đi", "Ga đến", "Trạng thái", "Số toa", "Đơn giá", "Nhân viên",
                        "Ngày tạo", "Ngày cập nhật"
                }) {
            boolean[] canEdit = new boolean[] {
                    false, false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        });
        table_DanhSachTau.setColumnSelectionAllowed(true);
        // table_DanhSachTau.getColumnModel().getColumn(0).setPreferredWidth(100);
        // table_DanhSachTau.getColumnModel().getColumn(1).setPreferredWidth(200);
        // table_DanhSachTau.getColumnModel().getColumn(2).setCellRenderer(new
        // CenterRenderer());
        // table_DanhSachTau.getColumnModel().getColumn(3).setCellRenderer(new
        // CenterRenderer());
        // table_DanhSachTau.getColumnModel().getColumn(11).setMinWidth(0);
        // table_DanhSachTau.getColumnModel().getColumn(11).setMaxWidth(0);
        // table_DanhSachTau.getColumnModel().getColumn(11).setWidth(0);
        // table_DanhSachTau.getColumnModel().getColumn(11).setPreferredWidth(0);
        table_DanhSachTau.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                table_DanhSachTauMouseClicked(evt);
            }
        });
        scroll_TableSanPham.setViewportView(table_DanhSachTau);

        javax.swing.GroupLayout panel_DanhSachSanPhamLayout = new javax.swing.GroupLayout(panel_DanhSachSanPham);
        panel_DanhSachSanPham.setLayout(panel_DanhSachSanPhamLayout);
        panel_DanhSachSanPhamLayout.setHorizontalGroup(
                panel_DanhSachSanPhamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING,
                                panel_DanhSachSanPhamLayout.createSequentialGroup()
                                        .addContainerGap(15, Short.MAX_VALUE)
                                        .addComponent(scroll_TableSanPham, javax.swing.GroupLayout.PREFERRED_SIZE, 1137,
                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(14, 14, 14)));
        panel_DanhSachSanPhamLayout.setVerticalGroup(
                panel_DanhSachSanPhamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING,
                                panel_DanhSachSanPhamLayout.createSequentialGroup()
                                        .addContainerGap()
                                        .addComponent(scroll_TableSanPham, javax.swing.GroupLayout.DEFAULT_SIZE, 380,
                                                Short.MAX_VALUE)));

        lbl_TieuDe.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        lbl_TieuDe.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl_TieuDe.setText("QUẢN LÝ TÀU");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(lbl_TieuDe, javax.swing.GroupLayout.DEFAULT_SIZE,
                                javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(5, 5, 5)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(panel_ThaoTac, javax.swing.GroupLayout.DEFAULT_SIZE, 1176,
                                                Short.MAX_VALUE)
                                        .addComponent(panel_DanhSachSanPham, javax.swing.GroupLayout.DEFAULT_SIZE, 1176,
                                                Short.MAX_VALUE)
                                        .addComponent(panel_ThongTin, javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(5, 5, 5)));
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(lbl_TieuDe, javax.swing.GroupLayout.PREFERRED_SIZE, 36,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(panel_ThongTin, javax.swing.GroupLayout.PREFERRED_SIZE, 211,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED,
                                        javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(panel_ThaoTac, javax.swing.GroupLayout.PREFERRED_SIZE, 63,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(panel_DanhSachSanPham, javax.swing.GroupLayout.PREFERRED_SIZE, 408,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap()));
    }// </editor-fold>//GEN-END:initComponents

    // Hàm load dữ liệu từ db lên table
    private void loadDuLieuTuDataLenTable() {

        ArrayList<TauEntity> dsTau = tau_dao.getAllTau();
        // DecimalFormat decimalFormat = new DecimalFormat();
        System.out.println("loadDuLieuTuDataLenTable" + dsTau.toString());

        model.setRowCount(0);
        for (TauEntity tau : dsTau) {

            String loaiTau = TauEnum.LoaiTau.convertLoaiTauToString(tau.getLoai());
            String trangThai = TauEnum.TinhTrangTau.convertTinhTrangTauToString(tau.getTrangThai());

            model.addRow(new Object[] {
                    tau.getMaTau(),
                    tau.getTenTau(),
                    loaiTau,
                    tau.getGaDi().getTenGa(),
                    tau.getGaDen().getTenGa(),
                    trangThai,
                    tau.getSoToa(),
                    0,
                    tau.getNhanVien().getTen(),
                    tau.getNgayTao(),
                    tau.getNgayCapNhat(),
            });
        }

        table_DanhSachTau.setModel(model);
        return;
    }

    private ImageIcon ResizeImageIcon(String duongDanAnh) throws IOException {
        URL url = getClass().getResource(duongDanAnh);
        if (url == null) {
            throw new IOException("Resource not found: " + duongDanAnh);
        }
        // Image img = ImageIO.read(url);
        // Image scaledImg = img.getScaledInstance(lbl_AnhSanPham.getWidth(),
        // lbl_AnhSanPham.getHeight(), Image.SCALE_SMOOTH);
        // return new ImageIcon(scaledImg);
        return null;
    }

    private void btn_ThemActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btn_ThemActionPerformed
        themTau();
    }// GEN-LAST:event_btn_ThemActionPerformed

    // Hàm thêm tàu
    private void themTau() {
        try {
            if (validateTau()) {
                String maTau = GenerateID.sinhMa("TAU");
                String ten = txt_TenTau.getText();
                int loai = cbo_LoaiTau.getSelectedIndex();
                System.out.println("loai" + loai);
                int soToa = Integer.parseInt(txt_SoToa.getText());

                if (!(soToa > 0)) {
                    JOptionPane.showMessageDialog(null, "Vui lòng chọn ảnh");
                }

                // Lấy giá trị ga đi và ga đến
                String tenGaDi = (String) cbo_GaDi.getSelectedItem();
                String tenGaDen = (String) cbo_GaDen.getSelectedItem();

                // Kiểm tra nếu ga đi hoặc ga đến không được chọn
                if (tenGaDi == null || tenGaDen == null) {
                    JOptionPane.showMessageDialog(null, "Vui lòng chọn cả ga đi và ga đến");
                    return; // Kết thúc phương thức nếu không hợp lệ
                }

                if (tenGaDen == tenGaDi) {
                    JOptionPane.showMessageDialog(null, "Ga đi và ga đến không được trùng nhau");
                    return; // Kết thúc phương thức nếu không hợp lệ
                }

                // check maGa
                String maGaDi = ga_dao.layMaGaTheoTen(tenGaDi);
                String maGaDen = ga_dao.layMaGaTheoTen(tenGaDen);

                GaTauEntity gaDi = new GaTauEntity(maGaDi);
                GaTauEntity gaDen = new GaTauEntity(maGaDen);

                int trangThai = cbo_TinhTrang.getSelectedIndex();
                NhanVienEntity nhanVien = new NhanVienEntity(ToanCuc.getMa());
                double donGia = 0;// Double.parseDouble(txt_DonGia.getText());

                TauEntity tau = new TauEntity(maTau, ten, gaDi, gaDen, soToa, loai, trangThai, nhanVien);
                boolean kiemTra = tau_dao.themTau(tau);
                System.out.println(tau.toString());
                if (kiemTra) {
                    // model.addRow(generateRowData(tau));
                    JOptionPane.showMessageDialog(null, "Thêm thành công");
                    lamMoi();
                } else {
                    JOptionPane.showMessageDialog(null, "Thêm không thành công");
                }

            }

        } catch (Exception e) {
            System.out.println(e);
            e.printStackTrace();
        }
    }

    private Object[] generateRowData(TauEntity tau) {
        String loaiTau = TauEnum.LoaiTau.convertLoaiTauToString(tau.getLoai());
        String trangThaiString = TauEnum.TinhTrangTau.convertTinhTrangTauToString(tau.getTrangThai());
        return new Object[] {
                tau.getMaTau(),
                tau.getTenTau(),
                loaiTau,
                tau.getGaDi().getTenGa(),
                tau.getGaDen().getTenGa(),
                trangThaiString,
                tau.getSoToa(),
                0,
                tau.getNhanVien().getTen(),
                tau.getNgayTao(),
                tau.getNgayCapNhat()
        };
    }

    private void table_DanhSachTauMouseClicked(java.awt.event.MouseEvent evt) {
        int row = table_DanhSachTau.getSelectedRow();

        txt_MaTau.setText(model.getValueAt(row, 0).toString());
        txt_TenTau.setText(model.getValueAt(row, 1).toString());
        cbo_LoaiTau.setSelectedItem(model.getValueAt(row, 2).toString());
        cbo_GaDi.setSelectedItem(model.getValueAt(row, 3).toString());
        cbo_GaDen.setSelectedItem(model.getValueAt(row, 4).toString());
        cbo_TinhTrang.setSelectedItem(model.getValueAt(row, 5).toString());
        txt_SoToa.setText(model.getValueAt(row, 6).toString());
        txt_DonGia.setText(model.getValueAt(row, 7).toString().replace(" VNĐ", "").replace(",", ""));

        btn_Them.setEnabled(false);
        txt_MaTau.setEnabled(false);
        txt_SoToa.setEnabled(true);

        maTauHienTai = model.getValueAt(row, 0).toString();
        tauHienTai = new TauEntity(model.getValueAt(row, 0).toString(), model.getValueAt(row, 1).toString(),
                Integer.parseInt(model.getValueAt(row, 6).toString()));
        btn_QuanLyToa.setEnabled(true);
        btn_QuanLyGhe.setEnabled(true);
    }

    private void btn_TimKiemActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btn_TimKiemActionPerformed
        String dieuKien = txt_MaTau_Search.getText().trim();
        timKiemTau(dieuKien);

    }// GEN-LAST:event_btn_TimKiemActionPerformed

    // Hàm tìm kiếm tàu
    private void timKiemTau(String dieuKien) {
        String timKiem = txt_MaTau_Search.getText().trim();
        if (timKiem.isBlank()) {
            JOptionPane.showMessageDialog(null, "Vui lòng nhập điều kiện tìm kiếm");
        } else {
            model.setRowCount(0);
            ArrayList<TauEntity> dsTau = tau_dao.getAllTau();
            // DecimalFormat decimalFormat = new DecimalFormat();
            boolean kt = false;

            for (TauEntity tau : dsTau) {
                if (matchesSearchTerm(tau, dieuKien)) {
                    model.addRow(generateRowData(tau));
                    kt = true;
                }
            }

            if (!kt) {
                JOptionPane.showMessageDialog(null, "Không tìm thấy tàu");
                lamMoi();
            }
        }
    }

    private void btn_LamMoiActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btn_LamMoiActionPerformed
        lamMoi();
    }// GEN-LAST:event_btn_LamMoiActionPerformed

    // Hàm làm mới tau
    public void lamMoi() {

        ArrayList<GaTauEntity> dsGaTau = ga_dao.getAllGaTau();
        duaDuLieuVaoComboBox(cbo_GaDi, dsGaTau, "TenGa");
        duaDuLieuVaoComboBox(cbo_GaDen, dsGaTau, "TenGa");

        txt_MaTau.setText("");
        txt_TenTau.setText("");
        cbo_GaDi.setSelectedIndex(0);
        txt_DonGia.setText("");
        txt_SoToa.setText("0");
        cbo_GaDen.setSelectedIndex(0);
        cbo_LoaiTau.setSelectedIndex(1);

        cbo_TinhTrang.setSelectedItem("Đang sử dụng");
        txt_MaTau_Search.setText("");

        model.setRowCount(0);

        btn_Them.setEnabled(true);
        txt_SoToa.setEditable(true);
        // ArrayList<TauEntity> dsTau = tau_dao.getAllTau();
        // for (TauEntity tau : dsTau) {
        // model.addRow(generateRowData(tau));
        // }
        loadDuLieuTuDataLenTable();
    }

    private void btn_CapNhatActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btn_CapNhatActionPerformed
        capNhatSanPham();
    }// GEN-LAST:event_btn_CapNhatActionPerformed

    // Hàm cập nhật tàu
    private void capNhatSanPham() {
        int row = table_DanhSachTau.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(null, "Chưa chọn tàu để cập nhật");
        } else {
            if (table_DanhSachTau.getSelectedRowCount() == 1) {
                if (validateTau()) {
                    if (JOptionPane.showConfirmDialog(null,
                            "Bạn có chắc chắc cập nhật tàu có mã " + table_DanhSachTau.getValueAt(row, 0)
                                    + " này không?",
                            "Cảnh báo cập nhật", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                        String maTau = txt_MaTau.getText();
                        String ten = txt_TenTau.getText();

                        int loai = cbo_LoaiTau.getSelectedIndex();

                        double donGia = 0;

                        int tinhTrang = 0;
                        if (cbo_TinhTrang.getSelectedItem().equals("Đang sử dụng")) {
                            tinhTrang = 1;
                        } else if (cbo_TinhTrang.getSelectedItem().equals("Tạm ngưng")) {
                            tinhTrang = 0;
                        }

                        int soToa = Integer.parseInt(txt_SoToa.getText());

                        // Lấy giá trị ga đi và ga đến
                        String tenGaDi = (String) cbo_GaDi.getSelectedItem();
                        String tenGaDen = (String) cbo_GaDen.getSelectedItem();

                        // Kiểm tra nếu ga đi hoặc ga đến không được chọn
                        if (tenGaDi == null || tenGaDen == null) {
                            JOptionPane.showMessageDialog(null, "Vui lòng chọn cả ga đi và ga đến");
                            return; // Kết thúc phương thức nếu không hợp lệ
                        }

                        // check maGa
                        String maGaDi = ga_dao.layMaGaTheoTen(tenGaDi);
                        String maGaDen = ga_dao.layMaGaTheoTen(tenGaDen);

                        GaTauEntity gaDi = new GaTauEntity(maGaDi);
                        GaTauEntity gaDen = new GaTauEntity(maGaDen);

                        int trangThai = cbo_TinhTrang.getSelectedIndex();
                        NhanVienEntity nhanVien = new NhanVienEntity(ToanCuc.getMa());
                        // double donGia = Double.parseDouble(txt_DonGia.getText());

                        TauEntity tau = new TauEntity(maTau, ten, gaDi, gaDen, soToa, loai, trangThai, nhanVien);

                        boolean kq = tau_dao.capNhatTau(tau);
                        if (kq) {
                            JOptionPane.showMessageDialog(null, "Cập nhật thành công");
                            lamMoi();
                        } else {
                            JOptionPane.showMessageDialog(null, "Cập nhật không thành công");
                        }
                    }
                }
            }
        }
    }

    private void btn_XuatExcelActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btn_XuatExcelActionPerformed
        xuatExcel();
        // xuatExcelTable();
    }// GEN-LAST:event_btn_XuatExcelActionPerformed

    // Hàm xuất excel từ db
    private void xuatExcel() {
        try {
            JFileChooser fileChooser = new JFileChooser("C:\\download");
            fileChooser.setDialogTitle("Chọn nơi lưu file");
            int chon = fileChooser.showSaveDialog(null);
            if (chon == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                String filePath = selectedFile.getAbsolutePath();
                if (!filePath.toLowerCase().endsWith(".xlsx")) {
                    filePath += ".xlsx";
                }
                XSSFWorkbook workbook = new XSSFWorkbook();
                XSSFSheet sheet = workbook.createSheet("Danh sách tàu");
                XSSFRow row = null;
                Cell cell = null;
                row = sheet.createRow(0);
                cell = row.createCell(0, CellType.STRING);
                cell.setCellValue("Mã tàu");
                cell = row.createCell(1, CellType.STRING);
                cell.setCellValue("Tên tàu");
                cell = row.createCell(2, CellType.STRING);
                cell.setCellValue("Loại tàu");
                cell = row.createCell(3, CellType.STRING);
                cell.setCellValue("Ga đi");
                cell = row.createCell(4, CellType.STRING);
                cell.setCellValue("Ga đến");
                cell = row.createCell(5, CellType.STRING);
                cell.setCellValue("Trạng thái");
                cell = row.createCell(6, CellType.STRING);
                cell.setCellValue("Số toa");
                cell = row.createCell(7, CellType.STRING);
                cell.setCellValue("Đơn giá");
                cell = row.createCell(8, CellType.STRING);
                cell.setCellValue("Nhân viên");
                cell = row.createCell(9, CellType.STRING);
                cell.setCellValue("Ngày tạo");
                cell = row.createCell(10, CellType.STRING);
                cell.setCellValue("Ngày cập nhật");
                cell = row.createCell(11, CellType.STRING);
                cell.setCellValue("Khuyến mãi");
                                cell = row.createCell(11, CellType.STRING);
                cell.setCellValue("Mã ga đi");
                                cell = row.createCell(11, CellType.STRING);
                cell.setCellValue("Mã ga đến");
                                cell = row.createCell(11, CellType.STRING);
                cell.setCellValue("Mã nhân viên");
                ArrayList<TauEntity> listItem = tau_dao.getAllSanPham();
                for (int i = 0; i < listItem.size(); i++) {
                    TauEntity tau = listItem.get(i);
                    
                    row = sheet.createRow(1 + i);

                    row.createCell(0).setCellValue(tau.getMatau());
                    row.createCell(1).setCellValue(tau.getTentau());
                    row.createCell(2).setCellValue(TauEnum.LoaiTau.convertLoaiTauToString(tau.getLoai()));
                    row.createCell(3).setCellValue(tau.getGaDi().getTenGa());
                    row.createCell(4).setCellValue(tau.getGaDen().getTenGa());
                    row.createCell(5).setCellValue(String trangThai = TauEnum.TinhTrangTau.convertTinhTrangTauToString(tau.getTrangThai()));
                    row.createCell(6).setCellValue(tau.getSoToa());
                    row.createCell(7).setCellValue(0);
                    row.createCell(8).setCellValue(tau.getNhanVien().getTen());
                    row.createCell(9).setCellValue(tau.getNgayTao());
                    row.createCell(10).setCellValue(tau.getNgayTao());
                    row.createCell(11).setCellValue("CTKM"); // TODO: 
                    row.createCell(11).setCellValue(tau.getGaDi().getMaGa()); 
                    row.createCell(11).setCellValue(tau.getGaDen().getMaGa()); 
                    row.createCell(11).setCellValue(tau.getNhanVien().getMaNV); 
                }
                File f = new File(filePath);
                try (FileOutputStream fos = new FileOutputStream(f)) {
                workbook.write(fos);
                JOptionPane.showMessageDialog(null, "Xuất file thành công");
                openExcelFile(f);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    // Hàm xuất excel từ table
    private void xuatExcelTable() {
        try {
            JFileChooser fileChooser = new JFileChooser("C:\\Users\\MY PC\\OneDrive\\Máy tính");
            fileChooser.setDialogTitle("Chọn nơi lưu file");
            int chon = fileChooser.showSaveDialog(null);
            if (chon == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                String filePath = selectedFile.getAbsolutePath();
                if (!filePath.toLowerCase().endsWith(".xlsx")) {
                    filePath += ".xlsx";
                }
                Workbook wb = new XSSFWorkbook();
                Sheet sheet = wb.createSheet("Danh sách tàu");
                Row rowCol = sheet.createRow(0);
                for (int i = 0; i < table_DanhSachTau.getColumnCount(); i++) {
                    Cell cell = rowCol.createCell(i);
                    cell.setCellValue(table_DanhSachTau.getColumnName(i));
                }
                for (int j = 0; j < table_DanhSachTau.getRowCount(); j++) {
                    Row row = sheet.createRow(j + 1);
                    for (int k = 0; k < table_DanhSachTau.getColumnCount(); k++) {
                        Cell cell = row.createCell(k);
                        if (table_DanhSachTau.getValueAt(j, k) != null) {
                            cell.setCellValue(table_DanhSachTau.getValueAt(j, k).toString());
                        }
                    }
                }
                File f = new File(filePath);
                try (FileOutputStream fos = new FileOutputStream(f)) {
                    wb.write(fos);
                    JOptionPane.showMessageDialog(null, "Xuất file thành công");
                    // System.out.println(f);
                    openExcelFile(f);

                }
                wb.close();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    // Hàm tự dộng mở file excel sau khi xuất
    private static void openExcelFile(File file) throws IOException {
        Desktop desktop = Desktop.getDesktop();
        if (desktop.isSupported(Desktop.Action.OPEN)) {
            desktop.open(file);
        } else {
            System.out.println("Không thể mở file.");
        }
    }

    private void btn_NhapExcelActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btn_NhapExcelActionPerformed
        // TODO add your handling code here:
        nhapExcel();
    }// GEN-LAST:event_btn_NhapExcelActionPerformed

    // Hàm nhập file excel
    private void nhapExcel() {
        File excelFile;
        FileInputStream excelFIS = null;
        BufferedInputStream excelBIS = null;
        XSSFWorkbook excelImportToJTable = null;
        String defaultCurrentDirectoryPath = "C:\\download";
        JFileChooser excelFileChooser = new JFileChooser(defaultCurrentDirectoryPath);
        excelFileChooser.setDialogTitle("Chọn file excel");
        FileNameExtensionFilter fnef = new FileNameExtensionFilter("EXCEL FILES", "xls", "xlsx", "xlsm");
        excelFileChooser.setFileFilter(fnef);
        int excelChooser = excelFileChooser.showOpenDialog(null);
        if (excelChooser == JFileChooser.APPROVE_OPTION) {
            Set<Object> maSanPhamSet = new HashSet<>();
            try {
                // excelFile = excelFileChooser.getSelectedFile();
                // excelFIS = new FileInputStream(excelFile);
                // excelBIS = new BufferedInputStream(excelFIS);
                // excelImportToJTable = new XSSFWorkbook(excelBIS);
                // XSSFSheet excelSheet = excelImportToJTable.getSheetAt(0);
                // for (int row = 1; row <= excelSheet.getLastRowNum(); row++) {
                // XSSFRow excelRow = excelSheet.getRow(row);
                // XSSFCell excelMaSP = excelRow.getCell(0);
                // // Kiểm tra xem mã tàu đã tồn tại trong tập hợp chưa
                // String maSanPham = excelMaSP.getStringCellValue().trim();
                // int existingRow = -1;
                // // Kiểm tra xem mã tàu nhập đã tồn tại trong bảng chưa
                // for (int i = 0; i < model.getRowCount(); i++) {
                // if (maSanPham.equals(model.getValueAt(i, 0).toString())) {
                // existingRow = i;
                // break;
                // }
                // }
                // XSSFCell excelTenSP = excelRow.getCell(1);
                // XSSFCell excelKichThuoc = excelRow.getCell(2);
                // XSSFCell excelMauSac = excelRow.getCell(3);
                // XSSFCell excelDonGia = excelRow.getCell(4);
                // XSSFCell excelTinhTrang = excelRow.getCell(5);
                // XSSFCell excelSoLuongTon = excelRow.getCell(6);
                // XSSFCell excelChatLieu = excelRow.getCell(7);
                // XSSFCell excelThuongHieu = excelRow.getCell(8);
                // XSSFCell excelDanhMuc = excelRow.getCell(9);
                // XSSFCell excelAnh = excelRow.getCell(10);
                // String duongDanAnh = excelAnh.getStringCellValue();
                // XSSFCell excelKhuyenMai = excelRow.getCell(11);
                // String khuyenMai = "";
                // if (excelKhuyenMai != null) {
                // khuyenMai = excelKhuyenMai.getStringCellValue().trim();
                // } else {
                // khuyenMai = "Không giảm giá";
                // }
                // int soLuongTonKho = (int) excelSoLuongTon.getNumericCellValue();
                // double donGia = excelDonGia.getNumericCellValue();
                // DecimalFormat decimalFormat = new DecimalFormat();
                // String formattedDonGia = decimalFormat.format(donGia) + " VNĐ";
                // String tinhTrang = excelTinhTrang.getStringCellValue().trim();
                // if (soLuongTonKho == 0 && "Đang bán".equals(tinhTrang)) {
                // tinhTrang = "Hết hàng";
                // }
                // if (existingRow != -1) {
                // model.setValueAt(excelTenSP.getStringCellValue(), existingRow, 1);
                // model.setValueAt(excelKichThuoc.getStringCellValue(), existingRow, 2);
                // model.setValueAt(excelMauSac, existingRow, 3);
                // model.setValueAt(formattedDonGia, existingRow, 4);
                // model.setValueAt(tinhTrang, existingRow, 5);
                // model.setValueAt(soLuongTonKho, existingRow, 6);
                // model.setValueAt(excelChatLieu, existingRow, 7);
                // model.setValueAt(excelThuongHieu, existingRow, 8);
                // model.setValueAt(excelDanhMuc, existingRow, 9);
                // model.setValueAt(khuyenMai, existingRow, 10);
                // model.setValueAt(duongDanAnh, existingRow, 11);
                // } else {
                // maSanPhamSet.add(maSanPham);
                // model.addRow(new Object[]{maSanPham, excelTenSP, excelKichThuoc, excelMauSac,
                // formattedDonGia, tinhTrang, soLuongTonKho, excelChatLieu, excelThuongHieu,
                // excelDanhMuc, khuyenMai, duongDanAnh});
                // }
                // }
                JOptionPane.showMessageDialog(null, "Nhập thành công");
            } catch (Exception iOException) {
                JOptionPane.showMessageDialog(null, iOException.getMessage());
            } finally {
                try {
                    if (excelFIS != null) {
                        excelFIS.close();
                    }
                    if (excelBIS != null) {
                        excelBIS.close();
                    }
                    if (excelImportToJTable != null) {
                        excelImportToJTable.close();
                    }
                } catch (IOException iOException) {
                    JOptionPane.showMessageDialog(null, iOException.getMessage());
                }
            }
        }
    }

    private void cbo_TinhTrangActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_cbo_TinhTrangActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_cbo_TinhTrangActionPerformed

    private void btn_QuanLyGheActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btn_QuanLyGheActionPerformed
        if (maTauHienTai.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Vui lòng chọn tàu trước khi quản lý ghế");
            return;
        } else {
            new QuanLyGhe_JPanel1(tauHienTai).setVisible(getFocusTraversalKeysEnabled());

        }
    }// GEN-LAST:event_btn_QuanLyGheActionPerformed

    private void btn_QuanLyToaActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btn_QuanLyToaActionPerformed

        if (maTauHienTai.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Vui lòng chọn tàu trước khi quản lý toa");
            return;
        } else {
            new QuanLyToa_JPanel(tauHienTai).setVisible(getFocusTraversalKeysEnabled());

        }
    }// GEN-LAST:event_btn_QuanLyToaActionPerformed

    private void btn_QuanLyGaActionPerformed(java.awt.event.ActionEvent evt) {
        new QuanLyGa_JPanel().setVisible(getFocusTraversalKeysEnabled()); // This line instantiates the new frame and
                                                                          // shows it

    }

    // Hàm kiểm tra tàu có chứa tiêu chí tìm kiếm không
    private boolean matchesSearchTerm(TauEntity tau, String search) {
        String lowercaseSearch = search.toLowerCase(); // Chuyển chuỗi tìm kiếm về chữ thường

        if (tau.getMaTau().toLowerCase().contains(lowercaseSearch)
                || tau.getTenTau().toLowerCase().contains(lowercaseSearch)
        // || (tau.getKichThuoc() != null &&
        // tau.getKichThuoc().toString().toLowerCase().contains(lowercaseSearch))
        // || Double.toString(tau.getDonGia()).toLowerCase().contains(lowercaseSearch)
        // || tau.getTinhTrang().toString().toLowerCase().contains(lowercaseSearch)
        // ||
        // Integer.toString(tau.getSoLuongTonKho()).toLowerCase().contains(lowercaseSearch)
        ) {
            return true;
        }

        return false;
    }

    // Hàm đưa dữ liệu vào combobox
    private void duaDuLieuVaoComboBox(JComboBox comboBox, ArrayList<?> data, String thuocTinh) {
        DefaultComboBoxModel<Object> model = new DefaultComboBoxModel<>();
        for (Object item : data) {
            try {
                Object propertyValue = item.getClass().getMethod("get" + thuocTinh).invoke(item);
                model.addElement(propertyValue);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        comboBox.setModel(model);
    }

    // Hàm kiểm tra mã tàu đã có trong table hay chưa
    private boolean kiemTraMaSanPhamTontaiTrongTable(DefaultTableModel model, String maSanPham) {
        for (int row = 0; row < model.getRowCount(); row++) {
            if (model.getValueAt(row, 0).equals(maSanPham)) {
                return true;
            }
        }
        return false;
    }

    // Hàm kiểm tra ảnh null
    // private void kiemTraAnhNull(int row) {
    // String img = sp_bus.getAllSanPham().get(row).getImgUrl();
    // ImageIcon imageIcon;
    // if (img != null) {
    // imageIcon = new ImageIcon(new
    // ImageIcon(img).getImage().getScaledInstance(lbl_AnhSanPham.getWidth(),
    // lbl_AnhSanPham.getHeight(), Image.SCALE_SMOOTH));
    // } else {
    // ImageIcon anhMacDinh = new ImageIcon("src//pic//icon//labelAnh.png");
    // imageIcon = new
    // ImageIcon(anhMacDinh.getImage().getScaledInstance(lbl_AnhSanPham.getWidth(),
    // lbl_AnhSanPham.getHeight(), Image.SCALE_SMOOTH));
    // }
    // lbl_AnhSanPham.setIcon(imageIcon);
    // }
    // Hàm kiểm tra regex
    private boolean validateTau() {
        String ten = txt_TenTau.getText().trim();
        String donGia = txt_DonGia.getText().trim();
        if (ten.isBlank()) {
            JOptionPane.showMessageDialog(null, "Tên tàu không được để trống");
            return false;
        }
        if (!ten.matches("^(\\p{L}{1}\\p{L}*\\s)*(\\p{L}{1}\\p{L}*)$")) {
            JOptionPane.showMessageDialog(null, "Tên tàu không được nhập số");
            txt_TenTau.requestFocus();
            txt_TenTau.selectAll();
            return false;
        }
        // if (!donGia.matches("^[1-9]\\d*")) {
        // JOptionPane.showMessageDialog(null, "Gía vé phải lớn hơn 0, không được nhập
        // chữ");
        // txt_DonGia.requestFocus();
        // txt_DonGia.selectAll();
        // return false;
        // }
        int soToa = Integer.parseInt(txt_SoToa.getText().trim());

        if (!(soToa > 0)) {
            JOptionPane.showMessageDialog(null, "Số toa phải lớn hơn 0");
            txt_SoToa.requestFocus();
            txt_SoToa.selectAll();
            return false;
        }
        return true;

    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_CapNhat;
    private javax.swing.JButton btn_LamMoi;
    private javax.swing.JButton btn_NhapExcel;
    private javax.swing.JButton btn_QuanLyGa;
    private javax.swing.JButton btn_QuanLyGhe;
    private javax.swing.JButton btn_QuanLyToa;
    private javax.swing.JButton btn_Them;
    private javax.swing.JButton btn_TimKiem;
    private javax.swing.JButton btn_XuatExcel;
    private javax.swing.JComboBox<String> cbo_GaDen;
    private javax.swing.JComboBox<String> cbo_GaDi;
    private javax.swing.JComboBox<String> cbo_LoaiTau;
    private javax.swing.JComboBox<String> cbo_TinhTrang;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JLabel lbl_ChatLieu;
    private javax.swing.JLabel lbl_DanhMuc;
    private javax.swing.JLabel lbl_DonGia;
    private javax.swing.JLabel lbl_KichThuoc;
    private javax.swing.JLabel lbl_MaSanPham;
    private javax.swing.JLabel lbl_MaSanPham_Search;
    private javax.swing.JLabel lbl_TenSanPham;
    private javax.swing.JLabel lbl_ThuongHieu;
    private javax.swing.JLabel lbl_TieuDe;
    private javax.swing.JLabel lbl_TinhTrang;
    private javax.swing.JPanel panel_DanhSachSanPham;
    private javax.swing.JPanel panel_ThaoTac;
    private javax.swing.JPanel panel_ThongTin;
    private javax.swing.JScrollPane scroll_TableSanPham;
    private javax.swing.JTable table_DanhSachTau;
    private DefaultTableModel model;
    private javax.swing.JTextField txt_DonGia;
    private javax.swing.JTextField txt_MaTau;
    private javax.swing.JTextField txt_MaTau_Search;
    private javax.swing.JTextField txt_SoToa;
    private javax.swing.JTextField txt_TenTau;
    // End of variables declaration//GEN-END:variables
}

// Căn giữa cột trong table
class CenterRenderer extends DefaultTableCellRenderer {

    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
            int row, int column) {
        Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        ((JLabel) c).setHorizontalAlignment(SwingConstants.CENTER);
        return c;
    }
}
