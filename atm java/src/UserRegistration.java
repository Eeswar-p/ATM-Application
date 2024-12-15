import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class UserRegistration extends JFrame implements ActionListener {
    private JTextField signupNameField, signupEmailField;
    private JPasswordField signupPasswordField, signupConfirmPasswordField;
    private JButton signupButton;
    private JLabel loginLink;

    public UserRegistration() {
        setTitle("User Registration");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        // Use BackgroundPanel for signupPanel with an image path
        JPanel signupPanel = new BackgroundPanel("D:/IntelliJ IDEA 2024.2.2/atm java/images/bg2.jpg"); // Update to the correct path
        signupPanel.setLayout(new GridBagLayout());
        signupPanel.setBorder(new EmptyBorder(30, 30, 30, 30));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);

        gbc.gridx = 0;
        gbc.gridy = 0;
        signupPanel.add(new JLabel("Name:"), gbc);
        signupNameField = new JTextField(20);
        gbc.gridx = 1;
        signupPanel.add(signupNameField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        signupPanel.add(new JLabel("Email (required):"), gbc);
        signupEmailField = new JTextField(20);
        gbc.gridx = 1;
        signupPanel.add(signupEmailField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        signupPanel.add(new JLabel("Password:"), gbc);
        signupPasswordField = new JPasswordField(20);
        gbc.gridx = 1;
        signupPanel.add(signupPasswordField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        signupPanel.add(new JLabel("Confirm Password:"), gbc);
        signupConfirmPasswordField = new JPasswordField(20);
        gbc.gridx = 1;
        signupPanel.add(signupConfirmPasswordField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        signupButton = new JButton("Sign Up");
        signupButton.setBackground(new Color(60, 60, 60));
        signupButton.setForeground(Color.WHITE);
        signupButton.addActionListener(this);
        signupPanel.add(signupButton, gbc);

        gbc.gridy++;
        loginLink = new JLabel("<html><u>Already have an account? Login</u></html>");
        loginLink.setForeground(new Color(60, 60, 60));
        loginLink.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        loginLink.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                openLoginScreen();
            }
        });
        signupPanel.add(loginLink, gbc);

        mainPanel.add(signupPanel, BorderLayout.CENTER);
        add(mainPanel);
        setVisible(true);
    }

    private void openLoginScreen() {
        JFrame loginFrame = new JFrame("Login");
        loginFrame.setSize(400, 250);
        loginFrame.setLocationRelativeTo(this);
        loginFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel loginPanel = new BackgroundPanel("D:/IntelliJ IDEA 2024.2.2/atm java/images/bg3.jpg"); // Update to the correct path
        loginPanel.setLayout(new GridBagLayout());
        loginPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);

        gbc.gridx = 0;
        gbc.gridy = 0;
        loginPanel.add(new JLabel("Email:"), gbc);
        JTextField emailField = new JTextField(20);
        gbc.gridx = 1;
        loginPanel.add(emailField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        loginPanel.add(new JLabel("Password:"), gbc);
        JPasswordField passwordField = new JPasswordField(20);
        gbc.gridx = 1;
        loginPanel.add(passwordField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        JButton loginButton = new JButton("Login");
        loginButton.setBackground(new Color(60, 60, 60));
        loginButton.setForeground(Color.WHITE);
        loginButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(loginFrame, "Login Successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
        });
        loginPanel.add(loginButton, gbc);

        loginFrame.add(loginPanel);
        loginFrame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == signupButton) {
            String name = signupNameField.getText();
            String email = signupEmailField.getText();
            String password = new String(signupPasswordField.getPassword());
            String confirmPassword = new String(signupConfirmPasswordField.getPassword());

            if (name.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill in all fields!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (!password.equals(confirmPassword)) {
                JOptionPane.showMessageDialog(this, "Passwords do not match!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            JOptionPane.showMessageDialog(this, "Signup Successful for " + name, "Success", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public static void main(String[] args) {
        new UserRegistration();
    }

    // Custom JPanel to handle the background image
    static class BackgroundPanel extends JPanel {
        private Image backgroundImage;

        public BackgroundPanel(String imagePath) {
            this.backgroundImage = new ImageIcon(imagePath).getImage();
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }
}
