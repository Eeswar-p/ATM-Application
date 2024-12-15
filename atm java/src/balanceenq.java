import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class balanceenq extends JPanel {

    private JLabel welcomeLabel, balanceLabel;
    private JButton checkBalanceButton;

    private final String url = "jdbc:mysql://localhost:3306/atm"; // Replace with actual DB name
    private final String user = "root"; // Replace with actual DB user
    private final String password = "1234"; // Replace with actual DB password
    private final String cardId;
    private Image backgroundImage; // Variable to store background image

    public balanceenq(String cardId) {
        this.cardId = cardId;

        // Load the background image (change the path to your actual image file)
        ImageIcon icon = new ImageIcon("D:/IntelliJ IDEA 2024.2.2/atm java/images/bg1.jpg"); // Replace with actual path
        backgroundImage = icon.getImage();

        // Panel layout
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(new Color(245, 245, 245));
        setBorder(new EmptyBorder(30, 30, 30, 30));

        // Welcome label
        welcomeLabel = new JLabel("Welcome to the ATM");
        welcomeLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        welcomeLabel.setForeground(new Color(33, 150, 243));  // Modern blue color
        welcomeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(welcomeLabel);

        // Spacing
        add(Box.createRigidArea(new Dimension(0, 20)));

        // Button Panel
        checkBalanceButton = new JButton("Check Balance");
        checkBalanceButton.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        checkBalanceButton.setBackground(new Color(33, 150, 243));
        checkBalanceButton.setForeground(Color.WHITE);
        checkBalanceButton.setFocusPainted(false);
        checkBalanceButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        checkBalanceButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        add(checkBalanceButton);

        // Spacing
        add(Box.createRigidArea(new Dimension(0, 20)));

        // Balance Label Panel
        JPanel balancePanel = new JPanel();
        balancePanel.setBackground(Color.WHITE);
        balancePanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
                new EmptyBorder(15, 15, 15, 15)
        ));
        balancePanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        balanceLabel = new JLabel("Balance will be displayed here", SwingConstants.CENTER);
        balanceLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        balanceLabel.setForeground(new Color(100, 100, 100));
        balancePanel.add(balanceLabel);

        add(balancePanel);

        // Button Action
        checkBalanceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                double balance = getBalance(cardId);
                if (balance >= 0) {
                    balanceLabel.setText("Your Current Balance: $" + balance);
                } else {
                    balanceLabel.setText("Invalid Card ID or error fetching balance.");
                }
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Draw the background image
        if (backgroundImage != null) {
            // Scale the image to fit the panel
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }

    private double getBalance(String cardId) {
        double balance = -1;
        String query = "SELECT Balance FROM user WHERE card_id = ?";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, cardId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    balance = rs.getDouble("Balance");
                }
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error fetching balance. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
        return balance;
    }
}
