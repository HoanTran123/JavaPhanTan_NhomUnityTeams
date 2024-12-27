package gui;

import bus.ThongKe_bus;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Locale;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.StandardBarPainter;
import org.jfree.data.category.DefaultCategoryDataset;
import util.ConvertDoubleToMoney;

public class ThongKeNVKH_Panel extends javax.swing.JPanel {

    private ThongKe_bus tkbus;
    private DefaultTableModel model;
    private BarRenderer renderer;
    private String sort = "desc";
    private ConvertDoubleToMoney convert = new ConvertDoubleToMoney();

    /**
     * Creates new form DoanhSo_JPanel
     */
    public static String layHaiSoCuoiNam(String namChuoi) {
        if (namChuoi.length() >= 2) {
            return namChuoi.substring(namChuoi.length() - 2);
        } else {
            // Xử lý trường hợp chuỗi có ít hơn 2 ký tự
            return namChuoi;
        }
    }

    public ThongKeNVKH_Panel() {
        initComponents();
        setupPanel();
        setupData();
        
        // Set size and visibility
        setSize(1183, 710);
        setVisible(true);
        
        // Initialize business logic
        tkbus = new ThongKe_bus();
        
        // Load initial data
        DocDuLieuLenTable();
        createChart();
        
        // Validate and repaint
        validate();
        repaint();
    }

    private void setupPanel() {
        setBounds(0, 0, 1183, 710);
        setVisible(true);
        
        // Setup giao diện
        jPanel5.setBackground(new Color(0, 128, 128));
        jLabel1.setForeground(Color.WHITE);
        
        // Setup table
        jTable.setDefaultEditor(Object.class, null);
        jTable.setRowHeight(25);
        jTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        jTable.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        
        // Setup chart panel
        paneldoanhso.setLayout(new BorderLayout());
        paneldoanhso.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Setup radio buttons
        ButtonGroup bgType = new ButtonGroup();
        bgType.add(rdo_bdc1);
        bgType.add(rdo_bdd2);
        
        ButtonGroup bgTime = new ButtonGroup();
        bgTime.add(rdo_staticticsByMonth);
        bgTime.add(rdo_staticticByYear);
        
        // Setup month chooser
        monthChooser.setLocale(new Locale("Vi", "VN"));
    }

    private void setupData() {
        tkbus = new ThongKe_bus();
        DocDuLieuLenTable();
        createChart();
    }
    

    private void createChart() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        
        try {
            int rowCount = jTable.getRowCount();
            renderer = new BarRenderer();
            
            for (int i = 0; i < rowCount; i++) {
                double value = convert.toDouble(model.getValueAt(i, 2).toString());
                String label = sort.equals("desc") ? "Doanh thu bán hàng" : "Tổng tiền mua hàng";
                String category = model.getValueAt(i, 0).toString();
                
                dataset.setValue(value, label, category);
                renderer.setSeriesPaint(i, new Color(0, 128, 128));
            }
            
            String title = getChartTitle();
            String xLabel = sort.equals("desc") ? "Mã nhân viên" : "Mã khách hàng";
            String yLabel = sort.equals("desc") ? "Doanh thu (triệu VNĐ)" : "Tiền mua (triệu VNĐ)";

            JFreeChart chart = ChartFactory.createBarChart(
                title, xLabel, yLabel, 
                dataset, PlotOrientation.VERTICAL, 
                false, true, false
            );

            // Customize chart
            CategoryPlot plot = chart.getCategoryPlot();
            plot.setBackgroundPaint(Color.WHITE);
            plot.setRangeGridlinePaint(Color.GRAY);
            
            renderer.setBarPainter(new StandardBarPainter());
            renderer.setDrawBarOutline(true);
            renderer.setItemMargin(0.1);
            
            plot.setRenderer(renderer);

            // Add chart to panel
            ChartPanel chartPanel = new ChartPanel(chart);
            paneldoanhso.removeAll();
            paneldoanhso.add(chartPanel, BorderLayout.CENTER);
            paneldoanhso.validate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getChartTitle() {
        String period = rdo_staticticsByMonth.isSelected() 
            ? " tháng " + (monthChooser.getMonth() + 1) + " năm " + spin_nam.getYear()
            : " năm " + spin_nam.getYear();
            
        return sort.equals("desc")
            ? "Top 5 nhân viên đạt doanh thu cao nhất" + period
            : "Top 5 khách hàng có tổng tiền mua nhiều nhất" + period;
    }

    public void XoaAllData() {
        DefaultTableModel md = (DefaultTableModel) jTable.getModel();
//        md.getDataVector().removeAllElements();
        md.setRowCount(0);
    }

    public void DocDuLieuLenTable() {
        String thang = String.valueOf(monthChooser.getMonth() + 1);
        String nam = String.valueOf(spin_nam.getValue());
        ArrayList<Object[]> ds = new ArrayList<>();
//        if (rdo_staticticByYear.isSelected()) { 
//            ds = tkbus.getListTop5NhanVienDoanhThuCaoNhat(" ", nam);
//        }
//        else ds = tkbus.getListTop5NhanVienDoanhThuCaoNhat(thang, nam);
//        for (Object[] tk : ds) {
//            model.addRow(tk);
//        }
    }
    
    public void DocDuLieuLenTableDuplication() {
        String thang = String.valueOf(monthChooser.getMonth() + 1);
        String nam = String.valueOf(spin_nam.getValue());
//        ArrayList<Object[]> ds = new ArrayList<>();
//        if (rdo_staticticByYear.isSelected()) { 
//            ds = tkbus.getListTop5KhachHangMuaHangNhieuNhat(" ", nam);
//        }
//        else ds = tkbus.getListTop5KhachHangMuaHangNhieuNhat(thang, nam);
//        for (Object[] tk : ds) {
//            model.addRow(tk);
//        }
    }

    private void xuatExcelTable() {
        try {
            JFileChooser fileChooser = new JFileChooser("C:\\Users\\MY PC\\OneDrive\\Máy tính");
            fileChooser.setDialogTitle("Chọn nơi lưu file");
            int chon = fileChooser.showSaveDialog(null);
            if (chon == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                String filePath = selectedFile.getAbsolutePath();
                if (!filePath.toLowerCase().endsWith(".xlsx")) {
                    filePath += ".xlsx";
                }
                Workbook wb = new XSSFWorkbook();
                Sheet sheet ;
                if (sort == "desc") {
                    if (rdo_staticticsByMonth.isSelected()) {
                        sheet = wb.createSheet("Top 5 nhân viên đạt doanh thu bán hàng cao nhất tháng " + (monthChooser.getMonth() + 1) + " năm " + spin_nam.getYear());
                    }
                    else sheet = wb.createSheet("Top 5 nhân viên đạt doanh thu bán hàng cao nhất" + " năm " + spin_nam.getYear());
                } else {
                    if (rdo_staticticsByMonth.isSelected()) {
                        sheet = wb.createSheet("Top 5 khách hàng có tổng tiền mua hàng nhiều nhất nhất tháng " + (monthChooser.getMonth() + 1) + " năm " + spin_nam.getYear());
                    }
                    else sheet = wb.createSheet("Top 5 khách hàng có tổng tiền mua hàng nhiều nhất" + " năm " + spin_nam.getYear());
                }
                Row rowCol = sheet.createRow(0);
                for (int i = 0; i < jTable.getColumnCount(); i++) {
                    Cell cell = rowCol.createCell(i);
                    cell.setCellValue(jTable.getColumnName(i));
                }
                for (int j = 0; j < jTable.getRowCount(); j++) {
                    Row row = sheet.createRow(j + 1);
                    for (int k = 0; k < jTable.getColumnCount(); k++) {
                        Cell cell = row.createCell(k);
                        if (jTable.getValueAt(j, k) != null) {
                            cell.setCellValue(jTable.getValueAt(j, k).toString());
                        }
                    }
                }
                File f = new File(filePath);
                try (FileOutputStream fos = new FileOutputStream(f)) {
                    wb.write(fos);
                    JOptionPane.showMessageDialog(null, "Xuất file thành công");
                }
                wb.close();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel5 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        monthChooser = new com.toedter.calendar.JMonthChooser();
        jLabel3 = new javax.swing.JLabel();
        rdo_staticticsByMonth = new javax.swing.JRadioButton();
        rdo_staticticByYear = new javax.swing.JRadioButton();
        spin_nam = new com.toedter.calendar.JYearChooser();
        button1 = new java.awt.Button();
        jLabel5 = new javax.swing.JLabel();
        rdo_bdc1 = new javax.swing.JRadioButton();
        rdo_bdd2 = new javax.swing.JRadioButton();
        paneldoanhso = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable = new javax.swing.JTable();

        setBackground(new java.awt.Color(187, 205, 197));
        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel5.setBackground(new java.awt.Color(187, 205, 197));
        jPanel5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("THỐNG KÊ THEO DOANH THU");
        jLabel1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jLabel1.setPreferredSize(new java.awt.Dimension(70, 30));
        jPanel5.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, -10, 490, 92));

        jLabel2.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Thống kê theo");
        jLabel2.setPreferredSize(new java.awt.Dimension(60, 30));
        jPanel5.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, 140, 50));

        monthChooser.setPreferredSize(new java.awt.Dimension(125, 30));
        monthChooser.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                monthChooserPropertyChange(evt);
            }
        });
        jPanel5.add(monthChooser, new org.netbeans.lib.awtextra.AbsoluteConstraints(146, 69, -1, 30));

        jLabel3.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Năm");
        jLabel3.setPreferredSize(new java.awt.Dimension(60, 30));
        jPanel5.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(291, 33, 68, -1));

        rdo_staticticsByMonth.setBackground(new java.awt.Color(187, 205, 197));
        rdo_staticticsByMonth.setFont(new java.awt.Font("Segoe UI", 3, 12)); // NOI18N
        rdo_staticticsByMonth.setSelected(true);
        rdo_staticticsByMonth.setText("Theo tháng");
        rdo_staticticsByMonth.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdo_staticticsByMonthActionPerformed(evt);
            }
        });
        jPanel5.add(rdo_staticticsByMonth, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 100, -1, -1));

        rdo_staticticByYear.setBackground(new java.awt.Color(187, 205, 197));
        rdo_staticticByYear.setFont(new java.awt.Font("Segoe UI", 3, 12)); // NOI18N
        rdo_staticticByYear.setText("Theo năm");
        rdo_staticticByYear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdo_staticticByYearActionPerformed(evt);
            }
        });
        jPanel5.add(rdo_staticticByYear, new org.netbeans.lib.awtextra.AbsoluteConstraints(800, 100, -1, -1));

        spin_nam.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                spin_namPropertyChange(evt);
            }
        });
        jPanel5.add(spin_nam, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 70, -1, 30));

        button1.setFont(new java.awt.Font("Dialog", 3, 12)); // NOI18N
        button1.setLabel("Xuất");
        button1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button1ActionPerformed(evt);
            }
        });
        jPanel5.add(button1, new org.netbeans.lib.awtextra.AbsoluteConstraints(1050, 80, 50, 30));

        jLabel5.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("Tháng");
        jLabel5.setPreferredSize(new java.awt.Dimension(60, 30));
        jPanel5.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(146, 33, -1, -1));

        rdo_bdc1.setBackground(new java.awt.Color(187, 205, 197));
        rdo_bdc1.setFont(new java.awt.Font("Segoe UI", 3, 12)); // NOI18N
        rdo_bdc1.setSelected(true);
        rdo_bdc1.setText("Top nhân viên doanh thu bán hàng cao");
        rdo_bdc1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdo_bdc1ActionPerformed(evt);
            }
        });
        jPanel5.add(rdo_bdc1, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 70, -1, -1));

        rdo_bdd2.setBackground(new java.awt.Color(187, 205, 197));
        rdo_bdd2.setFont(new java.awt.Font("Segoe UI", 3, 12)); // NOI18N
        rdo_bdd2.setText("Top khách hàng mua hàng nhiều");
        rdo_bdd2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdo_bdd2ActionPerformed(evt);
            }
        });
        jPanel5.add(rdo_bdd2, new org.netbeans.lib.awtextra.AbsoluteConstraints(800, 70, -1, -1));

        add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 6, -1, 130));

        paneldoanhso.setBackground(new java.awt.Color(187, 205, 197));
        paneldoanhso.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        paneldoanhso.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        add(paneldoanhso, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 266, 1168, 430));

        jTable.setModel(model = new javax.swing.table.DefaultTableModel(
            new Object [][] {
            },
            new String [] {
                "Mã nhân viên", "Tên nhân viên", "Tổng doanh thu"
            }
        ));
        jScrollPane1.setViewportView(jTable);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 142, 1168, -1));
    }// </editor-fold>//GEN-END:initComponents

    private void monthChooserPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_monthChooserPropertyChange
        if (sort.equals("desc")) {
            XoaAllData();
            DocDuLieuLenTable();
            createChart();
        }
        else {
            XoaAllData();
            DocDuLieuLenTableDuplication();
            createChart();
        }
        

    }//GEN-LAST:event_monthChooserPropertyChange

    private void rdo_staticticsByMonthActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdo_staticticsByMonthActionPerformed
        if (sort.equals("desc")) {
            XoaAllData();
            DocDuLieuLenTable();
            createChart();
        }
        else {
            XoaAllData();
            DocDuLieuLenTableDuplication();
            createChart();
        }
    }//GEN-LAST:event_rdo_staticticsByMonthActionPerformed

    private void rdo_staticticByYearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdo_staticticByYearActionPerformed
        if (sort.equals("desc")) {
            XoaAllData();
            DocDuLieuLenTable();
            createChart();
        }
        else {
            XoaAllData();
            DocDuLieuLenTableDuplication();
            createChart();
        }
//        createLineChart();
    }//GEN-LAST:event_rdo_staticticByYearActionPerformed

    private void spin_namPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_spin_namPropertyChange
        // TODO add your handling code here:
        XoaAllData();
        DocDuLieuLenTable();
        createChart();
    }//GEN-LAST:event_spin_namPropertyChange

    private void button1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button1ActionPerformed
        // TODO add your handling code here:
        xuatExcelTable();
//        XSSFWorkbook workbook = new XSSFWorkbook();
//        XSSFSheet sheet = workbook.createSheet("Khuyến Mãi");
//        XSSFRow row= null;
//        Cell cell = null;
//        row = sheet.createRow(3);
//
//        cell = row.createCell(0,CellType.STRING);
//        cell.setCellValue("Ngày / Tháng");
//
//        cell = row.createCell(1,CellType.STRING);
//        cell.setCellValue("Số Lượng sản phẩm bán được");
//
//        cell = row.createCell(2,CellType.STRING);
//        cell.setCellValue("Doanh Thu");
//
//        try {
//            int rows = jTable1.getRowCount();
//
//            for(int i=0;i<rows;i++ ){
//                row = sheet.createRow(4+i);
//                cell = row.createCell(0, CellType.STRING);
//                cell.setCellValue(model.getValueAt(i, 0).toString());
//
//                cell = row.createCell(1, CellType.NUMERIC);
//                cell.setCellValue(Integer.parseInt(model.getValueAt(i, 1).toString()));
//
//                cell = row.createCell(2, CellType.NUMERIC);
//                cell.setCellValue(Double.parseDouble(model.getValueAt(i, 2).toString()));
//
//            }
//
//            File fis = new File("E:\\KMExcel\\thongke.xlsx");
//            FileOutputStream fisO = new FileOutputStream(fis);
//            workbook.write(fisO);
//            fisO.close();
//            openExcelFile(fis);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

    }//GEN-LAST:event_button1ActionPerformed

    private void rdo_bdc1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdo_bdc1ActionPerformed
        sort = "desc";
        XoaAllData();
        DocDuLieuLenTable();
        createChart();
    }//GEN-LAST:event_rdo_bdc1ActionPerformed

    private void rdo_bdd2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdo_bdd2ActionPerformed
        sort = "asc";
        XoaAllData();
        DocDuLieuLenTableDuplication();
        createChart();
    }//GEN-LAST:event_rdo_bdd2ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private java.awt.Button button1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable;
    private com.toedter.calendar.JMonthChooser monthChooser;
    private javax.swing.JPanel paneldoanhso;
    private javax.swing.JRadioButton rdo_bdc1;
    private javax.swing.JRadioButton rdo_bdd2;
    private javax.swing.JRadioButton rdo_staticticByYear;
    private javax.swing.JRadioButton rdo_staticticsByMonth;
    private com.toedter.calendar.JYearChooser spin_nam;
    // End of variables declaration//GEN-END:variables
}
