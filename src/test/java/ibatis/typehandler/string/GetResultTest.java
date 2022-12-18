package ibatis.typehandler.string;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.ibatis.type.StringTypeHandler;

import study.connection.Dbms;

//CREATE TABLE notnull_varchar(vc VARCHAR(1000) NOT NULL);
//INSERT INTO notnull_varchar(vc) VALUES('test1'),('test2'),('test3');
public class GetResultTest {
	
	public static void main(String[] args) throws SQLException {
		Dbms mysql = Dbms.LOCAL_MYSQL;
		Connection con = mysql.getTestConnection();
		
		Statement stmt = con.createStatement();
		String queryString = "select vc from notnull_varchar";
		ResultSet resultSet = stmt.executeQuery(queryString);
		
		// ibatis StringTypeHandler 사용
		StringTypeHandler stringHandler = new StringTypeHandler();
		
		int rowCount = 1;
		while(resultSet.next()) {
			String value = null;
			// BaseTypeHandler<String>.getResult 호출 -> 내부적으로 StringTypeHandler.getNullableResult 호출
			value = stringHandler.getResult(resultSet, "vc");
			System.out.println(rowCount + ": " + value);
			rowCount++;
		}
		
		stmt.close();
		resultSet.close();
		
		con.close();
	}
	
}
