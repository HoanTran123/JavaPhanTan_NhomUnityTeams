package view;

import entity.KhuyenMai;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import com.toedter.calendar.JDateChooser;

public class KhuyenMaiPanel extends JPanel {
    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField txtSearch, txtIdKM, txtTenKM, txtPhanTramGiam, txtHangMuc, txtMoTa;
    private JDateChooser dateChooserNgayBatDau, dateChooserNgayKetThuc; // Thay JFormattedTextField bằng JDateChooser
    private List<KhuyenMai> khuyenMaiList;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    private EntityManagerFactory emf;
    private EntityManager em;

    public KhuyenMaiPanel() {
        setLayout(new BorderLayout());
        setBackground(new Color(248, 249, 250));
        khuyenMaiList = new ArrayList<>();

        // Khởi tạo EntityManagerFactory và EntityManager
        emf = Persistence.createEntityManagerFactory("mariadb");
        em = emf.createEntityManager();

        // Initialize components
        add(createTopPanel(), BorderLayout.NORTH);
        add(createCenterPanel(), BorderLayout.CENTER);
        add(createRightPanel(), BorderLayout.EAST);

        // Load data from database
        loadDataFromDatabase();
        refreshTable();
    }

    @Override
    public void removeNotify() {
        super.removeNotify();
        if (em != null && em.isOpen()) {
            em.close();
        }
        if (emf != null && emf.isOpen()) {
            emf.close();
        }
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
        // Kéo dài ô nhập tìm kiếm
        txtSearch.setPreferredSize(new Dimension(400, 35)); // Tăng chiều rộng lên 400px

        JButton btnSearch = createStyledButton("Tìm kiếm", new Color(0, 102, 204));
        btnSearch.addActionListener(e -> searchKhuyenMai());

        searchPanel.add(txtSearch, BorderLayout.CENTER);
        searchPanel.add(btnSearch, BorderLayout.EAST);

        topPanel.add(searchPanel, BorderLayout.WEST);
        return topPanel;
    }

    private JPanel createCenterPanel() {
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBackground(new Color(248, 249, 250));

        String[] columns = {"ID", "Tên KM", "Ngày Bắt Đầu", "Ngày Kết Thúc", "Phần Trăm Giảm", "Hạng Mục", "Mô Tả"};
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

        centerPanel.add(scrollPane, BorderLayout.CENTER);
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
        formPanel.add(new JLabel("ID Khuyến Mãi:"), gbc);
        txtIdKM = createStyledTextField();
        gbc.gridx = 1;
        formPanel.add(txtIdKM, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        formPanel.add(new JLabel("Tên Khuyến Mãi:"), gbc);
        txtTenKM = createStyledTextField();
        gbc.gridx = 1;
        formPanel.add(txtTenKM, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        formPanel.add(new JLabel("Ngày Bắt Đầu:"), gbc);
        dateChooserNgayBatDau = createStyledDateChooser(); // Sử dụng JDateChooser
        gbc.gridx = 1;
        formPanel.add(dateChooserNgayBatDau, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        formPanel.add(new JLabel("Ngày Kết Thúc:"), gbc);
        dateChooserNgayKetThuc = createStyledDateChooser(); // Sử dụng JDateChooser
        gbc.gridx = 1;
        formPanel.add(dateChooserNgayKetThuc, gbc);

        gbc.gridx = 0; gbc.gridy = 4;
        formPanel.add(new JLabel("Phần Trăm Giảm:"), gbc);
        txtPhanTramGiam = createStyledTextField();
        gbc.gridx = 1;
        formPanel.add(txtPhanTramGiam, gbc);

        gbc.gridx = 0; gbc.gridy = 5;
        formPanel.add(new JLabel("Hạng Mục:"), gbc);
        txtHangMuc = createStyledTextField();
        gbc.gridx = 1;
        formPanel.add(txtHangMuc, gbc);

        gbc.gridx = 0; gbc.gridy = 6;
        formPanel.add(new JLabel("Mô Tả:"), gbc);
        txtMoTa = createStyledTextField();
        gbc.gridx = 1;
        formPanel.add(txtMoTa, gbc);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        buttonPanel.setBackground(new Color(255, 255, 255));

        JButton btnAdd = createStyledButton("Thêm", new Color(0, 102, 204));
        JButton btnUpdate = createStyledButton("Sửa", new Color(0, 153, 0));
        JButton btnDelete = createStyledButton("Xóa", new Color(204, 0, 0));
        JButton btnClear = createStyledButton("Làm mới", new Color(100, 100, 100));

        btnAdd.addActionListener(e -> addKhuyenMai());
        btnUpdate.addActionListener(e -> updateKhuyenMai());
        btnDelete.addActionListener(e -> deleteKhuyenMai());
        btnClear.addActionListener(e -> clearForm());

        buttonPanel.add(btnAdd);
        buttonPanel.add(btnUpdate);
        buttonPanel.add(btnDelete);
        buttonPanel.add(btnClear);

        gbc.gridx = 0; gbc.gridy = 7; gbc.gridwidth = 2;
        formPanel.add(buttonPanel, gbc);

        rightPanel.add(formPanel, BorderLayout.NORTH);

        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && table.getSelectedRow() >= 0) {
                int selectedRow = table.getSelectedRow();
                KhuyenMai km = khuyenMaiList.get(selectedRow);
                txtIdKM.setText(km.getIdKM());
                txtTenKM.setText(km.getTenKhuyenMai());
                dateChooserNgayBatDau.setDate(km.getNgayBatDau());
                dateChooserNgayKetThuc.setDate(km.getNgayKetThuc());
                txtPhanTramGiam.setText(String.valueOf(km.getPhanTramGiam()));
                txtHangMuc.setText(km.getHangMuc());
                txtMoTa.setText(km.getMoTa());
            }
        });

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
        khuyenMaiList = em.createQuery("SELECT km FROM KhuyenMai km", KhuyenMai.class).getResultList();
    }

    private void refreshTable() {
        tableModel.setRowCount(0);
        for (KhuyenMai km : khuyenMaiList) {
            tableModel.addRow(new Object[]{
                    km.getIdKM(),
                    km.getTenKhuyenMai(),
                    km.getNgayBatDau() != null ? dateFormat.format(km.getNgayBatDau()) : "",
                    km.getNgayKetThuc() != null ? dateFormat.format(km.getNgayKetThuc()) : "",
                    km.getPhanTramGiam(),
                    km.getHangMuc(),
                    km.getMoTa()
            });
        }
    }

    private void addKhuyenMai() {
        try {
            KhuyenMai km = new KhuyenMai();
            km.setIdKM(txtIdKM.getText());
            km.setTenKhuyenMai(txtTenKM.getText());
            km.setNgayBatDau(dateChooserNgayBatDau.getDate());
            km.setNgayKetThuc(dateChooserNgayKetThuc.getDate());
            km.setPhanTramGiam(Double.parseDouble(txtPhanTramGiam.getText()));
            km.setHangMuc(txtHangMuc.getText());
            km.setMoTa(txtMoTa.getText());

            em.getTransaction().begin();
            em.persist(km);
            em.getTransaction().commit();

            khuyenMaiList.add(km);
            refreshTable();
            clearForm();
            JOptionPane.showMessageDialog(this, "Thêm khuyến mãi thành công!");
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            JOptionPane.showMessageDialog(this, "Lỗi: Vui lòng kiểm tra lại dữ liệu! " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void updateKhuyenMai() {
        try {
            int selectedRow = table.getSelectedRow();
            if (selectedRow >= 0) {
                KhuyenMai km = khuyenMaiList.get(selectedRow);
                km.setTenKhuyenMai(txtTenKM.getText());
                km.setNgayBatDau(dateChooserNgayBatDau.getDate());
                km.setNgayKetThuc(dateChooserNgayKetThuc.getDate());
                km.setPhanTramGiam(Double.parseDouble(txtPhanTramGiam.getText()));
                km.setHangMuc(txtHangMuc.getText());
                km.setMoTa(txtMoTa.getText());

                em.getTransaction().begin();
                em.merge(km);
                em.getTransaction().commit();

                refreshTable();
                clearForm();
                JOptionPane.showMessageDialog(this, "Cập nhật khuyến mãi thành công!");
            } else {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn một khuyến mãi để sửa!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            }
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            JOptionPane.showMessageDialog(this, "Lỗi: Vui lòng kiểm tra lại dữ liệu! " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void deleteKhuyenMai() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow >= 0) {
            int confirm = JOptionPane.showConfirmDialog(this,
                    "Bạn có chắc muốn xóa khuyến mãi này?",
                    "Xác nhận xóa",
                    JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                try {
                    KhuyenMai km = khuyenMaiList.get(selectedRow);

                    em.getTransaction().begin();
                    km = em.merge(km);
                    em.remove(km);
                    em.getTransaction().commit();

                    khuyenMaiList.remove(selectedRow);
                    refreshTable();
                    clearForm();
                    JOptionPane.showMessageDialog(this, "Xóa khuyến mãi thành công!");
                } catch (Exception e) {
                    if (em.getTransaction().isActive()) {
                        em.getTransaction().rollback();
                    }
                    JOptionPane.showMessageDialog(this, "Lỗi khi xóa khuyến mãi: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
                    e.printStackTrace();
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một khuyến mãi để xóa!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void searchKhuyenMai() {
        String searchText = txtSearch.getText().toLowerCase();
        try {
            khuyenMaiList = em.createQuery(
                            "SELECT km FROM KhuyenMai km WHERE LOWER(km.idKM) LIKE :search OR LOWER(km.tenKhuyenMai) LIKE :search OR LOWER(km.hangMuc) LIKE :search",
                            KhuyenMai.class
                    )
                    .setParameter("search", "%" + searchText + "%")
                    .getResultList();
            refreshTable();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi khi tìm kiếm: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void clearForm() {
        txtIdKM.setText("");
        txtTenKM.setText("");
        dateChooserNgayBatDau.setDate(null);
        dateChooserNgayKetThuc.setDate(null);
        txtPhanTramGiam.setText("");
        txtHangMuc.setText("");
        txtMoTa.setText("");
        table.clearSelection();
    }
}