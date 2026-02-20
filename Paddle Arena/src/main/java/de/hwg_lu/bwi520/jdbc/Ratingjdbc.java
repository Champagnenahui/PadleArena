package de.hwg_lu.bwi520.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import de.hwg_lu.bwi520.modell.Rating;
import de.hwg_lu.bwi520.modell.User;

public class Ratingjdbc {

    private final Connection connection;

    public Ratingjdbc(Connection connection) {
        this.connection = connection;
    }

    // =========================
    // CREATE TABLE
    // =========================
    public boolean createTable() throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS rating (" +
                "rating_id SERIAL PRIMARY KEY, " +
                "rating INT NOT NULL CHECK (rating >= 1 AND rating <= 5), " +
                "comment TEXT, " +
                "user_id VARCHAR(100) NOT NULL REFERENCES users(username)" +
                ");";
        try (Statement stmt = this.connection.createStatement()) {
            return stmt.execute(sql);
        }
    }

    // =========================
    // CREATE
    // =========================
    public void createRating(Rating rating) throws SQLException {
        String sql = "INSERT INTO rating (rating, comment, user_id) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = this.connection.prepareStatement(sql)) {
            stmt.setInt(1, rating.getRating());
            stmt.setString(2, rating.getComment());
            stmt.setString(3, rating.getUser().getUsername());
            stmt.executeUpdate();
        }
    }

    // =========================
    // READ BY ID
    // =========================
    public Rating getRatingById(int ratingId) throws SQLException {
        String sql = "SELECT r.rating_id, r.rating, r.comment, " +
                "u.username, u.password " +
                "FROM rating r " +
                "JOIN users u ON r.user_id = u.username " +
                "WHERE r.rating_id = ?";
        try (PreparedStatement stmt = this.connection.prepareStatement(sql)) {
            stmt.setInt(1, ratingId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapRatingFromResultSet(rs);
                }
            }
        }
        return null;
    }

    // =========================
    // READ ALL
    // =========================
    public List<Rating> getAllRatings() throws SQLException {
        List<Rating> ratings = new ArrayList<>();
        String sql = "SELECT r.rating_id, r.rating, r.comment, " +
                "u.username, u.password " +
                "FROM rating r " +
                "JOIN users u ON r.user_id = u.username";
        try (Statement stmt = this.connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                ratings.add(mapRatingFromResultSet(rs));
            }
        }
        return ratings;
    }

    // =========================
    // READ BY USER
    // =========================
    public List<Rating> getRatingsByUser(String username) throws SQLException {
        List<Rating> ratings = new ArrayList<>();
        String sql = "SELECT r.rating_id, r.rating, r.comment, " +
                "u.username, u.password " +
                "FROM rating r " +
                "JOIN users u ON r.user_id = u.username " +
                "WHERE r.user_id = ?";
        try (PreparedStatement stmt = this.connection.prepareStatement(sql)) {
            stmt.setString(1, username);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    ratings.add(mapRatingFromResultSet(rs));
                }
            }
        }
        return ratings;
    }

    // =========================
    // UPDATE
    // =========================
    public void updateRating(Rating rating) throws SQLException {
        String sql = "UPDATE rating SET rating = ?, comment = ? WHERE rating_id = ?";
        try (PreparedStatement stmt = this.connection.prepareStatement(sql)) {
            stmt.setInt(1, rating.getRating());
            stmt.setString(2, rating.getComment());
            stmt.setInt(3, rating.getRatingId());
            stmt.executeUpdate();
        }
    }

    // =========================
    // DELETE
    // =========================
    public void deleteRating(int ratingId) throws SQLException {
        String sql = "DELETE FROM rating WHERE rating_id = ?";
        try (PreparedStatement stmt = this.connection.prepareStatement(sql)) {
            stmt.setInt(1, ratingId);
            stmt.executeUpdate();
        }
    }

    // =========================
    // AVERAGE RATING
    // =========================
    public double getAverageRating() throws SQLException {
        String sql = "SELECT AVG(rating) AS avg_rating FROM rating";
        try (Statement stmt = this.connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next()) {
                return rs.getDouble("avg_rating");
            }
        }
        return 0.0;
    }

    // =========================
    // HELPER
    // =========================
    private Rating mapRatingFromResultSet(ResultSet rs) throws SQLException {
        User user = new User(rs.getString("username"), rs.getString("password"));
        return new Rating(
                rs.getInt("rating_id"),
                rs.getInt("rating"),
                rs.getString("comment"),
                user
        );
    }
}
