package app.controller;

import java.io.IOException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import app.database.DBConnector;
import app.model.Task;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;

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
    private CheckBox cb_dates;
    @FXML
    private DatePicker dp_start;
    @FXML
    private DatePicker dp_finish;
    @FXML
    private CheckBox cb_group;
    @FXML
    private Button btn_apply;
    @FXML
    private Button btn_reset;    
    @FXML
    private Button btn_return;
    
    public ObservableList<Task> tasks = FXCollections.observableArrayList();
	PreparedStatement ps;
	Connection conn;
	DBConnector db;
	
    @FXML
    void gobackAction(MouseEvent event) throws IOException {
    	Stage stage = new Stage();
    	Parent parent = null;
		parent = (Parent) FXMLLoader.load(getClass().getResource("/app/view/UserView1.fxml"));
    	Scene scene = new Scene(parent);
    	stage.setScene(scene);
    	stage.setTitle("User panel");
    	stage.show();		

		((Node)(event.getSource())).getScene().getWindow().hide();
    }

    @FXML
    void resetAction(MouseEvent event) {
    	select();
    }

    @FXML
    void filterDatesAction(MouseEvent event) {
    	if(cb_dates.isSelected()){
        dp_start.setDisable(false);
    	dp_finish.setDisable(false);
    	dp_finish.setValue(LocalDate.now());
    	dp_start.setValue(dp_finish.getValue().minusDays(7));		
    	} else {
            dp_start.setDisable(true);
        	dp_finish.setDisable(true);
    	}
    }
//    @FXML
//    void groupAction(MouseEvent event) {
//
//    }
    String start_date;
    String finish_date;
    
    @FXML
    void applyAction(MouseEvent event) {
    	if(cb_dates.isSelected()){
    		start_date = dp_start.getValue().toString();
    		finish_date = dp_finish.getValue().toString();
    		if(cb_group.isSelected()){
    			selectGrouped(start_date, finish_date);
    			// odnoœnik do kolejnego widoku
    		} else {
    			select(start_date, finish_date);
    		}
    	} else if(cb_group.isSelected()){
			selectGrouped();
    	} else {
    		select();
    	}
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
    private void select(String date1, String date2){
    	connection();
    	tasks.clear();
    	t.setItems(tasks); 	
    	try {
    		ps = conn.prepareStatement("SELECT id, name, description, category, date_start, duration, time_start, time_end FROM tasks WHERE user_id = ? AND date_start >= ? AND date_start <= ?");
    		ps.setInt(1, LoginController.user_id);
    		ps.setString(2, date1);
    		ps.setString(3, date2);
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

    private void selectGrouped(String date1, String date2) {
    	connection();
    	tasks.clear();
    	t.setItems(tasks); 	
    	try {
    		ps = conn.prepareStatement("SELECT category, sum(duration) FROM tasks WHERE user_id = ? AND date_start >= ? AND date_start <= ? GROUP BY category");
    		ps.setInt(1, LoginController.user_id);
    		ps.setString(2, date1);
    		ps.setString(3, date2);
    		ResultSet rs = ps.executeQuery();
    		
    		while(rs.next()){
    			tasks.add(new Task(0, "", "", 
    					rs.getString("category"), "", rs.getInt("sum(duration)"), "", ""));
    		}
    	    t_id.setCellValueFactory(new PropertyValueFactory<Task, Integer>("0"));
    	    t_name.setCellValueFactory(new PropertyValueFactory<Task, String>(""));
    	    t_description.setCellValueFactory(new PropertyValueFactory<Task, String>(""));
    	    t_category.setCellValueFactory(new PropertyValueFactory<Task, String>("category"));
    	    t_date.setCellValueFactory(new PropertyValueFactory<Task, String>(""));
    	    t_duration.setCellValueFactory(new PropertyValueFactory<Task, Integer>("duration"));
    	    t_time_start.setCellValueFactory(new PropertyValueFactory<Task, String>(""));
    	    t_time_end.setCellValueFactory(new PropertyValueFactory<Task, String>(""));
    		t.setItems(null);
    		t.setItems(tasks);   		
    	} catch (SQLException e){
    		e.printStackTrace();
    	}    	    	
    }
    private void selectGrouped() {
    	connection();
    	tasks.clear();
    	t.setItems(tasks); 	
    	try {
    		ps = conn.prepareStatement("SELECT category, sum(duration) FROM tasks WHERE user_id = ? GROUP BY category");
    		ps.setInt(1, LoginController.user_id);
    		ResultSet rs = ps.executeQuery();
    		
    		while(rs.next()){
    			tasks.add(new Task(0, "", "", 
    					rs.getString("category"), "", rs.getInt("sum(duration)"), "", ""));
    		}
    	    t_id.setCellValueFactory(new PropertyValueFactory<Task, Integer>("0"));
    	    t_name.setCellValueFactory(new PropertyValueFactory<Task, String>(""));
    	    t_description.setCellValueFactory(new PropertyValueFactory<Task, String>(""));
    	    t_category.setCellValueFactory(new PropertyValueFactory<Task, String>("category"));
    	    t_date.setCellValueFactory(new PropertyValueFactory<Task, String>(""));
    	    t_duration.setCellValueFactory(new PropertyValueFactory<Task, Integer>("duration"));
    	    t_time_start.setCellValueFactory(new PropertyValueFactory<Task, String>(""));
    	    t_time_end.setCellValueFactory(new PropertyValueFactory<Task, String>(""));
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
