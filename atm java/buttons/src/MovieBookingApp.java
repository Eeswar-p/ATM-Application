import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MovieBookingApp {

    // Create JFrame for the main window
    private JFrame frame;
    private JComboBox<String> movieComboBox;
    private JComboBox<String> timeComboBox;
    private JCheckBox[] seatCheckBoxes;
    private JButton bookButton;
    private JTextArea bookingDetailsArea;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                MovieBookingApp window = new MovieBookingApp();
                window.frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    // Create the application
    public MovieBookingApp() {
        frame = new JFrame("Movie Booking App");
        frame.setBounds(100, 100, 700, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(new BorderLayout());

        // Set a nice background color
        frame.getContentPane().setBackground(new Color(220, 220, 220));

        // Panel for movie selection
        JPanel moviePanel = new JPanel();
        moviePanel.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 20));
        moviePanel.setBackground(new Color(240, 240, 240));

        // Movie selection combo box
        JLabel movieLabel = new JLabel("Select Movie:");
        movieLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        moviePanel.add(movieLabel);

        String[] movies = {"Movie 1", "Movie 2", "Movie 3"};
        movieComboBox = new JComboBox<>(movies);
        movieComboBox.setFont(new Font("Arial", Font.PLAIN, 14));
        moviePanel.add(movieComboBox);

        // Panel for show time selection
        JPanel timePanel = new JPanel();
        timePanel.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 20));
        timePanel.setBackground(new Color(240, 240, 240));

        JLabel timeLabel = new JLabel("Select Showtime:");
        timeLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        timePanel.add(timeLabel);

        String[] times = {"10:00 AM", "2:00 PM", "6:00 PM"};
        timeComboBox = new JComboBox<>(times);
        timeComboBox.setFont(new Font("Arial", Font.PLAIN, 14));
        timePanel.add(timeComboBox);

        // Panel for seat selection (checkboxes)
        JPanel seatPanel = new JPanel();
        seatPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10)); // Centered alignment for seats
        seatPanel.setBackground(new Color(240, 240, 240));

        seatCheckBoxes = new JCheckBox[15];
        for (int i = 0; i < 15; i++) {
            seatCheckBoxes[i] = new JCheckBox("Seat " + (i + 1));
            seatCheckBoxes[i].setFont(new Font("Arial", Font.PLAIN, 12));
            seatCheckBoxes[i].setBackground(new Color(240, 240, 240));
            seatPanel.add(seatCheckBoxes[i]);
        }

        // Panel for booking confirmation
        JPanel bookingPanel = new JPanel();
        bookingPanel.setLayout(new BorderLayout());
        bookingPanel.setBackground(new Color(240, 240, 240));

        // Button to confirm booking
        bookButton = new JButton("Book Tickets");
        bookButton.setFont(new Font("Arial", Font.BOLD, 16));
        bookButton.setBackground(new Color(0, 123, 255));
        bookButton.setForeground(Color.WHITE);
        bookingPanel.add(bookButton, BorderLayout.NORTH);

        // Text area to display booking details
        bookingDetailsArea = new JTextArea();
        bookingDetailsArea.setEditable(false);
        bookingDetailsArea.setFont(new Font("Arial", Font.PLAIN, 14));
        bookingDetailsArea.setBackground(new Color(255, 255, 255));
        bookingDetailsArea.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        bookingPanel.add(new JScrollPane(bookingDetailsArea), BorderLayout.CENTER);

        // Adding all panels to the main frame
        frame.getContentPane().add(moviePanel, BorderLayout.NORTH);
        frame.getContentPane().add(timePanel, BorderLayout.CENTER);
        frame.getContentPane().add(seatPanel, BorderLayout.SOUTH);
        frame.getContentPane().add(bookingPanel, BorderLayout.EAST);

        // Action listener for booking button
        bookButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                bookTickets();
            }
        });
    }

    // Method to handle ticket booking and display the details
    private void bookTickets() {
        String movie = (String) movieComboBox.getSelectedItem();
        String showtime = (String) timeComboBox.getSelectedItem();
        StringBuilder selectedSeats = new StringBuilder();

        // Loop through checkboxes and add selected seats to the string
        for (int i = 0; i < seatCheckBoxes.length; i++) {
            if (seatCheckBoxes[i].isSelected()) {
                selectedSeats.append("Seat ").append(i + 1).append(", ");
            }
        }

        if (selectedSeats.length() > 0) {
            selectedSeats.setLength(selectedSeats.length() - 2); // Remove last comma
            bookingDetailsArea.setText("Movie: " + movie + "\nShowtime: " + showtime +
                    "\nSelected Seats: " + selectedSeats.toString() + "\n\nBooking Confirmed!");
        } else {
            bookingDetailsArea.setText("Please select at least one seat.");
        }
    }
}

