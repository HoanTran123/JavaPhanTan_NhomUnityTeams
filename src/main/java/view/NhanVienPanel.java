package view;

import dao.impl.NhanVienDAO;
import entity.NhanVien;
import util.HibernateUtil;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

public class NhanVienPanel extends JPanel {
    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField txtMaNV, txtHoTen, txtSDT, txtEmail, txtNamSinh, txtNgayVaoLam, txtChucVu;
    private JComboBox<String> cboGioiTinh;
    private NhanVienDAO nhanVienDao;
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");

    public NhanVienPanel() {
        try {
            nhanVienDao = new NhanVienDAO();
            nhanVienDao.setEntityManager(HibernateUtil.getEntityManager());
        } catch (Exception e) {
            showError("Lỗi khởi tạo NhanVienDAO: " + e.getMessage());
        }

        setLayout(new BorderLayout(0, 20));
        setBackground(new Color(248, 249, 250));

        add(createTitlePanel(), BorderLayout.NORTH);
        add(createMainPanel(), BorderLayout.CENTER);

        loadNhanVien();
    }

    private JPanel createTitlePanel() {
        JLabel lblTitle = new JLabel("QUẢN LÝ NHÂN VIÊN");
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

        JPanel gridPanel = new JPanel(new GridLayout(2, 4, 15, 15));
        gridPanel.setBackground(Color.WHITE);

        txtMaNV = createStyledTextField(false);
        txtHoTen = createStyledTextField(true);
        txtSDT = createStyledTextField(true);
        txtEmail = createStyledTextField(true);
        cboGioiTinh = createStyledComboBox(new String[]{"Nam", "Nữ", "Không xác định"});
        txtNamSinh = createStyledTextField(true);
        txtNgayVaoLam = createStyledTextField(false);
        txtChucVu = createStyledTextField(true);

        JLabel[] labels = {
                new JLabel("Mã NV:"), new JLabel("Họ Tên:"), new JLabel("SĐT:"),
                new JLabel("Email:"), new JLabel("Giới tính:"), new JLabel("Năm sinh:"),
                new JLabel("Ngày vào làm:"), new JLabel("Chức vụ:")
        };

        for (JLabel label : labels) {
            label.setFont(new Font("Segoe UI", Font.BOLD, 14));
            label.setForeground(new Color(70, 70, 70));
        }

        gridPanel.add(createFieldPanel(labels[0], txtMaNV));
        gridPanel.add(createFieldPanel(labels[1], txtHoTen));
        gridPanel.add(createFieldPanel(labels[2], txtSDT));
        gridPanel.add(createFieldPanel(labels[3], txtEmail));
        gridPanel.add(createFieldPanel(labels[4], cboGioiTinh));
        gridPanel.add(createFieldPanel(labels[5], txtNamSinh));
        gridPanel.add(createFieldPanel(labels[6], txtNgayVaoLam));
        gridPanel.add(createFieldPanel(labels[7], txtChucVu));

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
                new String[]{"Mã NV", "Họ Tên", "SĐT", "Email", "Giới Tính", "Năm Sinh", "Ngày Vào Làm", "Chức Vụ"},
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

        btnLamMoi.addActionListener(e -> loadNhanVien());
        btnThem.addActionListener(e -> addNhanVien());
        btnSua.addActionListener(e -> updateNhanVien());
        btnXoa.addActionListener(e -> deleteNhanVien());

        buttonPanel.add(btnThem);
        buttonPanel.add(btnSua);
        buttonPanel.add(btnXoa);
        buttonPanel.add(btnLamMoi);

        return buttonPanel;
    }

    private void addNhanVien() {
        try {
            // Validate input fields
            String hoTen = txtHoTen.getText().trim();
            String sdt = txtSDT.getText().trim();
            String email = txtEmail.getText().trim();
            String gioiTinh = cboGioiTinh.getSelectedItem().toString();
            String namSinhText = txtNamSinh.getText().trim();
            String chucVu = txtChucVu.getText().trim();

            if (hoTen.isEmpty() || sdt.isEmpty() || email.isEmpty() || namSinhText.isEmpty() || chucVu.isEmpty()) {
                showError("Vui lòng nhập đầy đủ thông tin!");
                return;
            }

            // Validate phone number
            if (!sdt.matches("\\d{10,11}")) {
                showError("Số điện thoại phải có 10-11 chữ số!");
                return;
            }

            // Validate email
            String emailRegex = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
            if (!Pattern.matches(emailRegex, email)) {
                showError("Email không hợp lệ!");
                return;
            }

            // Validate birth year
            int namSinh;
            try {
                namSinh = Integer.parseInt(namSinhText);
                int currentYear = java.time.Year.now().getValue();
                if (namSinh < 1900 || namSinh > currentYear) {
                    showError("Năm sinh phải nằm trong khoảng từ 1900 đến " + currentYear + "!");
                    return;
                }
            } catch (NumberFormatException e) {
                showError("Năm sinh phải là số nguyên hợp lệ!");
                return;
            }

            // Create and populate NhanVien object
            NhanVien nv = new NhanVien();
            nv.setIdNV(generateEmployeeId()); // Generate unique ID
            nv.setHoTen(hoTen);
            nv.setSdt(sdt);
            nv.setEmail(email);
            nv.setGioiTinh(gioiTinh);
            nv.setNamSinh(namSinh);
            nv.setNgayVaoLam(new Date()); // Set current date
            nv.setChucVu(chucVu);

            // Save to database
            if (nhanVienDao.save(nv)) {
                loadNhanVien(); // Reload table data
                showSuccess("Thêm nhân viên thành công!");
            } else {
                showError("Thêm nhân viên thất bại!");
            }
        } catch (Exception e) {
            showError("Lỗi khi thêm nhân viên: " + e.getMessage());
        }
    }

    private String generateEmployeeId() {
        return "NV" + System.currentTimeMillis();
    }

    private void updateNhanVien() {
        try {
            int selectedRow = table.getSelectedRow();
            if (selectedRow == -1) {
                showError("Vui lòng chọn nhân viên để cập nhật!");
                return;
            }

            String maNV = txtMaNV.getText();
            NhanVien nv = nhanVienDao.getById(maNV);
            if (nv == null) {
                showError("Nhân viên không tồn tại!");
                return;
            }

            nv.setHoTen(txtHoTen.getText());
            nv.setSdt(txtSDT.getText());
            nv.setEmail(txtEmail.getText());
            nv.setGioiTinh(cboGioiTinh.getSelectedItem().toString());
            nv.setNamSinh(Integer.parseInt(txtNamSinh.getText()));
            nv.setNgayVaoLam(DATE_FORMAT.parse(txtNgayVaoLam.getText()));
            nv.setChucVu(txtChucVu.getText());

            if (nhanVienDao.update(nv)) {
                loadNhanVien();
                showSuccess("Cập nhật nhân viên thành công!");
            } else {
                showError("Cập nhật nhân viên thất bại!");
            }
        } catch (Exception e) {
            showError("Lỗi khi cập nhật nhân viên: " + e.getMessage());
        }
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

    private void deleteNhanVien() {
        try {
            int selectedRow = table.getSelectedRow();
            if (selectedRow == -1) {
                showError("Vui lòng chọn nhân viên để xóa!");
                return;
            }

            String maNV = tableModel.getValueAt(selectedRow, 0).toString();
            NhanVien nv = nhanVienDao.getById(maNV);
            if (nv == null) {
                showError("Nhân viên không tồn tại!");
                return;
            }

            int confirm = JOptionPane.showConfirmDialog(this,
                    "Bạn có chắc chắn muốn xóa nhân viên này? Tài khoản liên quan cũng sẽ bị xóa!",
                    "Xác nhận xóa",
                    JOptionPane.YES_NO_OPTION);
            if (confirm != JOptionPane.YES_OPTION) {
                return;
            }

            if (nhanVienDao.delete(nv)) {
                loadNhanVien();
                showSuccess("Xóa nhân viên thành công!");
            } else {
                showError("Xóa nhân viên thất bại!");
            }
        } catch (Exception e) {
            showError("Lỗi khi xóa nhân viên: " + e.getMessage());
        }
    }

    private void loadNhanVien() {
        try {
            tableModel.setRowCount(0);
            List<NhanVien> list = nhanVienDao.getAll();
            for (NhanVien nv : list) {
                tableModel.addRow(new Object[]{
                        nv.getIdNV(),
                        nv.getHoTen(),
                        nv.getSdt(),
                        nv.getEmail(),
                        nv.getGioiTinh(),
                        nv.getNamSinh(),
                        nv.getNgayVaoLam() != null ? DATE_FORMAT.format(nv.getNgayVaoLam()) : "",
                        nv.getChucVu()
                });
            }
            clearForm();
        } catch (Exception e) {
            showError("Lỗi khi tải dữ liệu: " + e.getMessage());
        }
    }

    private void populateFormFromTable() {
        int selectedRow = table.getSelectedRow();
        txtMaNV.setText(tableModel.getValueAt(selectedRow, 0).toString());
        txtHoTen.setText(tableModel.getValueAt(selectedRow, 1).toString());
        txtSDT.setText(tableModel.getValueAt(selectedRow, 2).toString());
        txtEmail.setText(tableModel.getValueAt(selectedRow, 3).toString());
        cboGioiTinh.setSelectedItem(tableModel.getValueAt(selectedRow, 4).toString());
        txtNamSinh.setText(tableModel.getValueAt(selectedRow, 5).toString());
        txtNgayVaoLam.setText(tableModel.getValueAt(selectedRow, 6).toString());
        txtChucVu.setText(tableModel.getValueAt(selectedRow, 7).toString());
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Lỗi", JOptionPane.ERROR_MESSAGE);
    }

    private void showSuccess(String message) {
        JOptionPane.showMessageDialog(this, message, "Thành công", JOptionPane.INFORMATION_MESSAGE);
    }

    private void clearForm() {
        txtMaNV.setText("");
        txtHoTen.setText("");
        txtSDT.setText("");
        txtEmail.setText("");
        cboGioiTinh.setSelectedIndex(0);
        txtNamSinh.setText("");
        txtNgayVaoLam.setText("");
        txtChucVu.setText("");
    }
}