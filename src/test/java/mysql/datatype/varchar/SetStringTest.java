package mysql.datatype.varchar;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;

import study.connection.Dbms;

//CREATE TABLE nullable_varchar(vc VARCHAR(5));
public class SetStringTest {

	public static void main(String[] args) throws SQLException {
		Dbms mysql = Dbms.LOCAL_MYSQL;
		Connection con = mysql.getTestConnection();

		PreparedStatement pstmt = con.prepareStatement("insert into nullable_varchar(vc) values(?)");
		// setNull대신 setString(번호,null) 에러발생x
		pstmt.setString(1, null);
		System.out.println(pstmt.executeUpdate());

		pstmt.clearParameters();

		pstmt.setNull(1, Types.VARCHAR);
		System.out.println(pstmt.executeUpdate());

		pstmt.close();
		con.close();
	}

}
