package view;

import dao.impl.NhanVienDAO;
import entity.NhanVien;
import util.HibernateUtil;
import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;
import jakarta.persistence.EntityManager;

public class NhanVienPanel extends JPanel {
    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField txtMaNV, txtHoTen, txtSDT, txtEmail, txtNamSinh;
    private JDateChooser txtNgayVaoLam;
    private JComboBox<String> cboGioiTinh, cboChucVu;
    private NhanVienDAO nhanVienDao;
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");

    public NhanVienPanel() {
        try {
            nhanVienDao = new NhanVienDAO();
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

        // Search panel
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchPanel.setBackground(Color.WHITE);

        JLabel lblSearch = new JLabel("Tìm kiếm:");
        lblSearch.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblSearch.setForeground(new Color(70, 70, 70));

        JTextField txtSearch = createStyledTextField(true);
        txtSearch.setPreferredSize(new Dimension(800, 36)); // Reduced width to prevent stretching

        JButton btnSearch = createActionButton("Tìm", new Color(0, 123, 255));
        btnSearch.addActionListener(e -> searchNhanVien(txtSearch.getText().trim()));

        searchPanel.add(lblSearch);
        searchPanel.add(txtSearch);
        searchPanel.add(btnSearch);

        // Form fields
        JPanel gridPanel = new JPanel(new GridLayout(2, 4, 15, 15));
        gridPanel.setBackground(Color.WHITE);

        txtMaNV = createStyledTextField(false);
        txtHoTen = createStyledTextField(true);
        txtSDT = createStyledTextField(true);
        txtEmail = createStyledTextField(true);
        cboGioiTinh = createStyledComboBox(new String[]{"Nam", "Nữ", "Không xác định"});
        txtNamSinh = createStyledTextField(true);
        txtNgayVaoLam = createStyledDateChooser();
        cboChucVu = createStyledComboBox(new String[]{"Nhân viên", "Quản lý"});

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
        gridPanel.add(createFieldPanel(labels[7], cboChucVu));

        formPanel.add(Box.createVerticalStrut(20));
        formPanel.add(searchPanel);
        formPanel.add(Box.createVerticalStrut(20));
        formPanel.add(gridPanel);
        return formPanel;
    }    private JPanel createFieldPanel(JLabel label, JComponent field) {
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

    private JDateChooser createStyledDateChooser() {
        JDateChooser dateChooser = new JDateChooser();
        dateChooser.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        dateChooser.setBorder(BorderFactory.createCompoundBorder(
                new MatteBorder(1, 1, 1, 1, new Color(220, 220, 220)),
                new EmptyBorder(10, 12, 10, 12)
        ));
        dateChooser.setDateFormatString("dd/MM/yyyy");
        dateChooser.setMaxSelectableDate(new Date()); // Prevent future dates
        return dateChooser;
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

    // Helper method to validate input fields
    private boolean validateNhanVienInput(String hoTen, String sdt, String email, String namSinhText, String chucVu, Date ngayVaoLam, String maNV, boolean isAdd) {
        if (hoTen.isEmpty() || sdt.isEmpty() || email.isEmpty() || namSinhText.isEmpty() || chucVu == null || ngayVaoLam == null) {
            showError("Vui lòng nhập đầy đủ thông tin!");
            return false;
        }

        // Validate phone number
        if (!sdt.matches("\\d{10,11}")) {
            showError("Số điện thoại phải có 10-11 chữ số!");
            return false;
        }

        // Validate email
        String emailRegex = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
        if (!Pattern.matches(emailRegex, email)) {
            showError("Email không hợp lệ!");
            return false;
        }

        // Validate birth year
        int namSinh;
        try {
            namSinh = Integer.parseInt(namSinhText);
            int currentYear = java.time.Year.now().getValue();
            if (namSinh < 1900 || namSinh > currentYear) {
                showError("Năm sinh phải nằm trong khoảng từ 1900 đến " + currentYear + "!");
                return false;
            }
        } catch (NumberFormatException e) {
            showError("Năm sinh phải là số nguyên hợp lệ!");
            return false;
        }

        // Validate ngayVaoLam
        Date currentDate = new Date();
        if (ngayVaoLam.after(currentDate)) {
            showError("Ngày vào làm không được lớn hơn ngày hiện tại!");
            return false;
        }

        // Check for duplicate email or phone number
        EntityManager em = null;
        try {
            em = HibernateUtil.getEntityManager();
            nhanVienDao.setEntityManager(em);
            // Check email
            String emailQuery = isAdd ? "SELECT COUNT(n) FROM NhanVien n WHERE n.email = :email" :
                    "SELECT COUNT(n) FROM NhanVien n WHERE n.email = :email AND n.idNV != :maNV";
            var emailQueryObj = em.createQuery(emailQuery, Long.class)
                    .setParameter("email", email);
            if (!isAdd) {
                emailQueryObj.setParameter("maNV", maNV);
            }
            long emailCount = emailQueryObj.getSingleResult();
            if (emailCount > 0) {
                showError("Email đã được sử dụng!");
                return false;
            }
            // Check phone number
            String sdtQuery = isAdd ? "SELECT COUNT(n) FROM NhanVien n WHERE n.sdt = :sdt" :
                    "SELECT COUNT(n) FROM NhanVien n WHERE n.sdt = :sdt AND n.idNV != :maNV";
            var sdtQueryObj = em.createQuery(sdtQuery, Long.class)
                    .setParameter("sdt", sdt);
            if (!isAdd) {
                sdtQueryObj.setParameter("maNV", maNV);
            }
            long sdtCount = sdtQueryObj.getSingleResult();
            if (sdtCount > 0) {
                showError("Số điện thoại đã được sử dụng!");
                return false;
            }
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }

        return true;
    }

    private void addNhanVien() {
        EntityManager em = null;
        try {
            // Get input fields
            String hoTen = txtHoTen.getText().trim();
            String sdt = txtSDT.getText().trim();
            String email = txtEmail.getText().trim();
            String gioiTinh = cboGioiTinh.getSelectedItem().toString();
            String namSinhText = txtNamSinh.getText().trim();
            Date ngayVaoLam = txtNgayVaoLam.getDate();
            String chucVu = cboChucVu.getSelectedItem().toString();

            // Validate inputs
            if (!validateNhanVienInput(hoTen, sdt, email, namSinhText, chucVu, ngayVaoLam, null, true)) {
                return;
            }

            // Create and populate NhanVien object
            NhanVien nv = new NhanVien();
            String maNV = generateEmployeeId();
            nv.setIdNV(maNV);
            nv.setHoTen(hoTen);
            nv.setSdt(sdt);
            nv.setEmail(email);
            nv.setGioiTinh(gioiTinh);
            nv.setNamSinh(Integer.parseInt(namSinhText));
            nv.setNgayVaoLam(ngayVaoLam);
            nv.setChucVu(chucVu);

            // Save to database
            em = HibernateUtil.getEntityManager();
            nhanVienDao.setEntityManager(em);
            em.getTransaction().begin();
            try {
                boolean success = nhanVienDao.save(nv);
                em.getTransaction().commit();
                if (success) {
                    loadNhanVien();
                    showSuccess("Thêm nhân viên thành công!");
                } else {
                    showError("Thêm nhân viên thất bại!");
                }
            } catch (Exception e) {
                em.getTransaction().rollback();
                throw e;
            }
        } catch (Exception e) {
            e.printStackTrace();
            showError("Lỗi khi thêm nhân viên: " + e.getMessage());
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }

    private String generateEmployeeId() {
        return "NV" + System.currentTimeMillis();
    }

    private void updateNhanVien() {
        EntityManager em = null;
        try {
            int selectedRow = table.getSelectedRow();
            if (selectedRow == -1) {
                showError("Vui lòng chọn nhân viên để cập nhật!");
                return;
            }

            String maNV = txtMaNV.getText().trim();
            if (maNV.isEmpty()) {
                showError("Mã nhân viên không hợp lệ!");
                return;
            }

            // Get input fields
            String hoTen = txtHoTen.getText().trim();
            String sdt = txtSDT.getText().trim();
            String email = txtEmail.getText().trim();
            String gioiTinh = cboGioiTinh.getSelectedItem().toString();
            String namSinhText = txtNamSinh.getText().trim();
            Date ngayVaoLam = txtNgayVaoLam.getDate();
            String chucVu = cboChucVu.getSelectedItem().toString();

            // Validate inputs
            if (!validateNhanVienInput(hoTen, sdt, email, namSinhText, chucVu, ngayVaoLam, maNV, false)) {
                return;
            }

            // Fetch existing NhanVien
            em = HibernateUtil.getEntityManager();
            nhanVienDao.setEntityManager(em);
            NhanVien nv = nhanVienDao.getById(maNV);
            if (nv == null) {
                showError("Nhân viên không tồn tại!");
                return;
            }

            // Update NhanVien
            nv.setHoTen(hoTen);
            nv.setSdt(sdt);
            nv.setEmail(email);
            nv.setGioiTinh(gioiTinh);
            nv.setNamSinh(Integer.parseInt(namSinhText));
            nv.setNgayVaoLam(ngayVaoLam);
            nv.setChucVu(chucVu);

            // Save to database
            em.getTransaction().begin();
            try {
                boolean success = nhanVienDao.updateWithoutTransaction(nv);
                em.getTransaction().commit();
                if (success) {
                    loadNhanVien();
                    showSuccess("Cập nhật nhân viên thành công!");
                } else {
                    showError("Cập nhật nhân viên thất bại!");
                }
            } catch (Exception e) {
                em.getTransaction().rollback();
                throw e;
            }
        } catch (Exception e) {
            e.printStackTrace();
            showError("Lỗi khi cập nhật nhân viên: " + e.getMessage());
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }

    private void deleteNhanVien() {
        EntityManager em = null;
        try {
            int selectedRow = table.getSelectedRow();
            if (selectedRow == -1) {
                showError("Vui lòng chọn nhân viên để xóa!");
                return;
            }

            String maNV = tableModel.getValueAt(selectedRow, 0).toString();

            // Create a new EntityManager for this operation
            em = HibernateUtil.getEntityManager();
            nhanVienDao.setEntityManager(em);

            // Fetch NhanVien
            NhanVien nv = nhanVienDao.getById(maNV);
            if (nv == null) {
                showError("Nhân viên không tồn tại!");
                return;
            }

            // Check for critical records
            long chiTietHoaDonCount = (Long) em.createQuery("SELECT COUNT(ct) FROM ChiTietHoaDon ct WHERE ct.hoaDon IN (SELECT h FROM HoaDon h WHERE h.nhanVien.idNV = :maNV)")
                    .setParameter("maNV", maNV)
                    .getSingleResult();
            long phieuNhapCount = (Long) em.createQuery("SELECT COUNT(pn) FROM PhieuNhap pn WHERE pn.nhanVien.idNV = :maNV")
                    .setParameter("maNV", maNV)
                    .getSingleResult();
            long chiTietPhieuNhapCount = (Long) em.createQuery("SELECT COUNT(ctpn) FROM ChiTietPhieuNhap ctpn WHERE ctpn.phieuNhap IN (SELECT pn FROM PhieuNhap pn WHERE pn.nhanVien.idNV = :maNV)")
                    .setParameter("maNV", maNV)
                    .getSingleResult();
            if (chiTietHoaDonCount > 0 || phieuNhapCount > 0 || chiTietPhieuNhapCount > 0) {
                int confirmCritical = JOptionPane.showConfirmDialog(this,
                        "Nhân viên này có " + chiTietHoaDonCount + " chi tiết hóa đơn, " + phieuNhapCount + " phiếu nhập, và " + chiTietPhieuNhapCount + " chi tiết phiếu nhập liên quan. Bạn vẫn muốn xóa?",
                        "Cảnh báo xóa",
                        JOptionPane.YES_NO_OPTION);
                if (confirmCritical != JOptionPane.YES_OPTION) {
                    return;
                }
            }

            int confirm = JOptionPane.showConfirmDialog(this,
                    "Bạn có chắc chắn muốn xóa nhân viên " + nv.getHoTen() + "? Tài khoản, hóa đơn, chi tiết hóa đơn, phiếu nhập và chi tiết phiếu nhập liên quan cũng sẽ bị xóa!",
                    "Xác nhận xóa",
                    JOptionPane.YES_NO_OPTION);
            if (confirm != JOptionPane.YES_OPTION) {
                return;
            }

            // Begin transaction
            em.getTransaction().begin();
            try {
                // Delete related records
                System.out.println("Deleting related records for NhanVien: " + maNV);
                nhanVienDao.deleteRelatedTaiKhoan(maNV);
                System.out.println("Deleted TaiKhoan for NhanVien: " + maNV);
                nhanVienDao.deleteRelatedHoaDon(maNV);
                System.out.println("Deleted HoaDon and ChiTietHoaDon for NhanVien: " + maNV);
                nhanVienDao.deleteRelatedPhieuNhap(maNV);
                System.out.println("Deleted PhieuNhap and ChiTietPhieuNhap for NhanVien: " + maNV);
                boolean success = nhanVienDao.deleteWithoutTransaction(nv);
                System.out.println("Deleted NhanVien: " + maNV + ", Success: " + success);
                em.getTransaction().commit();
                System.out.println("Transaction committed for NhanVien: " + maNV);

                if (success) {
                    loadNhanVien();
                    showSuccess("Xóa nhân viên thành công!");
                } else {
                    showError("Xóa nhân viên thất bại!");
                }
            } catch (Exception e) {
                em.getTransaction().rollback();
                e.printStackTrace();
                showError("Lỗi khi xóa nhân viên: " + e.getMessage());
            }
        } catch (Exception e) {
            e.printStackTrace();
            showError("Lỗi khi xóa nhân viên: " + e.getMessage());
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }

    private void loadNhanVien() {
        EntityManager em = null;
        try {
            em = HibernateUtil.getEntityManager();
            nhanVienDao.setEntityManager(em);

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
            e.printStackTrace();
            showError("Lỗi khi tải dữ liệu: " + e.getMessage());
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }
    private void searchNhanVien(String searchTerm) {
        EntityManager em = null;
        try {
            em = HibernateUtil.getEntityManager();
            nhanVienDao.setEntityManager(em);

            tableModel.setRowCount(0); // Clear current table
            if (searchTerm.isEmpty()) {
                loadNhanVien(); // If search term is empty, load all employees
                return;
            }

            // Create a JPQL query for searching
            String jpql = "SELECT n FROM NhanVien n WHERE LOWER(n.idNV) LIKE :searchTerm " +
                    "OR LOWER(n.hoTen) LIKE :searchTerm " +
                    "OR LOWER(n.sdt) LIKE :searchTerm " +
                    "OR LOWER(n.email) LIKE :searchTerm";
            List<NhanVien> list = em.createQuery(jpql, NhanVien.class)
                    .setParameter("searchTerm", "%" + searchTerm.toLowerCase() + "%")
                    .getResultList();

            // Populate table with search results
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

            if (list.isEmpty()) {
                showError("Không tìm thấy nhân viên nào khớp với từ khóa!");
            }

            clearForm(); // Clear form after search
        } catch (Exception e) {
            e.printStackTrace();
            showError("Lỗi khi tìm kiếm nhân viên: " + e.getMessage());
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }
    private void populateFormFromTable() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) return;

        txtMaNV.setText(getSafeString(tableModel.getValueAt(selectedRow, 0)));
        txtHoTen.setText(getSafeString(tableModel.getValueAt(selectedRow, 1)));
        txtSDT.setText(getSafeString(tableModel.getValueAt(selectedRow, 2)));
        txtEmail.setText(getSafeString(tableModel.getValueAt(selectedRow, 3)));
        cboGioiTinh.setSelectedItem(getSafeString(tableModel.getValueAt(selectedRow, 4)));
        txtNamSinh.setText(getSafeString(tableModel.getValueAt(selectedRow, 5)));
        String ngayVaoLamStr = getSafeString(tableModel.getValueAt(selectedRow, 6));
        if (!ngayVaoLamStr.isEmpty()) {
            try {
                txtNgayVaoLam.setDate(DATE_FORMAT.parse(ngayVaoLamStr));
            } catch (Exception e) {
                txtNgayVaoLam.setDate(null);
            }
        } else {
            txtNgayVaoLam.setDate(null);
        }
        cboChucVu.setSelectedItem(getSafeString(tableModel.getValueAt(selectedRow, 7)));
    }

    private String getSafeString(Object value) {
        return value != null ? value.toString() : "";
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
        txtNgayVaoLam.setDate(null);
        cboChucVu.setSelectedIndex(0);
    }
}