/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package gui;

import java.awt.Component;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import dao.ChiTietHoaDon_dao;
import dao.Ghe_dao;
import dao.HoaDon_dao;
import dao.KhachHang_dao;
import dao.Tau_dao;
import dao.Toa_dao;
import dao.Ve_dao;
import entity.ChiTietHoaDonEntity;
import entity.GheEntity;
import entity.HoaDonEntity;
import entity.KhachHangEntity;
import entity.LichTrinhEntity;
import entity.NhanVienEntity;
import entity.TauEntity;
import entity.ToaTauEntity;
import entity.VeEntity;
import java.awt.Color;
import java.awt.event.MouseEvent;

import util.GenerateID;
import util.ToanCuc;

/**
 *
 * @author ploc2
 */
public class DatVe_JFrame extends JFrame {

        private ArrayList<LichTrinhEntity> danhSachLichTrinh = new ArrayList<>();

        private ArrayList<VeEntity> gioVeHienTai = new ArrayList<>();

        private TrainSeatPanel selectedSeatPanel; // Biến để lưu ghế được chọn

        private Tau_dao tau_dao = new Tau_dao();
        private Toa_dao toa_dao = new Toa_dao();
        private Ghe_dao ghe_dao = new Ghe_dao();

        private KhachHang_dao khachHang_dao = new KhachHang_dao();

        private HoaDon_dao hoaDon_dao = new HoaDon_dao();

        private ChiTietHoaDon_dao chiTietHoaDon_dao = new ChiTietHoaDon_dao();

        private Ve_dao ve_dao = new Ve_dao();

        /**
         * Creates new form DatVe
         */
        public DatVe_JFrame(ArrayList<LichTrinhEntity> danhSachLichTrinh) {
                this.danhSachLichTrinh = danhSachLichTrinh;

                // gioVeHienTai.add(null);

                initComponents();

                jPanel_DanhSachTau.setBorder(new EmptyBorder(10, 10, 20, 10));
                jPanel_DanhSachToa.setBorder(new EmptyBorder(10, 10, 20, 10));

                // Set full màn hình
                setExtendedState(JFrame.MAXIMIZED_BOTH); // Maximize cửa sổ

                loadTauVaoPanelDanhSachTau();

                // jPanel_danhSachGioVe.add(new ChiTietGioVe());
                // jPanel_danhSachGioVe.add(new ChiTietGioVe());

                loadGheLanDau();

        }

        private void loadTauVaoPanelDanhSachTau() {

                // lấy ra danh sách mã tàu trong lịch trình
                ArrayList<String> dsMaTau = new ArrayList<>();
                String resultString = "";
                String hanhTrinhString = "";
                for (LichTrinhEntity lichTrinh : danhSachLichTrinh) {
                        dsMaTau.add(lichTrinh.getTau().getMaTau());

                        resultString = resultString + ",'" + lichTrinh.getTau().getMaTau() + "'";
                        hanhTrinhString = hanhTrinhString + ",'" + lichTrinh.getMaHT() + "'";

                }

                if (resultString.length() == 0) {
                        return;
                }

                resultString = resultString.substring(1);
                hanhTrinhString = hanhTrinhString.substring(1);

                ArrayList<TauEntity> danhSachTau = tau_dao.getAllTauDatVe(resultString, hanhTrinhString);

                // create map from arraylist danhSachTau
                Map<String, TauEntity> mapTau = new HashMap<>();
                for (TauEntity tauEntity : danhSachTau) {
                        mapTau.put(tauEntity.getMaTau(), tauEntity);
                }

                for (LichTrinhEntity lichTrinh : danhSachLichTrinh) {
                        TrainInfoPanel panel2 = new TrainInfoPanel(mapTau.get(lichTrinh.getTau().getMaTau()), lichTrinh,
                                        this);
                        jPanel_DanhSachTau.add(panel2);
                }

                loadToaVaoTable(danhSachLichTrinh.get(0).getTau().getMaTau());
        }

        void loadGheLanDau() {
                // add 40 seats to the table, top to below after left to right
                for (int i = 1; i <= 4; i++) {
                        for (int j = 1; j <= 10; j++) {
                                int seatNumber = (j - 1) * 4 + i;
                                GheEntity ghe = null;
                                jPanel_DanhSachGhe.add(createSeatPanel(ghe, seatNumber));
                        }
                }
        }

        void loadGheVaoTable(int maToa) {
                System.out.println("loadGheVaoTable" + maToa);
                jPanel_DanhSachGhe.removeAll();
                jPanel_DanhSachGhe.revalidate(); // Revalidate to refresh the layout

                if (maToa == 0) {
                        JOptionPane.showMessageDialog(this, "Chưa có ghế nào được chọn");
                        return;
                }

                // get all ghe of the train
                ArrayList<GheEntity> dsGhe = ghe_dao.getAllGheVaTrangThaiHienTai(maToa + "",
                                ToanCuc.getLichTrinh().getMaHT());
                System.out.println("danh sach ghe " + dsGhe);

                for (int i = 1; i <= 4; i++) {
                        for (int j = 1; j <= 10; j++) {
                                int seatNumber = (j - 1) * 4 + i;

                                if (seatNumber > dsGhe.size()) {
                                        jPanel_DanhSachGhe.add(createSeatPanel(null, seatNumber));
                                } else {
                                        jPanel_DanhSachGhe.add(createSeatPanel(dsGhe.get(seatNumber - 1), seatNumber));

                                }
                        }
                }

                // Refresh the UI to reflect the changes
                jPanel_DanhSachGhe.revalidate();
                jPanel_DanhSachGhe.repaint();

        }

        private JPanel createSeatPanel(GheEntity ghe, int seatNumber) {
                TrainSeatPanel seatPanel = new TrainSeatPanel(ghe, seatNumber);

                return seatPanel;
        }

        void loadToaVaoTable(String maTau) {
                ArrayList<ToaTauEntity> dsToa = toa_dao.getAllToaTau(maTau);
                // Clear the existing components if necessary
                jPanel_DanhSachToa.removeAll();
                jPanel_DanhSachToa.revalidate(); // Revalidate to refresh the layout

                // Add all ToaTauPanels in reverse order
                for (int i = dsToa.size() - 1; i >= 0; i--) {
                        ToaTauEntity toaTauEntity = dsToa.get(i);
                        ThongTinToaPanel toa = new ThongTinToaPanel("trainCar2.png", toaTauEntity.getTenToa(),
                                        toaTauEntity, this);
                        jPanel_DanhSachToa.add(toa);
                }

                // Create a first panel to display at the beginning
                ThongTinToaPanel toa0 = new ThongTinToaPanel("dau-taupng.png", "0", null, this);
                jPanel_DanhSachToa.add(toa0);

                // Refresh the UI to reflect the changes
                jPanel_DanhSachToa.revalidate();
                jPanel_DanhSachToa.repaint();
        }

        private void btn_LamMoiActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btn_LamMoiActionPerformed
                lamMoi();
        }// GEN-LAST:event_btn_LamMoiActionPerformed

        private void btn_ThemVaoHoaDonActionPerformed(java.awt.event.ActionEvent evt) {
                themVaoHoaDon();
        }

        private void btn_CapNhatActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btn_CapNhatActionPerformed
                capNhat();
        }// GEN-LAST:event_btn_CapNhatActionPerformed

        private void btn_TaoHoaDonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btn_TaoHoaDonActionPerformed
                // TODO add your handling code here:
                taoHoaDon();
        }// GEN-LAST:event_btn_TaoHoaDonActionPerformed

        private void lamMoi() {
                loadGheVaoTable(Integer.parseInt(ToanCuc.getToaHienTai().getMaToa()));
        }

        private void themVaoHoaDon() {
                GheEntity ghe = ToanCuc.getGheHienTai();
                // check ghế có
                boolean checkGheDanhSachGheHienTai = ToanCuc.checkGheDanhSachGheHienTai(ghe);

                if (checkGheDanhSachGheHienTai == true) {
                        JOptionPane.showMessageDialog(this, "Ghế đã có trong danh sách giỏ vé hiện tại");
                        return;
                }

                ToanCuc.themGheDanhSachGheHienTai(ghe);

                ChiTietGioVe chiTietGioVe = new ChiTietGioVe(ToanCuc.getLichTrinh(), ToanCuc.getTauHienTai(),
                                ToanCuc.getToaHienTai(), ToanCuc.getGheHienTai());
                System.out.println("" + ToanCuc.getLichTrinh() + ToanCuc.getTauHienTai() + ToanCuc.getToaHienTai()
                                + ToanCuc.getGheHienTai());
                jPanel_danhSachGioVe.add(chiTietGioVe);

                jPanel_danhSachGioVe.revalidate();
                jPanel_danhSachGioVe.repaint();
                System.out.println(ghe.getMaGhe());
                return;
        }

        private void capNhat() {
                throw new UnsupportedOperationException("Not supported yet."); // Generated from
                                                                               // nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }

        private void taoHoaDon() {

                if (ToanCuc.getDanhSachGheHienTai().size() == 0) {
                        JOptionPane.showMessageDialog(null, "Vui lòng thêm vé vào giỏ hàng trước khi tạo hóa đơn",
                                        "Cảnh báo",
                                        ABORT);
                        return;
                }
                // Lấy thông tin từ các trường nhập liệu
                txt_HoaDonHoTen.setText("");
                txt_HoaDon_CMND.setText("");
                txt_HoaDon_SDT.setText("");

                jLabel_MaNhanVien.setText(ToanCuc.getMa());
                jLabel_TenNhanVien.setText(ToanCuc.getTen());

                // Khởi tạo DefaultTableModel
                DefaultTableModel model = new DefaultTableModel(
                                new String[] { "Họ Tên", "CMND", "Tuổi", "Tên Tàu", "Ghế", "Giá" }, 0);
                jTable_DanhSachVe.setModel(model);

                Double tongTien = 0.0;

                // Lặp qua các panel trong jPanel_danhSachGioVe
                for (Component comp : jPanel_danhSachGioVe.getComponents()) {
                        if (comp instanceof ChiTietGioVe) {
                                ChiTietGioVe chiTietGioVe = (ChiTietGioVe) comp; // Ép kiểu thành ChiTietGioVe

                                // Lấy thông tin từ các trường nhập liệu
                                String hoTen = chiTietGioVe.getTxt_hoTen();
                                String cmnd = chiTietGioVe.getTxt_CMND();
                                String tuoi = chiTietGioVe.getTxt_tuoi();

                                TauEntity tau = chiTietGioVe.getTau();
                                ToaTauEntity toa = chiTietGioVe.getToa();
                                GheEntity ghe = chiTietGioVe.getGhe();
                                LichTrinhEntity lichTrinh = chiTietGioVe.getLichtrinh();

                                // Kiểm tra xem các ô nhập liệu có rỗng không
                                if (hoTen.isEmpty() || cmnd.isEmpty() || tuoi.isEmpty()) {
                                        javax.swing.JOptionPane.showMessageDialog(this,
                                                        "Vui lòng điền đầy đủ thông tin!", "Cảnh báo",
                                                        javax.swing.JOptionPane.WARNING_MESSAGE);
                                        return; // Kết thúc hàm nếu có ô rỗng
                                }

                                tongTien += ghe.getGia();

                                model.addRow(new Object[] { hoTen, cmnd, tuoi, tau.getTenTau(),
                                                "Toa " + toa.getTenToa() + " - Ghế " + ghe.getMaGhe(),
                                                ghe.getGia(), ghe.getGia() });

                                VeEntity ve = new VeEntity();
                                ve.setGhe(ghe);
                                ve.setSoGhe(ghe.getSoGhe());
                                ve.setLichTrinh(lichTrinh);
                                ve.setTau(tau);
                                ve.setToa(toa);

                                KhachHangEntity khachHang = new KhachHangEntity();
                                khachHang.setHoTen(hoTen);
                                khachHang.setSoCCCD(cmnd);
                                khachHang.setTuoi(Integer.parseInt(tuoi));
                                ve.setKhachHang(khachHang);

                                ToanCuc.themVeDanhSachVeHienTai(ve);

                        }

                }

                ToanCuc.setTongTien(tongTien);

                jTable_DanhSachVe.setModel(model);
                jLabel_TongTien.setText(tongTien + " VND");
                // Hiển thị hóa đơn
                javax.swing.JOptionPane.showMessageDialog(this, "Hoàn tất xác nhận thông tin đặt vé để hoàn tất!",
                                "Hóa Đơn",
                                javax.swing.JOptionPane.INFORMATION_MESSAGE);
        }

        private void btn_HoanTatHoaDonMouseClicked(MouseEvent evt) {
                // Lấy thông tin từ các trường nhập liệu
                String hoTen = txt_HoaDonHoTen.getText();
                String cmnd = txt_HoaDon_CMND.getText();
                String sdt = txt_HoaDon_SDT.getText();

                KhachHangEntity khachHang = new KhachHangEntity();
                khachHang.setHoTen(hoTen);
                khachHang.setSoCCCD(cmnd);
                khachHang.setSoDienThoai(sdt);
                khachHang.setMaKH(GenerateID.sinhMa("KH"));

                // laasy thong tin nhan vien
                if (hoTen.isEmpty() || cmnd.isEmpty() || sdt.isEmpty()) {
                        JOptionPane.showMessageDialog(this, "Vui lòng điền đầy đủ thông tin!", "Cảnh báo",
                                        JOptionPane.WARNING_MESSAGE);
                        return; // Kết thúc hàm nếu có ô rỗng
                }

                String maNhanVien = jLabel_MaNhanVien.getText();
                String tenNhanVien = jLabel_TenNhanVien.getText();

                NhanVienEntity nv = new NhanVienEntity(maNhanVien, tenNhanVien);

                double tongTien = ToanCuc.getTongTien();

                // create Hoa don
                HoaDonEntity hoaDon = new HoaDonEntity();

                khachHang_dao.themKH(khachHang);

                // luu ve
                ArrayList<VeEntity> dsVe = ToanCuc.getDanhSachVeHienTai();
                for (VeEntity ve : dsVe) {

                        KhachHangEntity kh = ve.getKhachHang();
                        kh.setMaKH(GenerateID.sinhMa("KH"));
                        kh.setHoTen(ve.getKhachHang().getHoTen());
                        kh.setTuoi(ve.getKhachHang().getTuoi());

                        // khachHang_dao.themKH(kh);

                        ve.setMaVe(GenerateID.sinhMa("VE"));
                        ve.setKhachHang(kh);
                        ve.setGia(ve.getGhe().getGia());
                        ve_dao.themVe(ve);
                }

                hoaDon.setMaHD(GenerateID.sinhMa("HD"));
                hoaDon.setMaKH(khachHang);
                hoaDon.setPhuongThucThanhToan("Tiền mặt");
                hoaDon.setTrangThai(1);
                hoaDon.setTongTien(tongTien);

                hoaDon.setNhanVien(nv);

                // insert to database
                hoaDon_dao.themHoaDon2(hoaDon);

                for (VeEntity veEntity : dsVe) {
                        ChiTietHoaDonEntity chiTiet = new ChiTietHoaDonEntity();
                        chiTiet.setVe(veEntity);
                        chiTiet.setSoLuong(1);
                        chiTiet.setGiaBan(veEntity.getGhe().getGia());
                        chiTiet.setGiaGoc(veEntity.getGhe().getGia());
                        chiTiet.setThanhTien(veEntity.getGhe().getGia());
                        chiTiet.setMaCTDH(hoaDon.getMaHD());
                        chiTiet.setKhachHang(veEntity.getKhachHang());
                        chiTiet.setHoaDon(hoaDon);

                        chiTietHoaDon_dao.themChiTietHoaDon(chiTiet);
                }

                // thông báo đặt vé thành công, bấm ok để thoát
                JOptionPane.showMessageDialog(this, "Đặt vé thành công, bấm ok để thoát", "Thông báo",
                                JOptionPane.INFORMATION_MESSAGE);
                // this.dispose();
                ToanCuc.setDanhSachGheHienTai(new ArrayList<GheEntity>());
                ToanCuc.setDanhSachVeHienTai(new ArrayList<VeEntity>());

                this.setVisible(false);
                // tắt màn hình đặt vé Jframe hiện tại

                // new TrangChu_GUI().setVisible(true);

        }

        /**
         * This method is called from within the constructor to initialize the form.
         * WARNING: Do NOT modify this code. The content of this method is always
         * regenerated by the Form Editor.
         */
        @SuppressWarnings("unchecked")
        // <editor-fold defaultstate="collapsed" desc="Generated
        // <editor-fold defaultstate="collapsed" desc="Generated
        // <editor-fold defaultstate="collapsed" desc="Generated
        // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel_TieuDe = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jPanel_BenTrai = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jPanel_DanhSachTau = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jPanel_DanhSachToa = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jPanel_DanhSachGhe = new javax.swing.JPanel();
        btn_LamMoi = new javax.swing.JButton();
        btn_ThemVaoHoaDon = new javax.swing.JButton();
        btn_CapNhat = new javax.swing.JButton();
        btn_TaoHoaDon = new javax.swing.JButton();
        jPanel7 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        jPanel10 = new javax.swing.JPanel();
        jPanel12 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jPanel_GioVe = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        jPanel_danhSachGioVe = new javax.swing.JPanel();
        jPanel_ThongTinHoaDon = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txt_HoaDonHoTen = new javax.swing.JTextField();
        txt_HoaDon_CMND = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txt_HoaDon_SDT = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel_TenNhanVien = new javax.swing.JLabel();
        jLabel_MaNhanVien = new javax.swing.JLabel();
        jScrollPane5 = new javax.swing.JScrollPane();
        jTable_DanhSachVe = new javax.swing.JTable();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel_TongTien = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        btn_HoanTatHoaDon = new javax.swing.JButton();
        btn_Huy = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Đặt vé");
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setMaximumSize(new java.awt.Dimension(2000, 1800));

        jPanel1.setPreferredSize(new java.awt.Dimension(100, 100));

        jLabel_TieuDe.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel_TieuDe.setText("12321");
        jLabel_TieuDe.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jLabel_TieuDe.setName(""); // NOI18N

        jPanel3.setPreferredSize(new java.awt.Dimension(900, 587));
        jPanel3.setLayout(new java.awt.GridLayout(1, 3));

        jPanel_BenTrai.setMaximumSize(new java.awt.Dimension(800, 800));
        jPanel_BenTrai.setMinimumSize(new java.awt.Dimension(800, 800));
        jPanel_BenTrai.setRequestFocusEnabled(false);

        jScrollPane2.setBackground(new java.awt.Color(204, 204, 204));
        jScrollPane2.setBorder(javax.swing.BorderFactory.createTitledBorder("Danh sách Tàu"));
        jScrollPane2.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        jScrollPane2.setAlignmentX(5.0F);
        jScrollPane2.setAlignmentY(5.0F);

        jPanel_DanhSachTau.setBackground(new java.awt.Color(255, 255, 255));
        jPanel_DanhSachTau.setAlignmentX(5.0F);
        jPanel_DanhSachTau.setAlignmentY(5.0F);
        jPanel_DanhSachTau.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jPanel_DanhSachTau.setName(""); // NOI18N
        jScrollPane2.setViewportView(jPanel_DanhSachTau);

        jScrollPane1.setBorder(javax.swing.BorderFactory.createTitledBorder("Danh sách Toa"));
        jScrollPane1.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);

        jPanel_DanhSachToa.setBackground(new java.awt.Color(255, 255, 255));
        jScrollPane1.setViewportView(jPanel_DanhSachToa);

        jPanel_DanhSachGhe.setBackground(new java.awt.Color(255, 255, 255));
        jPanel_DanhSachGhe.setBorder(javax.swing.BorderFactory.createTitledBorder("Danh sách Ghế"));
        jPanel_DanhSachGhe.setMaximumSize(new java.awt.Dimension(800, 32767));
        jPanel_DanhSachGhe.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel_DanhSachGheMouseClicked(evt);
            }
        });
        jPanel_DanhSachGhe.setLayout(new java.awt.GridLayout(4, 10, 5, 5));
        jScrollPane3.setViewportView(jPanel_DanhSachGhe);

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

        btn_ThemVaoHoaDon.setBackground(new java.awt.Color(0, 51, 51));
        btn_ThemVaoHoaDon.setFont(new java.awt.Font("Times New Roman", 1, 15)); // NOI18N
        btn_ThemVaoHoaDon.setForeground(java.awt.Color.white);
        btn_ThemVaoHoaDon.setText("Thêm vào giỏ vé");
        btn_ThemVaoHoaDon.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_ThemVaoHoaDon.setHorizontalAlignment(javax.swing.SwingConstants.LEADING);
        btn_ThemVaoHoaDon.setPreferredSize(new java.awt.Dimension(90, 31));
        btn_ThemVaoHoaDon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_ThemVaoHoaDonActionPerformed(evt);
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

        btn_TaoHoaDon.setBackground(new java.awt.Color(0, 51, 51));
        btn_TaoHoaDon.setFont(new java.awt.Font("Times New Roman", 1, 15)); // NOI18N
        btn_TaoHoaDon.setForeground(java.awt.Color.white);
        btn_TaoHoaDon.setText("Tạo hóa đơn");
        btn_TaoHoaDon.setHorizontalAlignment(javax.swing.SwingConstants.LEADING);
        btn_TaoHoaDon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_TaoHoaDonActionPerformed(evt);
            }
        });

        jLabel11.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel11.setText("Ghế đã đặt");

        jLabel12.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel12.setText("Ghế đã chọn");

        jLabel13.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel13.setText("Ghế chưa sẵn sàng");

        jLabel14.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel14.setText("Ghế còn trống");

        jPanel8.setBackground(java.awt.Color.gray);

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 51, Short.MAX_VALUE)
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 52, Short.MAX_VALUE)
        );

        jPanel9.setBackground(java.awt.Color.green);

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 47, Short.MAX_VALUE)
        );

        jPanel10.setBackground(java.awt.Color.black);

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 51, Short.MAX_VALUE)
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 43, Short.MAX_VALUE)
        );

        jPanel12.setBackground(java.awt.Color.red);

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(30, 30, 30)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel12, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel11, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel13, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(1, 1, 1)
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(145, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel11)
                    .addComponent(jLabel14))
                .addGap(46, 46, 46)
                .addComponent(jLabel12)
                .addGap(32, 32, 32)
                .addComponent(jLabel13)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel12, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel8, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 12, Short.MAX_VALUE)
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(48, 48, 48))
        );

        javax.swing.GroupLayout jPanel_BenTraiLayout = new javax.swing.GroupLayout(jPanel_BenTrai);
        jPanel_BenTrai.setLayout(jPanel_BenTraiLayout);
        jPanel_BenTraiLayout.setHorizontalGroup(
            jPanel_BenTraiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_BenTraiLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel_BenTraiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addComponent(jScrollPane2)
                    .addComponent(jScrollPane3)
                    .addGroup(jPanel_BenTraiLayout.createSequentialGroup()
                        .addGroup(jPanel_BenTraiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel_BenTraiLayout.createSequentialGroup()
                                .addComponent(btn_LamMoi, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btn_ThemVaoHoaDon, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btn_CapNhat, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btn_TaoHoaDon))
                            .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 163, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel_BenTraiLayout.setVerticalGroup(
            jPanel_BenTraiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_BenTraiLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel_BenTraiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_CapNhat, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_ThemVaoHoaDon, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_LamMoi, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_TaoHoaDon, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 137, Short.MAX_VALUE)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(67, 67, 67))
        );

        jPanel3.add(jPanel_BenTrai);

        jPanel2.setLayout(new java.awt.BorderLayout());

        jPanel_GioVe.setBorder(javax.swing.BorderFactory.createTitledBorder("Gio ve"));
        jPanel_GioVe.setToolTipText("");
        jPanel_GioVe.setPreferredSize(new java.awt.Dimension(300, 300));
        jPanel_GioVe.setLayout(new javax.swing.BoxLayout(jPanel_GioVe, javax.swing.BoxLayout.LINE_AXIS));

        jScrollPane4.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane4.setToolTipText("");

        jPanel_danhSachGioVe.setBackground(new java.awt.Color(255, 255, 255));
        jPanel_danhSachGioVe.setLayout(new java.awt.GridLayout(0, 1));
        jScrollPane4.setViewportView(jPanel_danhSachGioVe);

        jPanel_GioVe.add(jScrollPane4);

        jPanel2.add(jPanel_GioVe, java.awt.BorderLayout.PAGE_START);

        jPanel_ThongTinHoaDon.setBackground(new java.awt.Color(255, 255, 255));
        jPanel_ThongTinHoaDon.setBorder(javax.swing.BorderFactory.createTitledBorder("Thông tin hóa đơn"));

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel1.setText("Thông tin người đặt mua");

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel2.setText("Họ tên");

        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel3.setText("CMND/Hộ chiếu");

        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel4.setText("Số điện thoại");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txt_HoaDonHoTen, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_HoaDon_CMND, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_HoaDon_SDT, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txt_HoaDonHoTen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txt_HoaDon_CMND, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txt_HoaDon_SDT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel5.setText("Thông tin Nhân viên");

        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel6.setText("Họ tên");

        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel7.setText("Mã nhân viên");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel_TenNhanVien, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel_MaNhanVien, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 81, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jLabel_TenNhanVien))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(jLabel_MaNhanVien))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTable_DanhSachVe.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Họ tên", "Số giấy tờ", "Tuổi", "Tàu", "Thông tin", "Giá", "Thành tiền"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, true, false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane5.setViewportView(jTable_DanhSachVe);

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel8.setText("Thông tin mua vé");

        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel9.setText("Tổng tiền");

        jLabel_TongTien.setText("0");

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel10.setText("Xác nhận thông tin đặt vé tàu");

        jPanel6.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        btn_HoanTatHoaDon.setBackground(new java.awt.Color(51, 255, 0));
        btn_HoanTatHoaDon.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btn_HoanTatHoaDon.setText("Hoàn tất");
        btn_HoanTatHoaDon.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btn_HoanTatHoaDon.setMaximumSize(new java.awt.Dimension(60, 30));
        btn_HoanTatHoaDon.setMinimumSize(new java.awt.Dimension(60, 30));
        btn_HoanTatHoaDon.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_HoanTatHoaDonMouseClicked(evt);
            }
        });
        jPanel6.add(btn_HoanTatHoaDon);

        btn_Huy.setBackground(new java.awt.Color(204, 204, 204));
        btn_Huy.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btn_Huy.setText("Hủy");
        btn_Huy.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btn_Huy.setMaximumSize(new java.awt.Dimension(60, 30));
        btn_Huy.setMinimumSize(new java.awt.Dimension(60, 30));
        btn_Huy.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_HuyMouseClicked(evt);
            }
        });
        btn_Huy.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_HuyActionPerformed(evt);
            }
        });
        jPanel6.add(btn_Huy);

        javax.swing.GroupLayout jPanel_ThongTinHoaDonLayout = new javax.swing.GroupLayout(jPanel_ThongTinHoaDon);
        jPanel_ThongTinHoaDon.setLayout(jPanel_ThongTinHoaDonLayout);
        jPanel_ThongTinHoaDonLayout.setHorizontalGroup(
            jPanel_ThongTinHoaDonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_ThongTinHoaDonLayout.createSequentialGroup()
                .addGroup(jPanel_ThongTinHoaDonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel_ThongTinHoaDonLayout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 370, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel_ThongTinHoaDonLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel_ThongTinHoaDonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane5)
                            .addGroup(jPanel_ThongTinHoaDonLayout.createSequentialGroup()
                                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(75, 75, 75)
                                .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel_ThongTinHoaDonLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel_TongTien, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
            .addGroup(jPanel_ThongTinHoaDonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel_ThongTinHoaDonLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addContainerGap()))
        );
        jPanel_ThongTinHoaDonLayout.setVerticalGroup(
            jPanel_ThongTinHoaDonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_ThongTinHoaDonLayout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel_ThongTinHoaDonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(9, 9, 9)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel_ThongTinHoaDonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(jLabel_TongTien))
                .addContainerGap(59, Short.MAX_VALUE))
            .addGroup(jPanel_ThongTinHoaDonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel_ThongTinHoaDonLayout.createSequentialGroup()
                    .addContainerGap(429, Short.MAX_VALUE)
                    .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap()))
        );

        jPanel2.add(jPanel_ThongTinHoaDon, java.awt.BorderLayout.CENTER);

        jPanel3.add(jPanel2);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel_TieuDe, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel_TieuDe, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 1612, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 847, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

        private void btn_HuyMouseClicked(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_btn_HuyMouseClicked
                // TODO add your handling code here:
        }// GEN-LAST:event_btn_HuyMouseClicked

        private void btn_HuyActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btn_HuyActionPerformed
                // TODO add your handling code here:
        }// GEN-LAST:event_btn_HuyActionPerformed

        private void jButton2MouseClicked(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_jButton2MouseClicked
                // TODO add your handling code here:
        }// GEN-LAST:event_jButton2MouseClicked

        private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButton2ActionPerformed
                // TODO add your handling code here:
        }// GEN-LAST:event_jButton2ActionPerformed

        private void jPanel_DanhSachGheMouseClicked(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_jPanel_DanhSachGheMouseClicked
        }// GEN-LAST:event_jPanel_DanhSachGheMouseClicked

        /**
         * @param args the command line arguments
         */

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_CapNhat;
    private javax.swing.JButton btn_HoanTatHoaDon;
    private javax.swing.JButton btn_Huy;
    private javax.swing.JButton btn_LamMoi;
    private javax.swing.JButton btn_TaoHoaDon;
    private javax.swing.JButton btn_ThemVaoHoaDon;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabel_MaNhanVien;
    private javax.swing.JLabel jLabel_TenNhanVien;
    private javax.swing.JLabel jLabel_TieuDe;
    private javax.swing.JLabel jLabel_TongTien;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JPanel jPanel_BenTrai;
    private javax.swing.JPanel jPanel_DanhSachGhe;
    private javax.swing.JPanel jPanel_DanhSachTau;
    private javax.swing.JPanel jPanel_DanhSachToa;
    private javax.swing.JPanel jPanel_GioVe;
    private javax.swing.JPanel jPanel_ThongTinHoaDon;
    private javax.swing.JPanel jPanel_danhSachGioVe;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JTable jTable_DanhSachVe;
    private javax.swing.JTextField txt_HoaDonHoTen;
    private javax.swing.JTextField txt_HoaDon_CMND;
    private javax.swing.JTextField txt_HoaDon_SDT;
    // End of variables declaration//GEN-END:variables

}
