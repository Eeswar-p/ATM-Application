import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegistrationForm {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> createForm());
    }

    public static void createForm() {
        // Create a frame
        JFrame frame = new JFrame("Register");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 500);
        frame.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));

        // Set up the form container
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setBackground(new Color(26, 26, 26));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        frame.add(formPanel);

        // Title with animation (could be simple text here)
        JLabel titleLabel = new JLabel("Register");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
        titleLabel.setForeground(new Color(0, 191, 255)); // #00bfff
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        formPanel.add(titleLabel);

        // Add message
        JLabel messageLabel = new JLabel("Signup now and get full access to our app.");
        messageLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        messageLabel.setForeground(new Color(255, 255, 255, 128)); // light white
        messageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        formPanel.add(messageLabel);
        formPanel.add(Box.createVerticalStrut(20));

        // First Name and Last Name
        JPanel namePanel = new JPanel();
        namePanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        formPanel.add(namePanel);

        JTextField firstNameField = createInputField("Firstname");
        JTextField lastNameField = createInputField("Lastname");
        namePanel.add(firstNameField);
        namePanel.add(lastNameField);

        // Email field
        JTextField emailField = createInputField("Email");
        formPanel.add(emailField);

        // Password and Confirm Password
        JPasswordField passwordField = new JPasswordField(20);
        passwordField.setBorder(BorderFactory.createLineBorder(new Color(105, 105, 105, 100)));
        passwordField.setBackground(new Color(51, 51, 51));
        passwordField.setForeground(Color.WHITE);
        formPanel.add(createLabeledPanel("Password", passwordField));

        JPasswordField confirmPasswordField = new JPasswordField(20);
        confirmPasswordField.setBorder(BorderFactory.createLineBorder(new Color(105, 105, 105, 100)));
        confirmPasswordField.setBackground(new Color(51, 51, 51));
        confirmPasswordField.setForeground(Color.WHITE);
        formPanel.add(createLabeledPanel("Confirm Password", confirmPasswordField));

        // Submit button
        JButton submitButton = new JButton("Submit");
        submitButton.setBackground(new Color(0, 191, 255)); // #00bfff
        submitButton.setForeground(Color.WHITE);
        submitButton.setFocusPainted(false);
        submitButton.setFont(new Font("Arial", Font.BOLD, 16));
        submitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        formPanel.add(Box.createVerticalStrut(20));
        formPanel.add(submitButton);

        // Sign-in link
        JLabel signInLabel = new JLabel("Already have an account? ");
        signInLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        signInLabel.setForeground(new Color(255, 255, 255, 128));
        signInLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        JLabel signInLink = new JLabel("<HTML><U>Sign In</U></HTML>");
        signInLink.setFont(new Font("Arial", Font.PLAIN, 14));
        signInLink.setForeground(new Color(0, 191, 255)); // #00bfff
        signInLink.setAlignmentX(Component.CENTER_ALIGNMENT);

        formPanel.add(signInLabel);
        formPanel.add(signInLink);

        // Show frame
        frame.setVisible(true);

        // Button action
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String firstName = firstNameField.getText();
                String lastName = lastNameField.getText();
                String email = emailField.getText();
                String password = new String(passwordField.getPassword());
                String confirmPassword = new String(confirmPasswordField.getPassword());

                // Add form submission logic here
                System.out.println("Form submitted: " + firstName + " " + lastName + ", " + email);
            }
        });
    }

    // Helper method to create input fields with labels
    private static JTextField createInputField(String placeholder) {
        JTextField textField = new JTextField(20);
        textField.setBackground(new Color(51, 51, 51));
        textField.setForeground(Color.WHITE);
        textField.setBorder(BorderFactory.createLineBorder(new Color(105, 105, 105, 100)));
        textField.setCaretColor(Color.WHITE);
        textField.setText(placeholder);
        textField.setFont(new Font("Arial", Font.PLAIN, 14));
        return textField;
    }

    // Helper method to create labeled panels
    private static JPanel createLabeledPanel(String label, JComponent input) {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        JLabel inputLabel = new JLabel(label);
        inputLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        inputLabel.setForeground(new Color(255, 255, 255, 128));
        panel.add(inputLabel, BorderLayout.NORTH);
        panel.add(input, BorderLayout.CENTER);
        return panel;
    }
}
