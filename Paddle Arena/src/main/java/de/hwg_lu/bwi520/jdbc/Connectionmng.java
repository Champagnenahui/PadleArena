package de.hwg_lu.bwi520.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Connectionmng {

    private static Connection instance;

    private static final String DB_DRIVER = "org.postgresql.Driver";
    private static final String DB_SERVER = "143.93.200.243";
    private static final String DB_PORT = "5435";
    private static final String DB_NAME = "BWUEBDB";
    private static final String DB_USER = "user1";
    private static final String DB_PASSWORD = "pgusers";
    private static final String DB_SCHEMA = "bwi520_638326";

    private Connectionmng() {
        // Utility-Klasse, nicht instanziierbar
    }

    private static void setSchema(Connection connection, String schema) throws SQLException {
        // Validierung des Schema-Namens gegen SQL-Injection
        if (!schema.matches("[a-zA-Z0-9_]+")) {
            throw new SQLException("Ungueltiger Schema-Name: " + schema);
        }
        String sql = "SET SCHEMA '" + schema + "'";
        Statement stmt = connection.createStatement();
        stmt.executeUpdate(sql);
        stmt.close();
        System.out.println("Schema " + schema + " gesetzt");
    }

    public static synchronized Connection getSharedConnection() throws ClassNotFoundException, SQLException {
        if (instance == null || instance.isClosed()) {
            instance = createConnection();
        }
        return instance;
    }

    public static Connection createConnection() throws ClassNotFoundException, SQLException {
        String dbURL = "jdbc:postgresql://" + DB_SERVER + ":" + DB_PORT + "/" + DB_NAME;
        Class.forName(DB_DRIVER);
        Connection connection = DriverManager.getConnection(dbURL, DB_USER, DB_PASSWORD);
        setSchema(connection, DB_SCHEMA);
        return connection;
    }

    public static void closeConnection() {
        if (instance != null) {
            try {
                instance.close();
                System.out.println("Verbindung geschlossen");
            } catch (SQLException e) {
                e.printStackTrace();
            }
            instance = null;
        }
    }

    public static void main(String[] args) {
        try {
            Connection conn = Connectionmng.getSharedConnection();
            System.out.println("Verbindung erfolgreich: " + (conn != null));

            Userjdbc userJdbc = new Userjdbc(conn);
            Courtsjdbc courtsJdbc = new Courtsjdbc(conn);
            Bookingjdbc bookingJdbc = new Bookingjdbc(conn);
            Ratingjdbc ratingJdbc = new Ratingjdbc(conn);

            userJdbc.createTable();
            courtsJdbc.createTable();
            bookingJdbc.createTable();
            ratingJdbc.createTable();

            System.out.println("Alle Tabellen erfolgreich erstellt.");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            Connectionmng.closeConnection();
        }
    }
}
