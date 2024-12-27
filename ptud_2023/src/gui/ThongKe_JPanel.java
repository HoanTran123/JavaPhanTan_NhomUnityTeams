package gui;

import bus.ThongKe_bus;
import connectDB.ConnectDB;
import dao.ThongKe_dao;
import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;

import java.util.Calendar;
import java.util.ArrayList;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.ChartFactory;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.chart.plot.PlotOrientation;
import java.text.DecimalFormat;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.renderer.category.BarRenderer;

public class ThongKe_JPanel extends javax.swing.JPanel {
//    private ThongKe_bus thongKeBus;
    private JComboBox<Integer> cbo_Nam;
    private DecimalFormat df = new DecimalFormat("#,###");
    private ThongKe_dao thongKe_dao ;
    private JPanel pnl_Main;
    
    public ThongKe_JPanel() {
            
            initComponents();
            thongKe_dao = new ThongKe_dao();
            setSize(1186, 748); 
//            thongKeBus = new ThongKe_bus();
            setBounds(0, 0, 1186, 748);
            setVisible(true); 
            setupComponents();
            
    }

    private void setupComponents() {
        setLayout(new BorderLayout(0, 0));
        JPanel pnl_TieuDe = createTitlePanel();
        add(pnl_TieuDe, BorderLayout.NORTH);

        JPanel pnl_ChonNam = createYearSelectionPanel();

        pnl_Main = new JPanel(new GridLayout(2, 2, 15, 15));
        pnl_Main.setBackground(Color.WHITE);
        pnl_Main.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JPanel pnl_Content = new JPanel(new BorderLayout());
        pnl_Content.setBackground(Color.WHITE);
        pnl_Content.add(pnl_ChonNam, BorderLayout.NORTH);
        pnl_Content.add(pnl_Main, BorderLayout.CENTER);
        
        add(pnl_Content, BorderLayout.CENTER);
        
        loadData();
    }

    private JPanel createTitlePanel() {
        JPanel panel = new JPanel();
        panel.setBackground(new Color(204, 204, 255));
        panel.setPreferredSize(new Dimension(1186, 50));
        
        JLabel lblTitle = new JLabel("THỐNG KÊ");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 20));
        panel.add(lblTitle);
        
        return panel;
    }

    private void loadData() {
        try {
            String selectedYear = cbo_Nam.getSelectedItem().toString();
            System.out.println("\n=== Loading data for year: " + selectedYear + " ===");
            
            // Clear existing data
            pnl_Main.removeAll();
            
            // Create and add each panel
            System.out.println("Creating train chart...");
            ChartPanel trainChart = createVeTheoTauChart();
            
            System.out.println("Creating employee chart...");
            ChartPanel employeeChart = createVeTheoNhanVienChart();
            
            System.out.println("Creating status chart...");
            ChartPanel statusChart = createNhanVienChart();
            
            System.out.println("Creating top 5 table...");
            JTable top5Table = createTop5NVTable();
            
            // Add components to panels
            JPanel[] panels = new JPanel[4];
            String[] titles = {
                "Thống kê vé theo tàu",
                "Thống kê vé theo nhân viên",
                "Thống kê trạng thái nhân viên",
                "Top 5 nhân viên xuất sắc"
            };
            
            for (int i = 0; i < panels.length; i++) {
                panels[i] = createStyledPanel(titles[i]);
            }
            
            if (trainChart != null) panels[0].add(trainChart, BorderLayout.CENTER);
            if (employeeChart != null) panels[1].add(employeeChart, BorderLayout.CENTER);
            if (statusChart != null) panels[2].add(statusChart, BorderLayout.CENTER);
            if (top5Table != null) panels[3].add(new JScrollPane(top5Table), BorderLayout.CENTER);
            
            // Add all panels to main panel
            for (JPanel panel : panels) {
                pnl_Main.add(panel);
            }
            
            pnl_Main.revalidate();
            pnl_Main.repaint();
            System.out.println("Data loaded successfully");
            
        } catch (Exception e) {
            System.out.println("Error loading data: " + e.getMessage());
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi tải dữ liệu: " + e.getMessage());
        }
    }

    private JPanel createStyledPanel(String title) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(204, 204, 204)),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        
        // Tạo panel tiêu đề
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(Color.WHITE);
        JLabel lblTitle = new JLabel(title);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 14));
        titlePanel.add(lblTitle);
        
        panel.add(titlePanel, BorderLayout.NORTH);
        return panel;
    }

    private ChartPanel createVeTheoTauChart() {
        try {
            DefaultCategoryDataset dataset = new DefaultCategoryDataset();
            String nam = cbo_Nam.getSelectedItem().toString();
            System.out.println("Loading train data for year: " + nam);
            
            ArrayList<Object[]> data = thongKe_dao.thongKeVeTheoTau(nam);
            System.out.println("Received " + data.size() + " train records");
            
            for (Object[] row : data) {
                String tenTau = row[1].toString();
                int soVe = Integer.parseInt(row[2].toString());
                dataset.addValue(soVe, "Số lượng vé", tenTau);
                System.out.println("Added train data: " + tenTau + " - " + soVe + " vé");
            }

            JFreeChart chart = ChartFactory.createBarChart(
                null,
                "Tàu",
                "Số lượng vé",
                dataset,
                PlotOrientation.VERTICAL,
                false, true, false
            );
            
            customizeChart(chart);
            ChartPanel chartPanel = new ChartPanel(chart);
            chartPanel.setPreferredSize(new Dimension(400, 300));
            return chartPanel;
            
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi tạo biểu đồ tàu: " + e.getMessage());
            return new ChartPanel(ChartFactory.createBarChart(
                "Lỗi dữ liệu", "Tàu", "Số lượng vé", 
                new DefaultCategoryDataset(), 
                PlotOrientation.VERTICAL, false, true, false));
        }
    }

    private ChartPanel createVeTheoNhanVienChart() {
        try {
            String nam = cbo_Nam.getSelectedItem().toString();
            System.out.println("Creating employee chart for year: " + nam);
            
            ArrayList<Object[]> data = thongKe_dao.thongKeVeTheoNhanVien(nam);
            if (data == null || data.isEmpty()) {
                System.out.println("No employee data found");
                return createEmptyChart("Không có dữ liệu nhân viên", "Nhân viên", "Số lượng vé");
            }
            
            DefaultCategoryDataset dataset = new DefaultCategoryDataset();
            for (Object[] row : data) {
                try {
                    String hoTen = row[1].toString();
                    int soVe = Integer.parseInt(row[2].toString());
                    dataset.addValue(soVe, "Số vé bán được", hoTen);
                    System.out.println("Added to chart: " + hoTen + " - " + soVe + " vé");
                } catch (Exception e) {
                    System.out.println("Error processing row: " + e.getMessage());
                }
            }

            JFreeChart chart = ChartFactory.createBarChart(
                null,                      
                "Nhân viên",              
                "Số lượng vé",            
                dataset,                   
                PlotOrientation.VERTICAL,  
                true,                      
                true,                      
                false                      
            );
            
            customizeChart(chart);
            ChartPanel chartPanel = new ChartPanel(chart);
            chartPanel.setPreferredSize(new Dimension(400, 300));
            return chartPanel;
            
        } catch (Exception e) {
            System.out.println("Error creating chart: " + e.getMessage());
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Lỗi khi tạo biểu đồ: " + e.getMessage());
            return new ChartPanel(ChartFactory.createBarChart(
                "Lỗi dữ liệu", "Nhân viên", "Số lượng vé", 
                new DefaultCategoryDataset(), 
                PlotOrientation.VERTICAL, true, true, false));
        }
    }

    private ChartPanel createEmptyChart(String title, String xLabel, String yLabel) {
        DefaultCategoryDataset emptyDataset = new DefaultCategoryDataset();
        JFreeChart chart = ChartFactory.createBarChart(
            title,
            xLabel,
            yLabel,
            emptyDataset,
            PlotOrientation.VERTICAL,
            false,
            true,
            false
        );
        customizeChart(chart);
        return new ChartPanel(chart);
    }

    private ChartPanel createNhanVienChart() {
        DefaultPieDataset dataset = new DefaultPieDataset();
        ArrayList<Object[]> data = thongKe_dao.thongKeNhanVien();
        for (Object[] row : data) {
            dataset.setValue(row[0].toString(), (Number) row[1]);
        }

        JFreeChart chart = ChartFactory.createPieChart(
            "Thống kê trạng thái nhân viên",
            dataset,
            true, true, false
        );

        return new ChartPanel(chart);
    }
    private void customizeChart(JFreeChart chart) {
        // Tùy chỉnh màu nền
        chart.setBackgroundPaint(Color.WHITE);
        
        if (chart.getPlot() instanceof CategoryPlot) {
            CategoryPlot plot = (CategoryPlot) chart.getPlot();
            plot.setBackgroundPaint(Color.WHITE);
            plot.setRangeGridlinePaint(Color.LIGHT_GRAY);
            plot.setDomainGridlinePaint(Color.LIGHT_GRAY);
            
            // Tùy chỉnh font
            Font labelFont = new Font("Segoe UI", Font.PLAIN, 12);
            plot.getDomainAxis().setLabelFont(labelFont);
            plot.getRangeAxis().setLabelFont(labelFont);
            
            // Tùy chỉnh màu cột
            BarRenderer renderer = (BarRenderer) plot.getRenderer();
            renderer.setSeriesPaint(0, new Color(51, 51, 255));
            renderer.setBarPainter(new org.jfree.chart.renderer.category.StandardBarPainter());
        }
    }

    private JTable createTop5NVTable() {
        String[] columnNames = {"STT", "Mã NV", "Tên nhân viên", "Số vé đã bán"};
        String nam = cbo_Nam.getSelectedItem().toString();
        String thang = "";
        ArrayList<Object[]> data = thongKe_dao.thongKeTop5NhanVien(thang, nam);
        
        System.out.println("Số lượng dữ liệu top 5: " + data.size()); // Debug
        
        Object[][] tableData = new Object[data.size()][4];
        for (int i = 0; i < data.size(); i++) {
            Object[] row = data.get(i);
            tableData[i][0] = i + 1;
            tableData[i][1] = row[0];
            tableData[i][2] = row[1];
            tableData[i][3] = df.format(Integer.parseInt(row[2].toString()));
        }

        JTable table = new JTable(tableData, columnNames);
        table.setFillsViewportHeight(true);
        table.setEnabled(false);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        
        // Căn giữa các cột
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
        
        return table;
    }

    private JPanel createYearSelectionPanel() {
        JPanel panel = new JPanel();
        panel.setBackground(new Color(240, 240, 240));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        
        JLabel lbl_Nam = new JLabel("Năm thống kê:");
        lbl_Nam.setFont(new Font("Segoe UI", Font.BOLD, 14));
        
        cbo_Nam = new JComboBox<>();
        cbo_Nam.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        cbo_Nam.setPreferredSize(new Dimension(120, 30));
        
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        for(int i = currentYear; i >= currentYear-5; i--) {
            cbo_Nam.addItem(i);
        }
        
        cbo_Nam.addActionListener(e -> loadData());
        
        panel.add(lbl_Nam);
        panel.add(cbo_Nam);
        
        return panel;
    }

    private void initComponents() {
        // Phần code tự động sinh bởi NetBeans
    }// </editor-fold>                        

}