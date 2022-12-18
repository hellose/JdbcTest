package mysql.datatype.varchar;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.type.JdbcType;

import study.connection.Dbms;

//CREATE TABLE nullable(vc VARCHAR(5));
public class SetNullTest {

	public static void main(String[] args) throws SQLException {

		List<Integer> list = new ArrayList<>();

		for (JdbcType item : JdbcType.values()) {
			list.add(item.TYPE_CODE);
		}
		System.out.println("타입 개수: " + list.size());
		int size = list.size();

		Connection con = Dbms.LOCAL_MYSQL.getTestConnection();

		String query = "insert into nullable(vc) values(?)";
		PreparedStatement pstmt = con.prepareStatement(query);

		for (int i = 0; i < size; i++) {
			//setNull 두번째 파라메터 설정 테스트
			pstmt.setNull(1, list.get(i));
			int effected = pstmt.executeUpdate();
			pstmt.clearParameters();
		}

		pstmt.close();
		con.close();
	}
}
