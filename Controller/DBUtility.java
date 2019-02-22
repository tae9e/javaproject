package Controller;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBUtility {

	public static Connection getConnection() {
		Connection con=null;
		try {
		// 1. Mysql database class를 로드한다.
		Class.forName("com.mysql.jdbc.Driver");
		// 2. 주소, 아이디, 비밀본호를 통해서 접속요청한다.
		con =DriverManager.getConnection("jdbc:mysql://localhost/new_project","root","123456");
//		LoginController.callAlert("데이터베이스 연결성공 : 데이터베이스연결이 성공했음");
		}catch(Exception e){
			LoginController.callAlert("데이터베이스 연결실패 : 점검");
			e.printStackTrace();
			return null;
		}
		return con;
	}
	
}
