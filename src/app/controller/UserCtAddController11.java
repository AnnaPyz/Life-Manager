package app.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import app.database.DBConnector;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class UserCtAddController11 {

	@FXML
	private TextField tf_name;
	@FXML
	private TextField tf_description;
	ObservableList<String> categories = FXCollections.observableArrayList("Work", "Leisure", "Study");
	@FXML
	private ComboBox<String> c_category;
	@FXML
	private DatePicker d_date;
	@FXML
	private TextField tf_duration_h;
	@FXML
	private TextField tf_duration_min;
	@FXML
	private Button btn_add;
	@FXML
	private Button btn_clear;
	@FXML
	private Button btn_return;

	@FXML
	void clearAction(MouseEvent event) {
		tf_name.clear();
		tf_description.clear();
		c_category.setValue(null);
		// d_date.c
		tf_duration_h.clear();
		tf_duration_min.clear();
	}

	@FXML
	void gobackAction(MouseEvent event) { // czemu nie dzia�a????????
		Stage stage = new Stage();
		Parent parent = null;
		System.out.println("111");
		try {
			parent = (Parent) FXMLLoader.load(getClass().getResource("/app/view/UserView1.fxml")); // TU SI� WYKRZACZA !!!
			Scene scene = new Scene(parent);
			stage.setScene(scene);
			stage.setTitle("User panel");
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
		((Node)(event.getSource())).getScene().getWindow().hide();

		// System.out.println(d_date.getValue().toString());
	}

	@FXML
	void submitAction(MouseEvent event) {
		if (tf_name.getText().length() < 1 || tf_description.getText().length() < 1 || c_category.getValue() == null
				|| (tf_duration_h.getText().length() < 1 || tf_duration_min.getText().length() < 1)) {
			Alert empty = new Alert(AlertType.WARNING);
			empty.setTitle("Incomplete questionaire");
			empty.setHeaderText("Incomplete questionnaire");
			empty.setContentText("Please fill all the fields");
			empty.showAndWait();
			System.out.println(d_date.getValue());
		} else {
			DBConnector db = new DBConnector();
			Connection conn = db.connInit();
			int duration = Integer.parseInt(tf_duration_h.getText()) * 60;
			duration += Integer.parseInt(tf_duration_min.getText());
			try {
				PreparedStatement ps = conn.prepareStatement(
						"INSERT INTO tasks (user_id, name, description, category, date_start, duration) values (?,?,?,?,?,?)");
				ps.setInt(1, LoginController.user_id);
				ps.setString(2, tf_name.getText());
				ps.setString(3, tf_description.getText());
				ps.setString(4, c_category.getValue());
				ps.setString(5, d_date.getValue().toString()); // DODA� OPCJ� IF ELSE Z AUTOMATYCNYM DOPISYWANIEM DZISIEJSZEJ DATY
				ps.setInt(6, duration);
				ps.executeUpdate();
				clearAction(null);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public void initialize() {
		c_category.setItems(categories);
	}

}
