package Controller;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class ChooseController implements Initializable {
	public Stage chooseStage;

	@FXML
	private Button chooseBtnHall;
	@FXML
	private Button chooseBtnKitchen;
	@FXML
	private Button chooseBtnPOS;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		chooseBtnHall.setOnAction((ActionEvent event) -> {
			handleChooseBtnHallAction();
		});
		chooseBtnKitchen.setOnAction((ActionEvent event) -> {
			handleChooseBtnKitchenAction();
		});
		chooseBtnPOS.setOnAction((ActionEvent event) -> {
			handleChooseBtnPOSAction();
		});

	}

	private void handleChooseBtnHallAction() {
		try {
			Stage forHereToGoStage = new Stage();
			FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/forHereToGo.fxml"));
			Parent forHereToGo = loader.load();
			ForHereToGoController forHereToGoController = loader.getController();
			forHereToGoController.forHereToGoStage = forHereToGoStage;
			Scene scene = new Scene(forHereToGo);
			forHereToGoStage.setScene(scene);
//			chooseStage.close();
			forHereToGoStage.show();

		} catch (Exception e) {
			callAlert("화면 전환 오류 :화면 전환에 문제가 있습니다");
			e.printStackTrace();
		}
	}

	private void handleChooseBtnKitchenAction() {
		try {
			Stage kitchenStage = new Stage();
			FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/kitchen.fxml"));
			Parent kitchen = loader.load();
			KitchenController kitchenController = loader.getController();
			kitchenController.kitchenStage = kitchenStage;
			Scene scene = new Scene(kitchen);
			kitchenStage.setScene(scene);
//			chooseStage.close();
			kitchenStage.show();

			callAlert("화면 전환 성공 :메인화면으로 전환되었습니다");
		} catch (Exception e) {
			callAlert("화면 전환 오류 :화면 전환에 문제가 있습니다");
			e.printStackTrace();
		}
	}

	private void handleChooseBtnPOSAction() {
		
		try {
			Stage managerLoginStage = new Stage(StageStyle.UTILITY);
			managerLoginStage.initModality(Modality.WINDOW_MODAL);
			managerLoginStage.initOwner(chooseStage);
			managerLoginStage.setTitle("매니저 로그인");
			FXMLLoader loader2 = new FXMLLoader(getClass().getResource("../View/managerLogin.fxml"));
			Parent managerLogin = loader2.load();
			// *********************************************************
			TextField managerLoginTextId = (TextField) managerLogin.lookup("#managerLoginTextId");
			PasswordField managerLoginTextPassword = (PasswordField) managerLogin.lookup("#managerLoginTextPassword");

			Button managerLoginBtnLogin = (Button) managerLogin.lookup("#managerLoginBtnLogin");
			Button managerLoginBtnClose = (Button) managerLogin.lookup("#managerLoginBtnClose");
			// *********************************************************
			
			managerLoginBtnLogin.setOnAction((e) -> {
//				if (!(managerLoginTextId.getText().equals("q") && managerLoginTextPassword.getText().equals("q"))) {
//					callAlert("로그인 실패 :아이디와 비밀번호를 확인하여 주세요");
//					managerLoginTextId.clear();
//					managerLoginTextPassword.clear();
//					return;
//				}else {
				Connection con=null;
				PreparedStatement pstmt=null;
				ResultSet rs=null;
				
				String id=managerLoginTextId.getText();
				String password=managerLoginTextPassword.getText();
				String sql= "select managerID, managerPassword " + "from managertbl where managerID = ? and managerPassword = ? ";
				
			try {	
				con=DBUtility.getConnection();
				pstmt=con.prepareStatement(sql);
				pstmt.setString(1, id);
				pstmt.setString(2, password);
				rs=pstmt.executeQuery();
				
				if(rs.next()) {
					id=rs.getString("managerID");
					password=rs.getString("managerPassword");
					
				
					try {
						Stage posStage=new Stage();
						FXMLLoader loader=new FXMLLoader(getClass().getResource("../View/pos.fxml"));
						Parent pos=loader.load();
						PosController posController =loader.getController();
						posController.posStage=posStage;
						Scene scene=new Scene(pos);
						posStage.setScene(scene);
//						chooseStage.close();
						managerLoginStage.close();
						posStage.show();
						
//						callAlert("화면 전환 성공 :메인화면으로 전환되었습니다");
					} catch (IOException e1) {
						callAlert("화면 전환 오류 :화면 전환에 문제가 있습니다");
						e1.printStackTrace();
					}	
				}else {
					callAlert("로그인 실패 :아이디와 비밀번호를 확인하여 주세요");
					managerLoginTextId.clear(); managerLoginTextPassword.clear();
					return;
				}
			} catch (Exception e1) {
				e1.printStackTrace();
			}
				
			});
			managerLoginBtnClose.setOnAction((e) -> {
				managerLoginStage.close();
			});
			Scene scene2 = new Scene(managerLogin);
			managerLoginStage.setScene(scene2);
			managerLoginStage.show();
		} catch (IOException e) {
			e.printStackTrace();
			callAlert("로그인문제 : 문제생김");
			callAlert("화면 전환 오류 :화면 전환에 문제가 있습니다");
			e.printStackTrace();
		}
	}

	private void callAlert(String contentText) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setHeaderText(contentText.substring(0, contentText.lastIndexOf(":")));
		alert.setContentText(contentText.substring(contentText.lastIndexOf(":") + 1));
		alert.showAndWait();
	}
}
