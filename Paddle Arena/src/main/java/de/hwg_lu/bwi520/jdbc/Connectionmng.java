package de.hwg_lu.bwi520.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connectionmng {


		private static Connection instance;
		
		
		private static void setSchema(Connection connection, String schema) throws SQLException {
			String sql = "SET Schema '" + schema + "'";
			System.out.println(sql);
			connection.prepareStatement(sql).executeUpdate();
			System.out.println("Schema " + schema + " gesetzt");
		}
		
		public static Connection getSharedConnection() throws ClassNotFoundException, SQLException {
			if (instance == null) {
				instance = createConnection();
			}
			return instance;
		}
		
		public static Connection createConnection() throws ClassNotFoundException, SQLException {
			String dbDrivername = "org.postgresql.Driver";
			String dbServer = "143.93.200.243";
			String dbPort = "5435";
			String dbName = "BWUEBDB";
			
			String dbUserid = "user1";
			String dbPassword = "pgusers";
			String schema = "bwi520_638326";
			String dbURL = "jdbc:postgresql://" + dbServer + ":" + dbPort + "/" + dbName;
			Class.forName(dbDrivername);
			
			
			Connection connection = DriverManager.getConnection(
					dbURL,
					dbUserid,
					dbPassword
				);
			setSchema(connection, schema);
			return connection;
		}
		public static void main(String[] args) {
			
				try {
					Connection conn = Connectionmng.getSharedConnection();
					System.out.println("Verbindung erfolgreich: " + (conn != null));
					Userjdbc userJdbc = new Userjdbc(conn);
					Bookingjdbc bookingJdbc = new Bookingjdbc(conn);
					Courtsjdbc courtsJdbc = new Courtsjdbc(conn);
					userJdbc.createTable();
					courtsJdbc.createTable();
					bookingJdbc.createTable();
					
				} catch (ClassNotFoundException | SQLException e) {
					e.printStackTrace();
				}
				
		}
	}
