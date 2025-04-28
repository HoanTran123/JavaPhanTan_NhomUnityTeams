package view;

import dao.impl.ThongKeDAO;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ThongKePanel extends JPanel {
    private final ThongKeDAO thongKeDAO;
    private JTable table;
    private DefaultTableModel tableModel;
    private JComboBox<String> cboYear;
    private JButton btnRevenue, btnTopProducts, btnEmployeePerformance;
    private JPanel chartPanel;

    public ThongKePanel() {
        thongKeDAO = new ThongKeDAO();
        setLayout(new BorderLayout(10, 10));
        setBackground(new Color(248, 249, 250));

        add(createFilterPanel(), BorderLayout.NORTH);
        add(createTabbedPane(), BorderLayout.CENTER);
    }

    private JPanel createFilterPanel() {
        JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        filterPanel.setBackground(new Color(248, 249, 250));

        JLabel lblYear = new JLabel("Năm:");
        lblYear.setFont(new Font("Segoe UI", Font.BOLD, 14));

        // Populate years from 2020 to 2025
        String[] years = new String[6];
        for (int i = 0; i < 6; i++) {
            years[i] = String.valueOf(2020 + i);
        }
        cboYear = new JComboBox<>(years);
        cboYear.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        cboYear.setSelectedItem("2025"); // Default to current year

        btnRevenue = createStyledButton("Doanh thu theo tháng");
        btnTopProducts = createStyledButton("Sản phẩm bán chạy");
        btnEmployeePerformance = createStyledButton("Hiệu suất nhân viên");

        btnRevenue.addActionListener(e -> {
            loadRevenueByMonth();
            loadRevenueChart();
        });
        btnTopProducts.addActionListener(e -> {
            loadTopSellingProducts();
            loadTopProductsChart();
        });
        btnEmployeePerformance.addActionListener(e -> {
            loadEmployeePerformance();
            loadEmployeePerformanceChart();
        });

        filterPanel.add(lblYear);
        filterPanel.add(cboYear);
        filterPanel.add(btnRevenue);
        filterPanel.add(btnTopProducts);
        filterPanel.add(btnEmployeePerformance);

        return filterPanel;
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setForeground(Color.WHITE);
        button.setBackground(new Color(0, 123, 255));
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(0, 123, 255).darker(), 1),
                BorderFactory.createEmptyBorder(8, 20, 8, 20)
        ));
        button.setFocusPainted(false);
        return button;
    }

    private JTabbedPane createTabbedPane() {
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        // Table tab
        JPanel tableTab = new JPanel(new BorderLayout());
        tableTab.add(createTablePanel(), BorderLayout.CENTER);
        tabbedPane.addTab("Bảng", tableTab);

        // Chart tab
        chartPanel = new JPanel(new BorderLayout());
        chartPanel.setBackground(new Color(248, 249, 250));
        tabbedPane.addTab("Biểu đồ", chartPanel);

        return tabbedPane;
    }

    private JScrollPane createTablePanel() {
        tableModel = new DefaultTableModel();
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

    private void loadRevenueByMonth() {
        try {
            int year = Integer.parseInt((String) cboYear.getSelectedItem());
            List<Object[]> data = thongKeDAO.getRevenueByMonth(year);

            tableModel.setColumnIdentifiers(new String[]{"Tháng", "Doanh thu"});
            tableModel.setRowCount(0);
            for (Object[] row : data) {
                tableModel.addRow(new Object[]{row[0], String.format("%,.0f", (Double) row[1])});
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi khi tải dữ liệu: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadTopSellingProducts() {
        try {
            List<Object[]> data = thongKeDAO.getTopSellingProducts(10);

            tableModel.setColumnIdentifiers(new String[]{"Tên sản phẩm", "Số lượng bán"});
            tableModel.setRowCount(0);
            for (Object[] row : data) {
                tableModel.addRow(new Object[]{row[0], row[1]});
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi khi tải dữ liệu: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadEmployeePerformance() {
        try {
            int year = Integer.parseInt((String) cboYear.getSelectedItem());
            List<Object[]> data = thongKeDAO.getEmployeePerformance(year);

            tableModel.setColumnIdentifiers(new String[]{"Nhân viên", "Số hóa đơn", "Tổng doanh thu"});
            tableModel.setRowCount(0);
            for (Object[] row : data) {
                tableModel.addRow(new Object[]{row[0], row[1], String.format("%,.0f", (Double) row[2])});
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi khi tải dữ liệu: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadRevenueChart() {
        try {
            int year = Integer.parseInt((String) cboYear.getSelectedItem());
            List<Object[]> data = thongKeDAO.getRevenueByMonth(year);

            DefaultCategoryDataset dataset = new DefaultCategoryDataset();
            for (Object[] row : data) {
                dataset.addValue((Double) row[1], "Doanh thu", String.valueOf(row[0]));
            }

            JFreeChart chart = ChartFactory.createBarChart(
                    "Doanh thu theo tháng - " + year,
                    "Tháng",
                    "Doanh thu (VNĐ)",
                    dataset,
                    PlotOrientation.VERTICAL,
                    true,
                    true,
                    false
            );

            chartPanel.removeAll();
            chartPanel.add(new ChartPanel(chart));
            chartPanel.revalidate();
            chartPanel.repaint();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi khi tải biểu đồ: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadTopProductsChart() {
        try {
            List<Object[]> data = thongKeDAO.getTopSellingProducts(10);

            DefaultCategoryDataset dataset = new DefaultCategoryDataset();
            for (Object[] row : data) {
                dataset.addValue((Number) row[1], "Số lượng bán", (String) row[0]);
            }

            JFreeChart chart = ChartFactory.createBarChart(
                    "Top 10 sản phẩm bán chạy",
                    "Sản phẩm",
                    "Số lượng bán",
                    dataset,
                    PlotOrientation.VERTICAL,
                    true,
                    true,
                    false
            );

            chartPanel.removeAll();
            chartPanel.add(new ChartPanel(chart));
            chartPanel.revalidate();
            chartPanel.repaint();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi khi tải biểu đồ: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadEmployeePerformanceChart() {
        try {
            int year = Integer.parseInt((String) cboYear.getSelectedItem());
            List<Object[]> data = thongKeDAO.getEmployeePerformance(year);

            DefaultCategoryDataset dataset = new DefaultCategoryDataset();
            for (Object[] row : data) {
                dataset.addValue((Double) row[2], "Tổng doanh thu", (String) row[0]);
            }

            JFreeChart chart = ChartFactory.createBarChart(
                    "Hiệu suất nhân viên - " + year,
                    "Nhân viên",
                    "Tổng doanh thu (VNĐ)",
                    dataset,
                    PlotOrientation.VERTICAL,
                    true,
                    true,
                    false
            );

            chartPanel.removeAll();
            chartPanel.add(new ChartPanel(chart));
            chartPanel.revalidate();
            chartPanel.repaint();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi khi tải biểu đồ: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }
}