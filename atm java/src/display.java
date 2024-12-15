import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class display extends JFrame {
    public String cardno = login.getCardno();
    public String pinno = login.getPinno();

    public display() {
        JFrame f = new JFrame("screen");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setLayout(null);

        // Create a CardLayout
        CardLayout cardLayout = new CardLayout();
        JPanel cardPanel = new JPanel(cardLayout);

        JPanel p = new JPanel();
        JPanel p1 = new JPanel();

        // Set background color of panels to white
        cardPanel.setBackground(Color.WHITE);

        // Load the background image
        ImageIcon backgroundImage = new ImageIcon("D:/IntelliJ IDEA 2024.2.2/atm java/images/ds.jpeg");
        Image image = backgroundImage.getImage();

        // Background panel with custom painting
        JPanel p2 = new JPanel() {
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
            }
        };
        p2.setBackground(Color.WHITE); // Set background color to white

        // Add the button panels to the card layout
        cardPanel.add(p2, "background");

        // Custom button class
        class ThreeDButton extends JButton {
            public ThreeDButton(String label) {
                super(label);
                setContentAreaFilled(false);
            }

            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(new Color(240, 240, 240));
                g2d.fill3DRect(0, 0, getSize().width - 1, getSize().height - 1, true);
                super.paintComponent(g);
            }

            protected void paintBorder(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setColor(new Color(200, 200, 200));
                g2d.setStroke(new BasicStroke(2));
                g2d.draw3DRect(0, 0, getSize().width - 1, getSize().height - 1, true);
            }
        }

        // Create buttons for different ATM functions
        ThreeDButton b = new ThreeDButton("deposit");
        ThreeDButton b1 = new ThreeDButton("withdrawal");
        ThreeDButton b2 = new ThreeDButton("change pin");
        ThreeDButton b3 = new ThreeDButton("balance enquiry");
        ThreeDButton b4 = new ThreeDButton("mini statement");
        ThreeDButton b5 = new ThreeDButton("sign out");

        // Add buttons to panels
        p.add(b);
        p.add(b1);
        p.add(b2);
        p1.add(b3);
        p1.add(b4);
        p1.add(b5);

        // Set positions of buttons in the panels
        b.setBounds(50, 100, 130, 40);
        b1.setBounds(50, 200, 130, 40);
        b2.setBounds(50, 300, 130, 40);
        b3.setBounds(50, 100, 130, 40);
        b4.setBounds(50, 200, 130, 40);
        b5.setBounds(50, 300, 130, 40);

        // Set preferred sizes for panels
        p.setPreferredSize(new Dimension(200, 300));
        p1.setPreferredSize(new Dimension(200, 300));

        // Set preferred size of cardPanel to a box shape
        cardPanel.setPreferredSize(new Dimension(300, 300));

        // Create a wrapper panel with BoxLayout to center cardPanel
        JPanel wrapperPanel = new JPanel();
        wrapperPanel.setLayout(new BoxLayout(wrapperPanel, BoxLayout.Y_AXIS));
        wrapperPanel.add(Box.createVerticalGlue()); // Top padding
        wrapperPanel.add(cardPanel);                // Add the cardPanel itself
        wrapperPanel.add(Box.createVerticalGlue()); // Bottom padding

        // Add main panels to frame with BorderLayout
        f.setLayout(new BorderLayout());
        f.add(p, BorderLayout.EAST);
        f.add(wrapperPanel, BorderLayout.CENTER);
        f.add(p1, BorderLayout.WEST);

        // Set layouts of p, p1, p2 panels
        p.setLayout(null);
        p1.setLayout(null);
        p2.setLayout(null);

        // Set frame size and visibility
        f.setSize(800, 500);
        f.setVisible(true);

        // Action listeners for switching panels in card layout
        b.addActionListener(e -> cardLayout.show(cardPanel, "deposit"));
        b3.addActionListener(e -> cardLayout.show(cardPanel, "balance enquiry"));
        b1.addActionListener(e -> cardLayout.show(cardPanel, "withdrawal"));
        b2.addActionListener(e -> cardLayout.show(cardPanel, "change pin"));
        b4.addActionListener(e -> cardLayout.show(cardPanel, "mini statement"));
        b5.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
              new login();
                f.dispose();
            }
        });

                // Add panels for each ATM function to the cardPanel
                cardPanel.add(new depo(cardno, pinno), "deposit");
        cardPanel.add(new Withdra(cardno, pinno), "withdrawal");
        cardPanel.add(new balanceenq(cardno), "balance enquiry");
        cardPanel.add(new changep(cardno), "change pin");
        cardPanel.add(new ministate(cardno), "mini statement");

    }
}
