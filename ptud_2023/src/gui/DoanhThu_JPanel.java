package gui;

import bus.ThongKe_bus;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;
import javax.swing.ButtonGroup;
import javax.swing.JOptionPane;
import javax.swing.Renderer;
import javax.swing.table.DefaultTableModel;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.CategoryItemRenderer;
import org.jfree.chart.renderer.category.StandardBarPainter;
import org.jfree.chart.renderer.xy.StandardXYItemRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import java.text.NumberFormat;
import java.util.Date;
import java.util.Locale;
import javax.swing.JFileChooser;

public class DoanhThu_JPanel extends javax.swing.JPanel {

    private final ThongKe_bus tkbus;
    private DefaultTableModel model;
    private BarRenderer Renderer;
    private static String tieude,trucX,trucY;
        // Định dạng số tiền sang VND
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));

    /**
     * Creates new form DoanhThu_JPanel
     */
    public DoanhThu_JPanel() {
        initComponents();
        setBounds(0, 0, 1180, 710);
        setVisible(true);
        tkbus = new ThongKe_bus();

        ButtonGroup btnGr = new ButtonGroup();
        btnGr.add(rdo_bdc);
        btnGr.add(rdo_bdd);

        monthChooser.setLocale(new Locale("vi", "VN"));
        DocDuLieuLenTable();
        tieude = "Doanh Thu Tháng";
        trucX = "Ngày";
        trucY = "Doanh Thu";
        charAt(tieude,trucX,trucY);
    }

    public void DocDuLieuLenTable() {
//        String month = String.valueOf(monthChooser.getMonth()+1);
//        String nam = String.valueOf(spin_nam.getValue());
//        System.out.println("Tháng " + month + nam);
//        ArrayList<Object[]> ds = tkbus.getListDoanhThuTheoThangvaNam(month, nam);
//        if(ds.isEmpty()){
//            JOptionPane.showMessageDialog(null, "Doanh thu trong tháng/ năm này chưa có !");
//            Date datenow = new Date();
//            monthChooser.setMonth(datenow.getMonth());
//            spin_nam.setYear(datenow.getYear() + 1900);
           
//        }
//        else for (Object[] tk : ds) {
//            System.out.println("Thong ke " + tk);
//            model.addRow(tk);
//        ChuyenDuLieuSoTrongTableThanhVND();
//        TongDoanhThu();
//        }

        
    }
        private void ChuyenDuLieuSoTrongTableThanhVND() {
            NumberFormat formatter = NumberFormat.getNumberInstance(new Locale("vi", "VN"));
            int Crow = jTable1.getRowCount();
            for (int i = 0; i < Crow; i++) {
                Object valueAtColumn1 = jTable1.getValueAt(i, 1);
                Object valueAtColumn2 = jTable1.getValueAt(i, 2);
                Object valueAtColumn3 = jTable1.getValueAt(i, 3);

                if (valueAtColumn1 instanceof Number) {
                    jTable1.setValueAt(formatter.format(valueAtColumn1), i, 1);
                }

                if (valueAtColumn2 instanceof Number) {
                    jTable1.setValueAt(formatter.format(valueAtColumn2), i, 2);
                }

                if (valueAtColumn3 instanceof Number) {
                    jTable1.setValueAt(formatter.format(valueAtColumn3), i, 3);
                }
            }
        }

    private void TongDoanhThu(){
        String Tong;
        double Sum =0;
        try {
            int Crow = jTable1.getRowCount();
            for (int i=0;i<Crow;i++){
                Sum+= Double.parseDouble(model.getValueAt(i, 3).toString().replace(".", ""));
            }
           
        } catch (Exception e) {
            e.printStackTrace();
        }
          Tong = currencyFormatter.format(Sum);
         
        lblTongDoanhThu.setText(Tong);
    }

    public void XoaAllData() {
        DefaultTableModel md = (DefaultTableModel) jTable1.getModel();
        md.getDataVector().removeAllElements();
    }

    public void charAt(String title,String x, String y) {
        DefaultCategoryDataset barchardata = new DefaultCategoryDataset();
        try {
            int countRow = jTable1.getRowCount();
            Renderer = new BarRenderer();
            for (int i = 0; i < countRow; i++) {
                double value = Double.parseDouble(model.getValueAt(i, 3).toString().replace(".", ""));
                barchardata.setValue(value, "Doanh Thu", model.getValueAt(i, 0).toString());
                Renderer.setSeriesPaint(i, Color.BLUE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        JFreeChart barchart = ChartFactory.createBarChart(title, x, y, barchardata, PlotOrientation.VERTICAL, false, true, false);

        CategoryPlot barchst = barchart.getCategoryPlot();
        barchst.setRangeCrosshairPaint(Color.ORANGE);

        // Customization for BarChart
        CategoryAxis xAxis = barchst.getDomainAxis();
        xAxis.setLowerMargin(0.02);
        xAxis.setUpperMargin(0.02);

        NumberAxis yAxis = (NumberAxis) barchst.getRangeAxis();
        yAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());

        // Optional: Use StandardBarPainter to make the bars look smoother
        Renderer.setBarPainter(new StandardBarPainter());

        // Set renderer for the plot
        barchst.setRenderer(Renderer);

        ChartPanel barPanel = new ChartPanel(barchart);
        Panel_bieudo.removeAll();
        Panel_bieudo.setLayout(new BorderLayout());
        Panel_bieudo.add(barPanel, BorderLayout.CENTER);
        Panel_bieudo.validate();
    }

    public void createLineChart(String title,String x, String y) {
        DefaultCategoryDataset lineChartData = new DefaultCategoryDataset();
        try {
            int countRow = jTable1.getRowCount();
            for (int i = 0; i < countRow; i++) {
                double value = Double.parseDouble(model.getValueAt(i, 3).toString().replace(".", ""));
                lineChartData.addValue(value, "Doanh Thu", model.getValueAt(i, 0).toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        JFreeChart lineChart = ChartFactory.createLineChart(
                title, // Tiêu đề biểu đồ
                x, // Label trục x
                y, // Label trục y
                lineChartData, // Dữ liệu
                PlotOrientation.VERTICAL,
                false,
                true,
                false
        );

        CategoryPlot linePlot = lineChart.getCategoryPlot();
        linePlot.setRangeCrosshairPaint(Color.ORANGE);

        // Customization for LineChart
        CategoryAxis xAxis = linePlot.getDomainAxis();
        xAxis.setLowerMargin(0.02);
        xAxis.setUpperMargin(0.02);

        NumberAxis yAxis = (NumberAxis) linePlot.getRangeAxis();
        yAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());

        ChartPanel linePanel = new ChartPanel(lineChart);
        Panel_bieudo.removeAll();
        Panel_bieudo.setLayout(new BorderLayout());
        Panel_bieudo.add(linePanel, BorderLayout.CENTER);
        Panel_bieudo.validate();

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel5 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        monthChooser = new com.toedter.calendar.JMonthChooser();
        jLabel3 = new javax.swing.JLabel();
        rdo_bdc = new javax.swing.JRadioButton();
        rdo_bdd = new javax.swing.JRadioButton();
        spin_nam = new com.toedter.calendar.JYearChooser();
        button1 = new java.awt.Button();
        btn_dtnam = new java.awt.Button();
        btnLamMoi = new javax.swing.JButton();
        btn_TopDT = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel4 = new javax.swing.JLabel();
        lblTongDoanhThu = new javax.swing.JLabel();
        Panel_bieudo = new javax.swing.JPanel();

        setBackground(new java.awt.Color(187, 205, 197));
        setPreferredSize(new java.awt.Dimension(1180, 730));
        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel5.setBackground(new java.awt.Color(187, 205, 197));
        jPanel5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("THỐNG KÊ");
        jLabel1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jLabel1.setPreferredSize(new java.awt.Dimension(70, 30));
        jPanel5.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 0, 189, 92));

        jLabel2.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Tháng");
        jLabel2.setPreferredSize(new java.awt.Dimension(60, 30));
        jPanel5.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(146, 33, -1, -1));

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
        jPanel5.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 30, 68, -1));

        rdo_bdc.setBackground(new java.awt.Color(187, 205, 197));
        rdo_bdc.setFont(new java.awt.Font("Segoe UI", 3, 12)); // NOI18N
        rdo_bdc.setSelected(true);
        rdo_bdc.setText("Xem biểu đồ cột");
        rdo_bdc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdo_bdcActionPerformed(evt);
            }
        });
        jPanel5.add(rdo_bdc, new org.netbeans.lib.awtextra.AbsoluteConstraints(784, 78, -1, -1));

        rdo_bdd.setBackground(new java.awt.Color(187, 205, 197));
        rdo_bdd.setFont(new java.awt.Font("Segoe UI", 3, 12)); // NOI18N
        rdo_bdd.setText("Xem biểu đồ đường");
        rdo_bdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdo_bddActionPerformed(evt);
            }
        });
        jPanel5.add(rdo_bdd, new org.netbeans.lib.awtextra.AbsoluteConstraints(935, 78, -1, -1));

        spin_nam.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                spin_namPropertyChange(evt);
            }
        });
        jPanel5.add(spin_nam, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 70, -1, 30));

        button1.setFont(new java.awt.Font("Dialog", 3, 12)); // NOI18N
        button1.setLabel("Xuất");
        button1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button1ActionPerformed(evt);
            }
        });
        jPanel5.add(button1, new org.netbeans.lib.awtextra.AbsoluteConstraints(1090, 80, -1, -1));

        btn_dtnam.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        btn_dtnam.setLabel("Xem doanh thu trong năm");
        btn_dtnam.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_dtnamActionPerformed(evt);
            }
        });
        jPanel5.add(btn_dtnam, new org.netbeans.lib.awtextra.AbsoluteConstraints(940, 40, -1, -1));

        btnLamMoi.setBackground(new java.awt.Color(0, 51, 51));
        btnLamMoi.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnLamMoi.setForeground(new java.awt.Color(255, 255, 255));
        btnLamMoi.setText("Làm Mới");
        btnLamMoi.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnLamMoiMouseClicked(evt);
            }
        });
        jPanel5.add(btnLamMoi, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 70, -1, 30));

        btn_TopDT.setBackground(new java.awt.Color(0, 51, 51));
        btn_TopDT.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        btn_TopDT.setForeground(new java.awt.Color(255, 255, 255));
        btn_TopDT.setText("Doanh Thu Tiêu Biểu");
        btn_TopDT.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_TopDTMouseClicked(evt);
            }
        });
        jPanel5.add(btn_TopDT, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 70, -1, 30));

        add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 5, 1168, 130));

        jPanel6.setBackground(new java.awt.Color(187, 205, 197));
        jPanel6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel6.setPreferredSize(new java.awt.Dimension(1167, 598));
        jPanel6.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jTable1.setModel(model  = new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Ngày/Tháng", "Tổng Tiền Đổi Trả","Tổng Tiền Hóa Đơn", "Doanh Thu"
            }
        ));
        jScrollPane2.setViewportView(jTable1);

        jPanel6.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(7, 7, 476, 490));

        jLabel4.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("TỔNG:");
        jPanel6.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 510, 100, 40));

        lblTongDoanhThu.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        lblTongDoanhThu.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTongDoanhThu.setText("jLabel5");
        jPanel6.add(lblTongDoanhThu, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 506, 180, 40));

        add(jPanel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 145, 490, 550));

        Panel_bieudo.setBackground(new java.awt.Color(187, 205, 197));
        Panel_bieudo.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout Panel_bieudoLayout = new javax.swing.GroupLayout(Panel_bieudo);
        Panel_bieudo.setLayout(Panel_bieudoLayout);
        Panel_bieudoLayout.setHorizontalGroup(
            Panel_bieudoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 668, Short.MAX_VALUE)
        );
        Panel_bieudoLayout.setVerticalGroup(
            Panel_bieudoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 548, Short.MAX_VALUE)
        );

        add(Panel_bieudo, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 145, 670, 550));
    }// </editor-fold>//GEN-END:initComponents

    private void rdo_bdcActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdo_bdcActionPerformed

       charAt(tieude,trucX,trucY);
    }//GEN-LAST:event_rdo_bdcActionPerformed

    private void monthChooserPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_monthChooserPropertyChange
       
        XoaAllData();
//        DocDuLieuLenTable();
         String month = String.valueOf(monthChooser.getMonth() + 1);
        String nam = String.valueOf(spin_nam.getValue());
        System.out.println("Tháng " + month + nam);
//        ArrayList<Object[]> ds = tkbus.getListDoanhThuTheoThangvaNam(month, nam);
//        if(ds.isEmpty()){
//            int i=1;
//           if(i!=1) JOptionPane.showMessageDialog(null, "Doanh thu trong tháng/ năm này chưa có !");
//           i++;
//            Date datenow = new Date();
//            monthChooser.setMonth(datenow.getMonth());
//            spin_nam.setYear(datenow.getYear() + 1900);
//           
//        }
//        else for (Object[] tk : ds) {
////            System.out.println("Thong ke " + tk);
//            model.addRow(tk);
//        ChuyenDuLieuSoTrongTableThanhVND();
//        TongDoanhThu();
//        }
//        if (rdo_bdc.isSelected())
//            charAt(tieude,trucX,trucY);
//        else
//            createLineChart(tieude,trucX,trucY);
    }//GEN-LAST:event_monthChooserPropertyChange

    private void btn_dtnamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_dtnamActionPerformed
        // TODO add your handling code here:
        tieude = "Doanh Thu Năm";
        trucX = "Tháng";
        trucY = "Doanh Thu";
        String nam = String.valueOf(spin_nam.getValue());
       
//        ArrayList<Object[]> ds = tkbus.getListDoanhThuTrongNam(nam);
//        if (ds != null) {
//            XoaAllData();
//            for (Object[] tk : ds) {
//                model.addRow(tk);
//            }
//             ChuyenDuLieuSoTrongTableThanhVND();
//            if (rdo_bdc.isSelected()) {
//               charAt(tieude,trucX,trucY);
//            } else {
//                createLineChart(tieude,trucX,trucY);
//            }
//        } else {
//            JOptionPane.showMessageDialog(null, "Doanh Thu trong năm " + nam + " không có dữ liệu");
//             Date datenow = new Date();
//            monthChooser.setMonth(datenow.getMonth());
//            spin_nam.setYear(datenow.getYear() + 1900);
//        }
//        
//        TongDoanhThu();

    }//GEN-LAST:event_btn_dtnamActionPerformed

    private void button1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button1ActionPerformed
        // TODO add your handling code here:
         JFileChooser fileChooser = new JFileChooser("C:\\Users\\MY PC\\OneDrive\\Máy tính");
            fileChooser.setDialogTitle("Chọn nơi lưu file");
            int chon = fileChooser.showSaveDialog(null);
         if (chon == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                String filePath = selectedFile.getAbsolutePath();
                if (!filePath.toLowerCase().endsWith(".xlsx")) {
                    filePath += ".xlsx";
                }
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("Doanh Thu");
        XSSFRow row = null;
        Cell cell = null;
        row = sheet.createRow(3);

        cell = row.createCell(0, CellType.STRING);
        cell.setCellValue("Ngày / Tháng");

        cell = row.createCell(1, CellType.STRING);
        cell.setCellValue("Tổng Tiền Đổi Trả");
        
        cell = row.createCell(2, CellType.STRING);
        cell.setCellValue("Tổng Tiền Hóa Đơn");
        
        cell = row.createCell(3, CellType.STRING);
        cell.setCellValue("Doanh Thu");

        try {
            int rows = jTable1.getRowCount();

            for (int i = 0; i < rows; i++) {
                row = sheet.createRow(4 + i);
                cell = row.createCell(0, CellType.STRING);
                cell.setCellValue(model.getValueAt(i, 0).toString());

                cell = row.createCell(1, CellType.NUMERIC);
                cell.setCellValue(Double.parseDouble(model.getValueAt(i, 1).toString().replace(".", "")));

                cell = row.createCell(2, CellType.NUMERIC);
                cell.setCellValue(Double.parseDouble(model.getValueAt(i, 2).toString().replace(".", "")));
                
                cell = row.createCell(3, CellType.NUMERIC);
                cell.setCellValue(Double.parseDouble(model.getValueAt(i, 3).toString().replace(".", "")));

            }

            File fis = new File(filePath);
            try {
             FileOutputStream fisO = new FileOutputStream(fis);
            workbook.write(fisO);
            JOptionPane.showMessageDialog(null, "Xuất file thành công");
           // fisO.close();
            openExcelFile(fis);
            } catch (Exception e) {
                e.printStackTrace();
            }
        
        } catch (Exception e) {
            e.printStackTrace();
        }
         }


    }//GEN-LAST:event_button1ActionPerformed

    private static void openExcelFile(File file) throws IOException {
        Desktop desktop = Desktop.getDesktop();
        if (desktop.isSupported(Desktop.Action.OPEN)) {
            desktop.open(file);
        } else {
            // Xử lý nếu không thể mở file
            System.out.println("Không thể mở file.");
        }
    }
    private void rdo_bddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdo_bddActionPerformed
        // TODO add your handling code here:
        createLineChart(tieude,trucX,trucY);
    }//GEN-LAST:event_rdo_bddActionPerformed

    private void spin_namPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_spin_namPropertyChange
        // TODO add your handling code here:
       
        XoaAllData();
//        DocDuLieuLenTable();
            String month = String.valueOf(monthChooser.getMonth() + 1);
        String nam = String.valueOf(spin_nam.getValue());
        System.out.println("Tháng " + month + nam);
//        ArrayList<Object[]> ds = tkbus.getListDoanhThuTheoThangvaNam(month, nam);
//        if(ds.isEmpty()){
//            int i =1;
//           if(i!=1) JOptionPane.showMessageDialog(null, "Doanh thu trong tháng/ năm này chưa có !");
//            i++;
//            Date datenow = new Date();
//            monthChooser.setMonth(datenow.getMonth());
//            spin_nam.setYear(datenow.getYear() + 1900);
//           
//        }
//        else for (Object[] tk : ds) {
////            System.out.println("Thong ke " + tk);
//            model.addRow(tk);
//        ChuyenDuLieuSoTrongTableThanhVND();
//        TongDoanhThu();
//        }
//          if (rdo_bdc.isSelected()) {
//         charAt(tieude,trucX,trucY);
//        } else {
//            createLineChart(tieude,trucX,trucY);
//        }
    }//GEN-LAST:event_spin_namPropertyChange

    private void btnLamMoiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnLamMoiMouseClicked
        // TODO add your handling code here:
        tieude = "Doanh Thu Tháng";
        trucX = "Ngày";
        trucY = "Doanh Thu";
        XoaAllData();
        DocDuLieuLenTable();  
        if (rdo_bdc.isSelected()) {
         charAt(tieude,trucX,trucY);
        } else {
            createLineChart(tieude,trucX,trucY);
        }

    }//GEN-LAST:event_btnLamMoiMouseClicked

    private void btn_TopDTMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_TopDTMouseClicked
        // TODO add your handling code here:
        XoaAllData();
           String month = String.valueOf(monthChooser.getMonth() + 1);
        String nam = String.valueOf(spin_nam.getValue());
        System.out.println("Tháng " + month + nam);
//        ArrayList<Object[]> ds = tkbus.getListDoanhThuBySort(month, nam);
//        if(ds.isEmpty()){
//            JOptionPane.showMessageDialog(null, "Doanh thu trong tháng/ năm này chưa có !");
//            Date datenow = new Date();
//            monthChooser.setMonth(datenow.getMonth());
//            spin_nam.setYear(datenow.getYear() + 1900);
//           
//        }
//        else for (Object[] tk : ds) {
////            System.out.println("Thong ke " + tk);
//            model.addRow(tk);
//        ChuyenDuLieuSoTrongTableThanhVND();
//        TongDoanhThu();  
//        }
//        if (rdo_bdc.isSelected()) {
//         charAt(tieude,trucX,trucY);
//        } else {
//            createLineChart(tieude,trucX,trucY);
//        }
    }//GEN-LAST:event_btn_TopDTMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel Panel_bieudo;
    private javax.swing.JButton btnLamMoi;
    private javax.swing.JButton btn_TopDT;
    private java.awt.Button btn_dtnam;
    private java.awt.Button button1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JLabel lblTongDoanhThu;
    private com.toedter.calendar.JMonthChooser monthChooser;
    private javax.swing.JRadioButton rdo_bdc;
    private javax.swing.JRadioButton rdo_bdd;
    private com.toedter.calendar.JYearChooser spin_nam;
    // End of variables declaration//GEN-END:variables
}
