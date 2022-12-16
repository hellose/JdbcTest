package oracle.datatype.timestamp;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

import study.connection.Dbms;

//windows에서 표시는 최대 6자리, 측정은3자리까지
//linux에서만 정확히 동작함
public class OracleTimestampPrecisionTest {

	public static void main(String[] args) throws Exception {
		Dbms labOracle = Dbms.LAB_ORACLE;

		Connection con = labOracle.getConnection();

		// NLS_TIMESTAMP_FORMAT 확인
		String checkParam = "select value from V$NLS_PARAMETERS where parameter = 'NLS_TIMESTAMP_FORMAT'";
		ResultSet checkResultSet = con.createStatement().executeQuery(checkParam);
		checkResultSet.next();
		System.out.println("NLS_TIMESTAMP_FORMAT: " + checkResultSet.getString(1));
		System.out.println();
		checkResultSet.close();

		// NLS_TIMESTAMP_FORMAT 변경 및 재확인
//		String alterParam = "alter session set nls_timestamp_format = 'YYYY-MM-DD HH:MI:SS.FF5'";
//		con.createStatement().execute(alterParam);
//		checkResultSet = con.createStatement().executeQuery(checkParam);
//		checkResultSet.next();
//		System.out.println("NLS_TIMESTAMP_FORMAT: " + checkResultSet.getString(1));
//		checkResultSet.close();

		// Timestamp타입 컬럽 precision 테스트
		String queryString = "select * from timestamp_precision";
		ResultSet resultSet2 = con.createStatement().executeQuery(queryString);

		ResultSetMetaData meta = resultSet2.getMetaData();
		int colSize = meta.getColumnCount();

		String[] colNameArray = new String[colSize];
		for (int i = 0; i < colSize - 1; i++) {
			colNameArray[i] = meta.getColumnName(i + 1);
		}

		int rowNum = 1;
		while (resultSet2.next()) {
			System.out.print("row" + rowNum + "\n");
			for (int i = 1; i <= colSize; i++) {
				System.out.print(colNameArray[i - 1] + ": " + "getString() ->" + resultSet2.getString(i)
						+ ", getTimestamp() ->" + resultSet2.getTimestamp(i).toString() + "\n");

			}
			rowNum++;
		}

		resultSet2.close();

		Dbms.closeAll();
	}

}
