package Controller;

import java.net.URL;
import java.text.DecimalFormat;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import Model.Modeling;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class PosController implements Initializable{
	public Stage posStage;
	
	@FXML private Tab posPageOrderList;
	@FXML private Tab posPageSales;
	@FXML private Tab posPageInventory;
	@FXML private Tab posPageInventoryOrder;
	@FXML private Label posLbOrderList;
	@FXML private Label posLbSales;
	@FXML private Label posLbInventory;
	@FXML private Label posLbInventoryOrder;
	@FXML private TabPane posTabPane;
	@FXML private TextField posInventoryTxtInventoryNo;
	@FXML private TextField posInventoryTxtProduct;
	@FXML private TextField posInventoryTxtNumber;
	@FXML private TextField posTextMoney;
	@FXML private TextField posOrderListTxtTime;
	@FXML private TextField posSalesTxtTime;
	@FXML private DatePicker posInventoryDatePicker;
	@FXML private Button posInventoryBtnRegister;
	@FXML private Button posInventoryBtnModify;
	@FXML private Button posInventoryBtnRemove;
	@FXML private Button posOrderRefresh;
	@FXML private Button posInventoryRefresh; 
	@FXML private LineChart<?, ?> posLinechart;
	
	@FXML private TableView<Modeling> posOrderTableView;
	@FXML private TableView<Modeling> posSalesTableView;
	@FXML private TableView<Modeling> posInventoryTableView;
	
	private Modeling select;
	private int selectIndex;
	private int count=0;
	
	
	ObservableList<Modeling> orderList = FXCollections.observableArrayList();
	ObservableList<Modeling> inventoryList = FXCollections.observableArrayList();
	
	ArrayList<Modeling> dbArrayList;
	ArrayList<Modeling> dbInventoryArrayList;
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		//dbInventoryArrayList.clear();
		//숫자로 제한
		setTextFieldInputFormat();
		//탭버튼세팅
		posLbOrderList.setOnMouseClicked(arg0 ->{handlePosPageOrderListAction();});
		posLbSales.setOnMouseClicked(arg0 ->{handleposPosPageSalesAction();});
		posLbInventory.setOnMouseClicked(arg0 ->{handlePosPageInventoryAction();});
		//주문테이블뷰세팅
		orderTableView();
		//주문창 새로고침
		posOrderRefresh.setOnAction((ActionEvent event)->{handlePosOrderRefreshAction();});
		//매출테이블뷰세팅
		salesTableView();
		//재고테이블뷰세팅
		inventoryTableView();
		//재고창 새로고침
		posInventoryRefresh.setOnAction((ActionEvent event)->{handlePosInventoryRefreshAction();});
		//재고창의 버튼세개
		posInventoryBtnRegister.setOnAction((ActionEvent event)->{handlePosInventoryBtnRegisterAction();});
		posInventoryTableView.setOnMouseClicked((e)->{handlePosInventoryBtnModifyAction(e);});
		posInventoryBtnModify.setOnAction((ActionEvent event)->{handlePosInventoryBtnModifyAction();});
		posInventoryBtnRemove.setOnAction((ActionEvent event)->{handlePosInventoryBtnRemoveAction();});
		//차트
		chart();
		//합계
		total();
		//시계
		clock();
	}


	private void setTextFieldInputFormat() {
		inputDecimalFormat(posInventoryTxtInventoryNo);
		inputDecimalFormat(posInventoryTxtNumber);
	}

	private void handlePosPageOrderListAction() {
		SingleSelectionModel<Tab> selectionModel= posTabPane.getSelectionModel();
		
		selectionModel.select(posPageOrderList);		
	}
	private void handleposPosPageSalesAction() {
		SingleSelectionModel<Tab> selectionModel= posTabPane.getSelectionModel();
		
		selectionModel.select(posPageSales);			
	}
	private void handlePosPageInventoryAction() {
		SingleSelectionModel<Tab> selectionModel= posTabPane.getSelectionModel();
		
		selectionModel.select(posPageInventory);			
	}

	
	//오더테이블
	private void orderTableView() {
		
		TableColumn tcReceiptNo=posOrderTableView.getColumns().get(0);
		tcReceiptNo.setCellValueFactory(new PropertyValueFactory<>("receiptNo"));
		tcReceiptNo.setStyle("-fx-alignment: CENTER;");
		
		TableColumn tcThatDay=posOrderTableView.getColumns().get(1);
		tcThatDay.setCellValueFactory(new PropertyValueFactory<>("thatDay"));
		tcThatDay.setStyle("-fx-alignment: CENTER;");
		
		TableColumn tcThatTime=posOrderTableView.getColumns().get(2);
		tcThatTime.setCellValueFactory(new PropertyValueFactory<>("thatTime"));
		tcThatTime.setStyle("-fx-alignment: CENTER;");

		TableColumn tcInStore=posOrderTableView.getColumns().get(3);
		tcInStore.setCellValueFactory(new PropertyValueFactory<>("inStore"));
		tcInStore.setStyle("-fx-alignment: CENTER;");
		
		TableColumn tcFood=posOrderTableView.getColumns().get(4);
		tcFood.setCellValueFactory(new PropertyValueFactory<>("food"));
		tcFood.setStyle("-fx-alignment: CENTER;");
		
		TableColumn tcPrice=posOrderTableView.getColumns().get(5);
		tcPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
		tcPrice.setStyle("-fx-alignment: CENTER;");	
		
		dbArrayList=projectDAO.getOrderDBData();
		
		for(Modeling modeling : dbArrayList) {
			Modeling order = new Modeling(
					modeling.getThatDay(),
					modeling.getThatTime(),
					modeling.getReceiptNo(),
					modeling.getInStore(),
					modeling.getFood(),
					modeling.getPrice());
			orderList.add(order);
		}
		posOrderTableView.setItems(orderList);
		
	}
	//주문창 새로고침버튼
	private void handlePosOrderRefreshAction() {
		
		  dbArrayList.clear();
		  orderList.clear();
		  dbArrayList=projectDAO.getOrderDBData();
			
			for(Modeling modeling : dbArrayList) {
				Modeling order = new Modeling(
						modeling.getThatDay(),
						modeling.getThatTime(),
						modeling.getReceiptNo(),
						modeling.getInStore(),
						modeling.getFood(),
						modeling.getPrice());
				orderList.add(order);
			}
			posOrderTableView.setItems(orderList);
	}

	//세일즈테이블
	private void salesTableView() {
		
	}
	//재고테이블
	private void inventoryTableView() {
			
		TableColumn tcProductNo=posInventoryTableView.getColumns().get(0);
		tcProductNo.setCellValueFactory(new PropertyValueFactory<>("productNo"));
		tcProductNo.setStyle("-fx-alignment: CENTER;");
		
		TableColumn tcProduct=posInventoryTableView.getColumns().get(1);
		tcProduct.setCellValueFactory(new PropertyValueFactory<>("product"));
		tcProduct.setStyle("-fx-alignment: CENTER;");
		
		TableColumn tcProductNumber=posInventoryTableView.getColumns().get(2);
		tcProductNumber.setCellValueFactory(new PropertyValueFactory<>("productNumber"));
		tcProductNumber.setStyle("-fx-alignment: CENTER;");
		
		TableColumn tcExpirationDate=posInventoryTableView.getColumns().get(3);
		tcExpirationDate.setCellValueFactory(new PropertyValueFactory<>("expirationDate"));
		tcExpirationDate.setStyle("-fx-alignment: CENTER;");
		
		dbInventoryArrayList=projectDAO.getInventoryDBData();
		
		for(Modeling modeling : dbInventoryArrayList) {
			Modeling inventory = new Modeling(
					modeling.getProductNo(),
					modeling.getProduct(),
					modeling.getProductNumber(),
					modeling.getExpirationDate());
			inventoryList.add(inventory);
		}
		posInventoryTableView.setItems(inventoryList);
	}
	//재고 새로고침
	private void handlePosInventoryRefreshAction() {	

		dbInventoryArrayList.clear();
		inventoryList.clear();
		dbInventoryArrayList=projectDAO.getInventoryDBData();
		for(Modeling modeling : dbInventoryArrayList) {
			Modeling inventory = new Modeling(
					modeling.getProductNo(),
					modeling.getProduct(),
					modeling.getProductNumber(),
					modeling.getExpirationDate());
			inventoryList.add(inventory);
		}
		posInventoryTableView.setItems(inventoryList);
	}
	//재고창에 입력후 등록버튼 누를때
	private void handlePosInventoryBtnRegisterAction() {
		try {
		Modeling modeling=new Modeling(Integer.parseInt(posInventoryTxtInventoryNo.getText()),
				posInventoryTxtProduct.getText(),
				Integer.parseInt(posInventoryTxtNumber.getText()),
				posInventoryDatePicker.getValue().toString());
		
		
		
		inventoryList.add(modeling);
		int count=projectDAO.insertInventoryData(modeling);
		if(count!=0) {
			callAlert("입력성공 : 데이터베이스에 입력이 성공되었습니다");
		}
		posInventoryTxtInventoryNo.clear();
		posInventoryTxtNumber.clear();
		posInventoryTxtProduct.clear();
		posInventoryDatePicker.getEditor().clear();
		}catch(NumberFormatException e) {
			callAlert("빈곳이 있습니다. : 모두 입력해주세요");
		}catch(NullPointerException e1) {
			callAlert("빈곳이 있습니다. : 모두 입력해주세요");
			return;
		}
	}
	//재고창 테이블뷰클릭할때값을 가져와 텍스트필드로 불러오는 함수
	private void handlePosInventoryBtnModifyAction(MouseEvent e) {
		try {
		select=posInventoryTableView.getSelectionModel().getSelectedItem();
		selectIndex=posInventoryTableView.getSelectionModel().getSelectedIndex();
		
		if(e.getClickCount()==1) {
			
			posInventoryTxtInventoryNo.setText(String.valueOf(select.getProductNo()));
			posInventoryTxtProduct.setText(select.getProduct());
			posInventoryTxtNumber.setText(String.valueOf(select.getProductNumber()));
			posInventoryDatePicker.setValue(LocalDate.parse(select.getExpirationDate()));
			}
		}catch(NullPointerException e1) {}
	}
	//재고창 수정
	private void handlePosInventoryBtnModifyAction() {
		try {
		Modeling inventory=new Modeling(Integer.parseInt(posInventoryTxtInventoryNo.getText()),
				posInventoryTxtProduct.getText(),
				Integer.parseInt(posInventoryTxtNumber.getText()),
				posInventoryDatePicker.getValue().toString());
		
		int count=projectDAO.inventoryModify(inventory);
		if(count!=0) {
			inventoryList.remove(selectIndex);
			inventoryList.add(selectIndex, inventory);
			int arrayIndex=dbInventoryArrayList.indexOf(select);
			dbInventoryArrayList.set(arrayIndex+1, inventory);
			
			posInventoryTxtInventoryNo.clear();
			posInventoryTxtNumber.clear();
			posInventoryTxtProduct.clear();
			posInventoryDatePicker.getEditor().clear();
			callAlert("수정완료 : "+select.getProduct()+"의 수정이 완료되었습니다");
		}else {
			return;
		}
		}catch(NumberFormatException e) {
			callAlert("빈곳이 있습니다. : 모두 입력해주세요");
		}catch(NullPointerException e1) {
			callAlert("빈곳이 있습니다. : 모두 입력해주세요");
		}
	}
	
	//재고창 삭제
	private void handlePosInventoryBtnRemoveAction() {	
		
		int count=projectDAO.deleteInventoryData(select.getProductNo());
		if(count !=0) {
		inventoryList.remove(selectIndex);
		
		inventoryList.remove(select);

		
		posInventoryTxtInventoryNo.clear();
		posInventoryTxtNumber.clear();
		posInventoryTxtProduct.clear();
		posInventoryDatePicker.getEditor().clear();
		
		callAlert("삭제완료: "+select.getProduct()+"의 삭제가 완료되었습니다");
		}else {
			return;	
			}
	}

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
	//차트
	private void chart() {
		XYChart.Series series=new XYChart.Series<>();
		
		ArrayList<Modeling> dbChartArrayList=projectDAO.getChartData();

		for(Modeling chart : dbChartArrayList) {
			String thatday=chart.getThatDay();
			int price=chart.getPrice();
			
		series.getData().add(new XYChart.Data(thatday,price));
		}
	
		posLinechart.getData().add(series);
	}

	//합계
	private void total() {
		int str=0;
		List<Modeling> list = projectDAO.getTotalPrice();
		for(Modeling mo : list) {
			str = mo.getPrice();
		}
		posTextMoney.setText(String.valueOf(str));
		
		
	}
	
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
	                	  posOrderListTxtTime.setText(strDate);
	                	  posSalesTxtTime.setText(strDate);
	                	  
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
	
	private void callAlert(String contentText) {		//알림창(중간에 : 을 적어줄것,,//ex."오류정보 :  값을 제대로 입력해주세요)
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setHeaderText(contentText.substring(0,contentText.lastIndexOf(":")));
		alert.setContentText(contentText.substring(contentText.lastIndexOf(":")+1));
		alert.showAndWait();
	}
}
