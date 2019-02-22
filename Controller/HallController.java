package Controller;

import java.net.URL;
import java.text.DecimalFormat;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

import javax.jws.WebParam.Mode;

import Model.Modeling;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.SelectionModel;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class HallController implements Initializable {
	public Stage hallStage;

	@FXML private Button hallBtnHotMenu;
	@FXML private Button hallBtnFood1;
	@FXML private Button hallBtnFood2;
	@FXML private Button hallBtnFood3;
	@FXML private Button hallBtnFood4;
	@FXML private Button hallBtnOrder;
	@FXML private Button hallBtnAllCancel;
	@FXML private Button hallBtnMainPage;
	@FXML private Tab hallPageHotMenu;
	@FXML private Tab hallPageFood1;
	@FXML private Tab hallPageFood2;
	@FXML private Tab hallPageFood3;
	@FXML private Tab hallPageFood4;
	@FXML private TabPane hallTabPane;
	@FXML private TextField hallTabTxtClock;
	@FXML private Button hallTab1BtnRiceM1;
	@FXML private Button hallTab1BtnRiceL1;
	@FXML private Button hallTab1BtnCurryM1;
	@FXML private Button hallTab1BtnCurryL1;
	@FXML private Button hallTab4BtnSalad1;
	@FXML private Button hallTab4BtnBeer1;
	@FXML private Button hallTab1BtnRiceM;
	@FXML private Button hallTab1BtnRiceL;
	@FXML private Button hallTab2BtnCurryM;
	@FXML private Button hallTab2BtnCurryL;
	@FXML private Button hallTab4BtnBeer;
	@FXML private Button hallTab4BtnSalad;
	@FXML private TableView<Modeling> hallTableView;
	@FXML private Label hallTab1LbRiceMPrice;
	@FXML private Label hallTab1LbRiceLPrice;
	@FXML private Label hallTab2LbCurryMPrice;
	@FXML private Label hallTab2LbCurryLPrice;
	@FXML private Label hallTab4LbSaladPrice;
	@FXML private Label hallTab4LbBeerPrice;
	@FXML private TextField hallTxtTotalPrice;
	@FXML private TextField hallTxtInsertMoney;
	@FXML private TextField hallTxtReturn;

	public static ObservableList<Modeling> kitchenListData = FXCollections.observableArrayList();
	ObservableList<Modeling> listData = FXCollections.observableArrayList();

	ArrayList<Modeling> list;
	private int sum = 0;
	private int foodIndex;
	private int count = 0;
	public static int orderNumber = 1;

	private int RiceM = 6000;
	private int RiceL = 6500;
	private int CurryM = 6500;
	private int CurryL = 7000;
	private int Salad = 3000;
	private int Beer = 3000;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// 텍스트필드
		setTextFieldInputFormat();
		// 탭전환
		hallBtnHotMenu.setOnAction((ActionEvent event) -> {handleHallBtnHotMenuAction();});
		hallBtnFood1.setOnMouseClicked(arg0 -> {handleHallBtnFood1Action();	});
		hallBtnFood2.setOnAction((ActionEvent event) -> {handleHallBtnFood2Action();});
		hallBtnFood3.setOnAction((ActionEvent event) -> {handleHallBtnFood3Action();});
		hallBtnFood4.setOnAction((ActionEvent event) -> {handleHallBtnFood4Action();});
		// 주문쪽 버튼
		hallBtnOrder.setOnAction((ActionEvent event) -> {handleHallBtnOrderAction();});
		hallBtnAllCancel.setOnAction((ActionEvent event) -> {handleHallBtnAllCancelAction();});
		hallBtnMainPage.setOnAction((ActionEvent event) -> {handleHallBtnMainPageAction();});
		// 메뉴버튼
		hallTab1BtnRiceM1.setOnAction((ActionEvent event) -> {handleHallTab1BtnRiceMAction();});
		hallTab1BtnRiceL1.setOnAction((ActionEvent event) -> {handleHallTab1BtnRiceLAction();});
		hallTab1BtnCurryM1.setOnAction((ActionEvent event) -> {handleHallTab2BtnCurryMAction();});
		hallTab1BtnCurryL1.setOnAction((ActionEvent event) -> {handleHallTab1BtnCurryLAction();});
		hallTab4BtnSalad1.setOnAction((ActionEvent event) -> {handleHallTab4BtnSaladAction();});
		hallTab4BtnBeer1.setOnAction((ActionEvent event) -> {handleHallTab4BtnBeerAction();});
		hallTab1BtnRiceM.setOnAction((ActionEvent event) -> {handleHallTab1BtnRiceMAction();});
		hallTab1BtnRiceL.setOnAction((ActionEvent event) -> {handleHallTab1BtnRiceLAction();});
		hallTab2BtnCurryM.setOnAction((ActionEvent event) -> {handleHallTab2BtnCurryMAction();});
		hallTab2BtnCurryL.setOnAction((ActionEvent event) -> {handleHallTab1BtnCurryLAction();});
		hallTab4BtnSalad.setOnAction((ActionEvent event) -> {handleHallTab4BtnSaladAction();});
		hallTab4BtnBeer.setOnAction((ActionEvent event) -> {handleHallTab4BtnBeerAction();});
		// 테이블뷰
		setTableView();
		// 합계텍스트필드
		totalTextField(0);
		// 시간표시
		clock();

	}

	private void setTextFieldInputFormat() {
		inputDecimalFormat(hallTxtInsertMoney);
		inputDecimalFormat(hallTxtTotalPrice);
		inputDecimalFormat(hallTxtReturn);
	}

	// 인기메뉴 탭전환
	private void handleHallBtnHotMenuAction() {
		SingleSelectionModel<Tab> selectionModel = hallTabPane.getSelectionModel();
		selectionModel.select(hallPageHotMenu);
	}

	// 1번탭전환
	private void handleHallBtnFood1Action() {
		SingleSelectionModel<Tab> selectionModel = hallTabPane.getSelectionModel();
		selectionModel.select(hallPageFood1);
	}

	// 2번탭전환
	private void handleHallBtnFood2Action() {
		SingleSelectionModel<Tab> selectionModel = hallTabPane.getSelectionModel();
		selectionModel.select(hallPageFood2);
	}

	// 3번탭전환
	private void handleHallBtnFood3Action() {
		SingleSelectionModel<Tab> selectionModel = hallTabPane.getSelectionModel();
		selectionModel.select(hallPageFood3);
	}

	// 4번탭전환
	private void handleHallBtnFood4Action() {
		SingleSelectionModel<Tab> selectionModel = hallTabPane.getSelectionModel();
		selectionModel.select(hallPageFood4);
	}
	// 주문버튼
	private void handleHallBtnOrderAction() {
		try {
			int insert = Integer.parseInt(hallTxtInsertMoney.getText());
			int total = Integer.parseInt(hallTxtTotalPrice.getText());
			int returnMoney = 0;

			returnMoney = insert - total;
			hallTxtReturn.setText(String.valueOf(returnMoney));
			if (returnMoney < 0) {
				callAlert("금액부족 : 금액이 부족합니다.");
				return;
			} else if (returnMoney > 0) {///////////////////////////////////////////////////////////////////////////////////////////////////////
				for (Modeling mo : listData) {
					System.out.println(listData+"1");
					String food = mo.getFood();
					int price = mo.getPrice();
					kitchenListData.add(new Modeling(food, hallTabTxtClock.getText().substring(11),
							hallTabTxtClock.getText().substring(5, 10), ForHereToGoController.flag));
					Modeling modeling = new Modeling(hallTabTxtClock.getText().substring(0, 10),
							hallTabTxtClock.getText().substring(11), 0, orderNumber, ForHereToGoController.flag, food,
							price);

					System.out.println(list+"2");
					System.out.println(food+"3");
					
					list=projectDAO.orderNInventory(food);
					
					System.out.println(list+"4");
					System.out.println(food+"5");
					for(Modeling momo:list) {
						String product=momo.getProduct();
						int productNumber=momo.getProductNumber();
						if(product.equals(food)) {
							productNumber=productNumber-1;
						}else if(product.equals("밥 中")) {
							productNumber=productNumber-1;
						}else if(product.equals("밥 大")) {
							productNumber=productNumber-1;
						}else if(product.equals("카레 中")) {
							productNumber=productNumber-1;
						}else {
							productNumber=productNumber-1;
						}
						Modeling update=new Modeling(product, productNumber,0,0);
						int count=projectDAO.updateInventoryset(update);
						if(count!=0) {
//							callAlert("입력성공 : 데이터베이스에 입력이 성공되었습니다");
						}
					}
					
					int count = projectDAO.insertData(modeling);
					if (count != 0) {
//						callAlert("입력성공 : 데이터베이스에 입력이 성공되었습니다");
					}
				}
				callAlert("주문완료 : 주문완료! 잔돈 " + returnMoney + "원이 반환됩니다");
			} else {
				for (Modeling mo : listData) {
					System.out.println(listData+"1");
					String food = mo.getFood();
					int price = mo.getPrice();
					kitchenListData.add(new Modeling(food, hallTabTxtClock.getText().substring(11),
							hallTabTxtClock.getText().substring(5, 10), ForHereToGoController.flag));
					Modeling modeling = new Modeling(hallTabTxtClock.getText().substring(0, 10),
							hallTabTxtClock.getText().substring(11), 0, orderNumber, ForHereToGoController.flag, food,
							price);

					System.out.println(list+"2");
					System.out.println(food+"3");
					
					list=projectDAO.orderNInventory(food);
					
					System.out.println(list+"4");
					System.out.println(food+"5");
					for(Modeling momo:list) {
						String product=momo.getProduct();
						int productNumber=momo.getProductNumber();
						if(product.equals(food)) {
							productNumber=productNumber-1;
						}else if(product.equals("밥 中")) {
							productNumber=productNumber-1;
						}else if(product.equals("밥 大")) {
							productNumber=productNumber-1;
						}else if(product.equals("카레 中")) {
							productNumber=productNumber-1;
						}else {
							productNumber=productNumber-1;
						}
						Modeling update=new Modeling(product, productNumber,0,0);
						int count=projectDAO.updateInventoryset(update);
						if(count!=0) {
//							callAlert("입력성공 : 데이터베이스에 입력이 성공되었습니다");
						}
					}
					
					int count = projectDAO.insertData(modeling);
					if (count != 0) {
//						callAlert("입력성공 : 데이터베이스에 입력이 성공되었습니다");
					}
				}
				callAlert("주문 완료 : 주문이 완료되었습니다.");
			}
		} catch (NumberFormatException e) {
			callAlert("금액부족 : 금액이 부족합니다.");
			return;
		}
		try {
			listData.removeAll(listData);
			Stage forHereToGoStage = new Stage();
			FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/forHereToGo.fxml"));
			Parent kitchen = loader.load();
			ForHereToGoController forHereToGoController = loader.getController();
			forHereToGoController.forHereToGoStage = forHereToGoStage;
			Scene scene = new Scene(kitchen);
			forHereToGoStage.setScene(scene);
			hallStage.close();
			forHereToGoStage.show();
		} catch (Exception e) {
			callAlert("화면 전환 오류 :화면 전환에 문제가 있습니다");
			e.printStackTrace();
		}
		orderNumber++;
		
	}

	// 주문목록 리스트뷰 전체 삭제
	private void handleHallBtnAllCancelAction() {
		try {
			listData.removeAll(listData);
			hallTxtTotalPrice.clear();
			sum = 0;
		} catch (IndexOutOfBoundsException e) {
		}
	}

	// 주문목록 리스트뷰 전체삭제/메인페이지로
	private void handleHallBtnMainPageAction() {
		try {
			listData.removeAll(listData);
			Stage forHereToGoStage = new Stage();
			FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/forHereToGo.fxml"));
			Parent kitchen = loader.load();
			ForHereToGoController forHereToGoController = loader.getController();
			forHereToGoController.forHereToGoStage = forHereToGoStage;

			Scene scene = new Scene(kitchen);
			forHereToGoStage.setScene(scene);
			hallStage.close();
			forHereToGoStage.show();
//			System.out.println(ForHereToGoController.flag+"나감");

		} catch (Exception e) {
			callAlert("화면 전환 오류 :화면 전환에 문제가 있습니다");
			e.printStackTrace();
		}
	}

//	메뉴 버튼
	private void handleHallTab1BtnRiceMAction() { // 밥M
		int riceMPrice = 0;

		riceMPrice = Integer.parseInt(hallTab1LbRiceMPrice.getText().substring(0, 4));
		Modeling modeling = new Modeling(hallTab1BtnRiceM.getText(), riceMPrice);

		listData.add(modeling);
		totalTextField(RiceM);
	}

	private void handleHallTab1BtnRiceLAction() { // 밥L
		int riceLPrice = 0;

		riceLPrice = Integer.parseInt(hallTab1LbRiceLPrice.getText().substring(0, 4));
		Modeling modeling = new Modeling(hallTab1BtnRiceL.getText(), riceLPrice);

		listData.add(modeling);
		totalTextField(RiceL);
	}

	private void handleHallTab2BtnCurryMAction() { // 카레M
		int CurryMPrice = 0;

		CurryMPrice = Integer.parseInt(hallTab2LbCurryMPrice.getText().substring(0, 4));
		Modeling modeling = new Modeling(hallTab2BtnCurryM.getText(), CurryMPrice);

		listData.add(modeling);
		totalTextField(CurryM);
	}

	private void handleHallTab1BtnCurryLAction() { // 카레L
		int CurryLPrice = 0;

		CurryLPrice = Integer.parseInt(hallTab2LbCurryLPrice.getText().substring(0, 4));
		Modeling modeling = new Modeling(hallTab2BtnCurryL.getText(), CurryLPrice);

		listData.add(modeling);
		totalTextField(CurryL);
	}

	private void handleHallTab4BtnSaladAction() { // 샐러드
		int SaladPrice = 0;

		SaladPrice = Integer.parseInt(hallTab4LbSaladPrice.getText().substring(0, 4));
		Modeling modeling = new Modeling(hallTab4BtnSalad.getText(), SaladPrice);

		listData.add(modeling);
		totalTextField(Salad);

	}

	private void handleHallTab4BtnBeerAction() { // 맥주
		int BeerPrice = 0;

		BeerPrice = Integer.parseInt(hallTab4LbBeerPrice.getText().substring(0, 4));
		Modeling modeling = new Modeling(hallTab4BtnBeer.getText(), BeerPrice);

		listData.add(modeling);
		totalTextField(Beer);

	}

	private void callAlert(String contentText) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setHeaderText(contentText.substring(0, contentText.lastIndexOf(":")));
		alert.setContentText(contentText.substring(contentText.lastIndexOf(":") + 1));
		alert.showAndWait();
	}

	// 테이블뷰
	private void setTableView() {
		TableColumn tcFood = hallTableView.getColumns().get(0);
		tcFood.setCellValueFactory(new PropertyValueFactory<>("food"));
		tcFood.setStyle("-fx-alignment: CENTER;");

		TableColumn tcPrice = hallTableView.getColumns().get(1);
		tcPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
		tcPrice.setStyle("-fx-alignment: CENTER;");
		hallTableView.setItems(listData);
	}

	private void totalTextField(int i) {
		sum = sum + i;
		hallTxtTotalPrice.setText(String.valueOf(sum));
//		for(Modeling str : listData) {
//			int str1=str.getPrice();
//			sum=sum+str1;
//		}
//		
//		hallTxtTotalPrice.setText(String.valueOf(sum));
	}

	// 숫자만 입력
	private void inputDecimalFormat(TextField textField) {
		// 숫자만 입력(정수만 입력받음), 실수입력받고싶으면 new DecimalFormat("###.#");
		DecimalFormat format = new DecimalFormat("######");
		// 점수 입력시 길이 제한 이벤트 처리
		textField.setTextFormatter(new TextFormatter<>(event -> {
			// 입력받은 내용이 없으면 이벤트를 리턴함.
			if (event.getControlNewText().isEmpty()) {
				return event;
			}
			// 구문을 분석할 시작 위치를 지정함.
			ParsePosition parsePosition = new ParsePosition(0);
			// 입력받은 내용과 분석위치를 지정한지점부터 format 내용과 일치한지 분석함.
			Object object = format.parse(event.getControlNewText(), parsePosition);
			// 리턴값이 null 이거나, 입력한길이와 구문분석위치값이 적어버리면(다 분석하지못했음을 뜻함) 거나, 입력한길이가 4이면(3자리를 넘었음을
			// 뜻함.) 이면 null 리턴함.
			if (object == null || parsePosition.getIndex() < event.getControlNewText().length()
					|| event.getControlNewText().length() == 7) {
				return null;
			} else {
				return event;
			}
		}));
	}

	// 시계
	private void clock() {
		Task<Void> task = new Task<Void>() {

			@Override
			protected Void call() throws Exception {
				try {
					count = 0;
					while (true) {
						count++;
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
						String strDate = sdf.format(new Date());
						Platform.runLater(() -> {
							hallTabTxtClock.setText(strDate);
						});
						Thread.sleep(1000);
					}
				} catch (InterruptedException e) {
					Platform.runLater(() -> {
						callAlert("문제 : 문제");
					});
				}
				return null;
			}
		};
		Thread thread = new Thread(task);
		thread.setDaemon(true);
		thread.start();
	}
}