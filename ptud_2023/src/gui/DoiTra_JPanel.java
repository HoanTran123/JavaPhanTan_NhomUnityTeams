package gui;

import java.awt.*;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.event.*;
import javax.swing.*;
import org.jdesktop.layout.GroupLayout;

public class DoiTra_JPanel extends javax.swing.JPanel {

    private CardLayout cartLayout;

    /**
     * Creates new form HoaDon_JPanel
     */
    public DoiTra_JPanel() {
        initComponents();
        setBounds(0, 0, 1186, 748);
        cartLayout = new CardLayout();
        panel_content.setLayout(cartLayout);
        
        TaoDoiTra_JPanel TaoDoiTra_Panel = new TaoDoiTra_JPanel();
        QuanLyDoiTra_JPanel QuanLyDoiTra_Panel = new QuanLyDoiTra_JPanel();
        panel_content.add(TaoDoiTra_Panel, "TaoDoiTra");
        panel_content.add(QuanLyDoiTra_Panel, "QuanLyDoiTra");
        
        btn_TaoDoiTra.setBackground(new Color(187,205,197));
        btn_TaoDoiTra.setSelected(true);
        btn_TaoDoiTra.setForeground(Color.black);
        btn_TaoDoiTra.setBorder(null);
        btn_QuanLyDoiTra.setBackground(new Color(0,51,51));
        btn_QuanLyDoiTra.setSelected(false);
        btn_QuanLyDoiTra.setForeground(Color.white);
        cartLayout.show(panel_content, "TaoDoiTra");
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panel_menu = new javax.swing.JPanel();
        btn_TaoDoiTra = new javax.swing.JButton();
        btn_QuanLyDoiTra = new javax.swing.JButton();
        panel_content = new javax.swing.JPanel();

        setBackground(new java.awt.Color(187, 205, 197));
        setMinimumSize(new java.awt.Dimension(1020, 700));
        setPreferredSize(new java.awt.Dimension(1186, 748));
        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        panel_menu.setBackground(new java.awt.Color(187, 205, 197));
        panel_menu.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btn_TaoDoiTra.setBackground(new java.awt.Color(0, 51, 51));
        btn_TaoDoiTra.setFont(new java.awt.Font("Times New Roman", 1, 15)); // NOI18N
        btn_TaoDoiTra.setForeground(new java.awt.Color(255, 255, 255));
        btn_TaoDoiTra.setText("Tạo đơn đổi trả");
        btn_TaoDoiTra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_TaoDoiTraActionPerformed(evt);
            }
        });
        panel_menu.add(btn_TaoDoiTra, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 140, 30));

        btn_QuanLyDoiTra.setBackground(new java.awt.Color(0, 51, 51));
        btn_QuanLyDoiTra.setFont(new java.awt.Font("Times New Roman", 1, 15)); // NOI18N
        btn_QuanLyDoiTra.setForeground(new java.awt.Color(255, 255, 255));
        btn_QuanLyDoiTra.setText("Quản lý đơn đổi trả");
        btn_QuanLyDoiTra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_QuanLyDoiTraActionPerformed(evt);
            }
        });
        panel_menu.add(btn_QuanLyDoiTra, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 0, 180, 30));

        add(panel_menu, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 1170, 30));

        javax.swing.GroupLayout panel_contentLayout = new javax.swing.GroupLayout(panel_content);
        panel_content.setLayout(panel_contentLayout);
        panel_contentLayout.setHorizontalGroup(
            panel_contentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        panel_contentLayout.setVerticalGroup(
            panel_contentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        add(panel_content, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, 1170, 690));
    }// </editor-fold>//GEN-END:initComponents

    private void btn_TaoDoiTraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_TaoDoiTraActionPerformed
        btn_TaoDoiTra.setBackground(new Color(187,205,197));
        btn_TaoDoiTra.setSelected(true);
        btn_TaoDoiTra.setForeground(Color.black);
        btn_TaoDoiTra.setBorder(null);
        btn_QuanLyDoiTra.setBackground(new Color(0,51,51));
        btn_QuanLyDoiTra.setSelected(false);
        btn_QuanLyDoiTra.setForeground(Color.white);
        cartLayout.show(panel_content, "TaoDoiTra");
    }//GEN-LAST:event_btn_TaoDoiTraActionPerformed

    private void btn_QuanLyDoiTraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_QuanLyDoiTraActionPerformed
        btn_QuanLyDoiTra.setBackground(new Color(187,205,197));
        btn_QuanLyDoiTra.setSelected(true);
        btn_QuanLyDoiTra.setForeground(Color.black);
        btn_QuanLyDoiTra.setBorder(null);
        btn_TaoDoiTra.setBackground(new Color(0,51,51));
        btn_TaoDoiTra.setSelected(false);
        btn_TaoDoiTra.setForeground(Color.white);
        cartLayout.show(panel_content, "QuanLyDoiTra");
    }//GEN-LAST:event_btn_QuanLyDoiTraActionPerformed

    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_QuanLyDoiTra;
    private javax.swing.JButton btn_TaoDoiTra;
    private javax.swing.JPanel panel_content;
    private javax.swing.JPanel panel_menu;
    // End of variables declaration//GEN-END:variables
}
