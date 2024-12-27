/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package gui;

import java.awt.Color;
import java.awt.Container;
import java.net.URL;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import entity.GheEntity;
import entity.KhachHangEntity;
import entity.LichTrinhEntity;
import entity.TauEntity;
import entity.ToaTauEntity;
import entity.VeEntity;
import javax.swing.JTextField;
import util.ToanCuc;

/**
 *
 * @author ploc2
 */
public class ChiTietGioVe extends javax.swing.JPanel {

    /**
     * Creates new form ChiTietGiove
     */

    private TauEntity tau;
    private LichTrinhEntity lichtrinh;
    private ToaTauEntity toa;
    private GheEntity ghe;

    public TauEntity getTau() {
        return tau;
    }

    public void setTau(TauEntity tau) {
        this.tau = tau;
    }

    public LichTrinhEntity getLichtrinh() {
        return lichtrinh;
    }

    public void setLichtrinh(LichTrinhEntity lichtrinh) {
        this.lichtrinh = lichtrinh;
    }

    public ToaTauEntity getToa() {
        return toa;
    }

    public void setToa(ToaTauEntity toa) {
        this.toa = toa;
    }

    public GheEntity getGhe() {
        return ghe;
    }

    public void setGhe(GheEntity ghe) {
        this.ghe = ghe;
    }

    public String getTxt_CMND() {
        return txt_CMND.getText();
    }

    public void setTxt_CMND(JTextField txt_CMND) {
        this.txt_CMND = txt_CMND;
    }

    public String getTxt_hoTen() {
        return txt_hoTen.getText();
    }

    public void setTxt_hoTen(JTextField txt_hoTen) {
        this.txt_hoTen = txt_hoTen;
    }

    public String getTxt_tuoi() {
        return txt_tuoi.getText();
    }

    public void setTxt_tuoi(JTextField txt_tuoi) {
        this.txt_tuoi = txt_tuoi;
    }

    public ChiTietGioVe(LichTrinhEntity lichTrinh, TauEntity tauHienTai, ToaTauEntity toaHienTai,
            GheEntity gheHienTai) {
        initComponents();

        this.tau = tauHienTai;
        this.lichtrinh = lichTrinh;
        this.toa = toaHienTai;
        this.ghe = gheHienTai;
        System.out.println("tau" + tau);

        URL urlBtnLamMoi = getClass().getResource("/pic/icon/buttonXoa.png");
        ImageIcon img_btnLamMoi = new ImageIcon(urlBtnLamMoi);
        img_btnLamMoi = new ImageIcon(img_btnLamMoi.getImage().getScaledInstance(30, 20, 0));
        jLabel_btnXoa.setIcon(img_btnLamMoi);

        jLabel_btnXoa.setToolTipText("Xóa vé");

        URL urlBtnLamMoi2 = getClass().getResource("/pic/icon/buttonThem.png");
        ImageIcon img_btnLamMoi2 = new ImageIcon(urlBtnLamMoi2);
        img_btnLamMoi2 = new ImageIcon(img_btnLamMoi2.getImage().getScaledInstance(30, 20, 0));
        jLabel_btnThem.setIcon(img_btnLamMoi2);

        jLabel_btnThem.setToolTipText("Thêm Hành Khách");

        // add mouse listener to button and remove it from this parent panel
        jLabel_btnXoa.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel_btnXoaMouseClicked(evt);
            }

            // hover effect
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                jLabel_btnXoa.setBackground(Color.RED);
            }

            // hover effect
            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                jLabel_btnXoa.setBackground(Color.WHITE);
            }
        });

        jLabel_btnThem.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                gnaKhachHang();
            }

            // hover effect
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                jLabel_btnThem.setBackground(Color.GREEN);
            }

            // hover effect
            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                jLabel_btnThem.setBackground(Color.WHITE);
            }
        });

        if (tau != null && lichtrinh != null && toa != null && ghe != null) {
            jLabel_TenTauVaGa
                    .setText("Tàu " + this.tau.getTenTau() + "   " + this.tau.getGaDi().getMaGa() + " - "
                            + this.tau.getGaDen().getMaGa());
            jLabel_ThoiGian.setText(this.lichtrinh.getNgayDi() + "  " + this.lichtrinh.getGioDi());
            jLabel_ToaVaGhe.setText("Toa " + this.toa.getTenToa() + " - Số ghế" + this.ghe.getSoGhe());

        }

    }

    private void gnaKhachHang() {
        // Kiểm tra xem các ô nhập liệu có rỗng không
        if (txt_hoTen.getText().isEmpty() || txt_CMND.getText().isEmpty() || txt_tuoi.getText().isEmpty()) {
            // Nếu có ô nào rỗng, có thể hiển thị thông báo
            JOptionPane.showMessageDialog(null, "Vui lòng nhập đầy đủ thông tin khách hàng", "Thông báo",
                    JOptionPane.WARNING_MESSAGE);
        } else {
            // Lấy thông tin từ các ô nhập liệu (nếu cần)
            String hoTen = txt_hoTen.getText();
            String cmnd = txt_CMND.getText();
            String tuoi = txt_tuoi.getText();

            KhachHangEntity khachHang = new KhachHangEntity();

            khachHang.setHoTen(hoTen);
            khachHang.setTuoi(Integer.parseInt(tuoi));
            khachHang.setSoCCCD(cmnd);

            VeEntity ve = new VeEntity();

            ve.setKhachHang(khachHang);

            JOptionPane.showMessageDialog(null, "Gán Thành công");
        }
    }

    private void jLabel_btnXoaMouseClicked(java.awt.event.MouseEvent evt) {
        Container parent = this.getParent();
        if (parent != null) {
            parent.remove(this); // Remove this component
            parent.revalidate(); // Revalidate to refresh the layout
            parent.repaint(); // Repaint the parent to reflect the changes
        } else {
            System.err.println("Parent container is null, cannot remove the component.");
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated
    // Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel_ThoiGian = new javax.swing.JLabel();
        jLabel_ToaVaGhe = new javax.swing.JLabel();
        jLabel_TenTauVaGa = new javax.swing.JLabel();
        jLabel_btnXoa = new javax.swing.JLabel();
        jLabel_btnThem = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        CMND = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txt_tuoi = new javax.swing.JTextField();
        txt_CMND = new javax.swing.JTextField();
        txt_hoTen = new javax.swing.JTextField();

        setBorder(javax.swing.BorderFactory
                .createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0))));
        setMaximumSize(new java.awt.Dimension(532, 200));

        jLabel_ThoiGian.setText("11/11/2024 06:00");
        jLabel_ThoiGian.setMaximumSize(new java.awt.Dimension(200, 30));
        jLabel_ThoiGian.setMinimumSize(new java.awt.Dimension(200, 30));
        jLabel_ThoiGian.setPreferredSize(new java.awt.Dimension(200, 30));

        jLabel_ToaVaGhe.setText("Toa 2 chỗ 33");
        jLabel_ToaVaGhe.setMaximumSize(new java.awt.Dimension(200, 30));
        jLabel_ToaVaGhe.setMinimumSize(new java.awt.Dimension(200, 30));
        jLabel_ToaVaGhe.setPreferredSize(new java.awt.Dimension(200, 30));

        jLabel_TenTauVaGa.setText("SE8 Sài Gòn - Hà Nội");
        jLabel_TenTauVaGa.setMaximumSize(new java.awt.Dimension(200, 30));
        jLabel_TenTauVaGa.setMinimumSize(new java.awt.Dimension(200, 30));
        jLabel_TenTauVaGa.setPreferredSize(new java.awt.Dimension(200, 30));

        jLabel_btnXoa.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel_btnXoa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pic/icon/buttonXoa.png"))); // NOI18N
        jLabel_btnXoa.setText("Xóa");
        jLabel_btnXoa.setMaximumSize(new java.awt.Dimension(35, 35));
        jLabel_btnXoa.setMinimumSize(new java.awt.Dimension(35, 35));
        jLabel_btnXoa.setPreferredSize(new java.awt.Dimension(35, 35));

        jLabel_btnThem.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel_btnThem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pic/icon/buttonXoa.png"))); // NOI18N
        jLabel_btnThem.setText("Gán HK");
        jLabel_btnThem.setMaximumSize(new java.awt.Dimension(35, 35));
        jLabel_btnThem.setMinimumSize(new java.awt.Dimension(35, 35));
        jLabel_btnThem.setPreferredSize(new java.awt.Dimension(35, 35));

        jLabel4.setText("Họ và tên");

        CMND.setText("Số căn cước");

        jLabel5.setText("Tuổi");

        txt_tuoi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_tuoiActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addGroup(layout
                                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(jLabel_ToaVaGhe,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addGroup(layout.createSequentialGroup()
                                                                .addComponent(jLabel_TenTauVaGa,
                                                                        javax.swing.GroupLayout.PREFERRED_SIZE, 144,
                                                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(
                                                                        javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                                .addComponent(jLabel_ThoiGian,
                                                                        javax.swing.GroupLayout.PREFERRED_SIZE, 125,
                                                                        javax.swing.GroupLayout.PREFERRED_SIZE))
                                                        .addGroup(layout.createSequentialGroup()
                                                                .addComponent(jLabel4,
                                                                        javax.swing.GroupLayout.PREFERRED_SIZE, 79,
                                                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(
                                                                        javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(txt_hoTen,
                                                                        javax.swing.GroupLayout.PREFERRED_SIZE, 186,
                                                                        javax.swing.GroupLayout.PREFERRED_SIZE)))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addGroup(layout
                                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addGroup(layout.createSequentialGroup()
                                                                .addComponent(jLabel_btnThem,
                                                                        javax.swing.GroupLayout.PREFERRED_SIZE, 91,
                                                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addGap(43, 43, 43)
                                                                .addComponent(jLabel_btnXoa,
                                                                        javax.swing.GroupLayout.PREFERRED_SIZE, 91,
                                                                        javax.swing.GroupLayout.PREFERRED_SIZE))
                                                        .addGroup(layout.createSequentialGroup()
                                                                .addComponent(jLabel5,
                                                                        javax.swing.GroupLayout.PREFERRED_SIZE, 42,
                                                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(
                                                                        javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(txt_tuoi,
                                                                        javax.swing.GroupLayout.PREFERRED_SIZE, 47,
                                                                        javax.swing.GroupLayout.PREFERRED_SIZE))))
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(CMND, javax.swing.GroupLayout.PREFERRED_SIZE, 79,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(txt_CMND, javax.swing.GroupLayout.PREFERRED_SIZE, 186,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addContainerGap()
                                                .addGroup(layout
                                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(jLabel_TenTauVaGa,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE, 24,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(jLabel_ThoiGian,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE, 24,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jLabel_ToaVaGhe, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                        24, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(14, 14, 14)
                                                .addGroup(layout
                                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(jLabel_btnXoa,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(jLabel_btnThem,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED,
                                        javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 27,
                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(txt_hoTen, javax.swing.GroupLayout.PREFERRED_SIZE, 27,
                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 27,
                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(txt_tuoi, javax.swing.GroupLayout.PREFERRED_SIZE, 27,
                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(CMND, javax.swing.GroupLayout.PREFERRED_SIZE, 27,
                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(txt_CMND, javax.swing.GroupLayout.PREFERRED_SIZE, 27,
                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(33, 33, 33)));
    }// </editor-fold>//GEN-END:initComponents

    private void txt_tuoiActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_txt_tuoiActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_txt_tuoiActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel CMND;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel_TenTauVaGa;
    private javax.swing.JLabel jLabel_ThoiGian;
    private javax.swing.JLabel jLabel_ToaVaGhe;
    private javax.swing.JLabel jLabel_btnThem;
    private javax.swing.JLabel jLabel_btnXoa;
    private javax.swing.JTextField txt_CMND;
    private javax.swing.JTextField txt_hoTen;
    private javax.swing.JTextField txt_tuoi;
    // End of variables declaration//GEN-END:variables
}
