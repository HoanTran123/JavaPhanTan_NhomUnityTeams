package view;

import dao.impl.PhieuNhapDAO;
import dao.impl.NhaCungCapDAO;
import entity.PhieuNhap;
import entity.NhaCungCap;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import com.toedter.calendar.JDateChooser;

public class PhieuNhapPanel extends JPanel {
    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField txtSearch, txtIdPN;
    private JComboBox<NhaCungCap> cbNhaCungCap;
    private JDateChooser dateChooserNgayNhap;
    private List<PhieuNhap> phieuNhapList;
    private List<NhaCungCap> nhaCungCapList;
    private PhieuNhapDAO phieuNhapDAO;
    private NhaCungCapDAO nhaCungCapDAO;

    public PhieuNhapPanel() {
        try {
            setLayout(new GridBagLayout());
            setBackground(new Color(245, 245, 245));
            phieuNhapList = new ArrayList<>();
            nhaCungCapList = new ArrayList<>();

            // Initialize DAOs
            phieuNhapDAO = new PhieuNhapDAO();
            nhaCungCapDAO = new NhaCungCapDAO();

            // Initialize UI components
            initComponents();

            // Load data after initializing components
            loadDataFromDatabase();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khởi tạo: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void initComponents() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.BOTH;

        // Search Panel
        JPanel searchCard = new JPanel(new BorderLayout(8, 0));
        searchCard.setBackground(Color.WHITE);
        searchCard.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)));

        txtSearch = createStyledTextField("Tìm kiếm phiếu nhập...");
        txtSearch.setPreferredSize(new Dimension(300, 36));

        JButton btnSearch = createStyledButton("Tìm", new Color(52, 152, 219));
        btnSearch.addActionListener(e -> searchPhieuNhap());

        searchCard.add(txtSearch, BorderLayout.CENTER);
        searchCard.add(btnSearch, BorderLayout.EAST);

        gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 1.0; gbc.weighty = 0.0;
        add(searchCard, gbc);

        // Table Panel
        JPanel tableCard = new JPanel(new BorderLayout());
        tableCard.setBackground(Color.WHITE);
        tableCard.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)));

        String[] columns = {"Mã PN", "Nhà Cung Cấp", "Ngày Nhập"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table = new JTable(tableModel);
        table.setFont(new Font("Roboto", Font.PLAIN, 14));
        table.setRowHeight(32);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setGridColor(new Color(230, 230, 230));
        table.setShowGrid(true);
        table.getTableHeader().setFont(new Font("Roboto", Font.BOLD, 14));
        table.getTableHeader().setBackground(new Color(52, 152, 219));
        table.getTableHeader().setForeground(Color.WHITE);
        table.getTableHeader().setOpaque(false);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        tableCard.add(scrollPane, BorderLayout.CENTER);

        gbc.gridx = 0; gbc.gridy = 1; gbc.weighty = 1.0;
        add(tableCard, gbc);

        // Form and Buttons Panel
        JPanel formCard = new JPanel(new GridBagLayout());
        formCard.setBackground(Color.WHITE);
        formCard.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)));

        GridBagConstraints formGbc = new GridBagConstraints();
        formGbc.insets = new Insets(8, 8, 8, 8);
        formGbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel titleLabel = new JLabel("Quản Lý Phiếu Nhập");
        titleLabel.setFont(new Font("Roboto", Font.BOLD, 16));
        titleLabel.setForeground(new Color(44, 62, 80));
        formGbc.gridx = 0; formGbc.gridy = 0; formGbc.gridwidth = 2;
        formCard.add(titleLabel, formGbc);

        formGbc.gridwidth = 1;
        formGbc.gridx = 0; formGbc.gridy = 1;
        formCard.add(new JLabel("Mã Phiếu Nhập:"), formGbc);
        txtIdPN = createStyledTextField("Nhập mã phiếu nhập...");
        formGbc.gridx = 1;
        formCard.add(txtIdPN, formGbc);

        formGbc.gridx = 0; formGbc.gridy = 2;
        formCard.add(new JLabel("Nhà Cung Cấp:"), formGbc);
        cbNhaCungCap = new JComboBox<>();
        cbNhaCungCap.setFont(new Font("Roboto", Font.PLAIN, 14));
        cbNhaCungCap.setBackground(Color.WHITE);
        formGbc.gridx = 1;
        formCard.add(cbNhaCungCap, formGbc);

        formGbc.gridx = 0; formGbc.gridy = 3;
        formCard.add(new JLabel("Ngày Nhập:"), formGbc);
        dateChooserNgayNhap = createStyledDateChooser();
        formGbc.gridx = 1;
        formCard.add(dateChooserNgayNhap, formGbc);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 0));
        buttonPanel.setOpaque(false);

        JButton btnAdd = createStyledButton("Thêm", new Color(52, 152, 219));
        JButton btnUpdate = createStyledButton("Sửa", new Color(39, 174, 96));
        JButton btnDelete = createStyledButton("Xóa", new Color(192, 57, 43));
        JButton btnClear = createStyledButton("Làm mới", new Color(127, 140, 141));

        btnAdd.addActionListener(e -> addPhieuNhap());
        btnUpdate.addActionListener(e -> updatePhieuNhap());
        btnDelete.addActionListener(e -> deletePhieuNhap());
        btnClear.addActionListener(e -> clearForm());

        buttonPanel.add(btnAdd);
        buttonPanel.add(btnUpdate);
        buttonPanel.add(btnDelete);
        buttonPanel.add(btnClear);

        formGbc.gridx = 0; formGbc.gridy = 4; formGbc.gridwidth = 2;
        formCard.add(buttonPanel, formGbc);

        gbc.gridx = 0; gbc.gridy = 2; gbc.weighty = 0.0;
        add(formCard, gbc);

        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && table.getSelectedRow() >= 0) {
                int selectedRow = table.getSelectedRow();
                PhieuNhap pn = phieuNhapList.get(selectedRow);
                txtIdPN.setText(pn.getIdPN());
                cbNhaCungCap.setSelectedItem(pn.getNhaCungCap());
                dateChooserNgayNhap.setDate(
                        pn.getThoiGian() != null ? Date.from(pn.getThoiGian().toInstant().atZone(ZoneId.systemDefault()).toInstant()) : null
                );
            }
        });
    }

    private JTextField createStyledTextField(String placeholder) {
        JTextField textField = new JTextField(20);
        textField.setFont(new Font("Roboto", Font.PLAIN, 14));
        textField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
                BorderFactory.createEmptyBorder(8, 8, 8, 8)));
        textField.setBackground(Color.WHITE);
        textField.setForeground(new Color(44, 62, 80));
        textField.setText(placeholder);
        textField.setForeground(new Color(149, 165, 166));
        textField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (textField.getText().equals(placeholder)) {
                    textField.setText("");
                    textField.setForeground(new Color(44, 62, 80));
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (textField.getText().isEmpty()) {
                    textField.setText(placeholder);
                    textField.setForeground(new Color(149, 165, 166));
                }
            }
        });

        return textField;
    }

    private JDateChooser createStyledDateChooser() {
        JDateChooser dateChooser = new JDateChooser();
        dateChooser.setFont(new Font("Roboto", Font.PLAIN, 14));
        dateChooser.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
                BorderFactory.createEmptyBorder(8, 8, 8, 8)));
        dateChooser.setBackground(Color.WHITE);
        dateChooser.setDateFormatString("dd/MM/yyyy");
        return dateChooser;
    }

    private JButton createStyledButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setFont(new Font("Roboto", Font.BOLD, 14));
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setBorder(BorderFactory.createEmptyBorder(8, 16, 8, 16));
        button.setFocusPainted(false);
        button.setContentAreaFilled(false);
        button.setOpaque(true);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(color.brighter());
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(color);
            }

            @Override
            public void mousePressed(MouseEvent e) {
                button.setBackground(color.darker());
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                button.setBackground(color);
            }
        });

        return button;
    }

    private void loadDataFromDatabase() {
        try {
            phieuNhapList = phieuNhapDAO.getAll();
            nhaCungCapList = nhaCungCapDAO.getAll();

            cbNhaCungCap.setModel(new DefaultComboBoxModel<>(nhaCungCapList.toArray(new NhaCungCap[0])));
            refreshTable();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this,
                    "Không thể tải dữ liệu từ cơ sở dữ liệu: " + e.getMessage(),
                    "Lỗi Cơ Sở Dữ Liệu",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void refreshTable() {
        tableModel.setRowCount(0);
        for (PhieuNhap pn : phieuNhapList) {
            tableModel.addRow(new Object[]{
                    pn.getIdPN(),
                    pn.getNhaCungCap() != null ? pn.getNhaCungCap().getTenNCC() : "",
                    pn.getThoiGian()
            });
        }
    }

    private void addPhieuNhap() {
        try {
            if (txtIdPN.getText().equals("Nhập mã phiếu nhập...")) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập mã phiếu nhập!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            PhieuNhap pn = new PhieuNhap();
            pn.setIdPN(txtIdPN.getText());
            pn.setNhaCungCap((NhaCungCap) cbNhaCungCap.getSelectedItem());
            Date ngayNhap = dateChooserNgayNhap.getDate();
            pn.setThoiGian(ngayNhap != null ? new java.sql.Timestamp(ngayNhap.getTime()) : null);

            phieuNhapDAO.add(pn);
            phieuNhapList.add(pn);
            refreshTable();
            clearForm();
            JOptionPane.showMessageDialog(this, "Thêm phiếu nhập thành công!");
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi: Vui lòng kiểm tra lại dữ liệu! " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updatePhieuNhap() {
        try {
            int selectedRow = table.getSelectedRow();
            if (selectedRow >= 0) {
                PhieuNhap pn = phieuNhapList.get(selectedRow);
                pn.setNhaCungCap((NhaCungCap) cbNhaCungCap.getSelectedItem());
                Date ngayNhap = dateChooserNgayNhap.getDate();
                pn.setThoiGian(ngayNhap != null ? new java.sql.Timestamp(ngayNhap.getTime()) : null);

                phieuNhapDAO.update(pn);
                refreshTable();
                clearForm();
                JOptionPane.showMessageDialog(this, "Cập nhật phiếu nhập thành công!");
            } else {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn một phiếu nhập để sửa!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi: Vui lòng kiểm tra lại dữ liệu! " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deletePhieuNhap() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow >= 0) {
            int confirm = JOptionPane.showConfirmDialog(this,
                    "Bạn có chắc muốn xóa phiếu nhập này?",
                    "Xác nhận xóa",
                    JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                try {
                    PhieuNhap pn = phieuNhapList.get(selectedRow);
                    phieuNhapDAO.delete(pn);
                    phieuNhapList.remove(selectedRow);
                    refreshTable();
                    clearForm();
                    JOptionPane.showMessageDialog(this, "Xóa phiếu nhập thành công!");
                } catch (Exception e) {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(this, "Lỗi khi xóa phiếu nhập: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một phiếu nhập để xóa!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void searchPhieuNhap() {
        String searchText = txtSearch.getText().toLowerCase();
        if (searchText.equals("tìm kiếm phiếu nhập...")) searchText = "";
        try {
            phieuNhapList = phieuNhapDAO.findMany(
                    "SELECT pn FROM PhieuNhap pn WHERE LOWER(pn.idPN) LIKE ?1",
                    PhieuNhap.class, "%" + searchText + "%"
            );
            refreshTable();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi khi tìm kiếm: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void clearForm() {
        txtIdPN.setText("Nhập mã phiếu nhập...");
        txtIdPN.setForeground(new Color(149, 165, 166));
        cbNhaCungCap.setSelectedIndex(-1);
        dateChooserNgayNhap.setDate(null);
        table.clearSelection();
    }
}