package view;

import dao.impl.PhieuNhapDAO;
import dao.impl.ChiTietPhieuNhapDAO;
import dao.impl.ThuocDAO;
import dao.impl.NhaCungCapDAO;
import entity.PhieuNhap;
import entity.ChiTietPhieuNhap;
import entity.Thuoc;
import entity.NhaCungCap;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
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
    private JTable detailTable;
    private DefaultTableModel detailTableModel;
    private JComboBox<Thuoc> cbThuoc;
    private JTextField txtSoLuong, txtDonGia;
    private List<PhieuNhap> phieuNhapList;
    private List<ChiTietPhieuNhap> chiTietList;
    private List<Thuoc> thuocList;
    private List<NhaCungCap> nhaCungCapList;
    private PhieuNhapDAO phieuNhapDAO;
    private ChiTietPhieuNhapDAO chiTietPhieuNhapDAO;
    private ThuocDAO thuocDAO;
    private NhaCungCapDAO nhaCungCapDAO;

    public PhieuNhapPanel() {
        try {
            setLayout(new BorderLayout());
            setBackground(new Color(248, 249, 250));
            phieuNhapList = new ArrayList<>();
            chiTietList = new ArrayList<>();
            thuocList = new ArrayList<>();
            nhaCungCapList = new ArrayList<>();

            // Khởi tạo DAO
            phieuNhapDAO = new PhieuNhapDAO();
            chiTietPhieuNhapDAO = new ChiTietPhieuNhapDAO();
            thuocDAO = new ThuocDAO();
            nhaCungCapDAO = new NhaCungCapDAO();

            // Load data
            loadDataFromDatabase();
            initComponents();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khởi tạo: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void initComponents() {
        add(createTopPanel(), BorderLayout.NORTH);
        add(createCenterPanel(), BorderLayout.CENTER);
        add(createRightPanel(), BorderLayout.EAST);
    }

    private JPanel createTopPanel() {
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(new Color(248, 249, 250));
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel searchPanel = new JPanel(new BorderLayout(10, 0));
        searchPanel.setBackground(new Color(248, 249, 250));

        txtSearch = new JTextField();
        txtSearch.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtSearch.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                BorderFactory.createEmptyBorder(8, 8, 8, 8)
        ));
        txtSearch.setPreferredSize(new Dimension(400, 35));

        JButton btnSearch = createStyledButton("Tìm kiếm", new Color(0, 102, 204));
        btnSearch.addActionListener(e -> searchPhieuNhap());

        searchPanel.add(txtSearch, BorderLayout.CENTER);
        searchPanel.add(btnSearch, BorderLayout.EAST);

        topPanel.add(searchPanel, BorderLayout.WEST);
        return topPanel;
    }

    private JPanel createCenterPanel() {
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBackground(new Color(248, 249, 250));

        String[] columns = {"Mã PN", "Nhà Cung Cấp", "Ngày Nhập"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table = new JTable(tableModel);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.setRowHeight(30);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        table.getTableHeader().setBackground(new Color(0, 102, 204));
        table.getTableHeader().setForeground(Color.WHITE);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));

        String[] detailColumns = {"Tên Thuốc", "Số Lượng", "Đơn Giá"};
        detailTableModel = new DefaultTableModel(detailColumns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        detailTable = new JTable(detailTableModel);
        detailTable.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        detailTable.setRowHeight(30);
        detailTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        detailTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        detailTable.getTableHeader().setBackground(new Color(0, 102, 204));
        detailTable.getTableHeader().setForeground(Color.WHITE);

        JScrollPane detailScrollPane = new JScrollPane(detailTable);
        detailScrollPane.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
        detailScrollPane.setPreferredSize(new Dimension(0, 200));

        centerPanel.add(scrollPane, BorderLayout.CENTER);
        centerPanel.add(detailScrollPane, BorderLayout.SOUTH);

        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && table.getSelectedRow() >= 0) {
                int selectedRow = table.getSelectedRow();
                PhieuNhap pn = phieuNhapList.get(selectedRow);
                txtIdPN.setText(pn.getIdPN());
                cbNhaCungCap.setSelectedItem(pn.getNhaCungCap());
                dateChooserNgayNhap.setDate(
                        pn.getThoiGian() != null ? Date.from(pn.getThoiGian().toInstant().atZone(ZoneId.systemDefault()).toLocalDate().atStartOfDay(ZoneId.systemDefault()).toInstant()) : null                );

                try {
                    refreshDetailTable(pn.getIdPN());
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(this, "Lỗi tải chi tiết: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        return centerPanel;
    }

    private JPanel createRightPanel() {
        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.setBackground(new Color(248, 249, 250));
        rightPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        rightPanel.setPreferredSize(new Dimension(350, 0));

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(new Color(255, 255, 255));
        formPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(new JLabel("Mã Phiếu Nhập:"), gbc);
        txtIdPN = createStyledTextField();
        gbc.gridx = 1;
        formPanel.add(txtIdPN, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        formPanel.add(new JLabel("Nhà Cung Cấp:"), gbc);
        cbNhaCungCap = new JComboBox<>(nhaCungCapList.toArray(new NhaCungCap[0]));
        cbNhaCungCap.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        gbc.gridx = 1;
        formPanel.add(cbNhaCungCap, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        formPanel.add(new JLabel("Ngày Nhập:"), gbc);
        dateChooserNgayNhap = createStyledDateChooser();
        gbc.gridx = 1;
        formPanel.add(dateChooserNgayNhap, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        formPanel.add(new JLabel("Thuốc:"), gbc);
        cbThuoc = new JComboBox<>(thuocList.toArray(new Thuoc[0]));
        cbThuoc.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        gbc.gridx = 1;
        formPanel.add(cbThuoc, gbc);

        gbc.gridx = 0; gbc.gridy = 4;
        formPanel.add(new JLabel("Số Lượng:"), gbc);
        txtSoLuong = createStyledTextField();
        gbc.gridx = 1;
        formPanel.add(txtSoLuong, gbc);

        gbc.gridx = 0; gbc.gridy = 5;
        formPanel.add(new JLabel("Đơn Giá:"), gbc);
        txtDonGia = createStyledTextField();
        gbc.gridx = 1;
        formPanel.add(txtDonGia, gbc);

        JButton btnAddDetail = createStyledButton("Thêm Chi Tiết", new Color(0, 153, 0));
        btnAddDetail.addActionListener(e -> addChiTiet());
        gbc.gridx = 0; gbc.gridy = 6; gbc.gridwidth = 2;
        formPanel.add(btnAddDetail, gbc);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        buttonPanel.setBackground(new Color(255, 255, 255));

        JButton btnAdd = createStyledButton("Thêm", new Color(0, 102, 204));
        JButton btnUpdate = createStyledButton("Sửa", new Color(0, 153, 0));
        JButton btnDelete = createStyledButton("Xóa", new Color(204, 0, 0));
        JButton btnClear = createStyledButton("Làm mới", new Color(100, 100, 100));

        btnAdd.addActionListener(e -> addPhieuNhap());
        btnUpdate.addActionListener(e -> updatePhieuNhap());
        btnDelete.addActionListener(e -> deletePhieuNhap());
        btnClear.addActionListener(e -> clearForm());

        buttonPanel.add(btnAdd);
        buttonPanel.add(btnUpdate);
        buttonPanel.add(btnDelete);
        buttonPanel.add(btnClear);

        gbc.gridx = 0; gbc.gridy = 7; gbc.gridwidth = 2;
        formPanel.add(buttonPanel, gbc);

        rightPanel.add(formPanel, BorderLayout.NORTH);
        return rightPanel;
    }

    private JTextField createStyledTextField() {
        JTextField textField = new JTextField();
        textField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        textField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                BorderFactory.createEmptyBorder(8, 8, 8, 8)
        ));
        return textField;
    }

    private JDateChooser createStyledDateChooser() {
        JDateChooser dateChooser = new JDateChooser();
        dateChooser.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        dateChooser.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                BorderFactory.createEmptyBorder(8, 8, 8, 8)
        ));
        dateChooser.setDateFormatString("dd/MM/yyyy");
        return dateChooser;
    }

    private JButton createStyledButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
        button.setFocusPainted(false);
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
        });

        return button;
    }

    private void loadDataFromDatabase() {
        try {
            phieuNhapList = phieuNhapDAO.getAll();
            chiTietList = chiTietPhieuNhapDAO.getAll();
            thuocList = thuocDAO.getAll();
            nhaCungCapList = nhaCungCapDAO.getAll();

            cbThuoc.setModel(new DefaultComboBoxModel<>(thuocList.toArray(new Thuoc[0])));
            cbNhaCungCap.setModel(new DefaultComboBoxModel<>(nhaCungCapList.toArray(new NhaCungCap[0])));

            refreshTable();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi tải dữ liệu: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
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

    private void refreshDetailTable(String idPN) throws Exception {
        detailTableModel.setRowCount(0);
        List<ChiTietPhieuNhap> details = phieuNhapDAO.getChiTietByPhieuNhap(idPN);
        for (ChiTietPhieuNhap ct : details) {
            detailTableModel.addRow(new Object[]{
                    ct.getThuoc().getTenThuoc(),
                    ct.getSoLuong(),
                    ct.getDonGia()
            });
        }
    }

    private void addPhieuNhap() {
        try {
            PhieuNhap pn = new PhieuNhap();
            pn.setIdPN(txtIdPN.getText());
            pn.setNhaCungCap((NhaCungCap) cbNhaCungCap.getSelectedItem());
            Date ngayNhap = dateChooserNgayNhap.getDate();
            pn.setThoiGian(ngayNhap != null ? new java.sql.Timestamp(ngayNhap.getTime()) : null);
            for (ChiTietPhieuNhap ct : chiTietList) {
                if (ct.getPhieuNhap().getIdPN().equals(pn.getIdPN())) {
                    pn.getChiTietPhieuNhap().add(ct);
                }
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
            if (selectedRow >= 0) {
                PhieuNhap pn = phieuNhapList.get(selectedRow);
                pn.setNhaCungCap((NhaCungCap) cbNhaCungCap.getSelectedItem());
                Date ngayNhap = dateChooserNgayNhap.getDate();
                pn.setThoiGian(ngayNhap != null ? new java.sql.Timestamp(ngayNhap.getTime()) : null);
                pn.getChiTietPhieuNhap().clear();
                for (ChiTietPhieuNhap ct : chiTietList) {
                    if (ct.getPhieuNhap().getIdPN().equals(pn.getIdPN())) {
                        pn.getChiTietPhieuNhap().add(ct);
                    }
                }

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
                    chiTietList.removeIf(ct -> ct.getPhieuNhap().getIdPN().equals(pn.getIdPN()));
                    refreshTable();
                    refreshDetailTable("");
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

    private void addChiTiet() {
        try {
            ChiTietPhieuNhap ct = new ChiTietPhieuNhap();
            PhieuNhap pn = new PhieuNhap();
            pn.setIdPN(txtIdPN.getText());
            ct.setPhieuNhap(pn);
            ct.setThuoc((Thuoc) cbThuoc.getSelectedItem());
            ct.setSoLuong(Integer.parseInt(txtSoLuong.getText()));
            ct.setDonGia(Double.parseDouble(txtDonGia.getText()));

            chiTietList.add(ct);
            refreshDetailTable(pn.getIdPN());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi: Vui lòng kiểm tra lại dữ liệu chi tiết! " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void searchPhieuNhap() {
        String searchText = txtSearch.getText().toLowerCase();
        try {
            phieuNhapList = phieuNhapDAO.findMany(
                    "SELECT pn FROM PhieuNhap pn WHERE LOWER(pn.idPN) LIKE ?1",
                    PhieuNhap.class, "%" + searchText + "%"
            );
            refreshTable();
            refreshDetailTable("");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi khi tìm kiếm: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void clearForm() {
        txtIdPN.setText("");
        cbNhaCungCap.setSelectedIndex(-1);
        dateChooserNgayNhap.setDate(null);
        cbThuoc.setSelectedIndex(-1);
        txtSoLuong.setText("");
        txtDonGia.setText("");
        table.clearSelection();
        detailTableModel.setRowCount(0);
        chiTietList.clear();
    }
}