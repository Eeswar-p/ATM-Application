import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class changep extends JPanel {
    private JPasswordField oldPinField;
    private JPasswordField newPinField;
    private JPasswordField confirmNewPinField;
    private JButton changePinButton;
    private String cardId;
    private Image backgroundImage;

    public changep(String cardId) {
        this.cardId = cardId;
        setLayout(null);

        // Load background image
        backgroundImage = new ImageIcon("D:/IntelliJ IDEA 2024.2.2/atm java/images/bg5.jpg").getImage();

        // Setup Labels and Fields
        setupComponents();

        // Change PIN button action listener
        changePinButton.addActionListener(e -> changePin(cardId));
    }

    private void setupComponents() {
        JLabel oldPinLabel = new JLabel("Old PIN:");
        oldPinLabel.setBounds(20, 80, 100, 25);
        oldPinLabel.setForeground(Color.WHITE);  // Set label color to white
        add(oldPinLabel);

        oldPinField = new JPasswordField();
        oldPinField.setBounds(130, 80, 160, 25);
        add(oldPinField);

        JLabel newPinLabel = new JLabel("New PIN:");
        newPinLabel.setBounds(20, 130, 100, 25);
        newPinLabel.setForeground(Color.WHITE);  // Set label color to white
        add(newPinLabel);

        newPinField = new JPasswordField();
        newPinField.setBounds(130, 130, 160, 25);
        add(newPinField);

        JLabel confirmNewPinLabel = new JLabel("Confirm New PIN:");
        confirmNewPinLabel.setBounds(20, 180, 120, 25);
        confirmNewPinLabel.setForeground(Color.WHITE);  // Set label color to white
        add(confirmNewPinLabel);

        confirmNewPinField = new JPasswordField();
        confirmNewPinField.setBounds(130, 180, 160, 25);
        add(confirmNewPinField);

        changePinButton = new JButton("Change PIN");
        changePinButton.setBounds(130, 230, 160, 30);
        add(changePinButton);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
    }

    private void changePin(String cardId) {
        String oldPin = new String(oldPinField.getPassword());
        String newPin = new String(newPinField.getPassword());
        String confirmNewPin = new String(confirmNewPinField.getPassword());

        if (!validateInput(oldPin, newPin, confirmNewPin)) {
            return;
        }

        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/atm", "root", "1234")) {
            if (!isOldPinCorrect(connection, cardId, oldPin)) {
                JOptionPane.showMessageDialog(this, "Incorrect old PIN.");
                return;
            }

            updatePin(connection, cardId, newPin);
            JOptionPane.showMessageDialog(this, "PIN changed successfully.");
            clearFields();

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database error: " + e.getMessage());
        }
    }

    private boolean validateInput(String oldPin, String newPin, String confirmNewPin) {
        if (oldPin.isEmpty() || newPin.isEmpty() || confirmNewPin.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields.");
            return false;
        }
        if (!newPin.equals(confirmNewPin)) {
            JOptionPane.showMessageDialog(this, "New PINs do not match.");
            return false;
        }
        if (newPin.length() < 4 || !newPin.matches("\\d+")) {
            JOptionPane.showMessageDialog(this, "PIN must be at least 4 digits.");
            return false;
        }
        return true;
    }

    private boolean isOldPinCorrect(Connection connection, String cardId, String oldPin) throws SQLException {
        String selectQuery = "SELECT pin FROM user WHERE card_id = ?";
        try (PreparedStatement selectStatement = connection.prepareStatement(selectQuery)) {
            selectStatement.setString(1, cardId);
            ResultSet rs = selectStatement.executeQuery();
            return rs.next() && rs.getString("pin").equals(oldPin);
        }
    }

    private void updatePin(Connection connection, String cardId, String newPin) throws SQLException {
        String updateQuery = "UPDATE user SET pin = ? WHERE card_id = ?";
        try (PreparedStatement updateStatement = connection.prepareStatement(updateQuery)) {
            updateStatement.setString(1, newPin);
            updateStatement.setString(2, cardId);
            updateStatement.executeUpdate();
        }
    }

    private void clearFields() {
        oldPinField.setText("");
        newPinField.setText("");
        confirmNewPinField.setText("");
    }
}
