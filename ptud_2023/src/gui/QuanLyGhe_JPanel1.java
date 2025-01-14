/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package gui;

import java.awt.Component;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import dao.Ghe_dao;
import dao.Toa_dao;
import entity.TauEntity;
import entity.TauEnum;
import entity.ToaTauEntity;
import entity.GheEntity;
import java.awt.event.ActionEvent;
import util.GenerateID;

/**
 *
 * @author ploc2
 */
public class QuanLyGhe_JPanel1 extends javax.swing.JFrame {

        private Ghe_dao ghe_dao;
        private Toa_dao toa_dao;
        private DefaultTableModel model;
        // private DefaultTableModel modelToa;
        private TauEntity tauHienTai;
        private String maToaHienTai = "";
//        private String maTauHienTai = "";


    /**
     * Creates new form QuanLyGhe_JPanel
     */
    public QuanLyGhe_JPanel1(TauEntity tau) {
        ghe_dao = new Ghe_dao();
        toa_dao =  new Toa_dao();
        tauHienTai = tau;
        initComponents();

        this.setTitle("Quản lý Ghe");
        this.setVisible(true);

        this.setLocationRelativeTo(null);   
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE); 

        lbl_TenTau.setText("Mã: " + tau.getMaTau() + " - Tên tàu: " + tau.getTenTau());

        model = new DefaultTableModel(new String [] {
            "Mã", "Tên", "Giá", "Loại", "Trạng thái", "Ngày tạo", "Ngày cập nhật"
        }, 0);

        // modelToa = new DefaultTableModel(new String [] {}, 0);



             getAllToaByTau(tau.getMaTau());
    
            // cbo_Toa.
    
            loadDuLieuTuDataLenTable();
    
            btn_CapNhat.setEnabled(false);
            btn_DoiTrangThai.setEnabled(false);
            // btn_Them.setEnabled(false);
        }
        
    private void getAllToaByTau(String maTau) {
        ArrayList<ToaTauEntity> dsToa = toa_dao.getAllToaTau(tauHienTai.getMaTau());

        // Clear existing items first
        cbo_Toa.removeAllItems();

        // Add items
        for (ToaTauEntity toa : dsToa) {
            cbo_Toa.addItem(toa.getTenToa());
        }

        // Only set selected index if there are items
        if (cbo_Toa.getItemCount() > 0) {
            cbo_Toa.setSelectedIndex(0);
        }
    }
        
            /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        btn_Them = new javax.swing.JButton();
        btn_CapNhat = new javax.swing.JButton();
        lbl_TenTau = new javax.swing.JLabel();
        lbl_MaSanPham2 = new javax.swing.JLabel();
        btn_DoiTrangThai = new javax.swing.JButton();
        txt_TenGhe = new javax.swing.JTextField();
        txt_GiaGhe = new javax.swing.JTextField();
        lbl_MaSanPham3 = new javax.swing.JLabel();
        lbl_MaSanPham4 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable_danhSachGhe = new javax.swing.JTable();
        lbl_MaSanPham5 = new javax.swing.JLabel();
        btn_TaoGheTuDong = new javax.swing.JButton();
        lbl_KichThuoc = new javax.swing.JLabel();
        cbo_Toa = new javax.swing.JComboBox<>();
        cbo_LoaiGhe = new javax.swing.JComboBox<>();
        txt_MaGhe = new javax.swing.JTextField();
        lbl_MaSanPham6 = new javax.swing.JLabel();
        cbo_TrangThai = new javax.swing.JComboBox<>();
        btn_TaoLamMoi = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(187, 205, 197));

        btn_Them.setBackground(new java.awt.Color(0, 51, 51));
        btn_Them.setFont(new java.awt.Font("Times New Roman", 1, 15)); // NOI18N
        btn_Them.setForeground(java.awt.Color.white);
        btn_Them.setText("Thêm");
        btn_Them.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_Them.setHorizontalAlignment(javax.swing.SwingConstants.LEADING);
        btn_Them.setPreferredSize(new java.awt.Dimension(90, 31));
        btn_Them.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_ThemActionPerformed(evt);
            }
        });

        btn_CapNhat.setBackground(new java.awt.Color(0, 51, 51));
        btn_CapNhat.setFont(new java.awt.Font("Times New Roman", 1, 15)); // NOI18N
        btn_CapNhat.setForeground(java.awt.Color.white);
        btn_CapNhat.setText("Cập nhật");
        btn_CapNhat.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_CapNhat.setHorizontalAlignment(javax.swing.SwingConstants.LEADING);
        btn_CapNhat.setPreferredSize(new java.awt.Dimension(90, 31));
        btn_CapNhat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_CapNhatActionPerformed(evt);
            }
        });

        lbl_TenTau.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        lbl_TenTau.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl_TenTau.setText("Quản lý Ghế ");
        lbl_TenTau.setPreferredSize(new java.awt.Dimension(85, 15));

        lbl_MaSanPham2.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        lbl_MaSanPham2.setText("Trạng thái");
        lbl_MaSanPham2.setPreferredSize(new java.awt.Dimension(85, 15));

        btn_DoiTrangThai.setBackground(new java.awt.Color(0, 51, 51));
        btn_DoiTrangThai.setFont(new java.awt.Font("Times New Roman", 1, 15)); // NOI18N
        btn_DoiTrangThai.setForeground(java.awt.Color.white);
        btn_DoiTrangThai.setText("Đổi trạng thái");
        btn_DoiTrangThai.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_DoiTrangThai.setHorizontalAlignment(javax.swing.SwingConstants.LEADING);
        btn_DoiTrangThai.setPreferredSize(new java.awt.Dimension(90, 31));
        btn_DoiTrangThai.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_DoiTrangThaiActionPerformed(evt);
            }
        });

        txt_TenGhe.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        txt_TenGhe.setMargin(new java.awt.Insets(0, 0, 0, 0));

        txt_GiaGhe.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        txt_GiaGhe.setMargin(new java.awt.Insets(0, 0, 0, 0));

        lbl_MaSanPham3.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        lbl_MaSanPham3.setText("Giá ghế");
        lbl_MaSanPham3.setPreferredSize(new java.awt.Dimension(85, 15));

        lbl_MaSanPham4.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        lbl_MaSanPham4.setText("Mã ghế");
        lbl_MaSanPham4.setPreferredSize(new java.awt.Dimension(85, 15));

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Danh sách Ghế trong Toa"));

        jScrollPane1.setBackground(new java.awt.Color(187, 205, 197));

        jTable_danhSachGhe.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Mã", "Tên", "Giá", "Loại", "Trạng thái", "Ngày tạo", "Ngày cập nhật"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable_danhSachGhe.setColumnSelectionAllowed(true);
        jTable_danhSachGhe.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        jTable_danhSachGhe.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jTable_danhSachGhe.setShowHorizontalLines(true);
        jTable_danhSachGhe.setShowVerticalLines(true);
        jTable_danhSachGhe.getTableHeader().setResizingAllowed(false);
        jTable_danhSachGhe.setUpdateSelectionOnSort(false);
        jTable_danhSachGhe.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable_danhSachGheMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTable_danhSachGhe);
        jTable_danhSachGhe.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 326, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(12, Short.MAX_VALUE))
        );

        lbl_MaSanPham5.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        lbl_MaSanPham5.setText("Loại ghế");
        lbl_MaSanPham5.setPreferredSize(new java.awt.Dimension(85, 15));

        btn_TaoGheTuDong.setBackground(new java.awt.Color(0, 51, 51));
        btn_TaoGheTuDong.setFont(new java.awt.Font("Times New Roman", 1, 15)); // NOI18N
        btn_TaoGheTuDong.setForeground(java.awt.Color.white);
        btn_TaoGheTuDong.setText("Tạo ghế tự động");
        btn_TaoGheTuDong.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_TaoGheTuDong.setHorizontalAlignment(javax.swing.SwingConstants.LEADING);
        btn_TaoGheTuDong.setPreferredSize(new java.awt.Dimension(90, 31));
        btn_TaoGheTuDong.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_TaoGheTuDongActionPerformed(evt);
            }
        });

        lbl_KichThuoc.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        lbl_KichThuoc.setText("Toa");
        lbl_KichThuoc.setPreferredSize(new java.awt.Dimension(85, 15));

        cbo_Toa.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        cbo_Toa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbo_ToaActionPerformed(evt);
            }
        });

        cbo_LoaiGhe.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        cbo_LoaiGhe.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Khác", "Ghế ngồi", "Ghế nằm" }));

        txt_MaGhe.setEditable(false);
        txt_MaGhe.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        txt_MaGhe.setMargin(new java.awt.Insets(0, 0, 0, 0));
//        txt_TenGhe.setEditable(false);

        lbl_MaSanPham6.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        lbl_MaSanPham6.setText("Tên ghế");
        lbl_MaSanPham6.setPreferredSize(new java.awt.Dimension(85, 15));

        cbo_TrangThai.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        cbo_TrangThai.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Tạm ngưng", "Đang sử dụng" }));
        cbo_TrangThai.setActionCommand("");

        btn_TaoLamMoi.setBackground(new java.awt.Color(0, 51, 51));
        btn_TaoLamMoi.setFont(new java.awt.Font("Times New Roman", 1, 15)); // NOI18N
        btn_TaoLamMoi.setForeground(java.awt.Color.white);
        btn_TaoLamMoi.setText("Làm mới");
        btn_TaoLamMoi.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_TaoLamMoi.setHorizontalAlignment(javax.swing.SwingConstants.LEADING);
        btn_TaoLamMoi.setPreferredSize(new java.awt.Dimension(90, 31));
        btn_TaoLamMoi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_TaoLamMoiActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbl_TenTau, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lbl_KichThuoc, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lbl_MaSanPham6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txt_TenGhe, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cbo_Toa, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addComponent(btn_Them, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(48, 48, 48)
                                .addComponent(btn_CapNhat, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(lbl_MaSanPham3, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txt_GiaGhe, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                        .addComponent(lbl_MaSanPham2, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(cbo_TrangThai, 0, 200, Short.MAX_VALUE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(lbl_MaSanPham5, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(lbl_MaSanPham4, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(cbo_LoaiGhe, 0, 200, Short.MAX_VALUE)
                                            .addComponent(txt_MaGhe))))
                                .addGap(263, 263, 263))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(37, 37, 37)
                                .addComponent(btn_DoiTrangThai, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(30, 30, 30)
                                .addComponent(btn_TaoGheTuDong, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btn_TaoLamMoi, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(lbl_TenTau, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbl_KichThuoc, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbo_Toa, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_MaGhe, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_MaSanPham4, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_TenGhe, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_MaSanPham5, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbo_LoaiGhe, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_MaSanPham6, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lbl_MaSanPham2, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(cbo_TrangThai, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(txt_GiaGhe, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_MaSanPham3, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btn_Them, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btn_CapNhat, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btn_DoiTrangThai, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btn_TaoGheTuDong, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btn_TaoLamMoi, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 575, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_DoiTrangThaiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_DoiTrangThaiActionPerformed
        doiTrangThaiGhe();
    }//GEN-LAST:event_btn_DoiTrangThaiActionPerformed

    private void btn_CapNhatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_CapNhatActionPerformed
        capNhatGhe();
    }//GEN-LAST:event_btn_CapNhatActionPerformed

    private void btn_ThemActionPerformed(java.awt.event.ActionEvent evt) {
        themGhe();
    }

    private void btn_TaoGheTuDongActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_TaoGheTuDongActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_TaoGheTuDongActionPerformed

    private void btn_TaoLamMoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_TaoLamMoiActionPerformed
        // TODO add your handling code here:
        lamMoi();
    }//GEN-LAST:event_btn_TaoLamMoiActionPerformed

    private void cbo_ToaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbo_ToaActionPerformed
            lamMoi();
            loadDuLieuTuDataLenTable();
    }//GEN-LAST:event_cbo_ToaActionPerformed

    private void themGhe() {
        try {
            // Check if combo box has selected item
            if (cbo_Toa.getSelectedItem() == null) {
                JOptionPane.showMessageDialog(null, "Vui lòng chọn toa");
                return;
            }

            String tenToa = cbo_Toa.getSelectedItem().toString();
            String maToa = toa_dao.layMaToaTheoTen(tauHienTai.getMaTau(), tenToa);

            // lay thong tin toa
            ToaTauEntity toa = toa_dao.getToaTauByMaToa(maToa);
            System.out.println("toa: " + toa.toString());
            if(toa == null) {
                JOptionPane.showMessageDialog(null, "Toa không tồn tại");
                return;
            
            }            
            int soGheHienTai = ghe_dao.laySoLuongGheTheoMaToa(maToa);

         System.out.println("soGheHienTai: " + soGheHienTai + " - " + toa.getSoGhe());
        if(soGheHienTai >= toa.getSoGhe()) {
            JOptionPane.showMessageDialog(null, "Bạn đã sử dụng tối đa toa, không thể tạo toa mới");
            return;
        }

        GheEntity ghe =  validateGheTau(toa);
        
        // toa.setMaGhe(maGhe);
        //  boolean kq = ;
        if (ghe_dao.themGhe(ghe) && ghe != null )  {
            JOptionPane.showMessageDialog(null, "Thêm Ghe thành công");
            lamMoi();
        } else {
            JOptionPane.showMessageDialog(null, "Thêm Ghe không thành công");
        }
        
    } catch (Exception e) {
        e.printStackTrace();
        System.out.println("Có lỗi xảy ra khi thêm bản ghi: " + e.getMessage());
    }
    }

    public GheEntity validateGheTau(ToaTauEntity toa) {
        // get dữ liệu txt_MaGhe txt_tenGhe txt_trangThai
        
        int trangThai = cbo_TrangThai.getSelectedIndex();
        int loaiGhe = cbo_LoaiGhe.getSelectedIndex();
        String tenGhe = txt_TenGhe.getText(); 
        int giaGhe;
        try {
            giaGhe = Integer.parseInt(txt_GiaGhe.getText());
            if (giaGhe <= 0) {
                JOptionPane.showMessageDialog(null, "Giá ghế phải lớn hơn 0");
                return null;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Giá ghế phải là một số nguyên hợp lệ");
            return null;
        }
        if(tenGhe.isEmpty() || !(giaGhe > 0)  ) {
            JOptionPane.showMessageDialog(null, "Vui lòng nhập đầy đủ thông tin");
            return null;
        }

        if(ghe_dao.kiemTraTenGhe(tenGhe, toa.getMaToa()) ) {
            JOptionPane.showMessageDialog(null, "Tên Ghế đã tồn tại");
            return null;
        }

        GheEntity ghe = new GheEntity();
        ghe.setTen(tenGhe);
        ghe.setLoai(loaiGhe);
        ghe.setGia(giaGhe);
        ghe.setTrangThai(trangThai);
        ghe.setToa(toa);
        return ghe;
    }

    private void lamMoi() {
            txt_TenGhe.setText("");
            cbo_TrangThai.setSelectedIndex(0);
            txt_GiaGhe.setText("0");

            btn_CapNhat.setEnabled(false);
            btn_DoiTrangThai.setEnabled(false);
            btn_TaoGheTuDong.setEnabled(true);
            btn_Them.setEnabled(true);

            loadDuLieuTuDataLenTable();
    }

    private void loadDuLieuTuDataLenTable() {

        // if get maToa from cbo_maTOa
        String maToa = toa_dao.layMaToaTheoTen(tauHienTai.getMaTau(), (String) cbo_Toa.getSelectedItem());

        ArrayList<GheEntity> dsGhe = ghe_dao.getAllGhe(maToa);

        model.setRowCount(0);
        
        for (GheEntity toa : dsGhe) {   

            String trangThai = TauEnum.TinhTrangTau.convertTinhTrangTauToString(toa.getTrangThai())  ;
            String loaiGhe = TauEnum.LoaiGhe.convertLoaiGheToString(toa.getLoai());
            model.addRow(new Object[]{
                    toa.getMaGhe(), toa.getTen(), toa.getGia(),loaiGhe,  trangThai, toa.getNgayTao(), toa.getNgayCapNhat()
            });
        }

        // add data to jTable
        jTable_danhSachGhe.setModel(model);

    }

    private void capNhatGhe() {
        try {
            // get dữ liệu txt_MaGhe txt_tenGhe txt_trangThai
        int maGhe = Integer.parseInt(txt_MaGhe.getText());

        String tenToa = (String) cbo_Toa.getSelectedItem().toString();

        // lay thong tin toa
        ToaTauEntity toa = toa_dao.getToaTauByTenToa(tenToa, tauHienTai);

        GheEntity ghe = validateGheTau(toa);
        if(ghe == null) {
            JOptionPane.showMessageDialog(null, "Vui lòng nhập đầy đủ thông tin");
            return;
        }

        ghe.setMaGhe(maGhe);

        if (ghe_dao.capNhatGhe(ghe))  {
            JOptionPane.showMessageDialog(null, "Cập nhật Ghe thành công");
            lamMoi();
        } else {
            JOptionPane.showMessageDialog(null, "Cập nhật Ghe không thành công");
        }
        
    } catch (Exception e) {
        e.printStackTrace();
        System.out.println("Có lỗi xảy ra khi thêm bản ghi: " + e.getMessage());
    }
    }

    private void doiTrangThaiGhe() {
        try {
            // get dữ liệu txt_MaGhe txt_tenGhe txt_trangThai
            String maGhe = txt_TenGhe.getText();

            int trangThai = cbo_TrangThai.getSelectedIndex();

            

//             GheTauEntity toa = validateGheTau();
//
//             if(toa == null) {
//                 JOptionPane.showMessageDialog(null, "Vui lòng nhập đầy đủ thông tin");
//                 return;
//             }
//
//             toa.setMaGhe(maGhe);
            boolean kq =ghe_dao.doiTrangThaiGhe(maGhe, trangThai);    
            
            if (kq) {
                JOptionPane.showMessageDialog(null, "Đổi trạng thái thành công");
                lamMoi();
            } else {
                JOptionPane.showMessageDialog(null, "Đổi trạng thái không thành công");
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            System.out.println("Có lỗi xảy ra khi thêm bản ghi: " + e.getMessage());
        }
        
    }

    private void jTable_danhSachGheMouseClicked(MouseEvent evt) {
        int row = jTable_danhSachGhe.getSelectedRow();
        
        // Check if row is valid
        if (row < 0 || row >= model.getRowCount()) {
            return;
        }

        // Check for null values before getting data
        Object maGhe = model.getValueAt(row, 0);
        Object tenGhe = model.getValueAt(row, 1);
        Object giaGhe = model.getValueAt(row, 2);
        Object loaiGhe = model.getValueAt(row, 3);
        Object trangThai = model.getValueAt(row, 4);

        if (maGhe != null) txt_MaGhe.setText(maGhe.toString());
        if (tenGhe != null) txt_TenGhe.setText(tenGhe.toString());
        if (giaGhe != null) txt_GiaGhe.setText(giaGhe.toString());
        if (loaiGhe != null) cbo_LoaiGhe.setSelectedItem(loaiGhe.toString());
        if (trangThai != null) cbo_TrangThai.setSelectedItem(trangThai.toString());

        btn_CapNhat.setEnabled(true);
        btn_DoiTrangThai.setEnabled(true);
        btn_Them.setEnabled(true);
        btn_TaoGheTuDong.setEnabled(false);
    
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_CapNhat;
    private javax.swing.JButton btn_DoiTrangThai;
    private javax.swing.JButton btn_TaoGheTuDong;
    private javax.swing.JButton btn_TaoLamMoi;
    private javax.swing.JButton btn_Them;
    private javax.swing.JComboBox<String> cbo_LoaiGhe;
    private javax.swing.JComboBox<String> cbo_Toa;
    private javax.swing.JComboBox<String> cbo_TrangThai;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable_danhSachGhe;
    private javax.swing.JLabel lbl_KichThuoc;
    private javax.swing.JLabel lbl_MaSanPham2;
    private javax.swing.JLabel lbl_MaSanPham3;
    private javax.swing.JLabel lbl_MaSanPham4;
    private javax.swing.JLabel lbl_MaSanPham5;
    private javax.swing.JLabel lbl_MaSanPham6;
    private javax.swing.JLabel lbl_TenTau;
    private javax.swing.JTextField txt_GiaGhe;
    private javax.swing.JTextField txt_MaGhe;
    private javax.swing.JTextField txt_TenGhe;
    // End of variables declaration//GEN-END:variables
}

//Căn giữa cột trong table
class CenterRenderer extends DefaultTableCellRenderer {

    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        ((JLabel) c).setHorizontalAlignment(SwingConstants.CENTER);
        return c;
    }
}
