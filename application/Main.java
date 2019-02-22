package application;

import Controller.LoginController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application{

	public static void main(String[] args) throws Exception{
		launch(args);
	}
	
	@Override
	public void start(Stage loginStage) throws Exception {
		FXMLLoader loder=new FXMLLoader(getClass().getResource("../View/login.fxml"));
		Parent login=loder.load();
		LoginController loginController=loder.getController();
		loginController.loginStage=loginStage;
		
		Scene scene=new Scene(login);
		scene.getStylesheets().add(getClass().getResource("add.css").toString());
		loginStage.setScene(scene);
		loginStage.show();
		
	}
}