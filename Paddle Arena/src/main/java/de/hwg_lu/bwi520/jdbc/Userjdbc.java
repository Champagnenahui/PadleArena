package de.hwg_lu.bwi520.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import de.hwg_lu.bwi520.modell.User;

public class Userjdbc {


		
		private final Connection connection;
		
		public Userjdbc(Connection connection) {
			this.connection = connection;
		}
		
		
		
		// Stellt sicher, dass die Datenbank Tabelle vorhanden ist
		public boolean createTable() throws SQLException {
			String sql = "CREATE TABLE IF NOT EXISTS users (username VARCHAR PRIMARY KEY,password VARCHAR NOT NULL);";
			Statement stmt = this.connection.createStatement();
			return stmt.execute(sql);
		}	
		
		public boolean dropTable() throws SQLException {
			String sql = "DROP TABLE IF EXISTS users;";
			Statement stmt = this.connection.createStatement();
			return stmt.execute(sql);
		}
	
	public User findUser(String username, String password) throws SQLException {
		String sql = "SELECT username, password FROM account WHERE username = ? AND password = ?;";
		PreparedStatement stmt = this.connection.prepareStatement(sql);
		stmt.setString(1, username);
		stmt.setString(2, password);
		ResultSet result = stmt.executeQuery();
		User user = null;
		if (result.next()) {
			user = new User();
			user.setUsername(result.getString(1));
			user.setPassword(result.getString(2));
		}
		return user;
	}
}
