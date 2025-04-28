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
    private static final Color PRIMARY_COLOR = new Color(33, 150, 243); // Blue
    private static final Color ERROR_COLOR = new Color(244, 67, 54); // Red
    private static final Color NEUTRAL_COLOR = new Color(120, 144, 156); // Gray
    private static final Font MAIN_FONT = new Font("Roboto", Font.PLAIN, 14);
    private static final Font TITLE_FONT = new Font("Roboto", Font.BOLD, 20);
    private static final Font BUTTON_FONT = new Font("Roboto", Font.BOLD, 14);

    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField txtSearch, txtIdPN, txtSoLuong;
    private JComboBox<NhaCungCap> cbNhaCungCap;
    private JComboBox<Thuoc> cbThuoc;
    private JDateChooser dateChooserNgayNhap;
    private List<PhieuNhap> phieuNhapList;
    private List<NhaCungCap> nhaCungCapList;
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

        // Input Panel (Row 0)
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

        // Input Frame
        JPanel inputFrame = new JPanel(new GridBagLayout());
        inputFrame.setBackground(Color.WHITE);
        inputFrame.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(189, 195, 199)), "Thông Tin Phiếu Nhập"));
        GridBagConstraints frameGbc = new GridBagConstraints();
        frameGbc.insets = new Insets(10, 10, 10, 10);
        frameGbc.fill = GridBagConstraints.HORIZONTAL;
        frameGbc.weightx = 1.0;

        // 2x3 Grid Layout for Input Fields
        frameGbc.gridx = 0; frameGbc.gridy = 0;
        inputFrame.add(new JLabel("Mã Phiếu Nhập:"), frameGbc);
        txtIdPN = createStyledTextField("Nhập mã phiếu nhập...");
        txtIdPN.setPreferredSize(new Dimension(200, 36));
        frameGbc.gridx = 1;
        inputFrame.add(txtIdPN, frameGbc);

        frameGbc.gridx = 2; frameGbc.gridy = 0;
        inputFrame.add(new JLabel("Ngày Nhập:"), frameGbc);
        dateChooserNgayNhap = createStyledDateChooser();
        dateChooserNgayNhap.setPreferredSize(new Dimension(200, 50));
        frameGbc.gridx = 3;
        inputFrame.add(dateChooserNgayNhap, frameGbc);

        frameGbc.gridx = 0; frameGbc.gridy = 1;
        inputFrame.add(new JLabel("Nhà Cung Cấp:"), frameGbc);
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
        frameGbc.gridx = 1;
        inputFrame.add(cbNhaCungCap, frameGbc);

        frameGbc.gridx = 2; frameGbc.gridy = 1;
        inputFrame.add(new JLabel("Thuốc:"), frameGbc);
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
        frameGbc.gridx = 3;
        inputFrame.add(cbThuoc, frameGbc);

        frameGbc.gridx = 0; frameGbc.gridy = 2;
        inputFrame.add(new JLabel("Số Lượng:"), frameGbc);
        txtSoLuong = createStyledTextField("Nhập số lượng...");
        txtSoLuong.setPreferredSize(new Dimension(200, 36));
        frameGbc.gridx = 1;
        inputFrame.add(txtSoLuong, frameGbc);

        inputGbc.gridx = 0; inputGbc.gridy = 1; inputGbc.gridwidth = 4; inputGbc.weightx = 1.0; inputGbc.weighty = 1.0;
        inputCard.add(inputFrame, inputGbc);

        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 1; gbc.weightx = 1.0; gbc.weighty = 0.2;
        add(inputCard, gbc);

        // Button Panel with Search (Row 1)
        JPanel buttonCard = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        buttonCard.setBackground(Color.WHITE);
        buttonCard.setBorder(new EmptyBorder(10, 10, 10, 10));
        txtSearch = createStyledTextField("Tìm kiếm phiếu nhập...");
        txtSearch.setPreferredSize(new Dimension(200, 36));
        JButton btnSearch = createStyledButton("Tìm", PRIMARY_COLOR, "Tìm kiếm phiếu nhập");
        btnSearch.addActionListener(e -> searchPhieuNhap());
        JButton btnAdd = createStyledButton("Thêm", PRIMARY_COLOR, "Thêm phiếu nhập mới");
        JButton btnUpdate = createStyledButton("Sửa", new Color(46, 204, 113), "Chỉnh sửa phiếu nhập");
        JButton btnDelete = createStyledButton("Xóa", ERROR_COLOR, "Xóa phiếu nhập");
        JButton btnClear = createStyledButton("Làm mới", NEUTRAL_COLOR, "Xóa toàn bộ dữ liệu nhập");
        btnAdd.addActionListener(e -> addPhieuNhap());
        btnUpdate.addActionListener(e -> updatePhieuNhap());
        btnDelete.addActionListener(e -> deletePhieuNhap());
        btnClear.addActionListener(e -> clearForm());
        buttonCard.add(txtSearch);
        buttonCard.add(btnSearch);
        buttonCard.add(btnAdd);
        buttonCard.add(btnUpdate);
        buttonCard.add(btnDelete);
        buttonCard.add(btnClear);
        gbc.gridx = 0; gbc.gridy = 1; gbc.gridwidth = 1; gbc.weightx = 1.0; gbc.weighty = 0.05;
        add(buttonCard, gbc);

        // Table Panel (Row 2)
        JPanel tableCard = new JPanel(new BorderLayout());
        tableCard.setBackground(Color.WHITE);
        tableCard.setBorder(new EmptyBorder(10, 10, 10, 10));
        String[] columns = {"Mã PN", "Nhà Cung Cấp", "Ngày Nhập", "Tên Thuốc", "Số Lượng"};
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
        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 1; gbc.weightx = 1.0; gbc.weighty = 0.75; // Larger weighty for table
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
                List<ChiTietPhieuNhap> chiTietList = phieuNhapDAO.getChiTietByPhieuNhap(pn.getIdPN());
                if (!chiTietList.isEmpty()) {
                    ChiTietPhieuNhap ctpn = chiTietList.get(0); // Assume one medicine per phiếu nhập
                    cbThuoc.setSelectedItem(ctpn.getThuoc());
                    txtSoLuong.setText(String.valueOf(ctpn.getSoLuong()));
                } else {
                    cbThuoc.setSelectedIndex(-1);
                    txtSoLuong.setText("Nhập số lượng...");
                    txtSoLuong.setForeground(NEUTRAL_COLOR);
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
            List<ChiTietPhieuNhap> chiTietList = phieuNhapDAO.getChiTietByPhieuNhap(pn.getIdPN());
            String tenThuoc = chiTietList.isEmpty() ? "Không có thuốc" : chiTietList.get(0).getThuoc().getTenThuoc();
            int soLuong = chiTietList.isEmpty() ? 0 : chiTietList.get(0).getSoLuong();
            tableModel.addRow(new Object[]{
                    pn.getIdPN(),
                    pn.getNhaCungCap() != null ? pn.getNhaCungCap().getTenNCC() : "",
                    pn.getThoiGian(),
                    tenThuoc,
                    soLuong
            });
        }
    }

    private void addPhieuNhap() {
        try {
            // Validate inputs
            if (txtIdPN.getText().equals("Nhập mã phiếu nhập...") || txtIdPN.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập mã phiếu nhập!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (cbNhaCungCap.getSelectedItem() == null) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn nhà cung cấp!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (dateChooserNgayNhap.getDate() == null) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn ngày nhập!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }
            Thuoc selectedThuoc = (Thuoc) cbThuoc.getSelectedItem();
            if (selectedThuoc == null) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn một thuốc!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }
            String soLuongStr = txtSoLuong.getText();
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

            // Create PhieuNhap
            PhieuNhap pn = new PhieuNhap();
            pn.setIdPN(txtIdPN.getText());
            pn.setNhaCungCap((NhaCungCap) cbNhaCungCap.getSelectedItem());
            Date ngayNhap = dateChooserNgayNhap.getDate();
            pn.setThoiGian(ngayNhap != null ? new java.sql.Timestamp(ngayNhap.getTime()) : null);

            // Create ChiTietPhieuNhap
            ChiTietPhieuNhap ctpn = new ChiTietPhieuNhap();
            ctpn.setThuoc(selectedThuoc);
            ctpn.setSoLuong(soLuong);
            ctpn.setDonGia(selectedThuoc.getGiaNhap());
            ctpn.setPhieuNhap(pn);
            chiTietPhieuNhapList = new ArrayList<>();
            chiTietPhieuNhapList.add(ctpn);

            pn.setChiTietPhieuNhap(chiTietPhieuNhapList);
            double tongTien = soLuong * selectedThuoc.getGiaNhap();
            pn.setTongTien(tongTien);

            // Update medicine stock
            Thuoc thuoc = thuocDAO.getById(selectedThuoc.getIdThuoc());
            thuoc.setSoLuongTon(thuoc.getSoLuongTon() + soLuong);
            thuocDAO.update(thuoc);

            // Save to database
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
            if (cbNhaCungCap.getSelectedItem() == null) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn nhà cung cấp!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (dateChooserNgayNhap.getDate() == null) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn ngày nhập!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }
            Thuoc selectedThuoc = (Thuoc) cbThuoc.getSelectedItem();
            if (selectedThuoc == null) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn một thuốc!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }
            String soLuongStr = txtSoLuong.getText();
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

            PhieuNhap pn = phieuNhapList.get(selectedRow);
            pn.setNhaCungCap((NhaCungCap) cbNhaCungCap.getSelectedItem());
            Date ngayNhap = dateChooserNgayNhap.getDate();
            pn.setThoiGian(ngayNhap != null ? new java.sql.Timestamp(ngayNhap.getTime()) : null);

            // Revert old stock
            List<ChiTietPhieuNhap> oldChiTietList = phieuNhapDAO.getChiTietByPhieuNhap(pn.getIdPN());
            for (ChiTietPhieuNhap oldCtpn : oldChiTietList) {
                Thuoc thuoc = thuocDAO.getById(oldCtpn.getThuoc().getIdThuoc());
                thuoc.setSoLuongTon(thuoc.getSoLuongTon() - oldCtpn.getSoLuong());
                thuocDAO.update(thuoc);
            }

            // Create new ChiTietPhieuNhap
            ChiTietPhieuNhap ctpn = new ChiTietPhieuNhap();
            ctpn.setThuoc(selectedThuoc);
            ctpn.setSoLuong(soLuong);
            ctpn.setDonGia(selectedThuoc.getGiaNhap());
            ctpn.setPhieuNhap(pn);
            chiTietPhieuNhapList = new ArrayList<>();
            chiTietPhieuNhapList.add(ctpn);

            pn.setChiTietPhieuNhap(chiTietPhieuNhapList);
            double tongTien = soLuong * selectedThuoc.getGiaNhap();
            pn.setTongTien(tongTien);

            // Update stock
            Thuoc thuoc = thuocDAO.getById(selectedThuoc.getIdThuoc());
            thuoc.setSoLuongTon(thuoc.getSoLuongTon() + soLuong);
            thuocDAO.update(thuoc);

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
        txtSearch.setText("Tìm kiếm phiếu nhập...");
        txtSearch.setForeground(NEUTRAL_COLOR);
        table.clearSelection();
    }
}