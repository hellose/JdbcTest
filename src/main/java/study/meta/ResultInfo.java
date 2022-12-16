package study.meta;

import java.util.ArrayList;
import java.util.List;

import lombok.Setter;

@Setter
public class ResultInfo {

	int columnSize = 0;
	List<Column> columns = new ArrayList<>();

	public void addColumn(Column columnInfo) {
		this.columns.add(columnInfo);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();

		sb.append("----------------------ResultInfo(column size:");
		sb.append(this.columnSize);
		sb.append(")\n");

		for (Column col : columns) {
			String str = col.toString();
			sb.append(str);
			sb.append("\n");
		}

		return sb.toString();
	}

}
