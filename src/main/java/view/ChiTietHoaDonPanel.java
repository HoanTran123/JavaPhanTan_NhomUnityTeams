package view;

import dao.impl.ChiTietHoaDonDAO;
import entity.ChiTietHoaDon;
import entity.HoaDon;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.rmi.RemoteException;
import java.util.List;

public class ChiTietHoaDonPanel extends JPanel {
    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField txtMaHD, txtTongTien, txtNgayLap;
    private ChiTietHoaDonDAO chiTietHoaDonDao;
    private HoaDon hoaDon;

    public ChiTietHoaDonPanel(HoaDon hoaDon) throws RemoteException {
        this.hoaDon = hoaDon;
        this.chiTietHoaDonDao = new ChiTietHoaDonDAO();

        setLayout(new BorderLayout(10, 10));
        setBackground(new Color(248, 249, 250));

        add(createHeaderPanel(), BorderLayout.NORTH);
        add(createTablePanel(), BorderLayout.CENTER);
        add(createFooterPanel(), BorderLayout.SOUTH);

        loadChiTietHoaDon();
    }

    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel(new GridLayout(1, 3, 10, 10));
        headerPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        headerPanel.setBackground(Color.WHITE);

        txtMaHD = createTextField("Mã Hóa Đơn: " + hoaDon.getIdHD());
        txtNgayLap = createTextField("Ngày Lập: " + (hoaDon.getThoiGian() != null ? hoaDon.getThoiGian() : "N/A"));
        txtTongTien = createTextField("Tổng Tiền: " + (hoaDon.getTongTien() != null ? hoaDon.getTongTien() : "0.0"));

        headerPanel.add(txtMaHD);
        headerPanel.add(txtNgayLap);
        headerPanel.add(txtTongTien);

        return headerPanel;
    }

    private JScrollPane createTablePanel() {
        tableModel = new DefaultTableModel(
                new String[]{"Tên Thuốc", "Số Lượng", "Đơn Giá", "Thành Tiền"},
                0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table = new JTable(tableModel);
        table.setRowHeight(30);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        table.getTableHeader().setBackground(new Color(0, 102, 204));
        table.getTableHeader().setForeground(Color.WHITE);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        return scrollPane;
    }

    private JPanel createFooterPanel() {
        JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        footerPanel.setBackground(new Color(248, 249, 250));

        JButton btnClose = new JButton("Đóng");
        btnClose.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnClose.setBackground(new Color(220, 53, 69));
        btnClose.setForeground(Color.WHITE);
        btnClose.addActionListener(e -> {
            SwingUtilities.getWindowAncestor(this).dispose();
        });

        footerPanel.add(btnClose);
        return footerPanel;
    }

    private JTextField createTextField(String text) {
        JTextField field = new JTextField(text);
        field.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        field.setEditable(false);
        field.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        return field;
    }

    private void loadChiTietHoaDon() {
        try {
            tableModel.setRowCount(0);
            List<ChiTietHoaDon> list = chiTietHoaDonDao.getById(hoaDon.getIdHD());
            for (ChiTietHoaDon cthd : list) {
                tableModel.addRow(new Object[]{
                        cthd.getThuoc() != null ? cthd.getThuoc().getTenThuoc() : "N/A",
                        cthd.getSoLuong(),
                        cthd.getDonGia() != null ? cthd.getDonGia() : 0.0,
                        cthd.getSoLuong() * (cthd.getDonGia() != null ? cthd.getDonGia() : 0.0)
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi tải chi tiết hóa đơn: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }
}