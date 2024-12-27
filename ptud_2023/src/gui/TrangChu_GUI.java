package gui;

import connectDB.ConnectDB;
import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Desktop;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.Timer;

import entity.EntityEnum;
import util.ToanCuc;

public class TrangChu_GUI extends javax.swing.JFrame {
        
    public TrangChu_GUI() throws Exception {
        
        ConnectDB.connect();
        ConnectDB.getConnection();
        ToanCuc.setMa("NV1000001");
        ToanCuc.setTen("Hoan Tran");

        initComponents();
        
        jlb_name.setText(ToanCuc.getTen());
        lbl_ChucVu.setText(EntityEnum.ConvertEnumChucVuToString(ToanCuc.getLoai()));
        
        if (ToanCuc.getGioiTinh().equals("Nữ")) {
            URL image_User_nu = TrangChu_GUI.class.getResource("/pic/icon/user_nu.png");
            ImageIcon img_User_nu = new ImageIcon(image_User_nu);
            Image scaled_Users_nu = img_User_nu.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
            img_User_nu = new ImageIcon(scaled_Users_nu);

            lbl_Users.setIcon(img_User_nu);
        } else {
            URL image_User_nam = TrangChu_GUI.class.getResource("/pic/icon/user_nam.png");
            ImageIcon img_User = new ImageIcon(image_User_nam);
            Image scaled_Users = img_User.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
            img_User = new ImageIcon(scaled_Users);
            lbl_Users.setIcon(img_User);
        }

        if (ToanCuc.getLoai() == 0) {
            Jpanel_NhanVien.setVisible(false);
        }
        setSize(1366, 768);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        getContentPane().setLayout(null);

        TrangChu_Panel = new TrangChu_JPanel();
        BanHang_Panel = new BanVe_JPanel();
        QuanLyTau_Panel = new QuanLyTau_JPanel();

        HoaDon_Panel = new HoaDon_JPanel();
        DoiTra_Panel = new TrangChu_JPanel();
        ThongKe_Panel = new ThongKe_JPanel();
        KhachHang_Panel = new KhachHang_JPanel();
        NhanVien_Panel = new NhanVien_JPanel();
        NhaSanXuat_Panel = new NhaSanXuat_JPanel();
        LichTrinh_Panel = new LichTrinh_JPanel();

        URL image_home = TrangChu_GUI.class.getResource("/pic/icon/home.png");   
        ImageIcon img_home = new ImageIcon(image_home);
        Image scaled_home = img_home.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        img_home = new ImageIcon(scaled_home);
        lbl_IconTrangChu.setIcon(img_home);
        
        URL image_SanPham = TrangChu_GUI.class.getResource("/pic/icon/productbox.png");   
        ImageIcon img_SanPham = new ImageIcon(image_SanPham);
        Image scaled_SanPham = img_SanPham.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        img_SanPham = new ImageIcon(scaled_SanPham);
        lbl_IconQuanLyTau.setIcon(img_SanPham);
        
        URL image_BanHang = TrangChu_GUI.class.getResource("/pic/icon/buttonTimKiem.png");
        ImageIcon img_BanHang = new ImageIcon(image_BanHang);
        Image scaled_BanHang = img_BanHang.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        img_BanHang = new ImageIcon(scaled_BanHang);
        lbl_IconTimTau.setIcon(img_BanHang);

        URL image_User = TrangChu_GUI.class.getResource("/pic/icon/user_nam.png");
        ImageIcon img_User = new ImageIcon(image_User);
        Image scaled_Users = img_User.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        img_User = new ImageIcon(scaled_Users);
        lbl_Users.setIcon(img_User);

        URL image_HoaDon = TrangChu_GUI.class.getResource("/pic/icon/hoadon.png");
        ImageIcon img_HoaDon = new ImageIcon(image_HoaDon);
        Image scaled_HoaDon = img_HoaDon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        img_HoaDon = new ImageIcon(scaled_HoaDon);
        lbl_IconHoaDon.setIcon(img_HoaDon);

        URL image_DoiTra = TrangChu_GUI.class.getResource("/pic/icon/return.png");
        ImageIcon img_DoiTra = new ImageIcon(image_DoiTra);
        Image scaled_DoiTra = img_DoiTra.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        img_DoiTra = new ImageIcon(scaled_DoiTra);
        lbl_IconDoiTra.setIcon(img_DoiTra);

        URL image_KhachHang = TrangChu_GUI.class.getResource("/pic/icon/customer.png");
        ImageIcon img_KhachHang = new ImageIcon(image_KhachHang);
        Image scaled_KhachHang = img_KhachHang.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        img_KhachHang = new ImageIcon(scaled_KhachHang);
        lbl_IconKhachHang.setIcon(img_KhachHang);

        URL image_NhanVien = TrangChu_GUI.class.getResource("/pic/icon/nhanvien.png");
        ImageIcon img_NhanVien = new ImageIcon(image_NhanVien);
        Image scaled_NhanVien = img_NhanVien.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        img_NhanVien = new ImageIcon(scaled_NhanVien);
        lbl_IconNhanVien.setIcon(img_NhanVien);

        URL image_ThongKe = TrangChu_GUI.class.getResource("/pic/icon/thongke.png");
        ImageIcon img_ThongKe = new ImageIcon(image_ThongKe);
        Image scaled_ThongKe = img_ThongKe.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        img_ThongKe = new ImageIcon(scaled_ThongKe);
        lbl_IconThongKe.setIcon(img_ThongKe);

        URL image_DangXuat = TrangChu_GUI.class.getResource("/pic/icon/logout.png");
        ImageIcon img_DangXuat = new ImageIcon(image_DangXuat);
        Image scaled_DangXuat = img_DangXuat.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
        img_DangXuat = new ImageIcon(scaled_DangXuat);
        JMenu_DangXuat.setIcon(img_DangXuat);

        URL image_ThayMatKhau = TrangChu_GUI.class.getResource("/pic/icon/key.png");
        ImageIcon img_ThayMatKhau = new ImageIcon(image_ThayMatKhau);
        Image scaled_ThayMatKhau = img_ThayMatKhau.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
        img_ThayMatKhau = new ImageIcon(scaled_ThayMatKhau);
        JMenu_ThayMatKhau.setIcon(img_ThayMatKhau);

        URL image_GioiThieu = TrangChu_GUI.class.getResource("/pic/icon/gioithieu.png");
        ImageIcon img_GioiThieu = new ImageIcon(image_GioiThieu);
        Image scaled_GioiThieu = img_GioiThieu.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
        img_GioiThieu = new ImageIcon(scaled_GioiThieu);
        JMenu_GioiThieu.setIcon(img_GioiThieu);

        URL image_TroGiup = TrangChu_GUI.class.getResource("/pic/icon/trogiup.png");
        ImageIcon img_TroGiup = new ImageIcon(image_TroGiup);
        Image scaled_TroGiup = img_TroGiup.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
        img_TroGiup = new ImageIcon(scaled_TroGiup);
        JMenu_TroGiup.setIcon(img_TroGiup);

        // Thêm Panel và Main
        Jpanel_Main.add(TrangChu_Panel);
        Jpanel_Main.add(BanHang_Panel);
        Jpanel_Main.add(QuanLyTau_Panel);
        Jpanel_Main.add(HoaDon_Panel);
        Jpanel_Main.add(DoiTra_Panel);
        Jpanel_Main.add(ThongKe_Panel);
        Jpanel_Main.add(KhachHang_Panel);
        Jpanel_Main.add(NhanVien_Panel);
        Jpanel_Main.add(LichTrinh_Panel);
//        Jpanel_Main.add(NhaSanXuat_Panel);
        

        // Gán panleTrangChu background
        Jpanel_TrangChu.setBackground(new Color(112, 128, 144));
        // Gán sự kiện click Jpanel
        Jpanel_TrangChu.addMouseListener(new PanelButtonMouseAdapter(Jpanel_TrangChu) {
            @Override
            public void mouseClicked(MouseEvent e) {
                TatChonCacMuc();
                Jpanel_TrangChu.setBackground(new Color(112, 128, 144));
                menuClicked(TrangChu_Panel);
            }
        });
        Jpanel_BanHang.addMouseListener(new PanelButtonMouseAdapter(Jpanel_BanHang) {
            @Override
            public void mouseClicked(MouseEvent e) {
                TatChonCacMuc();
                Jpanel_BanHang.setBackground(new Color(112, 128, 144));
                menuClicked(BanHang_Panel);
            }
        });
        Jpanel_DoiTra.addMouseListener(new PanelButtonMouseAdapter(Jpanel_DoiTra) {
            @Override
            public void mouseClicked(MouseEvent e) {
                TatChonCacMuc();
                Jpanel_DoiTra.setBackground(new Color(112, 128, 144));
                menuClicked(DoiTra_Panel);
            }
        });
        Jpanel_KhachHang.addMouseListener(new PanelButtonMouseAdapter(Jpanel_KhachHang) {
            @Override
            public void mouseClicked(MouseEvent e) {
                TatChonCacMuc();
                Jpanel_KhachHang.setBackground(new Color(112, 128, 144));

                menuClicked(KhachHang_Panel);
            }
        });

        Jpanel_LichTrinh.addMouseListener(new PanelButtonMouseAdapter(Jpanel_LichTrinh) {
            @Override
            public void mouseClicked(MouseEvent e) {
                TatChonCacMuc();
                Jpanel_LichTrinh.setBackground(new Color(112, 128, 144));

                menuClicked(LichTrinh_Panel);
            }
        });
        Jpanel_NhanVien.addMouseListener(new PanelButtonMouseAdapter(Jpanel_NhanVien) {
            @Override
            public void mouseClicked(MouseEvent e) {
                TatChonCacMuc();
                Jpanel_NhanVien.setBackground(new Color(112, 128, 144));

                menuClicked(NhanVien_Panel);
            }
        });

        Jpanel_QuanLyTau.addMouseListener(new PanelButtonMouseAdapter(Jpanel_QuanLyTau) {
            @Override
            public void mouseClicked(MouseEvent e) {
                TatChonCacMuc();
                Jpanel_QuanLyTau.setBackground(new Color(112, 128, 144));

                menuClicked(QuanLyTau_Panel);
            }
        });

        Jpanel_ThongKe.addMouseListener(new PanelButtonMouseAdapter(Jpanel_ThongKe) {
            @Override
            public void mouseClicked(MouseEvent e) {
                TatChonCacMuc();
                Jpanel_ThongKe.setBackground(new Color(112, 128, 144));

                menuClicked(ThongKe_Panel);
            }
        });
        Jpanel_HoaDon.addMouseListener(new PanelButtonMouseAdapter(Jpanel_HoaDon) {
            @Override
            public void mouseClicked(MouseEvent e) {
                TatChonCacMuc();
                Jpanel_HoaDon.setBackground(new Color(112, 128, 144));

                menuClicked(HoaDon_Panel);
            }
        });

        Jpanel_NhaSanXuat.addMouseListener(new PanelButtonMouseAdapter(Jpanel_NhaSanXuat) {
            @Override
            public void mouseClicked(MouseEvent e) {
                TatChonCacMuc();
                Jpanel_NhaSanXuat.setBackground(new Color(112, 128, 144));

                menuClicked(NhaSanXuat_Panel);
            }
        });

        // MenuBar
        jMenuBar1.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);

        // Tạo AcTion để xử lý sự kiện cho phím O
        Action dangXuatAction = new AbstractAction("Đăng Xuất") {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        };

        // Gán Phím Crtl + O cho action này
        dangXuatAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_O, KeyEvent.CTRL_MASK));
        JMenuItem_An.setAction(dangXuatAction);
        JMenuItem_An.setPreferredSize(new java.awt.Dimension(0, 0));
        JMenu_DangXuat.setToolTipText("Ctrl+0");
        JMenu_DangXuat.setAccelerator(null);

        // Tạo và cấu hình timer
        Timer timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Lấy thời gian hiện tại
                Date now = new Date();

                // Định dạng thời gian
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                String formattedDate = dateFormat.format(now);
                // Đặt thời gian đã định dạng vào JLabel
                JMenu_Time.setText(formattedDate);
            }
        });
        timer.start();

        // Kiểm tra xem NhanVien_Panel đã hiển thị lên giao diện chưa
//        if (!NhanVien_Panel.isVisible()) {
//            // Nếu chưa hiển thị, bật nó lên
//            NhanVien_Panel.setVisible(true);
//        }
        
            TatChonCacMuc();
         Jpanel_TrangChu.setBackground(new Color(112, 128, 144));
         menuClicked(Jpanel_TrangChu);

    }

    private void TatChonCacMuc() {
        Jpanel_BanHang.setBackground(new Color(0,102,102));
        Jpanel_DoiTra.setBackground(new Color(0,102,102));
        Jpanel_KhachHang.setBackground(new Color(0,102,102));
        Jpanel_LichTrinh.setBackground(new Color(0,102,102));
        Jpanel_NhanVien.setBackground(new Color(0,102,102));
        Jpanel_QuanLyTau.setBackground(new Color(0,102,102));
        Jpanel_ThongKe.setBackground(new Color(0,102,102));
        Jpanel_HoaDon.setBackground(new Color(0,102,102));
        Jpanel_TrangChu.setBackground(new Color(0,102,102));
        Jpanel_NhaSanXuat.setBackground(new Color(0,102,102));
    }

    public void menuClicked(JPanel panel) {

        TrangChu_Panel.setVisible(false);
        BanHang_Panel.setVisible(false);
        QuanLyTau_Panel.setVisible(false);
        HoaDon_Panel.setVisible(false);
        KhachHang_Panel.setVisible(false);
        DoiTra_Panel.setVisible(false);
        ThongKe_Panel.setVisible(false);
        NhanVien_Panel.setVisible(false);
        LichTrinh_Panel.setVisible(false);

        panel.setVisible(true);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Jpanel_Menu = new javax.swing.JPanel();
        Jpanel_Users = new javax.swing.JPanel();
        jlb_name = new javax.swing.JLabel();
        lbl_Users = new javax.swing.JLabel();
        lbl_ChucVu = new javax.swing.JLabel();
        Jpanel_LichTrinh = new javax.swing.JPanel();
        lbl_lichTrinh = new javax.swing.JLabel();
        lbl_IconLichTrinh = new javax.swing.JLabel();
        Jpanel_NhanVien = new javax.swing.JPanel();
        lbl_NhanVien = new javax.swing.JLabel();
        lbl_IconNhanVien = new javax.swing.JLabel();
        Jpanel_ThongKe = new javax.swing.JPanel();
        lbl_ThongKe = new javax.swing.JLabel();
        lbl_IconThongKe = new javax.swing.JLabel();
        Jpanel_DoiTra = new javax.swing.JPanel();
        lbl_DoiTra = new javax.swing.JLabel();
        lbl_IconDoiTra = new javax.swing.JLabel();
        Jpanel_BanHang = new javax.swing.JPanel();
        lbl_IconTimTau = new javax.swing.JLabel();
        lbl_BanHang = new javax.swing.JLabel();
        Jpanel_TrangChu = new javax.swing.JPanel();
        lbl_TrangChu = new javax.swing.JLabel();
        lbl_IconTrangChu = new javax.swing.JLabel();
        Jpanel_HoaDon = new javax.swing.JPanel();
        lbl_IconHoaDon = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        Jpanel_KhachHang = new javax.swing.JPanel();
        lbl_IconKhachHang = new javax.swing.JLabel();
        lbl_KhachHang = new javax.swing.JLabel();
        Jpanel_NhaSanXuat = new javax.swing.JPanel();
        lbl_Nhom19 = new javax.swing.JLabel();
        lbl_IconNhom19 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        Jpanel_QuanLyTau = new javax.swing.JPanel();
        lbl_QuanLyTau = new javax.swing.JLabel();
        lbl_IconQuanLyTau = new javax.swing.JLabel();
        Jpanel_Main = new javax.swing.JPanel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        JMenu_ThayMatKhau = new javax.swing.JMenuItem();
        JMenu_DangXuat = new javax.swing.JMenuItem();
        JMenuItem_An = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        JMenu_GioiThieu = new javax.swing.JMenuItem();
        JMenu_TroGiup = new javax.swing.JMenuItem();
        JMenu_Time = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(1200, 720));
        setUndecorated(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        Jpanel_Menu.setBackground(new java.awt.Color(0, 51, 51));
        Jpanel_Menu.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Jpanel_Menu.setMinimumSize(new java.awt.Dimension(180, 672));
        Jpanel_Menu.setPreferredSize(new java.awt.Dimension(180, 768));
        Jpanel_Menu.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        Jpanel_Users.setBackground(new java.awt.Color(0, 102, 102));
        Jpanel_Users.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(255, 255, 255)));
        Jpanel_Users.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jlb_name.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jlb_name.setForeground(new java.awt.Color(255, 255, 255));
        jlb_name.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlb_name.setText("Phạm Hữu Lộc");
        Jpanel_Users.add(jlb_name, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 40, 180, 30));
        Jpanel_Users.add(lbl_Users, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 30, 30));

        lbl_ChucVu.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        lbl_ChucVu.setForeground(new java.awt.Color(255, 255, 255));
        lbl_ChucVu.setText("Quản Lý");
        Jpanel_Users.add(lbl_ChucVu, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 10, 110, 30));

        Jpanel_Menu.add(Jpanel_Users, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 180, 80));

        Jpanel_LichTrinh.setBackground(new java.awt.Color(0, 102, 102));
        Jpanel_LichTrinh.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lbl_lichTrinh.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        lbl_lichTrinh.setForeground(new java.awt.Color(255, 255, 255));
        lbl_lichTrinh.setText("Lịch Trình");
        Jpanel_LichTrinh.add(lbl_lichTrinh, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 10, 100, 29));
        Jpanel_LichTrinh.add(lbl_IconLichTrinh, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 30, 30));

        Jpanel_Menu.add(Jpanel_LichTrinh, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 480, 180, 50));

        Jpanel_NhanVien.setBackground(new java.awt.Color(0, 102, 102));
        Jpanel_NhanVien.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lbl_NhanVien.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        lbl_NhanVien.setForeground(new java.awt.Color(255, 255, 255));
        lbl_NhanVien.setText("Nhân Viên");
        Jpanel_NhanVien.add(lbl_NhanVien, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 10, 100, 29));
        Jpanel_NhanVien.add(lbl_IconNhanVien, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 30, 30));

        Jpanel_Menu.add(Jpanel_NhanVien, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 430, 180, 50));

        Jpanel_ThongKe.setBackground(new java.awt.Color(0, 102, 102));
        Jpanel_ThongKe.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lbl_ThongKe.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        lbl_ThongKe.setForeground(new java.awt.Color(255, 255, 255));
        lbl_ThongKe.setText("Thống Kê");
        Jpanel_ThongKe.add(lbl_ThongKe, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 10, 100, 29));
        Jpanel_ThongKe.add(lbl_IconThongKe, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 30, 30));

        Jpanel_Menu.add(Jpanel_ThongKe, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 380, 180, 50));

        Jpanel_DoiTra.setBackground(new java.awt.Color(0, 102, 102));
        Jpanel_DoiTra.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lbl_DoiTra.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        lbl_DoiTra.setForeground(new java.awt.Color(255, 255, 255));
        lbl_DoiTra.setText("Đổi Trả Vé");
        Jpanel_DoiTra.add(lbl_DoiTra, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 10, 100, 29));
        Jpanel_DoiTra.add(lbl_IconDoiTra, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 30, 30));

        Jpanel_Menu.add(Jpanel_DoiTra, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 330, 180, 50));

        Jpanel_BanHang.setBackground(new java.awt.Color(0, 102, 102));
        Jpanel_BanHang.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        Jpanel_BanHang.add(lbl_IconTimTau, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 30, 30));

        lbl_BanHang.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        lbl_BanHang.setForeground(new java.awt.Color(255, 255, 255));
        lbl_BanHang.setText("Tìm Vé");
        Jpanel_BanHang.add(lbl_BanHang, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 10, 100, 29));

        Jpanel_Menu.add(Jpanel_BanHang, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 130, 180, 50));

        Jpanel_TrangChu.setBackground(new java.awt.Color(0, 102, 102));
        Jpanel_TrangChu.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lbl_TrangChu.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        lbl_TrangChu.setForeground(new java.awt.Color(255, 255, 255));
        lbl_TrangChu.setText("Trang Chủ");
        Jpanel_TrangChu.add(lbl_TrangChu, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 10, 100, 29));
        Jpanel_TrangChu.add(lbl_IconTrangChu, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 30, 30));

        Jpanel_Menu.add(Jpanel_TrangChu, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 80, 180, 50));

        Jpanel_HoaDon.setBackground(new java.awt.Color(0, 102, 102));
        Jpanel_HoaDon.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        Jpanel_HoaDon.add(lbl_IconHoaDon, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 30, 30));

        jLabel3.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Hóa Đơn");
        Jpanel_HoaDon.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 10, 100, 29));

        Jpanel_Menu.add(Jpanel_HoaDon, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 180, 180, 50));

        Jpanel_KhachHang.setBackground(new java.awt.Color(0, 102, 102));
        Jpanel_KhachHang.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        Jpanel_KhachHang.add(lbl_IconKhachHang, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 30, 30));

        lbl_KhachHang.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        lbl_KhachHang.setForeground(new java.awt.Color(255, 255, 255));
        lbl_KhachHang.setText("Hành Khách");
        Jpanel_KhachHang.add(lbl_KhachHang, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 10, 100, 29));

        Jpanel_Menu.add(Jpanel_KhachHang, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 280, 180, 50));

        Jpanel_NhaSanXuat.setBackground(new java.awt.Color(0, 102, 102));
        Jpanel_NhaSanXuat.setToolTipText("");

        lbl_Nhom19.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        lbl_Nhom19.setForeground(new java.awt.Color(255, 255, 255));
        lbl_Nhom19.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl_Nhom19.setText("Nhóm 19");

        lbl_IconNhom19.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pic/icon/about-us.png"))); // NOI18N
        lbl_IconNhom19.setText("jLabel4");
        lbl_IconNhom19.setMaximumSize(new java.awt.Dimension(30, 30));
        lbl_IconNhom19.setMinimumSize(new java.awt.Dimension(30, 30));

        javax.swing.GroupLayout Jpanel_NhaSanXuatLayout = new javax.swing.GroupLayout(Jpanel_NhaSanXuat);
        Jpanel_NhaSanXuat.setLayout(Jpanel_NhaSanXuatLayout);
        Jpanel_NhaSanXuatLayout.setHorizontalGroup(
            Jpanel_NhaSanXuatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Jpanel_NhaSanXuatLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(lbl_IconNhom19, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lbl_Nhom19, javax.swing.GroupLayout.DEFAULT_SIZE, 61, Short.MAX_VALUE)
                .addGap(56, 56, 56))
        );
        Jpanel_NhaSanXuatLayout.setVerticalGroup(
            Jpanel_NhaSanXuatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, Jpanel_NhaSanXuatLayout.createSequentialGroup()
                .addContainerGap(20, Short.MAX_VALUE)
                .addGroup(Jpanel_NhaSanXuatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbl_IconNhom19, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_Nhom19, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18))
        );

        Jpanel_Menu.add(Jpanel_NhaSanXuat, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 680, 180, 70));

        jPanel1.setBackground(new java.awt.Color(0, 102, 102));

        jLabel1.setText("jLabel1");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, Short.MAX_VALUE)
                .addContainerGap(132, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 20, Short.MAX_VALUE)
                .addContainerGap(29, Short.MAX_VALUE))
        );

        Jpanel_Menu.add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        Jpanel_QuanLyTau.setBackground(new java.awt.Color(0, 102, 102));
        Jpanel_QuanLyTau.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lbl_QuanLyTau.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        lbl_QuanLyTau.setForeground(new java.awt.Color(255, 255, 255));
        lbl_QuanLyTau.setText("Quản Lý Tàu");
        Jpanel_QuanLyTau.add(lbl_QuanLyTau, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 10, 100, 29));
        Jpanel_QuanLyTau.add(lbl_IconQuanLyTau, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 30, 30));

        Jpanel_Menu.add(Jpanel_QuanLyTau, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 230, 180, 50));

        getContentPane().add(Jpanel_Menu, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 180, 750));

        Jpanel_Main.setBackground(new java.awt.Color(204, 204, 255));
        Jpanel_Main.setPreferredSize(new java.awt.Dimension(1355, 843));

        javax.swing.GroupLayout Jpanel_MainLayout = new javax.swing.GroupLayout(Jpanel_Main);
        Jpanel_Main.setLayout(Jpanel_MainLayout);
        Jpanel_MainLayout.setHorizontalGroup(
            Jpanel_MainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1186, Short.MAX_VALUE)
        );
        Jpanel_MainLayout.setVerticalGroup(
            Jpanel_MainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 750, Short.MAX_VALUE)
        );

        getContentPane().add(Jpanel_Main, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 0, 1186, 750));

        jMenu1.setText("Hệ Thống");
        jMenu1.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N

        JMenu_ThayMatKhau.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        JMenu_ThayMatKhau.setText("Thay Mật Khẩu");
        JMenu_ThayMatKhau.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                JMenu_ThayMatKhauMouseClicked(evt);
            }
        });
        JMenu_ThayMatKhau.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JMenu_ThayMatKhauActionPerformed(evt);
            }
        });
        jMenu1.add(JMenu_ThayMatKhau);

        JMenu_DangXuat.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        JMenu_DangXuat.setText("Đăng Xuất");
        JMenu_DangXuat.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                JMenu_DangXuatMouseClicked(evt);
            }
        });
        JMenu_DangXuat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JMenu_DangXuatActionPerformed(evt);
            }
        });
        jMenu1.add(JMenu_DangXuat);

        JMenuItem_An.setText("keyDangXuat");
        jMenu1.add(JMenuItem_An);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Trợ Giúp");
        jMenu2.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jMenu2.setMargin(new java.awt.Insets(3, 6, 3, 8));

        JMenu_GioiThieu.setText("Giới Thiệu");
        JMenu_GioiThieu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JMenu_GioiThieuActionPerformed(evt);
            }
        });
        jMenu2.add(JMenu_GioiThieu);

        JMenu_TroGiup.setText("Trợ Giúp");
        JMenu_TroGiup.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JMenu_TroGiupActionPerformed(evt);
            }
        });
        jMenu2.add(JMenu_TroGiup);

        jMenuBar1.add(jMenu2);

        JMenu_Time.setText("Time");
        JMenu_Time.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        jMenuBar1.add(JMenu_Time);

        setJMenuBar(jMenuBar1);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void JMenu_DangXuatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JMenu_DangXuatActionPerformed
        // TODO add your handling code here:
        DangNhap dangnhap = new DangNhap();
        int hoi = JOptionPane.showConfirmDialog(null, "Bạn có chắn muốn đăng xuất ?", "Chú ý !", JOptionPane.YES_NO_OPTION);
        if (hoi == JOptionPane.YES_OPTION) {
            dangnhap.setVisible(true);
            dispose();
        }

    }//GEN-LAST:event_JMenu_DangXuatActionPerformed

    private void JMenu_DangXuatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JMenu_DangXuatMouseClicked
        // TODO add your handling code here:

    }//GEN-LAST:event_JMenu_DangXuatMouseClicked

    private void JMenu_GioiThieuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JMenu_GioiThieuActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_JMenu_GioiThieuActionPerformed

    private void JMenu_ThayMatKhauMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JMenu_ThayMatKhauMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_JMenu_ThayMatKhauMouseClicked

    private void JMenu_ThayMatKhauActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JMenu_ThayMatKhauActionPerformed
        ThayDoiMatKhau_GUI thayDoiMatKhay_Gui = new ThayDoiMatKhau_GUI();
        thayDoiMatKhay_Gui.setVisible(true);

        // TODO add your handling code here:
    }//GEN-LAST:event_JMenu_ThayMatKhauActionPerformed

    private void JMenu_TroGiupActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JMenu_TroGiupActionPerformed

        try {
            System.out.println("Start..");
            File file = new java.io.File("src/HTMLFile/HELPPAGE/main.html").getAbsoluteFile();
            Desktop.getDesktop().open(file);
            System.out.println("End..");
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_JMenu_TroGiupActionPerformed



    private class PanelButtonMouseAdapter extends MouseAdapter {

        JPanel panel;

        public PanelButtonMouseAdapter(JPanel panel) {
            this.panel = panel;
        }

        @Override
        public void mouseEntered(MouseEvent e) {
//			panel.setBackground(new Color(112,128,144));
        }

        @Override
        public void mouseExited(MouseEvent e) {
//            panel.setBackground(new Color(0,102,0));
        }

        @Override
        public void mousePressed(MouseEvent e) {
//            panel.setBackground(new Color(60, 179, 113));
        }

        @Override
        public void mouseReleased(MouseEvent e) {
//            panel.setBackground(new Color(112, 128, 144));
//               panel.setBackground(new Color(60, 179, 113));
        }
    }
    //

    private TrangChu_JPanel TrangChu_Panel;

    private BanVe_JPanel BanHang_Panel;
    private QuanLyTau_JPanel QuanLyTau_Panel;
    private HoaDon_JPanel HoaDon_Panel;
    private TrangChu_JPanel DoiTra_Panel;
    private KhachHang_JPanel KhachHang_Panel;
    private ThongKe_JPanel ThongKe_Panel;
    private NhanVien_JPanel NhanVien_Panel;
    private NhaSanXuat_JPanel NhaSanXuat_Panel;
    private LichTrinh_JPanel LichTrinh_Panel;
//    private SanPham_JPanel SanPham_Panel;


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem JMenuItem_An;
    private javax.swing.JMenuItem JMenu_DangXuat;
    private javax.swing.JMenuItem JMenu_GioiThieu;
    private javax.swing.JMenuItem JMenu_ThayMatKhau;
    private javax.swing.JMenu JMenu_Time;
    private javax.swing.JMenuItem JMenu_TroGiup;
    private javax.swing.JPanel Jpanel_BanHang;
    private javax.swing.JPanel Jpanel_DoiTra;
    private javax.swing.JPanel Jpanel_HoaDon;
    private javax.swing.JPanel Jpanel_KhachHang;
    private javax.swing.JPanel Jpanel_LichTrinh;
    private javax.swing.JPanel Jpanel_Main;
    private javax.swing.JPanel Jpanel_Menu;
    private javax.swing.JPanel Jpanel_NhaSanXuat;
    private javax.swing.JPanel Jpanel_NhanVien;
    private javax.swing.JPanel Jpanel_QuanLyTau;
    private javax.swing.JPanel Jpanel_ThongKe;
    private javax.swing.JPanel Jpanel_TrangChu;
    private javax.swing.JPanel Jpanel_Users;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel jlb_name;
    private javax.swing.JLabel lbl_BanHang;
    private javax.swing.JLabel lbl_ChucVu;
    private javax.swing.JLabel lbl_DoiTra;
    private javax.swing.JLabel lbl_IconDoiTra;
    private javax.swing.JLabel lbl_IconHoaDon;
    private javax.swing.JLabel lbl_IconKhachHang;
    private javax.swing.JLabel lbl_IconLichTrinh;
    private javax.swing.JLabel lbl_IconNhanVien;
    private javax.swing.JLabel lbl_IconNhom19;
    private javax.swing.JLabel lbl_IconQuanLyTau;
    private javax.swing.JLabel lbl_IconThongKe;
    private javax.swing.JLabel lbl_IconTimTau;
    private javax.swing.JLabel lbl_IconTrangChu;
    private javax.swing.JLabel lbl_KhachHang;
    private javax.swing.JLabel lbl_NhanVien;
    private javax.swing.JLabel lbl_Nhom19;
    private javax.swing.JLabel lbl_QuanLyTau;
    private javax.swing.JLabel lbl_ThongKe;
    private javax.swing.JLabel lbl_TrangChu;
    private javax.swing.JLabel lbl_Users;
    private javax.swing.JLabel lbl_lichTrinh;
    // End of variables declaration//GEN-END:variables
}
