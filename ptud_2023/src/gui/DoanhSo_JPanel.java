package gui;

import bus.ThongKe_bus;
import java.awt.BorderLayout;
import java.awt.Color;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Locale;
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

public class DoanhSo_JPanel extends javax.swing.JPanel {

    private final ThongKe_bus tkbus;
    private DefaultTableModel model;
    private BarRenderer renderer;
    private String sort = "desc";
    private String timeline = "m";

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

    public DoanhSo_JPanel() {
        initComponents();
          setBounds(0, 0, 1183, 710);
        setVisible(true);
        tkbus = new ThongKe_bus();
        DocDuLieuLenTable();
        charAt();
        ButtonGroup bg = new ButtonGroup();
        bg.add(rdo_bdc);
        bg.add(rdo_bdd);
        monthChooser.setLocale(new Locale("Vi", "VN"));

        ButtonGroup bgtime = new ButtonGroup();
        bgtime.add(rdo_month);
        bgtime.add(rdo_year);

        jtb_sp.setDefaultEditor(Object.class, null);

    }

    public void XoaAllData() {
        DefaultTableModel md = (DefaultTableModel) jtb_sp.getModel();
//        md.getDataVector().removeAllElements();
        md.setRowCount(0);
    }

    public void DocDuLieuLenTable() {
        String month = String.valueOf(monthChooser.getMonth() + 1);
        String nam = String.valueOf(spin_nam.getValue());
        String thangNam = month + layHaiSoCuoiNam(nam);
        System.out.println(thangNam);
//        ArrayList<Object[]> ds = tkbus.getListThongKeDoanhSoTheoThangNam(thangNam, sort);
//        for (Object[] tk : ds) {
//            System.out.println("Thong ke " + ds);
//            model.addRow(tk);
//        }
    }

    public void DocDuLieuLenTableTheoYear() {
        String nam = String.valueOf(spin_nam.getValue());
        String nam2So = layHaiSoCuoiNam(nam);
//        ArrayList<Object[]> ds = tkbus.getListThongKeDoanhSoTheoNam(nam2So, sort);
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
                Sheet sheet;
                if (sort == "desc") {
                    sheet = wb.createSheet("thống kê Sản phẩm bán chạy nhất ");
                } else {
                    sheet = wb.createSheet("thống kê Sản phẩm bán chậm nhất");
                }

                Row rowCol = sheet.createRow(0);
                for (int i = 0; i < jtb_sp.getColumnCount(); i++) {
                    Cell cell = rowCol.createCell(i);
                    cell.setCellValue(jtb_sp.getColumnName(i));
                }
                for (int j = 0; j < jtb_sp.getRowCount(); j++) {
                    Row row = sheet.createRow(j + 1);
                    for (int k = 0; k < jtb_sp.getColumnCount(); k++) {
                        Cell cell = row.createCell(k);
                        if (jtb_sp.getValueAt(j, k) != null) {
                            cell.setCellValue(jtb_sp.getValueAt(j, k).toString());
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

    public void charAt() {
        DefaultCategoryDataset barchardata = new DefaultCategoryDataset();
        try {
            int countRow = jtb_sp.getRowCount();
            renderer = new BarRenderer();
            for (int i = 0; i < countRow; i++) {
                int value = Integer.parseInt(model.getValueAt(i, 2).toString());
                barchardata.setValue(value, "Số lượng bán", model.getValueAt(i, 0).toString());
                renderer.setSeriesPaint(i, Color.BLUE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        String month = String.valueOf(monthChooser.getMonth() + 1);
        String nam = String.valueOf(spin_nam.getValue());
        String title = "Top 5 sản phẩm bán chạy nhất theo tháng " + month;
        if (sort == "desc" && timeline == "m") {
            title = "Top 5 sản phẩm bán chạy nhất theo tháng " + month;
        } else if (sort == "asc" && timeline == "m") {
            title = "Top 5 sản phẩm bán chậm nhất theo tháng " + month;
        } else if (sort == "asc" && timeline == "y") {
            title = "Top 5 sản phẩm bán chậm nhất theo năm " + nam;
        } else {
            title = "Top 5 sản phẩm bán chạy nhất theo năm " + nam;
        }
        JFreeChart barchart = ChartFactory.createBarChart(title, "Mã Sản Phẩm", "Số lượng bán", barchardata, PlotOrientation.VERTICAL, false, true, false);

        CategoryPlot barchst = barchart.getCategoryPlot();
        barchst.setRangeCrosshairPaint(Color.ORANGE);

        // Customization for BarChart
        CategoryAxis xAxis = barchst.getDomainAxis();
        xAxis.setLowerMargin(0.02);
        xAxis.setUpperMargin(0.02);

        NumberAxis yAxis = (NumberAxis) barchst.getRangeAxis();
        yAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());

        renderer.setBarPainter(new StandardBarPainter());

        // Set renderer for the plot
        barchst.setRenderer(renderer);

        ChartPanel barPanel = new ChartPanel(barchart);
        paneldoanhso.removeAll();
        paneldoanhso.setLayout(new BorderLayout());
        paneldoanhso.add(barPanel, BorderLayout.CENTER);
        paneldoanhso.validate();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel5 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        monthChooser = new com.toedter.calendar.JMonthChooser();
        jLabel3 = new javax.swing.JLabel();
        rdo_month = new javax.swing.JRadioButton();
        rdo_year = new javax.swing.JRadioButton();
        spin_nam = new com.toedter.calendar.JYearChooser();
        button1 = new java.awt.Button();
        jLabel5 = new javax.swing.JLabel();
        rdo_bdc = new javax.swing.JRadioButton();
        rdo_bdd = new javax.swing.JRadioButton();
        paneldoanhso = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jtb_sp = new javax.swing.JTable();

        setBackground(new java.awt.Color(187, 205, 197));

        jPanel5.setBackground(new java.awt.Color(187, 205, 197));
        jPanel5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel5.setPreferredSize(new java.awt.Dimension(1168, 110));
        jPanel5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("THỐNG KÊ THEO SẢN PHẨM");
        jLabel1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jLabel1.setPreferredSize(new java.awt.Dimension(70, 30));
        jPanel5.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, -10, 490, 92));

        jLabel2.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Thống kê theo");
        jLabel2.setPreferredSize(new java.awt.Dimension(60, 30));
        jPanel5.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 60, 140, 50));

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

        rdo_month.setBackground(new java.awt.Color(187, 205, 197));
        rdo_month.setFont(new java.awt.Font("Segoe UI", 3, 12)); // NOI18N
        rdo_month.setSelected(true);
        rdo_month.setText("Theo tháng");
        rdo_month.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdo_monthActionPerformed(evt);
            }
        });
        jPanel5.add(rdo_month, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 100, -1, -1));

        rdo_year.setBackground(new java.awt.Color(187, 205, 197));
        rdo_year.setFont(new java.awt.Font("Segoe UI", 3, 12)); // NOI18N
        rdo_year.setText("Theo năm");
        rdo_year.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdo_yearActionPerformed(evt);
            }
        });
        jPanel5.add(rdo_year, new org.netbeans.lib.awtextra.AbsoluteConstraints(740, 100, -1, -1));

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
        jPanel5.add(button1, new org.netbeans.lib.awtextra.AbsoluteConstraints(970, 70, 80, 30));

        jLabel5.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("Tháng");
        jLabel5.setPreferredSize(new java.awt.Dimension(60, 30));
        jPanel5.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(146, 33, -1, -1));

        rdo_bdc.setBackground(new java.awt.Color(187, 205, 197));
        rdo_bdc.setFont(new java.awt.Font("Segoe UI", 3, 12)); // NOI18N
        rdo_bdc.setSelected(true);
        rdo_bdc.setText("Top sản phẩm bán chạy");
        rdo_bdc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdo_bdcActionPerformed(evt);
            }
        });
        jPanel5.add(rdo_bdc, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 70, -1, -1));

        rdo_bdd.setBackground(new java.awt.Color(187, 205, 197));
        rdo_bdd.setFont(new java.awt.Font("Segoe UI", 3, 12)); // NOI18N
        rdo_bdd.setText("Top sản phẩm bán không chạy");
        rdo_bdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdo_bddActionPerformed(evt);
            }
        });
        jPanel5.add(rdo_bdd, new org.netbeans.lib.awtextra.AbsoluteConstraints(740, 70, -1, -1));

        paneldoanhso.setBackground(new java.awt.Color(187, 205, 197));
        paneldoanhso.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout paneldoanhsoLayout = new javax.swing.GroupLayout(paneldoanhso);
        paneldoanhso.setLayout(paneldoanhsoLayout);
        paneldoanhsoLayout.setHorizontalGroup(
            paneldoanhsoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        paneldoanhsoLayout.setVerticalGroup(
            paneldoanhsoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jtb_sp.setModel(model = new javax.swing.table.DefaultTableModel(
            new Object [][] {
            },
            new String [] {
                "Mã Sản Phẩm", "Tên Sản Phẩm", "Số lượng Bán"
            }
        ));
        jScrollPane1.setViewportView(jtb_sp);

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

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(paneldoanhso, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(paneldoanhso, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void monthChooserPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_monthChooserPropertyChange

        XoaAllData();
        if (timeline == "y") {
            DocDuLieuLenTableTheoYear();
        } else {
            DocDuLieuLenTable();
        }
        charAt();

    }//GEN-LAST:event_monthChooserPropertyChange

    private void rdo_monthActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdo_monthActionPerformed
        timeline = "m";
        XoaAllData();
        DocDuLieuLenTable();
        charAt();
    }//GEN-LAST:event_rdo_monthActionPerformed

    private void rdo_yearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdo_yearActionPerformed
        // TODO add your handling code here:
        timeline = "y";
        XoaAllData();
        DocDuLieuLenTableTheoYear();
        charAt();

//        createLineChart();
    }//GEN-LAST:event_rdo_yearActionPerformed

    private void spin_namPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_spin_namPropertyChange
        // TODO add your handling code here:
        XoaAllData();
         if (timeline == "y") {
            DocDuLieuLenTableTheoYear();
        } else {
            DocDuLieuLenTable();
        }
        charAt();
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


    private void rdo_bdcActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdo_bdcActionPerformed
        // TODO add your handling code here:
        sort = "desc";
        XoaAllData();
        if (timeline == "m") {
            DocDuLieuLenTable();
        } else {
            DocDuLieuLenTableTheoYear();
        }
        charAt();
    }//GEN-LAST:event_rdo_bdcActionPerformed

    private void rdo_bddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdo_bddActionPerformed
        sort = "asc";
        XoaAllData();
        if (timeline == "m") {
            DocDuLieuLenTable();
        } else {
            DocDuLieuLenTableTheoYear();
        }
        charAt();
        // TODO add your handling code here:
    }//GEN-LAST:event_rdo_bddActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private java.awt.Button button1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jtb_sp;
    private com.toedter.calendar.JMonthChooser monthChooser;
    private javax.swing.JPanel paneldoanhso;
    private javax.swing.JRadioButton rdo_bdc;
    private javax.swing.JRadioButton rdo_bdd;
    private javax.swing.JRadioButton rdo_month;
    private javax.swing.JRadioButton rdo_year;
    private com.toedter.calendar.JYearChooser spin_nam;
    // End of variables declaration//GEN-END:variables
}
