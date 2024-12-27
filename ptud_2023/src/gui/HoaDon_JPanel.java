package gui;

import dao.ChiTietHoaDon_dao;
import dao.HoaDon_dao;
import entity.ChiTietHoaDonEntity;
import entity.HoaDonEntity;
import entity.KhachHangEntity;
import util.ToanCuc;

import java.awt.Image;
import java.net.URL;
import java.sql.Date;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class HoaDon_JPanel extends javax.swing.JPanel {

    private HoaDon_dao hdDao;
    private DefaultTableModel model;
    private JPanel pnl_ChiTiet;
    private DefaultTableModel modelChiTiet;
    private ChiTietHoaDon_dao cthdDao;
    private DecimalFormat df;
    private SimpleDateFormat sdf;
    private JLabel lbl_MaHD, lbl_MaKH, lbl_NgayTao, lbl_NgayCapNhat, lbl_TrangThai, lbl_PhuongThucTT, lbl_TongTien;

    public HoaDon_JPanel() {
        initComponents();
        dateNgayLap.setLocale(new Locale("vi", "VN"));
        setBounds(0, 0, 1186, 748);

        setUpIcons();
        setupChiTietPanel();

        hdDao = new HoaDon_dao();
        cthdDao = new ChiTietHoaDon_dao();

        df = new DecimalFormat("#,###");
        sdf = new SimpleDateFormat("dd/MM/yyyy");

        model = new DefaultTableModel(
            new String[] { 
                "Mã Hóa Đơn", "Khách Hàng", "Tổng Tiền", 
                "Phương Thức Thanh Toán", "Trạng Thái", 
                "Ngày Lập", "Ngày Cập Nhật" 
            }, 
            0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Không cho phép sửa trực tiếp trên bảng
            }
        };
        
        jTable1.setModel(model);
        
        DocDuLieuTuSQLvaoTable();

        // pnl_ChiTiet.setVisible(false);
    }

    private void setupChiTietPanel() {
        // TODO Auto-generated method stub

    }

    private void setUpIcons() {
        try {
            setButtonIcon(btn_TimKiem, "/pic/icon/buttonTimKiem.png");
            setButtonIcon(btn_XemChiTiet, "/pic/icon/buttonXemChiTiet.png");
            setButtonIcon(btn_LamMoi, "/pic/icon/buttonLamMoi.png");
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

    private void DocDuLieuTuSQLvaoTable() {
        try {
            model.setRowCount(0);
            
            System.out.println("Bắt đầu lấy dữ liệu từ database...");
            ArrayList<HoaDonEntity> listHD = hdDao.getallHoaDon();
            System.out.println("Số lượng hóa đơn: " + (listHD != null ? listHD.size() : 0));
            
            if(listHD != null && !listHD.isEmpty()) {
                for (HoaDonEntity hd : listHD) {
                    try {
                        String maKH = "Khách lẻ";
                        if (hd.getMaKH() != null) {
                            maKH = hd.getMaKH().getMaKH() + " - " + hd.getMaKH().getHoTen();
                        }
                        
                        model.addRow(new Object[] {
                            hd.getMaHD(),
                            maKH,
                            df.format(hd.getTongTien()) + " VNĐ",
                            hd.getPhuongThucThanhToan(),
                            hd.getTrangThai() == 1 ? "Đã thanh toán" : "Chưa thanh toán",
                            sdf.format(hd.getNgayTao()),
                            sdf.format(hd.getNgayCapNhat())
                        });
                        
                        System.out.println("Đã thêm dòng dữ liệu: " + hd.getMaHD());
                    } catch (Exception e) {
                        System.err.println("Lỗi khi hiển thị hóa đơn: " + hd.getMaHD());
                        e.printStackTrace();
                    }
                }
            } else {
                System.out.println("Không có dữ liệu hóa đơn");
            }
            
            System.out.println("Tổng số dòng trong bảng: " + model.getRowCount());
            
        } catch (Exception e) {
            System.err.println("Lỗi trong quá trình hiển thị dữ liệu:");
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, 
                "Có lỗi xảy ra khi hiển thị dữ liệu: " + e.getMessage(),
                "Lỗi",
                JOptionPane.ERROR_MESSAGE);
        }
    }

    public void addRows(Object[] row) {
        model.addRow(row);
    }

    public String getMaHDInHD() {
        int row = jTable1.getSelectedRow();
        if (row < 0) {
            return null;
        }
        return jTable1.getValueAt(row, 0).toString();
    }

    private void XoahetDuLieuTrenTable() {
        model.setRowCount(0);
    }

    private void btn_TimKiemActionPerformed(java.awt.event.ActionEvent evt) {
        XoahetDuLieuTrenTable();

        String maHD = txt_MaHoaDon.getText().trim();
        Date ngayLap = dateNgayLap.getDate() != null ? new Date(dateNgayLap.getDate().getTime()) : null;

        if (!maHD.isEmpty() && ngayLap == null) {
            HoaDonEntity hd = hdDao.timKiemHoaDonTheoMa(maHD);
            if (hd != null) {
                addHoaDonToTable(hd);
            } else {
                JOptionPane.showMessageDialog(null, "Không tìm thấy hoá đơn này!");
            }
        } else if (maHD.isEmpty() && ngayLap != null) {
            ArrayList<HoaDonEntity> ds = hdDao.getHoaDonTheoNgayLap(ngayLap);
            if (!ds.isEmpty()) {
                for (HoaDonEntity hd : ds) {
                    addHoaDonToTable(hd);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Không tìm thấy hoá đơn nào!");
                DocDuLieuTuSQLvaoTable();
            }
        } else if (maHD.isEmpty() && ngayLap == null) {
            JOptionPane.showMessageDialog(null, "Vui lòng nhập dữ liệu Hoá đơn cần tìm!");
            DocDuLieuTuSQLvaoTable();
        } else {
            ArrayList<HoaDonEntity> ds = hdDao.getHoaDonTheoMaHDvaNgayLap(maHD, ngayLap);
            if (!ds.isEmpty()) {
                for (HoaDonEntity hd : ds) {
                    addHoaDonToTable(hd);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Không tìm thấy hoá đơn nào!");
                DocDuLieuTuSQLvaoTable();
            }
        }
    }

    private void addHoaDonToTable(HoaDonEntity hd) {

        model.addRow(new Object[] {
                hd.getMaHD(),
                // hd.getKhachHang() != null ? hd.getKhachHang().getMaKH() : "",
                hd.getTongTien(),
                hd.getPhuongThucThanhToan(),
                hd.getTrangThai(),
                hd.getNgayTao(),
                hd.getNgayCapNhat()
        });

        jTable1.setModel(model);
    }

    private void btn_LamMoiActionPerformed(java.awt.event.ActionEvent evt) {
        txt_MaHoaDon.setText("");
        dateNgayLap.setDate(null);
        XoahetDuLieuTrenTable();
        DocDuLieuTuSQLvaoTable();
    }

    private void btn_XemChiTietActionPerformed(java.awt.event.ActionEvent evt) {
        String maHD = getMaHDInHD();
        if (maHD == null) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn hóa đơn cần xem chi tiết!");
            return;
        }

        HoaDonEntity hd = hdDao.getHoaDonTheoMaHD(maHD);
        if (hd == null) {
            JOptionPane.showMessageDialog(this, "Không tìm thấy thông tin hóa đơn!");
            return;
        }

        // hienThiChiTietHoaDon(hd);
        ToanCuc.setHoaDon(hd);
        new ChiTietHoaDon_GUI().setVisible(true);

    }

    private void hienThiChiTietHoaDon(HoaDonEntity hd) {
        lbl_MaHD.setText(hd.getMaHD());
        lbl_MaKH.setText(hd.getMaKH() != null ? hd.getMaKH().getMaKH() : "Khách lẻ");
        lbl_NgayTao.setText(sdf.format(hd.getNgayTao()));
        lbl_NgayCapNhat.setText(sdf.format(hd.getNgayCapNhat()));
        lbl_TrangThai.setText(hd.getTrangThai() == 1 ? "Đã thanh toán" : "Chưa thanh toán");
        lbl_PhuongThucTT.setText(String.valueOf(hd.getPhuongThucThanhToan()));
        lbl_TongTien.setText(df.format(hd.getTongTien()) + " VNĐ");

        modelChiTiet.setRowCount(0);

        ArrayList<ChiTietHoaDonEntity> dsCTHD = cthdDao.getAllCTHDTheoMaHD(hd.getMaHD());
        for (ChiTietHoaDonEntity cthd : dsCTHD) {
            modelChiTiet.addRow(new Object[] {
                    // cthd.getSanPham().getMaVe(),
                    cthd.getSoLuong(),
                    df.format(cthd.getGiaGoc()),
                    df.format(cthd.getGiaBan()),
                    df.format(cthd.getThanhTien())
            });
        }

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        txt_MaHoaDon = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        dateNgayLap = new com.toedter.calendar.JDateChooser();
        btn_TimKiem = new javax.swing.JButton();
        btn_LamMoi = new javax.swing.JButton();
        btn_XemChiTiet = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();

        setBackground(new java.awt.Color(255, 255, 255));
        setPreferredSize(new java.awt.Dimension(1186, 748));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setPreferredSize(new java.awt.Dimension(1186, 748));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("QUẢN LÝ HÓA ĐƠN");

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Tìm kiếm",
                javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION,
                new java.awt.Font("Segoe UI", 1, 14))); // NOI18N

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel2.setText("Mã hóa đơn:");

        txt_MaHoaDon.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel3.setText("Ngày lập:");

        dateNgayLap.setDateFormatString("dd/MM/yyyy");
        dateNgayLap.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        btn_TimKiem.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btn_TimKiem.setText("Tìm kiếm");
        btn_TimKiem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_TimKiemActionPerformed(evt);
            }
        });

        btn_LamMoi.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btn_LamMoi.setText("Làm mới");
        btn_LamMoi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_LamMoiActionPerformed(evt);
            }
        });

        btn_XemChiTiet.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btn_XemChiTiet.setText("Xem chi tiết");
        btn_XemChiTiet.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_XemChiTietActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(50, 50, 50)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel2)
                                        .addComponent(txt_MaHoaDon, javax.swing.GroupLayout.PREFERRED_SIZE, 200,
                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(50, 50, 50)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel3)
                                        .addComponent(dateNgayLap, javax.swing.GroupLayout.PREFERRED_SIZE, 200,
                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(50, 50, 50)
                                .addComponent(btn_TimKiem)
                                .addGap(20, 20, 20)
                                .addComponent(btn_LamMoi)
                                .addGap(20, 20, 20)
                                .addComponent(btn_XemChiTiet)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
        jPanel2Layout.setVerticalGroup(
                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(20, 20, 20)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addGroup(jPanel2Layout.createSequentialGroup()
                                                .addComponent(jLabel3)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(dateNgayLap, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                        javax.swing.GroupLayout.DEFAULT_SIZE,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(jPanel2Layout.createSequentialGroup()
                                                .addComponent(jLabel2)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(txt_MaHoaDon, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                        javax.swing.GroupLayout.DEFAULT_SIZE,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(jPanel2Layout
                                                .createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                .addComponent(btn_TimKiem)
                                                .addComponent(btn_LamMoi)
                                                .addComponent(btn_XemChiTiet)))
                                .addGap(20, 20, 20)));

        jTable1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][] {

                },
                new String[] {
                        "Mã hóa đơn", "Mã khách hàng", "Tổng tiền", "Phương thức thanh toán", "Trạng thái", "Ngày tạo",
                        "Ngày cập nhật"
                }) {
            Class[] types = new Class[] {
                    java.lang.String.class, java.lang.String.class, java.lang.Double.class, java.lang.String.class,
                    java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean[] {
                    false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types[columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        });
        jTable1.setRowHeight(25);
        jTable1.setSelectionBackground(new java.awt.Color(232, 57, 95));
        jScrollPane1.setViewportView(jTable1);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(jScrollPane1)
                                        .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.LEADING,
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.LEADING,
                                                javax.swing.GroupLayout.DEFAULT_SIZE, 1174, Short.MAX_VALUE))
                                .addContainerGap()));
        jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel1)
                                .addGap(18, 18, 18)
                                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE,
                                        javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 547, Short.MAX_VALUE)
                                .addContainerGap()));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE,
                                javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE,
                                javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));
    }// </editor-fold>

    // Variables declaration - do not modify
    private javax.swing.JButton btn_LamMoi;
    private javax.swing.JButton btn_TimKiem;
    private javax.swing.JButton btn_XemChiTiet;
    private com.toedter.calendar.JDateChooser dateNgayLap;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField txt_MaHoaDon;
    // End of variables declaration
}
