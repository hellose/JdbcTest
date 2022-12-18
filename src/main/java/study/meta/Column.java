package study.meta;

import lombok.AllArgsConstructor;
import lombok.ToString;

@ToString
@AllArgsConstructor
public class Column {

	private String name;
	private int type;
	private String typeName;
	private int precision;

}
