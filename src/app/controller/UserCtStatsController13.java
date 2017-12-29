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
    private Button btn_filter_dates;
    @FXML
    private Button btn_bycategory;
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
    	Stage stage = new Stage();
    	Parent parent = null;
    	try {
			parent = (Parent)FXMLLoader.load(getClass().getResource("/app/view/UserCtStatsFilterView131.fxml/"));
		} catch (IOException e) {
			System.out.println("Error while trying to show next view.");
		}
    	Scene scene = new Scene(parent);
    	stage.setScene(scene);
    	stage.setTitle("Selecting dates");
    	stage.show();    		
// jak zrobiæ by program poczeka³ tu do zamkniêcia okna z wyborem dat ?
    	//System.out.println(UserCtStatsFilterController131.start_date +", " + UserCtStatsFilterController131.finish_date); //TEST
    	//select(UserCtStatsFilterController131.start_date, UserCtStatsFilterController131.finish_date);
    }
    void filterDates(String date1, String date2){
    	select(date1, date2);
    }
    
    @FXML
    void byCategoryAction(MouseEvent event) {
    	connection();
    	tasks.clear();
    	t.setItems(tasks); 	
    	try {
    		ps = conn.prepareStatement("SELECT category, sum(duration) FROM tasks WHERE user_id = ? AND date_start >= '?' AND date_start <= '?' GROUP BY category");
    		ps.setInt(1, LoginController.user_id);
    		ps.setString(2, UserCtStatsFilterController131.start_date);
    		ps.setString(3, UserCtStatsFilterController131.finish_date);
    		ResultSet rs = ps.executeQuery();
    		
    		while(rs.next()){
    			tasks.add(new Task(rs.getString("category"), rs.getInt("duration")));
    		}
    	    t_category.setCellValueFactory(new PropertyValueFactory<Task, String>("category"));
    	    t_duration.setCellValueFactory(new PropertyValueFactory<Task, Integer>("duration"));
    		t.setItems(null);
    		t.setItems(tasks);   		
    	} catch (SQLException e){
    		e.printStackTrace();
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
    
	public void initialize() {
		select();
	}
}
