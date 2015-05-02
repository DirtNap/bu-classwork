package edu.bu.cs565.donnelly.homework.database;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;

public class SchemaCreator implements Runnable {

	private Database db;

	public static final String items[] = {"Apple", "Orange", "Banana", "Milk"};
	
	public SchemaCreator(Database db) {
		this.db = db;
	}
	@Override
	public void run() {
		try {
			this.createInventoryTable();
			this.createInventoryRecords();
			this.createCartTable();
			this.createCartHistoryTable();
		} catch (SQLException ex) {
			System.out.println(ex.getMessage());
		}
	}
	private void createCartHistoryTable() throws SQLException  {
		StringBuilder sql = new StringBuilder();
		sql.append("CREATE TABLE IF NOT EXISTS cart_history ");
		sql.append("(cart_id     INT    NOT NULL,");
		sql.append(" item_id     INT    NOT NULL,");
		sql.append(" qty         INT    NOT NULL,");
		sql.append(" CONSTRAINT pk_cart_history PRIMARY KEY (cart_id, item_id))");
		Statement stmt = this.db.getStatement();
		stmt.executeUpdate(sql.toString());
		stmt.close();
	}
	private void createCartTable() throws SQLException  {
		StringBuilder sql = new StringBuilder();
		sql.append("CREATE TABLE IF NOT EXISTS cart ");
		sql.append("(item_id     INT    NOT NULL PRIMARY KEY,");
		sql.append(" qty         INT    NOT NULL,");
		sql.append(" priority    INT    DEFAULT 1 NOT NULL)");
		Statement stmt = this.db.getStatement();
		stmt.executeUpdate(sql.toString());
		stmt.close();

	}
	
	private void createInventoryRecords() throws SQLException {
		Random r = new Random();
		Statement del = this.db.getStatement();
		PreparedStatement ins = this.db.getConnection().prepareStatement(
				"INSERT INTO inventory (name, qty, price_cents) VALUES (?, ?, ?)");
		boolean wasAuto = this.db.getConnection().getAutoCommit();
		this.db.getConnection().setAutoCommit(false);
		del.executeUpdate("DELETE FROM inventory");
		for (int i = 0; i < SchemaCreator.items.length; ++i) {
			ins.setString(1, SchemaCreator.items[i]);
			ins.setInt(2, r.nextInt(50));
			ins.setDouble(3, r.nextInt(5000));
			ins.addBatch();
		}
		ins.executeBatch();
		this.db.getConnection().commit();
		del.close();
		ins.close();
		this.db.getConnection().setAutoCommit(wasAuto);
	}
	private void createInventoryTable() throws SQLException {
		StringBuilder sql = new StringBuilder();
		sql.append("CREATE TABLE IF NOT EXISTS inventory ");
		sql.append("(id          INTEGER PRIMARY KEY AUTOINCREMENT,");
		sql.append(" name        TEXT   NOT NULL UNIQUE,");
		sql.append(" qty         INT    NOT NULL,");
		sql.append(" price_cents INT    NOT NULL)");
		Statement stmt = this.db.getStatement();
		//stmt.executeUpdate("DROP TABLE inventory");
		stmt.executeUpdate(sql.toString());
		stmt.close();
	}
}
