package study.connection;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import study.meta.DatabaseMetaType;
import study.meta.ResultInfo;

public enum Dbms {
	LAB_POSTGRESQL("LabPostgreSQL", "비공개"), LAB_ORACLE("LabOracle", "비공개"),
	LOCAL_MYSQL("LocalMySql", "jdbc:mysql://127.0.0.1:3306/jdbc?user=root&password=1234");

	// LOCAL_POSTGRESQL("LocalPostgreSQL",
	// "jdbc:postgresql://127.0.0.1:5432/데이터베이스입력?user=사용자입력&password=비밀번호입력");

	private String dbmsName;
	private String jdbcUrl;
	private Connection connection;

	private Dbms(String dbmsName, String jdbcUrl) {
		this.dbmsName = dbmsName;
		this.jdbcUrl = jdbcUrl;
		try {
			this.connection = DriverManager.getConnection(jdbcUrl);
		} catch (SQLException e) {
			System.out.println("----------------------" + this.dbmsName + " getConnection failed");
			e.printStackTrace();
		}
	}

	private Connection getConnection() throws SQLException {

		Connection con = null;

		try {
			if (this.connection != null) {
				if (this.connection.isClosed()) {
					this.connection = DriverManager.getConnection(this.jdbcUrl);
				}
				con = this.connection;
			} else {
				this.connection = DriverManager.getConnection(this.jdbcUrl);
				con = this.connection;
			}
		} catch (SQLException e) {
			throw e;
		}

		return con;
	}

	private void close() throws SQLException {

		if (this.connection == null) {
			return;
		}

		try {
			if (this.connection.isClosed()) {
				this.connection = null;
				return;
			} else {
				this.connection.close();
				this.connection = null;
				return;
			}

		} catch (SQLException e) {
			throw e;
		}
	}

	public static void closeAll() throws SQLException {

		Dbms[] dbmsArray = Dbms.values();
		for (Dbms dbms : dbmsArray) {
			dbms.close();
			dbms.connection = null;
		}
	}

	public void printSelectQeury(String queryString) throws SQLException {
		System.out.println(this.dbmsName);

		Connection con = this.getConnection();
		Statement stmt = con.createStatement();

		ResultSet resultSet = stmt.executeQuery(queryString);

		ResultInfo resultInfo = this.getResultSetInfo(queryString, resultSet);
		System.out.println(resultInfo.toString());

		resultSet.close();
		stmt.close();
	}

	public void getDbInfoAndData(DatabaseMetaType type) throws SQLException {
		System.out.println(this.dbmsName);

		Connection con = this.getConnection();

		DatabaseMetaData dbMetaData = con.getMetaData();
		ResultSet resultSet = null;

		switch (type) {
		case CATALOG:
			resultSet = dbMetaData.getCatalogs();
			break;
		case SCHEMA:
			resultSet = dbMetaData.getSchemas();
			break;
		case TABLE:
			// avoid catalog hard coding
			String currentCatalog = this.getConnection().getCatalog();
			resultSet = dbMetaData.getTables(currentCatalog, null, null, null);
			break;
		default:
			throw new RuntimeException();
		}

		ResultInfo resultInfo = this.getResultSetInfo(type.getMetaString(), resultSet);
		System.out.println(resultInfo.toString());

		int columnSize = resultInfo.getColumnSize();
		StringBuilder sb = new StringBuilder();

		int rowNum = 1;
		while (resultSet.next()) {
			// row number
			sb.append("rowNum");
			sb.append(rowNum);
			sb.append("\n");

			// column's value
			for (int i = 0; i < columnSize; i++) {
				int paramIndex = i + 1;
				sb.append(paramIndex);
				sb.append(":");
				sb.append(resultSet.getString(i + 1));
				if (i != columnSize - 1) {
					sb.append(",");
				}
			}
			System.out.println(sb.toString());
			sb.setLength(0);
			rowNum++;
		}

		System.out.println();
		resultSet.close();
	}

	private ResultInfo getResultSetInfo(String queryString, ResultSet resultSet) throws SQLException {

		if (resultSet == null || resultSet.isClosed()) {
			return null;
		}

		return new ResultInfo(queryString, resultSet);
	}

	public Connection getTestConnection() throws SQLException {
		return this.getConnection();
	}

}
