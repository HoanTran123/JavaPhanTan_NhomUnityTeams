package view;

import dao.impl.NhaCungCapDAO;
import entity.NhaCungCap;
import util.HibernateUtil;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;
import java.awt.*;
import java.util.List;

public class NhaCungCapPanel extends JPanel {
    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField txtMaNCC, txtTenNCC, txtSDT, txtDiaChi;
    private NhaCungCapDAO nhaCungCapDao;

    public NhaCungCapPanel() {
        try {
            nhaCungCapDao = new NhaCungCapDAO();
            nhaCungCapDao.setEntityManager(HibernateUtil.getEntityManager());
        } catch (Exception e) {
            showError("Lỗi khởi tạo NhaCungCapDAO: " + e.getMessage());
        }

        setLayout(new BorderLayout(0, 20));
        setBackground(new Color(248, 249, 250));

        add(createTitlePanel(), BorderLayout.NORTH);
        add(createMainPanel(), BorderLayout.CENTER);

        loadNhaCungCap();
        revalidate();
        repaint();
    }

    private JPanel createTitlePanel() {
        JLabel lblTitle = new JLabel("QUẢN LÝ NHÀ CUNG CẤP");
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

        JPanel gridPanel = new JPanel(new GridLayout(2, 2, 15, 15));
        gridPanel.setBackground(Color.WHITE);

        txtMaNCC = createStyledTextField();
        txtTenNCC = createStyledTextField();
        txtSDT = createStyledTextField();
        txtDiaChi = createStyledTextField();

        JLabel[] labels = {
                new JLabel("Mã NCC:"), new JLabel("Tên NCC:"),
                new JLabel("SĐT:"), new JLabel("Địa chỉ:")
        };

        for (JLabel label : labels) {
            label.setFont(new Font("Segoe UI", Font.BOLD, 14));
            label.setForeground(new Color(70, 70, 70));
        }

        gridPanel.add(createFieldPanel(labels[0], txtMaNCC));
        gridPanel.add(createFieldPanel(labels[1], txtTenNCC));
        gridPanel.add(createFieldPanel(labels[2], txtSDT));
        gridPanel.add(createFieldPanel(labels[3], txtDiaChi));

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

    private JTextField createStyledTextField() {
        JTextField field = new JTextField();
        field.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        field.setBorder(BorderFactory.createCompoundBorder(
                new MatteBorder(1, 1, 1, 1, new Color(220, 220, 220)),
                new EmptyBorder(10, 12, 10, 12)
        ));
        return field;
    }

    private JScrollPane createTablePanel() {
        tableModel = new DefaultTableModel(
                new String[]{"Mã NCC", "Tên NCC", "SĐT", "Địa chỉ"},
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
        scrollPane.setPreferredSize(new Dimension(600, 300));
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

        btnLamMoi.addActionListener(e -> clearFields());
        btnThem.addActionListener(e -> addNhaCungCap());
        btnSua.addActionListener(e -> updateNhaCungCap());
        btnXoa.addActionListener(e -> deleteNhaCungCap());

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

    private void loadNhaCungCap() {
        try {
            tableModel.setRowCount(0);
            List<NhaCungCap> list = nhaCungCapDao.getAll();
            for (NhaCungCap ncc : list) {
                tableModel.addRow(new Object[]{
                        ncc.getIdNCC(),
                        ncc.getTenNCC(),
                        ncc.getSdt(),
                        ncc.getDiaChi()
                });
            }
        } catch (Exception e) {
            showError("Lỗi khi tải dữ liệu: " + e.getMessage());
        }
    }

    private void clearFields() {
        txtMaNCC.setText("");
        txtTenNCC.setText("");
        txtSDT.setText("");
        txtDiaChi.setText("");
    }

    private void populateFormFromTable() {
        int selectedRow = table.getSelectedRow();
        txtMaNCC.setText(tableModel.getValueAt(selectedRow, 0).toString());
        txtTenNCC.setText(tableModel.getValueAt(selectedRow, 1).toString());
        txtSDT.setText(tableModel.getValueAt(selectedRow, 2).toString());
        txtDiaChi.setText(tableModel.getValueAt(selectedRow, 3).toString());
    }

    private void addNhaCungCap() {
        try {
            String maNCC = txtMaNCC.getText().trim();
            String tenNCC = txtTenNCC.getText().trim();
            String sdt = txtSDT.getText().trim();
            String diaChi = txtDiaChi.getText().trim();

            if (maNCC.isEmpty() || tenNCC.isEmpty() || sdt.isEmpty() || diaChi.isEmpty()) {
                showError("Vui lòng nhập đầy đủ thông tin.");
                return;
            }

            NhaCungCap ncc = new NhaCungCap(maNCC, tenNCC, sdt, diaChi);

            if (nhaCungCapDao.add(ncc)) {
                loadNhaCungCap();
                JOptionPane.showMessageDialog(this, "Thêm nhà cung cấp thành công!");
                clearFields();
            } else {
                showError("Không thể thêm nhà cung cấp (mã NCC có thể đã tồn tại).");
            }
        } catch (Exception e) {
            showError("Lỗi khi thêm nhà cung cấp: " + e.getMessage());
        }
    }

    private void updateNhaCungCap() {
        try {
            String maNCC = txtMaNCC.getText().trim();
            String tenNCC = txtTenNCC.getText().trim();
            String sdt = txtSDT.getText().trim();
            String diaChi = txtDiaChi.getText().trim();

            if (maNCC.isEmpty() || tenNCC.isEmpty() || sdt.isEmpty() || diaChi.isEmpty()) {
                showError("Vui lòng nhập đầy đủ thông tin để cập nhật.");
                return;
            }

            NhaCungCap ncc = new NhaCungCap(maNCC, tenNCC, sdt, diaChi);

            if (nhaCungCapDao.update(ncc)) {
                loadNhaCungCap();
                JOptionPane.showMessageDialog(this, "Cập nhật nhà cung cấp thành công!");
                clearFields();
            } else {
                showError("Không thể cập nhật nhà cung cấp (mã NCC không tồn tại).");
            }
        } catch (Exception e) {
            showError("Lỗi khi cập nhật nhà cung cấp: " + e.getMessage());
        }
    }

    private void deleteNhaCungCap() {
        try {
            String maNCC = txtMaNCC.getText().trim();

            if (maNCC.isEmpty()) {
                showError("Vui lòng nhập mã nhà cung cấp cần xóa.");
                return;
            }

            int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn xóa nhà cung cấp này?", "Xác nhận xóa", JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                NhaCungCap ncc = nhaCungCapDao.getById(maNCC);
                if (ncc == null) {
                    showError("Không tìm thấy nhà cung cấp với mã đã nhập.");
                    return;
                }

                if (nhaCungCapDao.delete(ncc)) {
                    loadNhaCungCap();
                    JOptionPane.showMessageDialog(this, "Xóa nhà cung cấp thành công!");
                    clearFields();
                } else {
                    showError("Không thể xóa nhà cung cấp.");
                }
            }
        } catch (Exception e) {
            showError("Lỗi khi xóa nhà cung cấp: " + e.getMessage());
        }
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Lỗi", JOptionPane.ERROR_MESSAGE);
    }
}