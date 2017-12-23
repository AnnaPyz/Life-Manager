package app.controller;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class UserController1 {
	
	private void show(String file, String title){
		Stage stage = new Stage();
		Parent parent = null;
		
		try{
			parent = (Parent) FXMLLoader.load(getClass().getResource("/app/view/"+file));
		} catch (IOException e){
			System.out.println("Error while trying to show next view.");
		}
		Scene scene = new Scene(parent);
		stage.setScene(scene);
		stage.setTitle(title);
		stage.show();
	}
	

    @FXML
    void ctAddAction(MouseEvent event) {
    	show("UserCtAddView11.fxml","Add new current task");
    	((Node)(event.getSource())).getScene().getWindow().hide();
    }

    @FXML
    void ctClockAction(MouseEvent event) {
    	show("UserCtClockView12.fxml","Start clock for new current task");
    	((Node)(event.getSource())).getScene().getWindow().hide();
    }

    @FXML
    void ctStatsAction(MouseEvent event) {
    	show("UserCtStatsView13.fxml","Show current tasks stats");
    	((Node)(event.getSource())).getScene().getWindow().hide();	
    }

}
