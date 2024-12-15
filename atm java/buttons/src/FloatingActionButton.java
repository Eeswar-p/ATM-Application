import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.Timer;

public class FloatingActionButton extends JButton {
    private boolean pressed = false;

    public FloatingActionButton(String text) {
        super(text);
        setFocusable(false);
        setPreferredSize(new Dimension(60, 60)); // Button size
        setFont(new Font("Arial", Font.BOLD, 18)); // Font size
        setBackground(Color.BLUE);
        setForeground(Color.WHITE);
        setText("+");

        // Remove border
        setBorder(BorderFactory.createEmptyBorder());
        setFocusPainted(false);

        // Add click listener to animate button
        addActionListener(e -> animateButton());
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        if (pressed) {
            setBounds(getX() - 2, getY() - 2, getWidth() + 4, getHeight() + 4); // Slightly scale the button when pressed
            g2d.setColor(Color.CYAN);
        } else {
            g2d.setColor(Color.BLUE);
        }

        // Draw circular button
        g2d.fillOval(0, 0, getWidth(), getHeight());

        // Draw the text
        g2d.setColor(Color.WHITE);
        g2d.drawString(getText(), getWidth() / 3, getHeight() / 2 + 5); // Adjust text position for centering
    }

    // Method to animate the button when clicked
    private void animateButton() {
        pressed = !pressed; // Toggle the pressed state
        Timer timer = new Timer(100, e -> {
            repaint();
            if (!pressed) {
                ((Timer) e.getSource()).stop();
            }
        });
        timer.start();
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Floating Action Button");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);

        FloatingActionButton button = new FloatingActionButton("login");
        button.setBounds(100, 100, 60, 60);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new LoginPage();
            }
        });
        frame.add(button);

        frame.setSize(300, 300);
        frame.setVisible(true);
    }
}