package study.connection;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import study.meta.ResultInfo;

public enum Dbms {
	LAB_POSTGRESQL("LabPostgreSQL", "비공개"),
	LAB_ORACLE("LabOracle", "비공개"),
	LOCAL_MYSQL("LocalMySql", "jdbc:mysql://127.0.0.1:3306/jdbc?user=root&password=1234");

	// LOCAL_POSTGRESQL("LocalPostgreSQL", "jdbc:postgresql://127.0.0.1:5432/데이터베이스입력?user=사용자입력&password=비밀번호입력");

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

	public void catalogInfo() throws SQLException {
		System.out.println(this.dbmsName);

		Connection con = this.getConnection();

		DatabaseMetaData dbMetaData = con.getMetaData();
		ResultSet resultSet = dbMetaData.getCatalogs();

		ResultInfo resultInfo = this.getResultSetInfo("DatabaseMetaData.getCatalogs()", resultSet);
		System.out.println(resultInfo.toString());

//		while (resultSet.next()) {
//
//		}

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
