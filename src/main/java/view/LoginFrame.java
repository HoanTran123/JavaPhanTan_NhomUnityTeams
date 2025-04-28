package view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;

public class LoginFrame extends JFrame {
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JPanel cardPanel;

    public LoginFrame() {
        setTitle("Đăng Nhập - Hệ Thống Quản Lý Nhà Thuốc");
        setSize(500, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // Set modern Look and Feel
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
            UIManager.put("control", new Color(255, 255, 255));
            UIManager.put("nimbusFocus", new Color(3, 155, 229));
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Main panel with gradient background
        JPanel mainPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                GradientPaint gp = new GradientPaint(0, 0, new Color(3, 155, 229), 0, getHeight(), new Color(0, 102, 204));
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBorder(new EmptyBorder(30, 30, 30, 30));

        // Card panel for login form
        cardPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(Color.WHITE);
                g2d.fill(new RoundRectangle2D.Double(0, 0, getWidth()-1, getHeight()-1, 20, 20));
            }
        };
        cardPanel.setOpaque(false);
        cardPanel.setLayout(new BorderLayout());
        cardPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        cardPanel.setPreferredSize(new Dimension(400, 500));

        // Header with logo
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setOpaque(false);
        JLabel lblLogo = new JLabel("🧑‍⚕️", SwingConstants.CENTER);
        lblLogo.setFont(new Font("Segoe UI", Font.PLAIN, 48));
        JLabel lblHeader = new JLabel("HỆ THỐNG QUẢN LÝ NHÀ THUỐC", SwingConstants.CENTER);
        lblHeader.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblHeader.setForeground(new Color(33, 37, 41));
        headerPanel.add(lblLogo, BorderLayout.NORTH);
        headerPanel.add(lblHeader, BorderLayout.CENTER);

        // Form panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 10, 15, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Username
        gbc.gridx = 0;
        gbc.gridy = 0;
        JLabel lblUsername = new JLabel("Tên đăng nhập:");
        lblUsername.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        formPanel.add(lblUsername, gbc);

        gbc.gridy = 1;
        txtUsername = createStyledTextField("Nhập tên đăng nhập...");
        formPanel.add(txtUsername, gbc);

        // Password
        gbc.gridy = 2;
        JLabel lblPassword = new JLabel("Mật khẩu:");
        lblPassword.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        formPanel.add(lblPassword, gbc);

        gbc.gridy = 3;
        txtPassword = createStyledPasswordField("Nhập mật khẩu...");
        formPanel.add(txtPassword, gbc);

        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 0));
        buttonPanel.setOpaque(false);
        JButton btnLogin = createStyledButton("Đăng Nhập", new Color(3, 155, 229));
        JButton btnExit = createStyledButton("Thoát", new Color(220, 53, 69));

        btnLogin.addActionListener(e -> login());
        btnExit.addActionListener(e -> System.exit(0));

        buttonPanel.add(btnLogin);
        buttonPanel.add(btnExit);

        gbc.gridy = 4;
        gbc.gridwidth = 2;
        formPanel.add(buttonPanel, gbc);

        // Footer
        JLabel lblFooter = new JLabel("UnityTeams © 2025", SwingConstants.CENTER);
        lblFooter.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblFooter.setForeground(new Color(108, 117, 125));

        cardPanel.add(headerPanel, BorderLayout.NORTH);
        cardPanel.add(formPanel, BorderLayout.CENTER);
        cardPanel.add(lblFooter, BorderLayout.SOUTH);

        mainPanel.add(cardPanel, BorderLayout.CENTER);
        add(mainPanel);
    }

    private JTextField createStyledTextField(String placeholder) {
        JTextField textField = new JTextField(20);
        textField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        textField.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));
        textField.setBackground(new Color(248, 249, 250));
        textField.setForeground(new Color(33, 37, 41));
        textField.setText(placeholder);
        textField.setForeground(Color.GRAY);

        textField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (textField.getText().equals(placeholder)) {
                    textField.setText("");
                    textField.setForeground(new Color(33, 37, 41));
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (textField.getText().isEmpty()) {
                    textField.setText(placeholder);
                    textField.setForeground(Color.GRAY);
                }
            }
        });

        return textField;
    }

    private JPasswordField createStyledPasswordField(String placeholder) {
        JPasswordField passwordField = new JPasswordField(20);
        passwordField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        passwordField.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));
        passwordField.setBackground(new Color(248, 249, 250));
        passwordField.setForeground(new Color(33, 37, 41));
        passwordField.setEchoChar((char) 0);
        passwordField.setText(placeholder);
        passwordField.setForeground(Color.GRAY);

        passwordField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (new String(passwordField.getPassword()).equals(placeholder)) {
                    passwordField.setText("");
                    passwordField.setEchoChar('•');
                    passwordField.setForeground(new Color(33, 37, 41));
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (new String(passwordField.getPassword()).isEmpty()) {
                    passwordField.setText(placeholder);
                    passwordField.setEchoChar((char) 0);
                    passwordField.setForeground(Color.GRAY);
                }
            }
        });

        return passwordField;
    }

    private JButton createStyledButton(String text, Color color) {
        JButton button = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(getBackground());
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
                super.paintComponent(g);
            }
        };
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setBorder(BorderFactory.createEmptyBorder(12, 25, 12, 25));
        button.setFocusPainted(false);
        button.setContentAreaFilled(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(color.brighter());
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(color);
            }

            @Override
            public void mousePressed(MouseEvent e) {
                button.setBackground(color.darker());
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                button.setBackground(color);
            }
        });

        return button;
    }

    private void login() {
        String username = txtUsername.getText().trim();
        String password = new String(txtPassword.getPassword()).trim();

        if (username.equals("Nhập tên đăng nhập...") || password.equals("Nhập mật khẩu...")) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (username.equals("admin") && password.equals("admin123")) {
            JOptionPane.showMessageDialog(this, "Đăng nhập thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
            SwingUtilities.invokeLater(() -> {
                dispose();
                MainFrame mainFrame = new MainFrame();
                mainFrame.setVisible(true);
            });
        } else {
            JOptionPane.showMessageDialog(this, "Tên đăng nhập hoặc mật khẩu không đúng!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            txtPassword.setText("");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            LoginFrame frame = new LoginFrame();
            frame.setVisible(true);
        });
    }
}