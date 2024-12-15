import javax.swing.*;
import java.awt.*;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class login {
    static String cardno;
    static String pinno;

    public login() {
        // Create JFrame for login window
        JFrame f = new JFrame("Login");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setLayout(null);
        f.setLocationRelativeTo(null);

        // Create UI components
        JLabel l = new JLabel("CARD ID :");
        JTextField t = new JTextField();
        JLabel l2 = new JLabel("PIN :");
        JPasswordField p = new JPasswordField();

        // Create Circular Button
        class CircularButton extends JButton {
            public CircularButton(String label) {
                super(label);
                setContentAreaFilled(false);
            }

            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(getBackground());
                g2d.fillOval(0, 0, getSize().width - 1, getSize().height - 1);
                super.paintComponent(g);
            }

            @Override
            protected void paintBorder(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setColor(getForeground());
                g2d.drawOval(0, 0, getSize().width - 1, getSize().height - 1);
            }
        }

        CircularButton b = new CircularButton("Login");
        CircularButton b1 = new CircularButton("Register");

        // Set background image
        ImageIcon backgroundImage = new ImageIcon("/D://IntelliJ IDEA 2024.2.2//atm java//images//atmimage.jpg");
        Image image = backgroundImage.getImage();
        JPanel p1 = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
            }
        };

        // Add components to the panel
        p1.add(l);
        p1.add(t);
        p1.add(l2);
        p1.add(p);
        p1.add(b);
        p1.add(b1);

        // Set component properties
        l.setForeground(Color.WHITE);
        l2.setForeground(Color.WHITE);
        l.setBounds(100, 60, 150, 30);
        t.setBounds(170, 60, 150, 30);
        l2.setBounds(100, 120, 150, 30);
        p.setBounds(170, 120, 150, 30);
        b.setBounds(166, 190, 80, 30);
        b1.setBounds(296, 190, 100, 30);
        p1.setBounds(0, 0, 500, 500);

        // Set layout and add the panel to the frame
        p1.setLayout(null);
        f.add(p1);
        f.setSize(500, 500);
        f.setVisible(true);

        // Add ActionListener to the login button
        b.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String cardId = t.getText();
                String pin = String.valueOf(p.getPassword());

                // Validate login credentials
                if (validateLogin(cardId, pin)) {
                    JOptionPane.showMessageDialog(f, "Login Successful!");
                    f.dispose();  // Close the login window

                    // Open the display window (assuming display class exists)
                    new display();
                } else {
                    JOptionPane.showMessageDialog(f, "Invalid Card ID or PIN.");
                }
            }
        });
        b1.addActionListener(new ActionListener() {
            public void actionPerformed (ActionEvent e){
                f.dispose();
                new RegistrationPage();
            }
        });
    }


    // Method to validate login using MySQL
    public static boolean validateLogin(String cardId, String pin) {
        boolean status = false;
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            // Load MySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Connect to the MySQL database
            String url = "jdbc:mysql://localhost:3306/atm";
            String user = "root"; // your MySQL username
            String password = "1234"; // your MySQL password
            conn = DriverManager.getConnection(url, user, password);

            // SQL query to validate user
            String query = "SELECT * FROM user WHERE card_id = ? AND pin = ?";
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, cardId);
            pstmt.setString(2, pin);

            rs = pstmt.executeQuery();

            if (rs.next()) {
                cardno = rs.getString("card_id");
                pinno = rs.getString("pin");
                status = true; // Login successful
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }

        return status;
    }

    // Getter methods for cardno and pinno
    static String getCardno() {
        return cardno;
    }

    static String getPinno() {
        return pinno;
    }
    public static void main(String [] args){
        new login();
    }
}
