package Controller;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

import Model.Modeling;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class KitchenController implements Initializable{
	public Stage kitchenStage;
	
	@FXML TableView<Modeling> kitchenTableView;
	@FXML TextField kitchenTxtClock;
	
	private Modeling select;
	private int count=0;
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		setTableView();
		clock();
		kitchenTableView.setOnMouseClicked((e)->{handleKitchenTableViewAction(e);});
	}

	private void setTableView() {
		
		TableColumn tcFood=kitchenTableView.getColumns().get(0);
		tcFood.setCellValueFactory(new PropertyValueFactory<>("food"));
		tcFood.setStyle("-fx-alignment: CENTER;");
		
		TableColumn tcTime=kitchenTableView.getColumns().get(1);
		tcTime.setCellValueFactory(new PropertyValueFactory<>("thatTime"));
		tcTime.setStyle("-fx-alignment: CENTER;");
		
		TableColumn tcDate=kitchenTableView.getColumns().get(2);
		tcDate.setCellValueFactory(new PropertyValueFactory<>("thatDay"));
		tcDate.setStyle("-fx-alignment: CENTER;");
		
		TableColumn tcInStore=kitchenTableView.getColumns().get(3);
		tcInStore.setCellValueFactory(new PropertyValueFactory<>("inStore"));
		tcInStore.setStyle("-fx-alignment: CENTER;");
		
		kitchenTableView.setItems(HallController.kitchenListData);
		
	}

	private void handleKitchenTableViewAction(MouseEvent e) {
		select=kitchenTableView.getSelectionModel().getSelectedItem();
		if(e.getClickCount()==2) {
			HallController.kitchenListData.remove(select);
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
							kitchenTxtClock.setText(strDate);	
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
