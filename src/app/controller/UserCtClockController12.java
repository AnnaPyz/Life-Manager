package app.controller;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalTime;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class UserCtClockController12 {

    @FXML
    private Button btn_add;
    @FXML
    private Button btn_clear;
    @FXML
    private Button btn_return;
    @FXML
    private Button btn_start;
    @FXML
    private Button btn_stop;
    @FXML
    private Button btn_pause;
    
    static LocalTime start;
    static LocalTime stop;
    static Duration interval;   
    
    @FXML
    void startAction(MouseEvent event) {
    	start = LocalTime.now();
    }

    @FXML
    void stopAction(MouseEvent event) {
    	stop = LocalTime.now();    	
    	interval = Duration.between(start, stop);
//    	System.out.println(interval); TEST
//    	System.out.println(java.sql.Time.valueOf(start));
//    	System.out.println(interval.getSeconds());
//    	System.out.println(interval.getSeconds()/60);

    }    
    @FXML
    void addAction(MouseEvent event) throws IOException {
    	Stage stage = new Stage();
    	Parent parent = null;
    	parent = (Parent) FXMLLoader.load(getClass().getResource("/app/view/UserCtClockAddView121.fxml"));
    	Scene scene = new Scene(parent);
    	stage.setScene(scene);
    	stage.setTitle("Add new current task");
    	stage.show();    
    	((Node)(event.getSource())).getScene().getWindow().hide();
    }

    @FXML
    void clearAction(MouseEvent event) {
    	start = null;
    	stop = null;
    	interval = null;
    }

    @FXML
    void gobackAction(MouseEvent event) throws IOException {
    	Stage stage = new Stage();
    	Parent parent = null;
    	parent = (Parent) FXMLLoader.load(getClass().getResource("/app/view/UserView1.fxml"));
    	Scene scene = new Scene(parent);
    	stage.setScene(scene);
    	stage.setTitle("User panel");
    	stage.show();
    }
}
