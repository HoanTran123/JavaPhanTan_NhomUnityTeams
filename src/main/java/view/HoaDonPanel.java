package view;

import dao.impl.HoaDonDAO;
import entity.HoaDon;
import jakarta.persistence.EntityManager;
import util.HibernateUtil;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Date;
import java.util.List;

public class HoaDonPanel extends JPanel {
    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField txtMaHD, txtNgayLap, txtTongTien, txtMaKH;
    private HoaDonDAO hoaDonDao;

    public HoaDonPanel() {
        try {
            hoaDonDao = new HoaDonDAO();
            hoaDonDao.setEntityManager(HibernateUtil.getEntityManager());
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khởi tạo HoaDonDAO: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }

        setLayout(new BorderLayout(0, 20));
        setBackground(new Color(248, 249, 250));

        add(createTitlePanel(), BorderLayout.NORTH);
        add(createMainPanel(), BorderLayout.CENTER);

        loadHoaDon();
    }

    private JPanel createTitlePanel() {
        JLabel lblTitle = new JLabel("QUẢN LÝ HÓA ĐƠN");
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

        txtMaHD = createStyledTextField();
        txtNgayLap = createStyledTextField();
        txtTongTien = createStyledTextField();
        txtMaKH = createStyledTextField();

        JLabel[] labels = {
                new JLabel("Mã HĐ:"),
                new JLabel("Ngày Lập:"),
                new JLabel("Tổng Tiền:"),
                new JLabel("Mã KH:")
        };

        for (JLabel label : labels) {
            label.setFont(new Font("Segoe UI", Font.BOLD, 14));
            label.setForeground(new Color(70, 70, 70));
        }

        gridPanel.add(createFieldPanel(labels[0], txtMaHD));
        gridPanel.add(createFieldPanel(labels[1], txtNgayLap));
        gridPanel.add(createFieldPanel(labels[2], txtTongTien));
        gridPanel.add(createFieldPanel(labels[3], txtMaKH));

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
        field.setMaximumSize(new Dimension(Integer.MAX_VALUE, field.getPreferredSize().height));
        return field;
    }

    private JScrollPane createTablePanel() {
        tableModel = new DefaultTableModel(
                new String[]{"Mã HĐ", "Ngày Lập", "Tổng Tiền", "Mã KH"},
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
                int selectedRow = table.getSelectedRow();
                txtMaHD.setText(tableModel.getValueAt(selectedRow, 0).toString());
                txtNgayLap.setText(tableModel.getValueAt(selectedRow, 1).toString());
                txtTongTien.setText(tableModel.getValueAt(selectedRow, 2).toString());
                txtMaKH.setText(tableModel.getValueAt(selectedRow, 3).toString());
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

        btnLamMoi.addActionListener(e -> loadHoaDon());
        btnThem.addActionListener(e -> addHoaDon());
        btnSua.addActionListener(e -> updateHoaDon());
        btnXoa.addActionListener(e -> deleteHoaDon());

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
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return button;
    }

    private void loadHoaDon() {
        try {
            tableModel.setRowCount(0);
            List<HoaDon> list = hoaDonDao.getAll();
            for (HoaDon hd : list) {
                tableModel.addRow(new Object[]{
                        hd.getIdHD(),
                        hd.getThoiGian(),
                        hd.getTongTien(),
                        hd.getKhachHang().getIdKH()
                });
            }
            clearForm();
        } catch (Exception e) {
            e.printStackTrace();
            showError("Lỗi khi tải dữ liệu: " + e.getMessage());
        }
    }

    private void addHoaDon() {
        try {
            HoaDon hd = new HoaDon();
            hd.setIdHD(txtMaHD.getText());
            hd.setThoiGian(new Date());
            hd.setTongTien(Double.parseDouble(txtTongTien.getText()));
            hd.setKhachHang(hoaDonDao.getKhachHangById(txtMaKH.getText()));

            if (hoaDonDao.add(hd)) {
                showSuccess("Thêm hóa đơn thành công!");
                loadHoaDon();
            } else {
                showError("Thêm hóa đơn thất bại!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            showError("Lỗi: " + e.getMessage());
        }
    }

    private void updateHoaDon() {
        try {
            int selectedRow = table.getSelectedRow();
            if (selectedRow == -1) {
                showWarning("Vui lòng chọn hóa đơn để sửa!");
                return;
            }

            String idHD = tableModel.getValueAt(selectedRow, 0).toString();
            HoaDon hd = hoaDonDao.getById(idHD);
            if (hd == null) {
                showError("Hóa đơn không tồn tại!");
                return;
            }

            // Update the fields of the managed entity
            hd.setTongTien(Double.parseDouble(txtTongTien.getText()));
            hd.setKhachHang(hoaDonDao.getKhachHangById(txtMaKH.getText()));

            // Start a transaction before updating
            EntityManager entityManager = hoaDonDao.getEntityManager();
            entityManager.getTransaction().begin();

            // Merge the detached entity into the current session
            entityManager.merge(hd);

            entityManager.getTransaction().commit(); // Commit the transaction
            showSuccess("Cập nhật hóa đơn thành công!");
            loadHoaDon();
        } catch (Exception e) {
            e.printStackTrace();
            showError("Lỗi: " + e.getMessage());
        }
    }

    private void deleteHoaDon() {
        try {
            int selectedRow = table.getSelectedRow();
            if (selectedRow == -1) {
                showWarning("Vui lòng chọn hóa đơn để xóa!");
                return;
            }

            String idHD = tableModel.getValueAt(selectedRow, 0).toString();
            HoaDon hd = hoaDonDao.getById(idHD);

            if (hoaDonDao.delete(hd)) {
                showSuccess("Xóa hóa đơn thành công!");
                loadHoaDon();
            } else {
                showError("Xóa hóa đơn thất bại!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            showError("Lỗi: " + e.getMessage());
        }
    }

    private void clearForm() {
        txtMaHD.setText("");
        txtNgayLap.setText("");
        txtTongTien.setText("");
        txtMaKH.setText("");
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