package app.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import app.database.DBConnector;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class LoginController {

	@FXML
	private TextField tf_login;
	@FXML
	private PasswordField pf_login;
	@FXML
	private Button bt_login;
	@FXML
	void enterAction(KeyEvent event) {
		if (event.getCode().toString().equals("ENTER")) {
			loginMethod();
			((Node)(event.getSource())).getScene().getWindow().hide();
		}
	}

	@FXML
	void loginAction(MouseEvent event) {
		loginMethod();
		((Node)(event.getSource())).getScene().getWindow().hide();
	}

	DBConnector db;
	static String login;
	static int user_id;

	private void loginMethod() {
		String login = tf_login.getText();
		String pass = pf_login.getText();
		db = new DBConnector();
		Connection conn = db.connInit();
		ResultSet rs = null;
		try {
			Statement cursor = conn.createStatement();
			rs = cursor
					.executeQuery("SELECT id, access FROM users WHERE login = '" + login + "' AND pass = '" + pass + "';");
		} catch (SQLException e) {
			e.printStackTrace();
		}

		LoginController.login = login;
		try {
			if (rs.next()) {
				String temp = null;
				String title = null;
				if (rs.getInt("access") == 0) {
					temp = "UserView1.fxml";
					title = "User Panel";
				} else if (rs.getInt("access") == 1) {
					temp = "BossView2.fxml";
					title = "Boss Panel";
				}
				user_id = rs.getInt("id"); 
				Stage stage = new Stage();
				Parent parent = null;
				Scene scene = null;
				try {
					parent = (Parent) FXMLLoader.load(getClass().getResource("/app/view/" + temp));
				} catch (IOException e) {
					e.printStackTrace();
				}
				scene = new Scene(parent);
				stage.setScene(scene);
				stage.setTitle(title);
				stage.show();
				//((Node) (event.getSource())).getScene().getWindow().hide();  // JAK TO TU ZAPISAÆ BY DZIA£A£O?
			} else {
				Alert loginError = new Alert(AlertType.ERROR);
				loginError.setTitle("Login error!");
				loginError.setHeaderText("Login unsuccessful");
				loginError.setContentText("Credentials are incorrect");
				loginError.showAndWait();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
