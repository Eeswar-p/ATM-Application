import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.Random;

class depo extends JPanel {
    private JTextField amountField;
    private Connection connection;
    private String cardId;
    private String pin;
    private Image backgroundImage;

    public depo(String cardId, String pin) {
        this.cardId = cardId;
        this.pin = pin;

        // Set layout to null to allow custom component positioning with setBounds
        setLayout(null);

        // Load the background image
        backgroundImage = new ImageIcon("D:/IntelliJ IDEA 2024.2.2/atm java/images/bg2.jpg").getImage();

        // Deposit amount label
        JLabel amountLabel = new JLabel("Deposit Amount:");
        amountLabel.setBounds(30, 80, 120, 25);
        amountLabel.setForeground(Color.WHITE);
        add(amountLabel);

        // Deposit amount text field
        amountField = new JTextField(10);
        amountField.setBounds(130, 80, 180, 25);
        add(amountField);

        // Deposit button
        JButton depositButton = new JButton("DEPOSIT");
        depositButton.setBounds(140, 150, 100, 25);
        add(depositButton);

        // Action listener for the deposit button
        depositButton.addActionListener(e -> performDeposit(cardId, pin));

        // Connect to the database
        connectToDatabase();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
    }

    private void connectToDatabase() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/atm", "root", "1234");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error connecting to the database: " + e.getMessage());
        }
    }

    private void performDeposit(String cardId, String pin) {
        double depositAmount;

        // Check for valid input in amount field
        try {
            depositAmount = Double.parseDouble(amountField.getText());
            if (depositAmount <= 0) {
                JOptionPane.showMessageDialog(this, "Please enter a positive amount.");
                return;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter a valid amount.");
            return;
        }

        try {
            // Check if cardId and PIN are valid
            String query = "SELECT Balance FROM user WHERE card_id = ? AND pin = ?";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, cardId);
            stmt.setString(2, pin);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                double currentBalance = rs.getDouble("Balance");

                // Generate a random transaction ID
                int transId = Math.abs(new Random().nextInt());

                // Insert transaction record into the database
                String type = "DEPOSIT";
                String sql = "INSERT INTO transaction (transaction_id, card_id, transaction_type, amount) VALUES (?, ?, ?, ?)";
                PreparedStatement ps = connection.prepareStatement(sql);
                ps.setInt(1, transId);
                ps.setString(2, cardId);
                ps.setString(3, type);
                ps.setDouble(4, depositAmount);
                ps.executeUpdate();

                // Update the balance
                double newBalance = currentBalance + depositAmount;
                String updateQuery = "UPDATE user SET Balance = ? WHERE card_id = ?";
                PreparedStatement updateStmt = connection.prepareStatement(updateQuery);
                updateStmt.setDouble(1, newBalance);
                updateStmt.setString(2, cardId);
                updateStmt.executeUpdate();

                JOptionPane.showMessageDialog(this, "Deposit successful! Amount: " + depositAmount);
            } else {
                JOptionPane.showMessageDialog(this, "Invalid card ID or PIN.");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error during deposit: " + e.getMessage());
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
