package Controller;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ForHereToGoController implements Initializable {
	public Stage forHereToGoStage;
	
	@FXML private Button forHereToGoBtnForHere;
	@FXML private Button forHereToGoBtnToGo;
	@FXML private TextField forHereToGoTxtClock;
	private int count;
	public static int flag = 0;
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		forHereToGoBtnForHere.setOnAction((ActionEvent event)->{handleForHereToGoBtnForHereAction();});
		forHereToGoBtnToGo.setOnAction((ActionEvent event)->{handleForHereToGoBtnToGoAction();});
		//시간표시
		clock();
		
	}
	
	public void handleForHereToGoBtnForHereAction() {
		//매장
		try {
			Stage hallStage=new Stage();
			FXMLLoader loader=new FXMLLoader(getClass().getResource("../View/hall.fxml"));
			Parent kitchen=loader.load();
			HallController hallController =loader.getController();
			hallController.hallStage=hallStage;
			flag = 0;
			Scene scene=new Scene(kitchen);
			hallStage.setScene(scene);
			forHereToGoStage.close();
			hallStage.show();
			
			
		} catch (Exception e) {
			callAlert("화면 전환 오류 :화면 전환에 문제가 있습니다");
			e.printStackTrace();
		}		
	}
	public void handleForHereToGoBtnToGoAction() {
		//포장
		try {
			Stage hallStage=new Stage();
			FXMLLoader loader=new FXMLLoader(getClass().getResource("../View/hall.fxml"));
			Parent kitchen=loader.load();
			HallController hallController =loader.getController();
			hallController.hallStage=hallStage;
			flag = 1;
			Scene scene=new Scene(kitchen);
			hallStage.setScene(scene);
			forHereToGoStage.close();
			hallStage.show();
			
		} catch (Exception e) {
			callAlert("화면 전환 오류 :화면 전환에 문제가 있습니다");
			e.printStackTrace();
		}		
	}
	private void callAlert(String contentText) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setHeaderText(contentText.substring(0,contentText.lastIndexOf(":")));
		alert.setContentText(contentText.substring(contentText.lastIndexOf(":")+1));
		alert.showAndWait();
	}
	private void clock() {
		Task<Void> task =new Task<Void>() {

			@Override
			protected Void call() throws Exception {
				try {
					count=0;
					while(true) {
						count++;
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
						String strDate = sdf.format(new Date());
						Platform.runLater(() -> {
							forHereToGoTxtClock.setText(strDate);	
						});
						Thread.sleep(1000);
					}
				} catch (InterruptedException e) {
					Platform.runLater(() -> {
					callAlert("문제 : 문제");
					});
				}				return null;
			}
			 
		};
		Thread thread = new Thread(task);
		thread.setDaemon(true);
		thread.start();
		
	}
}
