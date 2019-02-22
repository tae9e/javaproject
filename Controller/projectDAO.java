package Controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import Model.Modeling;

public class projectDAO {
	public static ArrayList<Modeling> dbArrayList = new ArrayList<>();
	public static ArrayList<Modeling> dbChartArrayList = new ArrayList<Modeling>();
	public static ArrayList<Modeling> dbInventoryArrayList = new ArrayList<>();
	public static ArrayList<Modeling> dbInventory2ArrayList = new ArrayList<>();
	

	

	// 주문창
	public static int insertData(Modeling modeling) {
		StringBuffer insertProduct = new StringBuffer();
		insertProduct.append("insert into ordertb ");
		insertProduct.append("(thatday,thattime,receiptno,orderno,instore,food,price) ");
		insertProduct.append("values ");
		insertProduct.append("(?,?,?,?,?,?,?) ");

		Connection con = null;
		int count = 0;
		// 1.3 쿼리문을 실행해야할 Statement를 만들어야 한다.
		PreparedStatement psmt = null;

		try {
			con = DBUtility.getConnection();
			psmt = con.prepareStatement(insertProduct.toString());
			// 1.4 쿼리문에 실제데이터를 연결한다.
			psmt.setString(1, modeling.getThatDay());
			psmt.setString(2, modeling.getThatTime());
			psmt.setInt(3, modeling.getReceiptNo());
			psmt.setInt(4, modeling.getOrderNo());
			psmt.setInt(5, modeling.getInStore());
			psmt.setString(6, modeling.getFood());
			psmt.setInt(7, modeling.getPrice());

			// 1.5 실제 데이터를 연결한 퀴리문을 실행하라
			count = psmt.executeUpdate();
			if (count == 0) {
				LoginController.callAlert("삽입 쿼리 실패 : 삽입쿼리문 실패");
				return count;
			}

		} catch (SQLException e) {
			LoginController.callAlert("삽입 실패 : 데이터베이스에 삽입실패");
			e.printStackTrace();
		} finally {
			try {// 1.6 자원객체를 닫아야 한다.
				if (psmt != null) {
					psmt.close();
				}
				if (con != null) {
					con.close();
				}
			} catch (SQLException e) {
				LoginController.callAlert("닫기 실패 : psmt, con 닫기실패");
			}
		}
		return count;
	}

	// 주문확인
	public static ArrayList<Modeling> getOrderDBData() {
		// 2.1 데이터베이스에서 ordertb에 있는 레코드를 모두 가져오는 쿼리문
		String selectModeling = "select * from ordertb";
		// 2.2 데이타베이스 connetion을 가져와야 한다.
		Connection con = null;
		// 2.3 쿼리문을 실행해야할 Statement를 만들어야 한다.
		PreparedStatement psmt = null;
		// 2.4 쿼리문을 실행하고 나서 가져와야할 레코드를 담고있는 보자기 객체
		ResultSet rs = null;

		try {
			con = DBUtility.getConnection();
			psmt = con.prepareStatement(selectModeling);

			// 1.5 실제 데이터를 연결한 퀴리문을 실행하라(번개모양 아이콘)
			// executeQuery(); 쿼리문을 실행해서 결과를 가져올때 사용하는 번개모양아이콘
			// executeUpdate(); 쿼리문을 실행해서 테이블에 저장을 할때 사용하는 번개모양아이콘
			rs = psmt.executeQuery();
			if (rs == null) {
				LoginController.callAlert("select 실패 : select 쿼리문 실패");
				return null;
			}
			while (rs.next()) {
				Modeling modeling = new Modeling(rs.getString(1), rs.getString(2), rs.getInt(3), rs.getInt(4),
						rs.getInt(5), rs.getString(6), rs.getInt(7));
				dbArrayList.add(modeling);
			}

		} catch (SQLException e) {
			LoginController.callAlert("삽입 실패 : 데이터베이스에 삽입실패");
			e.printStackTrace();
		} finally {
			try {// 2.6 자원객체를 닫아야 한다.
				if (psmt != null) {
					psmt.close();
				}
				if (con != null) {
					con.close();
				}
			} catch (SQLException e) {
				LoginController.callAlert("닫기 실패 : psmt, con 닫기실패");
			}
		}
		return dbArrayList;
	}
	//주문하면 재고빠짐ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ
	public static ArrayList<Modeling> orderNInventory(String str) {
		// 
		String selectModeling = "select product, productNumber from inventorytbl where product = ? ";
		// 2.2 데이타베이스 connetion을 가져와야 한다.
		Connection con = null;
		// 2.3 쿼리문을 실행해야할 Statement를 만들어야 한다.
		PreparedStatement psmt = null;
		// 2.4 쿼리문을 실행하고 나서 가져와야할 레코드를 담고있는 보자기 객체
		ResultSet rs = null;

		try {
			con = DBUtility.getConnection();
			psmt = con.prepareStatement(selectModeling);
			psmt.setString(1, str);
			System.out.println(str+"DAO");
			// 1.5 실제 데이터를 연결한 퀴리문을 실행하라(번개모양 아이콘)
			// executeQuery(); 쿼리문을 실행해서 결과를 가져올때 사용하는 번개모양아이콘
			// executeUpdate(); 쿼리문을 실행해서 테이블에 저장을 할때 사용하는 번개모양아이콘
			rs = psmt.executeQuery();
			if (rs == null) {
				LoginController.callAlert("select 실패 : select 쿼리문 실패");
				return null;
			}
			while (rs.next()) {
				Modeling modeling = new Modeling(rs.getString(1), rs.getInt(2),0,0);
				System.out.println(modeling+"modeling");
				dbInventory2ArrayList.add(modeling);
			}
		} catch (SQLException e) {
			LoginController.callAlert("삽입 실패 : 데이터베이스에 삽입실패");
			e.printStackTrace();
		} finally {
			try {// 2.6 자원객체를 닫아야 한다.
				if (psmt != null) {
					psmt.close();
				}
				if (con != null) {
					con.close();
				}
			} catch (SQLException e) {
				LoginController.callAlert("닫기 실패 : psmt, con 닫기실패");
			}
		}
		return dbInventory2ArrayList;
	}
	//재고 뺀 다음 업데이트ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ
	public static int updateInventoryset(Modeling update) {

				StringBuffer updateStudent=new StringBuffer();
				updateStudent.append("update inventorytbl set ");
				updateStudent.append("productNumber=? where product=? ");
				
				// 1.2 데이타베이스 connetion을 가져와야 한다.
				Connection con = null;
				int count=0;
				// 1.3 쿼리문을 실행해야할 Statement를 만들어야 한다.
				PreparedStatement psmt = null;
				try {
					con = DBUtility.getConnection();
					psmt = con.prepareStatement(updateStudent.toString());
					// 1.4 쿼리문에 실제데이터를 연결한다.
					
					psmt.setInt(1, update.getProductNumber());
					psmt.setString(2, update.getProduct());						
					// 1.5 실제 데이터를 연결한 퀴리문을 실행하라
					count = psmt.executeUpdate();
					if (count == 0) {
						LoginController.callAlert("수정 쿼리 실패 : 수정쿼리문 실패");
						return count;
					}

				} catch (SQLException e) {
					LoginController.callAlert("수정 실패 : 데이터베이스에 수정실패");
				} finally {
					try {// 1.6 자원객체를 닫아야 한다.
						if (psmt != null) {
							psmt.close();
						}
						if (con != null) {
							con.close();
						}
					} catch (SQLException e) {
						LoginController.callAlert("닫기 실패 : psmt, con 닫기실패");
					}
				}
		return count;
	}

	// 재고 확인
	public static ArrayList<Modeling> getInventoryDBData() {
		// 2.1 데이터베이스에서 inventorytbl에 있는 레코드를 모두 가져오는 쿼리문
		String selectInventoryModeling = "select * from inventorytbl order by productNo asc ";
		// 2.2 데이타베이스 connetion을 가져와야 한다.
		Connection conInventory = null;
		// 2.3 쿼리문을 실행해야할 Statement를 만들어야 한다.
		PreparedStatement psmtInventory = null;
		// 2.4 쿼리문을 실행하고 나서 가져와야할 레코드를 담고있는 보자기 객체
		ResultSet rsInventory = null;

		try {
			conInventory = DBUtility.getConnection();
			psmtInventory = conInventory.prepareStatement(selectInventoryModeling);

			// 1.5 실제 데이터를 연결한 퀴리문을 실행하라(번개모양 아이콘)
			// executeQuery(); 쿼리문을 실행해서 결과를 가져올때 사용하는 번개모양아이콘
			// executeUpdate(); 쿼리문을 실행해서 테이블에 저장을 할때 사용하는 번개모양아이콘
			rsInventory = psmtInventory.executeQuery();
			if (rsInventory == null) {
				LoginController.callAlert("select 실패 : select 쿼리문 실패");
				return null;
			}
			while (rsInventory.next()) {
				Modeling inventorymodeling = new Modeling(rsInventory.getInt(1), rsInventory.getString(2),
						rsInventory.getInt(3), rsInventory.getString(4));
				dbInventoryArrayList.add(inventorymodeling);
			}

		} catch (SQLException e) {
			LoginController.callAlert("삽입 실패 : 데이터베이스에 삽입실패");
			e.printStackTrace();
		} finally {
			try {// 2.6 자원객체를 닫아야 한다.
				if (psmtInventory != null) {
					psmtInventory.close();
				}
				if (conInventory != null) {
					conInventory.close();
				}
			} catch (SQLException e) {
				LoginController.callAlert("닫기 실패 : psmtInventory, conInventory 닫기실패");
			}
		}
		return dbInventoryArrayList;
	}

	// 재고 저장
	public static int insertInventoryData(Modeling inventoryData) {
		StringBuffer insertProduct = new StringBuffer();
		insertProduct.append("insert into inventorytbl ");
		insertProduct.append("(productNo,product,productNumber,expirationDate) ");
		insertProduct.append("values ");
		insertProduct.append("(?,?,?,?) ");

		Connection conInventoryInsert = null;
		int count = 0;
		// 1.3 쿼리문을 실행해야할 Statement를 만들어야 한다.
		PreparedStatement psmtInventoryInsert = null;

		try {
			conInventoryInsert = DBUtility.getConnection();
			psmtInventoryInsert = conInventoryInsert.prepareStatement(insertProduct.toString());
			// 1.4 쿼리문에 실제데이터를 연결한다.
			psmtInventoryInsert.setInt(1, inventoryData.getProductNo());
			psmtInventoryInsert.setString(2, inventoryData.getProduct());
			psmtInventoryInsert.setInt(3, inventoryData.getProductNumber());
			psmtInventoryInsert.setString(4, inventoryData.getExpirationDate());

			// 1.5 실제 데이터를 연결한 퀴리문을 실행하라
			count = psmtInventoryInsert.executeUpdate();
			if (count == 0) {
				LoginController.callAlert("삽입 쿼리 실패 : 삽입쿼리문 실패");
				return count;
			}

		} catch (SQLException e) {
			LoginController.callAlert("삽입 실패 : 데이터베이스에 삽입실패");
			e.printStackTrace();
		} finally {
			try {// 1.6 자원객체를 닫아야 한다.
				if (psmtInventoryInsert != null) {
					psmtInventoryInsert.close();
				}
				if (conInventoryInsert != null) {
					conInventoryInsert.close();
				}
			} catch (SQLException e) {
				LoginController.callAlert("닫기 실패 : psmtInventoryInsert, conInventoryInsert 닫기실패");
			}
		}
		return count;
	}

	// 재고 수정
	public static int inventoryModify(Modeling inventoryModifyData) {
		StringBuffer inventoryModify = new StringBuffer();
		inventoryModify.append("update inventorytbl set ");
		inventoryModify.append("productNo=?, product=?, productNumber=?, expirationDate=? where productNo=? ");

		// 1.2 데이타베이스 connetion을 가져와야 한다.
		Connection conInventoryModifyData = null;
		int count = 0;
		// 1.3 쿼리문을 실행해야할 Statement를 만들어야 한다.
		PreparedStatement psmtInventoryModifyData = null;
		try {
			conInventoryModifyData = DBUtility.getConnection();
			psmtInventoryModifyData = conInventoryModifyData.prepareStatement(inventoryModify.toString());
			// 1.4 쿼리문에 실제데이터를 연결한다.

			psmtInventoryModifyData.setInt(1, inventoryModifyData.getProductNo());
			psmtInventoryModifyData.setString(2, inventoryModifyData.getProduct());
			psmtInventoryModifyData.setInt(3, inventoryModifyData.getProductNumber());
			psmtInventoryModifyData.setString(4, inventoryModifyData.getExpirationDate());
			psmtInventoryModifyData.setInt(5, inventoryModifyData.getProductNo());
			// 1.5 실제 데이터를 연결한 퀴리문을 실행하라
			count = psmtInventoryModifyData.executeUpdate();
			if (count == 0) {
				LoginController.callAlert("수정 쿼리 실패 : 수정쿼리문 실패");
				return count;
			}

		} catch (SQLException e) {
			LoginController.callAlert("수정 실패 : 데이터베이스에 수정실패");
			e.printStackTrace();
		} finally {
			try {// 1.6 자원객체를 닫아야 한다.
				if (psmtInventoryModifyData != null) {
					psmtInventoryModifyData.close();
				}
				if (conInventoryModifyData != null) {
					conInventoryModifyData.close();
				}
			} catch (SQLException e) {
				LoginController.callAlert("닫기 실패 : psmt, con 닫기실패");
			}
		}
		return count;
	}

	//삭제
	public static int deleteInventoryData(int productNo) {
		
			String deleteInventory = "delete from inventorytbl where productNo = ?";
			// 3.2 데이타베이스 connetion을 가져와야 한다.
			Connection con = null;
			// 3.3 쿼리문을 실행해야할 Statement를 만들어야 한다.
			PreparedStatement psmt = null;
			// 3.4 쿼리문을 실행하고 나서 가져와야할 레코드를 담고있는 보자기 객체
			int count=0;
			
			try {
				con = DBUtility.getConnection();
				psmt = con.prepareStatement(deleteInventory);
				psmt.setInt(1, productNo);
				// 1.5 실제 데이터를 연결한 퀴리문을 실행하라(번개모양 아이콘)
				//executeQuery(); 쿼리문을 실행해서 결과를 가져올때 사용하는 번개모양아이콘
				//executeUpdate(); 쿼리문을 실행해서 테이블에 저장을 할때 사용하는 번개모양아이콘
				count = psmt.executeUpdate();
				if (count == 0) {
					LoginController.callAlert("delete 실패 : delete 쿼리문 실패");
					return count;
				}
				
			} catch (SQLException e) {
				LoginController.callAlert("delete 실패 : 데이터베이스 delete에 삭제실패");
			} finally {
				try {// 2.6 자원객체를 닫아야 한다.
					if (psmt != null) {
						psmt.close();
					}
					if (con != null) {
						con.close();
					}
				} catch (SQLException e) {
					LoginController.callAlert("닫기 실패 : psmt, con 닫기실패");
				}
			}
		return count;
	}	
	//총합계
	public static ArrayList<Modeling> getTotalPrice() {
		// 2.1 데이터베이스에서 ordertb에 있는 레코드를 모두 가져오는 쿼리문
		String selectModeling = "select sum(price) from ordertb ";
		// 2.2 데이타베이스 connetion을 가져와야 한다.
		Connection con = null;
		// 2.3 쿼리문을 실행해야할 Statement를 만들어야 한다.
		PreparedStatement psmt = null;
		// 2.4 쿼리문을 실행하고 나서 가져와야할 레코드를 담고있는 보자기 객체
		ResultSet rs = null;

		try {
			con = DBUtility.getConnection();
			psmt = con.prepareStatement(selectModeling);

			// 1.5 실제 데이터를 연결한 퀴리문을 실행하라(번개모양 아이콘)
			// executeQuery(); 쿼리문을 실행해서 결과를 가져올때 사용하는 번개모양아이콘
			// executeUpdate(); 쿼리문을 실행해서 테이블에 저장을 할때 사용하는 번개모양아이콘
			rs = psmt.executeQuery();
			if (rs == null) {
				LoginController.callAlert("select 실패 : select 쿼리문 실패");
				return null;
			}
			while (rs.next()) {
				Modeling modeling = new Modeling(rs.getInt(1));
				dbArrayList.add(modeling);
			}

		} catch (SQLException e) {
			LoginController.callAlert("삽입 실패 : 데이터베이스에 삽입실패");
			e.printStackTrace();
		} finally {
			try {// 2.6 자원객체를 닫아야 한다.
				if (psmt != null) {
					psmt.close();
				}
				if (con != null) {
					con.close();
				}
			} catch (SQLException e) {
				LoginController.callAlert("닫기 실패 : psmt, con 닫기실패");
			}
		}
		return dbArrayList;
	}
	//차트만들기
	public static ArrayList<Modeling> getChartData() {
		// 2.1 데이터베이스에서 ordertb에 있는 레코드를 모두 가져오는 쿼리문
		String selectModeling = "select thatday, sum(price) as '매출' from ordertb group by thatday ";
		// 2.2 데이타베이스 connetion을 가져와야 한다.
		Connection con = null;
		// 2.3 쿼리문을 실행해야할 Statement를 만들어야 한다.
		PreparedStatement psmt = null;
		// 2.4 쿼리문을 실행하고 나서 가져와야할 레코드를 담고있는 보자기 객체
		ResultSet rs = null;

		try {
			con = DBUtility.getConnection();
			psmt = con.prepareStatement(selectModeling);
			// 1.5 실제 데이터를 연결한 퀴리문을 실행하라(번개모양 아이콘)
			// executeQuery(); 쿼리문을 실행해서 결과를 가져올때 사용하는 번개모양아이콘
			// executeUpdate(); 쿼리문을 실행해서 테이블에 저장을 할때 사용하는 번개모양아이콘
			rs = psmt.executeQuery();
		
			if (rs == null) {
				LoginController.callAlert("select 실패 : select 쿼리문 실패");
				return null;
			}
			while (rs.next()) {
				Modeling modeling = new Modeling(rs.getString(1), rs.getInt(2),0);
//				Modeling modeling=new Modeling(0);
//				Modeling.ThatDay that=modeling.new ThatDay(rs.getString(1), rs.getInt(2));
				dbChartArrayList.add(modeling);
//				System.out.println(dbChartArrayList);
			}
		} catch (SQLException e) {
			LoginController.callAlert("삽입 실패 : 데이터베이스에 삽입실패");
			e.printStackTrace();
		} finally {
			try {// 2.6 자원객체를 닫아야 한다.
				if (psmt != null) {
					psmt.close();
				}
				if (con != null) {
					con.close();
				}
			} catch (SQLException e) {
				LoginController.callAlert("닫기 실패 : psmt, con 닫기실패");
			}
		}
		return dbChartArrayList;
	}
}
