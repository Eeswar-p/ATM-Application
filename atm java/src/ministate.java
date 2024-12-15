import javax.swing.*;
import java.sql.*;
import java.awt.*;
import java.sql.Timestamp;

public class ministate extends JPanel {

    private final String url = "jdbc:mysql://localhost:3306/atm";
    private final String user = "root";
    private final String password = "1234";

    public ministate(String cardId) {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        // Heading label
        JLabel headingLabel = new JLabel("Mini Statement for Card ID: XXXXXXXX" , JLabel.CENTER);
        headingLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        headingLabel.setForeground(new Color(50, 50, 50));
        headingLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(headingLabel, BorderLayout.NORTH);

        // Panel to hold each transaction record
        JPanel transactionPanel = new JPanel();
        transactionPanel.setLayout(new BoxLayout(transactionPanel, BoxLayout.Y_AXIS));
        transactionPanel.setBackground(Color.WHITE);

        // Scroll pane for transactions
        JScrollPane scrollPane = new JScrollPane(transactionPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setPreferredSize(new Dimension(380, 520));
        scrollPane.getViewport().setBackground(Color.WHITE);
        add(scrollPane, BorderLayout.CENTER);

        // Fetch and display the latest 10 transactions for the given card ID, ordered by date
        fetchLatest10Transactions(transactionPanel, cardId);
        setVisible(true);
    }

    private void fetchLatest10Transactions(JPanel transactionPanel, String cardId) {
        String query = "SELECT * FROM transaction WHERE card_id = ? ORDER BY time DESC LIMIT 10";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, cardId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                JPanel singleTransaction = new JPanel();
                singleTransaction.setLayout(new GridLayout(5, 1, 5, 5)); // Add padding between labels
                singleTransaction.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createEmptyBorder(10, 10, 10, 10),
                        BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1)
                ));
                singleTransaction.setBackground(new Color(245, 245, 245));

                int transactionId = rs.getInt("transaction_id");
                String type = rs.getString("transaction_type");
                double amount = rs.getDouble("amount");
                Timestamp time = rs.getTimestamp("time");

                singleTransaction.add(createStyledLabel("Transaction ID: " + transactionId));
//                singleTransaction.add(createStyledLabel("Card ID: " + cardId));
                singleTransaction.add(createStyledLabel("Transaction Type: " + (type != null ? type : "N/A")));
                singleTransaction.add(createStyledLabel("Amount: $" + amount));
                singleTransaction.add(createStyledLabel("Time: " + time));

                transactionPanel.add(Box.createVerticalStrut(10)); // Space between transactions
                transactionPanel.add(singleTransaction);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error fetching data from database: " + e.getMessage());
        }
    }

    // Method to create styled labels
    private JLabel createStyledLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("SansSerif", Font.PLAIN, 14));
        label.setForeground(new Color(60, 60, 60));
        return label;
    }

}
