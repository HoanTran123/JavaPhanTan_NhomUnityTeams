package view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MainFrame extends JFrame {
    private JList<String> menuList;
    private JPanel contentPanel;
    private DefaultListModel<String> menuModel;

    public MainFrame() {
        setTitle("Quản Lý Nhà Thuốc - UnityTeams");
        setSize(1366, 768);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Set modern Look and Feel
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
            UIManager.put("nimbusBase", new Color(0, 102, 204));
            UIManager.put("control", new Color(248, 249, 250));
            UIManager.put("nimbusFocus", new Color(115, 164, 209));
            UIManager.put("nimbusLightBackground", new Color(248, 249, 250));
        } catch (Exception e) {
            e.printStackTrace();
        }

        add(createHeaderPanel(), BorderLayout.NORTH);
        add(createSidebarPanel(), BorderLayout.WEST);
        add(createContentPanel(), BorderLayout.CENTER);

        // Select first menu item by default
        menuList.setSelectedIndex(0);
    }

    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                GradientPaint gp = new GradientPaint(0, 0, new Color(0, 102, 204), getWidth(), 0, new Color(0, 153, 255));
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        headerPanel.setPreferredSize(new Dimension(0, 80));
        headerPanel.setBorder(new EmptyBorder(0, 25, 0, 25));

        JLabel headerLabel = new JLabel("HỆ THỐNG QUẢN LÝ NHÀ THUỐC");
        headerLabel.setForeground(Color.WHITE);
        headerLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        headerPanel.add(headerLabel, BorderLayout.WEST);

        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.setOpaque(false);
        rightPanel.add(createUserPanel(), BorderLayout.CENTER);
        rightPanel.add(createToolbarPanel(), BorderLayout.SOUTH);

        headerPanel.add(rightPanel, BorderLayout.EAST);
        return headerPanel;
    }

    private JPanel createUserPanel() {
        JPanel userPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 0));
        userPanel.setOpaque(false);

        JLabel lblUser = new JLabel("Admin");
        lblUser.setForeground(Color.WHITE);
        lblUser.setFont(new Font("Segoe UI", Font.BOLD, 16));

        userPanel.add(lblUser);
        return userPanel;
    }

    private JPanel createToolbarPanel() {
        JPanel toolbarPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        toolbarPanel.setOpaque(false);

        JButton btnSettings = createToolbarButton("Cài đặt", "/icons/settings.png");
        JButton btnLogout = createToolbarButton("Đăng xuất", "/icons/logout.png");

        toolbarPanel.add(btnSettings);
        toolbarPanel.add(btnLogout);
        return toolbarPanel;
    }

    private JPanel createSidebarPanel() {
        menuModel = new DefaultListModel<>();
        String[] menuItems = {
                "Quản Lý Khách Hàng",
                "Quản Lý Hóa Đơn",
                "Quản Lý Nhà Cung Cấp",
                "Quản Lý Thuốc",
                "Quản Lý Phiếu Nhập",
                "Quản Lý Khuyến Mãi",
                "Thống Kê"
        };
        for (String item : menuItems) {
            menuModel.addElement(item);
        }

        menuList = new JList<>(menuModel);
        menuList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        menuList.setFixedCellHeight(60);
        menuList.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        menuList.setBackground(new Color(40, 50, 60));
        menuList.setForeground(new Color(220, 220, 220));
        menuList.setBorder(new EmptyBorder(15, 20, 15, 20));

        menuList.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                label.setBorder(new EmptyBorder(10, 25, 10, 10));
                label.setFont(new Font("Segoe UI", isSelected ? Font.BOLD : Font.PLAIN, 16));
                if (isSelected) {
                    label.setBackground(new Color(0, 102, 204));
                    label.setForeground(Color.WHITE);
                    label.setBorder(BorderFactory.createCompoundBorder(
                            BorderFactory.createMatteBorder(0, 4, 0, 0, new Color(255, 255, 255)),
                            new EmptyBorder(10, 21, 10, 10)
                    ));
                } else {
                    label.setBackground(new Color(40, 50, 60));
                    label.setForeground(new Color(220, 220, 220));
                }
                return label;
            }
        });

        menuList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedIndex = menuList.getSelectedIndex();
                switch (selectedIndex) {
                    case 0:
                        showPanel(new KhachHangPanel());
                        break;
                    case 1:
                        showPanel(new HoaDonPanel());
                        break;
                    case 2:
                        showPanel(new NhaCungCapPanel());
                        break;
                    case 3:
                        showPanel(new ThuocPanel());
                        break;
                    default:
                        contentPanel.removeAll();
                        JLabel placeholder = new JLabel("💡 Vui lòng chọn một mục từ menu để hiển thị", SwingConstants.CENTER);
                        placeholder.setFont(new Font("Segoe UI", Font.PLAIN, 20));
                        placeholder.setForeground(new Color(120, 120, 120));
                        contentPanel.add(placeholder, BorderLayout.CENTER);
                        contentPanel.revalidate();
                        contentPanel.repaint();
                        break;
                }
            }
        });

        JScrollPane menuScroll = new JScrollPane(menuList);
        menuScroll.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, new Color(200, 200, 200)));
        menuScroll.setPreferredSize(new Dimension(280, 0));

        JPanel logoPanel = new JPanel(new BorderLayout());
        logoPanel.setBackground(new Color(40, 50, 60));
        logoPanel.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, new Color(70, 80, 90)));

        JLabel logoLabel = new JLabel("UnityTeams", SwingConstants.CENTER);
        logoLabel.setForeground(new Color(180, 180, 180));
        logoLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        logoLabel.setBorder(new EmptyBorder(15, 0, 15, 0));

        logoPanel.add(logoLabel, BorderLayout.CENTER);

        JPanel sidebarPanel = new JPanel(new BorderLayout());
        sidebarPanel.add(menuScroll, BorderLayout.CENTER);
        sidebarPanel.add(logoPanel, BorderLayout.SOUTH);
        return sidebarPanel;
    }

    private JPanel createContentPanel() {
        contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBackground(new Color(248, 249, 250));
        contentPanel.setBorder(new EmptyBorder(25, 25, 25, 25));
        return contentPanel;
    }

    private JButton createToolbarButton(String text, String iconPath) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        button.setBackground(new Color(255, 255, 255, 150));
        button.setForeground(Color.WHITE);
        button.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setBorderPainted(false);
        button.setOpaque(false);

        ImageIcon icon = loadIcon(iconPath);
        if (icon != null) {
            button.setIcon(icon);
            button.setHorizontalTextPosition(SwingConstants.LEFT);
            button.setIconTextGap(10);
        }

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(new Color(255, 255, 255, 200));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(new Color(255, 255, 255, 150));
            }
        });

        return button;
    }

    private ImageIcon loadIcon(String path) {
        java.net.URL imgURL = getClass().getResource(path);
        return imgURL != null ? new ImageIcon(imgURL) : null;
    }

    private void showPanel(JPanel panel) {
        contentPanel.removeAll();
        contentPanel.add(panel, BorderLayout.CENTER);
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainFrame frame = new MainFrame();
            frame.setVisible(true);

            // Add window shadow effect (requires JNA library)
            try {
                com.sun.jna.platform.WindowUtils.setWindowTransparent(frame, true);
            } catch (Exception e) {
                System.err.println("Shadow effect not supported on this platform.");
            }
        });
    }
}