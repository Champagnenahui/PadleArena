package de.hwg_lu.bwi520.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import de.hwg_lu.bwi520.modell.Courts;

public class Courtsjdbc {

    private final Connection connection;

    public Courtsjdbc(Connection connection) {
        this.connection = connection;
    }

    // =========================
    // CREATE TABLE
    // =========================
    public boolean createTable() throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS courts (" +
                "court_id SERIAL PRIMARY KEY, " +
                "name VARCHAR(200) NOT NULL" +
                ");";
        try (Statement stmt = this.connection.createStatement()) {
            return stmt.execute(sql);
        }
    }

    // =========================
    // CREATE
    // =========================
    public void createCourt(Courts court) throws SQLException {
        String sql = "INSERT INTO courts (name) VALUES (?)";
        try (PreparedStatement stmt = this.connection.prepareStatement(sql)) {
            stmt.setString(1, court.getName());
            stmt.executeUpdate();
        }
    }

    // =========================
    // READ ALL
    // =========================
    public List<Courts> getAllCourts() throws SQLException {
        List<Courts> courts = new ArrayList<>();
        String sql = "SELECT court_id, name FROM courts";
        try (Statement stmt = this.connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                courts.add(new Courts(rs.getInt("court_id"), rs.getString("name")));
            }
        }
        return courts;
    }

    // =========================
    // READ BY ID
    // =========================
    public Courts getCourtById(int courtId) throws SQLException {
        String sql = "SELECT court_id, name FROM courts WHERE court_id = ?";
        try (PreparedStatement stmt = this.connection.prepareStatement(sql)) {
            stmt.setInt(1, courtId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Courts(rs.getInt("court_id"), rs.getString("name"));
                }
            }
        }
        return null;
    }

    // =========================
    // UPDATE
    // =========================
    public void updateCourt(Courts court) throws SQLException {
        String sql = "UPDATE courts SET name = ? WHERE court_id = ?";
        try (PreparedStatement stmt = this.connection.prepareStatement(sql)) {
            stmt.setString(1, court.getName());
            stmt.setInt(2, court.getCourtId());
            stmt.executeUpdate();
        }
    }

    // =========================
    // DELETE
    // =========================
    public void deleteCourt(int courtId) throws SQLException {
        String sql = "DELETE FROM courts WHERE court_id = ?";
        try (PreparedStatement stmt = this.connection.prepareStatement(sql)) {
            stmt.setInt(1, courtId);
            stmt.executeUpdate();
        }
    }
}
