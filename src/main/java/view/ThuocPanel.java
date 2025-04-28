package view;

import dao.impl.ThuocDAO;
import entity.Thuoc;
import util.HibernateUtil;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;
import java.awt.*;
import java.time.LocalDate;
import java.util.List;

public class ThuocPanel extends JPanel {
    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField txtMaThuoc, txtTenThuoc, txtThanhPhan, txtSoLuongTon, txtGiaNhap, txtDonGia, txtHanSuDung;
    private ThuocDAO thuocDao;
    private HoaDonPanel hoaDonPanel; // Thêm tham chiếu đến HoaDonPanel

    public ThuocPanel(HoaDonPanel hoaDonPanel) {
        this.hoaDonPanel = hoaDonPanel;
        try {
            thuocDao = new ThuocDAO();
            thuocDao.setEntityManager(HibernateUtil.getEntityManager());
        } catch (Exception e) {
            showError("Lỗi khởi tạo ThuocDAO: " + e.getMessage());
        }

        setLayout(new BorderLayout(0, 20));
        setBackground(new Color(248, 249, 250));

        add(createTitlePanel(), BorderLayout.NORTH);
        add(createMainPanel(), BorderLayout.CENTER);

        loadThuoc();
        revalidate();
        repaint();
    }

    private JPanel createTitlePanel() {
        JLabel lblTitle = new JLabel("QUẢN LÝ THUỐC");
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

        txtMaThuoc = createStyledTextField();
        txtTenThuoc = createStyledTextField();
        txtThanhPhan = createStyledTextField();
        txtSoLuongTon = createStyledTextField();
        txtGiaNhap = createStyledTextField();
        txtDonGia = createStyledTextField();
        txtHanSuDung = createStyledTextField();

        JLabel[] labels = {
                new JLabel("Mã Thuốc:"), new JLabel("Tên Thuốc:"),
                new JLabel("Thành Phần:"), new JLabel("Số Lượng Tồn:"),
                new JLabel("Giá Nhập:"), new JLabel("Đơn Giá:"),
                new JLabel("Hạn Sử Dụng (YYYY-MM-DD):")
        };

        for (JLabel label : labels) {
            label.setFont(new Font("Segoe UI", Font.BOLD, 14));
            label.setForeground(new Color(70, 70, 70));
        }

        gridPanel.add(createFieldPanel(labels[0], txtMaThuoc));
        gridPanel.add(createFieldPanel(labels[1], txtTenThuoc));
        gridPanel.add(createFieldPanel(labels[2], txtThanhPhan));
        gridPanel.add(createFieldPanel(labels[3], txtSoLuongTon));
        gridPanel.add(createFieldPanel(labels[4], txtGiaNhap));
        gridPanel.add(createFieldPanel(labels[5], txtDonGia));
        gridPanel.add(createFieldPanel(labels[6], txtHanSuDung));

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
                new String[]{"Mã Thuốc", "Tên Thuốc", "Thành Phần", "Số Lượng Tồn", "Giá Nhập", "Đơn Giá", "Hạn Sử Dụng"},
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
        btnThem.addActionListener(e -> addThuoc());
        btnSua.addActionListener(e -> updateThuoc());
        btnXoa.addActionListener(e -> deleteThuoc());
        JButton btnThanhToan = createActionButton("Thanh Toán", new Color(0, 123, 255));
        btnThanhToan.addActionListener(e -> openBanThuocDialog());

        buttonPanel.add(btnThem);
        buttonPanel.add(btnSua);
        buttonPanel.add(btnXoa);
        buttonPanel.add(btnLamMoi);
        buttonPanel.add(btnThanhToan);

        return buttonPanel;
    }

    private void openBanThuocDialog() {
        BanThuocDialog dialog = new BanThuocDialog(
                (JFrame) SwingUtilities.getWindowAncestor(this),
                () -> {
                    if (hoaDonPanel != null) {
                        hoaDonPanel.loadHoaDon();
                    }
                }
        );
        dialog.setVisible(true);
        loadThuoc(); // Refresh the table after the dialog is closed
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

    private void loadThuoc() {
        try {
            tableModel.setRowCount(0);
            List<Thuoc> list = thuocDao.getAll();
            for (Thuoc thuoc : list) {
                tableModel.addRow(new Object[]{
                        thuoc.getIdThuoc(),
                        thuoc.getTenThuoc(),
                        thuoc.getThanhPhan(),
                        thuoc.getSoLuongTon(),
                        thuoc.getGiaNhap(),
                        thuoc.getDonGia(),
                        thuoc.getHanSuDung()
                });
            }
        } catch (Exception e) {
            showError("Lỗi khi tải dữ liệu: " + e.getMessage());
        }
    }

    private void clearFields() {
        txtMaThuoc.setText("");
        txtTenThuoc.setText("");
        txtThanhPhan.setText("");
        txtSoLuongTon.setText("");
        txtGiaNhap.setText("");
        txtDonGia.setText("");
        txtHanSuDung.setText("");
    }

    private void populateFormFromTable() {
        int selectedRow = table.getSelectedRow();
        txtMaThuoc.setText(tableModel.getValueAt(selectedRow, 0).toString());
        txtTenThuoc.setText(tableModel.getValueAt(selectedRow, 1).toString());
        txtThanhPhan.setText(tableModel.getValueAt(selectedRow, 2).toString());
        txtSoLuongTon.setText(tableModel.getValueAt(selectedRow, 3).toString());
        txtGiaNhap.setText(tableModel.getValueAt(selectedRow, 4).toString());
        txtDonGia.setText(tableModel.getValueAt(selectedRow, 5).toString());
        txtHanSuDung.setText(tableModel.getValueAt(selectedRow, 6).toString());
    }

    private void addThuoc() {
        try {
            String maThuoc = txtMaThuoc.getText().trim();
            String tenThuoc = txtTenThuoc.getText().trim();
            String thanhPhan = txtThanhPhan.getText().trim();
            int soLuongTon = Integer.parseInt(txtSoLuongTon.getText().trim());
            double giaNhap = Double.parseDouble(txtGiaNhap.getText().trim());
            double donGia = Double.parseDouble(txtDonGia.getText().trim());
            LocalDate hanSuDung = LocalDate.parse(txtHanSuDung.getText().trim());

            if (maThuoc.isEmpty() || tenThuoc.isEmpty() || thanhPhan.isEmpty()) {
                showError("Vui lòng nhập đầy đủ thông tin.");
                return;
            }

            Thuoc thuoc = new Thuoc();
            thuoc.setIdThuoc(maThuoc);
            thuoc.setTenThuoc(tenThuoc);
            thuoc.setThanhPhan(thanhPhan);
            thuoc.setSoLuongTon(soLuongTon);
            thuoc.setGiaNhap(giaNhap);
            thuoc.setDonGia(donGia);
            thuoc.setHanSuDung(hanSuDung);

            if (thuocDao.add(thuoc)) {
                loadThuoc();
                JOptionPane.showMessageDialog(this, "Thêm thuốc thành công!");
                clearFields();
            } else {
                showError("Không thể thêm thuốc (mã thuốc có thể đã tồn tại).");
            }
        } catch (Exception e) {
            showError("Lỗi khi thêm thuốc: " + e.getMessage());
        }
    }

    private void updateThuoc() {
        try {
            String maThuoc = txtMaThuoc.getText().trim();
            String tenThuoc = txtTenThuoc.getText().trim();
            String thanhPhan = txtThanhPhan.getText().trim();
            int soLuongTon = Integer.parseInt(txtSoLuongTon.getText().trim());
            double giaNhap = Double.parseDouble(txtGiaNhap.getText().trim());
            double donGia = Double.parseDouble(txtDonGia.getText().trim());
            LocalDate hanSuDung = LocalDate.parse(txtHanSuDung.getText().trim());

            if (maThuoc.isEmpty() || tenThuoc.isEmpty() || thanhPhan.isEmpty()) {
                showError("Vui lòng nhập đầy đủ thông tin để cập nhật.");
                return;
            }

            Thuoc thuoc = new Thuoc();
            thuoc.setIdThuoc(maThuoc);
            thuoc.setTenThuoc(tenThuoc);
            thuoc.setThanhPhan(thanhPhan);
            thuoc.setSoLuongTon(soLuongTon);
            thuoc.setGiaNhap(giaNhap);
            thuoc.setDonGia(donGia);
            thuoc.setHanSuDung(hanSuDung);

            if (thuocDao.update(thuoc)) {
                loadThuoc();
                JOptionPane.showMessageDialog(this, "Cập nhật thuốc thành công!");
                clearFields();
            } else {
                showError("Không thể cập nhật thuốc (mã thuốc không tồn tại).");
            }
        } catch (Exception e) {
            showError("Lỗi khi cập nhật thuốc: " + e.getMessage());
        }
    }

    private void deleteThuoc() {
        try {
            String maThuoc = txtMaThuoc.getText().trim();

            if (maThuoc.isEmpty()) {
                showError("Vui lòng nhập mã thuốc cần xóa.");
                return;
            }

            int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn xóa thuốc này?", "Xác nhận xóa", JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                Thuoc thuoc = thuocDao.getById(maThuoc);
                if (thuoc == null) {
                    showError("Không tìm thấy thuốc với mã đã nhập.");
                    return;
                }

                if (thuocDao.delete(thuoc)) {
                    loadThuoc();
                    JOptionPane.showMessageDialog(this, "Xóa thuốc thành công!");
                    clearFields();
                } else {
                    showError("Không thể xóa thuốc.");
                }
            }
        } catch (Exception e) {
            showError("Lỗi khi xóa thuốc: " + e.getMessage());
        }
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Lỗi", JOptionPane.ERROR_MESSAGE);
    }
}