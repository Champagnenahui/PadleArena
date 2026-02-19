package de.hwg_lu.bwi520.jdbc;

import de.hwg_lu.bwi520.modell.Courts;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Courtsjdbc {
    private final Connection connection;

    public Courtsjdbc(Connection connection) {
        this.connection = connection;
    }

    // Create courts table if it does not exist
    public boolean createTable() throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS courts (" +
                "court_id SERIAL PRIMARY KEY," +
                "name VARCHAR NOT NULL" +
                ");";
        Statement stmt = this.connection.createStatement();
        return stmt.execute(sql);
    }

    // Insert a new court
    public void createCourt(Courts court) throws SQLException {
        String sql = "INSERT INTO courts (name) VALUES (?)";
        PreparedStatement stmt = this.connection.prepareStatement(sql);
        stmt.setString(1, court.getName());
        stmt.executeUpdate();
        stmt.close();
    }

    // Get all courts
    public List<Courts> getAllCourts() throws SQLException {
        List<Courts> courts = new ArrayList<>();
        String sql = "SELECT court_id, name FROM courts";
        Statement stmt = this.connection.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        while (rs.next()) {
            courts.add(new Courts(rs.getInt("court_id"), rs.getString("name")));
        }
        rs.close();
        stmt.close();
        return courts;
    }

    // Get a court by ID
    public Courts getCourtById(int courtId) throws SQLException {
        String sql = "SELECT court_id, name FROM courts WHERE court_id = ?";
        PreparedStatement stmt = this.connection.prepareStatement(sql);
        stmt.setInt(1, courtId);
        ResultSet rs = stmt.executeQuery();
        Courts court = null;
        if (rs.next()) {
            court = new Courts(rs.getInt("court_id"), rs.getString("name"));
        }
        rs.close();
        stmt.close();
        return court;
    }
}