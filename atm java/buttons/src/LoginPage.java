import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.Kernel;
import java.awt.image.ConvolveOp;

public class LoginPage extends JFrame {

    public LoginPage() {
        setTitle("Login Page");
        setSize(600, 400);  // Increased the size of the window
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Background Panel (No blur on background)
        BackgroundPanel backgroundPanel = new BackgroundPanel(new ImageIcon("C:/Users/eshwa/OneDrive/Pictures/Saved Pictures/wea.jpg").getImage());
        backgroundPanel.setLayout(new GridBagLayout());

        // Panel for adding components (text fields, buttons, etc.)
        JPanel loginPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);  // Properly call the superclass to paint the component

                // Apply blur effect to this panel using a convolution filter
                BufferedImage bufferedImage = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
                Graphics2D g2d = bufferedImage.createGraphics();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                super.paintComponents(g2d);  // Draw all components of the loginPanel
                g2d.dispose();

                // Apply a stronger Gaussian blur to the loginPanel
                Kernel kernel = new Kernel(7, 7, new float[]{
                        1f / 49, 1f / 49, 1f / 49, 1f / 49, 1f / 49, 1f / 49, 1f / 49,
                        1f / 49, 1f / 49, 1f / 49, 1f / 49, 1f / 49, 1f / 49, 1f / 49,
                        1f / 49, 1f / 49, 1f / 49, 1f / 49, 1f / 49, 1f / 49, 1f / 49,
                        1f / 49, 1f / 49, 1f / 49, 1f / 49, 1f / 49, 1f / 49, 1f / 49,
                        1f / 49, 1f / 49, 1f / 49, 1f / 49, 1f / 49, 1f / 49, 1f / 49,
                        1f / 49, 1f / 49, 1f / 49, 1f / 49, 1f / 49, 1f / 49, 1f / 49,
                        1f / 49, 1f / 49, 1f / 49, 1f / 49, 1f / 49, 1f / 49, 1f / 49
                });
                BufferedImageOp op = new ConvolveOp(kernel);
                BufferedImage blurredImage = op.filter(bufferedImage, null);

                // Draw the blurred image
                g.drawImage(blurredImage, 0, 0, getWidth(), getHeight(), this);
            }
        };

        // Set the loginPanel to be transparent (with blur applied)
        loginPanel.setOpaque(false); // Make the panel transparent
        loginPanel.setPreferredSize(new Dimension(400, 300));  // Increase the size of the loginPanel
        loginPanel.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        JLabel welcomeLabel = new JLabel("Welcome");
        welcomeLabel.setForeground(Color.WHITE);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 20));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        loginPanel.add(welcomeLabel, gbc);

        JLabel emailLabel = new JLabel("Enter your email");
        emailLabel.setForeground(Color.WHITE);
        gbc.gridy = 1;
        loginPanel.add(emailLabel, gbc);

        JTextField emailField = new JTextField(20);
        gbc.gridy = 2;
        loginPanel.add(emailField, gbc);

        JLabel passwordLabel = new JLabel("Enter your password");
        passwordLabel.setForeground(Color.WHITE);
        gbc.gridy = 3;
        loginPanel.add(passwordLabel, gbc);

        JPasswordField passwordField = new JPasswordField(20);
        gbc.gridy = 4;
        loginPanel.add(passwordField, gbc);

        JCheckBox rememberMeCheckBox = new JCheckBox("Remember me");
        rememberMeCheckBox.setForeground(Color.WHITE);
        rememberMeCheckBox.setOpaque(false);
        gbc.gridy = 5;
        loginPanel.add(rememberMeCheckBox, gbc);

        JButton loginButton = new JButton("Log In");
        gbc.gridy = 6;
        loginPanel.add(loginButton, gbc);

        JButton forgotPasswordButton = new JButton("Forgot password?");
        forgotPasswordButton.setForeground(Color.LIGHT_GRAY);
        forgotPasswordButton.setBorderPainted(false);
        forgotPasswordButton.setContentAreaFilled(false);
        gbc.gridx = 1;
        loginPanel.add(forgotPasswordButton, gbc);

        JLabel registerLabel = new JLabel("Don't have an account? Register");
        registerLabel.setForeground(Color.LIGHT_GRAY);
        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.gridwidth = 2;
        loginPanel.add(registerLabel, gbc);

        // Adding loginPanel to backgroundPanel
        backgroundPanel.add(loginPanel);

        // Adding backgroundPanel to the JFrame
        add(backgroundPanel);
    }

    // Custom JPanel class for Background Image (No blur effect here)
    class BackgroundPanel extends JPanel {
        private Image backgroundImage;

        public BackgroundPanel(Image image) {
            this.backgroundImage = image;
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);  // Draw background image without blur
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            LoginPage loginPage = new LoginPage();
            loginPage.setVisible(true);
        });
    }
}
