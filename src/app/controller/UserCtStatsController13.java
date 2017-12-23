package app.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import app.database.DBConnector;
import app.model.Task;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class UserCtStatsController13 {

    @FXML
    private TableView<Task> t;
    @FXML
    private TableColumn<Task, Integer> t_id;
    @FXML
    private TableColumn<Task, String> t_name;
    @FXML
    private TableColumn<Task, String> t_description;
    @FXML
    private TableColumn<Task, String> t_category;
    @FXML
    private TableColumn<Task, String> t_date;
    @FXML
    private TableColumn<Task, Integer> t_duration;
    @FXML
    private TableColumn<Task, String> t_time_start;
    @FXML
    private TableColumn<Task, String> t_time_end;
    @FXML
    private Button btn_refresh;
    @FXML
    private Button btn_bycategory;
    @FXML
    private Button btn_return;
    public ObservableList<Task> tasks = FXCollections.observableArrayList();
	PreparedStatement ps;
	Connection conn;
	DBConnector db;
    @FXML
    void gobackAction(MouseEvent event) {
    	Stage stage = new Stage();
    	Parent parent = null;
    	try {
			parent = (Parent) FXMLLoader.load(getClass().getResource("/app/view/UserView1.fxml"));
    	Scene scene = new Scene(parent);
    	stage.setScene(scene);
    	stage.setTitle("User panel");
    	stage.show();		
    	} catch (IOException e) {
			e.printStackTrace();
		}
		((Node)(event.getSource())).getScene().getWindow().hide();
    }

    @FXML
    void refreshAction(MouseEvent event) {
    	select();
    }
    @FXML
    void byCategoryAction(MouseEvent event) {

    }
    
	private void connection() {
		db = new DBConnector();
		conn = db.connInit();
	}    
    private void select(){
    	connection();
    	tasks.clear();
    	t.setItems(tasks); 	
    	try {
    		ps = conn.prepareStatement("SELECT id, name, description, category, date_start, duration, time_start, time_end FROM tasks WHERE user_id = ?");
    		ps.setInt(1, LoginController.user_id);
    		ResultSet rs = ps.executeQuery();
    		
    		while(rs.next()){
    			tasks.add(new Task(rs.getInt("id"), rs.getString("name"), rs.getString("description"), 
    					rs.getString("category"), rs.getString("date_start"), rs.getInt("duration"), rs.getString("time_start"), rs.getString("time_end")));
    		}
    	    t_id.setCellValueFactory(new PropertyValueFactory<Task, Integer>("id"));
    	    t_name.setCellValueFactory(new PropertyValueFactory<Task, String>("name"));
    	    t_description.setCellValueFactory(new PropertyValueFactory<Task, String>("description"));
    	    t_category.setCellValueFactory(new PropertyValueFactory<Task, String>("category"));
    	    t_date.setCellValueFactory(new PropertyValueFactory<Task, String>("date_start"));
    	    t_duration.setCellValueFactory(new PropertyValueFactory<Task, Integer>("duration"));
    	    t_time_start.setCellValueFactory(new PropertyValueFactory<Task, String>("time_start"));
    	    t_time_end.setCellValueFactory(new PropertyValueFactory<Task, String>("time_end"));
    		t.setItems(null);
    		t.setItems(tasks);   		
    	} catch (SQLException e){
    		e.printStackTrace();
    	}    	
    }
    
	public void initialize() {
		select();
	}
}
