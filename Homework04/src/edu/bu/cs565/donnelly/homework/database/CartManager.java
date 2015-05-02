package edu.bu.cs565.donnelly.homework.database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CartManager {

	private Database db;

	public CartManager(Database db) {
		this.db = db;
	}
	
	public boolean addItemToCart(int item_id, int target_qty, int priority) {
		try {
			PreparedStatement stmt  = this.db.getConnection().prepareStatement("INSERT INTO cart VALUES (?, ?, ?)");
			stmt.setInt(1, item_id);
			stmt.setInt(2, target_qty);
			stmt.setInt(3, target_qty);
			stmt.executeUpdate();
			stmt.close();
		} catch (SQLException ex) {
			return false;
		}
		return true;
	}
	public ResultSet getCart() throws SQLException {
		StringBuilder sql  = new StringBuilder();
		sql.append("SELECT i.id, i.name, c.qty, c.priority ");
		sql.append("  FROM inventory i, cart c");
		sql.append(" WHERE i.id=c.item_id");
		Statement stmt = this.db.getStatement();
		ResultSet rs = stmt.executeQuery(sql.toString());
		stmt.close();
		return rs;
	}
	
	public int buyCart() throws SQLException {
		int result = -1;
		Statement stmt = this.db.getStatement();
		ResultSet rs = stmt.executeQuery("SELECT MAX(cart_id) FROM cart_history");
		if (rs.next()) {
			result = rs.getInt(0);
		}
		stmt.close();
		
		return result;
	}
}
