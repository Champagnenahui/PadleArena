package de.hwg_lu.bwi520.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import de.hwg_lu.bwi520.modell.User;

public class Userjdbc {

    private final Connection connection;

    public Userjdbc(Connection connection) {
        this.connection = connection;
    }

    // =========================
    // CREATE TABLE
    // =========================
    public boolean createTable() throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS users (" +
                "username VARCHAR(100) PRIMARY KEY, " +
                "password VARCHAR(255) NOT NULL" +
                ");";
        try (Statement stmt = this.connection.createStatement()) {
            return stmt.execute(sql);
        }
    }

    public boolean dropTable() throws SQLException {
        String sql = "DROP TABLE IF EXISTS users;";
        try (Statement stmt = this.connection.createStatement()) {
            return stmt.execute(sql);
        }
    }

    // =========================
    // CREATE
    // =========================
    public void createUser(User user) throws SQLException {
        String sql = "INSERT INTO users (username, password) VALUES (?, ?)";
        try (PreparedStatement stmt = this.connection.prepareStatement(sql)) {
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPassword());
            stmt.executeUpdate();
        }
    }

    // =========================
    // READ - Login (find by username + password)
    // =========================
    public User findUser(String username, String password) throws SQLException {
        String sql = "SELECT username, password FROM users WHERE username = ? AND password = ?";
        try (PreparedStatement stmt = this.connection.prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new User(rs.getString("username"), rs.getString("password"));
                }
            }
        }
        return null;
    }

    // =========================
    // READ - Find by username
    // =========================
    public User findByUsername(String username) throws SQLException {
        String sql = "SELECT username, password FROM users WHERE username = ?";
        try (PreparedStatement stmt = this.connection.prepareStatement(sql)) {
            stmt.setString(1, username);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new User(rs.getString("username"), rs.getString("password"));
                }
            }
        }
        return null;
    }

    // =========================
    // READ ALL
    // =========================
    public List<User> getAllUsers() throws SQLException {
        List<User> users = new ArrayList<>();
        String sql = "SELECT username, password FROM users";
        try (Statement stmt = this.connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                users.add(new User(rs.getString("username"), rs.getString("password")));
            }
        }
        return users;
    }

    // =========================
    // UPDATE
    // =========================
    public void updateUser(String oldUsername, User user) throws SQLException {
        String sql = "UPDATE users SET username = ?, password = ? WHERE username = ?";
        try (PreparedStatement stmt = this.connection.prepareStatement(sql)) {
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPassword());
            stmt.setString(3, oldUsername);
            stmt.executeUpdate();
        }
    }

    // =========================
    // DELETE
    // =========================
    public void deleteUser(String username) throws SQLException {
        String sql = "DELETE FROM users WHERE username = ?";
        try (PreparedStatement stmt = this.connection.prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.executeUpdate();
        }
    }
}
