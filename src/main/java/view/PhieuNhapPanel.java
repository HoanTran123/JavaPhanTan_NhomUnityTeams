package view;

import dao.impl.PhieuNhapDAO;
import dao.impl.NhaCungCapDAO;
import dao.impl.ThuocDAO;
import entity.ChiTietPhieuNhap;
import entity.PhieuNhap;
import entity.NhaCungCap;
import entity.Thuoc;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import com.toedter.calendar.JDateChooser;

public class PhieuNhapPanel extends JPanel {
    private static final Color PRIMARY_COLOR = new Color(33, 150, 243); // Màu chính (xanh dương)
    private static final Color ERROR_COLOR = new Color(244, 67, 54); // Màu lỗi
    private static final Color NEUTRAL_COLOR = new Color(120, 144, 156); // Màu trung tính
    private static final Font MAIN_FONT = new Font("Roboto", Font.PLAIN, 14);
    private static final Font TITLE_FONT = new Font("Roboto", Font.BOLD, 20);
    private static final Font BUTTON_FONT = new Font("Roboto", Font.BOLD, 14);

    private JTable table;
    private JTable thuocTable;
    private DefaultTableModel tableModel;
    private DefaultTableModel thuocTableModel;
    private JTextField txtSearch, txtIdPN, txtSoLuong;
    private JComboBox<NhaCungCap> cbNhaCungCap;
    private JComboBox<Thuoc> cbThuoc;
    private JDateChooser dateChooserNgayNhap;
    private List<PhieuNhap> phieuNhapList;
    private List<NhaCungCap> nhaCungCapList;
    private List<Thuoc> selectedThuocList;
    private List<ChiTietPhieuNhap> chiTietPhieuNhapList;
    private PhieuNhapDAO phieuNhapDAO;
    private NhaCungCapDAO nhaCungCapDAO;
    private ThuocDAO thuocDAO;

    public PhieuNhapPanel() {
        try {
            setLayout(new GridBagLayout());
            setBackground(new Color(245, 245, 245));
            phieuNhapList = new ArrayList<>();
            nhaCungCapList = new ArrayList<>();
            selectedThuocList = new ArrayList<>();
            chiTietPhieuNhapList = new ArrayList<>();

            // Initialize DAOs
            phieuNhapDAO = new PhieuNhapDAO();
            nhaCungCapDAO = new NhaCungCapDAO();
            thuocDAO = new ThuocDAO();

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

        // Search Panel (Row 0)
        JPanel searchCard = new JPanel(new BorderLayout(10, 0));
        searchCard.setBackground(Color.WHITE);
        searchCard.setBorder(new EmptyBorder(10, 10, 10, 10));
        txtSearch = createStyledTextField("Tìm kiếm phiếu nhập...");
        txtSearch.setPreferredSize(new Dimension(300, 36));
        JButton btnSearch = createStyledButton("Tìm", PRIMARY_COLOR, "Tìm kiếm phiếu nhập");
        btnSearch.addActionListener(e -> searchPhieuNhap());
        searchCard.add(txtSearch, BorderLayout.CENTER);
        searchCard.add(btnSearch, BorderLayout.EAST);
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2; gbc.weightx = 1.0; gbc.weighty = 0.05;
        add(searchCard, gbc);

        // Input Panel (Row 1)
        JPanel inputCard = new JPanel(new GridBagLayout());
        inputCard.setBackground(Color.WHITE);
        inputCard.setBorder(new EmptyBorder(10, 10, 10, 10));
        GridBagConstraints inputGbc = new GridBagConstraints();
        inputGbc.insets = new Insets(10, 10, 10, 10);
        inputGbc.fill = GridBagConstraints.HORIZONTAL;

        // Title
        JLabel titleLabel = new JLabel("Quản Lý Phiếu Nhập");
        titleLabel.setFont(TITLE_FONT);
        titleLabel.setForeground(new Color(33, 33, 33));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        inputGbc.gridx = 0; inputGbc.gridy = 0; inputGbc.gridwidth = 4; inputGbc.weightx = 1.0;
        inputCard.add(titleLabel, inputGbc);

        // Left Frame: PhieuNhap Details
        JPanel leftFrame = new JPanel(new GridBagLayout());
        leftFrame.setBackground(Color.WHITE);
        leftFrame.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(189, 195, 199)), "Thông Tin Phiếu Nhập"));
        GridBagConstraints leftGbc = new GridBagConstraints();
        leftGbc.insets = new Insets(10, 10, 10, 10);
        leftGbc.fill = GridBagConstraints.HORIZONTAL;
        leftGbc.weightx = 1.0;

        leftGbc.gridx = 0; leftGbc.gridy = 0;
        leftFrame.add(new JLabel("Mã Phiếu Nhập:"), leftGbc);
        txtIdPN = createStyledTextField("Nhập mã phiếu nhập...");
        txtIdPN.setPreferredSize(new Dimension(200, 36));
        leftGbc.gridx = 1;
        leftFrame.add(txtIdPN, leftGbc);

        leftGbc.gridx = 0; leftGbc.gridy = 1;
        leftFrame.add(new JLabel("Ngày Nhập:"), leftGbc);
        dateChooserNgayNhap = createStyledDateChooser();
        dateChooserNgayNhap.setPreferredSize(new Dimension(200, 36));
        leftGbc.gridx = 1;
        leftFrame.add(dateChooserNgayNhap, leftGbc);

        leftGbc.gridx = 0; leftGbc.gridy = 2;
        leftFrame.add(new JLabel("Nhà Cung Cấp:"), leftGbc);
        cbNhaCungCap = new JComboBox<>();
        cbNhaCungCap.setFont(MAIN_FONT);
        cbNhaCungCap.setBackground(Color.WHITE);
        cbNhaCungCap.setPreferredSize(new Dimension(200, 36));
        cbNhaCungCap.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof NhaCungCap) {
                    setText(((NhaCungCap) value).getTenNCC());
                }
                return this;
            }
        });
        leftGbc.gridx = 1;
        leftFrame.add(cbNhaCungCap, leftGbc);

        inputGbc.gridx = 0; inputGbc.gridy = 1; inputGbc.gridwidth = 2; inputGbc.weightx = 0.5; inputGbc.weighty = 1.0;
        inputCard.add(leftFrame, inputGbc);

        // Right Frame: Medicine Management
        JPanel rightFrame = new JPanel(new GridBagLayout());
        rightFrame.setBackground(Color.WHITE);
        rightFrame.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(189, 195, 199)), "Quản Lý Thuốc"));
        GridBagConstraints rightGbc = new GridBagConstraints();
        rightGbc.insets = new Insets(10, 10, 10, 10);
        rightGbc.fill = GridBagConstraints.HORIZONTAL;
        rightGbc.weightx = 1.0;

        rightGbc.gridx = 0; rightGbc.gridy = 0;
        rightFrame.add(new JLabel("Thuốc:"), rightGbc);
        cbThuoc = new JComboBox<>();
        cbThuoc.setFont(MAIN_FONT);
        cbThuoc.setBackground(Color.WHITE);
        cbThuoc.setPreferredSize(new Dimension(200, 36));
        cbThuoc.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof Thuoc) {
                    setText(((Thuoc) value).getTenThuoc());
                }
                return this;
            }
        });
        rightGbc.gridx = 1;
        rightFrame.add(cbThuoc, rightGbc);

        rightGbc.gridx = 0; rightGbc.gridy = 1;
        rightFrame.add(new JLabel("Số Lượng:"), rightGbc);
        txtSoLuong = createStyledTextField("Nhập số lượng...");
        txtSoLuong.setPreferredSize(new Dimension(200, 36));
        rightGbc.gridx = 1;
        rightFrame.add(txtSoLuong, rightGbc);

        JPanel thuocButtonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        thuocButtonPanel.setOpaque(false);
        JButton btnAddThuoc = createStyledButton("Thêm Thuốc", PRIMARY_COLOR, "Thêm thuốc vào danh sách");
        JButton btnRemoveThuoc = createStyledButton("Xóa Thuốc", ERROR_COLOR, "Xóa thuốc khỏi danh sách");
        btnAddThuoc.addActionListener(e -> addThuocToList());
        btnRemoveThuoc.addActionListener(e -> removeThuocFromList());
        thuocButtonPanel.add(btnAddThuoc);
        thuocButtonPanel.add(btnRemoveThuoc);
        rightGbc.gridx = 0; rightGbc.gridy = 2; rightGbc.gridwidth = 2;
        rightFrame.add(thuocButtonPanel, rightGbc);

        thuocTableModel = new DefaultTableModel(new String[]{"ID Thuốc", "Tên Thuốc", "Số Lượng"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        thuocTable = createStyledTable(thuocTableModel);
        JScrollPane thuocScrollPane = new JScrollPane(thuocTable);
        thuocScrollPane.setBorder(new EmptyBorder(0, 0, 0, 0));
        rightGbc.gridx = 0; rightGbc.gridy = 3; rightGbc.gridwidth = 2; rightGbc.weighty = 1.0;
        rightFrame.add(thuocScrollPane, rightGbc);

        inputGbc.gridx = 2; inputGbc.gridy = 1; inputGbc.gridwidth = 2; inputGbc.weightx = 0.5;
        inputCard.add(rightFrame, inputGbc);

        gbc.gridx = 0; gbc.gridy = 1; gbc.gridwidth = 2; gbc.weightx = 1.0; gbc.weighty = 0.2;
        add(inputCard, gbc);

        // Button Panel (Row 2)
        JPanel buttonCard = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        buttonCard.setBackground(Color.WHITE);
        buttonCard.setBorder(new EmptyBorder(10, 10, 10, 10));
        JButton btnAdd = createStyledButton("Thêm", PRIMARY_COLOR, "Thêm phiếu nhập mới");
        JButton btnUpdate = createStyledButton("Sửa", new Color(46, 204, 113), "Chỉnh sửa phiếu nhập");
        JButton btnDelete = createStyledButton("Xóa", ERROR_COLOR, "Xóa phiếu nhập");
        JButton btnClear = createStyledButton("Làm mới", NEUTRAL_COLOR, "Xóa toàn bộ dữ liệu nhập");
        btnAdd.addActionListener(e -> addPhieuNhap());
        btnUpdate.addActionListener(e -> updatePhieuNhap());
        btnDelete.addActionListener(e -> deletePhieuNhap());
        btnClear.addActionListener(e -> clearForm());
        buttonCard.add(btnAdd);
        buttonCard.add(btnUpdate);
        buttonCard.add(btnDelete);
        buttonCard.add(btnClear);
        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2; gbc.weightx = 1.0; gbc.weighty = 0.05;
        add(buttonCard, gbc);

        // Table Panel (Row 3)
        JPanel tableCard = new JPanel(new BorderLayout());
        tableCard.setBackground(Color.WHITE);
        tableCard.setBorder(new EmptyBorder(10, 10, 10, 10));
        String[] columns = {"Mã PN", "Nhà Cung Cấp", "Ngày Nhập", "Thuốc"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table = createStyledTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(new EmptyBorder(0, 0, 0, 0));
        tableCard.add(scrollPane, BorderLayout.CENTER);
        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2; gbc.weightx = 1.0; gbc.weighty = 0.7;
        add(tableCard, gbc);

        // Selection Listener
        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && table.getSelectedRow() >= 0) {
                int selectedRow = table.getSelectedRow();
                PhieuNhap pn = phieuNhapList.get(selectedRow);
                txtIdPN.setText(pn.getIdPN());
                cbNhaCungCap.setSelectedItem(pn.getNhaCungCap());
                dateChooserNgayNhap.setDate(
                        pn.getThoiGian() != null ? Date.from(pn.getThoiGian().toInstant().atZone(ZoneId.systemDefault()).toInstant()) : null
                );
                chiTietPhieuNhapList = phieuNhapDAO.getChiTietByPhieuNhap(pn.getIdPN());
                selectedThuocList.clear();
                thuocTableModel.setRowCount(0);
                for (ChiTietPhieuNhap ctpn : chiTietPhieuNhapList) {
                    selectedThuocList.add(ctpn.getThuoc());
                    thuocTableModel.addRow(new Object[]{
                            ctpn.getThuoc().getIdThuoc(),
                            ctpn.getThuoc().getTenThuoc(),
                            ctpn.getSoLuong()
                    });
                }
            }
        });
    }

    private JTextField createStyledTextField(String placeholder) {
        JTextField textField = new JTextField(20);
        textField.setFont(MAIN_FONT);
        textField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(189, 195, 199), 1),
                BorderFactory.createEmptyBorder(8, 8, 8, 8)));
        textField.setBackground(Color.WHITE);
        textField.setForeground(NEUTRAL_COLOR);
        textField.setText(placeholder);
        textField.setToolTipText(placeholder);

        textField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (textField.getText().equals(placeholder)) {
                    textField.setText("");
                    textField.setForeground(Color.BLACK);
                }
                textField.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(PRIMARY_COLOR, 2),
                        BorderFactory.createEmptyBorder(8, 8, 8, 8)));
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (textField.getText().isEmpty()) {
                    textField.setText(placeholder);
                    textField.setForeground(NEUTRAL_COLOR);
                }
                textField.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(new Color(189, 195, 199), 1),
                        BorderFactory.createEmptyBorder(8, 8, 8, 8)));
            }
        });
        return textField;
    }

    private JDateChooser createStyledDateChooser() {
        JDateChooser dateChooser = new JDateChooser();
        dateChooser.setFont(MAIN_FONT);
        dateChooser.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(189, 195, 199), 1),
                BorderFactory.createEmptyBorder(8, 8, 8, 8)));
        dateChooser.setBackground(Color.WHITE);
        dateChooser.setDateFormatString("dd/MM/yyyy");
        dateChooser.setToolTipText("Chọn ngày nhập");
        return dateChooser;
    }

    private JButton createStyledButton(String text, Color bgColor, String tooltip) {
        JButton button = new JButton(text);
        button.setFont(BUTTON_FONT);
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setBorder(BorderFactory.createEmptyBorder(8, 16, 8, 16));
        button.setFocusPainted(false);
        button.setContentAreaFilled(false);
        button.setOpaque(true);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setToolTipText(tooltip);

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(bgColor.brighter());
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(bgColor);
            }

            @Override
            public void mousePressed(MouseEvent e) {
                button.setBackground(bgColor.darker());
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                button.setBackground(bgColor);
            }
        });
        return button;
    }

    private JTable createStyledTable(DefaultTableModel model) {
        JTable table = new JTable(model);
        table.setFont(MAIN_FONT);
        table.setRowHeight(36);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setGridColor(new Color(238, 238, 238));
        table.setShowGrid(true);
        table.getTableHeader().setFont(new Font("Roboto", Font.BOLD, 14));
        table.getTableHeader().setBackground(PRIMARY_COLOR);
        table.getTableHeader().setForeground(Color.WHITE);
        table.getTableHeader().setOpaque(false);
        table.getTableHeader().setReorderingAllowed(false);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        return table;
    }

    private void addThuocToList() {
        try {
            Thuoc selectedThuoc = (Thuoc) cbThuoc.getSelectedItem();
            String soLuongStr = txtSoLuong.getText();
            if (selectedThuoc == null) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn một thuốc!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (soLuongStr.equals("Nhập số lượng...") || soLuongStr.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập số lượng!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }
            int soLuong;
            try {
                soLuong = Integer.parseInt(soLuongStr);
                if (soLuong <= 0) {
                    JOptionPane.showMessageDialog(this, "Số lượng phải lớn hơn 0!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập số lượng hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            for (ChiTietPhieuNhap ctpn : chiTietPhieuNhapList) {
                if (ctpn.getThuoc().getIdThuoc().equals(selectedThuoc.getIdThuoc())) {
                    JOptionPane.showMessageDialog(this, "Thuốc đã được thêm, vui lòng chỉnh sửa số lượng!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }

            ChiTietPhieuNhap ctpn = new ChiTietPhieuNhap();
            ctpn.setThuoc(selectedThuoc);
            ctpn.setSoLuong(soLuong);
            ctpn.setDonGia(selectedThuoc.getGiaNhap());
            chiTietPhieuNhapList.add(ctpn);
            selectedThuocList.add(selectedThuoc);

            thuocTableModel.addRow(new Object[]{
                    selectedThuoc.getIdThuoc(),
                    selectedThuoc.getTenThuoc(),
                    soLuong
            });

            txtSoLuong.setText("Nhập số lượng...");
            txtSoLuong.setForeground(NEUTRAL_COLOR);
            cbThuoc.setSelectedIndex(-1);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi khi thêm thuốc: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void removeThuocFromList() {
        int selectedRow = thuocTable.getSelectedRow();
        if (selectedRow >= 0) {
            chiTietPhieuNhapList.remove(selectedRow);
            selectedThuocList.remove(selectedRow);
            thuocTableModel.removeRow(selectedRow);
        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một thuốc để xóa!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void loadDataFromDatabase() {
        try {
            phieuNhapList = phieuNhapDAO.getAll();
            nhaCungCapList = nhaCungCapDAO.getAll();
            List<Thuoc> thuocList = thuocDAO.getAll();
            cbNhaCungCap.setModel(new DefaultComboBoxModel<>(nhaCungCapList.toArray(new NhaCungCap[0])));
            cbThuoc.setModel(new DefaultComboBoxModel<>(thuocList.toArray(new Thuoc[0])));
            refreshTable();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this,
                    "Không thể tải dữ liệu từ cơ sở dữ liệu: " + e.getMessage(),
                    "Lỗi Cơ Sở Dữ Liệu",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void refreshTable() {
        tableModel.setRowCount(0);
        for (PhieuNhap pn : phieuNhapList) {
            StringBuilder thuocInfo = new StringBuilder();
            List<ChiTietPhieuNhap> chiTietList = phieuNhapDAO.getChiTietByPhieuNhap(pn.getIdPN());
            for (ChiTietPhieuNhap ctpn : chiTietList) {
                thuocInfo.append(ctpn.getThuoc().getTenThuoc()).append(" (").append(ctpn.getSoLuong()).append("), ");
            }
            String thuocText = thuocInfo.length() > 0 ? thuocInfo.toString().replaceAll(", $", "") : "Không có thuốc";
            tableModel.addRow(new Object[]{
                    pn.getIdPN(),
                    pn.getNhaCungCap() != null ? pn.getNhaCungCap().getTenNCC() : "",
                    pn.getThoiGian(),
                    thuocText
            });
        }
    }

    private void addPhieuNhap() {
        try {
            if (txtIdPN.getText().equals("Nhập mã phiếu nhập...") || txtIdPN.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập mã phiếu nhập!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (chiTietPhieuNhapList.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng thêm ít nhất một thuốc!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            PhieuNhap pn = new PhieuNhap();
            pn.setIdPN(txtIdPN.getText());
            pn.setNhaCungCap((NhaCungCap) cbNhaCungCap.getSelectedItem());
            Date ngayNhap = dateChooserNgayNhap.getDate();
            pn.setThoiGian(ngayNhap != null ? new java.sql.Timestamp(ngayNhap.getTime()) : null);
            pn.setChiTietPhieuNhap(chiTietPhieuNhapList);
            double tongTien = chiTietPhieuNhapList.stream()
                    .mapToDouble(ctpn -> ctpn.getSoLuong() * ctpn.getDonGia())
                    .sum();
            pn.setTongTien(tongTien);

            for (ChiTietPhieuNhap ctpn : chiTietPhieuNhapList) {
                ctpn.setPhieuNhap(pn);
                Thuoc thuoc = thuocDAO.getById(ctpn.getThuoc().getIdThuoc());
                thuoc.setSoLuongTon(thuoc.getSoLuongTon() + ctpn.getSoLuong());
                thuocDAO.update(thuoc);
            }

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
            if (selectedRow < 0) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn một phiếu nhập để sửa!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (chiTietPhieuNhapList.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng thêm ít nhất một thuốc!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            PhieuNhap pn = phieuNhapList.get(selectedRow);
            pn.setNhaCungCap((NhaCungCap) cbNhaCungCap.getSelectedItem());
            Date ngayNhap = dateChooserNgayNhap.getDate();
            pn.setThoiGian(ngayNhap != null ? new java.sql.Timestamp(ngayNhap.getTime()) : null);

            List<ChiTietPhieuNhap> oldChiTietList = phieuNhapDAO.getChiTietByPhieuNhap(pn.getIdPN());
            for (ChiTietPhieuNhap oldCtpn : oldChiTietList) {
                Thuoc thuoc = thuocDAO.getById(oldCtpn.getThuoc().getIdThuoc());
                thuoc.setSoLuongTon(thuoc.getSoLuongTon() - oldCtpn.getSoLuong());
                thuocDAO.update(thuoc);
            }

            pn.setChiTietPhieuNhap(chiTietPhieuNhapList);
            double tongTien = chiTietPhieuNhapList.stream()
                    .mapToDouble(ctpn -> ctpn.getSoLuong() * ctpn.getDonGia())
                    .sum();
            pn.setTongTien(tongTien);

            for (ChiTietPhieuNhap ctpn : chiTietPhieuNhapList) {
                ctpn.setPhieuNhap(pn);
                Thuoc thuoc = thuocDAO.getById(ctpn.getThuoc().getIdThuoc());
                thuoc.setSoLuongTon(thuoc.getSoLuongTon() + ctpn.getSoLuong());
                thuocDAO.update(thuoc);
            }

            phieuNhapDAO.update(pn);
            refreshTable();
            clearForm();
            JOptionPane.showMessageDialog(this, "Cập nhật phiếu nhập thành công!");
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
                    List<ChiTietPhieuNhap> chiTietList = phieuNhapDAO.getChiTietByPhieuNhap(pn.getIdPN());
                    for (ChiTietPhieuNhap ctpn : chiTietList) {
                        Thuoc thuoc = thuocDAO.getById(ctpn.getThuoc().getIdThuoc());
                        thuoc.setSoLuongTon(thuoc.getSoLuongTon() - ctpn.getSoLuong());
                        thuocDAO.update(thuoc);
                    }
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
        txtIdPN.setForeground(NEUTRAL_COLOR);
        cbNhaCungCap.setSelectedIndex(-1);
        dateChooserNgayNhap.setDate(null);
        txtSoLuong.setText("Nhập số lượng...");
        txtSoLuong.setForeground(NEUTRAL_COLOR);
        cbThuoc.setSelectedIndex(-1);
        selectedThuocList.clear();
        chiTietPhieuNhapList.clear();
        thuocTableModel.setRowCount(0);
        table.clearSelection();
    }
}