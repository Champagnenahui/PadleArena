package de.hwg_lu.bwi520.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;

import de.hwg_lu.bwi520.modell.Booking;
import de.hwg_lu.bwi520.modell.Courts;
import de.hwg_lu.bwi520.modell.User;

public class Bookingjdbc {

    private Connection conn;

    public Bookingjdbc(Connection conn) {
        this.conn = conn;
    }

    // =========================
    // CREATE
    // =========================
    public void createBooking(Booking booking) throws SQLException {
        String sql = "INSERT INTO booking (user_id, court_id, start_time, end_time) VALUES (?, ?, ?, ?)";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, booking.getUser().getUsername()); // Assuming user_id is username
        stmt.setInt(2, booking.getCourt().getCourtId());
        stmt.setTimestamp(3, Timestamp.valueOf(booking.getStart()));
        stmt.setTimestamp(4, Timestamp.valueOf(booking.getEnde()));
        stmt.executeUpdate();
        stmt.close();
    }

    // =========================
    // READ BY ID
    // =========================
    public Booking getBookingById(int bookingId) throws SQLException {
        String sql = """
            SELECT b.booking_id,
                   b.start_time,
                   b.end_time,
                   u.username,
                   u.password,
                   c.court_id,
                   c.name
            FROM booking b
            JOIN users u   ON b.user_id = u.username
            JOIN courts c  ON b.court_id = c.court_id
            WHERE b.booking_id = ?
        """;
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, bookingId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (!rs.next()) return null;
                User user = new User(rs.getString("username"), rs.getString("password"));
                Courts court = new Courts(rs.getInt("court_id"), rs.getString("name"));
                LocalDateTime start = rs.getTimestamp("start_time").toLocalDateTime();
                LocalDateTime end = rs.getTimestamp("end_time").toLocalDateTime();
                return new Booking(rs.getInt("booking_id"), user, court, start, end);
            }
        }
    }

    // =========================
    // READ ALL
    // =========================
    public List<Booking> getAllBookings() throws SQLException {
        List<Booking> bookings = new ArrayList<>();
        String sql = "SELECT b.booking_id, b.start_time, b.end_time, u.username, u.password, c.court_id, c.name FROM booking b JOIN users u ON b.user_id = u.username JOIN courts c ON b.court_id = c.court_id";
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        while (rs.next()) {
            int bookingId = rs.getInt("booking_id");
            User user = new User(rs.getString("username"), rs.getString("password"));
            Courts court = new Courts(rs.getInt("court_id"), rs.getString("name"));
            LocalDateTime start = rs.getTimestamp("start_time").toLocalDateTime();
            LocalDateTime end = rs.getTimestamp("end_time").toLocalDateTime();
            bookings.add(new Booking(bookingId, user, court, start, end));
        }
        rs.close();
        stmt.close();
        return bookings;
    }

    // =========================
    // UPDATE
    // =========================
    public void updateBooking(Booking booking) throws SQLException {
        String sql = "UPDATE booking SET user_id = ?, court_id = ?, start_time = ?, end_time = ? WHERE booking_id = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, booking.getUser().getUsername()); // Assuming user_id is username
        stmt.setInt(2, booking.getCourt().getCourtId());
        stmt.setTimestamp(3, Timestamp.valueOf(booking.getStart()));
        stmt.setTimestamp(4, Timestamp.valueOf(booking.getEnde()));
        stmt.setInt(5, booking.getBookingId());
        stmt.executeUpdate();
        stmt.close();
    }

    // =========================
    // DELETE
    // =========================
    public void deleteBooking(int bookingId) throws SQLException {
        String sql = "DELETE FROM booking WHERE booking_id = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, bookingId);
        stmt.executeUpdate();
        stmt.close();
    }

    // =========================
    // CREATE TABLE
    // =========================
    public boolean createTable() throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS booking (" +
                "booking_id SERIAL PRIMARY KEY," +
                "user_id VARCHAR NOT NULL REFERENCES users(username)," +
                "court_id INT NOT NULL REFERENCES courts(court_id)," +
                "start_time TIMESTAMP NOT NULL," +
                "end_time TIMESTAMP NOT NULL" +
                ");";
        Statement stmt = this.conn.createStatement();
        return stmt.execute(sql);
    }
}