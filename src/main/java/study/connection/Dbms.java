package study.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import study.meta.Column;
import study.meta.ResultInfo;

public enum Dbms {
	// dbsm jdbc url 복원 필요
	// LAB_POSTGRESQL("LabPostgreSQL", ""),
	// LAB_ORACLE("LabOracle", "");

	LOCAL_POSTGRESQL("LocalPostgreSQL", "jdbc:postgresql://127.0.0.1:5432/데이터베이스입력?user=사용자입력&password=비밀번호입력");
	
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

	public void selectQuery(String queryString) throws SQLException {
		Connection con = this.getConnection();
		Statement stmt = con.createStatement();

		ResultSet resultSet = stmt.executeQuery(queryString);

		ResultInfo resultInfo = this.getResultSetInformation(resultSet);
		System.out.println(queryString);
		System.out.println(resultInfo.toString());

		resultSet.close();
		stmt.close();
	}

	private ResultInfo getResultSetInformation(ResultSet resultSet) throws SQLException {

		if (resultSet == null || resultSet.isClosed()) {
			return null;
		}

		ResultSetMetaData meta = resultSet.getMetaData();
		ResultInfo resultInfo = new ResultInfo();

		int columnSize = meta.getColumnCount();
		for (int i = 0; i <= columnSize - 1; i++) {
			Column column = new Column();
			column.setName(meta.getColumnName(i + 1));
			column.setTypeName(meta.getColumnTypeName(i + 1));
			column.setType(meta.getColumnType(i + 1));
			column.setPrecision(meta.getPrecision(i + 1));
			resultInfo.addColumn(column);
		}

		resultInfo.setColumnSize(columnSize);

		return resultInfo;
	}

}
