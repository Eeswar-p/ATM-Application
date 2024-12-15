import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.Timer;

public class FloatingLabelTextField {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Modern Animated TextField");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(400, 200);
            frame.setLayout(new FlowLayout());

            // Create a text field with a floating label effect
            FloatingTextFieldWithLabel textField = new FloatingTextFieldWithLabel("Enter Text");
            frame.add(textField);
            frame.setVisible(true);
        });
    }
}

class FloatingTextFieldWithLabel extends JPanel {
    private JTextField textField;
    private JLabel label;
    private boolean hasFocus = false;

    public FloatingTextFieldWithLabel(String labelText) {
        setLayout(new BorderLayout());

        // Create a label that will float
        label = new JLabel(labelText);
        label.setForeground(Color.GRAY);
        label.setFont(new Font("Arial", Font.PLAIN, 14));

        // Create the text field
        textField = new JTextField();
        textField.setFont(new Font("Arial", Font.PLAIN, 16));
        textField.setBorder(BorderFactory.createLineBorder(Color.GRAY));

        // When text field gains focus, animate label to float above
        textField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                hasFocus = true;
                animateLabel(true);
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (textField.getText().isEmpty()) {
                    hasFocus = false;
                    animateLabel(false);
                }
            }
        });

        // Move the label inside the text field area initially
        label.setBounds(5, 5, 100, 20);
        textField.setBounds(5, 25, 300, 30);

        // Add components
        add(label, BorderLayout.NORTH);
        add(textField, BorderLayout.CENTER);

        setPreferredSize(new Dimension(350, 100));
        setOpaque(false);
    }

    // Animate the label upwards or downwards
    private void animateLabel(boolean floatUp) {
        Timer animationTimer = new Timer(10, e -> {
            float targetY = floatUp ? -20 : 5;
            float currentY = label.getY();
            float newY = currentY + (targetY - currentY) * 0.1f;

            label.setBounds(5, (int) newY, 100, 20);
        });
        animationTimer.setRepeats(true);
        animationTimer.start();
    }
}
