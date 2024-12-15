import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class RegistrationPage extends JFrame {

    // Declare components
    private JLabel labelCardId, labelPin, labelUserName, labelMobile, labelBalance, labelBankName;
    private JTextField textCardId, textUserName, textMobile, textBalance, textBankName;
    private JPasswordField textPin;
    private JButton btnSubmit, btnExit;

    // Constructor to set up the UI
    public RegistrationPage() {
        // Initialize components
        labelCardId = new JLabel("Card ID:");
        labelPin = new JLabel("PIN:");
        labelUserName = new JLabel("User Name:");
        labelMobile = new JLabel("Mobile Number:");
        labelBalance = new JLabel("Balance:");
        labelBankName = new JLabel("Bank Name:");

        btnExit = new JButton("EXIT");
        btnExit.setBounds(230, 270, 100, 30);

        textCardId = new JTextField(20);
        textPin = new JPasswordField(20);
        textUserName = new JTextField(20);
        textMobile = new JTextField(20);
        textBalance = new JTextField(20);
        textBankName = new JTextField(20);

        btnSubmit = new JButton("Register");

        // Set layout to null for absolute positioning
        setLayout(null);

        // Set bounds for each component (x, y, width, height)
        labelCardId.setBounds(30, 30, 100, 25);
        textCardId.setBounds(150, 30, 200, 25);

        labelPin.setBounds(30, 70, 100, 25);
        textPin.setBounds(150, 70, 200, 25);

        labelUserName.setBounds(30, 110, 100, 25);
        textUserName.setBounds(150, 110, 200, 25);

        labelMobile.setBounds(30, 150, 100, 25);
        textMobile.setBounds(150, 150, 200, 25);

        labelBalance.setBounds(30, 190, 100, 25);
        textBalance.setBounds(150, 190, 200, 25);

        labelBankName.setBounds(30, 230, 100, 25);
        textBankName.setBounds(150, 230, 200, 25);

        btnSubmit.setBounds(150, 270, 100, 30);

        // Add components to the frame
        add(labelCardId);
        add(textCardId);
        add(labelPin);
        add(textPin);
        add(labelUserName);
        add(textUserName);
        add(labelMobile);
        add(textMobile);
        add(labelBalance);
        add(textBalance);
        add(labelBankName);
        add(textBankName);
        add(btnSubmit);
        add(btnExit);

        // Add action listener for button
        btnSubmit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                registerUser();
            }
        });

        // Add action listener for exit button
        btnExit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();  // Close the RegistrationPage window
                new login(); // Launch login page
            }
        });

        // Set frame properties
        setTitle("User Registration");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    // Method to register the user
    private void registerUser() {
        String cardId = textCardId.getText();
        String pin = new String(textPin.getPassword());
        String userName = textUserName.getText();
        String mobile = textMobile.getText();
        String balance = textBalance.getText();
        String bankName = textBankName.getText();

        // Basic validation for inputs
        if (cardId.isEmpty() || pin.isEmpty() || userName.isEmpty() || mobile.isEmpty() || balance.isEmpty() || bankName.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields.");
            return;
        }

        // Check if balance is numeric
        try {
            new java.math.BigDecimal(balance);  // Check if balance can be converted to BigDecimal
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Balance must be a valid number.");
            return;
        }

        // Hash the PIN before storing it for security
        String hashedPin = hashPin(pin);

        // Database connection parameters
        String url = "jdbc:mysql://localhost:3306/atm";
        String user = "root";
        String password = "1234";
        String query = "INSERT INTO user (card_id, pin, user_name, mob_no, balance, bank_name) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement stmt = conn.prepareStatement(query)) {

            // Set parameters for the query
            stmt.setString(1, cardId);
            stmt.setString(2, hashedPin);
            stmt.setString(3, userName);
            stmt.setString(4, mobile);
            stmt.setBigDecimal(5, new java.math.BigDecimal(balance));
            stmt.setString(6, bankName);

            // Execute the query
            int rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) {
                JOptionPane.showMessageDialog(this, "User registered successfully!");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error registering user: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    // Method to hash the PIN for security
    private String hashPin(String pin) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = md.digest(pin.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : hashBytes) {
                hexString.append(String.format("%02x", b));
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            JOptionPane.showMessageDialog(this, "Error hashing PIN: " + e.getMessage());
            return null;
        }
    }

    public static void main(String[] args) {
        new RegistrationPage();
    }
}
