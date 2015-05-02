package edu.bu.cs565.donnelly.homework.application;
	
import java.sql.SQLException;

import edu.bu.cs565.donnelly.homework.database.CartManager;
import edu.bu.cs565.donnelly.homework.database.Database;
import edu.bu.cs565.donnelly.homework.database.SchemaCreator;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.fxml.FXMLLoader;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			BorderPane root = (BorderPane)FXMLLoader.load(getClass().getResource("Cart.fxml"));
			Scene scene = new Scene(root,400,400);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		Database db = new Database("jdbc:sqlite:memory");
		db.connect();
		SchemaCreator sc = new SchemaCreator(db);
		(new Thread(sc)).start();
		launch(args);
		CartManager cm = new CartManager(db);
		try {
			System.out.println(cm.buyCart());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
