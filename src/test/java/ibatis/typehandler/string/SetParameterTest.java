package ibatis.typehandler.string;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.StringTypeHandler;

import study.connection.Dbms;

//create table notnull_varchar(vc varchar(100) not null);
public class SetParameterTest {

	public static void main(String[] args) throws SQLException {
		Dbms mysql = Dbms.LOCAL_MYSQL;
		Connection con = mysql.getTestConnection();

		PreparedStatement pstmt = con.prepareStatement("insert into notnull_varchar(vc) values(?)");

		// ibatis StringTypeHandler 사용
		StringTypeHandler stringHandler = new StringTypeHandler();
		String value = "value";
		// BaseTypeHandler<String>.setParameter 호출 -> 내부적으로 StringTypeHandler.setNonNullParameter호출
		stringHandler.setParameter(pstmt, 1, value, JdbcType.forCode(Types.VARCHAR));

		int effectedRowCount = pstmt.executeUpdate();
		System.out.println("effectedRowCount: " + effectedRowCount);
		con.close();

	}
}