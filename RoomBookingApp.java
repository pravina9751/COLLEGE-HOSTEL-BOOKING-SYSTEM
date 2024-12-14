import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class RoomBookingApp {
    // Room class
    static class Room {
        private String roomNumber;
        private boolean available;

        public Room(String roomNumber) {
            this.roomNumber = roomNumber;
            this.available = true;
        }

        public String getRoomNumber() {
            return roomNumber;
        }

        public boolean isAvailable() {
            return available;
        }

        public void book() {
            this.available = false;
        }

        public void release() {
            this.available = true;
        }
    }

    // RoomBookingManager class
    static class RoomBookingManager {
        private List<Room> rooms;

        public RoomBookingManager(List<Room> rooms) {
            this.rooms = rooms;
        }

        public List<Room> getRooms() {
            return rooms;
        }

        public boolean bookRoom(String roomNumber) {
            for (Room room : rooms) {
                if (room.getRoomNumber().equals(roomNumber) && room.isAvailable()) {
                    room.book();
                    return true;
                }
            }
            return false; // Room not available or not found
        }

        public void releaseRoom(String roomNumber) {
            for (Room room : rooms) {
                if (room.getRoomNumber().equals(roomNumber) && !room.isAvailable()) {
                    room.release();
                    return;
                }
            }
        }
    }

    private JFrame frame;
    private RoomBookingManager bookingManager;
    private JTextArea roomDisplayArea;

    public RoomBookingApp() {
        // Initialize rooms
        List<Room> rooms = new ArrayList<>();
        rooms.add(new Room("101"));
        rooms.add(new Room("102"));
        rooms.add(new Room("103"));
        rooms.add(new Room("104"));

        // Initialize booking manager
        bookingManager = new RoomBookingManager(rooms);

        // Initialize UI
        frame = new JFrame("Room Booking System");
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        roomDisplayArea = new JTextArea();
        roomDisplayArea.setEditable(false);
        updateRoomDisplay();

        // Book Room Button
        JButton bookRoomButton = new JButton("Book Room");
        bookRoomButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String roomNumber = JOptionPane.showInputDialog(frame, "Enter room number to book:");
                if (roomNumber != null && !roomNumber.trim().isEmpty()) {
                    boolean success = bookingManager.bookRoom(roomNumber);
                    if (success) {
                        JOptionPane.showMessageDialog(frame, "Room " + roomNumber + " successfully booked.");
                    } else {
                        JOptionPane.showMessageDialog(frame, "Room " + roomNumber + " is not available.");
                    }
                    updateRoomDisplay();  // Update room display after booking
                }
            }
        });

        // Release Room Button
        JButton releaseRoomButton = new JButton("Release Room");
        releaseRoomButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String roomNumber = JOptionPane.showInputDialog(frame, "Enter room number to release:");
                if (roomNumber != null && !roomNumber.trim().isEmpty()) {
                    bookingManager.releaseRoom(roomNumber);
                    JOptionPane.showMessageDialog(frame, "Room " + roomNumber + " released.");
                    updateRoomDisplay();  // Update room display after releasing
                }
            }
        });

        // Panel for buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(bookRoomButton);
        buttonPanel.add(releaseRoomButton);

        // Layout setup
        frame.setLayout(new BorderLayout());
        frame.add(new JScrollPane(roomDisplayArea), BorderLayout.CENTER);
        frame.add(buttonPanel, BorderLayout.SOUTH);

        frame.setVisible(true);
    }

    // Method to update room display area
    private void updateRoomDisplay() {
        StringBuilder displayText = new StringBuilder("Available Rooms:\n");
        for (Room room : bookingManager.getRooms()) {
            displayText.append("Room " + room.getRoomNumber() + ": " + (room.isAvailable() ? "Available" : "Booked") + "\n");
        }
        roomDisplayArea.setText(displayText.toString());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new RoomBookingApp();
            }
        });
    }
}