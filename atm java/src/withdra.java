import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.util.Random;

class Withdra extends JPanel {
    private JTextField amountField;
    private Connection connection;
    private String cardId;
    private String pin;
    private Image backgroundImage;

    public Withdra(String cardId, String pin) {
        this.cardId = cardId;
        this.pin = pin;

        // Set layout to null for custom positioning
        setLayout(null);

        // Load the background image
        backgroundImage = new ImageIcon("D:/IntelliJ IDEA 2024.2.2/atm java/images/bg6.jpg").getImage();

        // Withdraw amount label
        JLabel amountLabel = new JLabel("Withdraw Amount:");
        amountLabel.setBounds(30, 80, 120, 25);
        amountLabel.setForeground(Color.WHITE);
        add(amountLabel);

        // Withdraw amount text field
        amountField = new JTextField(10);
        amountField.setBounds(138, 80, 180, 25);
        add(amountField);

        // Withdraw button
        JButton withdrawButton = new JButton("Withdraw");
        withdrawButton.setBounds(140, 150, 100, 25);
        add(withdrawButton);

        // Action listener for withdrawal button
        withdrawButton.addActionListener(e -> performWithdrawal(cardId, pin));

        // Connect to the database
        connectToDatabase();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this); // Draw the background image
    }

    private void connectToDatabase() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/atm", "root", "1234");
            connection.setAutoCommit(false); // Enable manual transaction handling
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error connecting to the database: " + e.getMessage());
        }
    }

    private void performWithdrawal(String cardId, String pin) {
        double withdrawAmount;

        try {
            withdrawAmount = Double.parseDouble(amountField.getText());
            if (withdrawAmount <= 0) {
                JOptionPane.showMessageDialog(this, "Please enter a positive amount.");
                return;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter a valid amount.");
            return;
        }

        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            // Check if cardId and PIN are valid
            String query = "SELECT Balance FROM user WHERE card_id = ? AND pin = ?";
            stmt = connection.prepareStatement(query);
            stmt.setString(1, cardId);
            stmt.setString(2, pin);
            rs = stmt.executeQuery();

            if (rs.next()) {
                double currentBalance = rs.getDouble("Balance");

                if (withdrawAmount > currentBalance) {
                    JOptionPane.showMessageDialog(this, "Insufficient funds!");
                } else {
                    // Generate a random transaction ID
                    Random random = new Random();
                    int transId = Math.abs(random.nextInt());

                    // Insert transaction record
                    String sql = "INSERT INTO transaction (transaction_id, card_id, transaction_type, amount) VALUES (?, ?, ?, ?)";
                    try (PreparedStatement ps = connection.prepareStatement(sql)) {
                        ps.setInt(1, transId);
                        ps.setString(2, cardId);
                        ps.setString(3, "WITHDRAW");
                        ps.setDouble(4, withdrawAmount);
                        ps.executeUpdate();
                    }

                    // Update the balance
                    double newBalance = currentBalance - withdrawAmount;
                    String updateQuery = "UPDATE user SET Balance = ? WHERE card_id = ?";
                    try (PreparedStatement updateStmt = connection.prepareStatement(updateQuery)) {
                        updateStmt.setDouble(1, newBalance);
                        updateStmt.setString(2, cardId);
                        updateStmt.executeUpdate();
                    }

                    // Commit the transaction
                    connection.commit();
                    JOptionPane.showMessageDialog(this, "Withdrawal successful! Amount: " + withdrawAmount + "\nNew Balance: " + newBalance);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Invalid card ID or PIN.");
            }
        } catch (SQLException e) {
            try {
                connection.rollback(); // Roll back on failure
            } catch (SQLException rollbackEx) {
                JOptionPane.showMessageDialog(this, "Error rolling back transaction: " + rollbackEx.getMessage());
            }
            JOptionPane.showMessageDialog(this, "Error during withdrawal: " + e.getMessage());
        } finally {
            // Close resources
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
            } catch (SQLException closeEx) {
                JOptionPane.showMessageDialog(this, "Error closing resources: " + closeEx.getMessage());
            }
        }
    }

    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error closing the database connection: " + e.getMessage());
        }
    }
}
