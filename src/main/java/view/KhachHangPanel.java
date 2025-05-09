package view;

import dao.impl.KhachHangDAO;
import entity.KhachHang;
import util.HibernateUtil;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

public class KhachHangPanel extends JPanel {
    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField txtMaKH, txtHoTen, txtSDT, txtEmail, txtNgayThamGia, txtTongChiTieu, txtSearch;
    private JComboBox<String> cboGioiTinh;
    private KhachHangDAO khachHangDao;
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");

    public KhachHangPanel() {
        try {
            khachHangDao = new KhachHangDAO();
            khachHangDao.setEntityManager(HibernateUtil.getEntityManager());
        } catch (Exception e) {
            showError("Lỗi khởi tạo KhachHangDAO: " + e.getMessage());
        }

        setLayout(new BorderLayout(0, 20));
        setBackground(new Color(248, 249, 250));

        add(createTitlePanel(), BorderLayout.NORTH);
        add(createMainPanel(), BorderLayout.CENTER);

        loadKhachHang();
    }

    private JPanel createTitlePanel() {
        JLabel lblTitle = new JLabel("QUẢN LÝ KHÁCH HÀNG");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblTitle.setForeground(new Color(0, 102, 204));

        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        titlePanel.setBackground(new Color(248, 249, 250));
        titlePanel.add(lblTitle);

        return titlePanel;
    }

    private JPanel createMainPanel() {
        JPanel mainPanel = new JPanel(new BorderLayout(0, 15));
        mainPanel.setBackground(new Color(248, 249, 250));

        mainPanel.add(createFormPanel(), BorderLayout.NORTH);
        mainPanel.add(createTablePanel(), BorderLayout.CENTER);
        mainPanel.add(createButtonPanel(), BorderLayout.SOUTH);

        return mainPanel;
    }

    private JPanel createFormPanel() {
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(BorderFactory.createCompoundBorder(
                new MatteBorder(0, 0, 1, 0, new Color(230, 230, 230)),
                new EmptyBorder(20, 25, 20, 25)
        ));

        // Search panel
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 5));
        searchPanel.setBackground(Color.WHITE);

        JLabel lblSearch = new JLabel("Tìm kiếm:");
        lblSearch.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblSearch.setForeground(new Color(70, 70, 70));

        txtSearch = createStyledTextField(true);
        txtSearch.setPreferredSize(new Dimension(700, 40));

        JButton btnSearch = createActionButton("Tìm", new Color(255, 193, 7));
        btnSearch.addActionListener(e -> searchKhachHang());

        searchPanel.add(lblSearch);
        searchPanel.add(txtSearch);
        searchPanel.add(btnSearch);

        // Form fields panel
        JPanel gridPanel = new JPanel(new GridLayout(2, 4, 15, 15));
        gridPanel.setBackground(Color.WHITE);

        txtMaKH = createStyledTextField(false);
        txtHoTen = createStyledTextField(true);
        txtSDT = createStyledTextField(true);
        txtEmail = createStyledTextField(true);
        cboGioiTinh = createStyledComboBox(new String[]{"Nam", "Nữ", "Không xác định"});
        txtNgayThamGia = createStyledTextField(false);
        txtTongChiTieu = createStyledTextField(true);

        JLabel[] labels = {
                new JLabel("Mã KH:"), new JLabel("Họ Tên:"), new JLabel("SĐT:"),
                new JLabel("Email:"), new JLabel("Giới tính:"), new JLabel("Ngày tham gia:"),
                new JLabel("Tổng chi tiêu:")
        };

        for (JLabel label : labels) {
            label.setFont(new Font("Segoe UI", Font.BOLD, 14));
            label.setForeground(new Color(70, 70, 70));
        }

        gridPanel.add(createFieldPanel(labels[0], txtMaKH));
        gridPanel.add(createFieldPanel(labels[1], txtHoTen));
        gridPanel.add(createFieldPanel(labels[2], txtSDT));
        gridPanel.add(createFieldPanel(labels[3], txtEmail));
        gridPanel.add(createFieldPanel(labels[4], cboGioiTinh));
        gridPanel.add(createFieldPanel(labels[5], txtNgayThamGia));
        gridPanel.add(createFieldPanel(labels[6], txtTongChiTieu));
        gridPanel.add(new JLabel());

        formPanel.add(searchPanel);
        formPanel.add(Box.createVerticalStrut(15));
        formPanel.add(gridPanel);
        return formPanel;
    }

    private JPanel createFieldPanel(JLabel label, JComponent field) {
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        panel.setBackground(Color.WHITE);
        panel.add(label, BorderLayout.NORTH);
        panel.add(field, BorderLayout.CENTER);
        return panel;
    }

    private JTextField createStyledTextField(boolean editable) {
        JTextField field = new JTextField();
        field.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        field.setBorder(BorderFactory.createCompoundBorder(
                new MatteBorder(1, 1, 1, 1, new Color(220, 220, 220)),
                new EmptyBorder(10, 12, 10, 12)
        ));
        field.setEditable(editable);
        return field;
    }

    private JComboBox<String> createStyledComboBox(String[] items) {
        JComboBox<String> combo = new JComboBox<>(items);
        combo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        combo.setBorder(BorderFactory.createCompoundBorder(
                new MatteBorder(1, 1, 1, 1, new Color(220, 220, 220)),
                new EmptyBorder(5, 10, 5, 10)
        ));
        combo.setBackground(Color.WHITE);
        return combo;
    }

    private JScrollPane createTablePanel() {
        tableModel = new DefaultTableModel(
                new String[]{"Mã KH", "Họ Tên", "SĐT", "Email", "Giới Tính", "Ngày Tham Gia", "Tổng Chi Tiêu"},
                0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table = new JTable(tableModel);
        table.setRowHeight(40);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        table.getTableHeader().setBackground(new Color(0, 102, 204));
        table.getTableHeader().setForeground(Color.WHITE);

        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && table.getSelectedRow() != -1) {
                populateFormFromTable();
            }
        });

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createCompoundBorder(
                new MatteBorder(1, 0, 0, 0, new Color(230, 230, 230)),
                new EmptyBorder(0, 0, 0, 0)
        ));
        return scrollPane;
    }

    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 15));
        buttonPanel.setBackground(new Color(248, 249, 250));

        JButton btnThem = createActionButton("Thêm", new Color(40, 167, 69));
        JButton btnSua = createActionButton("Sửa", new Color(0, 123, 255));
        JButton btnXoa = createActionButton("Xóa", new Color(220, 53, 69));
        JButton btnLamMoi = createActionButton("Làm mới", new Color(108, 117, 125));

        btnLamMoi.addActionListener(e -> {
            txtSearch.setText("");
            loadKhachHang();
        });
        btnThem.addActionListener(e -> addKhachHang());
        btnSua.addActionListener(e -> updateKhachHang());
        btnXoa.addActionListener(e -> deleteKhachHang());

        buttonPanel.add(btnThem);
        buttonPanel.add(btnSua);
        buttonPanel.add(btnXoa);
        buttonPanel.add(btnLamMoi);

        return buttonPanel;
    }

    private JButton createActionButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setForeground(Color.WHITE);
        button.setBackground(bgColor);
        button.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(bgColor.darker(), 1),
                new EmptyBorder(8, 20, 8, 20)
        ));
        button.setFocusPainted(false);
        return button;
    }

    private void loadKhachHang() {
        try {
            tableModel.setRowCount(0);
            List<KhachHang> list = khachHangDao.getAll();
            for (KhachHang kh : list) {
                tableModel.addRow(new Object[]{
                        kh.getIdKH(),
                        kh.getHoTen(),
                        kh.getSdt(),
                        kh.getEmail(),
                        kh.getGioiTinh() == null ? "Không xác định" : (kh.getGioiTinh() ? "Nam" : "Nữ"),
                        kh.getNgayThamGia() != null ? DATE_FORMAT.format(kh.getNgayThamGia()) : "",
                        kh.getTongChiTieu()
                });
            }
            clearForm();
        } catch (Exception e) {
            showError("Lỗi khi tải dữ liệu: " + e.getMessage());
        }
    }

    private void searchKhachHang() {
        try {
            String searchText = txtSearch.getText().trim().toLowerCase();
            tableModel.setRowCount(0);

            List<KhachHang> list = khachHangDao.getAll();
            for (KhachHang kh : list) {
                boolean matches = searchText.isEmpty() ||
                        kh.getHoTen().toLowerCase().contains(searchText) ||
                        (kh.getSdt() != null && kh.getSdt().toLowerCase().contains(searchText)) ||
                        (kh.getEmail() != null && kh.getEmail().toLowerCase().contains(searchText));

                if (matches) {
                    tableModel.addRow(new Object[]{
                            kh.getIdKH(),
                            kh.getHoTen(),
                            kh.getSdt(),
                            kh.getEmail(),
                            kh.getGioiTinh() == null ? "Không xác định" : (kh.getGioiTinh() ? "Nam" : "Nữ"),
                            kh.getNgayThamGia() != null ? DATE_FORMAT.format(kh.getNgayThamGia()) : "",
                            kh.getTongChiTieu()
                    });
                }
            }

            if (tableModel.getRowCount() == 0) {
                showWarning("Không tìm thấy khách hàng phù hợp!");
            }
        } catch (Exception e) {
            showError("Lỗi khi tìm kiếm: " + e.getMessage());
        }
    }

    private String generateCustomerId() {
        try {
            int count = khachHangDao.countTotal() + 1;
            return "KH" + String.format("%04d", count);
        } catch (Exception e) {
            return "KH" + String.format("%04d", (int) (Math.random() * 10000));
        }
    }

    private void addKhachHang() {
        try {
            String hoTen = txtHoTen.getText().trim();
            String sdt = txtSDT.getText().trim();
            String email = txtEmail.getText().trim();
            String tongChiTieuText = txtTongChiTieu.getText().trim();

            // Validate input
            if (hoTen.isEmpty()) {
                showWarning("Vui lòng nhập họ tên!");
                return;
            }
            if (!sdt.isEmpty() && !Pattern.matches("\\d{10,11}", sdt)) {
                showWarning("Số điện thoại phải có 10-11 chữ số!");
                return;
            }
            if (!email.isEmpty() && !Pattern.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$", email)) {
                showWarning("Email không hợp lệ!");
                return;
            }

            KhachHang kh = new KhachHang();
            kh.setIdKH(generateCustomerId());
            kh.setHoTen(hoTen);
            kh.setSdt(sdt);
            kh.setEmail(email);
            String gioiTinh = (String) cboGioiTinh.getSelectedItem();
            kh.setGioiTinh(gioiTinh.equals("Không xác định") ? null : gioiTinh.equals("Nam"));
            kh.setNgayThamGia(new Date());
            try {
                kh.setTongChiTieu(tongChiTieuText.isEmpty() ? 0.0 : Double.parseDouble(tongChiTieuText));
            } catch (NumberFormatException e) {
                kh.setTongChiTieu(0.0);
            }

            if (khachHangDao.add(kh)) {
                showSuccess("Thêm khách hàng thành công!");
                loadKhachHang();
            } else {
                showError("Thêm khách hàng thất bại!");
            }
        } catch (Exception e) {
            showError("Lỗi: " + e.getMessage());
        }
    }

    private void updateKhachHang() {
        try {
            int selectedRow = table.getSelectedRow();
            if (selectedRow == -1) {
                showWarning("Vui lòng chọn khách hàng để sửa!");
                return;
            }

            String idKH = tableModel.getValueAt(selectedRow, 0).toString();
            KhachHang kh = khachHangDao.getById(idKH);
            if (kh == null) {
                showError("Khách hàng không tồn tại!");
                return;
            }

            String hoTen = txtHoTen.getText().trim();
            String sdt = txtSDT.getText().trim();
            String email = txtEmail.getText().trim();
            String tongChiTieuText = txtTongChiTieu.getText().trim();

            // Validate input
            if (hoTen.isEmpty()) {
                showWarning("Vui lòng nhập họ tên!");
                return;
            }
            if (!sdt.isEmpty() && !Pattern.matches("\\d{10,11}", sdt)) {
                showWarning("Số điện thoại phải có 10-11 chữ số!");
                return;
            }
            if (!email.isEmpty() && !Pattern.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$", email)) {
                showWarning("Email không hợp lệ!");
                return;
            }

            kh.setHoTen(hoTen);
            kh.setSdt(sdt);
            kh.setEmail(email);
            String gioiTinh = (String) cboGioiTinh.getSelectedItem();
            kh.setGioiTinh(gioiTinh.equals("Không xác định") ? null : gioiTinh.equals("Nam"));
            try {
                kh.setTongChiTieu(tongChiTieuText.isEmpty() ? 0.0 : Double.parseDouble(tongChiTieuText));
            } catch (NumberFormatException e) {
                kh.setTongChiTieu(0.0);
            }

            if (khachHangDao.update(kh)) {
                showSuccess("Cập nhật khách hàng thành công!");
                loadKhachHang();
            } else {
                showError("Cập nhật khách hàng thất bại!");
            }
        } catch (Exception e) {
            showError("Lỗi: " + e.getMessage());
        }
    }

    private void deleteKhachHang() {
        try {
            int selectedRow = table.getSelectedRow();
            if (selectedRow == -1) {
                showWarning("Vui lòng chọn khách hàng để xóa!");
                return;
            }

            int confirm = JOptionPane.showConfirmDialog(this,
                    "Bạn có chắc chắn muốn xóa khách hàng này? Các hóa đơn và phiếu đặt hàng liên quan cũng sẽ bị xóa!",
                    "Xác nhận xóa",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE);

            if (confirm != JOptionPane.YES_OPTION) {
                return;
            }

            String idKH = tableModel.getValueAt(selectedRow, 0).toString();
            KhachHang kh = khachHangDao.getById(idKH);
            if (kh == null) {
                showError("Khách hàng không tồn tại!");
                return;
            }

            khachHangDao.deleteRelatedRecords(idKH);
            if (khachHangDao.delete(kh)) {
                showSuccess("Xóa khách hàng thành công!");
                loadKhachHang();
            } else {
                showError("Xóa khách hàng thất bại!");
            }
        } catch (Exception e) {
            showError("Lỗi: " + e.getMessage());
        }
    }

    private void populateFormFromTable() {
        int selectedRow = table.getSelectedRow();
        txtMaKH.setText(tableModel.getValueAt(selectedRow, 0).toString());
        txtHoTen.setText(tableModel.getValueAt(selectedRow, 1).toString());
        txtSDT.setText(tableModel.getValueAt(selectedRow, 2).toString());
        txtEmail.setText(tableModel.getValueAt(selectedRow, 3).toString());
        cboGioiTinh.setSelectedItem(tableModel.getValueAt(selectedRow, 4).toString());
        txtNgayThamGia.setText(tableModel.getValueAt(selectedRow, 5).toString());
        txtTongChiTieu.setText(tableModel.getValueAt(selectedRow, 6).toString());
    }

    private void clearForm() {
        txtMaKH.setText("");
        txtHoTen.setText("");
        txtSDT.setText("");
        txtEmail.setText("");
        cboGioiTinh.setSelectedIndex(0);
        txtNgayThamGia.setText("");
        txtTongChiTieu.setText("0");
    }

    private void showSuccess(String message) {
        JOptionPane.showMessageDialog(this, message, "Thành công", JOptionPane.INFORMATION_MESSAGE);
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Lỗi", JOptionPane.ERROR_MESSAGE);
    }

    private void showWarning(String message) {
        JOptionPane.showMessageDialog(this, message, "Cảnh báo", JOptionPane.WARNING_MESSAGE);
    }
}