package study.meta;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import lombok.Setter;

@Setter
public class ResultInfo {

	private String queryString = "no query string";
	private int columnSize = 0;
	private List<Column> columns = new ArrayList<>();

	public ResultInfo(String queryString, ResultSet resultSet) throws SQLException {
		this(resultSet);

		if (queryString != null) {
			this.queryString = queryString;
		}
	}

	private ResultInfo(ResultSet resultSet) throws SQLException {
		ResultSetMetaData resultSetMetadata = resultSet.getMetaData();

		int columnSize = resultSetMetadata.getColumnCount();
		this.columnSize = columnSize;

		for (int i = 0; i <= columnSize - 1; i++) {

			String colName = resultSetMetadata.getColumnName(i + 1);
			int colType = resultSetMetadata.getColumnType(i + 1);
			String colTypeName = resultSetMetadata.getColumnTypeName(i + 1);
			int colPrecision = resultSetMetadata.getPrecision(i + 1);

			Column column = new Column(colName, colType, colTypeName, colPrecision);
			this.addColumn(column);
		}
	}

	public void addColumn(Column columnInfo) {
		this.columns.add(columnInfo);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();

		sb.append("----------------------ResultInfo(column size:");
		sb.append(this.columnSize);
		sb.append(")\n");

		sb.append(queryString);
		sb.append("\n");

		for (Column col : columns) {
			String str = col.toString();
			sb.append(str);
			sb.append("\n");
		}

		return sb.toString();
	}

}
