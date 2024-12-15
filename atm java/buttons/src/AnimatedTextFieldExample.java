import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.Timer;

public class AnimatedTextFieldExample {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Animated TextField Example");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(400, 200);
            frame.setLayout(new FlowLayout());

            // Create a text field
            JTextField textField = new JTextField(20);

            // Animation effect: Blinking cursor
            Timer blinkTimer = new Timer(500, e -> {
                if (textField.hasFocus()) {
                    textField.setCaretColor(textField.getCaretColor() == Color.BLACK ? Color.RED : Color.BLACK);
                }
            });

            // Start the timer when the text field gains focus
            textField.addFocusListener(new FocusAdapter() {
                @Override
                public void focusGained(FocusEvent e) {
                    blinkTimer.start();
                }

                @Override
                public void focusLost(FocusEvent e) {
                    blinkTimer.stop();
                    textField.setCaretColor(Color.BLACK); // Reset caret color
                }
            });

            // Animation effect: Background color change on typing
            textField.addKeyListener(new KeyAdapter() {
                @Override
                public void keyTyped(KeyEvent e) {
                    textField.setBackground(new Color(
                            (int) (Math.random() * 255),
                            (int) (Math.random() * 255),
                            (int) (Math.random() * 255)
                    ));
                }
            });

            frame.add(textField);
            frame.setVisible(true);
        });
    }
}
