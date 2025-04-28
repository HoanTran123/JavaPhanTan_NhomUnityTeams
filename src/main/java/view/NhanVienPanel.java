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

    private void addNhanVien() {
        // Similar to addKhachHang() in KhachHangPanel
    }

    private void updateNhanVien() {
        // Similar to updateKhachHang() in KhachHangPanel
    }

    private void deleteNhanVien() {
        // Similar to deleteKhachHang() in KhachHangPanel
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

    private void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Lỗi", JOptionPane.ERROR_MESSAGE);
    }
}