package edu.bu.cs565.donnelly.homework.database;

import java.sql.*;

public class Database {

	private String connectionString;
	private Connection connection;

	public Database(String connectionString) {
		this.connectionString = connectionString;
		try {
			Class.forName("org.sqlite.JDBC");
		} catch (Exception ex) {
			System.out.println("Could not load sqlite driver.");
		}
		
	}
	public void connect() {
		try {
			this.connection = DriverManager.getConnection(this.connectionString);
		} catch (SQLException ex) {
			System.out.println(ex.getMessage());
		}
	}
	public Connection getConnection() {
		return this.connection;
	}
	public Statement getStatement() throws SQLException {
		return this.connection.createStatement();
	}
}
