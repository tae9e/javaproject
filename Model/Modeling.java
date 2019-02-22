package Model;

public class Modeling {
	//발권기(orderTbl)
	private String thatDay;
	private String thatTime;
	private int receiptNo;
	private int orderNo;
	private int inStore;
	private String food;
	private int sideMenu;
	private int price;
	//재고(inventoryTbl)
	private int productNo;
	private String product;
	private int productNumber;
	private String expirationDate;
	//매니져(
	private String managerID;
	private String managerPassword;
	//점포정보(
	private int storeNo;
	private String storePassword;

	public Modeling(String food) {
		super();
		this.food = food;
	}
	//합계
	public Modeling(int price) {
		super();
		this.price = price;
	}
	//첫번째 포스창
	public Modeling(String thatDay, String thatTime, int receiptNo, int inStore, String food, int price) {
		super();
		this.thatDay = thatDay;
		this.thatTime = thatTime;
		this.receiptNo = receiptNo;
		this.inStore = inStore;
		this.food = food;
		this.price = price;
	}
	//키친전달목록
	public Modeling( String food, String thatTime, String thatDay, int inStore) {
		super();
		this.food = food;
		this.thatTime = thatTime;
		this.thatDay = thatDay;
		this.inStore = inStore;
	}
	//주문목록
	public Modeling(String food, int price) {
		super();
		this.food = food;
		this.price = price;
	}
	public Modeling(int productNumber,String food, int num2, int num3) {
		super();
		this.food = food;
		this.productNumber = productNumber;
	}
		
	public Modeling(String product, int productNumber, int num2, int num3) {
		super();
		this.product = product;
		this.productNumber = productNumber;
	}
	
	public Modeling(String thatDay, int price, int num) {
		super(); 
		this.thatDay = thatDay;
		this.price = price;
		}
	 
	//발권기(orderTbl)
	public Modeling(String thatDay, String thatTime, int receiptNo, int orderNo, int inStore, String food, int price) {
		super();
		this.thatDay = thatDay;
		this.thatTime = thatTime;
		this.receiptNo = receiptNo;
		this.orderNo = orderNo;
		this.inStore = inStore;
		this.food = food;
		this.price = price;
	}
	//재고(inventoryTbl)
	public Modeling(int productNo, String product, int productNumber, String expirationDate) {
		super();
		this.productNo = productNo;
		this.product = product;
		this.productNumber = productNumber;
		this.expirationDate = expirationDate;
	}
	//매니저
	public Modeling(String managerID, String managerPassword) {
		super();
		this.managerID = managerID;
		this.managerPassword = managerPassword;
	}
	//점포정보
	public Modeling(int storeNo, String storePassword) {
		super();
		this.storeNo = storeNo;
		this.storePassword = storePassword;
	}
	
	
	public String getThatDay() {
		return thatDay;
	}
	public void setThatDay(String thatDay) {
		this.thatDay = thatDay;
	}
	public String getThatTime() {
		return thatTime;
	}
	public void setThatTime(String thatTime) {
		this.thatTime = thatTime;
	}
	public int getReceiptNo() {
		return receiptNo;
	}
	public void setReceiptNo(int receiptNo) {
		this.receiptNo = receiptNo;
	}
	public int getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(int orderNo) {
		this.orderNo = orderNo;
	}
	public int getInStore() {
		return inStore;
	}
	public void setInStore(int inStore) {
		this.inStore = inStore;
	}
	public String getFood() {
		return food;
	}
	public void setfood(String food) {
		this.food = food;
	}
	public int getSideMenu() {
		return sideMenu;
	}
	public void setSideMenu(int sideMenu) {
		this.sideMenu = sideMenu;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public int getProductNo() {
		return productNo;
	}
	public void setProductNo(int productNo) {
		this.productNo = productNo;
	}
	public String getProduct() {
		return product;
	}
	public void setProduct(String product) {
		this.product = product;
	}
	public int getProductNumber() {
		return productNumber;
	}
	public void setProductCount(int productNumber) {
		this.productNumber = productNumber;
	}
	public String getExpirationDate() {
		return expirationDate;
	}
	public void setExpirationDate(String expirationDate) {
		this.expirationDate = expirationDate;
	}
	public String getManagerID() {
		return managerID;
	}
	public void setManagerID(String managerID) {
		this.managerID = managerID;
	}
	public String getManagerPassword() {
		return managerPassword;
	}
	public void setManagerPassword(String managerPassword) {
		this.managerPassword = managerPassword;
	}
	public int getStoreNo() {
		return storeNo;
	}
	public void setStoreNo(int storeNo) {
		this.storeNo = storeNo;
	}
	public String getStorePassword() {
		return storePassword;
	}
	public void setStorePassword(String storePassword) {
		this.storePassword = storePassword;
	}
	//
}