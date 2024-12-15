import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.Timer;

public class ModernAnimatedTextField {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Modern Animated TextField");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(400, 200);
            frame.setLayout(new FlowLayout());

            // Create a custom text field with animation
            AnimatedTextField textField = new AnimatedTextField(20);
            frame.add(textField);
            frame.setVisible(true);
        });
    }
}

class AnimatedTextField extends JTextField {
    private Color targetColor;
    private float targetFontSize;
    private Timer colorTimer, fontSizeTimer;
    private float currentFontSize = 18f;

    public AnimatedTextField(int columns) {
        super(columns);
        setPreferredSize(new Dimension(200, 40));
        setFont(new Font("Arial", Font.PLAIN, (int) currentFontSize));

        // Initial background color and font size
        targetColor = getBackground();
        targetFontSize = currentFontSize;

        // Add key listener to animate background color on typing
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                // Random color transition
                targetColor = new Color((int)(Math.random() * 255), (int)(Math.random() * 255), (int)(Math.random() * 255));
                startColorTransition();

                // Animate font size scaling
                targetFontSize = 24f; // Increase font size when typing
                startFontSizeTransition();
            }
        });

        // Animate background color smoothly
        colorTimer = new Timer(15, e -> {
            Color current = getBackground();
            int r = (int) interpolate(current.getRed(), targetColor.getRed(), 0.1f); // Use 0.1f instead of 0.1
            int g = (int) interpolate(current.getGreen(), targetColor.getGreen(), 0.1f);
            int b = (int) interpolate(current.getBlue(), targetColor.getBlue(), 0.1f);
            setBackground(new Color(r, g, b));
        });

        // Animate font size smoothly
        fontSizeTimer = new Timer(15, e -> {
            currentFontSize = interpolate(currentFontSize, targetFontSize, 0.1f); // Use 0.1f instead of 0.1
            setFont(new Font("Arial", Font.PLAIN, (int) currentFontSize));
        });
    }

    private void startColorTransition() {
        colorTimer.start();
    }

    private void startFontSizeTransition() {
        fontSizeTimer.start();
    }

    // Interpolation for smooth transitions (updated to work with float parameters)
    private float interpolate(float current, float target, float factor) {
        return current + (target - current) * factor;
    }

    // Interpolation for smooth transitions with integer values
    private int interpolate(int current, int target, float factor) {
        return (int) (current + (target - current) * factor);
    }
}
