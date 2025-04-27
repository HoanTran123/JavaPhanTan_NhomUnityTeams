package view;

import dao.impl.ChiTietHoaDonDAO;
import dao.impl.HoaDonDAO;
import dao.impl.KhachHangDAO;
import dao.impl.NhanVienDAO;
import dao.impl.ThuocDAO;
import entity.ChiTietHoaDon;
import entity.HoaDon;
import entity.KhachHang;
import entity.NhanVien;
import entity.Thuoc;
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.HibernateUtil;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.util.Date;
import java.util.UUID;

public class BanThuocDialog extends JDialog {
    private JComboBox<Thuoc> cbThuoc;
    private JComboBox<KhachHang> cbKhachHang;
    private JComboBox<NhanVien> cbNhanVien;
    private JTextField txtSoLuong;
    private ThuocDAO thuocDao;
    private HoaDonDAO hoaDonDao;
    private ChiTietHoaDonDAO chiTietHoaDonDao;
    private KhachHangDAO khachHangDao;
    private NhanVienDAO nhanVienDao;
    private Runnable refreshHoaDonCallback;

    public BanThuocDialog(JFrame parent, Runnable refreshHoaDonCallback) {
        super(parent, "Bán Thuốc", true);
        this.refreshHoaDonCallback = refreshHoaDonCallback;
        setSize(400, 400);
        setLocationRelativeTo(parent);

        try {
            thuocDao = new ThuocDAO();
            thuocDao.setEntityManager(HibernateUtil.getEntityManager());
            hoaDonDao = new HoaDonDAO();
            hoaDonDao.setEntityManager(HibernateUtil.getEntityManager());
            chiTietHoaDonDao = new ChiTietHoaDonDAO();
            chiTietHoaDonDao.setEntityManager(HibernateUtil.getEntityManager());
            khachHangDao = new KhachHangDAO();
            khachHangDao.setEntityManager(HibernateUtil.getEntityManager());
            nhanVienDao = new NhanVienDAO();
            nhanVienDao.setEntityManager(HibernateUtil.getEntityManager());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi khởi tạo DAO: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }

        setLayout(new BorderLayout(10, 10));
        add(createFormPanel(), BorderLayout.CENTER);
        add(createButtonPanel(), BorderLayout.SOUTH);
    }

    private JPanel createFormPanel() {
        JPanel formPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel lblThuoc = new JLabel("Chọn Thuốc:");
        JLabel lblKhachHang = new JLabel("Chọn Khách Hàng:");
        JLabel lblNhanVien = new JLabel("Nhân Viên:");
        JLabel lblSoLuong = new JLabel("Số Lượng:");

        cbThuoc = new JComboBox<>();
        cbKhachHang = new JComboBox<>();
        cbKhachHang.addItem(null); // Cho phép không chọn khách hàng
        cbNhanVien = new JComboBox<>();
        txtSoLuong = new JTextField();

        try {
            for (Thuoc thuoc : thuocDao.getAll()) {
                cbThuoc.addItem(thuoc);
            }
            for (KhachHang kh : khachHangDao.getAll()) {
                cbKhachHang.addItem(kh);
            }
            for (NhanVien nv : nhanVienDao.getAll()) {
                cbNhanVien.addItem(nv);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi khi tải danh sách: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }

        formPanel.add(lblThuoc);
        formPanel.add(cbThuoc);
        formPanel.add(lblKhachHang);
        formPanel.add(cbKhachHang);
        formPanel.add(lblNhanVien);
        formPanel.add(cbNhanVien);
        formPanel.add(lblSoLuong);
        formPanel.add(txtSoLuong);

        return formPanel;
    }

    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));

        JButton btnThanhToan = new JButton("Thanh Toán");
        btnThanhToan.addActionListener(e -> handleThanhToan());

        JButton btnHuy = new JButton("Hủy");
        btnHuy.addActionListener(e -> dispose());

        buttonPanel.add(btnThanhToan);
        buttonPanel.add(btnHuy);

        return buttonPanel;
    }

    private void handleThanhToan() {
        Transaction transaction = null;
        Session session = null;
        try {
            Thuoc selectedThuoc = (Thuoc) cbThuoc.getSelectedItem();
            KhachHang selectedKhachHang = (KhachHang) cbKhachHang.getSelectedItem();
            NhanVien selectedNhanVien = (NhanVien) cbNhanVien.getSelectedItem();
            String soLuongText = txtSoLuong.getText().trim();

            // Validate input
            if (selectedThuoc == null || selectedNhanVien == null || soLuongText.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn thuốc, nhân viên và nhập số lượng.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int soLuong;
            try {
                soLuong = Integer.parseInt(soLuongText);
                if (soLuong <= 0) {
                    JOptionPane.showMessageDialog(this, "Số lượng phải lớn hơn 0.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Số lượng không hợp lệ.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Check stock and expiration date
            if (selectedThuoc.getSoLuongTon() < soLuong) {
                JOptionPane.showMessageDialog(this, "Số lượng tồn không đủ.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (selectedThuoc.getHanSuDung().isBefore(LocalDate.now())) {
                JOptionPane.showMessageDialog(this, "Thuốc đã hết hạn sử dụng.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Confirm transaction
            double tongTien = selectedThuoc.getDonGia() * soLuong;
            String khachHangInfo = selectedKhachHang != null ? selectedKhachHang.getHoTen() : "Không có";
            int confirm = JOptionPane.showConfirmDialog(this,
                    String.format("Xác nhận thanh toán?\nThuốc: %s\nKhách hàng: %s\nNhân viên: %s\nSố lượng: %d\nTổng tiền: %.2f",
                            selectedThuoc.getTenThuoc(), khachHangInfo, selectedNhanVien.getHoTen(), soLuong, tongTien),
                    "Xác nhận", JOptionPane.YES_NO_OPTION);
            if (confirm != JOptionPane.YES_OPTION) {
                return;
            }

            // Start Hibernate transaction
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();

            // Create HoaDon
            HoaDon hoaDon = new HoaDon();
            hoaDon.setIdHD("HD" + UUID.randomUUID().toString().substring(0, 8));
            hoaDon.setThoiGian(new Date());
            hoaDon.setTongTien(tongTien);
            hoaDon.setKhachHang(selectedKhachHang);
            hoaDon.setNhanVien(selectedNhanVien);

            if (!hoaDonDao.add(hoaDon)) {
                throw new RuntimeException("Không thể tạo hóa đơn.");
            }

            // Create ChiTietHoaDon
            ChiTietHoaDon chiTiet = new ChiTietHoaDon();
            chiTiet.setHoaDon(hoaDon);
            chiTiet.setThuoc(selectedThuoc);
            chiTiet.setSoLuong(soLuong);
            chiTiet.setDonGia(selectedThuoc.getDonGia());

            if (!chiTietHoaDonDao.add(chiTiet)) {
                throw new RuntimeException("Không thể thêm chi tiết hóa đơn.");
            }

            // Update stock
            selectedThuoc.setSoLuongTon(selectedThuoc.getSoLuongTon() - soLuong);
            if (!thuocDao.update(selectedThuoc)) {
                throw new RuntimeException("Không thể cập nhật số lượng tồn.");
            }

            // Update tongChiTieu of KhachHang
            if (selectedKhachHang != null) {
                double newTongChiTieu = (selectedKhachHang.getTongChiTieu() != null ? selectedKhachHang.getTongChiTieu() : 0) + tongTien;
                selectedKhachHang.setTongChiTieu(newTongChiTieu);
                if (!khachHangDao.update(selectedKhachHang)) {
                    throw new RuntimeException("Không thể cập nhật tổng chi tiêu của khách hàng.");
                }
            }

            // Commit transaction
            transaction.commit();

            JOptionPane.showMessageDialog(this, "Thanh toán thành công!");
            if (refreshHoaDonCallback != null) {
                refreshHoaDonCallback.run();
            }
            dispose();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            JOptionPane.showMessageDialog(this, "Lỗi khi thanh toán: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
}