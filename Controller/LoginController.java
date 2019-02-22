package Controller;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.application.Platform;
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
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

public class LoginController implements Initializable {
	public Stage loginStage;
	
	@FXML private Button loginBtnLogin;
	@FXML private Button loginBtnClose;
	@FXML private TextField loginTextId;
	@FXML private PasswordField loginTextPassword;
	@FXML private ImageView loginImageView;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		loginBtnLogin.setOnAction((ActionEvent event)->{handleLoginBtnLoginAction();});
		loginBtnClose.setOnAction((ActionEvent event)->{handleLoginBtnCloseAction();});
		loginTextPassword.setOnKeyPressed(event -> {
	         if (event.getCode().equals(KeyCode.ENTER)) {
	        	 handleLoginBtnLoginAction();
	         }
	      });
	}

	private void handleLoginBtnLoginAction() {
		
			Connection con=null;
			PreparedStatement pstmt=null;
			ResultSet rs=null;
			
			String id=loginTextId.getText();
			String password=loginTextPassword.getText();
			String sql= "select storeNo, storePassword " + "from storetbl where storeNo = ? and storePassword = ? ";
			
		try {	
			con=DBUtility.getConnection();
			pstmt=con.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.setString(2, password);
			rs=pstmt.executeQuery();
			
			if(rs.next()) {
				id=rs.getString("storeNo");
				password=rs.getString("storePassword");
				
			
				try {
					Stage chooseStage=new Stage();
					FXMLLoader loader=new FXMLLoader(getClass().getResource("../View/choose.fxml"));
					Parent login=loader.load();
					ChooseController chooseController =loader.getController();
					chooseController.chooseStage=chooseStage;
					Scene scene=new Scene(login);
					chooseStage.setScene(scene);
					loginStage.close();
					chooseStage.show();
					
					callAlert("화면 전환 성공 :메인화면으로 전환되었습니다");
				} catch (IOException e) {
					callAlert("화면 전환 오류 :화면 전환에 문제가 있습니다");
					e.printStackTrace();
				}	
			}else {
				callAlert("로그인 실패 :아이디와 비밀번호를 확인하여 주세요");
				loginTextId.clear(); loginTextPassword.clear();
				return;
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
//		if(!(loginTextId.getText().equals("q")&&loginTextPassword.getText().equals("q"))) {
//			callAlert("로그인 실패 :아이디와 비밀번호를 확인하여 주세요");
//			loginTextId.clear(); loginTextPassword.clear();
//			return;
//		}
	
	}
	
	private void handleLoginBtnCloseAction() {
		loginStage.close();

		
	}
	
	public static void callAlert(String contentText) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setHeaderText(contentText.substring(0,contentText.lastIndexOf(":")));
		alert.setContentText(contentText.substring(contentText.lastIndexOf(":")+1));
		alert.showAndWait();
	}
}
